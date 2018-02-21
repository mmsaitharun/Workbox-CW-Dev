package com.incture.pmc.poadapter.util;

/**
 * The Java type that goes as soapenv:Fault detail element. Used in web services
 * exceptions, fault beans just hold the details of the SOAP fault. This one is
 * used by the {@link ExecutionFault}, {@link InvalidInputFault},
 * {@link NoUniqueRecordFault}, {@link NoRecordFault}, {@link RecordExistFault}.
 * Meant as custom message for User Interfaces
 * 
 * @author INC00400
 * @version 1.0
 * @since 2017-05-09
 */
public class MessageUIDto {

	private String message;

	public MessageUIDto() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}