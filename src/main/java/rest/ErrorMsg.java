package rest;

import com.google.gson.JsonObject;
import javax.ws.rs.core.Response;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Nov 27, 2016 
 */
public class ErrorMsg {
    public static Response createErrorMsg(String msg, Response.Status status){
        JsonObject errorObj = new JsonObject();
        errorObj.addProperty("errormsg", msg);
        Response response = Response.status(status).entity(errorObj).build();
        return response;
    }
}
