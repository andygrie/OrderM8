package edu.htl.orderm8.Resource;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.htl.orderm8.Data.Objects.Bill;
import edu.htl.orderm8.Service.BillService;

@Path("bill")
public class BillResource {
	
	private BillService billService = new BillService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBills() {
		GenericEntity<List<Bill>> entity = new GenericEntity<List<Bill>>(billService.getBills()) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();
	}

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Bill getBill(@PathParam("id") long id) {
    	return billService.getBill(id);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBill() {
    	Bill b = billService.insertBill();
    	return Response.status(Status.CREATED).entity(b).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBill(@PathParam("id") long id) {
    	billService.deleteBill(id);
    	return Response.status(Status.NO_CONTENT).build();
    }
}
