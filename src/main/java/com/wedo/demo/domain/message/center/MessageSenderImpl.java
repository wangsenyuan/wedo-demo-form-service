package com.wedo.demo.domain.message.center;

import com.wedo.demo.domain.message.Message;
import com.wedo.demo.domain.message.MessageSender;
import com.wedo.demo.domain.message.entity.MessageEntity;

import java.util.function.Consumer;

class MessageSenderImpl implements MessageSender {
    private final String sender;

    private final Consumer<Message> messageConsumer;

    MessageSenderImpl(String sender, Consumer<Message> messageConsumer) {
        this.sender = sender;
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void send(String destination, String message) {
        Message msg = new MessageEntity(sender, destination, message);
        this.messageConsumer.accept(msg);
    }
}
