package com.incture.inbox.services;

import java.util.List;

import javax.ejb.Local;

import com.incture.pmc.dto.SubstitutionResponseDto;
import com.incture.pmc.poadapter.services.ResponseDto;
import com.incture.pmc.poadapter.services.SubstitutionRuleDto;
import com.incture.pmc.poadapter.services.UserDto;

@Local
public interface SubstitutionRuleFacadeWsdlConsumerLocal {

	ResponseDto createRule(SubstitutionRuleDto ruleDto);

	ResponseDto deleteRule(SubstitutionRuleDto ruleDto);

	ResponseDto updateRule(SubstitutionRuleDto ruleDto);

	SubstitutionResponseDto getActiveRulesBySubstitute(String user);

	SubstitutionResponseDto getActiveRulesBySubstitutedUser(String substitutedUser);

	SubstitutionResponseDto getInactiveRulesBySubstitute(String substitutingUser);

	SubstitutionResponseDto getInactiveRulesBySubstitutedUser(String substitutedUser);

	List<UserDto> getSubstituteUsers(String substitutedUser);

	List<UserDto> getSubstitutedUsers(String substitutingUserString);

	SubstitutionResponseDto getRulesBySubstitute(String user);

	SubstitutionResponseDto getRulesBySubstitutedUser(String user);

	SubstitutionResponseDto getAllRulesByUser(String user);

	ResponseDto deleteAndCreate(SubstitutionRuleDto ruleDto);

}
