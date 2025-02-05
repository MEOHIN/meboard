package com.meohin.meboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.meohin.meboard.service.ReplyService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post/{postId}/reply")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping
    public String write(@PathVariable Long postId,
                       @RequestParam String content) {
        replyService.writeReply(postId, content);
        return "redirect:/post/" + postId;
    }
    
    @PostMapping("/{replyId}/reply")
    public String writeNestedReply(@PathVariable Long postId,
                                 @PathVariable Long replyId,
                                 @RequestParam String content) {
        replyService.writeNestedReply(postId, replyId, content);
        return "redirect:/post/" + postId;
    }

    @PostMapping("/{replyId}/modify")
    public String modify(@PathVariable Long postId,
                        @PathVariable Long replyId,
                        @RequestParam String content) {
        replyService.modifyReply(replyId, content);
        return "redirect:/post/" + postId;
    }

    @PostMapping("/{replyId}/delete")
    public String delete(@PathVariable Long postId,
                        @PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return "redirect:/post/" + postId;
    }

    @PostMapping("/{replyId}/like")
    public String like(@PathVariable Long postId,
                      @PathVariable Long replyId) {
        replyService.likeReply(replyId);
        return "redirect:/post/" + postId;
    }
}