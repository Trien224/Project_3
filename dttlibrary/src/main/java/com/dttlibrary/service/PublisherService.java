package com.dttlibrary.service;

import com.dttlibrary.model.Publisher;
import com.dttlibrary.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    public Publisher findById(Integer id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
    }

    public Publisher save(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public void delete(Integer id) {
        publisherRepository.deleteById(id);
    }
}
