package com.wedo.demo.config.ws;

import org.springframework.web.socket.WebSocketHandler;

public interface WebSocketRequestHandler extends WebSocketHandler {
    String getPath();

    String getAllowOrigins();

}
