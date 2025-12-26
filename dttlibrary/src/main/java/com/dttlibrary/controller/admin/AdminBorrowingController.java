package com.dttlibrary.controller.admin;

import com.dttlibrary.service.BorrowingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/admin/borrowings")
public class AdminBorrowingController {

    private final BorrowingService borrowingService;

    public AdminBorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("borrowings", borrowingService.findAll());
        return "admin/borrowings/list";
    }

    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable Integer id) {
        borrowingService.returnBook(id);
        return "redirect:/admin/borrowings";
    }
}
