package com.incture.pmc.reports;

import javax.persistence.EntityManager;

import com.incture.pmc.dao.ProcessEventsDao;
import com.incture.pmc.dto.PMCReportBaseDto;
import com.incture.pmc.dto.ReportPayload;
import com.incture.pmc.dto.UserProcessDetailRequestDto;
import com.incture.pmc.util.ServicesUtil;

/**
 * @author Saurabh
 *
 */
public class UserWorkloadReport implements Report {

	@Override
	public PMCReportBaseDto getData(ReportPayload payload, EntityManager entityManager) {
		System.err.println("[PMC] REPORT - UserWorkloadReport  - getData() - Started with ReportPayload - " + payload);
		if (!ServicesUtil.isEmpty(payload) && !ServicesUtil.isEmpty(entityManager)) {
			ProcessEventsDao processEventsDao = new ProcessEventsDao(entityManager);
			UserProcessDetailRequestDto requestDto = this.exportToUserProcessDetailRequestDto(payload);
			return processEventsDao.getProcessesByTaskOwner(requestDto);
		}
		return null;
	}

	private UserProcessDetailRequestDto exportToUserProcessDetailRequestDto(ReportPayload payload) {
		System.err.println("[PMC] REPORT - UserWorkloadReport  - exportToUserProcessDetailRequestDto() - Started with ReportPayload - " + payload);
		UserProcessDetailRequestDto dto = new UserProcessDetailRequestDto();
		dto.setGraphType(ServicesUtil.isEmpty(payload.getGraphType()) ? null : payload.getGraphType());
		dto.setLabelValue(ServicesUtil.isEmpty(payload.getLabelValue()) ? null : payload.getLabelValue());
		dto.setProcessName(ServicesUtil.isEmpty(payload.getProcessName()) ? null : payload.getProcessName());
		dto.setRequestId(ServicesUtil.isEmpty(payload.getRequestId()) ? null : payload.getRequestId());
		dto.setStatus(ServicesUtil.isEmpty(payload.getStatus()) ? null : payload.getStatus());
		dto.setUserId(ServicesUtil.isEmpty(payload.getUserId()) ? null : payload.getUserId());
		dto.setPage(ServicesUtil.isEmpty(payload.getPage()) ? null : payload.getPage());
		System.err.println("[PMC] REPORT - UserWorkloadReport  - exportToUserProcessDetailRequestDto() - Ended with UserProcessDetailRequestDto - " + dto);
		return dto;
	}

}
