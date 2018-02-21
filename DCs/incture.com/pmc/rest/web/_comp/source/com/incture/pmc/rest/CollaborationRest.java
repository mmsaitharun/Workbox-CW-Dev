package com.incture.pmc.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.incture.pmc.dto.CollaborationDto;
import com.incture.pmc.dto.CollaborationResponseDto;
import com.incture.pmc.dto.NotificationResponseDto;
import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.services.CollaborationFacadeLocal;

@Path("/collaboration")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })

public class CollaborationRest {
	
	@EJB
	private CollaborationFacadeLocal collaborationFacadeLocal;

	@Path("/createRecord")
	@POST
	public ResponseMessage createCollaboration(CollaborationDto dto) {
		return collaborationFacadeLocal.createCollaboration(dto);
	}

	@Path("/getMessageDetails")
	@GET
	public CollaborationResponseDto getMessageDetails(@QueryParam("processId") String processId, @QueryParam("taskId") String taskId) {
		return collaborationFacadeLocal.getMessageDetails(processId, taskId);
	}


	@Path("/getProcessWithTaskMessage")
	@GET
	public CollaborationResponseDto getProcessLevelWithTaskLevelMessage(
			@QueryParam("processId") String processId) {
		return collaborationFacadeLocal.getProcessLevelWithTaskLevelMessage(processId);
	}
	
	@Path("/getOwnerMessage")
	@GET
	public CollaborationResponseDto getOwnerMessage() {
		return collaborationFacadeLocal.getMessageUsingOwnerId();
	}
	
	@Path("/getNotification")
	@GET
	public NotificationResponseDto getNotification()
	{
		return collaborationFacadeLocal.getNotification();
	}
	
}
