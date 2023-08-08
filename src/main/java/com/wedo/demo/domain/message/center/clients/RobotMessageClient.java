package com.wedo.demo.domain.message.center.clients;

import com.wedo.demo.domain.message.Message;
import com.wedo.demo.domain.message.center.MessageClient;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;

public class RobotMessageClient implements MessageClient {
    private final String mailBox;
    private final Consumer<Message> messageConsumer;

    public RobotMessageClient(String mailBox, Consumer<Message> messageConsumer) {
        this.mailBox = mailBox;
        this.messageConsumer = messageConsumer;
    }

    @Override
    public boolean canHandle(String mailbox) {
        return StringUtils.startsWith(mailbox, this.mailBox);
    }

    @Override
    public void onMessage(Message message) {
        // here must do more things
        messageConsumer.accept(message);
    }
}
