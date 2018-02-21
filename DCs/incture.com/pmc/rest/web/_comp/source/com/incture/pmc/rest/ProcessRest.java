package com.incture.pmc.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.incture.pmc.dto.ProcessAgeingResponse;
import com.incture.pmc.dto.ProcessDetailsDto;
import com.incture.pmc.dto.ProcessDetailsResponse;
import com.incture.pmc.dto.ProcessEventsDto;
import com.incture.pmc.dto.UserDetailsDto;
import com.incture.pmc.dto.UserProcessDetailRequestDto;
import com.incture.pmc.services.ProcessFacadeLocal;

@Path("/process")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class ProcessRest {

	@EJB
	private ProcessFacadeLocal process;
	
	@POST
	@Path("/by/duration")
	public ProcessDetailsResponse getProcessesByDuration(ProcessDetailsDto processDetailsDto){
		return process.getProcessesByDuration(processDetailsDto);
	} 

	@POST
	@Path("/by/taskowner")
	public ProcessDetailsResponse getProcessesByTaskOwner(UserProcessDetailRequestDto request){
		return process.getProcessesByTaskOwner(request);
	}

	@GET
	@Path("/details/{processId}")
	public ProcessEventsDto getProcessDetailsByInstance(@PathParam("processId") String processId){
		return process.getProcessDetailsByInstance(processId);
	}
	
	@GET
	@Path("/createdBy/{inputString}")
	public List<UserDetailsDto> getCreatedByList(@PathParam("inputString") String inputString){
		return process.getCreatedByList(inputString);
	}
	
	@GET
	@Path("/ageing")
	public ProcessAgeingResponse getProcessAgingNew(@QueryParam("type") String ageingType, @QueryParam("process") String processName){
		return process.getProcessAgeing(ageingType,processName);
	}
}
