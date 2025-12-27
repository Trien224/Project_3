package com.dttlibrary.service;

import com.dttlibrary.model.Book;
import com.dttlibrary.model.BookImage;
import com.dttlibrary.model.BookItem;
import com.dttlibrary.repository.BookImageRepository;
import com.dttlibrary.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookImageRepository bookImageRepository;

    public BookService(BookRepository bookRepository,
                       BookImageRepository bookImageRepository) {
        this.bookRepository = bookRepository;
        this.bookImageRepository = bookImageRepository;
    }

    // ===== CRUD =====
    public List<Book> findAll() {
        return bookRepository.findAllWithDetails();
    }

    public List<Book> findAllWithAvailableItems() {
        return bookRepository.findAllWithAvailableItems(BookItem.Status.available);
    }

    public Book findById(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book findByIdWithItems(Integer id) {
        return bookRepository.findByIdWithItems(id).orElse(null);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }

    // ===== USER HOME =====
    public List<Book> findLatestBooks() {
        return bookRepository.findTop8ByOrderByCreatedAtDesc();
    }

    // ===== BOOK DETAIL =====

    // Ảnh chính
    public BookImage getPrimaryImage(Integer bookId) {
        return bookImageRepository
                .findFirstByBook_IdAndIsPrimaryTrue(bookId)
                .orElse(null);
    }

    // Tất cả ảnh
    public List<BookImage> getImages(Integer bookId) {
        return bookImageRepository.findByBook_Id(bookId);
    }

    // ===== DASHBOARD =====
    public long countBooks() {
        return bookRepository.count();
    }
}
