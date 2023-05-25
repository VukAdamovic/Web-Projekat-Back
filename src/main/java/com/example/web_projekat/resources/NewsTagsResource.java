package com.example.web_projekat.resources;

import com.example.web_projekat.entities.NewsTags;
import com.example.web_projekat.services.NewsTagsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/news_tags")
public class NewsTagsResource {

    @Inject
    private NewsTagsService newsTagsService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public NewsTags createNewsTags(@Valid NewsTags newsTags){
        return newsTagsService.createNews_Tags(newsTags);
    }

    @GET
    @Path("/newsId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findNewsTagsByNewsId(@PathParam("id") int id) {
        return Response.ok(newsTagsService.findNewsTagsByNewsId(id)).build();
    }
    @GET
    @Path("/tagsId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findNewsTagsByTagsId(@PathParam("id") int id) {
        return Response.ok(newsTagsService.findNewsTagsByTagId(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllNewsTags() {
        return Response.ok(newsTagsService.getAllNewsTags()).build();
    }


    @DELETE
    @Path("/{id1}/{id2}")
    public void deleteNewsTag(@PathParam("id1") int id1, @PathParam("id2") int id2) {
        newsTagsService.deleteNewsTags(id1, id2);
    }
}
