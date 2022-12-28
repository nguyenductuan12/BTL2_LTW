package com.example.managelibrary.service;

import com.example.managelibrary.entity.Book;
import com.example.managelibrary.entity.User;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();//tạo ra 1 interface lấy 1 list danh sách student

    Book saveBook(Book book);

    Book getBookById(Long id);

    Book updateBook(Book student);

    void deleteBookById(Long id);
    
    List<Book> getPurchasedbooks(User user);
}
