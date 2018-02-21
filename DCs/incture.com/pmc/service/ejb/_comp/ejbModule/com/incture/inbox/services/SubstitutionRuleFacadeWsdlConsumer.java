package com.incture.inbox.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;

import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.SubstitutionResponseDto;
import com.incture.pmc.poadapter.services.ResponseDto;
import com.incture.pmc.poadapter.services.SubstitutionManagementFacadeService;
import com.incture.pmc.poadapter.services.SubstitutionProfileManagerFacadeService;
import com.incture.pmc.poadapter.services.SubstitutionRuleDto;
import com.incture.pmc.poadapter.services.UserDto;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;

/**
 * Session Bean implementation class SubstitutionRuleFacadeWsdlConsumer
 */
@Stateless
public class SubstitutionRuleFacadeWsdlConsumer implements SubstitutionRuleFacadeWsdlConsumerLocal {


	public SubstitutionRuleFacadeWsdlConsumer() {
	}

	@WebServiceRef(name = "SubstitutionManagementFacadeService")
	private SubstitutionManagementFacadeService substitutionServices;

	/**
	 * Web service Reference for SubstitutionProfileManagerFacade
	 */
	@WebServiceRef(name = "SubstitutionProfileManagerFacadeService")
	private SubstitutionProfileManagerFacadeService substitutionProfileServices;

	@Override
	public ResponseDto createRule(SubstitutionRuleDto ruleDto){
		return substitutionServices.getSubstitutionManagementFacadePort().createRule(ruleDto);
	}

	@Override
	public ResponseDto deleteRule(SubstitutionRuleDto ruleDto){
		return substitutionServices.getSubstitutionManagementFacadePort().deleteRule(ruleDto);
	}

	@Override
	public ResponseDto updateRule(SubstitutionRuleDto ruleDto){
		return substitutionServices.getSubstitutionManagementFacadePort().updateRule(ruleDto);
	}

	@Override
	public SubstitutionResponseDto getActiveRulesBySubstitute(String  substitutingUser){
		SubstitutionResponseDto response = new SubstitutionResponseDto();
		ResponseMessage respMsg = new ResponseMessage();
		List<SubstitutionRuleDto> substituteList = substitutionServices.getSubstitutionManagementFacadePort().getActiveRulesBySubstitute(substitutingUser);
		if(!ServicesUtil.isEmpty(substituteList)){
			respMsg.setMessage("Substitution Rules Fetched");
			respMsg.setStatus("SUCCESS");
			respMsg.setStatusCode("0");
			response.setRuleDto(substituteList);
			response.setResponse(respMsg);
			return response;
		} else {
			respMsg.setMessage(PMCConstant.NO_RESULT);
			respMsg.setStatus("SUCCESS");
			respMsg.setStatusCode("0");
			response.setRuleDto(null);
			response.setResponse(respMsg);
			return response;
		}
	}

	@Override
	public SubstitutionResponseDto getActiveRulesBySubstitutedUser(String  substitutedUser){
		SubstitutionResponseDto response = new SubstitutionResponseDto();
		ResponseMessage respMsg = new ResponseMessage();
		List<SubstitutionRuleDto> substituteList = substitutionServices.getSubstitutionManagementFacadePort().getActiveRulesBySubstitutedUser(substitutedUser);
		if(!ServicesUtil.isEmpty(substituteList)){
			respMsg.setMessage("Substitution Rules Fetched");
			respMsg.setStatus("SUCCESS");
			respMsg.setStatusCode("0");
			response.setRuleDto(substituteList);
			response.setResponse(respMsg);
			return response;
		} else {
			respMsg.setMessage(PMCConstant.NO_RESULT);
			respMsg.setStatus("SUCCESS");
			respMsg.setStatusCode("0");
			response.setRuleDto(null);
			response.setResponse(respMsg);
			return response;
		}
	}
	@Override
	public SubstitutionResponseDto getInactiveRulesBySubstitute(String  substitutingUser){
		SubstitutionResponseDto response = new SubstitutionResponseDto();
		ResponseMessage respMsg = new ResponseMessage();
		List<SubstitutionRuleDto> substituteList = substitutionServices.getSubstitutionManagementFacadePort().getInactiveRulesBySubstitute(substitutingUser);
		if(!ServicesUtil.isEmpty(substituteList)){
			respMsg.setMessage("Substitution Rules Fetched");
			respMsg.setStatus("SUCCESS");
			respMsg.setStatusCode("0");
			response.setRuleDto(substituteList);
			response.setResponse(respMsg);
			return response;
		} else {
			respMsg.setMessage(PMCConstant.NO_RESULT);
			respMsg.setStatus("SUCCESS");
			respMsg.setStatusCode("0");
			response.setRuleDto(null);
			response.setResponse(respMsg);
			return response;
		}
	}

	@Override
	public SubstitutionResponseDto getInactiveRulesBySubstitutedUser(String  substitutedUser){
		SubstitutionResponseDto response = new SubstitutionResponseDto();
		ResponseMessage respMsg = new ResponseMessage();
		List<SubstitutionRuleDto> substituteList = substitutionServices.getSubstitutionManagementFacadePort().getInactiveRulesBySubstitutedUser(substitutedUser);
		if(!ServicesUtil.isEmpty(substituteList)){
			respMsg.setMessage("Substitution Rules Fetched");
			respMsg.setStatus("SUCCESS");
			respMsg.setStatusCode("0");
			response.setRuleDto(substituteList);
			response.setResponse(respMsg);
			return response;
		} else {
			respMsg.setMessage(PMCConstant.NO_RESULT);
			respMsg.setStatus("SUCCESS");
			respMsg.setStatusCode("0");
			response.setRuleDto(null);
			response.setResponse(respMsg);
			return response;
		}
	}

	@Override
	public SubstitutionResponseDto getRulesBySubstitute(String  user){
		SubstitutionResponseDto response = new SubstitutionResponseDto();
		ResponseMessage respMsg = new ResponseMessage();
		List<SubstitutionRuleDto> substituteList = substitutionServices.getSubstitutionManagementFacadePort().getRulesBySubstitute(user);
		if(!ServicesUtil.isEmpty(substituteList)){
			respMsg.setMessage("Substitution Rules Fetched");
			respMsg.setStatus("SUCCESS");
			respMsg.setStatusCode("0");
			response.setRuleDto(substituteList);
			response.setResponse(respMsg);
			return response;
		} else {
			respMsg.setMessage(PMCConstant.NO_RESULT);
			respMsg.setStatus("SUCCESS");
			respMsg.setStatusCode("0");
			response.setRuleDto(null);
			response.setResponse(respMsg);
			return response;
		}
	}

	@Override
	public SubstitutionResponseDto getRulesBySubstitutedUser(String  user){
		SubstitutionResponseDto response = new SubstitutionResponseDto();
		ResponseMessage respMsg = new ResponseMessage();
		List<SubstitutionRuleDto> substituteList = substitutionServices.getSubstitutionManagementFacadePort().getRulesBySubstitutedUser(user);
		if(!ServicesUtil.isEmpty(substituteList)){
			respMsg.setMessage("Substitution Rules Fetched");
			respMsg.setStatus("SUCCESS");
			respMsg.setStatusCode("0");
			response.setRuleDto(substituteList);
			response.setResponse(respMsg);
			return response;
		} else {
			respMsg.setMessage(PMCConstant.NO_RESULT);
			respMsg.setStatus("SUCCESS");
			respMsg.setStatusCode("0");
			response.setRuleDto(null);
			response.setResponse(respMsg);
			return response;
		}
	}

	@Override
	public List<UserDto> getSubstituteUsers(String  substitutedUser){
		return substitutionServices.getSubstitutionManagementFacadePort().getSubstituteUsers(substitutedUser);
	}

	@Override
	public List<UserDto> getSubstitutedUsers(String  substitutingUserString){
		return substitutionServices.getSubstitutionManagementFacadePort().getSubstitutedUsers(substitutingUserString);
	}

	@Override
	public SubstitutionResponseDto getAllRulesByUser(String  user){
		SubstitutionResponseDto response = new SubstitutionResponseDto();
		ResponseMessage respMsg = new ResponseMessage();
		List<SubstitutionRuleDto> list = substitutionServices.getSubstitutionManagementFacadePort().getRulesBySubstitutedUser(user);
		List<SubstitutionRuleDto> substituteList = substitutionServices.getSubstitutionManagementFacadePort().getRulesBySubstitute(user);
		if(!ServicesUtil.isEmpty(substituteList)){
			if(!ServicesUtil.isEmpty(list)){
				list.addAll(substituteList);
			}else{
				list = substituteList;
			}
			respMsg.setMessage("Substitution Rules Fetched");
		} else if(!ServicesUtil.isEmpty(list)){
			respMsg.setMessage("Substitution Rules Fetched");
		} else if(ServicesUtil.isEmpty(substituteList) && ServicesUtil.isEmpty(list)){
			respMsg.setMessage(PMCConstant.NO_RESULT);
		}
		respMsg.setStatus("SUCCESS");
		respMsg.setStatusCode("0");
		response.setResponse(respMsg);
		response.setRuleDto(list);
		return response;
	}

	@Override
	public ResponseDto deleteAndCreate(SubstitutionRuleDto ruleDto){
		ResponseDto dto = deleteRule(ruleDto);
		if(dto.getStatus().equals("SUCCESS")){
			dto = createRule(ruleDto);
			if (dto.getStatus().equals("SUCCESS")){
				dto.setMessage("Rule Successfully updated");
			}
		}
		return dto;	
	}

}
