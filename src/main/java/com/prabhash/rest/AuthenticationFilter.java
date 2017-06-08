package com.prabhash.rest;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
 
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // ... some logic
 
        // Get Acesss token from the Authorization header
        String accessToken = requestContext.getHeaderString("Authorization");
        accessToken = authHeader.replaceFirst("Bearer ", "");
        if (MyAuthService.isUserAuthorized(accessToken)l) {
            // Authorized
            // Create a Security Principal
            requestContext.setSecurityContext(new MySecurityContext(accessToken));
        } else {
            // Unauthorized
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                           .entity("Requires authorization.").build());
        }
    }