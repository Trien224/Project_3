package com.dttlibrary.repository;

import com.dttlibrary.model.Borrowing;
import com.dttlibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Integer> {

    // ✔ theo User
    List<Borrowing> findByUser(User user);

    // ✔ theo User + Status  (🔥 BẮT BUỘC)
    List<Borrowing> findByUserAndStatus(User user, String status);

    // ✔ theo userId (tuỳ dùng)
    List<Borrowing> findByUser_Id(Integer userId);

    long countByStatus(String status);

    long countByStatusAndDueDateBefore(String status, LocalDateTime date);
}
