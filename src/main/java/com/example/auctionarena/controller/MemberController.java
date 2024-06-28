package com.example.auctionarena.controller;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.auctionarena.dto.MemberDto;
import com.example.auctionarena.dto.PasswordChangeDto;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.repository.MemberRepository;
import com.example.auctionarena.service.MemberService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("member")
@RequiredArgsConstructor
@Log4j2
@Controller
public class MemberController {

    private final MemberService service;

    private final MemberRepository memberRepository;

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
    public String postPasswordFind(MemberDto dto, RedirectAttributes rttr) {
        log.info("비밀번호 찾기 {}", dto);
        // String email = dto.getEmail();

        try {
            service.passwordFind(dto);
            rttr.addFlashAttribute("email", dto.getEmail());
            return "redirect:/member/edit-password";
        } catch (IllegalStateException e) {
            rttr.addFlashAttribute("error", e.getMessage());
            rttr.addFlashAttribute("memberDto", dto);
            return "redirect:/member/find-password";
        }
    }

    @GetMapping("/edit-password")
    public String editPassword(@ModelAttribute("email") String email, Model model, PasswordChangeDto pDto) {
        pDto.setEmail(email);
        model.addAttribute("pDto", pDto);
        log.info("비밀번호 변경 요청 {}", pDto);
        return "/member/edit-password";
    }

    @PostMapping("/edit-password")
    public String postEditPassword(@ModelAttribute PasswordChangeDto pDto, RedirectAttributes rttr,
            HttpSession session) {
        log.info("변경 요청 {}", pDto);
        if (!pDto.getNewPassword().equals(pDto.getCheckPassword())) {
            rttr.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "redirect:/member/edit-password";
        }

        try {
            // 비밀번호가 일치하는 경우 비밀번호를 업데이트하고 로그인 페이지로 리다이렉트합니다.
            service.passwordUpdate(pDto);
            session.invalidate();
            return "redirect:/member/login";
        } catch (IllegalStateException e) {
            // 비밀번호 업데이트 중에 발생한 예외가 있는 경우 해당 예외 메시지를 에러로 추가하고 비밀번호 변경 페이지로 리다이렉트합니다.
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/edit-password";
        }
    }

    @GetMapping("/leave")
    public void getLeaveForm() {
        log.info("회원탈퇴");
    }

    @PostMapping("/leave")
    public String postLeave(@RequestParam("email") String email, RedirectAttributes rttr, HttpSession session) {
        log.info("회원탈퇴 {}", email);

        try {
            service.leave(email);
            session.invalidate();
            return "redirect:/";
        } catch (IllegalStateException e) {
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/mypage";
        }
    }

    @GetMapping("/mypage")
    public String mypage(Model model, MemberDto memberDto) {
        log.info("마이페이지 요청 {}", memberDto);

        // 현재 로그인된 사용자의 인증 객체를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 이메일 가져오기
        String email = authentication.getName();

        // 회원 서비스를 통해 회원 정보 가져오기
        Optional<Member> member = memberRepository.findByEmail(email);

        // 모델에 회원 정보를 추가하여 뷰로 전달
        if (member.isPresent()) {
            MemberDto dto = service.entityToDto(member.get());
            model.addAttribute("memberDto", dto);
        }

        return "/member/mypage";
    }

    @GetMapping("/accountInfo")
    public String editMemberInfo(Model model, MemberDto memberDto) {
        log.info("회원정보 수정");

        // 현재 로그인된 사용자의 인증 객체를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 이메일 가져오기
        String email = authentication.getName();

        // 회원 서비스를 통해 회원 정보 가져오기
        Optional<Member> member = memberRepository.findByEmail(email);

        // 모델에 회원 정보를 추가하여 뷰로 전달
        if (member.isPresent()) {
            MemberDto dto = service.entityToDto(member.get());
            model.addAttribute("memberDto", dto);
        }

        return "/member/accountInfo";
    }

    @PostMapping("/accountInfo")
    public String postAccountInfo(@Valid MemberDto dto, BindingResult result, RedirectAttributes rttr) {
        log.info("회원정보 수정 요청 {}", dto);

        if (result.hasErrors()) {
            return "/member/accountInfo";
        }

        try {
            service.editAccountInfo(dto);
            rttr.addFlashAttribute("message", "회원정보가 수정되었습니다.");
            return "redirect:/member/accountInfo";
        } catch (IllegalStateException e) {
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/accountInfo";
        }
    }

}
