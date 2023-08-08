package com.wedo.demo.domain.message;

public interface MessageSender {

    void send(String destination, String message);

}
