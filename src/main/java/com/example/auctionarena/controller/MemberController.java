package com.example.auctionarena.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.auctionarena.service.MemberService;

@RequestMapping("member")
@RequiredArgsConstructor
@Log4j2
@Controller
public class MemberController {

    private final MemberService service;

    @GetMapping("/login")
    public void login() {
        log.info("로그인 폼 요청");
    }

    @GetMapping("/signup")
    public String signup() {
        log.info("회원가입 폼 요청");
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
