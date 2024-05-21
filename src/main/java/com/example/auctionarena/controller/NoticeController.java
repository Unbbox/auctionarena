package com.example.auctionarena.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.auctionarena.dto.NoticeDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.service.NoticeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("notice")
@Log4j2
@RequiredArgsConstructor
@Controller
public class NoticeController {

    private final NoticeService service;

    // 공지사항
    @GetMapping("/notice")
    public void notice(Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("list 요청");

        model.addAttribute("result", service.getList(requestDto));
    }

    @GetMapping({ "/notice-details", "/notice-modify" })
    public void noticeDetails(@RequestParam Long nno, Model model,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("read or modify 요청");

        model.addAttribute("dto", service.getRow(nno));
    }

    @PostMapping("/notice-modify")
    public String postModify(NoticeDto dto, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("modify 요청 {}", dto);

        service.modify(dto);

        rttr.addAttribute("nno", dto.getNno());
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/notice/notice-details";
    }

    @PostMapping("/notice-remove")
    public String postRemove(@RequestParam Long nno, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("remove 요청 {}", nno);

        service.noticeRemove(nno);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/notice/notice";
    }

    @GetMapping("/notice-create")
    public void noticeCreate(NoticeDto noticeDto, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("create 요청");
    }

    @PostMapping("/notice-create")
    public String postCreate(@Valid NoticeDto noticeDto, BindingResult result, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("create {}", noticeDto);

        if (result.hasErrors()) {
            return "notice/notice-create";
        }

        Long nno = service.noticeCreate(noticeDto);

        rttr.addFlashAttribute("msg", nno);
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/notice/notice";
    }

}
