package com.wedo.demo.domain.chat.service;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.wedo.demo.domain.chat.dto.ChatMessage;
import com.wedo.demo.domain.chat.dto.ChatMessageType;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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
            closeWebSocket(session);
            return;
        }
        logger.debug("get message {} from {}", message, session.getId());
        senders.computeIfPresent(session.getId(), (sid, sender) -> {
            sendMessage(sender, sid, message);
            return sender;
        });
    }

    private void sendMessage(MessageSender sender, String sessionId, String message) {
        ChatMessage dto = gson.fromJson(message, ChatMessage.class);
        sender.send(dto.getReceiver(), sessionId, message);
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

            if (StringUtils.equals(session.getId(), message.getSessionId())) {
                sendMessageToSession(session, message);
            } else {
                logger.warn("message not in the same session {}/{}", message.getSessionId(), session.getId());
            }

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
            ChatMessage dto = gson.fromJson(message.getBody(), ChatMessage.class);
            // add sender
            if (dto != null) {
                dto.setSender(message.getSender());
            } else {
                dto = nullMessage(message.getSender());
            }
            session.sendMessage(new TextMessage(gson.toJson(dto)));
        } catch (Exception e) {
            logger.warn("failed to send message {} to {}", message.getId(), session.getId(), e);
        }
    }

    private static ChatMessage nullMessage(String sender) {
        ChatMessage dto = new ChatMessage();
        dto.setId(UUID.randomUUID().toString());
        dto.setMsgType(ChatMessageType.NULL);
        Map<String, String> payload = new HashMap<>();
        payload.put("value", "后台错误，未获取到有效信息");
        dto.setPayload(payload);
        dto.setSender(sender);
        return dto;
    }

    public void closeSession(WebSocketSession session) {
        logger.info("{} is closing session", session.getId());
        String userId = null;
        for (Map.Entry<String, WebSocketSession> cur : this.sessions.entrySet()) {
            if (StringUtils.equals(session.getId(), cur.getValue().getId())) {
                userId = cur.getKey();
                break;
            }
        }
        if (userId != null) {
            logger.info("sender for {} is removed", userId);
            this.sessions.remove(userId);
        }
        MessageSender sender = this.senders.remove(session.getId());
        if (sender != null) {
            sender.stop();
        }
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

    private static void closeWebSocket(WebSocketSession session) {
        try {
            session.close(CloseStatus.SERVICE_RESTARTED);
        } catch (IOException e) {
            logger.warn("close websocket session exception", e);
            // ignore
        }
    }

    public void join(WebSocketSession session, String userId) {
        this.sessions.put(userId, session);
        MessageSender sender = messageCenter.register(userId, this::onReply);
        this.senders.put(session.getId(), sender);
        logger.info("{}/{} join chat", session.getId(), userId);
    }
}
