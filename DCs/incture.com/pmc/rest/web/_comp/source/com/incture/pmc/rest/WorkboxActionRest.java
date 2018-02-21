package com.incture.pmc.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.incture.inbox.services.WorkBoxActionFacadeWsdlConsumerLocal;
import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.WorkBoxActionDto;
import com.incture.pmc.dto.WorkBoxActionListDto;

@Path("/workboxAction")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class WorkboxActionRest {

	@EJB
	private WorkBoxActionFacadeWsdlConsumerLocal workboxAction;
	
	@POST
	@Path("/claim")
	 public ResponseMessage claimTask(WorkBoxActionDto dto) {
		System.err.println("[PMC][WorkBoxAction][Rest][claim] method invoked");
    	return workboxAction.claimTask(dto);
	}
	
	@POST
	@Path("/release")
    public ResponseMessage release(WorkBoxActionDto dto) {
		System.err.println("[PMC][WorkBoxAction][Rest][release] method invoked");
    	return workboxAction.release(dto);
	}

	@POST
	@Path("/delegate")
    public ResponseMessage delegate(WorkBoxActionDto dto) {
		System.err.println("[PMC][WorkBoxAction][Rest][delegate] method invoked");
    	return workboxAction.delegate(dto);
	}
	@POST
	@Path("/nominate")
    public ResponseMessage nominate(WorkBoxActionDto dto) {
		System.err.println("[PMC][WorkBoxAction][Rest][nominate] method invoked");
    	return workboxAction.nominate(dto);
	}

	@POST
	@Path("/addNote")
    public ResponseMessage addNote(WorkBoxActionDto dto) {
		System.err.println("[PMC][WorkBoxAction][Rest][addNote] method invoked");
    	return workboxAction.addNote(dto);
	}
	
	@POST
	@Path("/complete")
    public ResponseMessage complete(WorkBoxActionListDto dto) {
		System.err.println("[PMC][WorkBoxAction][Rest][complete] method invoked");
    	return workboxAction.complete(dto);
	}

	@POST
	@Path("/claimDelegate")
    public ResponseMessage claimAndDelegate(WorkBoxActionDto dto) {
		System.err.println("[PMC][WorkBoxAction][Rest][claimDelegate] method invoked");
    	return workboxAction.claimAndDelegate(dto);
	}
}
