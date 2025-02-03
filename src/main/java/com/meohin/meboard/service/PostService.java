package com.meohin.meboard.service;

import java.time.LocalDateTime;
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

    public void writePost(String title, String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCreated_at(LocalDateTime.now());
        postRepository.save(post);
    }

    public List<Post> searchPost(String keyword) {
        return postRepository.findByTitleContains(keyword);
    }
}
