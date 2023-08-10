package com.wedo.demo.domain.chat;

import com.wedo.demo.config.ws.WebSocketRequestHandler;
import com.wedo.demo.domain.chat.service.ChatService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Service
public class ChatRoom extends AbstractWebSocketHandler implements WebSocketRequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(ChatRoom.class);
    private static final String PATH = "/ws/chat-room/**";
    private static final String ALLOW_ORIGINS = "*";

    @Autowired
    private ChatService chatService;

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public String getAllowOrigins() {
        return ALLOW_ORIGINS;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String userId = (String) session.getAttributes().get("userId");
        logger.info("{}/{} is connected", session.getId(), userId);

        if (StringUtils.isEmpty(userId)) {
            logger.warn("no userId got, can't join the chat");
            session.close(CloseStatus.GOING_AWAY);
            return;
        }
        chatService.join(session, userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        chatService.handleMessage(session, message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        chatService.closeSession(session);
    }
}
