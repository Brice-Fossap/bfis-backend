package com.fosebri.bfis.controller;

import com.fosebri.bfis.dto.client.ChangeClientStatusRequest;
import com.fosebri.bfis.dto.client.CreateClientRequest;
import com.fosebri.bfis.dto.client.UpdateClientRequest;
import com.fosebri.bfis.service.ClientService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.UUID;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ClientController {

    @Inject
    private ClientService clientService;

    @POST
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public Response createClient(@Valid CreateClientRequest request) {
        var clientCreated = clientService.createClient(request);
        return Response.created(URI.create("/clients/" + clientCreated.id()))
                .entity(clientCreated)
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public Response UpdateClient(
            @PathParam("id") UUID clientId,
            @Valid UpdateClientRequest request
    ) {
        return Response.ok(clientService.updateClient(clientId, request)).build();
    }

    @PATCH
    @Path("/{id}/change-status")
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public Response changeClientStatus(
            @PathParam("id") UUID clientId,
            @Valid ChangeClientStatusRequest request
    ) {
        clientService.changeStatus(clientId, request);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public Response getClient(@PathParam("id") UUID clientId) {
        return Response.ok(clientService.getClient(clientId)).build();
    }

    @GET
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public Response getClients() {
        return Response.ok(clientService.getClients()).build();
    }
}
