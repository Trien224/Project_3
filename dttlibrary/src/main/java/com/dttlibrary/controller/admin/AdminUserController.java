package com.dttlibrary.controller.admin;

import com.dttlibrary.model.User;
import com.dttlibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    // ===== LIST =====
    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users/list";
    }

    // ===== CREATE =====
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        return "admin/users/form";
    }

    // ===== SAVE =====
    @PostMapping("/save")
    public String save(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }

    // ===== EDIT =====
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/users/form";
    }

    // ===== DELETE =====
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
