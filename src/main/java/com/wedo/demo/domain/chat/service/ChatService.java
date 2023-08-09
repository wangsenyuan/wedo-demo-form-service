package com.wedo.demo.domain.chat.service;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.wedo.demo.domain.chat.dto.ChatMessageDto;
import com.wedo.demo.domain.chat.dto.EstablishSession;
import com.wedo.demo.domain.message.ConsumerStatus;
import com.wedo.demo.domain.message.Message;
import com.wedo.demo.domain.message.MessageCenter;
import com.wedo.demo.domain.message.MessageSender;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private ConcurrentMap<String, MessageSender> senders = new ConcurrentHashMap<>();
    private ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    @Autowired
    private MessageCenter messageCenter;

    private volatile boolean closing;

    private Gson gson = new Gson();

    public void handleMessage(WebSocketSession session, String message) {
        if (closing) {
            return;
        }
        senders.compute(session.getId(), (sid, sender) -> {
            if (sender == null) {
                return createSender(session, message);
            }

            sendMessage(sender, message);

            return sender;
        });
    }

    private void sendMessage(MessageSender sender, String message) {
        // 需要将message进行转换，按照后端期望的结果进行处理
        // 并从中提取出receiver信息
//        sender.send(receiver, body);
        ChatMessageDto dto = gson.fromJson(message, ChatMessageDto.class);
        sender.send(dto.getReceiver(), message);
    }

    private MessageSender createSender(WebSocketSession session, String message) {
        // 这里期望message是用户身份信息
        EstablishSession establishSession = gson.fromJson(message, EstablishSession.class);
        this.sessions.put(establishSession.getUserId(), session);
        return messageCenter.register(establishSession.getUserId(), this::onReply);
    }

    private ConsumerStatus onReply(Message message) {
        if (closing) {
            return ConsumerStatus.STOP;
        }

        String receiver = message.getReceiver();

        WebSocketSession ses = this.sessions.compute(receiver, (k, session) -> {
            if (session == null || !session.isOpen()) {
                return null;
            }
            sendMessageToSession(session, message);
            return session;
        });

        if (ses == null) {
            logger.warn("message session already closed");
            return ConsumerStatus.STOP;
        }

        return ConsumerStatus.CONTINUE;
    }

    private void sendMessageToSession(WebSocketSession session, Message message) {
        try {
            ChatMessageDto dto = gson.fromJson(message.getBody(), ChatMessageDto.class);
            // add sender
            dto.setSender(message.getSender());
            session.sendMessage(new TextMessage(gson.toJson(dto)));
        } catch (IOException e) {
            logger.warn("failed to send message {} to {}", message.getId(), session.getId(), e);
        }
    }

    public void closeSession(WebSocketSession session) {
        String userId = null;
        for (Map.Entry<String, WebSocketSession> cur : this.sessions.entrySet()) {
            if (StringUtils.equals(session.getId(), cur.getValue().getId())) {
                userId = cur.getKey();
                break;
            }
        }
        if (userId != null) {
            this.sessions.remove(userId);
        }
        this.senders.remove(session.getId());
    }

    @PreDestroy
    public void destroy() {
        closing = true;
        Set<String> userIds = Sets.newHashSet(this.sessions.keySet());

        for (String userId : userIds) {
            WebSocketSession session = this.sessions.remove(userId);
            if (session != null && session.isOpen()) {
                closeWebSocket(session);
                this.senders.remove(session.getId());
            }
        }
    }

    private void closeWebSocket(WebSocketSession session) {
        try {
            session.close(CloseStatus.SERVICE_RESTARTED);
        } catch (IOException e) {
            logger.warn("close websocket session exception", e);
            // ignore
        }
    }
}
