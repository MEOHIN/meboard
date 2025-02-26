package com.meohin.meboard.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.meohin.meboard.entity.Post;
import com.meohin.meboard.entity.Reply;
import com.meohin.meboard.entity.SiteUser;
import com.meohin.meboard.repository.ReplyRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    public List<Reply> getReplyList(Long postId) {
        return replyRepository.findByPostId(postId);
    }

    public void writeReply(Long postId, String content, SiteUser user) {
        Post post = new Post();
        post.setId(postId);

        Reply reply = new Reply();
        reply.setPost(post);
        reply.setContent(content);
        reply.setCreatedAt(LocalDateTime.now());
        reply.setAuthor(user);

        replyRepository.save(reply);
    }

    public void writeNestedReply(Long parentReplyId, String content, SiteUser user) {
        Reply parentReply = replyRepository.findById(parentReplyId).orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));
        Reply nestedReply = new Reply();
        nestedReply.setContent(content);
        nestedReply.setCreatedAt(LocalDateTime.now());
        nestedReply.setPost(parentReply.getPost());
        nestedReply.setParent(parentReply);  // 부모-자식 관계 설정
        nestedReply.setAuthor(user);

        replyRepository.save(nestedReply);

    }

    public void modifyReply(Long replyId, String content) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));
        reply.setContent(content);
        reply.setUpdatedAt(LocalDateTime.now());

        replyRepository.save(reply);
    }

    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
    }
}
