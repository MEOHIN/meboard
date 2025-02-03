package com.meohin.meboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meohin.meboard.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleContains(String keyword);
}
