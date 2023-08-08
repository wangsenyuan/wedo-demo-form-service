package com.wedo.demo.domain.message;

import java.util.function.Consumer;

public interface MessageCenter {

    MessageSender register(MessageClientType type, String mailbox, Consumer<Message> client);

}
