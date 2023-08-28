package com.wedo.demo.domain.message;

public interface MessageSender {

    void send(String receiver, String sessionId, String message);

    void stop();
}
