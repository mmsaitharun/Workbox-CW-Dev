package com.incture.pmc.services;

import javax.ejb.Local;

import com.incture.pmc.dto.DownloadReportResponseDto;
import com.incture.pmc.dto.ReportPayload;

@Local
public interface ReportFacadeLocal {

	DownloadReportResponseDto generateReport(ReportPayload payload);

}
