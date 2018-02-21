package com.incture.pmc.dao;

import javax.persistence.EntityManager;

import com.incture.pmc.dto.WorkloadRangeDto;
import com.incture.pmc.entity.WorkloadRangeDo;
import com.incture.pmc.util.ExecutionFault;
import com.incture.pmc.util.InvalidInputFault;
import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.ServicesUtil;

public class WorkloadRangeDao extends BaseDao<WorkloadRangeDo, WorkloadRangeDto> {

	public WorkloadRangeDao(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected WorkloadRangeDo importDto(WorkloadRangeDto fromDto) throws InvalidInputFault, ExecutionFault, NoResultFault {
		WorkloadRangeDo workloadRangeDo = new WorkloadRangeDo();
		if (!ServicesUtil.isEmpty(fromDto.getLoadType())) {
			workloadRangeDo.setLoadType(fromDto.getLoadType());
			if (!ServicesUtil.isEmpty(fromDto.getHighLimit()))
				workloadRangeDo.setHighLimit(fromDto.getHighLimit());
			if (!ServicesUtil.isEmpty(fromDto.getLowLimit()))
				workloadRangeDo.setLowLimit(fromDto.getLowLimit());
		}

		return workloadRangeDo;
	}

	@Override
	protected WorkloadRangeDto exportDto(WorkloadRangeDo entity) {
		WorkloadRangeDto workloadRangeDto = new WorkloadRangeDto();
		workloadRangeDto.setLoadType(entity.getLoadType());
		if (!ServicesUtil.isEmpty(entity.getHighLimit()))
			workloadRangeDto.setHighLimit(entity.getHighLimit());
		if (!ServicesUtil.isEmpty(entity.getLowLimit()))
			workloadRangeDto.setLowLimit(entity.getLowLimit());
		return workloadRangeDto;
	}

}
