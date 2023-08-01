package com.wedo.demo.domain.book;

import java.util.List;
import java.util.function.Function;

public interface BookService {

    <T> List<T> listAll(Function<Book, T> mapper);
}
