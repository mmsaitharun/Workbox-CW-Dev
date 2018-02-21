package com.incture.pmc.services;

import javax.ejb.Local;

import com.incture.pmc.dto.AdminControlDto;
import com.incture.pmc.dto.ResponseMessage;

@Local
public interface AdminControlFacadeLocal {

	
	public ResponseMessage deleteProcessConfig(String processName);

	public AdminControlDto getAdminConfigurationData();

	public ResponseMessage createUpdateDataAdmin(AdminControlDto adminControlDto);

	//public ResponseMessage createReportAging(ReportAgingDto dto);

	//ResponseMessage createProcessConfig(ProcessConfigDto dto);
}
