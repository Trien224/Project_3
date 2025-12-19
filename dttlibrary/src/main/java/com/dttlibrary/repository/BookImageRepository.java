package com.dttlibrary.repository;

import com.dttlibrary.model.BookImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookImageRepository extends JpaRepository<BookImage, Integer> {

    // Ảnh chính
    Optional<BookImage> findFirstByBook_IdAndIsPrimaryTrue(Integer bookId);

    // Tất cả ảnh của sách
    List<BookImage> findByBook_Id(Integer bookId);
}
