package com.wedo.demo.domain.message.center.clients;

import com.wedo.demo.domain.message.Message;
import com.wedo.demo.domain.message.center.MessageClient;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;

public class UserMessageClient implements MessageClient {

    private final String myMailBox;
    private final Consumer<Message> client;

    public UserMessageClient(String myMailBox, Consumer<Message> client) {
        this.myMailBox = myMailBox;
        this.client = client;
    }

    @Override
    public boolean canHandle(String mailbox) {
        return StringUtils.equals(mailbox, myMailBox);
    }

    @Override
    public void onMessage(Message message) {
        this.client.accept(message);
    }
}
