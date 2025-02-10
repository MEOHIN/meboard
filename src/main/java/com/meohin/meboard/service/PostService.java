package com.meohin.meboard.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.meohin.meboard.entity.Post;
import com.meohin.meboard.repository.PostRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    
    public List<Post> getPostList() {
        return postRepository.findAll();
    }

    public Optional<Post> getPost(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> searchPost(String keyword) {
        return postRepository.findByTitleContains(keyword);
    }

    public void writePost(String title, String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());
        post.setViewCount(0);
        post.setLikeCount(0);
        post.setReplyCount(0);
        post.setShareCount(0);
        post.setBookmarkCount(0);

        postRepository.save(post);
    }

    public void modifyPost(Long postId, String title, String content) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        post.setTitle(title);
        post.setContent(content);
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);  
    }

    public void likePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        post.setLikeCount(post.getLikeCount()+1);

        postRepository.save(post);
    }
}
