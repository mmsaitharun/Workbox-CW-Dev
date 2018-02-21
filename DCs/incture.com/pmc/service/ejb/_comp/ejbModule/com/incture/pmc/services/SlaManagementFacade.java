package com.incture.pmc.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.incture.pmc.dao.SlaManagementDao;
import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.SlaListDto;
import com.incture.pmc.responseDto.SlaProcessNamesResponse;

/**
 * Session Bean implementation class ConfigurationFacade
 */
@Stateless
public class SlaManagementFacade implements SlaManagementFacadeLocal {

	@EJB
	EntityManagerProviderLocal em;
	

	@EJB
	UserManagementFacadeWsdlConsumerLocal ume;

	@Override
	public SlaProcessNamesResponse getAllProcessNames() {
		SlaProcessNamesResponse response = new SlaProcessNamesResponse();
		ResponseMessage responseMessage = new ResponseMessage();
		response.setSlaProcessNames(new SlaManagementDao(em.getEntityManager()).getSlaProcessList(ume.getLoggedInUser().getUserId()));
		responseMessage.setMessage("Sla Processes List Fetched Successfully");
		responseMessage.setStatus("SUCCESS");
		responseMessage.setStatusCode("1");
		response.setResponseMessage(responseMessage);
		return response;
	}


	@Override
	public SlaListDto getSlaDetails(String processName) {
		SlaManagementDao slaManagementDao = new SlaManagementDao(em.getEntityManager());
		SlaListDto  slaList = slaManagementDao.getDetailSla(processName);
		return slaList;
	}

	@Override
	public ResponseMessage updateSla(SlaListDto dto) {
		return new SlaManagementDao(em.getEntityManager()).updateSla(dto);
	}
	
}
