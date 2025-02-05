package com.meohin.meboard.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.meohin.meboard.entity.Reply;
import com.meohin.meboard.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    public Optional<Reply> getReplyList(Long postId) {
        return replyRepository.findById(postId);
    }

    public void writeReply(Long postId, String content) {
        // TODO Auto-generated method stub
    }

    public void writeNestedReply(Long postId, Long replyId, String content) {
        // TODO Auto-generated method stub
    }

    public void modifyReply(Long replyId, String content) {
        // TODO Auto-generated method stub
    }

    public void deleteReply(Long replyId) {
        // TODO Auto-generated method stub
    }

    public void likeReply(Long replyId) {
        // TODO Auto-generated method stub
    }

    

}
