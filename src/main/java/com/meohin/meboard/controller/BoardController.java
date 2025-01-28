package com.meohin.meboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/board")
public class BoardController {
    
    @GetMapping("/list")
    public String list() {
        return "board/list";
    }

    @GetMapping("/write")
    public String write() {
        return "board/write";
    }

    @GetMapping("/search")
    public String search() {
        return "board/search";
    }
    
    // TODO: sort, filter, pagination; best, recnet, popular, tag, category, etc.
}
