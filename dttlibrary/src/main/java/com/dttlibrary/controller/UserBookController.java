package com.dttlibrary.controller;

import com.dttlibrary.model.Book;
import com.dttlibrary.service.BookItemService;
import com.dttlibrary.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class UserBookController {

    private final BookService bookService;
    private final BookItemService itemService;

    public UserBookController(BookService bookService,
                              BookItemService itemService) {
        this.bookService = bookService;
        this.itemService = itemService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAllWithAvailableItems());
        model.addAttribute("content", "books/list");
        return "books";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Book book = bookService.findById(id);

        long available = itemService.findAll().stream()
                .filter(i -> i.getBook().getId().equals(id)
                        && "available".equals(i.getStatus()))
                .count();

        model.addAttribute("book", book);
        model.addAttribute("available", available);
        model.addAttribute("content", "books/detail");
        return "layout";
    }

}
