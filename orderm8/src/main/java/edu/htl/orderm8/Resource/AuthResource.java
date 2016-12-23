package edu.htl.orderm8.Resource;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.htl.orderm8.Authentication.TokenWrapper;
import edu.htl.orderm8.Service.AuthService;

@Path("auth")
public class AuthResource {
	private AuthService authService = new AuthService();
	
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken(
    		@FormParam("username") String username,
    		@FormParam("password") String password ) {
    	
    	TokenWrapper tokenWrapper = authService.getToken(username, password);
    	System.out.println("User logged in: " + username);
    	System.out.println("Token: " + tokenWrapper.getToken());
    	
    	return Response.status(Status.OK).entity(tokenWrapper).build();
    }
}
