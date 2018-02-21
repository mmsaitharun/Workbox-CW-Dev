package com.incture.pmc.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.RuleManagementDto;
import com.incture.pmc.dto.RuleManagementResponseDto;
import com.incture.pmc.services.RuleManagementFacadeLocal;


@Path("/rules")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class RulesRest {

	@EJB
	private RuleManagementFacadeLocal rule;
	
	@GET
	@Path("/details/{processName}")
    public RuleManagementResponseDto getRules(@PathParam("processName") String processName) {
		
		return rule.getRules(processName);
	}
	
	@POST
	@Path("/updateRules")
	public ResponseMessage submit(List<RuleManagementDto> dtoList){
		return rule.onSubmit(dtoList);
	}
	
}
