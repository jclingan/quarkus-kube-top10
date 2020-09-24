package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/person")
public class PersonResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List getPersons() {
        return Person.findAll().list();
    }

}

