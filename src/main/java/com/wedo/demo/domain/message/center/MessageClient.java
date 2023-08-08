package com.wedo.demo.domain.message.center;

import com.wedo.demo.domain.message.Message;

public interface MessageClient {

    boolean canHandle(String mailbox);

    void onMessage(Message message);

}
