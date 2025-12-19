package com.dttlibrary.controller.admin;

import com.dttlibrary.model.Book;
import com.dttlibrary.model.BookImage;
import com.dttlibrary.service.BookService;
import com.dttlibrary.service.BookImageService;
import com.dttlibrary.service.CategoryService;
import com.dttlibrary.service.FileStorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/books")
public class AdminBookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final BookImageService bookImageService;
    private final FileStorageService fileStorageService;

    public AdminBookController(BookService bookService,
                               CategoryService categoryService,
                               BookImageService bookImageService,
                               FileStorageService fileStorageService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
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
        return "admin/books/form";
    }

    // ===== SAVE =====
    @PostMapping("/save")
    public String save(@ModelAttribute Book book,
                       @RequestParam(required = false) MultipartFile image) {

        bookService.save(book);

        if (image != null && !image.isEmpty()) {

            String fileName = fileStorageService.store(image);

            BookImage img = new BookImage();
            img.setBook(book);
            img.setUrl("/uploads/" + fileName);
            img.setIsPrimary(true);

            bookImageService.save(img);
        }

        return "redirect:/admin/books";
    }
}
