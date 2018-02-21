package com.incture.pmc.poadapter.services;

import javax.ejb.Local;

import com.incture.pmc.poadapter.dto.CustomAttributeDto;

@Local
public interface CustomAttributesServiceLocal {

	CustomAttributeDto getCustomAttributes(String taskId);

}
