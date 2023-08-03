package com.wedo.demo.domain.process;

import java.util.function.Consumer;

public interface ProcessService {

    String submit(Consumer<ProcessBuilder> taskBuilder);

}
