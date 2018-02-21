package com.incture.pmc.services;

import java.util.List;

import javax.ejb.Local;

import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.RuleManagementDto;
import com.incture.pmc.dto.RuleManagementResponseDto;

@Local
public interface RuleManagementFacadeLocal {

	RuleManagementResponseDto getRules(String processName);

	ResponseMessage onSubmit(List<RuleManagementDto> dtoList);

}
