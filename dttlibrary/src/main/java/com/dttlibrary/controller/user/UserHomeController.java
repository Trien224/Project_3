package com.dttlibrary.controller.user;

import com.dttlibrary.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserHomeController {

    private final BookService bookService;

    public UserHomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/home")
    public String home(Model model) {

        model.addAttribute("newBooks", bookService.findLatestBooks());
        model.addAttribute("categories", List.of());

        return "user/home";
    }
}
