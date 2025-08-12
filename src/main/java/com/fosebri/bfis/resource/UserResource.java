package com.fosebri.bfis.resource;

import com.fosebri.bfis.dto.ChangePasswordRequest;
import com.fosebri.bfis.dto.ChangeUserStatusRequest;
import com.fosebri.bfis.dto.CreateUserRequest;
import com.fosebri.bfis.service.UserService;
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
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    public Response createUser(@Valid CreateUserRequest request) {
        var userCreated = userService.createUser(request);
        return Response.created(URI.create("/users/" + userCreated.id()))
                .entity(userCreated)
                .build();
    }

    @PATCH
    @Path("/change-password/{id}")
    public Response changeUserPassword(@PathParam("id") UUID userId, @Valid ChangePasswordRequest request) {
        userService.changeUserPassword(userId, request);
        return Response.ok().build();
    }

    @PATCH
    @Path("/change-status/{id}")
    public Response changeUserStatus(
            @PathParam("id") UUID userId,
            @Valid ChangeUserStatusRequest request
    ) {
        userService.changeUserStatus(userId, request);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") UUID userId) {
        return Response.ok(userService.getUser(userId)).build();
    }

    @GET
    public Response getUsers() {
        return Response.ok(userService.getUsers()).build();
    }
}
