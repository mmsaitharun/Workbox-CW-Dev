//package com.incture.pmc.rest;
//
//import javax.ejb.EJB;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//import com.incture.pmc.poc.services.PrincipalPropagationDto;
//import com.incture.pmc.poc.services.PrincipalPropagationLocal;
//
//@Path("/adapter")
//@Produces({ MediaType.APPLICATION_JSON })
//@Consumes({ MediaType.APPLICATION_JSON })
//public class PrincipalPropagationRest {
//	
//	@EJB
//	private PrincipalPropagationLocal service;
//
//	@POST
//	@Path("/principalPropagation")
//	public void doPostPropagation(PrincipalPropagationDto propagationDto) {
//		service.doPost(propagationDto);
//	}
//
//}
