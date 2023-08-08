package com.wedo.demo.domain.message.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.wedo.demo.domain.message.Message;

import java.io.IOException;

public class MessageSerializer extends StdSerializer<Message> {

    public MessageSerializer() {
        super(Message.class);
    }

    @Override
    public void serialize(Message message, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("sender", message.getSender());
        jgen.writeStringField("destination", message.getDestination());
        jgen.writeStringField("content", message.getContent());
        jgen.writeEndObject();
    }
}
