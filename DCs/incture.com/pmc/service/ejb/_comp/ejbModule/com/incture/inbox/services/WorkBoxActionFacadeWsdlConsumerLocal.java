package com.incture.inbox.services;

import javax.ejb.Local;

import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.WorkBoxActionDto;
import com.incture.pmc.dto.WorkBoxActionListDto;

@Local
public interface WorkBoxActionFacadeWsdlConsumerLocal {

	ResponseMessage claimTask(WorkBoxActionDto  dto);

	ResponseMessage release(WorkBoxActionDto  dto);

	ResponseMessage delegate(WorkBoxActionDto  dto);

	ResponseMessage addNote(WorkBoxActionDto  dto);

	ResponseMessage claimAndDelegate(WorkBoxActionDto dto);

	ResponseMessage nominate(WorkBoxActionDto dto);

	ResponseMessage complete(WorkBoxActionListDto dtoList);

}
