package edu.htl.orderm8.Resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.htl.orderm8.Authentication.Secured;
import edu.htl.orderm8.Authentication.UserPrincipal;
import edu.htl.orderm8.Data.Objects.Bill;
import edu.htl.orderm8.Data.Objects.BillOrderEntriesWrapper;
import edu.htl.orderm8.Data.Objects.OrderEntryProductWrapper;
import edu.htl.orderm8.Data.Objects.TableOrderWrapper;
import edu.htl.orderm8.Data.Objects.TableStatusWrapper;
import edu.htl.orderm8.Service.BillService;
import edu.htl.orderm8.Service.MiscService;

@Path("misc")
public class MiscResource {

	private MiscService miscService = new MiscService();
	
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tableorderwrapper")
	public Response getTableOrderWrapper(@Context SecurityContext securityContext) {
		UserPrincipal up = (UserPrincipal)securityContext.getUserPrincipal();
		
		GenericEntity<List<TableOrderWrapper>> entity = new GenericEntity<List<TableOrderWrapper>>(miscService.getTableOrderWrappers(up.getUser())) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/orderentryproductwrapper")
	public Response getOrderEntryProductWrapper(@Context SecurityContext securityContext) {
		UserPrincipal up = (UserPrincipal)securityContext.getUserPrincipal();
		
		GenericEntity<List<OrderEntryProductWrapper>> entity = new GenericEntity<List<OrderEntryProductWrapper>>(miscService.getOrderEntryProductWrappers(up.getUser())) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();		
	}
	
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/orderentryproductwrapper/{tableid}")
	public Response getOrderEntryProductWrapper(@Context SecurityContext securityContext, 
			@PathParam("tableid") long tableid) {
		UserPrincipal up = (UserPrincipal)securityContext.getUserPrincipal();
		
		GenericEntity<List<OrderEntryProductWrapper>> entity = new GenericEntity<List<OrderEntryProductWrapper>>(miscService.getOrderEntryProductWrappersByTable(up.getUser(), tableid)) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();		
	}
	
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/billorderentrieswrapper")
	public Response getBillORderEntriesWrapper(@Context SecurityContext securityContext) {
		UserPrincipal up = (UserPrincipal)securityContext.getUserPrincipal();
		
		GenericEntity<List<BillOrderEntriesWrapper>> entity = new GenericEntity<List<BillOrderEntriesWrapper>>(miscService.getBillOrderEntriesWrappers(up.getUser())) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();	
	}
	
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tablestatuswrapper")
	public Response getTableStatusWrapper(@Context SecurityContext securityContext) {
		UserPrincipal up = (UserPrincipal)securityContext.getUserPrincipal();
		
		GenericEntity<List<TableStatusWrapper>> entity = new GenericEntity<List<TableStatusWrapper>>(miscService.getTableStatusWrappers(up.getUser())) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();	
	}
}
