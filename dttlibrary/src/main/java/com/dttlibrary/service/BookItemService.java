package com.dttlibrary.service;

import com.dttlibrary.model.BookItem;
import com.dttlibrary.repository.BookItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookItemService {

    private final BookItemRepository bookItemRepository;

    public BookItemService(BookItemRepository bookItemRepository) {
        this.bookItemRepository = bookItemRepository;
    }

    public List<BookItem> findAll() {
        return bookItemRepository.findAll();
    }

    public BookItem findById(Integer id) {
        return bookItemRepository.findById(id).orElse(null);
    }

    public void save(BookItem item) {
        bookItemRepository.save(item);
    }

    // ✅ DASHBOARD
    public long countItems() {
        return bookItemRepository.count();
    }
    public BookItem findFirstAvailable(Integer bookId) {
        return bookItemRepository
                .findFirstByBook_IdAndStatus(
                        bookId,
                        BookItem.Status.available
                )
                .orElse(null);
    }

    public long countAvailableByBookId(Integer bookId) {
        return bookItemRepository.countByBook_IdAndStatus(
                bookId,
                BookItem.Status.available
        );
    }
}
