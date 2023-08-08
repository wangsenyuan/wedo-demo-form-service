package com.wedo.demo.domain.message.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.wedo.demo.domain.message.Message;

import java.io.IOException;

public class MessageDeserializer extends StdDeserializer<Message> {
    public MessageDeserializer() {
        super(Message.class);
    }

    @Override
    public Message deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String sender = jp.getValueAsString("sender");
        String destination = jp.getValueAsString("destination");
        String content = jp.getValueAsString("content");
        return new MessageEntity(sender, destination, content);
    }
}
