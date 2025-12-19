package com.dttlibrary.service;

import com.dttlibrary.model.BookImage;
import com.dttlibrary.repository.BookImageRepository;
import org.springframework.stereotype.Service;

@Service
public class BookImageService {

    private final BookImageRepository bookImageRepository;

    public BookImageService(BookImageRepository bookImageRepository) {
        this.bookImageRepository = bookImageRepository;
    }

    public void save(BookImage image) {
        bookImageRepository.save(image);
    }
}
