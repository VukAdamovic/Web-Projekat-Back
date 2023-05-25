package com.example.web_projekat.resources;

import com.example.web_projekat.entities.News;
import com.example.web_projekat.repositories.dto.news.NewsDto;
import com.example.web_projekat.services.NewsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/news")
public class NewsResource {

    @Inject
    private NewsService newsService;

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News createNews(@PathParam("id") int id ,@Valid NewsDto newsDto){
        return newsService.createNews(id ,newsDto);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findNewsById(@PathParam("id") int id) {
        return Response.ok(newsService.findNewsById(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllNews() {
        return Response.ok(newsService.getAllNews()).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNews(@PathParam("id") int id, @Valid NewsDto newsDto){
        return Response.ok(newsService.updateNews(id, newsDto)).build();
    }

    @DELETE
    @Path("/{id}")
    public void deleteNews(@PathParam("id") int id){
        newsService.deleteNewsById(id);
    }

}
