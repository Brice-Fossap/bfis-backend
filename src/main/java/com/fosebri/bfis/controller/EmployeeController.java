package com.fosebri.bfis.controller;

import com.fosebri.bfis.dto.employee.ChangeEmployeeStatusRequest;
import com.fosebri.bfis.dto.employee.CreateEmployeeRequest;
import com.fosebri.bfis.dto.employee.UpdateEmployeeRequest;
import com.fosebri.bfis.service.EmployeeService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.UUID;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class EmployeeController {

    @Inject
    private EmployeeService employeeService;

    @POST
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public Response createEmployee(@Valid CreateEmployeeRequest request) {
        var employeeCrested = employeeService.createEmployee(request);
        return Response.created(URI.create("/employees/" + employeeCrested.id()))
                .entity(employeeCrested)
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public Response updateEmployee(
            @PathParam("id") UUID id,
            @Valid UpdateEmployeeRequest request
    ) {
        return Response.ok(employeeService.updateEmployee(id, request)).build();
    }

    @PATCH
    @Path("/{id}/change-status")
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public Response changeStatus(
            @PathParam("id") UUID id,
            @Valid ChangeEmployeeStatusRequest request
    ) {
        employeeService.changeStatus(id, request);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public Response getEmployee(@PathParam("id") UUID id) {
        return Response.ok(employeeService.getEmployee(id)).build();
    }

    @GET
    @RolesAllowed({"SUPER_ADMIN", "ADMIN"})
    public Response getEmployees() {
        return Response.ok(employeeService.getEmployees()).build();
    }
}
