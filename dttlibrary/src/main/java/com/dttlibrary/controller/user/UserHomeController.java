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
public class UserHomeController {

    private final BorrowingService borrowingService;
    private final UserService userService;

    public UserHomeController(BorrowingService borrowingService,
                              UserService userService) {
        this.borrowingService = borrowingService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {

        String username = authentication.getName();
        User user = userService.findByUsername(username);

        model.addAttribute("user", user);
        model.addAttribute("borrowings",
                borrowingService.findByUserId(user.getId()));

        return "user/home";
    }
}
