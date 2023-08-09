package com.wedo.demo.domain.message;

public interface MessageCenter {

    MessageSender register(String mailbox, MessageConsumer consumer);

}
