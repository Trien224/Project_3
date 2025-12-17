package com.dttlibrary.controller.user;

import com.dttlibrary.model.Borrowing;
import com.dttlibrary.model.User;
import com.dttlibrary.service.BorrowingService;
import com.dttlibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user/borrowings")
public class UserBorrowingController {

    private final BorrowingService borrowingService;
    private final UserService userService;

    public UserBorrowingController(BorrowingService borrowingService,
                                   UserService userService) {
        this.borrowingService = borrowingService;
        this.userService = userService;
    }

    // 👉 Lịch sử mượn sách của user
    @GetMapping
    public String borrowHistory(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByUsername(principal.getName());

        List<Borrowing> borrowings =
                borrowingService.findByUser(user);

        model.addAttribute("borrowings", borrowings);

        return "user/borrow-history";
    }
}
