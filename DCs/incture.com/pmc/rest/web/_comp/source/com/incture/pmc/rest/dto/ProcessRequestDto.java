package com.incture.pmc.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProcessRequestDto {
	private String processId;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	@Override
	public String toString() {
		return "ProcessRequestDto [processId=" + processId + "]";
	}

}
