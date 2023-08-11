package com.wedo.demo.domain.attendance;

import java.util.function.Consumer;
import java.util.function.Function;

public interface AttendanceService {

    <T> T factory(AttendanceContext context, Long id, Consumer<Attendance.Builder> updater, Function<Attendance, T> fn);
}
