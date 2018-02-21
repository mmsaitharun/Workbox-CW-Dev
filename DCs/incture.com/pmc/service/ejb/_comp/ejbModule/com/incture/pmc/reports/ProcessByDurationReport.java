package com.incture.pmc.reports;

import javax.persistence.EntityManager;

import com.incture.pmc.dao.ProcessEventsDao;
import com.incture.pmc.dto.PMCReportBaseDto;
import com.incture.pmc.dto.ProcessDetailsDto;
import com.incture.pmc.dto.ReportPayload;
import com.incture.pmc.util.ServicesUtil;

/**
 * @author Saurabh
 *
 */
public class ProcessByDurationReport implements Report {

	@Override
	public PMCReportBaseDto getData(ReportPayload payload, EntityManager entityManager) {
		System.err.println("[PMC] REPORT - ProcessByDurationReport  - getData() - Started with ReportPayload - " + payload);
		if (!ServicesUtil.isEmpty(payload) && !ServicesUtil.isEmpty(entityManager)) {
			payload.setProcessName(payload.getProcessName().substring(1, payload.getProcessName().length()-1));
			ProcessEventsDao dao = new ProcessEventsDao(entityManager);
			return dao.getProcessByDuration(new ProcessDetailsDto(payload.getProcessName(), payload.getStartDayFrom(), payload.getStartDayTo(), payload.getPage()));
		}
		return null;
	}

}
