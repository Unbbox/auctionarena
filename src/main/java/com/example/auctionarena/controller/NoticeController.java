package com.example.auctionarena.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequestMapping("auctionArena")
@Log4j2
@RequiredArgsConstructor
@Controller
public class NoticeController {

    private final NoticeService service;

    // 공지사항
    @GetMapping("/notice")
    public void notice(@ModelAttribute("requestDto") PageRequestDto requestDto, RedirectAttributes rttr, Model model) {
        log.info("list 요청");

        model.addAttribute("result", service.getList(requestDto));
    }

    @GetMapping("/notice-details")
    public String noticeDetails() {
        return "auctionArena/notice-details";
    }
}
