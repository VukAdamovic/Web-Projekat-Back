package com.example.web_projekat.repositories.comment;

import com.example.web_projekat.entities.Comment;
import com.example.web_projekat.repositories.dto.comment.CommentDto;

import java.util.List;

public interface CommentRepository {

    Comment createComment(CommentDto commentDto);

    List<Comment> getAllComments(int page);

    Comment findCommentById(int id);

    void deleteCommentById(int id);
}
