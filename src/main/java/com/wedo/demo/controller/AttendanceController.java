package com.wedo.demo.controller;

import com.wedo.demo.domain.attendance.AttendanceContext;
import com.wedo.demo.domain.attendance.service.AttendanceServiceImpl;
import com.wedo.demo.dto.attendance.AttendanceDto;
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
    private AttendanceServiceImpl attendanceService;

    @PostMapping("/save")
    public R<AttendanceDto> save(@RequestBody AttendanceDto dto) {
        AttendanceDto res = attendanceService.factory(AttendanceContext.current(), dto.getId(), builder -> {
            builder.setCustomer(dto.getCustomer());
            builder.setLocation(dto.getLocation());
            builder.setImageUrl(dto.getImageUrl());
            builder.setOccurredAt(dto.getOccurredAt());
            builder.setOperator(dto.getOperator());
        }, AttendanceDto::new);

        return R.success(res);
    }
}
