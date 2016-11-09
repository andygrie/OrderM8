package edu.htl.orderm8.Resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.htl.orderm8.Data.Objects.User;
import edu.htl.orderm8.Service.UserService;

@Path("user")
public class UserResource {
private UserService userService = new UserService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		GenericEntity<List<User>> entity = new GenericEntity<List<User>>(userService.getUsers()) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();
	}

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") long id) {
    	return userService.getUser(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User u) {
    	u = userService.insertUser(u);
    	return Response.status(Status.CREATED).entity(u).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") long id, User u) {
    	userService.updateUser(id, u);
    	return Response.status(Status.NO_CONTENT).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") long id) {
    	userService.deleteUser(id);
    	return Response.status(Status.NO_CONTENT).build();
    }
}
