package com.wedo.demo.domain.fee.service;

import com.wedo.demo.domain.fee.Fee;
import com.wedo.demo.domain.fee.FeeContext;
import com.wedo.demo.domain.fee.FeeService;
import com.wedo.demo.domain.fee.entity.FeeEntity;
import com.wedo.demo.domain.fee.impl.FeeBuilderImpl;
import com.wedo.demo.domain.fee.impl.FeeImpl;
import com.wedo.demo.domain.fee.impl.FeeWorkUnit;
import com.wedo.demo.domain.fee.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class FeeServiceImpl implements FeeService {

    @Autowired
    private FeeRepository feeRepository;
    @Autowired
    private FeeWorkUnit workUnit;

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
}
