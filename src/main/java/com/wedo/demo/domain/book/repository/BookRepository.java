package com.wedo.demo.domain.book.repository;

import com.wedo.demo.domain.book.Book;
import com.wedo.demo.domain.book.BookContext;
import com.wedo.demo.domain.book.entity.BookEntity;
import com.wedo.demo.domain.book.internal.BookImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookRepository {

    private static final String LIST_ALL = "select id, author, title from books";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookEntity> listAll() {
        return this.jdbcTemplate.query(LIST_ALL, new RowMapper<BookEntity>() {

            @Override
            public BookEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                String author = rs.getString("author");
                String title = rs.getString("title");

                BookEntity entity = new BookEntity();
                entity.setId(id);
                entity.setAuthor(author);
                entity.setTitle(title);
                return entity;
            }
        });
    }

}
