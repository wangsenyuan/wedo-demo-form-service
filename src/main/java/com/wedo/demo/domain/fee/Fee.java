package com.wedo.demo.domain.fee;

import java.math.BigDecimal;
import java.util.Date;

public interface Fee {
    Long getId();

    String getReason();

    String getType();

    BigDecimal getAmount();

    Date getOccurredAt();

    String getDeparture();

    String getDestination();

    String getLocation();

    Long save(FeeContext context);

    interface Builder {
        Builder setReason(String reason);

        Builder setType(String type);

        Builder setAmount(BigDecimal amount);

        Builder setOccurredAt(Date occurredAt);

        Builder setDeparture(String departure);

        Builder setDestination(String destination);

        Builder setLocation(String location);
    }
}
