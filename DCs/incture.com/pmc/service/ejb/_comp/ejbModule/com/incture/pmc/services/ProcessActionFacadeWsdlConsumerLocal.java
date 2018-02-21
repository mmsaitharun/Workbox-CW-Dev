package com.incture.pmc.services;

import javax.ejb.Local;

import com.incture.pmc.dto.ProcessActionDto;
import com.incture.pmc.dto.ResponseMessage;

@Local
public interface ProcessActionFacadeWsdlConsumerLocal {

	ResponseMessage cancelProcess(ProcessActionDto processList);

}
