package com.wedo.demo.controller;

import com.wedo.demo.dto.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private StringRedisTemplate redisTemplate;

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
}
