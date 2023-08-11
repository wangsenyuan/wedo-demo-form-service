package com.wedo.demo.domain.followup;

import java.util.function.Consumer;
import java.util.function.Function;

public interface CustomerFollowUpService {

    <T> T factory(CustomerFollowUpContext context, Long id, Consumer<CustomerFollowUp.Builder> updater, Function<CustomerFollowUp, T> fn);
}
