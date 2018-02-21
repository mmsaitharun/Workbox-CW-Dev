package com.incture.pmc.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.incture.inbox.services.SubstitutionProfileFacadeWsdlConsumerLocal;
import com.incture.pmc.dto.ProcessModelList;
import com.incture.pmc.dto.SubstitutionProfileInputDto;
import com.incture.pmc.dto.UserProfileResponseDto;
import com.incture.pmc.poadapter.services.ResponseDto;
import com.incture.pmc.poadapter.services.SubstitutionProfileDto;
import com.incture.pmc.poadapter.services.SubstitutionProfileResponse;
import com.incture.pmc.poadapter.services.TaskModelDto;

@Path("/profile")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class SubstitutionProfileRest {

	@EJB
	private SubstitutionProfileFacadeWsdlConsumerLocal profileServices;

	
	@POST
	@Path("/create")
	public ResponseDto createSubstitutionProfile(SubstitutionProfileDto profileDto){
		return profileServices.createSubstitutionProfile(profileDto);
	}
	
	@GET
	@Path("/getAllProfiles")
	public SubstitutionProfileResponse getAllProfiles(){
		return profileServices.getAllProfiles();
	}
	
	@POST
	@Path("/delete")
	public ResponseDto deleteProfile(SubstitutionProfileDto profileDto){
		return profileServices.deleteProfile(profileDto);
	}
	
	@POST
	@Path("/getProfileById")
	public SubstitutionProfileResponse getProfileById(SubstitutionProfileInputDto profileDto){
		return profileServices.getProfileById(profileDto.getProfileId());
	}
	
	@POST
	@Path("/getProfileByKey")
	public SubstitutionProfileResponse getProfileByKey(SubstitutionProfileInputDto profileDto){
		return profileServices.getProfileByKey(profileDto.getProfileKey());
	}
	
	@GET
	@Path("/getMyTaskModelIds")
	public List<TaskModelDto> getMyTaskModelIds(){
		return profileServices.getMyTaskModelIds();
	}
	
	@GET
	@Path("/myProfiles")
	public UserProfileResponseDto getProfileByUser(){
		return profileServices.getProfileByUser();
	}
	
	@GET
	@Path("/getProcessForProfile")
	public ProcessModelList getProcessForProfile(){
		return  profileServices.getProcessForProfile();
	}
	
}
