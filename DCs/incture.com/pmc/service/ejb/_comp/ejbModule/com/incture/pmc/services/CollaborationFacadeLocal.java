package com.incture.pmc.services;

import javax.ejb.Local;

import com.incture.pmc.dto.CollaborationDto;
import com.incture.pmc.dto.CollaborationResponseDto;
import com.incture.pmc.dto.NotificationResponseDto;
import com.incture.pmc.dto.ResponseMessage;

@Local
public interface CollaborationFacadeLocal {

	public ResponseMessage createCollaboration(CollaborationDto dto);

	public CollaborationResponseDto getMessageDetails(String processId, String taskId);

	public CollaborationResponseDto getProcessLevelWithTaskLevelMessage(String processId);

	public CollaborationResponseDto getMessageUsingOwnerId();
	
	public NotificationResponseDto getNotification();
}
