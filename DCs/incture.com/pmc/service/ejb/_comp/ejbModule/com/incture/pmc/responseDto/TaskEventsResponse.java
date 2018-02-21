package com.incture.pmc.responseDto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.TaskEventsDto;
@XmlRootElement
public class TaskEventsResponse {
	
	private List<TaskEventsDto> taskEventDtos;
	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage2) {
		this.responseMessage = responseMessage2;
	}

	public List<TaskEventsDto> getTaskEventDtos() {
		return taskEventDtos;
	}

	public void setTaskEventDtos(List<TaskEventsDto> taskEventDtos) {
		this.taskEventDtos = taskEventDtos;
	}

	@Override
	public String toString() {
		return "TaskEventsResponse [taskEventDtos=" + taskEventDtos + "]";
	}

}
