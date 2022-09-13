package net.jmesnil.demo;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import net.jmesnil.demo.service.PowerService;

@Path("/")
public class PowerOfNEndpoint {

    private static final int N = 2;

    @Inject
    private PowerService service;

    @GET
    @Produces("text/plain")
    public Response doGet(@QueryParam("value") long value) {
        long response = service.powerOf(value, N);
        return Response.ok(response).build();
    }
}
