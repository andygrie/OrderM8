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

import edu.htl.orderm8.Data.Objects.ProductType;
import edu.htl.orderm8.Service.ProductTypeService;

@Path("producttype")
public class ProductTypeResource {
	
	private ProductTypeService productTypeService = new ProductTypeService();
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductTypes() {
    	GenericEntity<List<ProductType>> entity = new GenericEntity<List<ProductType>>(productTypeService.getProductTypes()) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductType getProductType(@PathParam("id") long id) {
    	return productTypeService.getProductType(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProductType(ProductType pt) {
    	pt = productTypeService.insertProductType(pt);
    	return Response.status(Status.CREATED).entity(pt).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProductType(@PathParam("id") long id, ProductType pt) {
    	productTypeService.updateProductType(id, pt);
    	return Response.status(Status.NO_CONTENT).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProductType(@PathParam("id") long id) {
    	productTypeService.deleteProductType(id);
    	return Response.status(Status.NO_CONTENT).build();
    }
}
