package com.wedo.demo.domain.followup.service;

import com.wedo.demo.domain.followup.CustomerFollowUp;
import com.wedo.demo.domain.followup.CustomerFollowUpContext;
import com.wedo.demo.domain.followup.CustomerFollowUpService;
import com.wedo.demo.domain.followup.entity.CustomerFollowUpEntity;
import com.wedo.demo.domain.followup.impl.CustomerFollowUpBuilder;
import com.wedo.demo.domain.followup.impl.CustomerFollowUpImpl;
import com.wedo.demo.domain.followup.impl.CustomerFollowUpWorkUnit;
import com.wedo.demo.domain.followup.repository.CustomerFollowUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class CustomerFollowUpServiceImpl implements CustomerFollowUpService {
    @Autowired
    private CustomerFollowUpRepository repository;
    @Autowired
    private CustomerFollowUpWorkUnit workUnit;

    @Override
    @Transactional
    public <T> T factory(CustomerFollowUpContext context, Long id, Consumer<CustomerFollowUp.Builder> updater, Function<CustomerFollowUp, T> fn) {
        CustomerFollowUpEntity entity = new CustomerFollowUpEntity();

        if (id != null) {
            var opt = repository.findById(id);
            if (opt.isPresent()) {
                entity = opt.get();
            }
        }
        CustomerFollowUp.Builder builder = new CustomerFollowUpBuilder(entity);

        updater.accept(builder);

        CustomerFollowUp followUp = new CustomerFollowUpImpl(entity, workUnit);

        followUp.save(context);

        return fn.apply(followUp);
    }
}
