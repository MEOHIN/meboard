package com.meohin.meboard.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meohin.meboard.entity.Post;
import com.meohin.meboard.entity.Reply;
import com.meohin.meboard.entity.SiteUser;
import com.meohin.meboard.service.PostService;
import com.meohin.meboard.service.ReplyService;
import com.meohin.meboard.service.UserService;
import com.meohin.meboard.vo.PostVO;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ReplyService replyService;
    private final UserService userService;
    
    @GetMapping("/list")
    public String list(Model model) {

        List<Post> postList = postService.getPostList();
        if (postList.isEmpty()) {
            model.addAttribute("message", "게시글이 없습니다.");
            
        }
        model.addAttribute("postList", postList);

        return "list";
    }

    @GetMapping("/{postId}")
    public String detail(Model model, @PathVariable("postId") Long postId) {
        Optional<Post> post = postService.getPost(postId);  // Optional<Post>은 작성자가 삭제한 순간 타사용자가 해당 글을 조회할 때 null을 반환하는 것을 방지하기 위함
        List<Reply> replyList = replyService.getReplyList(postId);

        if (!post.isPresent()) {
            return "redirect:/post/list";
        }

        postService.addViewCount(postId, 1);

        model.addAttribute("post", post.get());
        model.addAttribute("replyList", replyList);

        return "detail";
    }

    @GetMapping("/search")
    public String searchPost(Model model, @RequestParam String keyword) {
        System.out.println("keyword: " + keyword);
        List<Post> postList = postService.searchPost(keyword);
        if (postList.isEmpty()) {
            model.addAttribute("message", "검색 결과가 없습니다.");            
        }
        model.addAttribute("postList", postList);
        return "list";
    }

    @GetMapping("/write")
    public String writePost(PostVO postVO) {
        return "write";
    }

    @PostMapping("/write")
    public String writePost(@Valid PostVO postVO, Principal principal) {
        SiteUser currentUser = userService.getUser(principal.getName());

        postService.writePost(postVO.getTitle(), postVO.getContent(), currentUser);
        return "redirect:/post/list";
    }

    @GetMapping("/{postId}/modify")
    public String modifyPost(PostVO postVO) {
        return "write";
    }

    @PostMapping("/{postId}/modify")
    public String modifyPost(@PathVariable("postId") Long postId, 
                        @Valid PostVO postVO) {
        postService.modifyPost(postId, postVO.getTitle(), postVO.getContent());
        return "redirect:/post/" + postId;
    }

    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return "redirect:/post/list";
    }

    // TODO: paging, sort, filter, pagination; best, recnet, popular, tag, category, etc.
}
