package com.incture.pmc.poadapter.services;

/**
 * Service Endpoint Interface (generated by SAP WSDL to Java generator).
 */
@javax.jws.WebService(name = "WorkBoxActionFacade", targetNamespace = "http://incture.com/pmc/poadapter/services/")
@javax.jws.soap.SOAPBinding(parameterStyle = javax.jws.soap.SOAPBinding.ParameterStyle.WRAPPED, style = javax.jws.soap.SOAPBinding.Style.DOCUMENT, use = javax.jws.soap.SOAPBinding.Use.LITERAL)
public interface WorkBoxActionFacade {

  /**
   * Java representation of web method [claimTask].
   */
  @javax.jws.WebMethod(operationName = "claimTask")
  @javax.xml.ws.RequestWrapper(localName = "claimTask", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.ClaimTask")
  @javax.xml.ws.ResponseWrapper(localName = "claimTaskResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.ClaimTaskResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public boolean claimTask(@javax.jws.WebParam(name = "taskInstanceId", targetNamespace = "") java.lang.String taskInstanceId);

  /**
   * Java representation of web method [release].
   */
  @javax.jws.WebMethod(operationName = "release")
  @javax.xml.ws.RequestWrapper(localName = "release", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.Release")
  @javax.xml.ws.ResponseWrapper(localName = "releaseResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.ReleaseResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public boolean release(@javax.jws.WebParam(name = "taskInstanceId", targetNamespace = "") java.lang.String taskInstanceId);

  /**
   * Java representation of web method [delegate].
   */
  @javax.jws.WebMethod(operationName = "delegate")
  @javax.xml.ws.RequestWrapper(localName = "delegate", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.Delegate")
  @javax.xml.ws.ResponseWrapper(localName = "delegateResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.DelegateResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public boolean delegate(@javax.jws.WebParam(name = "taskInstanceId", targetNamespace = "") java.lang.String taskInstanceId, @javax.jws.WebParam(name = "userId", targetNamespace = "") java.lang.String userId);

  /**
   * Java representation of web method [addNote].
   */
  @javax.jws.WebMethod(operationName = "addNote")
  @javax.xml.ws.RequestWrapper(localName = "addNote", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.AddNote")
  @javax.xml.ws.ResponseWrapper(localName = "addNoteResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.AddNoteResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public java.lang.String addNote(@javax.jws.WebParam(name = "taskInstanceId", targetNamespace = "") java.lang.String taskInstanceId, @javax.jws.WebParam(name = "content", targetNamespace = "") java.lang.String content);

  /**
   * Java representation of web method [complete].
   */
  @javax.jws.WebMethod(operationName = "complete")
  @javax.xml.ws.RequestWrapper(localName = "complete", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.Complete")
  @javax.xml.ws.ResponseWrapper(localName = "completeResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.CompleteResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public boolean complete(@javax.jws.WebParam(name = "taskInstanceId", targetNamespace = "") java.lang.String taskInstanceId, @javax.jws.WebParam(name = "action", targetNamespace = "") java.lang.String action);

  /**
   * Java representation of web method [getNotes].
   */
  @javax.jws.WebMethod(operationName = "getNotes")
  @javax.xml.ws.RequestWrapper(localName = "getNotes", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetNotes")
  @javax.xml.ws.ResponseWrapper(localName = "getNotesResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetNotesResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public java.util.List<com.incture.pmc.poadapter.services.NoteDto> getNotes(@javax.jws.WebParam(name = "taskInstanceId", targetNamespace = "") java.lang.String taskInstanceId);

  /**
   * Java representation of web method [claimAndDelegate].
   */
  @javax.jws.WebMethod(operationName = "claimAndDelegate")
  @javax.xml.ws.RequestWrapper(localName = "claimAndDelegate", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.ClaimAndDelegate")
  @javax.xml.ws.ResponseWrapper(localName = "claimAndDelegateResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.ClaimAndDelegateResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public java.lang.String claimAndDelegate(@javax.jws.WebParam(name = "taskInstanceId", targetNamespace = "") java.lang.String taskInstanceId, @javax.jws.WebParam(name = "userId", targetNamespace = "") java.lang.String userId);

  /**
   * Java representation of web method [nominate].
   */
  @javax.jws.WebMethod(operationName = "nominate")
  @javax.xml.ws.RequestWrapper(localName = "nominate", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.Nominate")
  @javax.xml.ws.ResponseWrapper(localName = "nominateResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.NominateResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public java.lang.String nominate(@javax.jws.WebParam(name = "taskInstanceId", targetNamespace = "") java.lang.String taskInstanceId, @javax.jws.WebParam(name = "userId", targetNamespace = "") java.lang.String userId);

}
