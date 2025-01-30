package com.meohin.meboard.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meohin.meboard.entity.Post;
import com.meohin.meboard.service.PostService;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    
    @GetMapping("/list")
    public String list(Model model) {

        // TODO: get post list from service
        List<Post> postList = postService.getPostList();
        model.addAttribute("postList", postList);

        return "list";
    }

    @GetMapping("/write")
    public String write() {
        return "write";
    }

    @GetMapping("/search")
    public String search() {
        return "redirect:/post/list";
    }

    @GetMapping("/modify")
    public String modify() {
        return "redirect:/post/id";
    }

    @GetMapping("/delete")
    public String delete() {
        return "redirect:/post/id";
    }

    @GetMapping("/like")
    public String like() {
        return "redirect:/post/id";
    }

    @GetMapping("/share")
    public String report() {
        return "redirect:/post/id";
    }
    
    @GetMapping("/reply")
    public String comment() {
        return "redirect:/post/id";
    }
    
    // TODO: paging, sort, filter, pagination; best, recnet, popular, tag, category, etc.
}
