package com.dttlibrary.controller.user;

import com.dttlibrary.model.Borrowing;
import com.dttlibrary.model.User;
import com.dttlibrary.service.BorrowingService;
import com.dttlibrary.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserBorrowingController {

    private final BorrowingService borrowingService;
    private final UserService userService;

    public UserBorrowingController(BorrowingService borrowingService,
                                   UserService userService) {
        this.borrowingService = borrowingService;
        this.userService = userService;
    }

    // 📘 Danh sách / lịch sử mượn sách của user
    @GetMapping("/borrowings")
    public String borrowings(@AuthenticationPrincipal UserDetails userDetails,
                             Model model) {

        // 🔐 Chưa đăng nhập
        if (userDetails == null) {
            return "redirect:/login";
        }

        // 👤 User hiện tại
        User user = userService.findByUsername(userDetails.getUsername());

        // 📚 Danh sách mượn
        List<Borrowing> borrowings =
                borrowingService.findByUserId(user.getId());

        model.addAttribute("borrowings", borrowings);

        // 👉 View tự gắn user-layout
        return "user/borrowings";
    }
}
