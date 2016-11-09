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

import edu.htl.orderm8.Data.Objects.OrderEntry;
import edu.htl.orderm8.Service.OrderEntryService;

@Path("orderentry")
public class OrderEntryResource {
	
	private OrderEntryService orderEntryService = new OrderEntryService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderEntries() {
		GenericEntity<List<OrderEntry>> entity = new GenericEntity<List<OrderEntry>>(orderEntryService.getOrderEntries()) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();
	}

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public OrderEntry getOrderEntry(@PathParam("id") long id) {
    	return orderEntryService.getOrderEntry(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOrderEntry(OrderEntry oe) {
    	oe = orderEntryService.insertOrderEntry(oe);
    	return Response.status(Status.CREATED).entity(oe).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrderEntry(@PathParam("id") long id, OrderEntry OrderEntry) {
    	orderEntryService.updateOrderEntry(id, OrderEntry);
    	return Response.status(Status.NO_CONTENT).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrderEntry(@PathParam("id") long id) {
    	orderEntryService.deleteOrderEntry(id);
    	return Response.status(Status.NO_CONTENT).build();
    }
}
