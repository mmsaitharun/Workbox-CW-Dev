package com.incture.pmc.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;

import com.incture.pmc.dto.ProcessActionDto;
import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.poadapter.services.ProcessActionFacadeService;
import com.incture.pmc.util.ServicesUtil;

/**
 * Session Bean implementation class UserManagementFacadeWsdlConsumer
 */
@Stateless
public class ProcessActionFacadeWsdlConsumer implements ProcessActionFacadeWsdlConsumerLocal {

	public ProcessActionFacadeWsdlConsumer() {
	}

	@WebServiceRef(name = "ProcessActionFacadeService")
	private ProcessActionFacadeService actionServices;

	@Override
	public ResponseMessage cancelProcess(ProcessActionDto processList) {
		System.err.println("[PMC][services][processAction][cancelProcess] method invoked with input" + processList);
		ResponseMessage responseDto = new ResponseMessage();
		
		List<String> processInstanceList = processList.getProcessInstanceList(); 
		if(!ServicesUtil.isEmpty(processInstanceList)){
			for(String processInstance : processInstanceList){
				System.err.println("[PMC][services][processAction][cancelProcess] in loop  with instance " + processInstance);
				if (!ServicesUtil.isEmpty(processInstance)) {
					try {
						System.err.println("[PMC][services][processAction][cancelProcess][taskInstanceId] " + processInstance);
						responseDto.setMessage(actionServices.getProcessActionFacadePort().cancelProcess(processInstance));
					} catch (Exception e) {
						responseDto.setMessage("Process cancellation Failed because" + e.getMessage());
					}
				} else {
					responseDto.setMessage("Instance Id required to cancel");
					break;
				}
			} 
		}
		else {
			responseDto.setMessage(" No Instance Id sent to to cancel");
		}
		if(responseDto.getMessage().equals("SUCCESS")){
			responseDto.setStatus("SUCCESS");
			responseDto.setStatusCode("0");	
		}else{
			responseDto.setStatus("FAILURE");
			responseDto.setStatusCode("1");
		}
		return responseDto;
	}


}
