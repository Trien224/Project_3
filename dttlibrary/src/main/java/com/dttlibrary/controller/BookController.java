package com.dttlibrary.controller;

import com.dttlibrary.model.Book;
import com.dttlibrary.model.BookItem;
import com.dttlibrary.service.BookItemService;
import com.dttlibrary.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookItemService bookItemService;

    public BookController(BookService bookService,
                          BookItemService bookItemService) {
        this.bookService = bookService;
        this.bookItemService = bookItemService;
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {

        Book book = bookService.findById(id);
        if (book == null) return "redirect:/books";

        long available = bookItemService.countAvailableByBookId(id);

        // ✅ LẤY 1 BOOK ITEM AVAILABLE QUA SERVICE
        BookItem bookItem = bookItemService.findFirstAvailable(id);

        model.addAttribute("book", book);
        model.addAttribute("available", available);
        model.addAttribute("bookItem", bookItem);
        model.addAttribute("primaryImage", bookService.getPrimaryImage(id));

        return "book-detail";
    }
}