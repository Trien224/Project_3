package com.dttlibrary.controller.admin;

import com.dttlibrary.model.Book;
import com.dttlibrary.model.BookImage;
import com.dttlibrary.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/books")
public class AdminBookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final AuthorService authorService; // Thêm AuthorService
    private final BookImageService bookImageService;
    private final FileStorageService fileStorageService;

    public AdminBookController(BookService bookService,
                               CategoryService categoryService,
                               AuthorService authorService, // Thêm vào constructor
                               BookImageService bookImageService,
                               FileStorageService fileStorageService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.authorService = authorService; // Gán giá trị
        this.bookImageService = bookImageService;
        this.fileStorageService = fileStorageService;
    }

    // ===== LIST =====
    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "admin/books/list";
    }

    // ===== CREATE =====
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("authors", authorService.findAll()); // Thêm danh sách tác giả
        return "admin/books/form";
    }

    // ===== EDIT =====
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("authors", authorService.findAll()); // Thêm danh sách tác giả
        return "admin/books/form";
    }

    // ===== SAVE =====
    @PostMapping("/save")
    public String save(@ModelAttribute Book book,
                       @RequestParam(required = false) MultipartFile image) {

        Book savedBook = bookService.save(book);

        if (image != null && !image.isEmpty()) {
            String fileName = fileStorageService.store(image);
            BookImage img = new BookImage();
            img.setBook(savedBook);
            img.setUrl("/uploads/" + fileName);
            img.setIsPrimary(true);
            bookImageService.save(img);
        }

        return "redirect:/admin/books";
    }

    // ===== DELETE =====
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        bookService.delete(id);
        return "redirect:/admin/books";
    }
}
