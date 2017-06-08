package com.prabhash.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ClientBinding;

import com.prabhash.messenger.model.Message;

public class RestApiClient {

	public static void main(String[] args) {
		
		Client client = ClientBuilder.newClient();
		
		WebTarget basetarget= client.target("http://localhost:8010/Advance-Jax-rs/webapi/");
		WebTarget messageTarget= basetarget.path("messages");
		WebTarget singleMessageTarget=messageTarget.path("{messageId}");
				//Builder builder=target.request();
				//	Response response=builder.get();
		
		Message message= singleMessageTarget.resolveTemplate("messageId", "2")
				.request(MediaType.APPLICATION_JSON)
				.get(Message.class);
		
		//POST
		
		Message newMessage = new Message(2, "Prabhash Mishra", "Prabhash");
		
		Response response= messageTarget.request()
						.post(Entity.json(newMessage));
		
		Message myMessage= response.readEntity(Message.class);
		
		System.out.println(myMessage);

	}

}
