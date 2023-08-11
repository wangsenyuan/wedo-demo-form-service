package com.wedo.demo.domain.attendance.repository;

import com.wedo.demo.domain.attendance.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
}
