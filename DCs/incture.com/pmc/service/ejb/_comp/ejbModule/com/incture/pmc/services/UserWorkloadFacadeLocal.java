package com.incture.pmc.services;

import javax.ejb.Local;

import com.incture.pmc.dto.TaskCountDto;
import com.incture.pmc.dto.UserProcessDetailRequestDto;
import com.incture.pmc.dto.UserSearchRequestDto;
import com.incture.pmc.dto.UserTaskStatusResponseDto;
import com.incture.pmc.dto.UserWorkloadResponseDto;

@Local
public interface UserWorkloadFacadeLocal {

	UserWorkloadResponseDto getUserWorkLoadHeatMap(UserSearchRequestDto request);

	TaskCountDto getUserWorkLoadTrendGraph(UserProcessDetailRequestDto request);

	UserTaskStatusResponseDto getUserWorkLoadTaskStausGraph(UserProcessDetailRequestDto request);
}
