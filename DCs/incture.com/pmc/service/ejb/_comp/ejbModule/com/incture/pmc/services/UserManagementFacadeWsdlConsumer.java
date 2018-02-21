package com.incture.pmc.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceRef;

import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.RoleOfPmcDto;
import com.incture.pmc.dto.UserWorkloadDetailsDto;
import com.incture.pmc.poadapter.services.GroupInfoDto;
import com.incture.pmc.poadapter.services.RoleInfoDto;
import com.incture.pmc.poadapter.services.UMEUserManagementFacadeService;
import com.incture.pmc.poadapter.services.UserDetailsDto;
import com.incture.pmc.poadapter.services.UserDto;
import com.incture.pmc.poadapter.services.UserGroupDto;
import com.incture.pmc.responseDto.GroupInfoDtoResponse;
import com.incture.pmc.responseDto.RoleInfoResponse;
import com.incture.pmc.responseDto.UserDtoResponse;
import com.incture.pmc.responseDto.UserGroupDtoResponse;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;

/**
 * Session Bean implementation class UserManagementFacadeWsdlConsumer
 */
@WebService(name = "UserManagementFacadeWsdlConsumer", portName = "UserManagementFacadeWsdlConsumerPort", serviceName = "UserManagementFacadeWsdlConsumerService", targetNamespace = "http://incture.com/pmc/services/")
@Stateless
public class UserManagementFacadeWsdlConsumer implements UserManagementFacadeWsdlConsumerLocal {

	public UserManagementFacadeWsdlConsumer() {
	}

	@WebServiceRef(name = "UMEUserManagementFacadeService")
	private UMEUserManagementFacadeService userServices;

	@WebMethod(operationName = "getUserDetailsById", exclude = false)
	@Override
	public UserDetailsDto getUserDetailsById(@WebParam(name = "userId") String userId) {
		return userServices.getUMEUserManagementFacadePort().getUserDetailsByUserId(userId);
	}

	@WebMethod(operationName = "getUserGroupsById", exclude = false)
	@Override
	public GroupInfoDtoResponse getUserGroupsById(@WebParam(name = "userId") String userId) {
		GroupInfoDtoResponse groupInfoDtoResponse = new GroupInfoDtoResponse();
		ResponseMessage responseMessage = new ResponseMessage();
		List<GroupInfoDto> groupInfo = null;
		try {
			groupInfo= userServices.getUMEUserManagementFacadePort().getUserGroupByuserId(userId);
			if(!ServicesUtil.isEmpty(groupInfo)){
				responseMessage.setMessage("User Groups Fetched successfully");
			}
			else{
				responseMessage.setMessage("PMCConstant.NO_RESULT");
			}
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("1");
		} catch (Exception e) {
			responseMessage.setMessage("Failed due to" +e.getMessage());
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("0");
		}
		groupInfoDtoResponse.setResponseMessage(responseMessage);
		groupInfoDtoResponse.setGroupInfoDtos(groupInfo);
		return groupInfoDtoResponse;
	}

	@WebMethod(operationName = "getUserRoleByUserId", exclude = false)
	@Override
	public RoleInfoResponse getUserRoleByUserId(@WebParam(name = "userId") String userId) {
		RoleInfoResponse roleInfoResponse = new RoleInfoResponse();
		List<RoleInfoDto> roleInfoDtos= null;
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			roleInfoDtos= userServices.getUMEUserManagementFacadePort().getUserRoleByuserId(userId);
			if(!ServicesUtil.isEmpty(roleInfoDtos)){
				responseMessage.setMessage("User Groups Fetched successfully");
			}
			else{
				responseMessage.setMessage("PMCConstant.NO_RESULT");
			}
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("1");
		} catch (Exception e) {
			responseMessage.setMessage("Failed due to" +e.getMessage());
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("0");
		}
		roleInfoResponse.setResponseMessage(responseMessage);
		roleInfoResponse.setRoleInfoDtos(roleInfoDtos);
		return roleInfoResponse;
	}
	
	@WebMethod(operationName = "getLoggedInUser", exclude = false)
	@Override
	public UserDetailsDto getLoggedInUser() {
		return userServices.getUMEUserManagementFacadePort().getLoggedInUser();
	}

	@WebMethod(operationName = "getUsersAssignedInGroup", exclude = false)
	@Override
	public List<String> getUsersAssignedInGroup(@WebParam(name = "groupId") String groupId) {
		return userServices.getUMEUserManagementFacadePort().getUsersAssignedInGroup(groupId);
		
	}
	

	@WebMethod(operationName = "getUserDetailsAssignedInGroup", exclude = false)
	@Override
	public UserDtoResponse getUserDetailsAssignedInGroup(@WebParam(name = "groupId") String groupId) {
		
		UserDtoResponse userDtoResponse = new UserDtoResponse();
		ResponseMessage responseMessage = new ResponseMessage();
		List<UserDto> usersList = null;
		try {
			usersList =userServices.getUMEUserManagementFacadePort().getUserDetailsAssignedInGroup(groupId);
			if(!ServicesUtil.isEmpty(usersList)){
				responseMessage.setMessage("User Groups Fetched successfully");
			}
			else{
				responseMessage.setMessage(PMCConstant.NO_RESULT);
			}
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("1");
		} catch (Exception e) {
			responseMessage.setMessage("Failed due to" +e.getMessage());
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("0");
		}
		userDtoResponse.setResponseMessage(responseMessage);
		userDtoResponse.setUserDtos(usersList);
		return userDtoResponse;
	}
		
		
	@WebMethod(operationName = "getUsersByRoleUniqueName", exclude = false)
	@Override
	public List<UserDetailsDto> getUsersByRoleUniqueName(@WebParam(name = "roleUniqueName") String roleUniqueName) {
		return userServices.getUMEUserManagementFacadePort().getUsersByRole(roleUniqueName);
	}

	@WebMethod(operationName = "getAllGroups", exclude = false)
	@Override
	public UserGroupDtoResponse getAllGroups() {
		UserGroupDtoResponse userGroupDtoResponse = new UserGroupDtoResponse();
		ResponseMessage responseMessage = new ResponseMessage();
		List<UserGroupDto> listDtos = null;
		try {
			listDtos = userServices.getUMEUserManagementFacadePort().getAllUserGroup();
			if(!ServicesUtil.isEmpty(listDtos)){
				responseMessage.setMessage("User Groups Fetched successfully");
			}
			else{
				responseMessage.setMessage(PMCConstant.NO_RESULT);
			}
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("1");
		} catch (Exception e) {
			responseMessage.setMessage("Failed due to" +e.getMessage());
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("0");
		}
		userGroupDtoResponse.setResponseMessage(responseMessage);
		userGroupDtoResponse.setUserGroupDtos(listDtos);
		return userGroupDtoResponse;
	}

	

	@WebMethod(operationName = "getAllRoles", exclude = false)
	public RoleInfoResponse getAllRoles() {
		RoleInfoResponse roleInfoResponse = new RoleInfoResponse();
		ResponseMessage responseMessage = new ResponseMessage();
		List<RoleInfoDto> userRoles = null;
		try {
			userRoles = userServices.getUMEUserManagementFacadePort().getAllUserRole();
			if(!ServicesUtil.isEmpty(userRoles)){
				responseMessage.setMessage("User Groups Fetched successfully");
			}
			else{
				responseMessage.setMessage(PMCConstant.NO_RESULT);
			}
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("1");
		} catch (Exception e) {
			responseMessage.setMessage("Failed due to" +e.getMessage());
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("0");
		}
		roleInfoResponse.setResponseMessage(responseMessage);
		roleInfoResponse.setRoleInfoDtos(userRoles);
		return roleInfoResponse;
	}
	

	@WebMethod(operationName = "getAllUsers", exclude = false)
	public UserDtoResponse getAllUsers(@WebParam(name = "userSearch") String userSearch) {
		
		ResponseMessage responseMessage = new ResponseMessage();
		UserDtoResponse userDtoResponse = new UserDtoResponse();
		List<UserDto> usersList = null;
		try {
			usersList = userServices.getUMEUserManagementFacadePort().getAllUsers(userSearch);
			if(!ServicesUtil.isEmpty(usersList)){
				responseMessage.setMessage("User Groups Fetched successfully");
			}
			else{
				responseMessage.setMessage(PMCConstant.NO_RESULT);
			}
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("1");
		} catch (Exception e) {
			responseMessage.setMessage("Failed due to" +e.getMessage());
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("0");
		}
		userDtoResponse.setResponseMessage(responseMessage);
		userDtoResponse.setUserDtos(usersList);
		return userDtoResponse;
	}


	@WebMethod(operationName = "getUserEmailById", exclude = false)
	@Override
	public String getUserEmailById(@WebParam(name = "userId") String userId) {
		return userServices.getUMEUserManagementFacadePort().getUserEmailByuserId(userId);
	}

	@WebMethod(operationName = "getUserInformation", exclude = false)
	@Override
	public UserWorkloadDetailsDto getUserInformation(@WebParam(name = "userId") String userId) {
		UserWorkloadDetailsDto userWorkloadDetailsDto = null;
		if (!ServicesUtil.isEmpty(userId)) {
			userWorkloadDetailsDto = new UserWorkloadDetailsDto();
			UserDetailsDto userDetailsDto = getUserDetailsById(userId);
			if (!ServicesUtil.isEmpty(userDetailsDto)) {

				StringBuffer name = new StringBuffer();
				name = userDetailsDto.getFirstName() == null ? name.append("")
						: name.append(userDetailsDto.getFirstName()).append(" ");
				name = userDetailsDto.getLastName() == null ? name.append("")
						: name.append(userDetailsDto.getLastName());
				userWorkloadDetailsDto.setUserName(name.toString().trim());
				userWorkloadDetailsDto.setUserId(userId);
				userWorkloadDetailsDto.setUserMailId(userDetailsDto.getEmailId());
				userWorkloadDetailsDto.setImage(userDetailsDto.getPhoto());
			}
			List<GroupInfoDto> groupInfoDtos = getUserGroupsById(userId).getGroupInfoDtos();
			if (!ServicesUtil.isEmpty(groupInfoDtos)) {
				StringBuffer groupsName = new StringBuffer();
				for (int i = 0; i < groupInfoDtos.size(); i++) {
					if (i == groupInfoDtos.size() - 1)
						groupsName.append(" ").append(groupInfoDtos.get(i).getGroupUniqName().trim());
					else if (i == 0)
						groupsName.append(groupInfoDtos.get(i).getGroupUniqName().trim()).append(",");
					else
						groupsName.append(" ").append(groupInfoDtos.get(i).getGroupUniqName().trim()).append(",");
				}
				userWorkloadDetailsDto.setApplicationGroupsName(groupsName.toString().trim());
			}
		}
		return userWorkloadDetailsDto;
	}

	@WebMethod(operationName = "getParticularUserRoleByUserId", exclude = false)
	@Override
	public RoleOfPmcDto getParticularUserRoleByUserId() {
		
		
		UserDetailsDto getLoggedInUser=new UserDetailsDto();
		getLoggedInUser=userServices.getUMEUserManagementFacadePort().getLoggedInUser();
		String userId=getLoggedInUser.getUserId();
		RoleOfPmcDto list=new RoleOfPmcDto();
		Map<String,String> roleMap=new HashMap<String,String>();
		List<RoleInfoDto> allRole=new ArrayList<RoleInfoDto>();
		allRole=userServices.getUMEUserManagementFacadePort().getUserRoleByuserId(userId);
		roleMap.put("CW_RL_WB_READ", "false");
		roleMap.put("CW_RL_WB_ADMIN", "false");
		roleMap.put("CW_RL_WB_WM", "false");
		roleMap.put("CW_RL_WB_PT", "false");
		roleMap.put("CW_RL_WB_SLA","false");
		roleMap.put("ZXiMdt.EditMonitor", "false");
		
		for (RoleInfoDto dto : allRole) {
			if(roleMap.containsKey(dto.getRoleUniqName()))
				roleMap.put(dto.getRoleUniqName(), "true");
		}
		list.setRoleMap(roleMap);
		return list;
	}

	

}
