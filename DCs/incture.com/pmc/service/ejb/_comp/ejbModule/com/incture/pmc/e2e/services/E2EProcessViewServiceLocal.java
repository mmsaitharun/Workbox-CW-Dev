package com.incture.pmc.e2e.services;

import javax.ejb.Local;

@Local
public interface E2EProcessViewServiceLocal {

	E2EProcessResponse drawImage(String processName);

}
