package com.wedo.demo.config.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
    private List<WebSocketRequestHandler> handlers = new ArrayList<>();

    @Autowired(required = false)
    public void setHandlers(List<WebSocketRequestHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        handlers.forEach(handler -> {
            registry.addHandler(handler, handler.getPath()).setAllowedOrigins(handler.getAllowOrigins()).addInterceptors(userIdInterceptor());
        });
    }

    @Bean
    public HandshakeInterceptor userIdInterceptor() {
        return new HandshakeInterceptor() {
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

                // Get the URI segment corresponding to the auction id during handshake
                String path = request.getURI().getPath();
                String userId = path.substring(path.lastIndexOf('/') + 1);
                logger.debug("userId is {}", userId);
                // This will be added to the websocket session
                attributes.put("userId", userId);
                return true;
            }

            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
                // Nothing to do after handshake
            }
        };
    }
}
