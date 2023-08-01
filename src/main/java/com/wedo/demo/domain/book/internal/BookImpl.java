package com.wedo.demo.domain.book.internal;

import com.wedo.demo.domain.book.Book;
import com.wedo.demo.domain.book.BookContext;
import com.wedo.demo.domain.book.entity.BookEntity;

public class BookImpl implements Book {

    private final BookEntity book;
    private final BookContext context;

    public BookImpl(BookEntity book, BookContext context) {
        this.book = book;
        this.context = context;
    }

    @Override
    public Long getId() {
        return book.getId();
    }

    @Override
    public String getTitle() {
        return book.getTitle();
    }

    @Override
    public String getAuthor() {
        return book.getAuthor();
    }
}
