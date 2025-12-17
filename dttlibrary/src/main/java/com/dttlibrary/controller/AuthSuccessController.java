package com.dttlibrary.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthSuccessController {

    @GetMapping("/login-success")
    public String loginSuccess(Authentication authentication) {

        for (GrantedAuthority role : authentication.getAuthorities()) {

            if (role.getAuthority().equals("ROLE_ADMIN")
                    || role.getAuthority().equals("ROLE_LIBRARIAN")) {
                return "redirect:/admin";
            }

            if (role.getAuthority().equals("ROLE_MEMBER")) {
                return "redirect:/user";
            }
        }

        return "redirect:/";
    }
}
