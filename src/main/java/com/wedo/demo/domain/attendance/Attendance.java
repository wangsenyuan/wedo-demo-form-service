package com.wedo.demo.domain.attendance;

import java.util.Date;

public interface Attendance {
    Long getId();

    Date getOccurredAt();

    String getCustomer();

    String getOperator();

    String getLocation();

    String getImageUrl();

    Long save(AttendanceContext context);

    interface Builder {
        Builder setOccurredAt(Date occurredAt);

        Builder setCustomer(String customer);

        Builder setLocation(String location);

        Builder setImageUrl(String imageUrl);

        Builder setOperator(String operator);
    }
}
