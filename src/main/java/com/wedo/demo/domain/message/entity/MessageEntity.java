package com.wedo.demo.domain.message.entity;

import com.google.common.base.MoreObjects;
import com.wedo.demo.domain.message.Message;
import com.wedo.demo.domain.message.MessageState;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "messages")
public class MessageEntity implements Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    private String sender;
    private String receiver;

    private String body;

    @Enumerated(EnumType.STRING)
    private MessageState state;

    private Date createdAt;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public MessageState getState() {
        return state;
    }

    public void setState(MessageState state) {
        this.state = state;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("sender", sender).add("receiver", receiver).add("body", body).add("state", state).add("createdAt", createdAt).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity entity = (MessageEntity) o;
        return Objects.equals(id, entity.id) && Objects.equals(sender, entity.sender) && Objects.equals(receiver, entity.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, receiver);
    }
}
