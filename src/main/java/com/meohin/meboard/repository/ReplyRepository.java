package com.meohin.meboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meohin.meboard.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
