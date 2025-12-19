package com.dttlibrary.service;

import com.dttlibrary.model.BookItem;
import com.dttlibrary.model.Borrowing;
import com.dttlibrary.model.User;
import com.dttlibrary.repository.BorrowingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BookItemService bookItemService;
    private final UserService userService;

    public BorrowingService(BorrowingRepository borrowingRepository,
                            BookItemService bookItemService,
                            UserService userService) {
        this.borrowingRepository = borrowingRepository;
        this.bookItemService = bookItemService;
        this.userService = userService;
    }

    // ===== USER =====
    public List<Borrowing> findByUsername(String username) {
        return borrowingRepository.findByUserUsername(username);
    }

    @Transactional(readOnly = true)
    public List<Borrowing> findByUser(User user) {
        return borrowingRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Borrowing> findByUserId(Integer userId) {
        return borrowingRepository.findByUser_Id(userId);
    }

    // ===== ADMIN =====
    @Transactional(readOnly = true)
    public List<Borrowing> findAll() {
        return borrowingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Borrowing findById(Integer id) {
        return borrowingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrowing not found"));
    }

    // ===== BORROW =====
    @Transactional
    public void borrowBook(Integer bookItemId, Integer userId) {

        BookItem item = bookItemService.findById(bookItemId);

        if (item == null || item.getStatus() != BookItem.Status.available) {
            throw new RuntimeException("Book not available");
        }

        User user = userService.findById(userId);

        Borrowing borrowing = new Borrowing();
        borrowing.setUser(user);
        borrowing.setBookItem(item);
        borrowing.setBorrowDate(LocalDateTime.now());
        borrowing.setDueDate(LocalDateTime.now().plusDays(7));
        borrowing.setStatus(Borrowing.Status.borrowed);

        borrowingRepository.save(borrowing);

        item.setStatus(BookItem.Status.borrowed);
        bookItemService.save(item);
    }

    // ===== RETURN =====
    @Transactional
    public void returnBook(Integer id) {

        Borrowing borrowing = findById(id);

        if (borrowing.getStatus() == Borrowing.Status.returned) {
            return;
        }

        borrowing.setStatus(Borrowing.Status.returned);
        borrowing.setReturnDate(LocalDateTime.now());
        borrowingRepository.save(borrowing);

        BookItem item = borrowing.getBookItem();
        item.setStatus(BookItem.Status.available);
        bookItemService.save(item);
    }

    // ===== DASHBOARD =====
    public long countBorrowed() {
        return borrowingRepository.countByStatus(Borrowing.Status.borrowed);
    }

    public long countOverdue() {
        return borrowingRepository.countByStatusAndDueDateBefore(
                Borrowing.Status.borrowed,
                LocalDateTime.now()
        );
    }
}
