package com.wedo.demo.domain.followup;

import com.wedo.demo.domain.Context;
import com.wedo.demo.domain.followup.impl.CustomerFollowUpContextImpl;

public interface CustomerFollowUpContext extends Context {

    static CustomerFollowUpContext current() {
        return new CustomerFollowUpContextImpl();
    }
}
