package com.incture.pmc.responseDto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.poadapter.services.UserDetailsDto;
@XmlRootElement
public class UserDetailsResponse {

	private List<UserDetailsDto> userDetailsDtos;
	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	public List<UserDetailsDto> getUserDetailsDtos() {
		return userDetailsDtos;
	}

	public void setUserDetailsDtos(List<UserDetailsDto> userDetailsDtos) {
		this.userDetailsDtos = userDetailsDtos;
	}

	@Override
	public String toString() {
		return "UserDetailsResponse [userDetailsDtos=" + userDetailsDtos + ", responseMessage=" + responseMessage + "]";
	}

}
