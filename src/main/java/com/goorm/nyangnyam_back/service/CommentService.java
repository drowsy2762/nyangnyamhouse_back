package com.goorm.nyangnyam_back.service;

import com.goorm.nyangnyam_back.model.BComment;
import com.goorm.nyangnyam_back.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<BComment> getCommentsByBoardId(Integer boardId){
        return commentRepository.findByBoardId(boardId);
    }
    public void saveComment(BComment comment){
        commentRepository.save(comment);
    }
    public void deleteComment(Integer Id){
        commentRepository.deleteById(Id);
    }
}
