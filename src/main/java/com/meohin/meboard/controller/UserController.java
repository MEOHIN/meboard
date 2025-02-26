package com.meohin.meboard.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.meohin.meboard.entity.Post;
import com.meohin.meboard.entity.Reply;
import com.meohin.meboard.entity.SiteUser;
import com.meohin.meboard.service.UserService;
import com.meohin.meboard.vo.UserVO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    // 회원가입
    // /signup URL 이 GET 으로 요청되면 회원 가입을 위한 validation object인 UserVO를 렌더링
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

        return "redirect:/login";
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
    public String mypage(Model model, UserVO userVO, Principal principal) {

        String username = principal.getName();
        SiteUser currentUser = userService.getUser(username);
        
        // 사용자 정보를 담을 UserVO 객체 생성 및 초기값 설정
        userVO.setUsername(username);
        userVO.setNickname(currentUser.getNickname());
        
        model.addAttribute("userVO", userVO);

        return "mypage";
    }

    // 회원정보 수정
    @PostMapping("/mypage")
    public String mypage(@Valid UserVO userVO, BindingResult bindingResult, 
                        @RequestParam("action") String action,
                        Principal principal,
                        RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "mypage";
        }

        // 현재 로그인한 사용자 정보 확인
        String username = principal.getName();
        SiteUser currentUser = userService.getUser(username);

        try {
            switch (action) {
                // 닉네임 수정 로직
                case "updateNickname":
                        userService.modifyNickname(username, userVO.getNickname());
                        redirectAttributes.addFlashAttribute("message", "닉네임이 성공적으로 변경되었습니다.");
                        break;

                // 비밀번호 수정 로직
                case "updatePassword":
                    // 비밀번호 일치여부 검사
                    if (!userVO.getPassword1().equals(userVO.getPassword2())) {
                        bindingResult.rejectValue("password2", "passwordInCorrect", 
                            "새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
                        return "mypage";
                    }
                    
                    userService.modifyPassword(
                        currentUser, 
                        userVO.getCurrentPassword(), 
                        userVO.getPassword1()
                    );
                    redirectAttributes.addFlashAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
                    break;

                default:
                    throw new IllegalArgumentException("잘못된 요청입니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("modificationFailed", e.getMessage());
            return "mypage";
        }

        return "redirect:/mypage";
    }

    // 회원탈퇴
    @PostMapping("/delete")
    public String delete() {
        return "redirect:/";
    }
    
    // 나의활동
    @GetMapping("/mylog")
    public String mylog(Model model, Principal principal) {
        SiteUser currentUser = userService.getUser(principal.getName());

        List<Post> myPosts = userService.getPostsByUser(currentUser);
        if (myPosts.isEmpty()) {
            model.addAttribute("postMessage", "작성한 게시글이 없습니다.");
        }

        List<Reply> myReplies = userService.getRepliesByUser(currentUser);
        if (myReplies.isEmpty()) {
            model.addAttribute("replyMessage", "작성한 댓글이 없습니다.");

        }

        model.addAttribute("myPosts", myPosts);
        model.addAttribute("myReplies", myReplies);

        return "mylog";
    }
}
