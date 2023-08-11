package com.wedo.demo.controller;

import com.wedo.demo.domain.chat.dto.ChatMessage;
import com.wedo.demo.domain.chat.dto.ChatMessageType;
import com.wedo.demo.domain.chat.robot.ChatRobot;
import com.wedo.demo.dto.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ChatRobot chatRobot;

    @GetMapping("/current")
    public R<Long> current() {
        return R.success(System.nanoTime());
    }

    @GetMapping("/redis-test")
    public R<String> redisTest() {
        redisTemplate.boundValueOps("demo-test").set("hello", Duration.ofSeconds(1));

        String res = redisTemplate.boundValueOps("demo-test").get();

        return R.success(res);
    }

    @GetMapping("/ai-test")
    public R<ChatMessage> aiTest() {
        ChatMessage dto = new ChatMessage();
        dto.setSender("xxx");
        dto.setId("1");
        dto.setReceiver("ai.chat.bot");
        dto.setMsgType(ChatMessageType.TEXT);
        Map<String, String> payload = new HashMap<>();
        payload.put("value", "hello");
        dto.setPayload(payload);
        dto = chatRobot.askAi(dto);

        return R.success(dto);
    }
}
