package com.incture.pmc.reports;

import javax.persistence.EntityManager;

import com.incture.pmc.dao.WorkBoxDao;
import com.incture.pmc.dto.PMCReportBaseDto;
import com.incture.pmc.dto.ReportPayload;
import com.incture.pmc.util.ServicesUtil;

public class CompletedTasksReport implements Report{
	
	@Override
	public PMCReportBaseDto getData(ReportPayload payload, EntityManager entityManger) {
		System.err.println("[PMC] REPORT - CompletedTasksReport  - getData() - Started with ReportPayload - " + payload);
		if(!ServicesUtil.isEmpty(payload)){
			WorkBoxDao dao = new WorkBoxDao(entityManger);
			return dao.getWorkboxCompletedFilterData(payload.getUserId(), payload.getProcessName(), payload.getRequestId(),
					payload.getCreatedBy(), payload.getCreatedAt(), payload.getCompletedAt(), payload.getPeriod(),
					payload.getSkipCount(), payload.getMaxCount(), payload.getPage());
		}
		return null;
	}
	
	

}
