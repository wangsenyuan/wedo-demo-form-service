package com.wedo.demo.controller;

import com.wedo.demo.domain.followup.entity.CustomerFollowUpEntity;
import com.wedo.demo.domain.followup.service.FollowUpService;
import com.wedo.demo.dto.common.R;
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
    private FollowUpService followUpService;

    @PostMapping("/save")
    public R<Long> save(@RequestBody CustomerFollowUpEntity entity) {
        logger.debug("follow-up save {}", entity);
        Long id = followUpService.save(entity);
        return R.success(id);
    }
}
