package com.dttlibrary.controller.admin;

import com.dttlibrary.model.Author;
import com.dttlibrary.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/authors")
public class AdminAuthorController {

    private final AuthorService authorService;

    public AdminAuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("content", "admin/authors/list");
        return "admin/admin-layout";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("content", "admin/authors/form");
        return "admin/admin-layout";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Author author) {
        authorService.save(author);
        return "redirect:/admin/authors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        model.addAttribute("content", "admin/authors/form");
        return "admin/admin-layout";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        authorService.delete(id);
        return "redirect:/admin/authors";
    }
}
