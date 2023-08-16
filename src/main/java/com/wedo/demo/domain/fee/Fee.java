package com.wedo.demo.domain.fee;

import java.math.BigDecimal;
import java.util.Date;

public interface Fee {
    Long getId();

    String getReason();

    String getType();

    String getTypeName();

    BigDecimal getAmount();

    Date getOccurredAt();

    String getDeparture();

    String getDestination();

    String getLocation();

    Long save(FeeContext context);

    void updateProcess(FeeContext context, String processKey);

    interface Builder {
        Builder setReason(String reason);

        Builder setType(String type);

        Builder setAmount(BigDecimal amount);

        Builder setOccurredAt(Date occurredAt);

        Builder setDeparture(String departure);

        Builder setDestination(String destination);

        Builder setLocation(String location);

        Builder setTypeName(String typeName);
    }
}
