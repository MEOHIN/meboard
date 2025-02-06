package com.meohin.meboard.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.meohin.meboard.entity.Post;
import com.meohin.meboard.entity.Reply;
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

    public void writeReply(Long postId, String content) {
        Post post = new Post();
        post.setId(postId);

        Reply reply = new Reply();
        reply.setPost(post);
        reply.setContent(content);
        reply.setCreatedAt(LocalDateTime.now());
        reply.setLikeCount(0);

        replyRepository.save(reply);
    }

    public void writeNestedReply(Long postId, Long replyId, String content) {
        // TODO Auto-generated method stub
    }

    public void modifyReply(Long replyId, String content) {
        // TODO Auto-generated method stub
    }

    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
    }

    public void likeReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        reply.setLikeCount(reply.getLikeCount() + 1);
        
        replyRepository.save(reply);
    }

    

}
