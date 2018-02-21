package com.incture.pmc.responseDto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.poadapter.services.RoleInfoDto;
@XmlRootElement
public class RoleInfoResponse {

	private List<RoleInfoDto> roleInfoDtos;
	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}


	public List<RoleInfoDto> getRoleInfoDtos() {
		return roleInfoDtos;
	}

	public void setRoleInfoDtos(List<RoleInfoDto> roleInfoDtos) {
		this.roleInfoDtos = roleInfoDtos;
	}

	@Override
	public String toString() {
		return "RoleInfoResponse [roleInfoDtos=" + roleInfoDtos + ", responseMessage=" + responseMessage + "]";
	}

}
