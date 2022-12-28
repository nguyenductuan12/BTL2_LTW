package com.example.managelibrary.reponsitory;

import com.example.managelibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReponsitory extends JpaRepository<Book, Long> {
}
