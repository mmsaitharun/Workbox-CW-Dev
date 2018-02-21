package com.incture.pmc.services;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;

import com.incture.pmc.dao.CollaborationDao;
import com.incture.pmc.dto.CollaborationDto;
import com.incture.pmc.dto.CollaborationMessagesDto;
import com.incture.pmc.dto.CollaborationNotificationDto;
import com.incture.pmc.dto.CollaborationResponseDto;
import com.incture.pmc.dto.NotificationResponseDto;
import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.poadapter.services.UMEUserManagementFacadeService;
import com.incture.pmc.poadapter.services.UserDetailsDto;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;

@Stateless
public class CollaborationFacade implements CollaborationFacadeLocal {

	@EJB
	EntityManagerProviderLocal em;

	@WebServiceRef(name = "UMEUserManagementFacadeService")
	private UMEUserManagementFacadeService userServices;

	public CollaborationFacade() {
	}
	
	CollaborationResponseDto collaborationMessageDto;
	ResponseMessage responseDto;

	public ResponseMessage createCollaboration(CollaborationDto dto) {
		
		responseDto = new ResponseMessage();
		UserDetailsDto getLoggedInUser = userServices.getUMEUserManagementFacadePort().getLoggedInUser();
		
		dto.setCreatedAt(new Date());
		dto.setUserId(getLoggedInUser.getUserId());
		dto.setUserDisplayName(getLoggedInUser.getDisplayName());
		
		if (!ServicesUtil.isEmpty(dto.getMessage()) && !ServicesUtil.isEmpty(dto.getProcessId())) {
			if (new CollaborationDao(em.getEntityManager()).createCollaborationDetail(dto).equals("SUCCESS")) {
				responseDto.setMessage("Created Successfully");
				responseDto.setStatus("SUCCESS");
				responseDto.setStatusCode("0");
				return responseDto;
			} 
		} else {
			responseDto.setMessage("Mandatory Fields are Empty");
		}
		responseDto.setStatus("FAILURE");
		responseDto.setStatusCode("1");
		return responseDto;
	}

	public CollaborationResponseDto getMessageDetails( String processId, String taskId) {
		collaborationMessageDto = new CollaborationResponseDto();
		responseDto = new ResponseMessage();
		responseDto.setStatus("FAILURE");
		responseDto.setStatusCode("1");
		try{
			List<CollaborationMessagesDto>	dto = new CollaborationDao(em.getEntityManager()).getMessageDetails(processId,taskId);
			if (!ServicesUtil.isEmpty(dto)) {
				collaborationMessageDto.setResponseDtos(dto);
				responseDto.setMessage("Data fetched Successfully");
			} else {
				responseDto.setMessage(PMCConstant.NO_RESULT);
			}
			responseDto.setStatus("SUCCESS");
			responseDto.setStatusCode("0");
		}
		catch(Exception e){
			System.err.println("[PMC][CollaborationFacade][getMessageDetails][error]"+e.getMessage());
			responseDto.setMessage("Fetching data failed due to " + e.getMessage());
		}
		collaborationMessageDto.setResponseMessage(responseDto);
		return collaborationMessageDto;
	}

	public CollaborationResponseDto getProcessLevelWithTaskLevelMessage(String processId) {
		collaborationMessageDto = new CollaborationResponseDto();
		responseDto = new ResponseMessage();
		responseDto.setStatus("FAILURE");
		responseDto.setStatusCode("1");
		try{
			List<CollaborationMessagesDto>	dto = new CollaborationDao(em.getEntityManager()).getAllDetailsOfProcessWithTask(processId);
			if (!ServicesUtil.isEmpty(dto)) {
				collaborationMessageDto.setResponseDtos(dto);
				responseDto.setMessage("Data fetched Successfully");
			} else {
				responseDto.setMessage(PMCConstant.NO_RESULT);
			}
			responseDto.setStatus("SUCCESS");
			responseDto.setStatusCode("0");
		}
		catch(Exception e){
			System.err.println("[PMC][CollaborationFacade][getProcessLevelWithTaskLevelMessage][error]"+e.getMessage());
			responseDto.setMessage("Fetching data failed due to " + e.getMessage());
		}
		collaborationMessageDto.setResponseMessage(responseDto);
		return collaborationMessageDto;
	}

	public CollaborationResponseDto getMessageUsingOwnerId() {
		collaborationMessageDto = new CollaborationResponseDto();
		responseDto = new ResponseMessage();
		responseDto.setStatus("FAILURE");
		responseDto.setStatusCode("1");
		try{
			List<CollaborationMessagesDto>	dto = new CollaborationDao(em.getEntityManager()).getMessageOfOwner(userServices.getUMEUserManagementFacadePort().getLoggedInUser().getUserId());
			if (!ServicesUtil.isEmpty(dto)) {
				collaborationMessageDto.setResponseDtos(dto);
				responseDto.setMessage("Data fetched Successfully");
			} else {
				responseDto.setMessage(PMCConstant.NO_RESULT);
			}
			responseDto.setStatus("SUCCESS");
			responseDto.setStatusCode("0");
		}
		catch(Exception e){
			System.err.println("[PMC][CollaborationFacade][getMessageUsingOwnerId][error]"+e.getMessage());
			responseDto.setMessage("Fetching data failed due to " + e.getMessage());
		}
		collaborationMessageDto.setResponseMessage(responseDto);
		return collaborationMessageDto;
	}

	public NotificationResponseDto getNotification() {
		NotificationResponseDto dto = new NotificationResponseDto();
		responseDto = new ResponseMessage();
		responseDto.setStatus("FAILURE");
		responseDto.setStatusCode("1");
		try{
			List<CollaborationNotificationDto> dtos = new CollaborationDao(em.getEntityManager()).getNotificationForOwner(userServices.getUMEUserManagementFacadePort().getLoggedInUser().getUserId());
			if (!ServicesUtil.isEmpty(dtos)) {
				dto.setResponseDto(dtos);
				responseDto.setMessage("Notifications fetched Successfully");
			} else {
				responseDto.setMessage(PMCConstant.NO_RESULT);
			}
			responseDto.setStatus("SUCCESS");
			responseDto.setStatusCode("0");
		}
		catch(Exception e){
			System.err.println("[PMC][CollaborationFacade][getNotification][error]"+e.getMessage());
			responseDto.setMessage("Fetching notification failed due to " + e.getMessage());
		}
		dto.setResponseMessage(responseDto);
		return dto;

	}
}
