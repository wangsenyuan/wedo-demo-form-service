package com.wedo.demo.dto.attendance;

import com.wedo.demo.domain.attendance.Attendance;

import java.util.Date;

public class AttendanceDto {
    private Long id;
    private Date occurredAt;
    private String customer;

    private String operator;

    private String location;

    private String imageUrl;

    private String formType = "ATTENDANCE";

    public AttendanceDto() {

    }

    public AttendanceDto(Attendance domain) {
        this.id = domain.getId();
        this.occurredAt = domain.getOccurredAt();
        this.customer = domain.getCustomer();
        this.operator = domain.getOperator();
        this.location = domain.getLocation();
        this.imageUrl = domain.getImageUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Date occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }
}
