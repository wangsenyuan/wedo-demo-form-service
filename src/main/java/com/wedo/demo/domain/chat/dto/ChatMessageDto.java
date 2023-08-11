package com.wedo.demo.domain.chat.dto;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.Map;

public class ChatMessageDto {
    private String id;

    private String receiver;

    private String sender;

    private Date createdAt;

    private String replyToId;
    private ChatMessageType msgType = ChatMessageType.TEXT;

    private Map<String, String> payload;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReplyToId() {
        return replyToId;
    }

    public void setReplyToId(String replyToId) {
        this.replyToId = replyToId;
    }

    public ChatMessageType getMsgType() {
        return msgType;
    }

    public void setMsgType(ChatMessageType msgType) {
        this.msgType = msgType;
    }

    public Map<String, String> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, String> payload) {
        this.payload = payload;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("receiver", receiver).add("sender", sender).add("createdAt", createdAt).add("replyToId", replyToId).add("msgType", msgType).add("payload", payload).toString();
    }
}
