package com.meohin.meboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meohin.meboard.entity.Post;
import com.meohin.meboard.entity.SiteUser;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleContains(String keyword);

    List<Post> findByAuthor(SiteUser currentUser);
}
