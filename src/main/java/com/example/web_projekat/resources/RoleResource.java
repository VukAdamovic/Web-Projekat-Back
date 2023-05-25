package com.example.web_projekat.resources;

import com.example.web_projekat.entities.Role;
import com.example.web_projekat.repositories.dto.role.RoleDto;
import com.example.web_projekat.services.RoleService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/roles")
public class RoleResource {

    @Inject
    private RoleService roleService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Role createRole(@Valid RoleDto roleDto){
        return roleService.createRole(roleDto);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRoleById(@PathParam("id") int id) {
        return Response.ok(roleService.findRoleById(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllRoles() {
        return Response.ok(roleService.getAllRoles()).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRole(@PathParam("id") int id, @Valid RoleDto roleDto){
        return Response.ok(roleService.updateRole(id, roleDto)).build();
    }

    @DELETE
    @Path("/{id}")
    public void deleteRole(@PathParam("id") int id){
        roleService.deleteRole(id);
    }
}
