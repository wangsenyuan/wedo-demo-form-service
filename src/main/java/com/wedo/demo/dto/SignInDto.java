package com.wedo.demo.dto;

import java.util.Date;

public class SignInDto {
    /**
     * 打卡时间
     */
    private Date occurredAt;
    /**
     * 客户
     */
    private String client;
    /**
     * 详细地址
     */
    private String location;

    /**
     * 拍照
     */
    private String imageUrl;

    public Date getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Date occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
