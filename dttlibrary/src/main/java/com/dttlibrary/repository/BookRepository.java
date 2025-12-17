package com.dttlibrary.repository;

import com.dttlibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b LEFT JOIN b.bookItems bi WHERE bi.status = 'available'")
    List<Book> findAllWithAvailableItems();
}
