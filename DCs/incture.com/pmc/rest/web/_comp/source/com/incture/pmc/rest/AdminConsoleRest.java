package com.incture.pmc.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.incture.pmc.dto.AdminControlDto;
import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.services.AdminControlFacadeLocal;

@Path("/admin")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })

public class AdminConsoleRest {
	@EJB
	private AdminControlFacadeLocal adminControlFacadeLocal;

	@Path("/configurations")
	@GET
	public AdminControlDto getAdminConfigurationData() {
		return adminControlFacadeLocal.getAdminConfigurationData();
	}

	@Path("/delete/{processName}")
	@POST
	public ResponseMessage deleteProcessConfig(@PathParam("processName")String processName) {
		return adminControlFacadeLocal.deleteProcessConfig(processName);
	}

	@Path("/configurations")
	@POST
	public ResponseMessage createUpdateDataAdmin(AdminControlDto adminControlDto) {
		return adminControlFacadeLocal.createUpdateDataAdmin(adminControlDto);
	}

}