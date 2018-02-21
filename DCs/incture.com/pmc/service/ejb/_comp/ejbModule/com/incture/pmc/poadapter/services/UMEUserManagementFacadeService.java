package com.incture.pmc.poadapter.services;

/**
 * Service implementation of {UMEUserManagementFacadeService} (generated by SAP WSDL to Java generator).
 */
@javax.xml.ws.WebServiceClient(name = "UMEUserManagementFacadeService", targetNamespace = "http://incture.com/pmc/poadapter/services/", wsdlLocation = "META-INF/wsdl/com/incture/pmc/poadapter/services/rootwsdl_UMEUserManagementFacadeService/rootwsdl_UMEUserManagementFacadeService.wsdl")
public class UMEUserManagementFacadeService extends javax.xml.ws.Service {

  private final static java.net.URL UMEUSERMANAGEMENTFACADESERVICE_WSDL_LOCATION = null;
  /**
   * Default service constructor.
   */
  public UMEUserManagementFacadeService() throws java.net.MalformedURLException {
    super(UMEUSERMANAGEMENTFACADESERVICE_WSDL_LOCATION, new javax.xml.namespace.QName("http://incture.com/pmc/poadapter/services/", "UMEUserManagementFacadeService"));
  }
  public UMEUserManagementFacadeService(java.net.URL wsdlLocation, javax.xml.namespace.QName serviceName) {
    super(wsdlLocation, serviceName);
  }
  /**
   * Get method for webservice port [UMEUserManagementFacadePort].
   */
  @javax.xml.ws.WebEndpoint(name = "UMEUserManagementFacadePort")
  public com.incture.pmc.poadapter.services.UMEUserManagementFacade getUMEUserManagementFacadePort() {
    javax.xml.namespace.QName portName = new javax.xml.namespace.QName("http://incture.com/pmc/poadapter/services/","UMEUserManagementFacadePort");
    return (com.incture.pmc.poadapter.services.UMEUserManagementFacade) super.getPort(portName,com.incture.pmc.poadapter.services.UMEUserManagementFacade.class);
  }
}
