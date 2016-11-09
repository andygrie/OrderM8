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

import edu.htl.orderm8.Data.Objects.Table;
import edu.htl.orderm8.Service.TableService;

@Path("table")
public class TableResource {

	private TableService tableService = new TableService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTables() {
		GenericEntity<List<Table>> entity = new GenericEntity<List<Table>>(tableService.getTables()) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Table geTable(@PathParam("id") long id) {
    	return tableService.getTable(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTable(Table t) {
    	t = tableService.insertTable(t);
    	return Response.status(Status.CREATED).entity(t).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTable(@PathParam("id") long id, Table t) {
    	tableService.updateTable(id, t);
    	return Response.status(Status.NO_CONTENT).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTable(@PathParam("id") long id) {
    	tableService.deleteTable(id);
    	return Response.status(Status.NO_CONTENT).build();
    }
}
