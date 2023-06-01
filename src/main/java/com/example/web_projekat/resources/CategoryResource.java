package com.example.web_projekat.resources;

import com.example.web_projekat.entities.Category;
import com.example.web_projekat.repositories.dto.category.CategoryDto;
import com.example.web_projekat.services.CategoryService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/categories")
public class CategoryResource {

    @Inject
    private CategoryService categoryService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Category createCategory(@Valid CategoryDto categoryDto){
        return categoryService.createCategory(categoryDto);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCategoryById(@PathParam("id") int id) {
        return Response.ok(categoryService.findCategoryById(id)).build();
    }

    @GET
    @Path("/news/{id}/{numberPage}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllNewsById(@PathParam("id") int id, @PathParam("numberPage") int page) {
        return Response.ok(categoryService.getNewsByCategoryId(id, page)).build();
    }

    @GET
    @Path("/page/{numberPage}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllCategories(@PathParam("numberPage") int page) {
        return Response.ok(categoryService.getAllCategory(page)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllCategories() {
        return Response.ok(categoryService.getAllCategory()).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(@PathParam("id") int id, @Valid CategoryDto categoryDto){
        return Response.ok(categoryService.updateCategory(id, categoryDto)).build();
    }

    @DELETE
    @Path("/{id}")
    public void deleteCategory(@PathParam("id") int id){
        categoryService.deleteCategoryById(id);
    }

}
