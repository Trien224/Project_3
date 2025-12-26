package com.dttlibrary.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin");
                return;
            } else if (auth.getAuthority().equals("ROLE_MEMBER") || auth.getAuthority().equals("ROLE_LIBRARIAN")) {
                response.sendRedirect("/user/home");
                return;
            }
        }
        // Fallback to home page if no specific role is matched
        response.sendRedirect("/");
    }
}
