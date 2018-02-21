package com.incture.pmc.reports;

import javax.persistence.EntityManager;

import com.incture.pmc.dao.ProcessEventsDao;
import com.incture.pmc.dto.PMCReportBaseDto;
import com.incture.pmc.dto.ReportPayload;
import com.incture.pmc.util.ServicesUtil;

/**
 * @author Saurabh
 *
 */
public class ProcessReport implements Report {
	
	@Override
	public PMCReportBaseDto getData(ReportPayload payload, EntityManager entityManager) {
		System.err.println("[PMC] REPORT - ProcessReport  - getData() - Started with ReportPayload - " + payload);
		if (!ServicesUtil.isEmpty(payload) && !ServicesUtil.isEmpty(entityManager)) {
			ProcessEventsDao dao = new ProcessEventsDao(entityManager);
			return dao.getProcessAgeing(payload.getGraphType(), payload.getProcessName(), payload.getReportAgingDtos());
		}
		return null;
	}

}
