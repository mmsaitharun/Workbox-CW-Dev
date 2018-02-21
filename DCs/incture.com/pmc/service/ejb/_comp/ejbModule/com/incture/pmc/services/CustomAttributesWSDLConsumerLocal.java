package com.incture.pmc.services;

import javax.ejb.Local;

import com.incture.pmc.poadapter.services.CustomAttributeDto;

@Local
public interface CustomAttributesWSDLConsumerLocal {

	CustomAttributeDto getCUstomAttribute(String taskId);

}
