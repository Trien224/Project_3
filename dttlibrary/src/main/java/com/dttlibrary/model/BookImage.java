package com.dttlibrary.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dtt_book_images")
public class BookImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ===== RELATION =====
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // ===== FIELDS =====
    @Column(length = 1000, nullable = false)
    private String url;

    @Column(nullable = false)
    private Boolean isPrimary = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ===== LIFECYCLE =====
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ===== GETTERS & SETTERS =====
    public Integer getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    // 🔥 METHOD BỊ THIẾU → GÂY LỖI
    public void setBook(Book book) {
        this.book = book;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
