package com.incture.pmc.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.incture.pmc.dao.ArchProcessTaskEventsDao;
import com.incture.pmc.util.PMCConstant;
import javax.jws.WebService;
import javax.jws.WebMethod;

/**
 * Session Bean implementation class ArchivingService
 */
@WebService(name = "ArchivingService", portName = "ArchivingServicePort", serviceName = "ArchivingServiceService", targetNamespace = "http://incture.com/pmc/services/")
@Stateless
public class ArchivingService implements ArchivingServiceLocal {

	@EJB
	EntityManagerProviderLocal em;

	public ArchivingService() {
	}

	@WebMethod(operationName = "doArchiving", exclude = false)
	@Override
	public void doArchiving() {
		System.err.println("Hello Scheduler");
		ArchProcessTaskEventsDao dao = new ArchProcessTaskEventsDao(em.getEntityManager());
		dao.archiveData(PMCConstant.ARCHIVE_DAY);
		dao.deleteArchivedData(PMCConstant.ARCHIVE_DAY);
	}
}
