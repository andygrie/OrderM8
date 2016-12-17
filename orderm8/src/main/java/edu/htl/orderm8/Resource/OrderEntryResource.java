package edu.htl.orderm8.Resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import edu.htl.orderm8.Authentication.MySecurityContext;
import edu.htl.orderm8.Authentication.Secured;
import edu.htl.orderm8.Authentication.UserPrincipal;
import edu.htl.orderm8.Data.Objects.OrderEntry;
import edu.htl.orderm8.Data.Objects.User;
import edu.htl.orderm8.Service.OrderEntryService;

@Path("orderentry")
public class OrderEntryResource {
	
	private OrderEntryService orderEntryService = new OrderEntryService();
	
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderEntries() {
		GenericEntity<List<OrderEntry>> entity = new GenericEntity<List<OrderEntry>>(orderEntryService.getOrderEntries()) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();
	}

    @GET
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public OrderEntry getOrderEntry(@PathParam("id") long id) {
    	return orderEntryService.getOrderEntry(id);
    }
    
    @POST
	@Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOrderEntry(OrderEntry oe) {
    	oe = orderEntryService.insertOrderEntry(oe);
    	return Response.status(Status.CREATED).entity(oe).build();
    }
    
    @PUT
	@Secured
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrderEntry(@PathParam("id") long id, OrderEntry OrderEntry) {
    	orderEntryService.updateOrderEntry(id, OrderEntry);
    	return Response.status(Status.NO_CONTENT).build();
    }
    
    @DELETE
	@Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrderEntry(@PathParam("id") long id) {
    	orderEntryService.deleteOrderEntry(id);
    	return Response.status(Status.NO_CONTENT).build();
    }
    
    @GET
    @Secured
    @Path("/open")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderEntriesOpen( @Context SecurityContext securityContext, @PathParam("idtable") long idtable) {
		UserPrincipal up = (UserPrincipal)securityContext.getUserPrincipal();
		
		GenericEntity<List<OrderEntry>> entity = new GenericEntity<List<OrderEntry>>(orderEntryService.getOrderEntriesOpen(up.getUser())) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();
    }
    
    @GET
    @Secured
    @Path("/open/{idtable}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderEntriesOpenByTable( @Context SecurityContext securityContext, @PathParam("idtable") long idtable) {
		UserPrincipal up = (UserPrincipal)securityContext.getUserPrincipal();
		
		GenericEntity<List<OrderEntry>> entity = new GenericEntity<List<OrderEntry>>(orderEntryService.getOrderEntriesOpenByTable(up.getUser(), idtable)) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();
    }
}
