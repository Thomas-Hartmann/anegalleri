package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.ws.rs.core.Response;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 26, 2016 
 */
public class RestUtil {
    public static Response getErrorResponse(Gson gson, String errormsg, Exception ex){
        JsonObject job = new JsonObject();
        job.addProperty("error", ex.getMessage());
        job.addProperty("msg", errormsg);
        return Response.status(500).entity(gson.toJson(job)).build();
    }
}
