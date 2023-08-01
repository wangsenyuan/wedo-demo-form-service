package com.wedo.demo.controller;

import com.wedo.demo.domain.book.BookService;
import com.wedo.demo.dto.book.BookDto;
import com.wedo.demo.dto.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public R<List<BookDto>> listAll() {
        List<BookDto> dtos = bookService.listAll(BookDto::new);

        return R.success(dtos);
    }
}
