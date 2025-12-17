package com.dttlibrary.repository;

import com.dttlibrary.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByName(String name);

    boolean existsBySlug(String slug);
}
