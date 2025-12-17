package com.dttlibrary.service;

import com.dttlibrary.model.Book;
import com.dttlibrary.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAllWithAvailableItems() {
        return bookRepository.findAllWithAvailableItems();
    }

    public Book findById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }

    // ===== DÙNG CHO DASHBOARD =====
    public long countBooks() {
        return bookRepository.count();
    }
}
