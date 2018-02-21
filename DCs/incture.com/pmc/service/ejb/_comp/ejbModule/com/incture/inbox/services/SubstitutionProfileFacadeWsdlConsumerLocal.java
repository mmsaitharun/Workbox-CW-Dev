package com.incture.inbox.services;

import java.util.List;

import javax.ejb.Local;

import com.incture.pmc.dto.ProcessModelList;
import com.incture.pmc.dto.UserProfileResponseDto;
import com.incture.pmc.poadapter.services.ResponseDto;
import com.incture.pmc.poadapter.services.SubstitutionProfileDto;
import com.incture.pmc.poadapter.services.SubstitutionProfileResponse;
import com.incture.pmc.poadapter.services.TaskModelDto;

@Local
public interface SubstitutionProfileFacadeWsdlConsumerLocal {

	ResponseDto createSubstitutionProfile(SubstitutionProfileDto profileDto);

	SubstitutionProfileResponse getAllProfiles();

	SubstitutionProfileResponse getProfileById(String profileId);

	SubstitutionProfileResponse getProfileByKey(String profileKey);

	List<TaskModelDto> getMyTaskModelIds();

	UserProfileResponseDto getProfileByUser();

	ProcessModelList getProcessForProfile();

	ResponseDto deleteProfile(SubstitutionProfileDto profileDto);

}
