package com.incture.inbox.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;

import com.incture.pmc.dao.ProcessConfigDao;
import com.incture.pmc.dao.SubstProfileMgmtDao;
import com.incture.pmc.dto.ProcessModel;
import com.incture.pmc.dto.ProcessModelList;
import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.SubstProfileMgmtDto;
import com.incture.pmc.dto.UserProfileResponseDto;
import com.incture.pmc.entity.SubstProfileMgmtDo;
import com.incture.pmc.poadapter.services.ResponseDto;
import com.incture.pmc.poadapter.services.SubstitutionProfileDto;
import com.incture.pmc.poadapter.services.SubstitutionProfileManagerFacadeService;
import com.incture.pmc.poadapter.services.SubstitutionProfileResponse;
import com.incture.pmc.poadapter.services.TaskModelDto;
import com.incture.pmc.services.EntityManagerProviderLocal;
import com.incture.pmc.services.UserManagementFacadeWsdlConsumerLocal;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;

/**
 * Session Bean implementation class SubstitutionRuleFacadeWsdlConsumer
 */
@Stateless
public class SubstitutionProfileFacadeWsdlConsumer implements SubstitutionProfileFacadeWsdlConsumerLocal {


	public SubstitutionProfileFacadeWsdlConsumer() {
	}

	@WebServiceRef(name = "SubstitutionProfileManagerFacadeService")
	private SubstitutionProfileManagerFacadeService substitutionProfileServices;

	@EJB
	EntityManagerProviderLocal em;

	@EJB
	UserManagementFacadeWsdlConsumerLocal userService;

	@Override
	public ResponseDto createSubstitutionProfile(SubstitutionProfileDto profileDto){
		System.err.println("[PMC][SubstitutionRuleFacadeWsdlConsumer][createSubstitutionProfile][initiated]"+profileDto);
		try{
			SubstitutionProfileResponse response =  substitutionProfileServices.getSubstitutionProfileManagerFacadePort().createSubstitutionProfile(profileDto);
			SubstProfileMgmtDto dto = new SubstProfileMgmtDto();
			if(!ServicesUtil.isEmpty(response.getProfiles())){
				ResponseDto responseDto = response.getResponseMessage();
				if(responseDto.getStatus().equals("SUCCESS")){
					if(!(new SubstProfileMgmtDao(em.getEntityManager()).createProfile(convertProfileToMgmt(response.getProfiles().get(0))).equals("SUCCESS"))){
						responseDto.setStatus("FAILURE");
						responseDto.setStatusCode("1");
						responseDto.setMessage("Failed to create profile");
						deleteProfileById(dto.getProfileId());
					}
					response.setResponseMessage(responseDto);
				}
			}
			return response.getResponseMessage();
		}
		catch(Exception e){
			System.err.println("[PMC][SubstitutionRuleFacadeWsdlConsumer][createSubstitutionProfile][error]"+e.getMessage());
		}
		return null;
	}

	@Override
	public SubstitutionProfileResponse getAllProfiles(){
		return substitutionProfileServices.getSubstitutionProfileManagerFacadePort().getAllProfiles();
	}
	
	private SubstProfileMgmtDto convertProfileToMgmt(SubstitutionProfileDto profileDto){
		SubstProfileMgmtDto mgmtDto = new SubstProfileMgmtDto();
		mgmtDto.setProfileId(profileDto.getProfileId());
		mgmtDto.setProfileName(profileDto.getProfileName());
		mgmtDto.setUserId(userService.getLoggedInUser().getUserId());
		return mgmtDto;
	}

	@Override
	public ResponseDto deleteProfile(SubstitutionProfileDto profileDto){
		ResponseDto responseDto = deleteProfileById(profileDto.getProfileId());
		if(responseDto.getStatus().equals("SUCCESS")){
			if(!(new SubstProfileMgmtDao(em.getEntityManager()).deleteProfile(convertProfileToMgmt(profileDto)).equals("SUCCESS"))){
				responseDto.setStatus("FAILURE");
				responseDto.setStatusCode("1");
				responseDto.setMessage("Failed to delete profile");
			}
		}
		return responseDto;
	}

	private ResponseDto deleteProfileById(String profileId){
		return substitutionProfileServices.getSubstitutionProfileManagerFacadePort().deleteProfile(profileId);
	}

	@Override
	public SubstitutionProfileResponse getProfileById(String profileId){
		return substitutionProfileServices.getSubstitutionProfileManagerFacadePort().getProfileById(profileId);
	}

	@Override
	public SubstitutionProfileResponse getProfileByKey(String profileKey){
		return substitutionProfileServices.getSubstitutionProfileManagerFacadePort().getProfileByKey(profileKey);
	}

	@Override
	public List<TaskModelDto> getMyTaskModelIds(){
		return substitutionProfileServices.getSubstitutionProfileManagerFacadePort().getMyTaskModelIds();
	}

	@Override
	public UserProfileResponseDto getProfileByUser(){
		UserProfileResponseDto responseDto = new UserProfileResponseDto();
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setStatus("FAILURE");
		responseMessage.setStatusCode("1");
		try{
			String user = userService.getLoggedInUser().getUserId();
			if(!ServicesUtil.isEmpty(user)){
				List<SubstProfileMgmtDo> doList = new SubstProfileMgmtDao(em.getEntityManager()).getProfilesByUser(user);
				if(!ServicesUtil.isEmpty(doList)){

					List<SubstProfileMgmtDto>  dtoList =  new ArrayList<SubstProfileMgmtDto>();
					for(SubstProfileMgmtDo entity : doList){
						SubstProfileMgmtDto dto = new SubstProfileMgmtDao(em.getEntityManager()).exportDto(entity);
						dto.setProcessesSelected(getProcessesSelected(dto.getProfileId()));
						dtoList.add(dto);
					}
					responseDto.setProfileList(dtoList);
					responseMessage.setMessage("User Profiles Fetched Successfully");
				}
				else{
					responseMessage.setMessage("No Profiles exist for the user");
				}
				responseMessage.setStatus("SUCCESS");
				responseMessage.setStatusCode("0");
			}
			else{
				responseMessage.setMessage("No user is logged in to fetch profiles");
			}
		}
		catch(Exception e){
			System.err.println("[PMC][SubstitutionRuleFacadeWsdlConsumer][getProfileByUser][error]"+e.getMessage());
			responseMessage.setMessage("Fetching User Profiles Failed");
			//responseMessage.setresponseMessage("Fetching User Profiles failed because "+ e.getresponseMessage());
		}
		responseDto.setResponseMessage(responseMessage);
		return responseDto;	
	}


	@Override
	public ProcessModelList getProcessForProfile(){
		ProcessModelList processModelList = new ProcessModelList();
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setStatus("SUCCESS");
		responseMessage.setStatusCode("0");
		try {
			List<TaskModelDto> modelDtos = getMyTaskModelIds();
			if(!ServicesUtil.isEmpty(modelDtos)){
				List<ProcessModel> modelList = new ProcessConfigDao(em.getEntityManager()).getModelsByProcess(modelDtos);
				if(!ServicesUtil.isEmpty(modelList)){
					processModelList.setProcessModelList(modelList);	
					responseMessage.setMessage("Process fetched successfully");
				}
				else{
					responseMessage.setMessage(PMCConstant.NO_RESULT);
				}
			}
			else{
				responseMessage.setMessage(PMCConstant.NO_RESULT);
			}
		} catch (Exception e) {
			System.err.println("[PMC][SubstitutionProfileFacadeWsdlConsumer][getProcessForProfile][error]"+e.getMessage());
			responseMessage.setMessage("Failed because "+e.getMessage());
			responseMessage.setStatus("1");
			responseMessage.setStatus("FAILURE");
		}
		processModelList.setResponseMessage(responseMessage);
		return processModelList;
	}
	private List<String> getProcessesSelected(String profileId){
		List<TaskModelDto> dtoList = getProfileById(profileId).getProfiles().get(0).getTaskModelIds();
		String modelListString = "";
		for(TaskModelDto dto : dtoList){
			modelListString =modelListString + "'"+dto.getTaskModelName()+ "',";
		}
		modelListString = modelListString.substring(0, modelListString.length()-1);
		return  new ProcessConfigDao(em.getEntityManager()).getProcessByModel(modelListString);

	}

}
