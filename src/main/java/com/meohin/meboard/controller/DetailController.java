package com.meohin.meboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/detail")
public class DetailController {
    
    
    @GetMapping("/modify")
    public String modify() {
        return "board/modify";
    }

    @GetMapping("/delete")
    public String delete() {
        return "board/delete";
    }

    @GetMapping("/like")
    public String like() {
        return "board/like";
    }

    @GetMapping("/dislike")
    public String dislike() {
        return "board/dislike";
    }

    @GetMapping("/share")
    public String report() {
        return "board/share";
    }
    
    @GetMapping("/reply")
    public String comment() {
        return "board/reply";
    }
}
