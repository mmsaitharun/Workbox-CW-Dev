package com.incture.pmc.poadapter.services;

/**
 * Service Endpoint Interface (generated by SAP WSDL to Java generator).
 */
@javax.jws.WebService(name = "UMEUserManagementFacade", targetNamespace = "http://incture.com/pmc/poadapter/services/")
@javax.jws.soap.SOAPBinding(parameterStyle = javax.jws.soap.SOAPBinding.ParameterStyle.WRAPPED, style = javax.jws.soap.SOAPBinding.Style.DOCUMENT, use = javax.jws.soap.SOAPBinding.Use.LITERAL)
public interface UMEUserManagementFacade {

  /**
   * Java representation of web method [getUserDetailsByUserId].
   */
  @javax.jws.WebMethod(operationName = "getUserDetailsByUserId")
  @javax.xml.ws.RequestWrapper(localName = "getUserDetailsByUserId", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUserDetailsByUserId")
  @javax.xml.ws.ResponseWrapper(localName = "getUserDetailsByUserIdResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUserDetailsByUserIdResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public com.incture.pmc.poadapter.services.UserDetailsDto getUserDetailsByUserId(@javax.jws.WebParam(name = "userId", targetNamespace = "") java.lang.String userId);

  /**
   * Java representation of web method [getAllUserGroup].
   */
  @javax.jws.WebMethod(operationName = "getAllUserGroup")
  @javax.xml.ws.RequestWrapper(localName = "getAllUserGroup", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetAllUserGroup")
  @javax.xml.ws.ResponseWrapper(localName = "getAllUserGroupResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetAllUserGroupResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public java.util.List<com.incture.pmc.poadapter.services.UserGroupDto> getAllUserGroup();

  /**
   * Java representation of web method [getUsersAssignedInGroup].
   */
  @javax.jws.WebMethod(operationName = "getUsersAssignedInGroup")
  @javax.xml.ws.RequestWrapper(localName = "getUsersAssignedInGroup", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUsersAssignedInGroup")
  @javax.xml.ws.ResponseWrapper(localName = "getUsersAssignedInGroupResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUsersAssignedInGroupResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public java.util.List<java.lang.String> getUsersAssignedInGroup(@javax.jws.WebParam(name = "userGroup", targetNamespace = "") java.lang.String userGroup);

  /**
   * Java representation of web method [getUserGroupByuserId].
   */
  @javax.jws.WebMethod(operationName = "getUserGroupByuserId")
  @javax.xml.ws.RequestWrapper(localName = "getUserGroupByuserId", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUserGroupByuserId")
  @javax.xml.ws.ResponseWrapper(localName = "getUserGroupByuserIdResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUserGroupByuserIdResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public java.util.List<com.incture.pmc.poadapter.services.GroupInfoDto> getUserGroupByuserId(@javax.jws.WebParam(name = "userUniqueId", targetNamespace = "") java.lang.String userUniqueId);

  /**
   * Java representation of web method [getUserRoleByuserId].
   */
  @javax.jws.WebMethod(operationName = "getUserRoleByuserId")
  @javax.xml.ws.RequestWrapper(localName = "getUserRoleByuserId", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUserRoleByuserId")
  @javax.xml.ws.ResponseWrapper(localName = "getUserRoleByuserIdResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUserRoleByuserIdResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public java.util.List<com.incture.pmc.poadapter.services.RoleInfoDto> getUserRoleByuserId(@javax.jws.WebParam(name = "userUniqueId", targetNamespace = "") java.lang.String userUniqueId);

  /**
   * Java representation of web method [getUserEmailByuserId].
   */
  @javax.jws.WebMethod(operationName = "getUserEmailByuserId")
  @javax.xml.ws.RequestWrapper(localName = "getUserEmailByuserId", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUserEmailByuserId")
  @javax.xml.ws.ResponseWrapper(localName = "getUserEmailByuserIdResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUserEmailByuserIdResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public java.lang.String getUserEmailByuserId(@javax.jws.WebParam(name = "userUniqueId", targetNamespace = "") java.lang.String userUniqueId);

  /**
   * Java representation of web method [getLoggedInUser].
   */
  @javax.jws.WebMethod(operationName = "getLoggedInUser")
  @javax.xml.ws.RequestWrapper(localName = "getLoggedInUser", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetLoggedInUser")
  @javax.xml.ws.ResponseWrapper(localName = "getLoggedInUserResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetLoggedInUserResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public com.incture.pmc.poadapter.services.UserDetailsDto getLoggedInUser();

  /**
   * Java representation of web method [getAllUsers].
   */
  @javax.jws.WebMethod(operationName = "getAllUsers")
  @javax.xml.ws.RequestWrapper(localName = "getAllUsers", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetAllUsers")
  @javax.xml.ws.ResponseWrapper(localName = "getAllUsersResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetAllUsersResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public java.util.List<com.incture.pmc.poadapter.services.UserDto> getAllUsers(@javax.jws.WebParam(name = "userSearch", targetNamespace = "") java.lang.String userSearch);

  /**
   * Java representation of web method [getAllUserRole].
   */
  @javax.jws.WebMethod(operationName = "getAllUserRole")
  @javax.xml.ws.RequestWrapper(localName = "getAllUserRole", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetAllUserRole")
  @javax.xml.ws.ResponseWrapper(localName = "getAllUserRoleResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetAllUserRoleResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public java.util.List<com.incture.pmc.poadapter.services.RoleInfoDto> getAllUserRole();

  /**
   * Java representation of web method [getUserDetailsAssignedInGroup].
   */
  @javax.jws.WebMethod(operationName = "getUserDetailsAssignedInGroup")
  @javax.xml.ws.RequestWrapper(localName = "getUserDetailsAssignedInGroup", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUserDetailsAssignedInGroup")
  @javax.xml.ws.ResponseWrapper(localName = "getUserDetailsAssignedInGroupResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUserDetailsAssignedInGroupResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public java.util.List<com.incture.pmc.poadapter.services.UserDto> getUserDetailsAssignedInGroup(@javax.jws.WebParam(name = "getUserDetailsAssignedInGroup", targetNamespace = "") java.lang.String getUserDetailsAssignedInGroup);

  /**
   * Java representation of web method [getUsersByRole].
   */
  @javax.jws.WebMethod(operationName = "getUsersByRole")
  @javax.xml.ws.RequestWrapper(localName = "getUsersByRole", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUsersByRole")
  @javax.xml.ws.ResponseWrapper(localName = "getUsersByRoleResponse", targetNamespace = "http://incture.com/pmc/poadapter/services/", className = "com.incture.pmc.poadapter.services.GetUsersByRoleResponse")
  @javax.jws.WebResult(name = "return", targetNamespace = "")
  public java.util.List<com.incture.pmc.poadapter.services.UserDetailsDto> getUsersByRole(@javax.jws.WebParam(name = "role", targetNamespace = "") java.lang.String role);

}