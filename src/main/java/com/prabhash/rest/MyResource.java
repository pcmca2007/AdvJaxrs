package com.prabhash.rest;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("test")
public class MyResource {
	
	//@PathParam("pathParam") private String pathParamExample;
	//@QueryParam("query") private String queryParamexample;
	
	int count=0;
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Date testMethod(){
		
		//count=count+1;
		return Calendar.getInstance().getTime();
		//return "Prabhash mishra this application runs"+count+""+"Times"+"PathparamValue:"+pathParamExample+" "+"QueryParamValue:"+queryParamexample;
	}

}
