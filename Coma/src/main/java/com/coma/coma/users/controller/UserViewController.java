package com.coma.coma.users.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("users/login")
    public String loginPage() {
        return "user/login"; // user/login.html 템플릿을 반환
    }

    @GetMapping("users/register")
    public String registerPage() {
        return "user/register"; // user/register.html 템플릿을 반환
    }

}
