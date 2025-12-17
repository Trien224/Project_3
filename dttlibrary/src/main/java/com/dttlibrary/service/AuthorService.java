package com.dttlibrary.service;

import com.dttlibrary.model.Author;
import com.dttlibrary.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findById(Integer id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public void delete(Integer id) {
        authorRepository.deleteById(id);
    }
}
