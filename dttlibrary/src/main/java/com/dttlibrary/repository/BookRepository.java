package com.dttlibrary.repository;

import com.dttlibrary.model.Book;
import com.dttlibrary.model.BookItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.category LEFT JOIN FETCH b.author LEFT JOIN FETCH b.images")
    List<Book> findAllWithDetails();

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.bookItems WHERE b.id = :id")
    Optional<Book> findByIdWithItems(@Param("id") Integer id);

    List<Book> findTop8ByOrderByCreatedAtDesc();

    @Query("SELECT DISTINCT b FROM Book b JOIN b.bookItems bi WHERE bi.status = :status")
    List<Book> findAllWithAvailableItems(@Param("status") BookItem.Status status);
}
