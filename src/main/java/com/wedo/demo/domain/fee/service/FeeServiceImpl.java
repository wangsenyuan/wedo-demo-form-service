package com.wedo.demo.domain.fee.service;

import com.wedo.demo.domain.Context;
import com.wedo.demo.domain.fee.Fee;
import com.wedo.demo.domain.fee.FeeContext;
import com.wedo.demo.domain.fee.FeeService;
import com.wedo.demo.domain.fee.entity.FeeEntity;
import com.wedo.demo.domain.fee.impl.FeeBuilderImpl;
import com.wedo.demo.domain.fee.impl.FeeImpl;
import com.wedo.demo.domain.fee.impl.FeeWorkUnit;
import com.wedo.demo.domain.fee.repository.FeeRepository;
import com.wedo.demo.domain.process.Process;
import com.wedo.demo.domain.process.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class FeeServiceImpl implements FeeService {
    private static final String PROCESS_CODE = "PROC-D5D5D54C-C651-4060-A56D-A76395A27FDC";

    @Autowired
    private FeeRepository feeRepository;
    @Autowired
    private FeeWorkUnit workUnit;

    @Autowired
    private ProcessService processService;

    @Override
    @Transactional
    public <T> T factory(FeeContext context, Long id, Consumer<Fee.Builder> up, Function<Fee, T> fn) {
        FeeEntity entity = new FeeEntity();

        if (id != null) {
            entity = feeRepository.getReferenceById(id);
        }

        Fee.Builder builder = new FeeBuilderImpl(entity);

        up.accept(builder);

        entity = feeRepository.saveAndFlush(entity);

        Fee fee = new FeeImpl(entity, workUnit);

        fee.save(context);

        return fn.apply(fee);
    }

    @Override
    @Transactional
    public <T> T get(Long id, Function<Fee, T> fn) {
        FeeEntity entity = feeRepository.getReferenceById(id);
        Fee fee = new FeeImpl(entity, workUnit);
        return fn.apply(fee);
    }

    @Override
    @Transactional
    public <T> T submit(FeeContext context, Long id, Consumer<Fee.Builder> up, Function<Fee, T> fn) {
        Fee fee = factory(context, id, up, Function.identity());
        processService.submit(new Context() {
            @Override
            public String getOperator() {
                return context.getOperator();
            }
        }, PROCESS_CODE, builder -> {
            addFieldValues(builder, fee);
        }, process -> {
            updateProcess(context, process, fee);
            return process.getKey();
        });

        return fn.apply(fee);
    }

    private void updateProcess(FeeContext context, Process process, Fee fee) {
        fee.updateProcess(context, process.getKey());
    }

    private static void addFieldValues(Process.Builder builder, Fee fee) {
        builder.addField("金额", "" + fee.getAmount());
        builder.addField("事由", fee.getReason());
        builder.addField("类型", fee.getTypeName());
        builder.addField("出发地", fee.getDeparture());
        builder.addField("目的地", fee.getDestination());
    }
}
