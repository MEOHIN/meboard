package com.meohin.meboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meohin.meboard.service.UserService;
import com.meohin.meboard.vo.UserVO;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    
    // 회원가입
    // /user/signup URL 이 GET 으로 요청되면 회원 가입을 위한 validation object인 UserVO를 렌더링
    @GetMapping("/register")
    public String register(UserVO userVO) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserVO userVO, BindingResult bindingResult) {
        // 1. 입력값 검증
        if (bindingResult.hasErrors()) {
            return "register";
        }

        // 2. 아이디 중복 검사
        try {
            if (userService.isUsernameTaken(userVO.getUsername())) {
                bindingResult.rejectValue("username", "duplicateUsername", "이미 등록된 아이디입니다.");
                return "register";
            }
        } catch (Exception e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "register";
        }

        // 3. 닉네임 중복 검사
        if (userService.isNicknameTaken(userVO.getNickname())) {
            bindingResult.rejectValue("nickname", "duplicateNickname", "이미 등록된 닉네임입니다.");
            return "register";
        }

        // 4. 비밀번호 일치여부 검사
        if (!userVO.getPassword1().equals(userVO.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호와 비밀번호 확인이 다릅니다.");
            return "register";
        }

        // 5. 회원가입 진행
        try {
            userService.register(userVO.getUsername(), userVO.getNickname(), userVO.getPassword1());
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "register";
        }

        return "redirect:/user/login";
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
