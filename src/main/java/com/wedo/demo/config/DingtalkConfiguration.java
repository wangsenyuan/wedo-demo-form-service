package com.wedo.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "wedo.demo.ding-talk")
public class DingtalkConfiguration {

    private String appKey;
    private String appSecret;

    private String mockUser;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMockUser() {
        return mockUser;
    }

    public void setMockUser(String mockUser) {
        this.mockUser = mockUser;
    }
}
