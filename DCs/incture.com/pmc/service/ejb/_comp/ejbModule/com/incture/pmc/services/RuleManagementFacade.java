package com.incture.pmc.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.incture.pmc.dao.RuleManagementDao;
import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.RuleManagementDto;
import com.incture.pmc.dto.RuleManagementResponseDto;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;

/**
 * Session Bean implementation class ConfigurationFacade
 */
@Stateless
public class RuleManagementFacade implements RuleManagementFacadeLocal {

	@EJB
	EntityManagerProviderLocal em;

	@Override
	public RuleManagementResponseDto getRules(String processName) {
		RuleManagementResponseDto responseDto = new RuleManagementResponseDto();
		ResponseMessage message = new ResponseMessage();
	    RuleManagementDao  dao = new RuleManagementDao(em.getEntityManager());		
		List<RuleManagementDto> rules = dao.getRules(processName);
		if(!ServicesUtil.isEmpty(rules)){
			message.setStatusCode("0");
		}else{
			message.setStatusCode("1");
			message.setMessage(PMCConstant.NO_RESULT);
		}
		
		message.setStatus(PMCConstant.STATUS_SUCCESS);
	
		responseDto.setActionDto(dao.getActions());
		responseDto.setTaskList(dao.getTasks(processName));
		responseDto.setMessage(message);
		responseDto.setRuleManagementDtos(rules);
		return responseDto;
	}

	@Override
	public ResponseMessage onSubmit(List<RuleManagementDto> dtoList) {
		return new RuleManagementDao(em.getEntityManager()).submitRules(dtoList);
	}
	
}
