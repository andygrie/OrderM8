package edu.htl.orderm8.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {

	@Override
	public Response toResponse(ConflictException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 409);
		
		return Response.status(Status.CONFLICT).entity(errorMessage).build();
	}
	
}
