package com.dttlibrary.controller.user;

import com.dttlibrary.model.Borrowing;
import com.dttlibrary.model.User;
import com.dttlibrary.repository.BorrowingRepository;
import com.dttlibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/borrow")
public class ProfileController {

    private final UserService userService;
    private final BorrowingRepository borrowingRepository;

    public ProfileController(UserService userService, BorrowingRepository borrowingRepository) {
        this.userService = userService;
        this.borrowingRepository = borrowingRepository;
    }

    @GetMapping("/my")
    public String myBorrowings(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        List<Borrowing> borrowings = borrowingRepository.findByUserAndStatus(user, "borrowed");
        model.addAttribute("borrowings", borrowings);
        return "user/my-borrowings";
    }
}
