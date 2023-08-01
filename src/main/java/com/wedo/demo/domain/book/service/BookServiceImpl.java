package com.wedo.demo.domain.book.service;

import com.wedo.demo.domain.book.Book;
import com.wedo.demo.domain.book.BookContext;
import com.wedo.demo.domain.book.BookService;
import com.wedo.demo.domain.book.context.BookContextImpl;
import com.wedo.demo.domain.book.entity.BookEntity;
import com.wedo.demo.domain.book.internal.BookImpl;
import com.wedo.demo.domain.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private BookContext createContext() {
        return new BookContextImpl();
    }


    @Override
    @Transactional(readOnly = true)
    public <T> List<T> listAll(Function<Book, T> mapper) {
        List<BookEntity> entities = bookRepository.listAll();
        BookContext context = createContext();

        List<Book> books = entities.stream().map(entity -> new BookImpl(entity, context)).collect(Collectors.toList());

        return books.stream().map(mapper).collect(Collectors.toList());
    }

}
