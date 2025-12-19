package com.dttlibrary.controller.user;

import com.dttlibrary.model.User;
import com.dttlibrary.service.BorrowingService;
import com.dttlibrary.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserBorrowController {

    private final BorrowingService borrowingService;
    private final UserService userService;

    public UserBorrowController(BorrowingService borrowingService,
                                UserService userService) {
        this.borrowingService = borrowingService;
        this.userService = userService;
    }

    // ===== MY BORROWINGS =====
    @GetMapping("/my-borrowings")
    public String myBorrowings(Model model,
                               @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername());

        model.addAttribute("user", user);
        model.addAttribute("borrowings",
                borrowingService.findByUserId(user.getId()));

        return "user/my-borrowings";
    }

    // ===== BORROW BOOK =====
    @PostMapping("/borrow/{bookItemId}")
    public String borrow(@PathVariable Integer bookItemId,
                         @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername());

        borrowingService.borrowBook(bookItemId, user.getId());

        return "redirect:/user/my-borrowings";
    }
}
