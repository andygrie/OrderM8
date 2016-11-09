package edu.htl.orderm8.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class InternalServerErrorExceptionMapper implements ExceptionMapper<InternalServerErrorException> {

	@Override
	public Response toResponse(InternalServerErrorException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 500);
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
	}

}
