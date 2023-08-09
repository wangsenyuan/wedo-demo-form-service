package com.wedo.demo.domain.message;

import com.wedo.demo.domain.message.ConsumerStatus;
import com.wedo.demo.domain.message.Message;

public interface MessageConsumer {
    ConsumerStatus apply(Message message);
}
