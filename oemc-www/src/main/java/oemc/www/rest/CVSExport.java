package oemc.www.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import org.json.JSONArray;

/**
 * REST Web Service
 *
 * @author moroz
 */
@Path("exp")
public class CVSExport {

    @Context
    private UriInfo context;
    
    public CVSExport() {
    }

    @Path("csv")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public JSONArray expCSV(JSONArray content) {
        return content;
    }
}
