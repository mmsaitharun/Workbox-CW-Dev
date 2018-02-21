package com.incture.pmc.reports;

import javax.persistence.EntityManager;

import com.incture.pmc.dao.TaskEventsDao;
import com.incture.pmc.dto.ManageTasksRequestDto;
import com.incture.pmc.dto.PMCReportBaseDto;
import com.incture.pmc.dto.ReportPayload;
import com.incture.pmc.util.ServicesUtil;

/**
 * @author Saurabh
 *
 */
public class TaskManagerReport implements Report {

	@Override
	public PMCReportBaseDto getData(ReportPayload payload, EntityManager entityManager) {
		System.err.println("[PMC] REPORT - TaskManagerReport  - getData() - Started with ReportPayload - " + payload);
		if (!ServicesUtil.isEmpty(payload) && !ServicesUtil.isEmpty(entityManager)) {
			TaskEventsDao dao = new TaskEventsDao(entityManager);
			return dao.getTasksByUserAndDuration(new ManageTasksRequestDto(payload.getUserId(), payload.getStatus(), payload.getProcessName(), payload.getStartDayFrom(), payload.getStartDayTo(),
					payload.getPage(), payload.getRequestId(), payload.getLabelValue()));
		}
		return null;
	}

}
