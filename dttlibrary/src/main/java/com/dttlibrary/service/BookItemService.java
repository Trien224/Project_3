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

    public List<BookItem> findAvailable() {
        return bookItemRepository.findByStatus("available");
    }

    public BookItem findById(Integer id) {
        return bookItemRepository.findById(id).orElse(null);
    }

    public void save(BookItem item) {
        bookItemRepository.save(item);
    }

    // ===== DÙNG CHO DASHBOARD =====
    public long countItems() {
        return bookItemRepository.count();
    }
}
