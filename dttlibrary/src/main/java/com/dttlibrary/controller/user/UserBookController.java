package com.dttlibrary.controller.user;

import com.dttlibrary.model.Book;
import com.dttlibrary.model.BookImage;
import com.dttlibrary.service.BookItemService;
import com.dttlibrary.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/books")
public class UserBookController {

    private final BookService bookService;
    private final BookItemService bookItemService;

    public UserBookController(BookService bookService,
                              BookItemService bookItemService) {
        this.bookService = bookService;
        this.bookItemService = bookItemService;
    }

    // 📚 Danh sách sách
    @GetMapping
    public String list(Model model) {

        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);

        // 👉 View tự dùng user-layout
        return "user/books/list";
    }

    // 📖 Chi tiết sách
    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {

        Book book = bookService.findById(id);
        if (book == null) {
            return "redirect:/user/books";
        }

        // 📦 Số bản còn mượn được
        long available = bookItemService.countAvailableByBookId(id);

        // 🖼️ Ảnh sách
        BookImage primaryImage = bookService.getPrimaryImage(id);
        List<BookImage> images = bookService.getImages(id);

        model.addAttribute("book", book);
        model.addAttribute("available", available);
        model.addAttribute("primaryImage", primaryImage);
        model.addAttribute("images", images);

        return "user/books/detail";
    }
}
