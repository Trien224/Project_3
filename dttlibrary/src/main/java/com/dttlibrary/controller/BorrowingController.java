package com.dttlibrary.controller;

import com.dttlibrary.model.User;
import com.dttlibrary.service.BorrowingService;
import com.dttlibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {

    private final BorrowingService borrowingService;
    private final UserService userService;

    public BorrowingController(BorrowingService borrowingService,
                               UserService userService) {
        this.borrowingService = borrowingService;
        this.userService = userService;
    }

    @PostMapping("/borrow")
    public String borrow(@RequestParam Integer bookItemId, Principal principal) {
        if (principal == null) return "redirect:/login";

        User user = userService.findByUsername(principal.getName());
        borrowingService.borrowBook(bookItemId, user.getId());

        return "redirect:/user/borrowings";
    }

    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable Integer id) {
        borrowingService.returnBook(id);
        return "redirect:/user/borrowings";
    }
}
