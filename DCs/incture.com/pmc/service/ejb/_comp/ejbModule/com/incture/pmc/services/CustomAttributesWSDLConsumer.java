package com.incture.pmc.services;

import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;

import com.incture.pmc.poadapter.services.CustomAttributeDto;
import com.incture.pmc.poadapter.services.CustomAttributesServiceService;

/**
 * Session Bean implementation class CustomAttributesWSDLConsumer
 */
@Stateless
public class CustomAttributesWSDLConsumer implements CustomAttributesWSDLConsumerLocal {

   @WebServiceRef(name="CustomAttributesServiceService")
   private CustomAttributesServiceService serv;
   
   @Override
   public CustomAttributeDto getCUstomAttribute(String taskId){
	   return serv.getCustomAttributesServicePort().getCustomAttributes(taskId);
   }

}
