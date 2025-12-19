package com.dttlibrary.repository;

import com.dttlibrary.model.BookItem;
import com.dttlibrary.model.BookItem.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookItemRepository extends JpaRepository<BookItem, Integer> {

    // Đếm số bản copy available của 1 book
    long countByBook_IdAndStatus(Integer bookId, Status status);

    // Lấy danh sách book item theo trạng thái
    List<BookItem> findByStatus(Status status);

    // Lấy 1 bản copy available để mượn
    Optional<BookItem> findFirstByBook_IdAndStatus(Integer bookId, Status status);
}
