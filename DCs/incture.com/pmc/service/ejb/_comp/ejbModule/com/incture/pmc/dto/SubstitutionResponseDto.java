package com.incture.pmc.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.poadapter.services.SubstitutionRuleDto;

@XmlRootElement
public class SubstitutionResponseDto {

	List<SubstitutionRuleDto> ruleDto;
	ResponseMessage response;

	public List<SubstitutionRuleDto> getRuleDto() {
		return ruleDto;
	}

	public void setRuleDto(List<SubstitutionRuleDto> ruleDto) {
		this.ruleDto = ruleDto;
	}

	public ResponseMessage getResponse() {
		return response;
	}

	public void setResponse(ResponseMessage response) {
		this.response = response;
	}

}
