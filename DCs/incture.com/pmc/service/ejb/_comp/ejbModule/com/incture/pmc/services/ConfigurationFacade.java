package com.incture.pmc.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.incture.pmc.dao.ProcessConfigDao;
import com.incture.pmc.dao.ProcessEventsDao;
import com.incture.pmc.dao.ReportAgingDao;
import com.incture.pmc.dao.WorkloadRangeDao;
import com.incture.pmc.dto.ProcessConfigDto;
import com.incture.pmc.dto.ProcessListDto;
import com.incture.pmc.dto.ReportAgingDto;
import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.WorkloadRangeDto;
import com.incture.pmc.responseDto.ProcessConfigResponse;
import com.incture.pmc.responseDto.WorkloadRangeResponse;
import com.incture.pmc.util.ExecutionFault;
import com.incture.pmc.util.InvalidInputFault;
import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;

/**
 * Session Bean implementation class ConfigurationFacade
 */
@Stateless
public class ConfigurationFacade implements ConfigurationFacadeLocal {

	@EJB
	EntityManagerProviderLocal em;


	@EJB
	UserManagementFacadeWsdlConsumerLocal ume;

	@Override
	public ProcessListDto getAllProcessNames() {

		ProcessEventsDao processEventsDao = new ProcessEventsDao(em.getEntityManager());
		List<String> processNameList = null;
		try {
			processNameList = processEventsDao.getAllProcessName();
		} catch (NoResultFault e) {
		}
		ProcessListDto processListDto = new ProcessListDto();
		processListDto.setProcessNameList(processNameList);
		return processListDto;
	}

	@Override
	public ProcessConfigResponse getAllBusinessLabels() {
		ProcessConfigResponse response = new ProcessConfigResponse();
		ProcessConfigDao processLabelDao = new ProcessConfigDao(em.getEntityManager());
		ResponseMessage responseMessage = new ResponseMessage();
		List<ProcessConfigDto> processConfigDtos = null;
		try {
			processConfigDtos =	getLabels(processLabelDao.getAllProcessConfigEntry());
			if(!ServicesUtil.isEmpty(processConfigDtos)){
				responseMessage.setMessage("Business Labels Fetched successfully");
			} else {
				responseMessage.setMessage(PMCConstant.NO_RESULT);
			}
			
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("1");
		} catch (NoResultFault e) {
			System.err.println(e.getMessage());
			responseMessage.setMessage("Failed due to" +e.getMessage());
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("0");
		}
		response.setProcessConfigDtos(processConfigDtos);
		response.setResponseMessage(responseMessage);
		return response;
	}



	private  List<ProcessConfigDto> getLabels(List<ProcessConfigDto> processConfigDtos){
		Set<String> userGroupSet = new LinkedHashSet<String>();
		if (!ServicesUtil.isEmpty(processConfigDtos)) {
			ProcessConfigDto allProcessDto = null;
			for (ProcessConfigDto dto : processConfigDtos) {
				if (!ServicesUtil.isEmpty(dto) && !ServicesUtil.isEmpty(dto.getUserGroup())) {
					if (PMCConstant.SEARCH_ALL.equals(dto.getUserGroup())) {
						//	userGroupSet.add(dto.getUserGroup());
						allProcessDto = dto;
					} else {
						List<String> li = new ArrayList<String>();
						li.addAll(Arrays.asList(dto.getUserGroup().split(", ")));
						li.add(PMCConstant.SEARCH_SMALL_ALL);
						dto.setUserList(li);
						userGroupSet.addAll(dto.getUserList());
					}
				}
			}
			List<String> allUserList = new ArrayList<String>();
			allUserList.addAll(userGroupSet);
			if (!ServicesUtil.isEmpty(allProcessDto))
				allProcessDto.setUserList(allUserList);
		}
		return processConfigDtos;
	}

	@Override
	public ProcessConfigResponse getUserBusinessLabels() {
		ResponseMessage responseMessage = new ResponseMessage();
		ProcessConfigResponse response = new ProcessConfigResponse();
		ProcessConfigDao processLabelDao = new ProcessConfigDao(em.getEntityManager());
		List<ProcessConfigDto> processConfigDtos = null;
		try {
			processConfigDtos =	getLabels(processLabelDao.getAllProcessConfigEntryByRole(ume.getLoggedInUser().getUserId()));
			if(!ServicesUtil.isEmpty(processConfigDtos)){
				responseMessage.setMessage("User Business Labels Fetched successfully");
			} else {
				responseMessage.setMessage(PMCConstant.NO_RESULT);
			}
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("1");
		} catch (NoResultFault e) {
			System.err.println(e.getMessage());
			responseMessage.setMessage("Failed due to" +e.getMessage());
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("0");
		}
		response.setProcessConfigDtos(processConfigDtos);
		response.setResponseMessage(responseMessage);
		return response;
	}

	@Override
	public ProcessConfigDto getBusinessLabelByProcessName(String processName) {
		ProcessConfigDto processConfigDto = null;
		if (!ServicesUtil.isEmpty(processName)) {
			processConfigDto = new ProcessConfigDto();
			processConfigDto.setProcessName(processName.trim());
			ProcessConfigDao processConfigDao = new ProcessConfigDao(em.getEntityManager());
			try {
				processConfigDto = processConfigDao.getByKeys(processConfigDto);
				if (!ServicesUtil.isEmpty(processConfigDto) && !ServicesUtil.isEmpty(processConfigDto.getUserGroup())) {
					processConfigDto.setUserList(Arrays.asList(processConfigDto.getUserGroup().split(",")));
				}
			} catch (ExecutionFault e) {
				e.printStackTrace();
			} catch (InvalidInputFault e) {
				e.printStackTrace();
			} catch (NoResultFault e) {
				System.err.println(e.getMessage());
			}
		}
		return processConfigDto;
	}

	@Override
	public WorkloadRangeResponse getWorkLoadRange() {
		WorkloadRangeResponse response = new WorkloadRangeResponse();
		List<WorkloadRangeDto> rangeDtos = null;
		ResponseMessage responseMessage = new ResponseMessage();
		WorkloadRangeDao rangeDao = new WorkloadRangeDao(em.getEntityManager());
		try {
			rangeDtos = rangeDao.getAllResults("WorkloadRangeDo");
			responseMessage.setMessage("Workload Ranges Fetched Sucessfully");
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("1");
		} catch (NoResultFault e) {
			responseMessage.setMessage("Failed due to" +e.getMessage());
			responseMessage.setStatus("FAILURE");
			responseMessage.setStatusCode("0");
		}
		response.setResponseMessage(responseMessage);
		response.setWorkloadRangeDtos(rangeDtos);
		return response;
	}


	@Override
	public List<ReportAgingDto> getAgeingBuckets(String reportName) {
		ReportAgingDao agingDao = new ReportAgingDao(em.getEntityManager());
		return agingDao.getConfigByReportName(reportName);
	}
	@Override
	public String getAllProcessNamesByRoleAsString(String processName)
	{
		if(PMCConstant.SEARCH_SMALL_ALL.equals(processName)){
			try {
				String processNameString = new ProcessConfigDao(em.getEntityManager()).getAllProcessNamesByRoleAsString(ume.getLoggedInUser().getUserId());
				if(!ServicesUtil.isEmpty(processName)){
					processName = processNameString;
				}
				else{
					processName = "'"+processName+"'";
				}
			} catch (NoResultFault e) {
				System.err.println("[PMC][UserWorkloadFacadeNew][getUserWorkLoadHeatMap][error]"+e.getMessage());					
				processName = "'"+processName+"'";
			}
		}
		else{
			processName = "'"+processName+"'";
		}
		return processName;
	}

}
