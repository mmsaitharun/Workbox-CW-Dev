
package com.incture.pmc.poadapter.services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.incture.pmc.poadapter.services package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BaseDto_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "baseDto");
    private final static QName _GetProcessDetails_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getProcessDetails");
    private final static QName _GetProcessDetailsResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getProcessDetailsResponse");
    private final static QName _GetTaskDetails_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getTaskDetails");
    private final static QName _GetTaskDetailsResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getTaskDetailsResponse");
    private final static QName _ProcessEventsDto_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "processEventsDto");
    private final static QName _TaskEventsDto_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "taskEventsDto");
    private final static QName _TaskOwnerDetailsDto_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "taskOwnerDetailsDto");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.incture.pmc.poadapter.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetProcessDetails }
     * 
     */
    public GetProcessDetails createGetProcessDetails() {
        return new GetProcessDetails();
    }

    /**
     * Create an instance of {@link GetProcessDetailsResponse }
     * 
     */
    public GetProcessDetailsResponse createGetProcessDetailsResponse() {
        return new GetProcessDetailsResponse();
    }

    /**
     * Create an instance of {@link GetTaskDetails }
     * 
     */
    public GetTaskDetails createGetTaskDetails() {
        return new GetTaskDetails();
    }

    /**
     * Create an instance of {@link GetTaskDetailsResponse }
     * 
     */
    public GetTaskDetailsResponse createGetTaskDetailsResponse() {
        return new GetTaskDetailsResponse();
    }

    /**
     * Create an instance of {@link ProcessEventsDto }
     * 
     */
    public ProcessEventsDto createProcessEventsDto() {
        return new ProcessEventsDto();
    }

    /**
     * Create an instance of {@link TaskEventsDto }
     * 
     */
    public TaskEventsDto createTaskEventsDto() {
        return new TaskEventsDto();
    }

    /**
     * Create an instance of {@link TaskOwnerDetailsDto }
     * 
     */
    public TaskOwnerDetailsDto createTaskOwnerDetailsDto() {
        return new TaskOwnerDetailsDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BaseDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "baseDto")
    public JAXBElement<BaseDto> createBaseDto(BaseDto value) {
        return new JAXBElement<BaseDto>(_BaseDto_QNAME, BaseDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProcessDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getProcessDetails")
    public JAXBElement<GetProcessDetails> createGetProcessDetails(GetProcessDetails value) {
        return new JAXBElement<GetProcessDetails>(_GetProcessDetails_QNAME, GetProcessDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProcessDetailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getProcessDetailsResponse")
    public JAXBElement<GetProcessDetailsResponse> createGetProcessDetailsResponse(GetProcessDetailsResponse value) {
        return new JAXBElement<GetProcessDetailsResponse>(_GetProcessDetailsResponse_QNAME, GetProcessDetailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTaskDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getTaskDetails")
    public JAXBElement<GetTaskDetails> createGetTaskDetails(GetTaskDetails value) {
        return new JAXBElement<GetTaskDetails>(_GetTaskDetails_QNAME, GetTaskDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTaskDetailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getTaskDetailsResponse")
    public JAXBElement<GetTaskDetailsResponse> createGetTaskDetailsResponse(GetTaskDetailsResponse value) {
        return new JAXBElement<GetTaskDetailsResponse>(_GetTaskDetailsResponse_QNAME, GetTaskDetailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessEventsDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "processEventsDto")
    public JAXBElement<ProcessEventsDto> createProcessEventsDto(ProcessEventsDto value) {
        return new JAXBElement<ProcessEventsDto>(_ProcessEventsDto_QNAME, ProcessEventsDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaskEventsDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "taskEventsDto")
    public JAXBElement<TaskEventsDto> createTaskEventsDto(TaskEventsDto value) {
        return new JAXBElement<TaskEventsDto>(_TaskEventsDto_QNAME, TaskEventsDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaskOwnerDetailsDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "taskOwnerDetailsDto")
    public JAXBElement<TaskOwnerDetailsDto> createTaskOwnerDetailsDto(TaskOwnerDetailsDto value) {
        return new JAXBElement<TaskOwnerDetailsDto>(_TaskOwnerDetailsDto_QNAME, TaskOwnerDetailsDto.class, null, value);
    }

}
