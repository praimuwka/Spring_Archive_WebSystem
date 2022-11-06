package com.github.praimuwka.spring_archive_websystem.config.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public String getLoginPage() {
//        ModelAndView mav = new ModelAndView("auth/login");
        return "auth/login";
    }

    @GetMapping("/success")
    @PostAuthorize("isAuthenticated()")
    public String getSuccessPage() {
        return "auth/loginHome";
    }
}
