package com.wedo.demo.dto.fee;

import com.wedo.demo.domain.fee.Fee;

import java.math.BigDecimal;
import java.util.Date;

public class FeeDto {

    private Long id;

    private String reason;

    private String type;

    private BigDecimal amount;

    private Date occurredAt;

    private String departure;

    private String destination;

    private String location;

    public FeeDto() {

    }

    public FeeDto(Fee fee) {
        this.id = fee.getId();
        this.reason = fee.getReason();
        this.amount = fee.getAmount();
        this.type = fee.getType();
        this.location = fee.getLocation();
        this.departure = fee.getDeparture();
        this.destination = fee.getDestination();
        this.occurredAt = fee.getOccurredAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Date occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}