package com.dttlibrary.controller.user;

import com.dttlibrary.model.User;
import com.dttlibrary.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 👤 USER PROFILE
     * URL: /user/profile
     */
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails,
                          Model model) {

        // 🔐 Chưa đăng nhập
        if (userDetails == null) {
            return "redirect:/login";
        }

        // 👤 Lấy user hiện tại
        User user = userService.findByUsername(userDetails.getUsername());

        model.addAttribute("user", user);

        // 👉 View tự gắn user-layout
        return "user/profile";
    }
}
