package com.incture.pmc.responseDto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.poadapter.services.GroupInfoDto;
@XmlRootElement
public class GroupInfoDtoResponse {

	private List<GroupInfoDto> groupInfoDtos;
	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}


	public List<GroupInfoDto> getGroupInfoDtos() {
		return groupInfoDtos;
	}

	public void setGroupInfoDtos(List<GroupInfoDto> groupInfoDtos) {
		this.groupInfoDtos = groupInfoDtos;
	}

	@Override
	public String toString() {
		return "GroupInfoDtoResponse [groupInfoDtos=" + groupInfoDtos + "]";
	}
	
}
