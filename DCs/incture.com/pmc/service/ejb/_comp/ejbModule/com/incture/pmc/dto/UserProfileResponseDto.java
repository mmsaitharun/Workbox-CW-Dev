package com.incture.pmc.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserProfileResponseDto  {

	private ResponseMessage responseMessage;
	private List<SubstProfileMgmtDto> profileList;
	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}
	public List<SubstProfileMgmtDto> getProfileList() {
		return profileList;
	}
	public void setProfileList(List<SubstProfileMgmtDto> profileList) {
		this.profileList = profileList;
	}
	
	
	@Override
	public String toString() {
		return "UserProfileResponseDto [responseMessage=" + responseMessage + ", profileList=" + profileList + "]";
	}
	

	
}