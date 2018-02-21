package com.incture.pmc.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.Query;

import com.incture.pmc.dao.TaskOwnersDao;
import com.incture.pmc.dto.ManageTasksDto;
import com.incture.pmc.dto.ManageTasksRequestDto;
import com.incture.pmc.dto.ManageTasksResponseDto;
import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.TaskAgeingResponse;
import com.incture.pmc.dto.TaskEventsDto;
import com.incture.pmc.dto.TaskOwnersDto;
import com.incture.pmc.dto.TaskOwnersListDto;
import com.incture.pmc.dto.WorkBoxActionDto;
import com.incture.pmc.entity.TaskEventsDo;
import com.incture.pmc.entity.TaskOwnersDo;
import com.incture.pmc.responseDto.TaskEventsResponse;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;

/**
 * Session Bean implementation class TaskFacade
 */
@WebService(name = "TaskFacade", portName = "TaskFacadePort", serviceName = "TaskFacadeService", targetNamespace = "http://incture.com/pmc/services/")
@Stateless
public class TaskFacade implements TaskFacadeLocal {

	@EJB
	EntityManagerProviderLocal em;

	@EJB
	UserManagementFacadeWsdlConsumerLocal webService;

	@EJB
	private ConfigurationFacadeLocal config;

	@WebMethod(operationName = "getTaskDetailsByProcessInstance", exclude = false)
	@SuppressWarnings("unchecked")
	@Override
	public TaskEventsResponse getTaskDetailsByProcessInstance(@WebParam(name = "processId") String processId) {
		TaskEventsResponse response = null;
		List<TaskEventsDto> taskEventsDtos = null;
		List<TaskEventsDo> taskEventsDos = null;
		ResponseMessage responseMessage = new ResponseMessage();
		DateFormat formate = new SimpleDateFormat(PMCConstant.DETAILDATE_AMPM_FORMATE);
		DateFormat createdFormat = new SimpleDateFormat(PMCConstant.TASK_CREATED_FORMATE);

		if (!ServicesUtil.isEmpty(processId)) {
			Query query = em.getEntityManager()
					.createQuery("select te from TaskEventsDo te where te.taskEventsDoPK.processId =:processId");
			query.setParameter("processId", processId);
			taskEventsDos = (List<TaskEventsDo>) query.getResultList();
			if (!ServicesUtil.isEmpty(taskEventsDos)) {
				response = new TaskEventsResponse();
				taskEventsDtos = new ArrayList<TaskEventsDto>();
				for (TaskEventsDo entity : taskEventsDos) {
					TaskEventsDto taskEventsDto = new TaskEventsDto();
					taskEventsDto.setEventId(entity.getTaskEventsDoPK().getEventId());
					taskEventsDto.setProcessId(entity.getTaskEventsDoPK().getProcessId());
					taskEventsDto.setDescription(entity.getDescription());
					taskEventsDto.setName(entity.getName());
					taskEventsDto.setSubject(entity.getSubject());
					taskEventsDto.setStatus(entity.getStatus());
					taskEventsDto.setCurrentProcessor(entity.getCurrentProcessor());
					taskEventsDto.setCurrentProcessorDisplayName(entity.getCurrentProcessorDisplayName());
					taskEventsDto.setTaskType(entity.getTaskType());
					taskEventsDto.setForwardedBy(entity.getForwardedBy());
					taskEventsDto.setForwardedAt(entity.getForwardedAt());
					if (!ServicesUtil.isEmpty(entity.getForwardedAt()))
						taskEventsDto.setForwardedAtInString(formate.format(entity.getForwardedAt()));
					taskEventsDto.setStatusFlag(entity.getStatusFlag());
					taskEventsDto.setTaskMode(entity.getTaskMode());
					List<TaskOwnersDo> taskOwnersDos;
					List<String> owners = new ArrayList<String>();
					String ownersName = "";
					if (ServicesUtil.isEmpty(entity.getCompletedAt())) {
						if (!ServicesUtil.isEmpty(taskEventsDto.getProcessId())
								&& !ServicesUtil.isEmpty(taskEventsDto.getName())
								&& !ServicesUtil.isEmpty(entity.getCreatedAt())) {
							String qry = "select A.SLA AS SLA, A.URGENT_SLA AS URGENT_SLA from TASK_SLA A where TASK_DEF='"
									+ taskEventsDto.getName()
									+ "' and PROC_NAME IN( select PROC_NAME from PROCESS_EVENTS where PROCESS_ID = '"
									+ taskEventsDto.getProcessId() + "')";
							query = em.getEntityManager().createNativeQuery(qry.toString(), "taskSLA");
							List<Object[]> resultList = query.getResultList();
							System.err.println("Query: " + qry);
							if (resultList != null) {
								for (Object[] obj : resultList) {
									if (!ServicesUtil.isEmpty(obj[0])) {
										taskEventsDto.setSla((String) obj[0]);
										// taskEventsDto.setUrgentsla(obj[1] ==
										// null ? "" : (String) obj[1]);
									}
								}
								if (!ServicesUtil.isEmpty(taskEventsDto.getSla())) {
									Calendar created = ServicesUtil
											.timeStampToCal(createdFormat.format(entity.getCreatedAt()));
									Calendar slaDate = ServicesUtil.getSLADueDate(created, taskEventsDto.getSla());
									SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(
											PMCConstant.DETAIL_DATE_FORMATE);
									taskEventsDto.setSlaDisplayDate(simpleDateFormat1.format(slaDate.getTime()));
									String timeLeftString = ServicesUtil.getSLATimeLeft(slaDate);
									if (timeLeftString.equals("Breach")) {
										taskEventsDto.setBreached(true);
									} else {
										taskEventsDto.setBreached(false);
										taskEventsDto.setTimeLeftDisplayString(timeLeftString);
										taskEventsDto.setTimePercentCompleted(
												ServicesUtil.getPercntTimeCompleted(created, slaDate));
									}
								}
							}
						}
					} else {
						taskEventsDto.setCompletedAt(entity.getCompletedAt());
						taskEventsDto.setCompletedAtInString(formate.format(entity.getCompletedAt()));
					}
					if (ServicesUtil.isEmpty(taskEventsDto.getCurrentProcessor())) {
						if (!ServicesUtil.isEmpty(taskEventsDto.getEventId())) {
							query = em.getEntityManager().createQuery(
									"select tw from TaskOwnersDo tw where tw.taskOwnersDoPK.eventId =:eventId");
							query.setParameter("eventId", taskEventsDto.getEventId());
							taskOwnersDos = (List<TaskOwnersDo>) query.getResultList();
							if (!ServicesUtil.isEmpty(taskOwnersDos)) {
								for (int i = 0; i < taskOwnersDos.size(); i++) {
									owners.add(taskOwnersDos.get(i).getTaskOwnersDoPK().getTaskOwner());
									if (i == taskOwnersDos.size() - 1) {
										ownersName = ownersName + " "
												+ taskOwnersDos.get(i).getTaskOwnerDisplayName().trim();
									} else if (i == 0) {
										ownersName = ownersName + taskOwnersDos.get(i).getTaskOwnerDisplayName().trim()
												+ ",";
									} else {
										ownersName = ownersName + " "
												+ taskOwnersDos.get(i).getTaskOwnerDisplayName().trim() + ",";
									}
								}
							}
						}
					} else {
						owners.add(taskEventsDto.getCurrentProcessor());
						ownersName = taskEventsDto.getCurrentProcessorDisplayName();
					}
					taskEventsDto.setOwners(owners);
					taskEventsDto.setOwnersName(ownersName.trim());
					taskEventsDto.setPriority(entity.getPriority());
					taskEventsDto.setCreatedAt(entity.getCreatedAt());
					if (!ServicesUtil.isEmpty(entity.getCreatedAt()))
						taskEventsDto.setCreatedAtInString(formate.format(entity.getCreatedAt()));
					taskEventsDto.setCompletionDeadLine(entity.getCompletionDeadLine());
					taskEventsDtos.add(taskEventsDto);
				}
			}
			else{
				response = new TaskEventsResponse();
				responseMessage.setMessage("Process Id not exist");
				responseMessage.setStatus("Failure");
				responseMessage.setStatusCode("0");
				response.setResponseMessage(responseMessage);
				response.setTaskEventDtos(taskEventsDtos);
				return response;
			}
			Comparator<TaskEventsDto> sortByStartedAt = new Comparator<TaskEventsDto>() {
				@Override
				public int compare(TaskEventsDto o1, TaskEventsDto o2) {
					return o1.getCreatedAt().compareTo(o2.getCreatedAt());
				}
			};
			if (!ServicesUtil.isEmpty(taskEventsDos))
				Collections.sort(taskEventsDtos, sortByStartedAt);

			responseMessage.setMessage("Task Details Fetched Sucessfully");
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("1");
			response.setResponseMessage(responseMessage);
			response.setTaskEventDtos(taskEventsDtos);
			return response;
		} else {
			response = new TaskEventsResponse();
			responseMessage.setMessage("Process Id is null");
			responseMessage.setStatus("Failure");
			responseMessage.setStatusCode("0");
			response.setResponseMessage(responseMessage);
			response.setTaskEventDtos(taskEventsDtos);
			return response;
		}
	}

	@WebMethod(operationName = "getTaskAgeing", exclude = false)
	@Override
	public TaskAgeingResponse getTaskAgeing(@WebParam(name = "processName") String processName,
			@WebParam(name = "userGroup") String userGroup, @WebParam(name = "status") String status,
			@WebParam(name = "requestId") String requestId, @WebParam(name = "labelValue") String labelValue) {
		List<String> usersList = null;
		if (!ServicesUtil.isEmpty(userGroup) && !userGroup.equals(PMCConstant.SEARCH_ALL)) {
			usersList = webService.getUsersAssignedInGroup(userGroup);
		}
		processName = config.getAllProcessNamesByRoleAsString(processName);
		TaskOwnersDao dao = new TaskOwnersDao(em.getEntityManager());
		return dao.getTaskAgeing(processName, usersList, status, requestId, labelValue);
	}

	@SuppressWarnings("unchecked")
	@WebMethod(operationName = "getTasksByUserAndDuration", exclude = false)
	@Override
	public ManageTasksResponseDto getTasksByUserAndDuration(ManageTasksRequestDto request) {
		ManageTasksResponseDto response = new ManageTasksResponseDto();
		List<ManageTasksDto> tasks = new ArrayList<ManageTasksDto>();
		ResponseMessage message = new ResponseMessage();
		Date startDateFrom = null;
		Date startDateTo = null;
		try {
			startDateFrom = ServicesUtil.getDate(request.getStartDayFrom());
			startDateTo = ServicesUtil.getDate(request.getStartDayTo());
			startDateTo = ServicesUtil.setEndTime(startDateTo);
			System.err.println("startDate  - " + startDateFrom + "endDate  - " + startDateTo);
			request.setProcessName(config.getAllProcessNamesByRoleAsString(request.getProcessName()));
			StringBuffer taskStr = new StringBuffer(
					"SELECT p.REQUEST_ID AS REQUEST_ID, p.NAME AS NAME, t.EVENT_ID AS EVENT_ID, t.SUBJECT AS SUBJECT, t.CREATED_AT AS CREATED_AT, t.STATUS AS STATUS, v.TASK_OWNER AS TASK_OWNER, v.TASK_OWNER_DISP AS TASK_OWNER_DISP, c.PROCESS_DISPLAY_NAME AS PROCESS_DISP_NAME FROM PROCESS_EVENTS p LEFT OUTER JOIN PROCESS_CONFIG_TB c ON p.NAME = c.PROCESS_NAME, TASK_EVENTS t, TASK_OWNERS v where p.PROCESS_ID=t.PROCESS_ID and v.EVENT_ID=t.EVENT_ID and p.STATUS = \'IN_PROGRESS\'");
			if (!ServicesUtil.isEmpty(request.getProcessName()) || !ServicesUtil.isEmpty(request.getLabelValue())
					|| !ServicesUtil.isEmpty(request.getRequestId())) {
				taskStr.append(" and t.PROCESS_ID IN (select D.PROCESS_ID from PROCESS_EVENTS D where 1=1");
				if (!request.getProcessName().equals(PMCConstant.SEARCH_ALL))
					taskStr.append(" and D.NAME IN (" + request.getProcessName() + ")");
				if (!ServicesUtil.isEmpty(request.getLabelValue()))
					taskStr.append(" and D.SUBJECT like '%" + request.getLabelValue() + "%'");
				if (!ServicesUtil.isEmpty(request.getRequestId()))
					taskStr.append(" and D.REQUEST_ID = '" + request.getRequestId() + "'");
			}
			if (request.getStartDayFrom() >= 0 && request.getStartDayTo() >= 0) {
				DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy hh:mm:ss a");
				taskStr.append(" and t.CREATED_AT between TO_DATE('" + dateFormatter.format(startDateFrom)
						+ "', 'DD/MM/YY hh:mi:ss AM') and TO_DATE('" + dateFormatter.format(startDateTo)
						+ "', 'DD/MM/YY hh:mi:ss PM')");

				// + "'" + dateFormatter.format(startDateFrom) + "' and '" +
				// dateFormatter.format(startDateTo) + "'");
			}
			taskStr.append(")");
			if (!ServicesUtil.isEmpty(request.getOwner())) {
				if (!ServicesUtil.isEmpty(request.getTaskStatus())) {
					if (PMCConstant.SEARCH_RESERVED.equalsIgnoreCase(request.getTaskStatus())) {
						taskStr.append(" AND t.STATUS = '" + request.getTaskStatus() + "' AND t.CUR_PROC = '"
								+ request.getOwner() + "' AND v.IS_PROCESSED = \'1\'");
					} else if (PMCConstant.SEARCH_READY.equalsIgnoreCase(request.getTaskStatus())) {
						taskStr.append(" AND t.STATUS = '" + PMCConstant.TASK_STATUS_READY
								+ "' AND t.EVENT_ID IN (SELECT t.EVENT_ID FROM TASK_OWNERS v WHERE t.EVENT_ID = v.EVENT_ID AND v.TASK_OWNER = '"
								+ request.getOwner() + "')");
					} else {
						taskStr.append(" AND (t.STATUS = '" + PMCConstant.TASK_STATUS_READY
								+ "' AND t.EVENT_ID IN (SELECT t.EVENT_ID FROM TASK_OWNERS v WHERE t.EVENT_ID = v.EVENT_ID AND v.TASK_OWNER = '"
								+ request.getOwner() + "')  OR (t.STATUS = '" + PMCConstant.TASK_STATUS_RESERVED
								+ "' AND v.IS_PROCESSED = \'1\' AND t.CUR_PROC = '" + request.getOwner() + "'))");
					}
				}
			}
			Query query = em.getEntityManager().createNativeQuery(taskStr.toString(), "taskManager");
			/*
			 * if (!ServicesUtil.isEmpty(request.getPage())) { int first =
			 * (request.getPage() - 1) * PMCConstant.PAGE_SIZE; int last =
			 * PMCConstant.PAGE_SIZE; query.setFirstResult(first);
			 * query.setMaxResults(last); }
			 */
			List<Object[]> resultList = query.getResultList();
			System.err.println("Query: " + taskStr);
			if (resultList != null) {
				for (Object[] obj : resultList) {
					ManageTasksDto taskDto = new ManageTasksDto();
					taskDto.setRequestId(obj[0] == null ? "" : (String) obj[0]);
					taskDto.setProcessName(obj[1] == null ? "" : (String) obj[1]);
					taskDto.setEventId(obj[2] == null ? "" : (String) obj[2]);
					taskDto.setTaskSubject(obj[3] == null ? "" : (String) obj[3]);
					taskDto.setCreatedDate(obj[4] == "" ? null : ServicesUtil.resultAsDate(obj[4]));
					taskDto.setStatus(obj[5] == null ? "" : (String) obj[5]);
					taskDto.setOwners(obj[6] == null ? "" : (String) obj[6]);
					taskDto.setOpenSinceinHrs(ServicesUtil.getDateDifferenceInHours(ServicesUtil.resultAsDate(obj[4])));
					taskDto.setProcessDisplayName(
							obj[8] == null ? obj[1] == null ? "" : (String) obj[1] : (String) obj[8]);
					tasks.add(taskDto);
				}
			}
			HashMap<String, ManageTasksDto> map = new HashMap<String, ManageTasksDto>();
			for (ManageTasksDto dto : tasks) {
				if (map.containsKey(dto.getEventId())) {
					ManageTasksDto t = map.get(dto.getEventId());
					String owner = t.getOwners() + ", " + dto.getOwners();
					dto.setOwners(owner);
					map.put(dto.getEventId(), dto);
				} else {
					map.put(dto.getEventId(), dto);
				}
			}
			List<ManageTasksDto> tasks1 = new ArrayList<ManageTasksDto>();
			for (Entry<String, ManageTasksDto> entry : map.entrySet()) {
				tasks1.add(entry.getValue());
			}
			message.setStatus("Success");
			message.setStatusCode("0");
			message.setMessage("Process Details Fetched Successfully");
			response.setTasks(tasks1);
			response.setMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public TaskOwnersListDto getTaskOwners(WorkBoxActionDto dto) {
		System.err.println("[PMC][TaskFacade][getTaskOwners] method invoked with id " + dto.getTaskInstanceId());
		ResponseMessage message = new ResponseMessage();
		TaskOwnersListDto response = new TaskOwnersListDto();
		if (!ServicesUtil.isEmpty(dto.getTaskInstanceId())) {
			try {
				List<TaskOwnersDto> ownersList = new TaskOwnersDao(em.getEntityManager())
						.getTaskOwners(dto.getTaskInstanceId());
				if (!ServicesUtil.isEmpty(ownersList)) {
					System.err.println("[PMC][TaskFacade][getTaskOwners][ownersList] not empty" + ownersList);
					response.setOwnersList(ownersList);
					message.setMessage("Task Owners Fetched Successfully");
					message.setStatus("SUCCESS");
					message.setStatusCode("0");
				} else {
					System.err.println("[PMC][TaskFacade][getTaskOwners][ownersList] empty");
					message.setMessage("No Records available to fetch");
					message.setStatus("SUCCESS");
					message.setStatusCode("0");
				}
			} catch (Exception e) {
				message.setMessage(e.getMessage());
				message.setStatus("FAILURE");
				message.setStatusCode("1");
			}
		} else {
			message.setMessage("TaskInstanceId cannot be null to fetch Owners");
			message.setStatus("FAILURE");
			message.setStatusCode("1");
		}
		response.setMessage(message);
		return response;
	}
}