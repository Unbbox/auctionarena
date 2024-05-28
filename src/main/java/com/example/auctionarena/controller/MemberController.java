package com.example.auctionarena.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.auctionarena.dto.MemberDto;
import com.example.auctionarena.service.MemberService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public void signup(MemberDto memberDto) {
        log.info("회원가입 폼 요청");
    }

    @PostMapping("/signup")
    public String postSignup(@Valid MemberDto dto, BindingResult result, RedirectAttributes rttr) {
        log.info("회원가입 요청 {}", dto);

        if (result.hasErrors()) {
            return "/member/signup";
        }

        try {
            String email = service.signup(dto);
            rttr.addFlashAttribute("email", email);
            return "redirect:/member/login";
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("이메일")) {
                result.rejectValue("email", "duplicate", "이미 가입된 이메일입니다.");
            } else if (e.getMessage().contains("닉네임")) {
                result.rejectValue("nickname", "duplicate", "이미 사용중인 닉네임입니다.");
            }
            return "/member/signup";
        }
    }

    @GetMapping("/find-password")
    public void passwordFind(MemberDto memberDto) {
        log.info("비밀번호 찾기 요청");
    }

    @PostMapping("/find-password")
    public String postPasswordFind(MemberDto dto, RedirectAttributes rttr, Model model) {
        log.info("비밀번호 찾기 {}", dto);

        try {
            service.passwordFind(dto);
            return "/member/edit-password";
        } catch (IllegalStateException e) {
            rttr.addFlashAttribute("error", e.getMessage());
            rttr.addFlashAttribute("memberDto", dto);
            return "redirect:/member/find-password";
        }
    }

    @GetMapping("/edit-password")
    public String editProfile() {
        return "/member/edit-password";
    }
}
