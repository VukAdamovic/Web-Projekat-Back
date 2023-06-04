package com.example.web_projekat.resources;

import com.example.web_projekat.entities.Tags;
import com.example.web_projekat.repositories.dto.tags.TagsDto;
import com.example.web_projekat.services.TagsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tags")
public class TagsResource {

    @Inject
    private TagsService tagsService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Tags createTag(@Valid TagsDto tagsDto){
        return tagsService.createTag(tagsDto);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTagById(@PathParam("id") int id) {
        return Response.ok(tagsService.findTagById(id)).build();
    }

    @GET
    @Path("/{id}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterNewsByTag(@PathParam("id") int id, @PathParam("page") int page) {
        return Response.ok(tagsService.filterByTag(id,page)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllTags() {
        return Response.ok(tagsService.getAllTags()).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTag(@PathParam("id") int id, @Valid TagsDto tagsDto){
        return Response.ok(tagsService.updateTag(id, tagsDto)).build();
    }

    @DELETE
    @Path("/{id}")
    public void deleteTag(@PathParam("id") int id){
        tagsService.deleteTagById(id);
    }
}
