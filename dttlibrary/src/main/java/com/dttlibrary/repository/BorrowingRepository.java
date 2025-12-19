package com.dttlibrary.repository;

import com.dttlibrary.model.Borrowing;
import com.dttlibrary.model.Borrowing.Status;
import com.dttlibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Integer> {

    // ===== USER =====

    // Lấy danh sách mượn theo User
    List<Borrowing> findByUser(User user);

    // Lấy theo username (🔥 dùng cho My Borrowings)
    List<Borrowing> findByUserUsername(String username);

    // Lấy theo User + Status
    List<Borrowing> findByUserAndStatus(User user, Status status);

    // Lấy theo userId
    List<Borrowing> findByUser_Id(Integer userId);

    // ===== ADMIN / STAT =====

    long countByStatus(Status status);

    long countByStatusAndDueDateBefore(Status status, LocalDateTime date);
}
