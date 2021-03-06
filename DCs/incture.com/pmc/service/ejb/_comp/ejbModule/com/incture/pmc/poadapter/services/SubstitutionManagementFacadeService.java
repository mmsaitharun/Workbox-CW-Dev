package com.incture.pmc.poadapter.services;

/**
 * Service implementation of {SubstitutionManagementFacadeService} (generated by SAP WSDL to Java generator).
 */
@javax.xml.ws.WebServiceClient(name = "SubstitutionManagementFacadeService", targetNamespace = "http://incture.com/pmc/poadapter/services/", wsdlLocation = "META-INF/wsdl/com/incture/pmc/poadapter/services/rootwsdl_SubstitutionManagementFacadeService/rootwsdl_SubstitutionManagementFacadeService.wsdl")
public class SubstitutionManagementFacadeService extends javax.xml.ws.Service {

  private final static java.net.URL SUBSTITUTIONMANAGEMENTFACADESERVICE_WSDL_LOCATION = null;
  /**
   * Default service constructor.
   */
  public SubstitutionManagementFacadeService() throws java.net.MalformedURLException {
    super(SUBSTITUTIONMANAGEMENTFACADESERVICE_WSDL_LOCATION, new javax.xml.namespace.QName("http://incture.com/pmc/poadapter/services/", "SubstitutionManagementFacadeService"));
  }
  public SubstitutionManagementFacadeService(java.net.URL wsdlLocation, javax.xml.namespace.QName serviceName) {
    super(wsdlLocation, serviceName);
  }
  /**
   * Get method for webservice port [SubstitutionManagementFacadePort].
   */
  @javax.xml.ws.WebEndpoint(name = "SubstitutionManagementFacadePort")
  public com.incture.pmc.poadapter.services.SubstitutionManagementFacade getSubstitutionManagementFacadePort() {
    javax.xml.namespace.QName portName = new javax.xml.namespace.QName("http://incture.com/pmc/poadapter/services/","SubstitutionManagementFacadePort");
    return (com.incture.pmc.poadapter.services.SubstitutionManagementFacade) super.getPort(portName,com.incture.pmc.poadapter.services.SubstitutionManagementFacade.class);
  }
}
