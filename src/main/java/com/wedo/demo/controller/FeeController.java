package com.wedo.demo.controller;

import com.wedo.demo.domain.fee.entity.FeeEntity;
import com.wedo.demo.domain.fee.service.FeeService;
import com.wedo.demo.dto.common.R;
import com.wedo.demo.util.AccessTokenUtil;
import demo.WorkflowDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fee")
public class FeeController {
    @Autowired
    private FeeService feeService;

    @PostMapping("/save")
    public R<Long> save(@RequestBody FeeEntity fee) {
        Long id = feeService.save(fee);
        return R.success(id);
    }

    @PostMapping("/submit")
    public R<String> submit(@RequestBody FeeEntity fee) {
        String accessToken = AccessTokenUtil.getToken();

        String result = WorkflowDemo.run(accessToken, fee);

        return R.success(result);
    }
}
