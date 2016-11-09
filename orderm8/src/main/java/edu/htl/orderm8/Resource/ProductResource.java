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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.MediaType;

import edu.htl.orderm8.Data.Objects.Product;
import edu.htl.orderm8.Service.ProductService;

@Path("product")
public class ProductResource {
	
	private ProductService productService = new ProductService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProducts() {
		GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(productService.getProducts()) {};
        return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();
	}

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProduct(@PathParam("id") long id) {
    	return productService.getProduct(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(Product p) {
    	p = productService.insertProduct(p);
    	return Response.status(Status.CREATED).entity(p).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") long id, Product p) {
    	productService.updateProduct(id, p);
    	return Response.status(Status.NO_CONTENT).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("id") long id) {
    	productService.deleteProduct(id);
    	return Response.status(Status.NO_CONTENT).build();
    }
}
