package com.incture.pmc.services;

import java.util.List;

import javax.ejb.Local;

import com.incture.pmc.dto.ProcessConfigDto;
import com.incture.pmc.dto.ProcessListDto;
import com.incture.pmc.dto.ReportAgingDto;
import com.incture.pmc.responseDto.ProcessConfigResponse;
import com.incture.pmc.responseDto.WorkloadRangeResponse;

@Local
public interface ConfigurationFacadeLocal {

	ProcessListDto getAllProcessNames();

	ProcessConfigResponse getAllBusinessLabels();

	ProcessConfigDto getBusinessLabelByProcessName(String processName);

	WorkloadRangeResponse getWorkLoadRange();

	List<ReportAgingDto> getAgeingBuckets(String reportName);

	ProcessConfigResponse getUserBusinessLabels();

	String getAllProcessNamesByRoleAsString(String processName);

}
