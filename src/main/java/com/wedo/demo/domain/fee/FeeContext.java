package com.wedo.demo.domain.fee;

import com.wedo.demo.domain.fee.impl.FeeContextImpl;

public interface FeeContext {
    String getOperator();

    static FeeContext current() {
        return new FeeContextImpl();
    }
}
