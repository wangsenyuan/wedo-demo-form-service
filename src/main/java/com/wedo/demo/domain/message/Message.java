package com.wedo.demo.domain.message;

import java.util.Date;

public interface Message {

    Long getId();

    String getSender();

    String getReceiver();

    String getBody();

    MessageState getState();

    Date getCreatedAt();

}
