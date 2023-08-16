package com.wedo.demo.domain.fee;

import java.util.function.Consumer;
import java.util.function.Function;

public interface FeeService {

    <T> T factory(FeeContext context, Long id, Consumer<Fee.Builder> up, Function<Fee, T> fn);

    <T> T get(Long id, Function<Fee, T> fn);

    <T> T submit(FeeContext context, Long id, Consumer<Fee.Builder> up, Function<Fee, T> fn);
}
