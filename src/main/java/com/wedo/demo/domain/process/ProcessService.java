package com.wedo.demo.domain.process;

import com.wedo.demo.domain.Context;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ProcessService {

    <T> T submit(Context context, String processCode, Consumer<Process.Builder> updater, Function<Process, T> fn);
}
