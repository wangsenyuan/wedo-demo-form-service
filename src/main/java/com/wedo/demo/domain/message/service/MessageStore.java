package com.wedo.demo.domain.message.service;

import com.wedo.demo.domain.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class MessageStore {
    private static final Logger logger = LoggerFactory.getLogger(MessageStore.class);
    private ConcurrentMap<String, LinkedBlockingQueue<Message>> queues = new ConcurrentHashMap<>();

    @TransactionalEventListener
    public void sendMessageToReceiver(Message message) {
        this.queues.compute(message.getReceiver(), (k, que) -> {
            if (que == null) {
                que = new LinkedBlockingQueue<>();
            }
            try {
                que.put(message);
            } catch (Exception ex) {
                logger.warn("failed to send message {}", message, ex);
            }
            return que;
        });
    }

    public Message pullMessageFromQueue(String queue) {
        if (this.queues.containsKey(queue)) {
            try {
                return this.queues.get(queue).poll(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                return null;
            }
        }
        return null;
    }

}
