package com.cryptoalerts.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/health")
public class HealthCheckController {
    @GET
    public String healthCheck() {
        return "Server is up!";
    }
}
