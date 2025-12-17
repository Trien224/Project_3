package com.dttlibrary.controller.auth;

import com.dttlibrary.model.Role;
import com.dttlibrary.model.User;
import com.dttlibrary.repository.RoleRepository;
import com.dttlibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class RegisterController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public RegisterController(UserService userService,
                              RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    // 👉 Form đăng ký
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // 👉 Xử lý đăng ký
    @PostMapping("/register")
    public String register(@ModelAttribute User user) {

        // gán ROLE_MEMBER
        Role memberRole = roleRepository.findByRoleName("MEMBER");
        user.setRoles(Set.of(memberRole));

        userService.save(user);

        return "redirect:/login?register_success";
    }
}
