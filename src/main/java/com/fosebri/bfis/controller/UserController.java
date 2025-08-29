package com.fosebri.bfis.controller;

import com.fosebri.bfis.dto.user.ChangePasswordRequest;
import com.fosebri.bfis.dto.user.ChangeUserStatusRequest;
import com.fosebri.bfis.dto.user.CreateUserRequest;
import com.fosebri.bfis.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UserController {

    @Inject
    private UserService userService;

    @POST
    @RolesAllowed({"SUPER_ADMIN", "ADMIN", "USER"})
    public Response createUser(@Valid CreateUserRequest request) {
        var userCreated = userService.createUser(request);
        return Response.created(URI.create("/users/" + userCreated.id()))
                .entity(userCreated)
                .build();
    }

    @PATCH
    @Path("/change-password/{id}")
    @RolesAllowed({"SUPER_ADMIN", "ADMIN", "USER"})
    public Response changeUserPassword(@PathParam("id") UUID userId, @Valid ChangePasswordRequest request) {
        userService.changeUserPassword(userId, request);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}/change-status")
    @RolesAllowed({"SUPER_ADMIN", "ADMIN", "USER"})
    public Response changeUserStatus(
            @PathParam("id") UUID userId,
            @Valid ChangeUserStatusRequest request
    ) {
        userService.changeUserStatus(userId, request);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"SUPER_ADMIN", "ADMIN", "USER"})
    public Response getUser(@PathParam("id") UUID userId) {
        return Response.ok(userService.getUser(userId)).build();
    }

    @GET
    @RolesAllowed({"SUPER_ADMIN", "ADMIN", "USER"})
    public Response getUsers() {
        return Response.ok(userService.getUsers()).build();
    }
}
