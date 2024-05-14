package com.example.auctionarena.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("member")
@Log4j2
@Controller
public class MemberController {
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "member/signup";
    }

    @GetMapping("/edit-password")
    public String editProfile() {
        return "member/edit-password";
    }

    @GetMapping("/find-password")
    public String findProfile() {
        return "member/find-password";
    }
}
