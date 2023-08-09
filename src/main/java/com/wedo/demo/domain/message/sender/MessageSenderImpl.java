package com.wedo.demo.domain.message.sender;

import com.wedo.demo.domain.message.Message;
import com.wedo.demo.domain.message.MessageSender;
import com.wedo.demo.domain.message.entity.MessageEntity;

import java.util.function.Consumer;

public class MessageSenderImpl implements MessageSender {
    private final String sender;

    private final Consumer<Message> messageConsumer;

    public MessageSenderImpl(String sender, Consumer<Message> messageConsumer) {
        this.sender = sender;
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void send(String receiver, String message) {
        MessageEntity entity = new MessageEntity();
        entity.setBody(message);
        entity.setSender(sender);
        entity.setReceiver(receiver);
        this.messageConsumer.accept(entity);
    }
}
