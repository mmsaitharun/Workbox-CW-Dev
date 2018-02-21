package com.incture.inbox.services;

import javax.ejb.Local;

import com.incture.pmc.dto.TrackingResponse;
import com.incture.pmc.dto.WorkboxResponseDto;

@Local
public interface InboxFacadeLocal {

	WorkboxResponseDto getWorkboxFilterData(String processName, String requestId, String createdBy, String createdAt,
			String status, Integer skipCount, Integer maxCount, Integer page, String orderBy, String orderType);

	WorkboxResponseDto getWorkboxCompletedFilterData(String processName, String requestId, String createdBy,
			String createdAt, String completedAt, String period, Integer skipCount, Integer maxCount, Integer page);

	TrackingResponse getTrackingResults();

}
