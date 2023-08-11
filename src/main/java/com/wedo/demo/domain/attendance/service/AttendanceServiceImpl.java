package com.wedo.demo.domain.attendance.service;

import com.wedo.demo.domain.attendance.Attendance;
import com.wedo.demo.domain.attendance.AttendanceContext;
import com.wedo.demo.domain.attendance.AttendanceService;
import com.wedo.demo.domain.attendance.entity.AttendanceEntity;
import com.wedo.demo.domain.attendance.impl.AttendanceBuilder;
import com.wedo.demo.domain.attendance.impl.AttendanceImpl;
import com.wedo.demo.domain.attendance.impl.AttendanceWorkUnit;
import com.wedo.demo.domain.attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceWorkUnit workUnit;

    @Override
    @Transactional
    public <T> T factory(AttendanceContext context, Long id, Consumer<Attendance.Builder> updater, Function<Attendance, T> fn) {
        AttendanceEntity entity = new AttendanceEntity();

        if (id != null) {
            var opt = attendanceRepository.findById(id);
            if (opt.isPresent()) {
                entity = opt.get();
            }
        }

        Attendance.Builder builder = new AttendanceBuilder(entity);

        updater.accept(builder);

        Attendance attendance = new AttendanceImpl(entity, workUnit);

        attendance.save(context);

        return fn.apply(attendance);
    }
}
