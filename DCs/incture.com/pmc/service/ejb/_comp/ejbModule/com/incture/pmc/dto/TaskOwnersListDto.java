package com.incture.pmc.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.util.EnOperation;
import com.incture.pmc.util.InvalidInputFault;

@XmlRootElement
public class TaskOwnersListDto extends BaseDto{

	private List<TaskOwnersDto> ownersList;
	private ResponseMessage message;


	@Override
	public String toString() {
		return "TaskOwnersListDto [ownersList=" + ownersList + ", message=" + message + "]";
	}

	public List<TaskOwnersDto> getOwnersList() {
		return ownersList;
	}

	public void setOwnersList(List<TaskOwnersDto> ownersList) {
		this.ownersList = ownersList;
	}

	public ResponseMessage getMessage() {
		return message;
	}

	public void setMessage(ResponseMessage message) {
		this.message = message;
	}

	@Override
	public Boolean getValidForUsage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		// TODO Auto-generated method stub
		
	}

}