package com.dttlibrary.service;

import com.dttlibrary.model.Category;
import com.dttlibrary.repository.CategoryRepository;
import com.github.slugify.Slugify;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Slugify slugify;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.slugify = Slugify.builder().build();
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void save(Category category) {
        // Tự động tạo slug từ name nếu slug rỗng
        if (category.getSlug() == null || category.getSlug().isBlank()) {
            category.setSlug(slugify.slugify(category.getName()));
        } else {
            // Chuẩn hóa slug nếu người dùng tự nhập
            category.setSlug(slugify.slugify(category.getSlug()));
        }

        // ✅ CREATE
        if (category.getId() == null) {
            if (categoryRepository.existsByName(category.getName())) {
                throw new RuntimeException("Category name already exists");
            }
            if (categoryRepository.existsBySlug(category.getSlug())) {
                throw new RuntimeException("Category slug already exists");
            }
        }
        // ✅ EDIT
        else {
            Category old = findById(category.getId());
            if (!old.getName().equals(category.getName())
                    && categoryRepository.existsByName(category.getName())) {
                throw new RuntimeException("Category name already exists");
            }
            if (!old.getSlug().equals(category.getSlug())
                    && categoryRepository.existsBySlug(category.getSlug())) {
                throw new RuntimeException("Category slug already exists");
            }
        }

        categoryRepository.save(category);
    }

    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }
}
