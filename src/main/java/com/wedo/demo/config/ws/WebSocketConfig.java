package com.wedo.demo.config.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private List<WebSocketRequestHandler> handlers = new ArrayList<>();

    @Autowired(required = false)
    public void setHandlers(List<WebSocketRequestHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        handlers.forEach(handler -> {
            registry.addHandler(handler, handler.getPath()).setAllowedOrigins(handler.getAllowOrigins());
        });
    }
}
