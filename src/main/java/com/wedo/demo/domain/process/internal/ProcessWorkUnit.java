package com.wedo.demo.domain.process.internal;

import com.wedo.demo.domain.process.ProcessState;
import com.wedo.demo.domain.process.adapter.DingtalkProcessServiceAdapter;
import com.wedo.demo.domain.process.entity.ProcessInstanceEntity;
import com.wedo.demo.domain.process.repository.ProcessInstanceRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessWorkUnit {
    @Autowired
    private ProcessInstanceRepository repository;

    @Autowired
    private DingtalkProcessServiceAdapter dingtalkProcessServiceAdapter;

    public String submit(ProcessInstanceEntity entity) {
        repository.save(entity);

        if (StringUtils.isNotEmpty(entity.getProcessKey())) {
            dingtalkProcessServiceAdapter.terminateIfAny(entity.getProcessKey());
        }

        String key = dingtalkProcessServiceAdapter.submit(entity);

        entity.setProcessKey(key);
        entity.setState(ProcessState.SUBMITTED);

        repository.save(entity);

        return key;
    }
}
