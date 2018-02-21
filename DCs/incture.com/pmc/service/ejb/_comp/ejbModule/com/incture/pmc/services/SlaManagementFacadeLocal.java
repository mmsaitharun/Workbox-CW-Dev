package com.incture.pmc.services;

import javax.ejb.Local;

import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.SlaListDto;
import com.incture.pmc.responseDto.SlaProcessNamesResponse;

@Local
public interface SlaManagementFacadeLocal {

	SlaProcessNamesResponse getAllProcessNames();

	SlaListDto getSlaDetails(String processName);

	ResponseMessage updateSla(SlaListDto dto);

}
