package com.incture.pmc.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.incture.pmc.dto.TaskCountDto;
import com.incture.pmc.dto.UserTaskStatusResponseDto;
import com.incture.pmc.dto.UserWorkloadResponseDto;
import com.incture.pmc.services.UserWorkloadFacadeLocal;

@Path("/userload")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserWorkloadRest {

	@EJB
	private UserWorkloadFacadeLocal services;

	@Path("/heatmap")
	@POST
	public UserWorkloadResponseDto getUserWorkLoadHeatMap(com.incture.pmc.dto.UserSearchRequestDto searchRequestDto) {
		return services.getUserWorkLoadHeatMap(searchRequestDto);
	}
	
	@Path("/trend/graph")
	@POST
	public TaskCountDto getUserWorkLoadTrendGraph(com.incture.pmc.dto.UserProcessDetailRequestDto request){
		return services.getUserWorkLoadTrendGraph(request);
	}

	
	
	@Path("/status/graph")
	@POST
	public UserTaskStatusResponseDto getUserWorkLoadTaskStausGraph(com.incture.pmc.dto.UserProcessDetailRequestDto request){
		return services.getUserWorkLoadTaskStausGraph(request);
	}

}
