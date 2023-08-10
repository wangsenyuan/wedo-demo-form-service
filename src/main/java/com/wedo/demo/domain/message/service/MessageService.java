package com.wedo.demo.domain.message.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedo.demo.domain.message.Message;
import com.wedo.demo.domain.message.MessageState;
import com.wedo.demo.domain.message.entity.MessageEntity;
import com.wedo.demo.domain.message.repository.MessageRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.Set;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private static final String MESSAGE_REDIS_QUEUE_PREFIX = "message.queue.";

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public void sendMessage(Message message) {
        MessageEntity entity = (MessageEntity) message;
        entity.setCreatedAt(new Date());
        entity.setState(MessageState.QUEUED);
        entity = messageRepository.save(entity);
        logger.info("{} is saved", entity);

        this.sendMessageToReceiver(entity);
    }


    //    @TransactionalEventListener
    public void sendMessageToReceiver(Message message) {
        try {
            String json = objectMapper.writeValueAsString(message);
            redisTemplate.boundListOps(MESSAGE_REDIS_QUEUE_PREFIX + message.getReceiver()).leftPush(json);
        } catch (Exception e) {
            logger.warn("failed to send message {}", message, e);
        }
    }


    public Message receiveMessage(Set<String> ques) {
        for (String que : ques) {
            MessageEntity message = pullMessageFromQueue(MESSAGE_REDIS_QUEUE_PREFIX + que);
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

    private MessageEntity pullMessageFromQueue(String queue) {

        try {
            String json = redisTemplate.boundListOps(queue).rightPop(Duration.ofMillis(100));
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return objectMapper.readValue(json, MessageEntity.class);
        } catch (Exception e) {
            logger.warn("failed to pull message", e);
            return null;
        }
    }

}
