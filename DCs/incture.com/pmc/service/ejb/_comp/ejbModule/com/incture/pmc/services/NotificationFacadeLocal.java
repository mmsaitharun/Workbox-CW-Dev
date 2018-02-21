package com.incture.pmc.services;

import javax.ejb.Local;

import com.incture.pmc.dto.MailRequestDto;
import com.incture.pmc.dto.ResponseMessage;

@Local
public interface NotificationFacadeLocal {

	public ResponseMessage sendNotification(MailRequestDto requestDto);

	ResponseMessage sendProcessRemainder();

	ResponseMessage sendTaskRemainder();

}
