package com.incture.pmc.services;

import com.sap.scheduler.runtime.mdb.MDBJobImplementation;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import com.sap.scheduler.runtime.JobContext;

/**
 * Message-Driven Bean implementation class for: DbArchivingScheduler
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "JobDefinition='DbArchivingScheduler' AND ApplicationName='incture.com/pmc~service~ejb~app'") })
public class DbArchivingScheduler extends MDBJobImplementation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	ArchivingServiceLocal local;
    /**
     * Default constructor. 
     */
    public DbArchivingScheduler() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void onJob(JobContext jobContext) throws Exception {
		local.doArchiving();
	}

}
