package com.meohin.meboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;


@Controller
@RequestMapping("/user")
public class UserController {
    
    // 회원가입
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model) {
        return "redirect:/post/list";
    }

    // 로그인
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout() {
        return "redirect:/post/list";
    }
    
    // 회원정보
    @GetMapping("/mypage")
    public String mypage() {
        return "mypage";
    }

    // 프로필
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @PostMapping("/profile")
    public String profile(Model model) {
        return "redirect:/user/profile";
    }
    
    // 회원정보 수정
    @GetMapping("/modify")
    public String modify() {
        return "modify";
    }

    @PostMapping("/modify")
    public String modify(Model model) {
        return "redirect:/user/modify";
    }

    // 회원탈퇴
    @PostMapping("/delete")
    public String delete() {
        return "redirect:/";
    }

    // 좋아요한 게시글 목록
    @GetMapping("/like")
    public String like() {
        return "user/like";
    }

    // 작성한 댓글 목록
    @GetMapping("/reply")
    public String reply() {
        return "user/reply";
    }
}
