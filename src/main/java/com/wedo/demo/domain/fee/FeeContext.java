package com.wedo.demo.domain.fee;

import com.wedo.demo.domain.Context;
import com.wedo.demo.domain.fee.impl.FeeContextImpl;

public interface FeeContext extends Context {

    static FeeContext current() {
        return new FeeContextImpl();
    }
}
