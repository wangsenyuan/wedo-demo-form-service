package com.wedo.demo.dto.book;

import com.wedo.demo.domain.book.Book;

public class BookDto {
    private Long id;
    private String title;

    private String author;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookDto() {

    }

    public BookDto(Book book) {
        this.id = book.getId();
        this.author = book.getAuthor();
        this.title = book.getTitle();
    }
}
