package com.fosebri.bfis.controller;

import com.fosebri.bfis.dto.auth.LoginRequest;
import com.fosebri.bfis.dto.auth.RefreshTokenRequest;
import com.fosebri.bfis.service.AuthService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AuthController {

    @Inject
    private AuthService authService;

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest request) {
        var response = authService.login(request);
        return Response.ok(response).build();
    }

    @POST
    @Path("/refresh-token")
    public Response refreshToken(@Valid RefreshTokenRequest request) {
        var response = authService.refreshToken(request);
        return Response.ok(response).build();
    }
}
