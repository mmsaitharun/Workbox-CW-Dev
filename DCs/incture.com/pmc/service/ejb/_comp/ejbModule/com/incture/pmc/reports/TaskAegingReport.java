package com.incture.pmc.reports;

import javax.persistence.EntityManager;

import com.incture.pmc.dao.TaskOwnersDao;
import com.incture.pmc.dto.PMCReportBaseDto;
import com.incture.pmc.dto.ReportPayload;
import com.incture.pmc.util.ServicesUtil;

/**
 * @author Saurabh
 *
 */
public class TaskAegingReport implements Report {

	@Override
	public PMCReportBaseDto getData(ReportPayload payload, EntityManager entityManager) {
		System.err.println("[PMC] REPORT - TaskAegingReport  - getData() - Started with ReportPayload - " + payload);
		if (!ServicesUtil.isEmpty(payload) && !ServicesUtil.isEmpty(entityManager)) {
			TaskOwnersDao dao = new TaskOwnersDao(entityManager);
			return dao.getTaskAgeing(payload.getProcessName(), payload.getUsersList(), payload.getStatus(),payload.getRequestId(), payload.getLabelValue());
		}
		return null;
	}
}
