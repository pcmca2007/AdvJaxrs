package com.prabhash.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.token.OAuthToken;


public class OAuthResource {
	
	
	@Path("google")
	public class GoogleResource {
	    @Context
	    private UriInfo uriInfo;
	 
	    @GET
	    @Produces("text/html")
	    public Response authenticate() {
	        try {
	            OAuthClientRequest request = OAuthClientRequest
	                    .authorizationProvider(OAuthProviderType.GOOGLE)
	                    .setClientId("YOUR_CLIENT_ID_HERE")
	                    .setResponseType("code")
	                    .setScope("openid email https://www.googleapis.com/auth/plus.login")
	                    .setRedirectURI(
	                            UriBuilder.fromUri(uriInfo.getBaseUri())
	                                    .path("oauth2callback").build().toString())
	                    .buildQueryMessage();
	            URI redirect = new URI(request.getLocationUri());
	            return Response.seeOther(redirect).build();
	        } catch (OAuthSystemException e) {
	            throw new WebApplicationException(e);
	        } catch (URISyntaxException e) {
	            throw new WebApplicationException(e);
	        }
	    }
	 
	}
	
	
	@Path("oauth2callback")
	public class GoogleAuthorizationResource {
	    @Context
	    private UriInfo uriInfo;
	 
	    @GET
	    public Response authorize(@QueryParam("code") String code, @QueryParam("state") String state) {
	        // path to redirect after authorization
	        final URI uri = uriInfo.getBaseUriBuilder().path("/index.jsp").build();
	        try {
	            // Request to exchange code for access token and id token
	            OAuthClientRequest request = OAuthClientRequest
	                    .tokenProvider(OAuthProviderType.GOOGLE)
	                    .setCode(code)
	                    .setClientId("YOUR_CLIENT_ID_HERE")
	                    .setClientSecret("YOUR_CLIENT_SECRET_HERE")
	                    .setRedirectURI(UriBuilder.fromUri(uriInfo.getBaseUri())
	                        .path("oauth2callback").build().toString())
	                        .setGrantType(GrantType.AUTHORIZATION_CODE)
	                        .buildBodyMessage();
	 
	            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
	            OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request);
	 
	            // Get the access token from the response
	            OAuthToken accessToken = oAuthResponse.getOAuthToken();
	 
	            // Get the id token from the response
	            String jwtToken = oAuthResponse.getParam("id_token");
	 
	            // Insert code from Step 5. here
	 
	            // Add code to notify application of authenticated user
	        } catch (OAuthSystemException e) {
	            throw new WebApplicationException(e);
	        } catch (OAuthProblemException e) {
	            throw new WebApplicationException(e);
	        }
	 
	        return Response.seeOther(uri).build();
	    }
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response create(@Context SecurityContext sc, MyResource res) {
	    // Creation logic
	    return Response.created(myResourceUri).build();
	}

}
