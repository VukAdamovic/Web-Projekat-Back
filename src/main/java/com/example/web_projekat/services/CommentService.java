package com.example.web_projekat.services;

import com.example.web_projekat.entities.Comment;
import com.example.web_projekat.repositories.comment.CommentRepository;
import com.example.web_projekat.repositories.dto.comment.CommentDto;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepository commentRepository;

    public Comment createComment(CommentDto commentDto){
        return commentRepository.createComment(commentDto);
    }

    public List<Comment> getAllComments(int page){
        return commentRepository.getAllComments(page);
    }

    public Comment findCommentById(int id){
        return commentRepository.findCommentById(id);
    }

    public void deleteCommentById(int id){
        commentRepository.deleteCommentById(id);
    }
}
