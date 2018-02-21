package com.incture.pmc.dto;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProcessMessageDto {

	private List<MessageDto> processOrTaskLevelMessageDto;
	private String eventId;
	private Map<String,MessageDto> mapOfProcess;
	
	public void setProcessOrTaskLevelMessageDto(List<MessageDto> processOrTaskLevelMessageDto) {
		this.processOrTaskLevelMessageDto = processOrTaskLevelMessageDto;
	}
	public List<MessageDto> getProcessOrTaskLevelMessageDto() {
		return processOrTaskLevelMessageDto;
	}
	public List<MessageDto> getProcessOrTtaskLevelMessageDto() {
		return processOrTaskLevelMessageDto;
	}
	public void setProcessOrTtaskLevelMessageDto(List<MessageDto> processOrTtaskLevelMessageDto) {
		this.processOrTaskLevelMessageDto = processOrTtaskLevelMessageDto;
	}
	
	
	public Map<String, MessageDto> getMapOfProcess() {
		return mapOfProcess;
	}
	public void setMapOfProcess(Map<String, MessageDto> mapOfProcess) {
		this.mapOfProcess = mapOfProcess;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	
	
	@Override
	public String toString() {
		return "ProcessMessageDto [processOrTaskLevelMessageDto=" + processOrTaskLevelMessageDto + ", eventId="
				+ eventId + ", mapOfProcess=" + mapOfProcess + "]";
	}
	
	
	
	
	
	
	
	
}
