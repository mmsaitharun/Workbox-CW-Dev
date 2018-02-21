package com.incture.pmc.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.incture.pmc.util.SequenceGenerator;

@Path("/sequence")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class SequenceGeneratorRest {

	@GET
	@Path("/number")
	public String getSequenceNo() {
		Integer i = SequenceGenerator.nextNum();
		System.err.println("SequenceGenerator : " + i);
		return String.valueOf(i);
	}
}
