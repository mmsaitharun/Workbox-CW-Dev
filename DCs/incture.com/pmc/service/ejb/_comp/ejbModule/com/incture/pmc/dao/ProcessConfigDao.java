package com.incture.pmc.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.incture.pmc.dto.ProcessConfigDto;
import com.incture.pmc.dto.ProcessModel;
import com.incture.pmc.entity.ProcessConfigDo;
import com.incture.pmc.poadapter.services.TaskModelDto;
import com.incture.pmc.util.ExecutionFault;
import com.incture.pmc.util.InvalidInputFault;
import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.ServicesUtil;

public class ProcessConfigDao extends BaseDao<ProcessConfigDo, ProcessConfigDto> {

	public ProcessConfigDao(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected ProcessConfigDo importDto(ProcessConfigDto fromDto) throws InvalidInputFault, ExecutionFault, NoResultFault {
		ProcessConfigDo entity = new ProcessConfigDo();
		if (!ServicesUtil.isEmpty(fromDto.getProcessName()))
			entity.setProcessName(fromDto.getProcessName());
		if (!ServicesUtil.isEmpty(fromDto.getLabelName()))
			entity.setLabelName(fromDto.getLabelName());
		if (!ServicesUtil.isEmpty(fromDto.getSla()))
			entity.setSla(fromDto.getSla());
		if (!ServicesUtil.isEmpty(fromDto.getUrgentSla()))
			entity.setUrgentSla(fromDto.getUrgentSla());
		if (!ServicesUtil.isEmpty(fromDto.getUserGroup()))
			entity.setUserGroup(fromDto.getUserGroup());
		if (!ServicesUtil.isEmpty(fromDto.getUserRole()))
			entity.setUserRole(fromDto.getUserRole());
		/*if (!ServicesUtil.isEmpty(fromDto.getProcessConfigId()))
			entity.setProcessConfigId(fromDto.getProcessConfigId());
		 */if (!ServicesUtil.isEmpty(fromDto.getLaneCount()))
			 entity.setLaneCount(fromDto.getLaneCount());
		 if (!ServicesUtil.isEmpty(fromDto.getProcessDisplayName()))
			 entity.setProcessDisplayName(fromDto.getProcessDisplayName());
		 if (!ServicesUtil.isEmpty(fromDto.getSubject()))
			 entity.setSubject(fromDto.getSubject());
		 if (!ServicesUtil.isEmpty(fromDto.getDescription()))
			 entity.setDescription(fromDto.getDescription());

		 return entity;
	}

	@Override
	protected ProcessConfigDto exportDto(ProcessConfigDo entity) {
		ProcessConfigDto processEventsDto = new ProcessConfigDto();
		if (!ServicesUtil.isEmpty(entity.getProcessName())){
			processEventsDto.setProcessName(entity.getProcessName());
			processEventsDto.setKey(entity.getProcessName());
		}if (!ServicesUtil.isEmpty(entity.getLabelName()))
			processEventsDto.setLabelName(entity.getLabelName());
		if (!ServicesUtil.isEmpty(entity.getSla()))
			processEventsDto.setSla(entity.getSla());
		if (!ServicesUtil.isEmpty(entity.getUrgentSla()))
			processEventsDto.setUrgentSla(entity.getUrgentSla());
		if (!ServicesUtil.isEmpty(entity.getUserGroup()))
			processEventsDto.setUserGroup(entity.getUserGroup());
		if (!ServicesUtil.isEmpty(entity.getUserRole()))
			processEventsDto.setUserRole(entity.getUserRole());
		if (!ServicesUtil.isEmpty(entity.getProcessDisplayName()))
			processEventsDto.setProcessDisplayName(entity.getProcessDisplayName());
		if (!ServicesUtil.isEmpty(entity.getLaneCount()))
			processEventsDto.setLaneCount(entity.getLaneCount());
		if (!ServicesUtil.isEmpty(entity.getSubject()))
			processEventsDto.setSubject(entity.getSubject());
		if (!ServicesUtil.isEmpty(entity.getDescription()))
			processEventsDto.setDescription(entity.getDescription());
		/*if(!ServicesUtil.isEmpty(entity.getProcessConfigId()))
			processEventsDto.setProcessConfigId(entity.getProcessConfigId());
		 */
		System.err.println("[PMC][process_config_det] : "+processEventsDto.toString());
		return processEventsDto;
	}

	@SuppressWarnings("unchecked")
	public List<ProcessConfigDto> getAllProcessConfigEntry() throws NoResultFault {
		List<ProcessConfigDto> processLabelDtos = new ArrayList<ProcessConfigDto>();
		//String queryName = "select pl.processName, pl.labelName, pl.userGroup, pl.processDisplayName,pl.userRole from ProcessConfigDo pl";
		String queryName = "select pl from ProcessConfigDo pl";

		List<ProcessConfigDo> doList = this.getEntityManager().createQuery(queryName).getResultList();
		if (!ServicesUtil.isEmpty(doList)) {
			String processName = "";
			ProcessConfigDto dto = new ProcessConfigDto();
			for (ProcessConfigDo entity : doList) {
				if (!entity.getProcessName().equals("ALL")) {
					if (!ServicesUtil.isEmpty(processName)) {
						processName = processName + "','" + entity.getProcessName();
					} else {
						processName = entity.getProcessName();
					}
					processLabelDtos.add(exportDto(entity));
				} else {
					dto = exportDto(entity);
				}
			}
			dto.setKey("'" + processName + "'");
			if (!ServicesUtil.isEmpty(processLabelDtos)) {
				processLabelDtos.add(dto);
			}
		}
		return processLabelDtos;
	}


	@SuppressWarnings("unchecked")
	public List<ProcessConfigDto> getAllProcessConfigEntryByRole(String user) throws NoResultFault {
		List<ProcessConfigDto> processLabelDtos = new ArrayList<ProcessConfigDto>();
		String queryName = "select pl from ProcessConfigDo pl where (pl.userRole like '%" + user
				+ "%') or ( pl.processName='ALL')";
		List<ProcessConfigDo> doList = this.getEntityManager().createQuery(queryName).getResultList();
		if (!ServicesUtil.isEmpty(doList)) {
			ProcessConfigDto dto = new ProcessConfigDto();
			for (ProcessConfigDo entity : doList) {
				if (!entity.getProcessName().equals("ALL")) {
					processLabelDtos.add(exportDto(entity));
				} else {
					dto = exportDto(entity);
					dto.setProcessName("All");
					dto.setKey("All");
					processLabelDtos.add(dto);
				}
			}
		}
		return processLabelDtos;
	}

	@SuppressWarnings("unchecked")
	public String getAllProcessNamesByRoleAsString(String user) throws NoResultFault {
		String queryName = "select pl.processName from ProcessConfigDo pl where (pl.userRole like '%" + user
				+ "%')";
		List<String> doList = this.getEntityManager().createQuery(queryName).getResultList();
		String returnString = "";
		for(String s : doList){
			returnString = returnString+"'"+ s+"',";
		}
		if(!returnString.equals("")){
			returnString = returnString.substring(0,returnString.length()-1);
		}
		return returnString;
	}



	/*public void updateForAdminConsole(ProcessConfigDto processConfigDto)
	{

		ProcessConfigDo entity = new ProcessConfigDo();
		if (!ServicesUtil.isEmpty(processConfigDto.getProcessName()))
			entity.setProcessName(processConfigDto.getProcessName());
		if (!ServicesUtil.isEmpty(processConfigDto.getProcessDisplayName()))
				entity.setProcessDisplayName(processConfigDto.getProcessDisplayName());
		if (!ServicesUtil.isEmpty(processConfigDto.getLabelName()))
			entity.setLabelName(processConfigDto.getLabelName());
		if (!ServicesUtil.isEmpty(processConfigDto.getUserGroup()))
			entity.setUserGroup(processConfigDto.getUserGroup());

		System.err.println("Update for admin console process dto is successfully done");
	}*/


	@SuppressWarnings({ "unchecked"})
	public List<ProcessModel> getModelsByProcess(List<TaskModelDto> modelDtoList) {
		String queryString = "Select p.PROCESS_DISPLAY_NAME AS PROCESS_DISPLAY_NAME,p.PROCESS_NAME AS PROCESS_NAME,s.TASK_DEF AS TASK_DEF from PROCESS_CONFIG_TB p , TASK_SLA s where p.PROCESS_NAME = s.PROC_NAME and  p.PROCESS_NAME <> 'ALL'";
		List<Object[]> resultList = this.getEntityManager().createNativeQuery(queryString.trim(), "processModelResult").getResultList();
		if(!ServicesUtil.isEmpty(resultList)){
			Map<String , Integer> processMap = new HashMap<String , Integer>(); 
			List<ProcessModel> processModelList = new ArrayList<ProcessModel>();
			ProcessModel processModelDto = null;
			List<TaskModelDto> modelList = null;
			for(Object[] obj : resultList){
				TaskModelDto modelDto = new TaskModelDto();
				for(TaskModelDto dto : modelDtoList){

					if(dto.getTaskModelName().equals((String) obj[2])){
						if (processMap.containsKey(obj[1])) {
							processModelList.get(processMap.get(obj[1])).getModelList().add(modelDto);
						} else {
							processModelDto = new ProcessModel();
							processModelDto.setProcessDisplayName((String) obj[0]);
							processModelDto.setProcessName((String) obj[1]);	
							modelList =  new ArrayList<TaskModelDto>();
							modelList.add(dto);
							processModelDto.setModelList(modelList);
							processModelList.add(processModelDto);
							processMap.put((String) obj[1], processModelList.size() - 1);
						}
						modelDtoList.remove(dto);
						break;
					}

				}
				//			List<TaskModelDto> md = modelDtoList.stream().filter(new Predicate<TaskModelDto>() {
				//				@Override
				//				public boolean test(TaskModelDto p) {
				//					return p.getTaskModelName().equals((String) obj[2]);
				//				}
				//			}).collect(Collectors.toList());
				//		
				//				TaskModelDto mddd = new TaskModelDto();
				//				mddd.setTaskModelName((String) obj[2]);
				//				System.err.println("Index" + modelDtoList.indexOf(mddd));

			}
			return processModelList;
		}
		return null;
	}

//	@SuppressWarnings("unchecked")
//	public List<ProcessModel> getProcessByModel(String modelListString) {
//		List<ProcessModel> processModelList = new ArrayList<ProcessModel>();
//		String queryString = "Select p.PROCESS_DISPLAY_NAME AS PROCESS_DISPLAY_NAME,p.PROCESS_NAME AS PROCESS_NAME,s.TASK_DEF AS TASK_DEF from PROCESS_CONFIG_TB p , TASK_SLA s where p.PROCESS_NAME = s.PROC_NAME and  p.PROCESS_NAME <> 'ALL' and s.TASK_DEF IN("+modelListString+")";
//		List<Object[]> resultList = this.getEntityManager().createNativeQuery(queryString.trim(), "processModelResult").getResultList();
//		if(!ServicesUtil.isEmpty(resultList)){
//			Map<String , Integer> processMap = new HashMap<String , Integer>(); 
//			ProcessModel processModelDto = null;
//			List<TaskModelDto> modelList = null;
//			for(Object[] obj : resultList){
//				TaskModelDto modelDto = new TaskModelDto();
//				modelDto.setTaskModelName((String) obj[3]);
//				if (processMap.containsKey(obj[1])) {
//					processModelList.get(processMap.get(obj[1])).getModelList().add(modelDto);
//				} else {
//					processModelDto = new ProcessModel();
//					processModelDto.setProcessDisplayName((String) obj[0]);
//					processModelDto.setProcessName((String) obj[1]);	
//					modelList =  new ArrayList<TaskModelDto>();
//					modelList.add(modelDto);
//					processModelDto.setModelList(modelList);
//					processModelList.add(processModelDto);
//					processMap.put((String) obj[1], processModelList.size() - 1);
//				}
//
//			}
//		}
//		return processModelList;
//	}

	
	@SuppressWarnings("unchecked")
	public List<String> getProcessByModel(String modelListString) {
		
		String queryString = "Select Distinct p.processDisplayName from ProcessConfigDo p , SlaManagementDo s where p.processName = s.processName and  p.processName <> 'ALL' and s.taskName IN ("+modelListString+")";
		List<String> resultList = this.getEntityManager().createQuery(queryString.trim()).getResultList();
		return resultList;
	}


}
