//package com.incture.pmc.services;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.List;
//
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.jws.WebMethod;
//import javax.jws.WebParam;
//import javax.jws.WebService;
//import javax.persistence.Query;
//import javax.persistence.TemporalType;
//
//import com.incture.pmc.dao.ProcessEventsDao;
//import com.incture.pmc.dto.ProcessDetailsDto;
//import com.incture.pmc.dto.ProcessDetailsResponse;
//import com.incture.pmc.dto.ProcessEventsDto;
//import com.incture.pmc.dto.ResponseMessage;
//import com.incture.pmc.util.ServicesUtil;
//
///**
// * Session Bean implementation class ProcessDetailFacade
// */
//@WebService(name = "ProcessDetailFacade", portName = "ProcessDetailFacadePort", serviceName = "ProcessDetailFacadeService", targetNamespace = "http://incture.com/pmc/services/")
//@Stateless
//public class ProcessDetailFacade implements ProcessDetailFacadeLocal {
//
//	@EJB
//	EntityManagerProviderLocal em;
//
//	@EJB
//	private UserManagementFacadeWsdlConsumerLocal webService;
//
//	public ProcessDetailFacade() {
//	}
//
//	@WebMethod(operationName = "getProcessDetailList", exclude = false)
//	@Override
//	public ProcessEventsDto getProcessDetailList(@WebParam(name = "processId") String processId) {
//		ProcessEventsDao processEventsDao = new ProcessEventsDao(em.getEntityManager());
//		ProcessEventsDto processEventsDto = processEventsDao.getProcessDetail(processId);
//		if (!ServicesUtil.isEmpty(processEventsDto) && !ServicesUtil.isEmpty(processEventsDto.getStartedBy())) {
//			com.incture.pmc.poadapter.services.UserDetailsDto userDetailsDto = webService.getUserDetailsById(processEventsDto.getStartedBy());
//			if (!ServicesUtil.isEmpty(userDetailsDto)) {
//				String name = userDetailsDto.getFirstName() == null ? "" : userDetailsDto.getFirstName();
//				name = name + " " + (userDetailsDto.getLastName() == null ? "" : userDetailsDto.getLastName());
//				processEventsDto.setStartedByUser(name);
//			}
//		}
//		return processEventsDto;
//	}
//	
//	@WebMethod(operationName = "getProcessDetail", exclude = false)
//	@Override
//	public ProcessDetailsResponse getProcessDetail(@WebParam(name = "processDetailsDto") ProcessDetailsDto processDetailsDto) {
//		ProcessDetailsResponse detailResponse = new ProcessDetailsResponse();
//		ResponseMessage message = new ResponseMessage();
//
//		StringBuffer processQuery = new StringBuffer(
//				"select p.requestId,p.processId,p.name,p.subject,p.startedAt,p.startedBy from ProcessEventsDo p where ");
//		if (!ServicesUtil.isEmpty(processDetailsDto.getProcessName())) {
//			processQuery.append("p.name =:processName");
//		}
//		if (!ServicesUtil.isEmpty(processDetailsDto.getStartDayFrom())
//				&& !ServicesUtil.isEmpty(processDetailsDto.getStartDayTo())) {
//			processQuery.append(" and p.startedAt >=:startDate	 AND p.startedAt <=:endDate");
//		}
//		Query query = em.getEntityManager().createQuery(processQuery.toString());
//		if (!ServicesUtil.isEmpty(processDetailsDto.getProcessName())) {
//			query.setParameter("processName", processDetailsDto.getProcessName());
//		}
//		if (!ServicesUtil.isEmpty(processDetailsDto.getStartDayFrom())
//				&& !ServicesUtil.isEmpty(processDetailsDto.getStartDayTo())) {
//			try {
//				Date startDateFrom = ServicesUtil.getDate(processDetailsDto.getStartDayFrom());
//				Date startDateTo = ServicesUtil.getDate(processDetailsDto.getStartDayTo());
//				startDateTo = ServicesUtil.setEndTime(startDateTo);
//				query.setParameter("startDate", startDateFrom, TemporalType.TIMESTAMP);
//				query.setParameter("endDate", startDateTo, TemporalType.TIMESTAMP);
//			} catch (ParseException e) {
//				message.setStatus("Failed");
//				message.setStatusCode("1");
//				message.setMessage("Parse Exception :"+e.getMessage());
//				detailResponse.setResponseMessage(message);
//			}
//		}
//		List<Object[]> resultList = query.getResultList();
//		if (resultList != null) {
//			List<ProcessEventsDto> processEventsList = new ArrayList<ProcessEventsDto>();
//			for (Object[] obj : resultList) {
//				ProcessEventsDto processEventsDto = new ProcessEventsDto();
//				processEventsDto.setRequestId(obj[0] == null ? null : (String) obj[0]);
//				processEventsDto.setProcessId(obj[1] == null ? null : (String) obj[1]);
//				processEventsDto.setName(obj[2] == null ? null : (String) obj[2]);
//				processEventsDto.setSubject(obj[3] == null ? null : (String) obj[3]);
//				processEventsDto.setStartedAt(obj[4] == null ? null : (Date) obj[4]);
//				if(obj[5]!=null){
//					com.incture.pmc.poadapter.services.UserDetailsDto dto = webService.getUserDetailsById((String) obj[5]);
//					StringBuffer name = new StringBuffer();
//					if (!ServicesUtil.isEmpty(dto)) {
//						name = dto.getFirstName() == null ? name.append("")
//								: name.append(dto.getFirstName()).append(" ");
//						name = dto.getLastName() == null ? name.append("") : name.append(dto.getLastName());
//						processEventsDto.setStartedBy(name.toString().trim());
//					}
//				}
//				processEventsList.add(processEventsDto);
//			}
//			Comparator<ProcessEventsDto> sortByStartedAt = new Comparator<ProcessEventsDto>() {
//				@Override
//				public int compare(ProcessEventsDto o1, ProcessEventsDto o2) {
//					 return o2.getStartedAt().compareTo(o1.getStartedAt());
//				}
//			};
//			Collections.sort(processEventsList,sortByStartedAt);
//			detailResponse.setProcessEventsList(processEventsList);
//			message.setStatus("Success");
//			message.setStatusCode("0");
//			message.setMessage("Process Details Fetched Successfully");
//			detailResponse.setResponseMessage(message);
//		} else {
//			message.setStatus("Success");
//			message.setStatusCode("1");
//			message.setMessage("No Results Found for the requested query");
//			detailResponse.setResponseMessage(message);
//		}
//		return detailResponse;
//	}
//
//}
