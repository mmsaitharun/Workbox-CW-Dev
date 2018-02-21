package com.incture.pmc.reports;

import javax.persistence.EntityManager;

import com.incture.pmc.dto.PMCReportBaseDto;
import com.incture.pmc.dto.ReportPayload;

/**
 * @author Saurabh
 *
 */
public interface Report {
	
	public PMCReportBaseDto getData(ReportPayload payload, EntityManager entityManager);

}
