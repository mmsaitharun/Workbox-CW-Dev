package com.incture.pmc.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.incture.pmc.dto.MailRequestDto;
import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.services.NotificationFacadeLocal;

@Path("/notification")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class NotificationRest {

	@EJB
	private NotificationFacadeLocal notification;

	@POST
	@Path("/sendMail")
	public ResponseMessage sendMail(MailRequestDto requestDto) {
		return notification.sendNotification(requestDto);
	}

}
