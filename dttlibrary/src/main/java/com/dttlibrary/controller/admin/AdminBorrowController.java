package com.dttlibrary.controller.admin;

import com.dttlibrary.service.BorrowingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/borrows")
public class AdminBorrowController {

    private final BorrowingService borrowingService;

    public AdminBorrowController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("borrows", borrowingService.findAll());
        model.addAttribute("content", "admin/borrows/list");
        return "admin/admin-layout";
    }
}
