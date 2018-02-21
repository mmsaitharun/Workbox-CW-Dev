package com.incture.pmc.responseDto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.dto.ProcessConfigDto;
import com.incture.pmc.dto.ResponseMessage;
@XmlRootElement
public class ProcessConfigResponse {
	
	private List<ProcessConfigDto> processConfigDtos;
	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	public List<ProcessConfigDto> getProcessConfigDtos() {
		return processConfigDtos;
	}

	public void setProcessConfigDtos(List<ProcessConfigDto> processConfigDtos) {
		this.processConfigDtos = processConfigDtos;
	}

	@Override
	public String toString() {
		return "ProcessConfigResponse [processConfigDtos=" + processConfigDtos + "]";
	}

}
