package edu.htl.orderm8.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {

	@Override
	public Response toResponse(UnauthorizedException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 401);
		
		return Response.status(Status.UNAUTHORIZED).entity(errorMessage).build();
	}

}
