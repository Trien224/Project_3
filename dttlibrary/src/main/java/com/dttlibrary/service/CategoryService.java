package com.dttlibrary.service;

import com.dttlibrary.model.Category;
import com.dttlibrary.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void save(Category category) {

        // ✅ CREATE
        if (category.getId() == null) {
            if (categoryRepository.existsByName(category.getName())) {
                throw new RuntimeException("Category name already exists");
            }
        }
        // ✅ EDIT (không được trùng với category khác)
        else {
            Category old = findById(category.getId());
            if (!old.getName().equals(category.getName())
                    && categoryRepository.existsByName(category.getName())) {
                throw new RuntimeException("Category name already exists");
            }
        }

        categoryRepository.save(category);
    }

    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }
}
