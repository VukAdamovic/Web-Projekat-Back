package com.example.web_projekat.resources;

import com.example.web_projekat.entities.Comment;
import com.example.web_projekat.repositories.dto.comment.CommentDto;
import com.example.web_projekat.services.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Comment createComment(@Valid CommentDto commentDto){
        return commentService.createComment(commentDto);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCommentById(@PathParam("id") int id) {
        return Response.ok(commentService.findCommentById(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllComments() {
        return Response.ok(commentService.getAllComments()).build();
    }

    @DELETE
    @Path("/{id}")
    public void deleteComment(@PathParam("id") int id){
        commentService.deleteCommentById(id);
    }
}
