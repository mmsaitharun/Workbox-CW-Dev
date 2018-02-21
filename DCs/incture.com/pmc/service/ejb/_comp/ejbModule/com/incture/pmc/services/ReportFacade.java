package com.incture.pmc.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.incture.pmc.dto.DownloadReportResponseDto;
import com.incture.pmc.dto.PMCReportBaseDto;
import com.incture.pmc.dto.ReportFormattedDto;
import com.incture.pmc.dto.ReportPayload;
import com.incture.pmc.reports.DownloadReportFactoryGenerator;
import com.incture.pmc.reports.File;
import com.incture.pmc.reports.Report;
import com.incture.pmc.reports.ServiceFactory;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;

/**
 * Session Bean implementation class ReportFacade
 */
/**
 * @author Saurabh
 *
 */
@WebService(name = "ReportFacade", portName = "ReportFacadePort", serviceName = "ReportFacadeService", targetNamespace = "http://incture.com/pmc/services/")
@Stateless
public class ReportFacade implements ReportFacadeLocal {

	@EJB
	private EntityManagerProviderLocal em;

	@EJB
	private UserManagementFacadeWsdlConsumerLocal webService;

	@EJB
	private ConfigurationFacadeLocal config;

	public ReportFacade() {
	}

	@WebMethod(operationName = "generateReport", exclude = false)
	@Override
	public DownloadReportResponseDto generateReport(@WebParam(name = "payload") ReportPayload payload) {
		System.err.println("[PMC] REPORT - ReportFacade  - generateReport() - Started with ReportPayload - " + payload);
		DownloadReportResponseDto response = null;
		if (!ServicesUtil.isEmpty(payload) && !ServicesUtil.isEmpty(payload.getFileFormate()) && !ServicesUtil.isEmpty(payload.getReportName())) {
			System.err.println("payload - Report Name =  " + payload.getReportName() + "  File Formate = " + payload.getFileFormate());
			if (!ServicesUtil.isEmpty(payload.getUserGroup()) && !payload.getUserGroup().equals(PMCConstant.SEARCH_ALL)) {
				payload.setUsersList(webService.getUsersAssignedInGroup(payload.getUserGroup()));
				System.err.println("UserList - " + payload.getUsersList());
			}
			if (!ServicesUtil.isEmpty(payload.getGraphType()) && PMCConstant.GRAPH_TREND_MONTH.equalsIgnoreCase(payload.getGraphType())) {
				payload.setReportAgingDtos(config.getAgeingBuckets(PMCConstant.PROCESS_AGING_REPORT));
			}
			if(ServicesUtil.isEmpty(payload.getUserId())){
				payload.setUserId(webService.getLoggedInUser().getUserId());
			}
			
			payload.setProcessName(config.getAllProcessNamesByRoleAsString(payload.getProcessName()));
			
			ServiceFactory reportFactory = DownloadReportFactoryGenerator.getReportFactory();
			
			Report report = reportFactory.getReport(payload.getReportName());
			PMCReportBaseDto pmcReportBaseDto = report.getData(payload, em.getEntityManager());
			System.err.println("[PMC] REPORT - ReportFacade  - generateReport() - pmcReportBaseDto- " + pmcReportBaseDto);

			ServiceFactory downloadFactory = DownloadReportFactoryGenerator.getDownloadFactory();
			File file = downloadFactory.getFile(payload.getFileFormate());
			ReportFormattedDto formattedDto = file.setSheetName(payload.getReportName());
			response = file.pushData(file.exportToFormattedDto(pmcReportBaseDto, formattedDto));
		}
		System.err.println("[PMC] REPORT - ReportFacade  - generateReport() - Ended with ReportPayload - " + response);
		return response;
	}
}
