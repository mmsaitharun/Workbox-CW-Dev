package com.incture.pmc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.incture.pmc.dto.SubstProfileMgmtDto;
import com.incture.pmc.entity.SubstProfileMgmtDo;
import com.incture.pmc.util.ExecutionFault;
import com.incture.pmc.util.InvalidInputFault;
import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.ServicesUtil;

public class SubstProfileMgmtDao extends BaseDao<SubstProfileMgmtDo, SubstProfileMgmtDto> {
	public SubstProfileMgmtDao(EntityManager entityManager) {
		super(entityManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.incture.pmc.dao.BaseDao#importDto(com.incture.pmc.dto.BaseDto)
	 */
	@Override
	protected SubstProfileMgmtDo importDto(SubstProfileMgmtDto fromDto)
			throws InvalidInputFault, ExecutionFault, NoResultFault {
		SubstProfileMgmtDo entity = new SubstProfileMgmtDo();
		if (!ServicesUtil.isEmpty(fromDto.getProfileId()))
			entity.setProfileId(fromDto.getProfileId());
		if (!ServicesUtil.isEmpty(fromDto.getProfileName()))
			entity.setProfileName(fromDto.getProfileName());
		if (!ServicesUtil.isEmpty(fromDto.getUserId()))
			entity.setUserId(fromDto.getUserId());
		return entity;
	}

	@Override
	public SubstProfileMgmtDto exportDto(SubstProfileMgmtDo entity) {
		SubstProfileMgmtDto profileManagementDto = new SubstProfileMgmtDto();
		if (!ServicesUtil.isEmpty(entity.getProfileId()))
			profileManagementDto.setProfileId(entity.getProfileId());
		if (!ServicesUtil.isEmpty(entity.getProfileName()))
			profileManagementDto.setProfileName(entity.getProfileName());
		if (!ServicesUtil.isEmpty(entity.getUserId()))
			profileManagementDto.setUserId(entity.getUserId());
		return profileManagementDto;
	}


	@SuppressWarnings("unchecked")
	public List<SubstProfileMgmtDo> getProfilesByUser(String userId) {
		String queryString = "SELECT p FROM SubstProfileMgmtDo p WHERE p.userId = '"+userId+"'";
		System.err.println("[PMC][SubstProfileMgmtDao][getProfileByUser][query] - " + queryString);
		Query query = this.getEntityManager().createQuery(queryString);

		List<SubstProfileMgmtDo> resultList = query.getResultList();
		return resultList;
	}

	public String createProfile(SubstProfileMgmtDto dto) {
		System.err.println("[PMC][SubstProfileMgmtDao][createProfile][initiated] with" + dto);
		try {
			create(dto);
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][SubstProfileMgmtDao][createProfile][error] - " + e.getMessage());
		}

		return "FAILURE";

	}
	
	public String deleteProfile(SubstProfileMgmtDto dto) {
		System.err.println("[PMC][SubstProfileMgmtDao][deleteProfile][initiated] with" + dto);
		try {
			delete(dto);
			return "SUCCESS";
		} catch (Exception e) {
			System.err.println("[PMC][SubstProfileMgmtDao][deleteProfile][error] - " + e.getMessage());
		}

		return "FAILURE";

	}
	

}
