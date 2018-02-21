package com.incture.pmc.services;


import java.util.List;

import javax.ejb.Local;

import com.incture.pmc.dto.RoleOfPmcDto;
import com.incture.pmc.dto.UserWorkloadDetailsDto;
import com.incture.pmc.poadapter.services.UserDetailsDto;
import com.incture.pmc.responseDto.GroupInfoDtoResponse;
import com.incture.pmc.responseDto.RoleInfoResponse;
import com.incture.pmc.responseDto.UserDtoResponse;
import com.incture.pmc.responseDto.UserGroupDtoResponse;

@Local
public interface UserManagementFacadeWsdlConsumerLocal {

	UserDetailsDto getUserDetailsById(String userId);

	GroupInfoDtoResponse getUserGroupsById(String userId);

	RoleInfoResponse getUserRoleByUserId(String userId);

	List<String> getUsersAssignedInGroup(String groupId);

	UserGroupDtoResponse getAllGroups();
	
	RoleInfoResponse getAllRoles();

	UserDtoResponse getAllUsers(String userSearch);

	UserWorkloadDetailsDto getUserInformation(String userId);

	UserDetailsDto getLoggedInUser();

	String getUserEmailById(String userId);

	UserDtoResponse getUserDetailsAssignedInGroup(String groupId);

	List<UserDetailsDto> getUsersByRoleUniqueName(String roleUniqueName);

	
	RoleOfPmcDto getParticularUserRoleByUserId();


	
	
}
