package com.wedo.demo.domain.chat.robot;

import com.google.gson.Gson;
import com.wedo.demo.domain.chat.dto.ChatMessage;
import com.wedo.demo.domain.chat.robot.adapter.ChatApi;
import com.wedo.demo.domain.message.ConsumerStatus;
import com.wedo.demo.domain.message.Message;
import com.wedo.demo.domain.message.MessageCenter;
import com.wedo.demo.domain.message.MessageSender;
import com.wedo.demo.dto.common.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import javax.annotation.PostConstruct;

@Service
public class ChatRobot {

    private static final Logger logger = LoggerFactory.getLogger(ChatRobot.class);

    private static final String ROBOT_ID = "ai.chat.robot";

    @Autowired
    private MessageCenter messageCenter;

    @Autowired
    private TaskExecutor executor;

    @Autowired
    private ChatApi chatApi;
    private MessageSender messageSender;

    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        // will be used to reply
        this.messageSender = messageCenter.register(ROBOT_ID, this::handleMessage);
    }

    private ConsumerStatus handleMessage(Message message) {
        // here will send the message to backend ai service
        ChatMessage dto = gson.fromJson(message.getBody(), ChatMessage.class);
        // add sender
        dto.setSender(message.getSender());

        executor.execute(() -> {
            try {
                ChatMessage res = askAi(dto);

                replyMessage(message.getSender(), message.getSessionId(), res);
            } catch (Exception ex) {
                logger.warn("exception happend for message {}", message.getId(), ex);
            }
        });

        // always standby, may stop when stopping?
        return ConsumerStatus.CONTINUE;
    }

    public ChatMessage askAi(ChatMessage dto) {
        logger.debug("ask ai {}", dto.getId());
        Call<R<ChatMessage>> call = chatApi.chat(dto);
        R<ChatMessage> res = execute(call);
        if (res == null) {
            return null;
        }
        logger.debug("get result {}", dto.getId());
        return res.getData();
    }

    private static <T> R<T> execute(Call<R<T>> call) {
        try {
            Response<R<T>> response = call.execute();
            logger.debug("get response {}", response.message());
            return response.body();
        } catch (Exception ex) {
            logger.warn("failed make call", ex);
            // ingore
            return null;
        }
    }

    private void replyMessage(String receiver, String sessionId, ChatMessage dto) {
        String body = gson.toJson(dto);
        messageSender.send(receiver, sessionId, body);
    }
}
