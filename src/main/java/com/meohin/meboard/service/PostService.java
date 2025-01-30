package com.meohin.meboard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.meohin.meboard.entity.Post;
import com.meohin.meboard.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    
    public List<Post> getPostList() {
        return postRepository.findAll();
    }
}
