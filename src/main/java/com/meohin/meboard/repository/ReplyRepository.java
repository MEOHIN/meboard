package com.meohin.meboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meohin.meboard.entity.Reply;
import com.meohin.meboard.entity.SiteUser;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByPostId(Long postId);

    List<Reply> findByAuthor(SiteUser currentUser);
}
