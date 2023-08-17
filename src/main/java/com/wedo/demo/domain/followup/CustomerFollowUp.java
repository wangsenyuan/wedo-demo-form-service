package com.wedo.demo.domain.followup;

import java.util.Date;

public interface CustomerFollowUp {
    Long getId();

    String getCustomer();

    String getOperator();

    Date getOccurredAt();

    String getNote();

    String getAudioUrl();

    Long save(CustomerFollowUpContext context);

    interface Builder {
        Builder customer(String customer);

        Builder operator(String operator);

        Builder note(String note);

        Builder audioUrl(String audioUrl);
    }

}
