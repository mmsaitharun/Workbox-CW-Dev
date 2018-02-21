package com.incture.pmc.poadapter.services;

import javax.ejb.Local;


@Local
public interface processActionFacadeLocal {

	String cancelProcess(String processInstanceId);


}
