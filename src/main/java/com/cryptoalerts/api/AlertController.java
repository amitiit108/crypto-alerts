package com.cryptoalerts.api;

import com.cryptoalerts.dto.AlertResponse;
import com.cryptoalerts.model.Alert;
import com.cryptoalerts.service.AlertService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/alerts")
public class AlertController {
    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAlert(String json) {
        try {
            Gson gson = new Gson();
            Alert alert = gson.fromJson(json, Alert.class);
            if (alert != null) {
                System.out.println("Alert details from request payload:");
                System.out.println(json); // Assuming message is a property
            } else {
                // Handle the case where the alert object is null (e.g., log an error)
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid request payload").build();
            }
            alertService.createAlert(alert);
            AlertResponse response = new AlertResponse("Alert created successfully");
            // Convert the response object to JSON
            String jsonResponse = gson.toJson(response);
            return Response.status(Response.Status.CREATED).entity(jsonResponse).build();
        } catch (JsonSyntaxException e) {
            // Handle JSON parsing error
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid JSON payload").build();
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error occurred while processing the request").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAlerts() {
        List<Alert> alerts = alertService.getAllAlerts();
        String json = new Gson().toJson(alerts);
        return Response.ok(json).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeAlert(@PathParam("id") int id) {
        try {
            alertService.removeAlert(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
