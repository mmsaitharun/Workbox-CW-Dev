package com.incture.pmc.reports;

/**
 * @author Saurabh
 *
 */
public abstract class ServiceFactory {
	
	
	public abstract Report getReport(String reportName);

	public abstract File getFile(String fileFormate);
}
