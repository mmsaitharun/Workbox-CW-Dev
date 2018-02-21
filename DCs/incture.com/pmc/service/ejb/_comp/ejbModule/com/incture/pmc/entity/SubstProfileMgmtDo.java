package com.incture.pmc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: RuleManagementDo
 *
 */
@Entity
@Table(name = "SUBST_PROF_MGMT")
public class SubstProfileMgmtDo implements BaseDo, Serializable {

	private static final long serialVersionUID = 1L;

	public SubstProfileMgmtDo() {
		super();
	}

	@Id
	@Column(name = "PROFILE_ID", length = 80)
	private String profileId;

	@Column(name = "USER_ID", length = 50)
	private String userId;

	@Column(name = "PROFILE_NAME", length = 50)
	private String profileName;

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
		return "SubstProfileMgmtDo [profileId=" + profileId + ", userId=" + userId + ", profileName=" + profileName
				+ "]";
	}

	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return profileId;
	}
}
