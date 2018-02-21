package com.incture.pmc.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestIdListDto{

	private String eventId;
	private String requestId;

	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	@Override
	public String toString() {
		return "RequestIdListDto [eventId=" + eventId + ", requestId=" + requestId + "]";
	}
	
}
