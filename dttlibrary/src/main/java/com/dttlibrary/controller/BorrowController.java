package com.dttlibrary.controller;

import com.dttlibrary.service.BorrowingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BorrowController {

    private final BorrowingService borrowingService;

    public BorrowController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @GetMapping("/borrow")
    public String showBorrowPage() {
        return "borrow";
    }

    @PostMapping("/borrow")
    public String borrow(@RequestParam Integer userId,
                         @RequestParam Integer bookItemId) {

        borrowingService.borrowBook(bookItemId, userId);
        return "redirect:/borrow?success";
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam Integer borrowId) {
        borrowingService.returnBook(borrowId);
        return "redirect:/borrow?returned";
    }
}
