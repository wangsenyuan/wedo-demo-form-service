package com.wedo.demo.domain.attendance;

import com.wedo.demo.domain.Context;
import com.wedo.demo.domain.attendance.impl.AttendanceContextImpl;

public interface AttendanceContext extends Context {

    static AttendanceContext current() {
        return new AttendanceContextImpl();
    }
}
