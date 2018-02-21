package com.incture.pmc.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SubstitutionProfileInputDto {
	private String defaultName;
	private String profileKey;
	private List<String> taskModelIds;
	private String profileId;

	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public String getProfileKey() {
		return profileKey;
	}

	public void setProfileKey(String profileKey) {
		this.profileKey = profileKey;
	}

	public List<String> getTaskModelIds() {
		return taskModelIds;
	}

	public void setTaskModelIds(List<String> taskModelIds) {
		this.taskModelIds = taskModelIds;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	@Override
	public String toString() {
		return "SubstitutionProfileInputDto [defaultName=" + defaultName + ", profileKey=" + profileKey
				+ ", taskModelIds=" + taskModelIds + ", profileId=" + profileId + "]";
	}

}
