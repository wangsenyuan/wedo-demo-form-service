package com.wedo.demo.domain.message.entity;

import com.wedo.demo.domain.message.Message;

public class MessageEntity implements Message {
    private final String sender;

    private final String destination;
    private final String content;

    public MessageEntity(String sender, String destination, String content) {
        this.sender = sender;
        this.destination = destination;
        this.content = content;
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getDestination() {
        return destination;
    }
}
