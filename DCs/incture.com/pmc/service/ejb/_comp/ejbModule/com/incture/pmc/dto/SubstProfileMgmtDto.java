package com.incture.pmc.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.util.EnOperation;
import com.incture.pmc.util.InvalidInputFault;

@XmlRootElement
public class SubstProfileMgmtDto extends BaseDto  {

	private String profileId;
	private String userId;
	private String profileName;
	private List<String> processesSelected;
	
	
	public List<String> getProcessesSelected() {
		return processesSelected;
	}


	public void setProcessesSelected(List<String> processesSelected) {
		this.processesSelected = processesSelected;
	}


	public String getProfileId() {
		return profileId;
	}


	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getProfileName() {
		return profileName;
	}


	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}


	@Override
	public String toString() {
		return "SubstProfileMgmtDto [profileId=" + profileId + ", userId=" + userId + ", profileName=" + profileName
				+ ", processesSelected=" + processesSelected + "]";
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