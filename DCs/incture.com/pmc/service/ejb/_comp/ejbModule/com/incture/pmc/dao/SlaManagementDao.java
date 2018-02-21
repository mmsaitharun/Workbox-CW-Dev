package com.incture.pmc.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.SlaListDto;
import com.incture.pmc.dto.SlaManagementDto;
import com.incture.pmc.dto.SlaProcessNameListDto;
import com.incture.pmc.entity.ProcessConfigDo;
import com.incture.pmc.entity.SlaManagementDo;
import com.incture.pmc.util.ExecutionFault;
import com.incture.pmc.util.InvalidInputFault;
import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.ServicesUtil;

public class SlaManagementDao extends BaseDao<SlaManagementDo, SlaManagementDto> {
	public SlaManagementDao(EntityManager entityManager) {
		super(entityManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.incture.pmc.dao.BaseDao#importDto(com.incture.pmc.dto.BaseDto)
	 */
	@Override
	protected SlaManagementDo importDto(SlaManagementDto fromDto)
			throws InvalidInputFault, ExecutionFault, NoResultFault {
		SlaManagementDo entity = new SlaManagementDo();
		String sla = "";
		if (!ServicesUtil.isEmpty(fromDto.getSlaId()))
			entity.setSlaId(fromDto.getSlaId());
		if (!ServicesUtil.isEmpty(fromDto.getProcessName()))
			entity.setProcessName(fromDto.getProcessName());
		if (!ServicesUtil.isEmpty(fromDto.getTaskType()))
			entity.setTaskType(fromDto.getTaskType());
		if (!ServicesUtil.isEmpty(fromDto.getModeName()))
			entity.setModeName(fromDto.getModeName());
		if (!ServicesUtil.isEmpty(fromDto.getTaskName()))
			entity.setTaskName(fromDto.getTaskName());
		if (!ServicesUtil.isEmpty(fromDto.getDescription()))
			entity.setDescription(fromDto.getDescription());
		if (!ServicesUtil.isEmpty(fromDto.getSubject()))
			entity.setSubject(fromDto.getSubject());
		if (!ServicesUtil.isEmpty(fromDto.getSlaCount()) && !ServicesUtil.isEmpty(fromDto.getSlaUnit())) {
			sla = fromDto.getSlaCount() + " " + fromDto.getSlaUnit();
			entity.setSla(sla);
		}
		if (!ServicesUtil.isEmpty(fromDto.getUrgentSlaCount()) && !ServicesUtil.isEmpty(fromDto.getUrgentSlaUnit())) {
			sla = fromDto.getUrgentSlaCount() + " " + fromDto.getUrgentSlaUnit();
			entity.setUrgentSla(sla);
		}
		return entity;
	}

	@Override
	protected SlaManagementDto exportDto(SlaManagementDo entity) {
		SlaManagementDto slaManagementDto = new SlaManagementDto();
		String[] sla, urgentSla;
		if (!ServicesUtil.isEmpty(entity.getSlaId()))
			slaManagementDto.setSlaId(entity.getSlaId());
		if (!ServicesUtil.isEmpty(entity.getProcessName()))
			slaManagementDto.setProcessName(entity.getProcessName());
		if (!ServicesUtil.isEmpty(entity.getModeName()))
			slaManagementDto.setModeName(entity.getModeName());
		if (!ServicesUtil.isEmpty(entity.getTaskName()))
			slaManagementDto.setTaskName(entity.getTaskName());
		if (!ServicesUtil.isEmpty(entity.getTaskType()))
			slaManagementDto.setTaskType(entity.getTaskType());
		if (!ServicesUtil.isEmpty(entity.getDescription()))
			slaManagementDto.setDescription(entity.getDescription());
		if (!ServicesUtil.isEmpty(entity.getSubject()))
			slaManagementDto.setSubject(entity.getSubject());

		if (!ServicesUtil.isEmpty(entity.getSla())) {
			sla = entity.getSla().split("\\s");
			if (!ServicesUtil.isEmpty(sla[1])) {
				slaManagementDto.setSlaCount(sla[0]);
				slaManagementDto.setSlaUnit(sla[1]);
				slaManagementDto.setSlaCountOld(sla[0]);
				slaManagementDto.setSlaUnitOld(sla[1]);
			}
		}
		if (!ServicesUtil.isEmpty(entity.getUrgentSla())) {
			urgentSla = entity.getUrgentSla().split("\\s");
			if (!ServicesUtil.isEmpty(urgentSla[1])) {
				slaManagementDto.setUrgentSlaCount(urgentSla[0]);
				slaManagementDto.setUrgentSlaUnit(urgentSla[1]);
				slaManagementDto.setUrgentSlaCountOld(urgentSla[0]);
				slaManagementDto.setUrgentSlaUnitOld(urgentSla[1]);
			}
		}
		return slaManagementDto;
	}

	@SuppressWarnings("unchecked")
	public List<SlaProcessNameListDto> getAllProcessName(String user) throws NoResultFault {
		System.err.println("[PMC][SlaManagementDao][getAllProcessName] method invoked ");
		Query query = this.getEntityManager().createQuery("select p from ProcessConfigDo p where p.userRole like '%"+user+"%'");
		List<ProcessConfigDo> processNameList = (List<ProcessConfigDo>) query.getResultList();
		List<SlaProcessNameListDto> slaProcessList = new ArrayList<SlaProcessNameListDto>();
		
		if (!ServicesUtil.isEmpty(processNameList)) {
		System.err.println("[PMC][SlaManagementDao][getAllProcessName] processList not empty ");
		for (ProcessConfigDo entity : processNameList) {
			if(!entity.getProcessName().equals("ALL")){
			SlaProcessNameListDto dto = new SlaProcessNameListDto();
			dto.setProcessName(entity.getProcessName());
			dto.setProcessDisplayName(entity.getProcessDisplayName());
			if (!ServicesUtil.isEmpty(entity.getSla())) {
			String[] sla = entity.getSla().split("\\s");
				if (!ServicesUtil.isEmpty(sla[1])) {
					dto.setSlaCount(sla[0]);
					dto.setSlaUnit(sla[1]);
				}
			}
			if (!ServicesUtil.isEmpty(entity.getSla())) {
				dto.setSlaExist(true);
			} else {
				dto.setSlaExist(false);
			}
			slaProcessList.add(dto);
			System.err.println("[PMC][SlaManagementDao][getAllProcessName] entity " + entity);
			}
		}
		}
		else{
			System.err.println("[PMC][SlaManagementDao][getAllProcessName] processList is empty ");
			throw new NoResultFault("NO RECORD FOUND");
		}
		
		return slaProcessList;
	}

	/**
	 * @param processName
	 *            -
	 * @return
	 * @throws NoResultFault
	 */
	public Long getNoOfInstances(String processName) {
		Query query = this.getEntityManager()
				.createQuery("select count(DISTINCT p.processId) from ProcessEventsDo p where p.name =:processName");
		query.setParameter("processName", processName);
		Long instanceCount = (Long) query.getSingleResult();
		return instanceCount;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SlaProcessNameListDto> getSlaProcessList(String user) {
		try {
			List<SlaProcessNameListDto> resultset = this.getAllProcessName(user);
			Query query = this.getEntityManager().createQuery(
					"select DISTINCT p.processName from SlaManagementDo p where p.sla is not null or p.urgentSla is not null ");
			List<String> slaProcessNameList = (List<String>) query.getResultList();
			if (!ServicesUtil.isEmpty(slaProcessNameList)) {
				for (SlaProcessNameListDto entity : resultset) {
					if (!entity.getSlaExist() && slaProcessNameList.contains(entity.getProcessName())) {
						entity.setSlaExist(true);
					}
				}
				return resultset;
			} else {
				return resultset;
			}
		} catch (NoResultFault e) {
			System.err.println("NO PROCESS FOUND");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public SlaListDto getDetailSla(String processName) {
		System.err.println("[PMC][SlaManagementDao][getDetailSla] Method Initiated with Inputs :" + processName);
		SlaListDto slaDetailDto = new SlaListDto();

		 Query query = this.getEntityManager().createQuery("select p from SlaManagementDo p where p.processName =:processName and upper(p.taskName) <> 'END' and upper(p.taskName) <> 'START'");
		 query.setParameter("processName", processName);
		
		List<SlaManagementDo> slaDoList = (List<SlaManagementDo>) query.getResultList();
		System.err.println("Size : " + slaDoList.size());
		System.err.println("[PMC][SlaManagementDao][getDetailSla] Sla List is empty check");
		if (!ServicesUtil.isEmpty(slaDoList)) {
			System.err.println("[PMC][SlaManagementDao][getDetailSla] Sla List isnt empty");
			List<SlaManagementDto> slaDtoList = new ArrayList<SlaManagementDto>();
			for (SlaManagementDo entity : slaDoList) {
				System.err.println("SlaManagementDto" + entity.toString());
				if (!ServicesUtil.isEmpty(entity.getTaskName())) {
					slaDtoList.add(exportDto(entity));
				}
			}
			slaDetailDto.setSlaList(slaDtoList);
		}
		Query query1 = this.getEntityManager()
				.createQuery("select p from ProcessConfigDo p where  p.processName = :processName");
		query1.setParameter("processName", processName);
		List<ProcessConfigDo> headerSlas = (List<ProcessConfigDo>) query1.getResultList();
		for (ProcessConfigDo headerSla : headerSlas) {
			if (!ServicesUtil.isEmpty(headerSla.getSla())) {
				slaDetailDto.setSlaCount(headerSla.getSla().split("\\s")[0]);
				slaDetailDto.setSlaUnit(headerSla.getSla().split("\\s")[1]);
				slaDetailDto.setSlaCountOld(headerSla.getSla().split("\\s")[0]);
				slaDetailDto.setSlaUnitOld(headerSla.getSla().split("\\s")[1]);
			}
			slaDetailDto.setProcessDisplayName(headerSla.getProcessDisplayName());
		}
		slaDetailDto.setNoOfInstances(this.getNoOfInstances(processName).toString());
		slaDetailDto.setProcessName(processName);
		return slaDetailDto;
	}

	public ResponseMessage updateSla(SlaListDto slaDto) {

		List<SlaManagementDto> slaDtoList = slaDto.getSlaList();
		ResponseMessage response = new ResponseMessage();
		if (!ServicesUtil.isEmpty(slaDto.getIsUpdated()) && slaDto.getIsUpdated() == true) {
			response.setStatus(this.headerUpdate(slaDto));
		}

		if (!ServicesUtil.isEmpty(slaDtoList)) {
			for (SlaManagementDto dto : slaDtoList) {
				System.err.println("[PMC][SlaManagementDao][updateSla][updatedDto]" + dto.toString());
				if(!ServicesUtil.isEmpty(dto.getSlaId())){
				Query query = this.getEntityManager().createQuery(
						"update SlaManagementDo p set p.sla = :sla , p.urgentSla = :urgentSla  where  p.slaId =:slaId");
				query.setParameter("slaId", dto.getSlaId());
				if (!ServicesUtil.isEmpty(dto.getSlaCount()) && !ServicesUtil.isEmpty(dto.getSlaUnit())) {
					query.setParameter("sla", dto.getSlaCount() + " " + dto.getSlaUnit());
				} else {
					query.setParameter("sla", null);
				}
				if (!ServicesUtil.isEmpty(dto.getUrgentSlaCount()) && !ServicesUtil.isEmpty(dto.getUrgentSlaUnit())) {
					query.setParameter("urgentSla", dto.getUrgentSlaCount() + " " + dto.getUrgentSlaUnit());
				} else {
					query.setParameter("urgentSla", null);
				}

				Integer i = query.executeUpdate();
				}
				else{
				response.setMessage("Newly added item is sent as update");
				return response;
				}
			}
		} else {
			response.setMessage("NO UPDATE EXIST");
			return response;
		}

		response.setMessage("UPDATED SUCCESSFULLY");
		return response;
	}

	public String headerUpdate(SlaListDto dto) {
		Query query = this.getEntityManager().createQuery("update ProcessConfigDo p set p.sla =:sla where p.processName=:processName");
		if (!ServicesUtil.isEmpty(dto.getSlaCount()) && !ServicesUtil.isEmpty(dto.getSlaUnit())) {
			query.setParameter("sla", dto.getSlaCount() + " " + dto.getSlaUnit());
		} else {
			query.setParameter("sla", null);
		}
		query.setParameter("processName", dto.getProcessName());
		Integer i = query.executeUpdate();
		return "S";
	}
	
	
}
