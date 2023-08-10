package com.wedo.demo.domain.message.center;

import com.google.common.collect.Sets;
import com.wedo.demo.domain.message.*;
import com.wedo.demo.domain.message.sender.MessageSenderImpl;
import com.wedo.demo.domain.message.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class MessageCenterImpl implements MessageCenter {
    private static final Logger logger = LoggerFactory.getLogger(MessageCenter.class);
    private ConcurrentMap<String, MessageConsumer> clients = new ConcurrentHashMap<>();

    @Value("${wedo.demo.message.loop:false}")
    private boolean runLoop;

    private volatile boolean running;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private MessageService messageService;

    @PostConstruct
    public void init() {

        if (!runLoop) {
            return;
        }
        running = true;
        taskExecutor.execute(this::messageLoop);
        logger.info("message center initialized");
    }

    @Override
    public MessageSender register(String mailbox, MessageConsumer messageConsumer) {
        if (!runLoop) {
            logger.warn("message loop is not started yet, it may not process any message correctly");
        }
        clients.put(mailbox, messageConsumer);
        return new MessageSenderImpl(mailbox, messageService::sendMessage, () -> {
            clients.remove(mailbox);
        });
    }

    private static int[] NULL_LOOP_LIMITS = {1000, 100, 10, 1};
    private static long[] NULL_LOOP_SLEEP_SECONDS = {5, 2, 1, 0};

    private void messageLoop() {
        int nullLoopTimes = 0;
        long lastRunTime = System.nanoTime();
        while (running) {
            try {
                Set<String> queues = Sets.newHashSet(this.clients.keySet());
                Message message = messageService.receiveMessage(queues);
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

                if (System.nanoTime() - lastRunTime > 10_000_000) {
                    logger.debug("message loop is active");
                }

                lastRunTime = System.nanoTime();
            } catch (Exception ex) {
                logger.error("message loop exception", ex);
            }
        }
    }

    private void handleMessage(Message message) {
        MessageConsumer res = clients.computeIfPresent(message.getReceiver(), (k, consumer) -> {
            ConsumerStatus status = consumer.apply(message);
            messageService.updateMessageState(message, MessageState.DELIVERED);
            if (status == ConsumerStatus.STOP) {
                return null;
            }
            return consumer;
        });

        if (res == null) {
            logger.info("message consumer for {} stopped.", message.getReceiver());
        }
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
