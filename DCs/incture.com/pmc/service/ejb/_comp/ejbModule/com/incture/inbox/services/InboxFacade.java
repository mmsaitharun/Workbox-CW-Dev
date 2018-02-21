package com.incture.inbox.services;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.incture.pmc.dao.WorkBoxDao;
import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.TasksCountDTO;
import com.incture.pmc.dto.TrackingResponse;
import com.incture.pmc.dto.WorkBoxDto;
import com.incture.pmc.dto.WorkboxResponseDto;
import com.incture.pmc.poadapter.services.UserDto;
import com.incture.pmc.services.CustomAttributesWSDLConsumerLocal;
import com.incture.pmc.services.EntityManagerProviderLocal;
import com.incture.inbox.services.SubstitutionRuleFacadeWsdlConsumerLocal;
import com.incture.pmc.services.UserManagementFacadeWsdlConsumerLocal;
import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;

/**
 * Session Bean implementation class InboxFacade
 */
@Stateless
public class InboxFacade implements InboxFacadeLocal {

	@EJB
	UserManagementFacadeWsdlConsumerLocal webservice;

	@EJB
	CustomAttributesWSDLConsumerLocal customAttribute;

	@EJB
	SubstitutionRuleFacadeWsdlConsumerLocal substitution;

	@EJB
	EntityManagerProviderLocal em;
	
    /**
     * Default constructor. 
     */
    public InboxFacade() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public WorkboxResponseDto getWorkboxFilterData(String processName, String requestId, String createdBy, String createdAt, String status, Integer skipCount, Integer maxCount, Integer page,String orderBy, String orderType) {
		//	System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData] method invoked ");
		
		WorkboxResponseDto workboxResponseDto = new WorkboxResponseDto();
		ResponseMessage message = new ResponseMessage();
		String taskOwner = webservice.getLoggedInUser().getUserId();
		List<UserDto> userList = substitution.getSubstitutedUsers(taskOwner);
		String users = "'"+taskOwner+"'";
		if(!ServicesUtil.isEmpty(userList)){
			for(UserDto user: userList){
				users = users+",'"+  user.getLoginId()+"'";	
			}
		}
		//	String taskOwner = "saurabh";
		if (!ServicesUtil.isEmpty(taskOwner)) {
			System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData][getLoggedInUser] " + taskOwner);
			if (taskOwner.equals(webservice.getLoggedInUser().getUserId())) {
				SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MMM-yy hh:mm:ss a");
				String dataQuery = "SELECT te.EVENT_ID AS TASK_ID,pe.REQUEST_ID AS REQUEST_ID, pe.NAME AS PROCESS_NAME , te.DESCRIPTION AS DESCRIPTION, te.NAME AS TASK_NAME, te.SUBJECT AS TASK_SUBJECT, pe.STARTED_BY_DISP AS STARTED_BY, te.CREATED_AT AS TASK_CREATED_AT, te.STATUS AS TASK_STATUS,te.CUR_PROC AS CUR_PROC,ts.SLA AS SLA, te.PROCESS_ID AS PROCESS_ID, te.URL AS URL,te.COMP_DEADLINE AS SLA_DUE_DATE, Te.FORWARDED_BY AS FORWARDED_BY, Te.FORWARDED_AT AS FORWARDED_AT, Pct.PROCESS_DISPLAY_NAME AS PROCESS_DISPLAY_NAME FROM TASK_EVENTS te LEFT JOIN PROCESS_CONFIG_TB Pct ON TE.PROC_NAME = PCT.PROCESS_NAME LEFT JOIN TASK_SLA ts ON te.NAME = ts.TASK_DEF, PROCESS_EVENTS pe, TASK_OWNERS tw WHERE pe.PROCESS_ID = te.PROCESS_ID AND tw.EVENT_ID = te.EVENT_ID AND pe.STATUS = 'IN_PROGRESS'";
				String query="";
				if (!ServicesUtil.isEmpty(status)) {
					query = query + " AND te.STATUS = '" + status + "' AND tw.TASK_OWNER IN (" + users + ")";
					if (status.equals("READY")) {
						query = query + "and te.status='READY'";
					} else if (status.equals("RESERVED")) {
						query = query + "AND  te.CUR_PROC IN (" + users + ")";
					}
				} else {
					query = query + " AND te.STATUS <> 'COMPLETED' AND(tw.TASK_OWNER IN (" + users
							+ ") and (te.status='READY' OR te.CUR_PROC IN (" + users + ")))";
				}
				if (!ServicesUtil.isEmpty(processName)) {
					query = query + " AND pe.NAME";
					if(processName.indexOf(",")!=-1){
						query = query +" IN (";
						String[] processes = processName.split(",");
						for(String process:processes){
							query = query +"'"+process+"',";	
						}
						query = query.substring(0, query.length()-1);
						query = query +")";
					}
					else{
						query = query +" = '" + processName + "'";
					}
				}
				if (!ServicesUtil.isEmpty(requestId)) {
					query = query + " AND pe.REQUEST_ID LIKE '%" + requestId + "%'";
				}
				if (!ServicesUtil.isEmpty(createdBy)) {
					query = query + " AND pe.STARTED_BY = '" + createdBy + "'";
				}
				if (!ServicesUtil.isEmpty(createdAt)) {
					query = query + " AND to_char(cast(te.CREATED_AT as date),'MM/DD/YYYY')= '" + createdAt + "'";
				}
				/*
				For SLA_DUE_DATE
				if (!ServicesUtil.isEmpty(orderType)&& orderType.equals(PMCConstant.ORDER_TYPE_SLA_DUE_DATE)) {

					query = query + " AND to_char(cast(te.COMP_DEADLINE as date),'MM/DD/YYYY')= '" + orderType + "'";
				}*/


				String countQuery = " SELECT  COUNT(*) AS COUNT FROM TASK_EVENTS te, PROCESS_EVENTS pe, TASK_OWNERS tw WHERE pe.PROCESS_ID = te.PROCESS_ID AND tw.EVENT_ID = te.EVENT_ID AND pe.STATUS = 'IN_PROGRESS'" + query; 
				System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData][countQuery]" + countQuery);
				Query cq = em.getEntityManager().createNativeQuery(countQuery.trim(), "workBoxCountResult");
				BigDecimal count = (BigDecimal) cq.getSingleResult();


				if(ServicesUtil.isEmpty(orderType)&&ServicesUtil.isEmpty(orderBy))
					query=query+" ORDER BY 8 DESC";
				else if(!ServicesUtil.isEmpty(orderType)){
					
						query=query+" ORDER BY ";
						if (orderType.equals(PMCConstant.ORDER_TYPE_CREATED_AT)) {
							query=query + "8";
						} else if (orderType.equals(PMCConstant.ORDER_TYPE_SLA_DUE_DATE)) {
							query=query + "14";
						}
						if(ServicesUtil.isEmpty(orderBy) || (!ServicesUtil.isEmpty(orderBy) && orderBy.equals(PMCConstant.ORDER_BY_ASC))){
							query = query + " ASC";
						}
						else if (orderBy.equals(PMCConstant.ORDER_BY_DESC))
							query = query + " DESC";
					}

				System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData][query]" + query);

				dataQuery = dataQuery + query;
				System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData][dataQuery]" + dataQuery);
				Query q = em.getEntityManager().createNativeQuery(dataQuery.trim(), "workBoxResults");


				if (!ServicesUtil.isEmpty(maxCount) && maxCount > 0 && !ServicesUtil.isEmpty(skipCount)
						&& skipCount >= 0) {
					int first = skipCount;
					int last = maxCount;
					q.setFirstResult(first);
					q.setMaxResults(last);
				}
				if (!ServicesUtil.isEmpty(page) && page > 0) {
					int first = (page - 1) * PMCConstant.PAGE_SIZE;
					int last = PMCConstant.PAGE_SIZE;
					q.setFirstResult(first);
					q.setMaxResults(last);
				}

				@SuppressWarnings("unchecked")
				List<Object[]> resultList = q.getResultList();
				if (ServicesUtil.isEmpty(resultList)) {
					try {
						throw new NoResultFault("NO RECORD FOUND");
					} catch (NoResultFault e) {
						System.err.println("NO RESULT FOUND");
						message.setStatus("NO RESULT FOUND");
						message.setStatusCode("1");
						workboxResponseDto.setResponseMessage(message);
					}
				} else {
					List<WorkBoxDto> workBoxDtos = new ArrayList<WorkBoxDto>();
					System.err.println("ResultList - " + resultList.size());
					for (Object[] obj : resultList) {
						WorkBoxDto workBoxDto = new WorkBoxDto();
						workBoxDto.setTaskId(obj[0] == null ? null : (String) obj[0]);
						workBoxDto.setRequestId(obj[1] == null ? null : (String) obj[1]);
						workBoxDto.setProcessName(obj[2] == null ? null : (String) obj[2]);
						workBoxDto.setTaskDescription(obj[3] == null ? null : (String) obj[3]);
						workBoxDto.setName(obj[4] == null ? null : (String) obj[4]);
						workBoxDto.setSubject(obj[5] == null ? null : (String) obj[5]);
						workBoxDto.setStartedBy(obj[6] == null ? null : (String) obj[6]);
						System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData][obj[7]]" + obj[7]+"ServicesUtil.resultAsDate(obj[7])"+ServicesUtil.resultAsDate(obj[7])+"simpleDateFormat1.format(ServicesUtil.resultAsDate(obj[7]))"+simpleDateFormat1.format(ServicesUtil.resultAsDate(obj[7])));	
						workBoxDto.setCreatedAt(obj[7] == null ? null : simpleDateFormat1.format(ServicesUtil.resultAsDate(obj[7])));
						workBoxDto.setStatus(obj[8] == null ? null : (String) obj[8]);
						workBoxDto.setSlaDisplayDate(obj[13] == null ? null :simpleDateFormat1.format(ServicesUtil.resultAsDate(obj[13])));
						//						if(!ServicesUtil.isEmpty((String) obj[12])){
						//							((String) obj[12]).replace("incturecwd", "115.110.225.49");
						//							System.err.println("[pmc][change_string_rep] "+((String) obj[12]).replace("incturecwd", "115.110.225.49"));
						//						}
						workBoxDto.setDetailURL(obj[12] == null ? null : ((String) obj[12]));
						if(!ServicesUtil.isEmpty(obj[13]) && !ServicesUtil.isEmpty(obj[7]) ){
							System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData][ created]" + obj[7]);
							Calendar created = ServicesUtil.timeStampToCal(obj[7]);
							Calendar slaDate = ServicesUtil.timeStampToCal(obj[13]);
							//workBoxDto.setSlaDisplayDate(simpleDateFormat1.format(slaDate.getTime()));
							String timeLeftString = ServicesUtil.getSLATimeLeft(slaDate);
							if(timeLeftString.equals("Breach")){
								workBoxDto.setBreached(true);
							}
							else{
								workBoxDto.setBreached(false);
								workBoxDto.setTimeLeftDisplayString(timeLeftString);
								workBoxDto.setTimePercentCompleted(ServicesUtil.getPercntTimeCompleted(created,slaDate));
							}
						}
						workBoxDto.setSla(obj[10] == null ? null : (String) obj[10]);
						workBoxDto.setProcessId(obj[11] == null ? null : (String) obj[11]);
						workBoxDto.setCustomAttributes(customAttribute.getCUstomAttribute(workBoxDto.getTaskId()));
						workBoxDto.setForwardedBy(obj[14] == null ? null : (String) obj[14]);
						workBoxDto.setForwardedAt(obj[15] == null ? null :simpleDateFormat1.format(ServicesUtil.resultAsDate(obj[15])));
						workBoxDto.setProcessDisplayName(obj[16] == null ? (String) obj[1] : (String) obj[16]);
						//	workBoxDto.setUrl();
						workBoxDtos.add(workBoxDto);
					}
					workboxResponseDto.setPageCount(PMCConstant.PAGE_SIZE);
					workboxResponseDto.setCount(count);
					workboxResponseDto.setWorkBoxDtos(workBoxDtos);
					message.setStatus("Success");
					message.setStatusCode("0");
					message.setMessage("Process Details Fetched Successfully");
					workboxResponseDto.setResponseMessage(message);
				}
				return workboxResponseDto;
			}
		}
		message.setStatus("FAILURE");
		message.setStatusCode("1");
		message.setMessage("NO USER FOUND");
		workboxResponseDto.setResponseMessage(message);
		return workboxResponseDto;

	}
    
	@Override
	public WorkboxResponseDto getWorkboxCompletedFilterData(String processName, String requestId, String createdBy, String createdAt, String completedAt, String period, Integer skipCount, Integer maxCount, Integer page) {
		WorkBoxDao dao = new WorkBoxDao(em.getEntityManager());
		//WbDaoLocal dao = new WbDao();
		String userId = webservice.getLoggedInUser().getUserId();
		return dao.getWorkboxCompletedFilterData(userId, processName, requestId, createdBy, createdAt, completedAt, period, skipCount, maxCount, page);
	}
	
	/*private BigDecimal getCount(String taskOwner, String processName) {
		System.err.println("[PMC][getCount] method invoked");
		String query = " SELECT  COUNT(*) AS COUNT FROM TASK_EVENTS te, PROCESS_EVENTS pe, TASK_OWNERS tw WHERE pe.PROCESS_ID = te.PROCESS_ID AND tw.EVENT_ID = te.EVENT_ID AND pe.STATUS = 'IN_PROGRESS' AND te.STATUS <> 'COMPLETED'";
		if (!ServicesUtil.isEmpty(processName)) {
			query = query + " AND pe.NAME = '" + processName + "'";
		}
		if (!ServicesUtil.isEmpty(taskOwner)) {
			query = query + "AND(tw.TASK_OWNER = '" + taskOwner + "' and (te.status='READY' OR te.CUR_PROC = '"
					+ taskOwner + "'))";
		}
		System.err.println("[PMC][getCount][qury] " + query);
		Query q = em.getEntityManager().createNativeQuery(query.trim(), "workBoxCountResult");
		return (BigDecimal) q.getSingleResult();

	}
*/
	@Override
	public TrackingResponse getTrackingResults() {

		TrackingResponse response = new TrackingResponse();
		ResponseMessage respMessage = new ResponseMessage();
		String userId = webservice.getLoggedInUser().getUserId();

		if(!ServicesUtil.isEmpty(userId)){

			BigDecimal compOnSlaCount = null;
			BigDecimal compOffSlaCount = null;
			BigDecimal inProgOnSlaCount = null;
			BigDecimal inProgOffSlaCount = null;
			BigDecimal inProgApproachingCount = null;

			List<TasksCountDTO> responseList = new ArrayList<TasksCountDTO>();

			String completedCountQry1 = "SELECT COUNT(EVENT_ID) AS COUNT FROM TASK_EVENTS WHERE STATUS = 'COMPLETED' AND COMP_DEADLINE <= COMPLETED_AT and CUR_PROC = '"
					+ userId + "'";
			String completedCountQry2 = "SELECT COUNT(EVENT_ID) AS COUNT FROM TASK_EVENTS WHERE STATUS = 'COMPLETED' AND COMP_DEADLINE > COMPLETED_AT and CUR_PROC = '"
					+ userId + "'";

			Query comCountQry1 = em.getEntityManager().createNativeQuery(completedCountQry1, "countResult");
			compOffSlaCount = (BigDecimal) comCountQry1.getSingleResult();

			Query comCountQry2 = em.getEntityManager().createNativeQuery(completedCountQry2, "countResult");
			compOnSlaCount = (BigDecimal) comCountQry2.getSingleResult();

			System.err.println("Completed off Sla Count : " + compOffSlaCount);
			System.err.println("Completed on Sla Count : " + compOnSlaCount);

			Date currDate = new Date();
			DateFormat df = new SimpleDateFormat("dd-MMM-yy hh:mm:ss a");


			String inProgressCountQry1 = "SELECT COUNT(DISTINCT(TE.EVENT_ID)) AS COUNT " +
					"FROM TASK_EVENTS TE " +
					"JOIN TASK_OWNERS TW " +
					"ON TE.EVENT_ID    = TW.EVENT_ID " +
					"AND ((TE.STATUS   = 'RESERVED' " +
					"AND TE.CUR_PROC   = '"+userId+"'  AND TW.IS_PROCESSED = '1') " +
					"OR (TE.STATUS     = 'READY' " +
					"AND TW.TASK_OWNER ='"+userId+"')) " +
					"AND (TO_DATE('"+df.format(currDate)+"', 'DD/MM/YY hh:mi:ss AM') > TE.COMP_DEADLINE)";

			Query resInProgressCountQry1 = em.getEntityManager().createNativeQuery(inProgressCountQry1, "countResult");
			inProgOffSlaCount = (BigDecimal) resInProgressCountQry1.getSingleResult();
			System.err.println("[pmc][tracking] : "+inProgOnSlaCount);

			String inProgressCountQry2 = "SELECT COUNT(DISTINCT(TE.EVENT_ID)) AS COUNT " +
					"FROM TASK_EVENTS TE " +
					"JOIN TASK_OWNERS TW " +
					"ON TE.EVENT_ID    = TW.EVENT_ID " +
					"AND ((TE.STATUS   = 'RESERVED' " +
					"AND TE.CUR_PROC   = '"+userId+"' AND TW.IS_PROCESSED = '1') " +
					"OR (TE.STATUS     = 'READY' " +
					"AND TW.TASK_OWNER ='"+userId+"')) " +
					"AND (TO_DATE('"+df.format(currDate)+"', 'DD/MM/YY hh:mi:ss AM')  < TE.COMP_DEADLINE-1)";

			Query resInProgressCountQry2 = em.getEntityManager().createNativeQuery(inProgressCountQry2, "countResult");
			inProgOnSlaCount = (BigDecimal) resInProgressCountQry2.getSingleResult();
			System.err.println("[pmc][tracking] : "+inProgOnSlaCount);

			String inProgressApproching = "SELECT COUNT(DISTINCT(TE.EVENT_ID)) AS COUNT " +
					"FROM TASK_EVENTS TE " +
					"JOIN TASK_OWNERS TW " +
					"ON TE.EVENT_ID    = TW.EVENT_ID " +
					"AND ((TE.STATUS   = 'RESERVED' " +
					"AND TE.CUR_PROC   = '"+userId+"' AND TW.IS_PROCESSED = '1') " +
					"OR (TE.STATUS     = 'READY' " +
					"AND TW.TASK_OWNER ='"+userId+"')) " +
					"AND (TO_DATE('"+df.format(currDate)+"', 'DD/MM/YY hh:mi:ss AM') > TE.COMP_DEADLINE - 1 AND (TO_DATE('"+df.format(currDate)+"', 'DD/MM/YY hh:mi:ss AM') <= TE.COMP_DEADLINE))";

			Query resInProgressApproaching = em.getEntityManager().createNativeQuery(inProgressApproching, "countResult");
			inProgApproachingCount = (BigDecimal) resInProgressApproaching.getSingleResult();
			System.err.println("[pmc][tracking] : "+inProgApproachingCount);

			if ((!ServicesUtil.isEmpty(compOnSlaCount) && (!ServicesUtil.isEmpty(compOffSlaCount)
					&& (!ServicesUtil.isEmpty(inProgOnSlaCount) && (!ServicesUtil.isEmpty(inProgOffSlaCount)))))) {

				TasksCountDTO resp = new TasksCountDTO();
				resp.setStatus("Completed - Past Due");
				resp.setCount(compOffSlaCount);
				responseList.add(resp);
				TasksCountDTO resp1 = new TasksCountDTO();
				resp1.setStatus("Completed - On Track");
				resp1.setCount(compOnSlaCount);
				responseList.add(resp1);
				TasksCountDTO resp2 = new TasksCountDTO();
				resp2.setStatus("In Progress - Past Due");
				resp2.setCount(inProgOffSlaCount);
				responseList.add(resp2);
				TasksCountDTO resp3 = new TasksCountDTO();
				resp3.setStatus("In Progress - On Track");
				resp3.setCount(inProgOnSlaCount);
				responseList.add(resp3);
				TasksCountDTO resp4 = new TasksCountDTO();
				resp4.setStatus("In Progress - Near Due");
				resp4.setCount(inProgApproachingCount);
				responseList.add(resp4);


				response.setTasksCount(responseList);
				respMessage.setMessage("Tasks Count Sent Successfully");
				respMessage.setStatus("Success");
				respMessage.setStatusCode("0");
				response.setResponseMessage(respMessage);
				return response;
			} else {
				respMessage.setMessage(PMCConstant.NO_RESULT);
				respMessage.setStatus("Failure");
				respMessage.setStatusCode("1");
				response.setResponseMessage(respMessage);
			}
			respMessage.setMessage("No Logged In User Found");
			respMessage.setStatus("Failure");
			respMessage.setStatusCode("1");
			response.setResponseMessage(respMessage);
		}
		return response;

	}

}
