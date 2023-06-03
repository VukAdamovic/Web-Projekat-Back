package com.example.web_projekat.resources;

import com.example.web_projekat.entities.User;
import com.example.web_projekat.repositories.dto.requests.LoginRequest;
import com.example.web_projekat.repositories.dto.user.UserCreateDto;
import com.example.web_projekat.repositories.dto.user.UserUpdateDto;
import com.example.web_projekat.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/users")
public class UserResource {
    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest) {
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (jwt == null) {
            response.put("message", "These credentials do not match our records OR your status is forbidden");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);

        return Response.ok(response).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(@Valid UserCreateDto userCreateDto){
        return userService.createUser(userCreateDto);
    }

    @GET
    @Path("/getUser/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserById(@PathParam("id") int id) {
        return Response.ok(userService.findUserById(id)).build();
    }

    @GET
    @Path("/page/{numberPage}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllUsers(@PathParam("numberPage") int page) {
        return Response.ok(userService.getAllUser(page)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, @Valid UserUpdateDto userUpdateDto){
        return Response.ok(userService.updateUser(id, userUpdateDto)).build();
    }

    @PUT
    @Path("/status/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeActivation(@PathParam("id") int id){
        return Response.ok(userService.changeActivation(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") int id){
        userService.deleteUserById(id);
    }
}
