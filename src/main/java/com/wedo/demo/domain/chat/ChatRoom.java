package com.wedo.demo.domain.chat;

import com.wedo.demo.config.ws.WebSocketRequestHandler;
import com.wedo.demo.domain.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Service
public class ChatRoom extends AbstractWebSocketHandler implements WebSocketRequestHandler {
    private static final String PATH = "/chat-room/**";
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
