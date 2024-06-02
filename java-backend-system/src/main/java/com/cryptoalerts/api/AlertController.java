package com.cryptoalerts.api;

import com.cryptoalerts.dto.AlertResponse;
import com.cryptoalerts.model.Alert;
import com.cryptoalerts.service.AlertService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/alerts")
public class AlertController {
    private final AlertService alertService;
    private final Gson gson = new Gson();

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAlert(String json) {
        try {
            // Input validation
            if (json == null || json.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Request body is empty").build();
            }

            // Parse JSON payload
            Alert alert = gson.fromJson(json, Alert.class);
            if (alert == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid JSON payload").build();
            }

            // Validate alert properties
            if (!isValidAlert(alert)) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid alert properties").build();
            }

            // Create the alert
            alertService.createAlert(alert);
            AlertResponse response = new AlertResponse("Alert created successfully");

            // Convert response object to JSON
            String jsonResponse = gson.toJson(response);
            return Response.status(Response.Status.CREATED).entity(jsonResponse).build();
        } catch (JsonSyntaxException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid JSON payload").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error occurred while processing the request").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAlerts() {
        try {
            List<Alert> alerts = alertService.getAllAlerts();
            String json = gson.toJson(alerts);
            return Response.ok(json).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error occurred while processing the request").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removeAlert(@PathParam("id") int id) {
        alertService.removeAlert(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    private boolean isValidAlert(Alert alert) {
        // Implement validation logic for alert properties
        // Return true if the alert is valid, false otherwise
        return true;
    }
}
