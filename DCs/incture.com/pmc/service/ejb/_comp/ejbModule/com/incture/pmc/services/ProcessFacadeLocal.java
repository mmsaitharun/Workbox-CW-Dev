package com.incture.pmc.services;

import java.util.List;

import javax.ejb.Local;

import com.incture.pmc.dto.ProcessAgeingResponse;
import com.incture.pmc.dto.ProcessDetailsDto;
import com.incture.pmc.dto.ProcessDetailsResponse;
import com.incture.pmc.dto.ProcessEventsDto;
import com.incture.pmc.dto.UserDetailsDto;
import com.incture.pmc.dto.UserProcessDetailRequestDto;

@Local
public interface ProcessFacadeLocal {

	// List<AgingGraphDto> getProcessAgeingGraph(String graphTrendType);
	//
	// AgingResponseDto getProcessAgingTable(String ageingType);

	ProcessDetailsResponse getProcessesByDuration(ProcessDetailsDto processDetailsDto);

	ProcessEventsDto getProcessDetailsByInstance(String processId);

	ProcessDetailsResponse getProcessesByTaskOwner(UserProcessDetailRequestDto request);

	ProcessAgeingResponse getProcessAgeing(String ageingType, String processName);

	List<UserDetailsDto> getCreatedByList(String inputValue);

	// byte[] generateExcelByDuration(ProcessDetailsDto processDetailsDto);

}
