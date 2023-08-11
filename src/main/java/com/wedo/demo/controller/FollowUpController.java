package com.wedo.demo.controller;

import com.wedo.demo.domain.followup.CustomerFollowUp;
import com.wedo.demo.domain.followup.CustomerFollowUpContext;
import com.wedo.demo.domain.followup.service.CustomerFollowUpServiceImpl;
import com.wedo.demo.dto.common.R;
import com.wedo.demo.dto.followup.CustomerFollowUpDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow-up")
public class FollowUpController {
    private static final Logger logger = LoggerFactory.getLogger(FollowUpController.class);

    @Autowired
    private CustomerFollowUpServiceImpl followUpService;

    @PostMapping("/save")
    public R<Long> save(@RequestBody CustomerFollowUpDto dto) {
        Long id = followUpService.factory(CustomerFollowUpContext.current(), dto.getId(), builder -> {

            builder.audioUrl(dto.getAudioUrl());
            builder.note(dto.getNote());
            builder.customer(dto.getCustomer());
            builder.occurredAt(dto.getOccurredAt());
            builder.operator(dto.getOperator());

        }, CustomerFollowUp::getId);
        return R.success(id);
    }
}
