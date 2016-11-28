/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import data.DBFacade;
import domain.entity.Article;
import domain.entity.Articleversion;
import domain.entity.Image;
import domain.entity.Tag;
import domain.entity.wrappers.ImageWrapper;
import domain.excecption.NoSuchUserException;
import domain.excecption.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk
 */
@Path("image")
public class ImageResource {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    DBFacade dbf = new DBFacade();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ImageResource
     */
    public ImageResource() {
    }

    /**
     * Retrieves representation of an instance of rest.ImageResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("actives")
    public Response getAllActive() {
        List<ImageWrapper> images = dbf.wrapImages(dbf.getAllActiveImages());
        return Response.ok().entity(gson.toJson(images)).build();
//            return "øvbøv";
    }

    /**
     * Retrieves representation of an instance of rest.ImageResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public Response getAll() {
        List<ImageWrapper> images = dbf.wrapImages(dbf.getAllImages());
        return Response.ok().entity(gson.toJson(images)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getImage(@PathParam("id") int id) {
        try {
            ImageWrapper img = dbf.wrapImage(dbf.getImage(id));
            return Response.ok().entity(gson.toJson(img)).build();
        } catch (NonexistentEntityException ex) {
            return RestUtil.getErrorResponse(gson, "Could not get image: " + id, ex);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tag/{tag}")
    public Response getImagesByTag(@PathParam("tag") String tag) {
        List<ImageWrapper> images = dbf.wrapImages(dbf.getImagesByTag(tag));
        return Response.ok().entity(gson.toJson(images)).build();
    }

    /**
     * POST method for updating or creating an instance of ImageResource
     *
     * @param content representation for the resource
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("edit")
    public Response editImage(String content) {
        JsonObject output = new JsonObject();
        output.addProperty("msg", "test");
        Image image = null;
        String errmsg = "";
        try {
            JsonObject input = new JsonParser().parse(content).getAsJsonObject();
            //ImageWrapper image = gson.fromJson(input, ImageWrapper.class);            
            int id = input.get("id").getAsInt();
            String username = input.get("username").getAsString();
            try {
                image = dbf.getImage(id);
            } catch (NonexistentEntityException ex) {
                 return ErrorMsg.createErrorMsg("Image does not exist. Id:"+id, Response.Status.BAD_REQUEST);
            }
            
            //ArrayList<Integer> codes = new ArrayList();
            for (Map.Entry<String, JsonElement> entry : input.entrySet()) {

                switch (entry.getKey()) {
                    case "id": break;
                    case "username": break;
                    case "imagename": image.setImagename(entry.getValue().getAsString()); break;
                    case "imagedesc": image.setImagedesc(entry.getValue().getAsString()); break;
                    case "path": image.setPath(entry.getValue().getAsString()); break;
                    case "year": image.setYear(entry.getValue().getAsInt()); break;
                    case "tags": JsonArray tagsarray = entry.getValue().getAsJsonArray();
                        for (JsonElement element : tagsarray) {
                            String tagname = element.getAsString();
                            Tag tag = dbf.getTagByName(tagname);
                            image.addTag(tag);
                        }
                        break;
                    case "article": 
                        Articleversion av = new Articleversion(dbf.getUserFromName(username), entry.getValue().getAsString());
                        if(image.getArticle() == null)
                            image.setArticle(new Article("Article by "+username));
                        image.getArticle().addVersion(av);
                        break;
                    case "isactive": image.setIsActive(entry.getValue().getAsBoolean()); break;
                    default: 
                        output.addProperty("from", "default");
                        Response.ok(output).build();
                }
               //output.addProperty(entry.getKey(), entry.getValue().getAsString());
            }
        }catch(Exception ex){ 
            return ErrorMsg.createErrorMsg(ex.getMessage(), Response.Status.NOT_FOUND);
        }

//        } catch (JsonSyntaxException ex) {
//            JsonObject errorobj = new JsonObject();
//            //errorobj.addProperty("error", "Some error trying to edit the image");
//            errorobj.addProperty("msg", "JsonSyntaxerror");
//            return Response.status(200).entity(gson.toJson(errorobj)).build();
//        } catch(NonexistentEntityException ex){
//            JsonObject errorobj = new JsonObject();
//            errorobj.addProperty("msg", "NonexistentEntityException");
//            return Response.status(200).entity(gson.toJson(errorobj)).build();
//            
//        }catch( NoSuchUserException ex){
//            JsonObject errorobj = new JsonObject();
//            errorobj.addProperty("msg", "NoSuchUserException");
//            return Response.status(200).entity(gson.toJson(errorobj)).build();
//        }
 //       return Response.status(200).entity(gson.toJson(new ImageWrapper(image))).build();
         output.addProperty("last", "out");
         output.addProperty("error", errmsg);
         ImageWrapper iw = new ImageWrapper(image);
        return Response.status(200).entity(gson.toJson(iw)).build();
    }
}
