package com.incture.pmc.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProcessModelList{

	private ResponseMessage responseMessage;
	private List<ProcessModel> processModelList;
	
	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}
	public List<ProcessModel> getProcessModelList() {
		return processModelList;
	}
	public void setProcessModelList(List<ProcessModel> processModelList) {
		this.processModelList = processModelList;
	}
	@Override
	public String toString() {
		return "ProcessModelList [responseMessage=" + responseMessage + ", processModelList=" + processModelList + "]";
	}
	
	
}