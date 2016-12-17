package edu.htl.orderm8.Authentication;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.apache.tomcat.util.http.fileupload.RequestContext;

import edu.htl.orderm8.Data.Objects.User;
import edu.htl.orderm8.Exception.InternalServerErrorException;
import edu.htl.orderm8.Exception.UnauthorizedException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = 
            requestContext.getHeaderString("x-access-token");

        if (authorizationHeader == null ) {
            throw new UnauthorizedException("x-access-token header must be provided");
        }

        String token = authorizationHeader;
        final User u = validateToken(token);
        
        requestContext.setSecurityContext(new SecurityContext() {
			
			@Override
			public boolean isUserInRole(String role) {
				return false;
			}
			
			@Override
			public boolean isSecure() {
				return false;
			}
			
			@Override
			public Principal getUserPrincipal() {
				return new UserPrincipal(u);
			}
			
			@Override
			public String getAuthenticationScheme() {
				return null;
			}
		});
    }

    private User validateToken(String token)  {
    	User u = Authenticator.getInstance().getUser(token);
        if(u == null)
        	throw new UnauthorizedException("token invalid");
        else
        	return u;
    }
}