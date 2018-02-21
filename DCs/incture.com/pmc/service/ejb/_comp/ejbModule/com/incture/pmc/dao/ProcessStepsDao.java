package com.incture.pmc.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.incture.pmc.dto.ProcessStepsDto;
import com.incture.pmc.entity.ProcessStepsDo;
import com.incture.pmc.util.ExecutionFault;
import com.incture.pmc.util.InvalidInputFault;
import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.ServicesUtil;

public class ProcessStepsDao extends BaseDao<ProcessStepsDo, ProcessStepsDto> {
	public ProcessStepsDao(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected ProcessStepsDo importDto(ProcessStepsDto fromDto) throws InvalidInputFault, ExecutionFault, NoResultFault {
		ProcessStepsDo entity = new ProcessStepsDo();
		if (!ServicesUtil.isEmpty(fromDto.getProcessId()))
			entity.setProcessId(fromDto.getProcessId());
		if (!ServicesUtil.isEmpty(fromDto.getTaskStep()))
			entity.setTaskStep(fromDto.getTaskStep());
		if (!ServicesUtil.isEmpty(fromDto.getActivityType()))
			entity.setActivityType(fromDto.getActivityType());
		if (!ServicesUtil.isEmpty(fromDto.getProcessName()))
			entity.setProcessName(fromDto.getProcessName());
		if (!ServicesUtil.isEmpty(fromDto.getSubject()))
			entity.setSubject(fromDto.getSubject());
		if (!ServicesUtil.isEmpty(fromDto.getDescription()))
			entity.setDescription(fromDto.getDescription());
		return entity;
	}

	@Override
	protected ProcessStepsDto exportDto(ProcessStepsDo entity) {
		ProcessStepsDto processStepsDto = new ProcessStepsDto();
		if (!ServicesUtil.isEmpty(entity.getProcessId()))
			processStepsDto.setProcessId(entity.getProcessId());
		if (!ServicesUtil.isEmpty(entity.getTaskStep()))
			processStepsDto.setTaskStep(entity.getTaskStep());
		if (!ServicesUtil.isEmpty(entity.getActivityType()))
			processStepsDto.setActivityType(entity.getActivityType());
		if (!ServicesUtil.isEmpty(entity.getProcessName()))
			processStepsDto.setProcessName(entity.getProcessName());
		if (!ServicesUtil.isEmpty(entity.getSubject()))
			processStepsDto.setSubject(entity.getSubject());
		if (!ServicesUtil.isEmpty(entity.getDescription()))
			processStepsDto.setDescription(entity.getDescription());
		return processStepsDto;
	}

	public List<ProcessStepsDto> getProcessSteps(String processName) {
		List<ProcessStepsDto> processStepsDtos = null;
		try {
			processStepsDtos = getSpecificActiveResults("ProcessStepsDo", "processName", processName);
		} catch (NoResultFault e) {
			e.printStackTrace();
		}
		return processStepsDtos;

	}
}
