package com.prabhash.rest;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER_KEY= "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX= "Basic ";
	private static final String SECURED_URL_PREFIX = "secured";
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		if(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)){
		List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);

		
			if(authHeader != null && authHeader.size()>0){
				
				String authToken= authHeader.get(0);
				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				String encodedString = Base64.decodeAsString(authToken);
				StringTokenizer st = new StringTokenizer(encodedString, ":");
				String username= st.nextToken();
				String password = st.nextToken();
				
				if("user".equals(username) && "password".equals(password)){
					
					return;
				}
				
				Response authResponse = Response.status(Response.Status.UNAUTHORIZED)
						.entity("User Can't Access this page").build();
				
				requestContext.abortWith(authResponse);
				
			}
				
			}
	}

}
