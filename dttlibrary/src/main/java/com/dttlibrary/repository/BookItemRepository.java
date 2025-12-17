package com.dttlibrary.repository;

import com.dttlibrary.model.BookItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookItemRepository extends JpaRepository<BookItem, Integer> {
    List<BookItem> findByStatus(String status);
}
