package com.wedo.demo.domain.message.service;

import com.wedo.demo.domain.message.Message;
import com.wedo.demo.domain.message.MessageState;
import com.wedo.demo.domain.message.entity.MessageEntity;
import com.wedo.demo.domain.message.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageStore messageStore;

    @Transactional
    public void sendMessage(Message message) {
        MessageEntity entity = (MessageEntity) message;
        entity.setCreatedAt(new Date());
        entity.setState(MessageState.QUEUED);
        entity = messageRepository.save(entity);
        logger.info("{} is saved", entity);

        messageStore.sendMessageToReceiver(entity);
    }


    public Message receiveMessage(Set<String> ques) {
        for (String que : ques) {
            Message message = messageStore.pullMessageFromQueue(que);
            if (message != null) {
                return message;
            }
        }
        return null;

    }

    @Transactional
    public void updateMessageState(Message message, MessageState state) {
        this.messageRepository.updateState(message.getId(), state);
    }

}
