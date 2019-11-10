package fr.efrei.se.emapp.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * general handler, serves for verification purposes
 */
@Path("/")
public class GeneralResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Pong !";
    }
}
