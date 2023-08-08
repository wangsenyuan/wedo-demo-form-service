package com.wedo.demo.domain.message.center;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wedo.demo.domain.message.Message;
import com.wedo.demo.domain.message.MessageCenter;
import com.wedo.demo.domain.message.MessageClientType;
import com.wedo.demo.domain.message.MessageSender;
import com.wedo.demo.domain.message.center.clients.RobotMessageClient;
import com.wedo.demo.domain.message.center.clients.UserMessageClient;
import com.wedo.demo.domain.message.entity.MessageDeserializer;
import com.wedo.demo.domain.message.entity.MessageSerializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

@Service
public class MessageCenterImpl implements MessageCenter {

    private static final String MESSAGE_REDIS_QUEUE = "message.queue";

    private static final Logger logger = LoggerFactory.getLogger(MessageCenter.class);
    private ConcurrentMap<String, MessageClient> clients = new ConcurrentHashMap<>();

    @Value("${wedo.demo.message.loop:false}")
    private boolean runLoop;

    private volatile boolean running;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper;


    @PostConstruct
    public void init() {
        prepareJsonMapper();

        if (!runLoop) {
            return;
        }
        running = true;
        taskExecutor.execute(this::messageLoop);
        logger.info("message center initialized");
    }

    private void prepareJsonMapper() {
        objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(Message.class, new MessageSerializer());
        module.addDeserializer(Message.class, new MessageDeserializer());

        objectMapper.registerModule(module);
    }

    @Override
    public MessageSender register(MessageClientType clientType, String mailbox, Consumer<Message> messageConsumer) {
        if (!runLoop) {
            logger.warn("message loop is not started yet, it may not process any message correctly");
        }
        // mailbox is the sender for message
        MessageClient client = null;
        if (clientType == MessageClientType.USER) {
            client = new UserMessageClient(mailbox, messageConsumer);
        } else {
            // robot here don't expect reply
            client = new RobotMessageClient(mailbox, messageConsumer);
        }

        clients.put(mailbox, client);
        return new MessageSenderImpl(mailbox, this::doSendMessage);
    }

    private void doSendMessage(Message message) {
        // just put it into queue
        try {
            String json = objectMapper.writeValueAsString(message);
            redisTemplate.boundListOps(MESSAGE_REDIS_QUEUE).leftPush(json);
            redisTemplate.expire(MESSAGE_REDIS_QUEUE, Duration.ofMinutes(1));
        } catch (Exception e) {
            logger.warn("failed to send message {}", message, e);
        }
    }

    private Message pullMessage() {

        try {
            String json = redisTemplate.boundListOps(MESSAGE_REDIS_QUEUE).rightPop(Duration.ofSeconds(1));
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return objectMapper.readValue(json, Message.class);
        } catch (Exception e) {
            logger.warn("failed to pull message", e);
            return null;
        }
    }

    private static int[] NULL_LOOP_LIMITS = {1000, 100, 10, 1};
    private static long[] NULL_LOOP_SLEEP_SECONDS = {5, 2, 1, 0};

    private void messageLoop() {
        int nullLoopTimes = 0;
        while (running) {
            Message message = pullMessage();
            if (message == null) {
                nullLoopTimes++;
                int i = 0;
                while (i < NULL_LOOP_LIMITS.length && nullLoopTimes < NULL_LOOP_LIMITS[i]) {
                    i++;
                }
                sleepThreads(NULL_LOOP_SLEEP_SECONDS[i]);
                if (i == 0) {
                    logger.debug("total {} null messages got", nullLoopTimes);
                    nullLoopTimes /= 10;
                }
            } else {
                nullLoopTimes = 0;
                handleMessage(message);
            }
        }
    }

    private void handleMessage(Message message) {
        MessageClient client = clients.get(message.getDestination());
        if (client == null) {
            for (MessageClient clt : clients.values()) {
                if (clt.canHandle(message.getDestination())) {
                    client = clt;
                    break;
                }
            }
        }
        if (client == null) {
            logger.warn("no message client found to process message {}", message.getDestination());
            return;
        }
        client.onMessage(message);
    }

    private void sleepThreads(long sleepSeconds) {
        try {
            Thread.sleep(sleepSeconds * 1000);
        } catch (InterruptedException e) {
            logger.warn("thread sleeping interrupted", e);
            // ignore
        }
    }
}
