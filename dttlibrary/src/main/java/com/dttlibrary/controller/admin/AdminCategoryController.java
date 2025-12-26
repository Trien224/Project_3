package com.dttlibrary.controller.admin;

import com.dttlibrary.model.Category;
import com.dttlibrary.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/categories/list";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        return "admin/categories/form";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "admin/categories/form";
    }

    // SAVE (CREATE + EDIT)
    @PostMapping("/save")
    public String save(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return "redirect:/admin/categories";
    }
}
