package com.dttlibrary.controller.admin;

import com.dttlibrary.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final BookService bookService;
    private final BookItemService bookItemService;
    private final BorrowingService borrowingService;

    public AdminDashboardController(BookService bookService,
                                    BookItemService bookItemService,
                                    BorrowingService borrowingService) {
        this.bookService = bookService;
        this.bookItemService = bookItemService;
        this.borrowingService = borrowingService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalBooks", bookService.countBooks());
        model.addAttribute("totalItems", bookItemService.countItems());
        model.addAttribute("borrowed", borrowingService.countBorrowed());
        model.addAttribute("overdue", borrowingService.countOverdue());

        model.addAttribute("content", "admin/dashboard");
        return "layout/admin-layout";
    }
}
