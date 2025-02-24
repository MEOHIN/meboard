package com.meohin.meboard.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meohin.meboard.entity.Post;
import com.meohin.meboard.entity.Reply;
import com.meohin.meboard.service.PostService;
import com.meohin.meboard.service.ReplyService;

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
    public String detail(@PathVariable("postId") Long postId, Model model) {
        Optional<Post> post = postService.getPost(postId);  // Optional<Post>은 작성자가 삭제한 순간 타사용자가 해당 글을 조회할 때 null을 반환하는 것을 방지하기 위함
        List<Reply> replyList = replyService.getReplyList(postId);

        if (!post.isPresent()) {
            return "redirect:/post/list";
        }

        model.addAttribute("post", post.get());
        model.addAttribute("replyList", replyList);

        return "detail";
    }

    @GetMapping("/search")
    public String searchPost(@RequestParam String keyword, Model model) {
        System.out.println("keyword: " + keyword);
        List<Post> postList = postService.searchPost(keyword);
        if (postList.isEmpty()) {
            model.addAttribute("message", "검색 결과가 없습니다.");            
        }
        model.addAttribute("postList", postList);
        return "list";
    }

    @GetMapping("/write")
    public String writePost() {
        return "write";
    }

    @PostMapping("/write")
    public String writePost(@RequestParam String title, @RequestParam String content) {
        postService.writePost(title, content);
        return "redirect:/post/list";
    }

    @GetMapping("/{postId}/modify")
    public String modifyPost() {
        return "write";
    }

    @PostMapping("/{postId}/modify")
    public String modifyPost(@PathVariable("postId") Long postId, 
                        @RequestParam String title, 
                        @RequestParam String content) {
        postService.modifyPost(postId, title, content);
        return "redirect:/post/" + postId;
    }

    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return "redirect:/post/list";
    }

    // TODO: paging, sort, filter, pagination; best, recnet, popular, tag, category, etc.
}
