package com.wedo.demo.controller;

import com.wedo.demo.domain.attendance.entity.AttendanceEntity;
import com.wedo.demo.domain.attendance.service.AttendanceService;
import com.wedo.demo.dto.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/save")
    public R<Long> save(@RequestBody AttendanceEntity entity) {
        Long id = attendanceService.save(entity);
        return R.success(id);
    }
}
