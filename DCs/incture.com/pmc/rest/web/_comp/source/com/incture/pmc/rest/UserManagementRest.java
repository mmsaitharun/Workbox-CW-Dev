package com.incture.pmc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.incture.pmc.dto.RoleOfPmcDto;
import com.incture.pmc.dto.UserWorkloadDetailsDto;
import com.incture.pmc.poadapter.services.UserDetailsDto;
import com.incture.pmc.poadapter.services.UserDto;
import com.incture.pmc.responseDto.GroupInfoDtoResponse;
import com.incture.pmc.responseDto.RoleInfoResponse;
import com.incture.pmc.responseDto.UserDtoResponse;
import com.incture.pmc.responseDto.UserGroupDtoResponse;
import com.incture.pmc.services.UserManagementFacadeWsdlConsumerLocal;

@Path("/user")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserManagementRest {


	@EJB
	UserManagementFacadeWsdlConsumerLocal user; 
	@GET
	@Path("/groups")
	public UserGroupDtoResponse getAllGroups(){
		return user.getAllGroups();
	}

	@GET
	@Path("/roles")
	public RoleInfoResponse getAllRoles(){
		return user.getAllRoles();
	}

	@GET
	@Path("/users/{userSearch}")
	public UserDtoResponse getAllUsers(@PathParam("userSearch") String userSearch)
	{
		return user.getAllUsers(userSearch);
	}
	
	@GET
	@Path("/pmcUserRole")
	public RoleOfPmcDto getParticularUserRoleByUserId(){
		return user.getParticularUserRoleByUserId();
	}
	
	@GET
	@Path("/info/{userId}")
	public UserWorkloadDetailsDto getUserInformation(@PathParam("userId")String userId){
		return user.getUserInformation(userId);
	}

	@GET
	@Path("/login")
	public UserDetailsDto getLoggedInUser(){
		return user.getLoggedInUser();
	}

	@GET
	@Path("/userGroupById/{userId}")
	public GroupInfoDtoResponse getUserGroupsById(@PathParam("userId") String userId)
	{
		return user.getUserGroupsById(userId);
	}
	
	@GET
	@Path("/usersByRole/{roleUniqueName}")
	public List<UserDetailsDto> getUsersByRoleUniqueName(@PathParam("roleUniqueName") String roleUniqueName)
	{
		return user.getUsersByRoleUniqueName(roleUniqueName);
	}
	@GET
	@Path("/userRoleById/{userId}")
	
	public RoleInfoResponse getUserRoleByUserId(@PathParam( "userId") String userId)
	{
		return user.getUserRoleByUserId(userId);

	}

	@GET
	@Path("/getUsersAssignedInGroup/{groupId}")
	public UserDtoResponse getUserDetailsAssignedInGroup(@PathParam( "groupId") String groupId)
	{
		return  user.getUserDetailsAssignedInGroup(groupId);
	}
	
	@GET
	@Path("/getUserIdsAssignedInGroup/{groupId}")
	public List<UserDto> getUsersAssignedInGroup(@PathParam( "groupId") String groupId)
	{
	List<String> stringList =  user.getUsersAssignedInGroup(groupId);
	List<UserDto> userDtoList = new ArrayList<UserDto>(); 
	for(String st : stringList){
	UserDto dto = new UserDto();
	dto.setLoginId(st);
	userDtoList.add(dto);
	}
	return userDtoList;

	}






}
