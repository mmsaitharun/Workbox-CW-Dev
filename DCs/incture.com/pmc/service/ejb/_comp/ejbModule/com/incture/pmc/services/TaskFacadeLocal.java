package com.incture.pmc.services;

import javax.ejb.Local;

import com.incture.pmc.dto.ManageTasksRequestDto;
import com.incture.pmc.dto.ManageTasksResponseDto;
import com.incture.pmc.dto.TaskAgeingResponse;
import com.incture.pmc.dto.TaskOwnersListDto;
import com.incture.pmc.dto.WorkBoxActionDto;
import com.incture.pmc.responseDto.TaskEventsResponse;

@Local
public interface TaskFacadeLocal {

	TaskEventsResponse getTaskDetailsByProcessInstance(String processId);
	
	ManageTasksResponseDto getTasksByUserAndDuration(ManageTasksRequestDto request);

	TaskOwnersListDto getTaskOwners(WorkBoxActionDto dto);

	TaskAgeingResponse getTaskAgeing(String processName, String userGroup, String status, String requestId, String labelValue);

}
