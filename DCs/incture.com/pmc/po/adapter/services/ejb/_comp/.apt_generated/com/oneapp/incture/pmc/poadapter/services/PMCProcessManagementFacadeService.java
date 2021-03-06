package com.oneapp.incture.pmc.poadapter.services;

/**
 * Service implementation of {PMCProcessManagementFacadeService} (generated by SAP WSDL to Java generator).
 */
@javax.xml.ws.WebServiceClient(name = "PMCProcessManagementFacadeService", targetNamespace = "http://oneapp.com/incture/pmc/poadapter/services/", wsdlLocation = "META-INF/wsdl/com/oneapp/incture/pmc/poadapter/services/rootwsdl_PMCProcessManagementFacadeService/rootwsdl_PMCProcessManagementFacadeService.wsdl")
public class PMCProcessManagementFacadeService extends javax.xml.ws.Service {

  private final static java.net.URL PMCPROCESSMANAGEMENTFACADESERVICE_WSDL_LOCATION = null;
  /**
   * Default service constructor.
   */
  public PMCProcessManagementFacadeService() throws java.net.MalformedURLException {
    super(PMCPROCESSMANAGEMENTFACADESERVICE_WSDL_LOCATION, new javax.xml.namespace.QName("http://oneapp.com/incture/pmc/poadapter/services/", "PMCProcessManagementFacadeService"));
  }
  public PMCProcessManagementFacadeService(java.net.URL wsdlLocation, javax.xml.namespace.QName serviceName) {
    super(wsdlLocation, serviceName);
  }
  /**
   * Get method for webservice port [ProcessManagementFacadePort].
   */
  @javax.xml.ws.WebEndpoint(name = "ProcessManagementFacadePort")
  public com.oneapp.incture.pmc.poadapter.services.PMCProcessManagementFacade getProcessManagementFacadePort() {
    javax.xml.namespace.QName portName = new javax.xml.namespace.QName("http://oneapp.com/incture/pmc/poadapter/services/","ProcessManagementFacadePort");
    return (com.oneapp.incture.pmc.poadapter.services.PMCProcessManagementFacade) super.getPort(portName,com.oneapp.incture.pmc.poadapter.services.PMCProcessManagementFacade.class);
  }
}
