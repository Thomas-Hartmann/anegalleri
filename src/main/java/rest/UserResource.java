/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import data.DBFacade;
import domain.entity.User;
import domain.entity.wrappers.UserWrapper;
import domain.excecption.NoSuchUserException;
import domain.excecption.NonexistentEntityException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import securtity.Secret;

/**
 * REST Web Service
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk
 */
@Path("user")
public class UserResource {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    DBFacade dbf = new DBFacade();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public UserResource() {
    }

    /**
     * Retrieves representation of an instance of rest.UserResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        String result;
        try {
            UserWrapper user = new UserWrapper(dbf.getUser(1));
            result = gson.toJson(user);
        } catch (NoSuchUserException ex) {
            JsonObject job = new JsonObject();
            job.addProperty("msg", "Error in user");
            result = gson.toJson(job);
        }
        return result;
    }

    /**
     * PUT method for updating or creating an instance of UserResource
     *
     * @param content representation for the resource
     */
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createUser(String content) {
            JsonObject job = new JsonObject();
            String username = null;
        try {
            JsonObject input = new JsonParser().parse(content).getAsJsonObject();
            User user = gson.fromJson(input, User.class);
            dbf.createUser(user);
            username = user.getUsername();
//        String username = input.get("username").getAsString();
//        String passsword = input.get("password").getAsString();
//        JsonArray roles = input.get("roles").getAsJsonArray();
            job.addProperty("id", user.getId());
            job.addProperty("username", user.getUsername());
        } catch (NonexistentEntityException ex) {
            job.addProperty("errormsg", ex.getMessage());
            return gson.toJson(job);
        }
        job.addProperty("succes", "succesfully created new user: "+username);
        return gson.toJson(job);
    }
//
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(String content){
        try {
            JsonObject input = new JsonParser().parse(content).getAsJsonObject();
            JsonObject output = new JsonObject();
            User user = gson.fromJson(input, User.class);
            List<String> roles;
            if((roles = dbf.authenticate(user.getUsername(), user.getPassword()))!=null){
                String token = issueToken(user.getUsername(), roles);
                output.addProperty("username", user.getUsername());
                output.addProperty("token", token);
            }
                return Response.status(200).entity(gson.toJson(output)).build();
        }catch(Exception e){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    private String issueToken(String subject, List<String> roles) throws JOSEException {
    StringBuilder res = new StringBuilder();
    for (String string : roles) {
      res.append(string);
      res.append(",");
    }
    String rolesAsString = res.length() > 0 ? res.substring(0, res.length() - 1) : "";
    String issuer = "Thomas Hartmann";
    // Generate random 256-bit (32-byte) shared secret
    SecureRandom random = new SecureRandom();
    
    Secret.SHARED_SECRET = new byte[32];
    random.nextBytes(Secret.SHARED_SECRET);

    JWSSigner signer = new MACSigner(Secret.SHARED_SECRET);
    Date date = new Date();

    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .subject(subject)
            .claim("username", subject)
            .claim("roles", roles)
            .claim("issuer", issuer)
            .issueTime(date)
            .expirationTime(new Date(date.getTime() + 1000 * 60 * 60))
            .build();
    SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
    signedJWT.sign(signer);
    return signedJWT.serialize();
  }
    
//    @POST
//    @Path("login")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String authUser(String content) {
//        JsonObject response = new JsonObject();
//        try {
//            JsonObject job = new JsonObject();
//            JsonObject input = new JsonParser().parse(content).getAsJsonObject();
//            String username = input.get("username").getAsString();
//            String password = input.get("password").getAsString();
//            List<String> roles = dbf.authenticate(username, password);
//            String result = gson.toJson(roles);
//            return result;
//        } catch (NotAuthenticatedException ex) {
//            response.addProperty("errormsg", "Not authenticated");
//            return gson.toJson(response);
//        }
//    }
}
