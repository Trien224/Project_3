package com.dttlibrary.controller.user;

import com.dttlibrary.model.User;
import com.dttlibrary.service.BorrowingService;
import com.dttlibrary.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserDashboardController {

    private final UserService userService;
    private final BorrowingService borrowingService;

    public UserDashboardController(UserService userService,
                                   BorrowingService borrowingService) {
        this.userService = userService;
        this.borrowingService = borrowingService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {

        String username = authentication.getName();
        User user = userService.findByUsername(username);

        model.addAttribute("user", user);
        model.addAttribute("borrowings",
                borrowingService.findByUserId(user.getId()));

        return "user/dashboard";
    }
}
