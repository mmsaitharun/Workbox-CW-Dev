
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

    private final static QName _LoggedInUser_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"loggedInUser");
	private final static QName _LoggedInUserResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"loggedInUserResponse");
	private final static QName _CancelProcess_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"cancelProcess");
	private final static QName _CancelProcessResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"cancelProcessResponse");
	private final static QName _AddNote_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "addNote");
	private final static QName _AddNoteResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"addNoteResponse");
	private final static QName _ClaimAndDelegate_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"claimAndDelegate");
	private final static QName _ClaimAndDelegateResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"claimAndDelegateResponse");
	private final static QName _ClaimTask_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "claimTask");
	private final static QName _ClaimTaskResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"claimTaskResponse");
	private final static QName _Complete_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "complete");
	private final static QName _CompleteResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"completeResponse");
	private final static QName _Delegate_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "delegate");
	private final static QName _DelegateResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"delegateResponse");
	private final static QName _GetNotes_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getNotes");
	private final static QName _GetNotesResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getNotesResponse");
	private final static QName _Nominate_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "nominate");
	private final static QName _NominateResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"nominateResponse");
	private final static QName _Release_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "release");
	private final static QName _ReleaseResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"releaseResponse");
	private final static QName _GetCustomAttributes_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getCustomAttributes");
	private final static QName _GetCustomAttributesResponse_QNAME = new QName(
			"http://incture.com/pmc/poadapter/services/", "getCustomAttributesResponse");
	private final static QName _GetAllUserGroup_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getAllUserGroup");
	private final static QName _GetAllUserGroupResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getAllUserGroupResponse");
	private final static QName _GetAllUserRole_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getAllUserRole");
	private final static QName _GetAllUserRoleResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getAllUserRoleResponse");
	private final static QName _GetAllUsers_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getAllUsers");
	private final static QName _GetAllUsersResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getAllUsersResponse");
	private final static QName _GetLoggedInUser_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getLoggedInUser");
	private final static QName _GetLoggedInUserResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getLoggedInUserResponse");
	private final static QName _GetUserDetailsAssignedInGroup_QNAME = new QName(
			"http://incture.com/pmc/poadapter/services/", "getUserDetailsAssignedInGroup");
	private final static QName _GetUserDetailsAssignedInGroupResponse_QNAME = new QName(
			"http://incture.com/pmc/poadapter/services/", "getUserDetailsAssignedInGroupResponse");
	private final static QName _GetUserDetailsByUserId_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getUserDetailsByUserId");
	private final static QName _GetUserDetailsByUserIdResponse_QNAME = new QName(
			"http://incture.com/pmc/poadapter/services/", "getUserDetailsByUserIdResponse");
	private final static QName _GetUserEmailByuserId_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getUserEmailByuserId");
	private final static QName _GetUserEmailByuserIdResponse_QNAME = new QName(
			"http://incture.com/pmc/poadapter/services/", "getUserEmailByuserIdResponse");
	private final static QName _GetUserGroupByuserId_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getUserGroupByuserId");
	private final static QName _GetUserGroupByuserIdResponse_QNAME = new QName(
			"http://incture.com/pmc/poadapter/services/", "getUserGroupByuserIdResponse");
	private final static QName _GetUserRoleByuserId_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getUserRoleByuserId");
	private final static QName _GetUserRoleByuserIdResponse_QNAME = new QName(
			"http://incture.com/pmc/poadapter/services/", "getUserRoleByuserIdResponse");
	private final static QName _GetUsersAssignedInGroup_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getUsersAssignedInGroup");
	private final static QName _GetUsersAssignedInGroupResponse_QNAME = new QName(
			"http://incture.com/pmc/poadapter/services/", "getUsersAssignedInGroupResponse");
	private final static QName _GetUsersByRole_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getUsersByRole");
	private final static QName _GetUsersByRoleResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getUsersByRoleResponse");
	private final static QName _GroupInfoDto_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"groupInfoDto");
	private final static QName _RoleInfoDto_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"roleInfoDto");
	private final static QName _UserDetailsDto_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"userDetailsDto");
	private final static QName _UserGroupDto_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"userGroupDto");
	private final static QName _CreateSubstitutionProfile_QNAME = new QName(
			"http://incture.com/pmc/poadapter/services/", "createSubstitutionProfile");
	private final static QName _CreateSubstitutionProfileResponse_QNAME = new QName(
			"http://incture.com/pmc/poadapter/services/", "createSubstitutionProfileResponse");
	private final static QName _DeleteProfile_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"deleteProfile");
	private final static QName _DeleteProfileResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"deleteProfileResponse");
	private final static QName _GetAllProfiles_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getAllProfiles");
	private final static QName _GetAllProfilesResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getAllProfilesResponse");
	private final static QName _GetMyTaskModelIds_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getMyTaskModelIds");
	private final static QName _GetMyTaskModelIdsResponse_QNAME = new QName(
			"http://incture.com/pmc/poadapter/services/", "getMyTaskModelIdsResponse");
	private final static QName _GetProfileById_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getProfileById");
	private final static QName _GetProfileByIdResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getProfileByIdResponse");
	private final static QName _GetProfileByKey_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getProfileByKey");
	private final static QName _GetProfileByKeyResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getProfileByKeyResponse");
	private final static QName _GetTaskModel_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getTaskModel");
	private final static QName _GetTaskModelResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"getTaskModelResponse");
	private final static QName _TaskModelDto_QNAME = new QName("http://incture.com/pmc/poadapter/services/",
			"taskModelDto");
	private final static QName _BaseDto_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "baseDto");
    private final static QName _CreateRule_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "createRule");
    private final static QName _CreateRuleResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "createRuleResponse");
    private final static QName _DeleteRule_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "deleteRule");
    private final static QName _DeleteRuleResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "deleteRuleResponse");
    private final static QName _GetActiveRulesBySubstitute_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getActiveRulesBySubstitute");
    private final static QName _GetActiveRulesBySubstituteResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getActiveRulesBySubstituteResponse");
    private final static QName _GetActiveRulesBySubstitutedUser_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getActiveRulesBySubstitutedUser");
    private final static QName _GetActiveRulesBySubstitutedUserResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getActiveRulesBySubstitutedUserResponse");
    private final static QName _GetInactiveRulesBySubstitute_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getInactiveRulesBySubstitute");
    private final static QName _GetInactiveRulesBySubstituteResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getInactiveRulesBySubstituteResponse");
    private final static QName _GetInactiveRulesBySubstitutedUser_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getInactiveRulesBySubstitutedUser");
    private final static QName _GetInactiveRulesBySubstitutedUserResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getInactiveRulesBySubstitutedUserResponse");
    private final static QName _GetRulesBySubstitute_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getRulesBySubstitute");
    private final static QName _GetRulesBySubstituteResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getRulesBySubstituteResponse");
    private final static QName _GetRulesBySubstitutedUser_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getRulesBySubstitutedUser");
    private final static QName _GetRulesBySubstitutedUserResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getRulesBySubstitutedUserResponse");
    private final static QName _GetSubstituteUsers_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getSubstituteUsers");
    private final static QName _GetSubstituteUsersResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getSubstituteUsersResponse");
    private final static QName _GetSubstitutedUsers_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getSubstitutedUsers");
    private final static QName _GetSubstitutedUsersResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "getSubstitutedUsersResponse");
    private final static QName _ResponseDto_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "responseDto");
    private final static QName _SubstitutionRuleDto_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "substitutionRuleDto");
    private final static QName _UpdateRule_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "updateRule");
    private final static QName _UpdateRuleResponse_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "updateRuleResponse");
    private final static QName _UserDto_QNAME = new QName("http://incture.com/pmc/poadapter/services/", "userDto");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.incture.pmc.poadapter.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateRule }
     * 
     */
    public CreateRule createCreateRule() {
        return new CreateRule();
    }

    /**
     * Create an instance of {@link CreateRuleResponse }
     * 
     */
    public CreateRuleResponse createCreateRuleResponse() {
        return new CreateRuleResponse();
    }

    /**
     * Create an instance of {@link DeleteRule }
     * 
     */
    public DeleteRule createDeleteRule() {
        return new DeleteRule();
    }

    /**
     * Create an instance of {@link DeleteRuleResponse }
     * 
     */
    public DeleteRuleResponse createDeleteRuleResponse() {
        return new DeleteRuleResponse();
    }

    /**
     * Create an instance of {@link GetActiveRulesBySubstitute }
     * 
     */
    public GetActiveRulesBySubstitute createGetActiveRulesBySubstitute() {
        return new GetActiveRulesBySubstitute();
    }

    /**
     * Create an instance of {@link GetActiveRulesBySubstituteResponse }
     * 
     */
    public GetActiveRulesBySubstituteResponse createGetActiveRulesBySubstituteResponse() {
        return new GetActiveRulesBySubstituteResponse();
    }

    /**
     * Create an instance of {@link GetActiveRulesBySubstitutedUser }
     * 
     */
    public GetActiveRulesBySubstitutedUser createGetActiveRulesBySubstitutedUser() {
        return new GetActiveRulesBySubstitutedUser();
    }

    /**
     * Create an instance of {@link GetActiveRulesBySubstitutedUserResponse }
     * 
     */
    public GetActiveRulesBySubstitutedUserResponse createGetActiveRulesBySubstitutedUserResponse() {
        return new GetActiveRulesBySubstitutedUserResponse();
    }

    /**
     * Create an instance of {@link GetInactiveRulesBySubstitute }
     * 
     */
    public GetInactiveRulesBySubstitute createGetInactiveRulesBySubstitute() {
        return new GetInactiveRulesBySubstitute();
    }

    /**
     * Create an instance of {@link GetInactiveRulesBySubstituteResponse }
     * 
     */
    public GetInactiveRulesBySubstituteResponse createGetInactiveRulesBySubstituteResponse() {
        return new GetInactiveRulesBySubstituteResponse();
    }

    /**
     * Create an instance of {@link GetInactiveRulesBySubstitutedUser }
     * 
     */
    public GetInactiveRulesBySubstitutedUser createGetInactiveRulesBySubstitutedUser() {
        return new GetInactiveRulesBySubstitutedUser();
    }

    /**
     * Create an instance of {@link GetInactiveRulesBySubstitutedUserResponse }
     * 
     */
    public GetInactiveRulesBySubstitutedUserResponse createGetInactiveRulesBySubstitutedUserResponse() {
        return new GetInactiveRulesBySubstitutedUserResponse();
    }

    /**
     * Create an instance of {@link GetRulesBySubstitute }
     * 
     */
    public GetRulesBySubstitute createGetRulesBySubstitute() {
        return new GetRulesBySubstitute();
    }

    /**
     * Create an instance of {@link GetRulesBySubstituteResponse }
     * 
     */
    public GetRulesBySubstituteResponse createGetRulesBySubstituteResponse() {
        return new GetRulesBySubstituteResponse();
    }

    /**
     * Create an instance of {@link GetRulesBySubstitutedUser }
     * 
     */
    public GetRulesBySubstitutedUser createGetRulesBySubstitutedUser() {
        return new GetRulesBySubstitutedUser();
    }

    /**
     * Create an instance of {@link GetRulesBySubstitutedUserResponse }
     * 
     */
    public GetRulesBySubstitutedUserResponse createGetRulesBySubstitutedUserResponse() {
        return new GetRulesBySubstitutedUserResponse();
    }

    /**
     * Create an instance of {@link GetSubstituteUsers }
     * 
     */
    public GetSubstituteUsers createGetSubstituteUsers() {
        return new GetSubstituteUsers();
    }

    /**
     * Create an instance of {@link GetSubstituteUsersResponse }
     * 
     */
    public GetSubstituteUsersResponse createGetSubstituteUsersResponse() {
        return new GetSubstituteUsersResponse();
    }

    /**
     * Create an instance of {@link GetSubstitutedUsers }
     * 
     */
    public GetSubstitutedUsers createGetSubstitutedUsers() {
        return new GetSubstitutedUsers();
    }

    /**
     * Create an instance of {@link GetSubstitutedUsersResponse }
     * 
     */
    public GetSubstitutedUsersResponse createGetSubstitutedUsersResponse() {
        return new GetSubstitutedUsersResponse();
    }

    /**
     * Create an instance of {@link ResponseDto }
     * 
     */
    public ResponseDto createResponseDto() {
        return new ResponseDto();
    }

    /**
     * Create an instance of {@link SubstitutionRuleDto }
     * 
     */
    public SubstitutionRuleDto createSubstitutionRuleDto() {
        return new SubstitutionRuleDto();
    }

    /**
     * Create an instance of {@link UpdateRule }
     * 
     */
    public UpdateRule createUpdateRule() {
        return new UpdateRule();
    }

    /**
     * Create an instance of {@link UpdateRuleResponse }
     * 
     */
    public UpdateRuleResponse createUpdateRuleResponse() {
        return new UpdateRuleResponse();
    }

    /**
     * Create an instance of {@link UserDto }
     * 
     */
    public UserDto createUserDto() {
        return new UserDto();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateRule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "createRule")
    public JAXBElement<CreateRule> createCreateRule(CreateRule value) {
        return new JAXBElement<CreateRule>(_CreateRule_QNAME, CreateRule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateRuleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "createRuleResponse")
    public JAXBElement<CreateRuleResponse> createCreateRuleResponse(CreateRuleResponse value) {
        return new JAXBElement<CreateRuleResponse>(_CreateRuleResponse_QNAME, CreateRuleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "deleteRule")
    public JAXBElement<DeleteRule> createDeleteRule(DeleteRule value) {
        return new JAXBElement<DeleteRule>(_DeleteRule_QNAME, DeleteRule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRuleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "deleteRuleResponse")
    public JAXBElement<DeleteRuleResponse> createDeleteRuleResponse(DeleteRuleResponse value) {
        return new JAXBElement<DeleteRuleResponse>(_DeleteRuleResponse_QNAME, DeleteRuleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActiveRulesBySubstitute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getActiveRulesBySubstitute")
    public JAXBElement<GetActiveRulesBySubstitute> createGetActiveRulesBySubstitute(GetActiveRulesBySubstitute value) {
        return new JAXBElement<GetActiveRulesBySubstitute>(_GetActiveRulesBySubstitute_QNAME, GetActiveRulesBySubstitute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActiveRulesBySubstituteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getActiveRulesBySubstituteResponse")
    public JAXBElement<GetActiveRulesBySubstituteResponse> createGetActiveRulesBySubstituteResponse(GetActiveRulesBySubstituteResponse value) {
        return new JAXBElement<GetActiveRulesBySubstituteResponse>(_GetActiveRulesBySubstituteResponse_QNAME, GetActiveRulesBySubstituteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActiveRulesBySubstitutedUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getActiveRulesBySubstitutedUser")
    public JAXBElement<GetActiveRulesBySubstitutedUser> createGetActiveRulesBySubstitutedUser(GetActiveRulesBySubstitutedUser value) {
        return new JAXBElement<GetActiveRulesBySubstitutedUser>(_GetActiveRulesBySubstitutedUser_QNAME, GetActiveRulesBySubstitutedUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActiveRulesBySubstitutedUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getActiveRulesBySubstitutedUserResponse")
    public JAXBElement<GetActiveRulesBySubstitutedUserResponse> createGetActiveRulesBySubstitutedUserResponse(GetActiveRulesBySubstitutedUserResponse value) {
        return new JAXBElement<GetActiveRulesBySubstitutedUserResponse>(_GetActiveRulesBySubstitutedUserResponse_QNAME, GetActiveRulesBySubstitutedUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInactiveRulesBySubstitute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getInactiveRulesBySubstitute")
    public JAXBElement<GetInactiveRulesBySubstitute> createGetInactiveRulesBySubstitute(GetInactiveRulesBySubstitute value) {
        return new JAXBElement<GetInactiveRulesBySubstitute>(_GetInactiveRulesBySubstitute_QNAME, GetInactiveRulesBySubstitute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInactiveRulesBySubstituteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getInactiveRulesBySubstituteResponse")
    public JAXBElement<GetInactiveRulesBySubstituteResponse> createGetInactiveRulesBySubstituteResponse(GetInactiveRulesBySubstituteResponse value) {
        return new JAXBElement<GetInactiveRulesBySubstituteResponse>(_GetInactiveRulesBySubstituteResponse_QNAME, GetInactiveRulesBySubstituteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInactiveRulesBySubstitutedUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getInactiveRulesBySubstitutedUser")
    public JAXBElement<GetInactiveRulesBySubstitutedUser> createGetInactiveRulesBySubstitutedUser(GetInactiveRulesBySubstitutedUser value) {
        return new JAXBElement<GetInactiveRulesBySubstitutedUser>(_GetInactiveRulesBySubstitutedUser_QNAME, GetInactiveRulesBySubstitutedUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInactiveRulesBySubstitutedUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getInactiveRulesBySubstitutedUserResponse")
    public JAXBElement<GetInactiveRulesBySubstitutedUserResponse> createGetInactiveRulesBySubstitutedUserResponse(GetInactiveRulesBySubstitutedUserResponse value) {
        return new JAXBElement<GetInactiveRulesBySubstitutedUserResponse>(_GetInactiveRulesBySubstitutedUserResponse_QNAME, GetInactiveRulesBySubstitutedUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRulesBySubstitute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getRulesBySubstitute")
    public JAXBElement<GetRulesBySubstitute> createGetRulesBySubstitute(GetRulesBySubstitute value) {
        return new JAXBElement<GetRulesBySubstitute>(_GetRulesBySubstitute_QNAME, GetRulesBySubstitute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRulesBySubstituteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getRulesBySubstituteResponse")
    public JAXBElement<GetRulesBySubstituteResponse> createGetRulesBySubstituteResponse(GetRulesBySubstituteResponse value) {
        return new JAXBElement<GetRulesBySubstituteResponse>(_GetRulesBySubstituteResponse_QNAME, GetRulesBySubstituteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRulesBySubstitutedUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getRulesBySubstitutedUser")
    public JAXBElement<GetRulesBySubstitutedUser> createGetRulesBySubstitutedUser(GetRulesBySubstitutedUser value) {
        return new JAXBElement<GetRulesBySubstitutedUser>(_GetRulesBySubstitutedUser_QNAME, GetRulesBySubstitutedUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRulesBySubstitutedUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getRulesBySubstitutedUserResponse")
    public JAXBElement<GetRulesBySubstitutedUserResponse> createGetRulesBySubstitutedUserResponse(GetRulesBySubstitutedUserResponse value) {
        return new JAXBElement<GetRulesBySubstitutedUserResponse>(_GetRulesBySubstitutedUserResponse_QNAME, GetRulesBySubstitutedUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubstituteUsers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getSubstituteUsers")
    public JAXBElement<GetSubstituteUsers> createGetSubstituteUsers(GetSubstituteUsers value) {
        return new JAXBElement<GetSubstituteUsers>(_GetSubstituteUsers_QNAME, GetSubstituteUsers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubstituteUsersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getSubstituteUsersResponse")
    public JAXBElement<GetSubstituteUsersResponse> createGetSubstituteUsersResponse(GetSubstituteUsersResponse value) {
        return new JAXBElement<GetSubstituteUsersResponse>(_GetSubstituteUsersResponse_QNAME, GetSubstituteUsersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubstitutedUsers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getSubstitutedUsers")
    public JAXBElement<GetSubstitutedUsers> createGetSubstitutedUsers(GetSubstitutedUsers value) {
        return new JAXBElement<GetSubstitutedUsers>(_GetSubstitutedUsers_QNAME, GetSubstitutedUsers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubstitutedUsersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getSubstitutedUsersResponse")
    public JAXBElement<GetSubstitutedUsersResponse> createGetSubstitutedUsersResponse(GetSubstitutedUsersResponse value) {
        return new JAXBElement<GetSubstitutedUsersResponse>(_GetSubstitutedUsersResponse_QNAME, GetSubstitutedUsersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "responseDto")
    public JAXBElement<ResponseDto> createResponseDto(ResponseDto value) {
        return new JAXBElement<ResponseDto>(_ResponseDto_QNAME, ResponseDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubstitutionRuleDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "substitutionRuleDto")
    public JAXBElement<SubstitutionRuleDto> createSubstitutionRuleDto(SubstitutionRuleDto value) {
        return new JAXBElement<SubstitutionRuleDto>(_SubstitutionRuleDto_QNAME, SubstitutionRuleDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateRule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "updateRule")
    public JAXBElement<UpdateRule> createUpdateRule(UpdateRule value) {
        return new JAXBElement<UpdateRule>(_UpdateRule_QNAME, UpdateRule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateRuleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "updateRuleResponse")
    public JAXBElement<UpdateRuleResponse> createUpdateRuleResponse(UpdateRuleResponse value) {
        return new JAXBElement<UpdateRuleResponse>(_UpdateRuleResponse_QNAME, UpdateRuleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "userDto")
    public JAXBElement<UserDto> createUserDto(UserDto value) {
        return new JAXBElement<UserDto>(_UserDto_QNAME, UserDto.class, null, value);
    }

	/**
	 * Create an instance of     {@link CreateSubstitutionProfile     }
	 */
	public CreateSubstitutionProfile createCreateSubstitutionProfile() {
		return new CreateSubstitutionProfile();
	}

	/**
	 * Create an instance of     {@link CreateSubstitutionProfileResponse     }
	 */
	public CreateSubstitutionProfileResponse createCreateSubstitutionProfileResponse() {
		return new CreateSubstitutionProfileResponse();
	}

	/**
	 * Create an instance of     {@link DeleteProfile     }
	 */
	public DeleteProfile createDeleteProfile() {
		return new DeleteProfile();
	}

	/**
	 * Create an instance of     {@link DeleteProfileResponse     }
	 */
	public DeleteProfileResponse createDeleteProfileResponse() {
		return new DeleteProfileResponse();
	}

	/**
	 * Create an instance of     {@link GetAllProfiles     }
	 */
	public GetAllProfiles createGetAllProfiles() {
		return new GetAllProfiles();
	}

	/**
	 * Create an instance of     {@link GetAllProfilesResponse     }
	 */
	public GetAllProfilesResponse createGetAllProfilesResponse() {
		return new GetAllProfilesResponse();
	}

	/**
	 * Create an instance of     {@link GetMyTaskModelIds     }
	 */
	public GetMyTaskModelIds createGetMyTaskModelIds() {
		return new GetMyTaskModelIds();
	}

	/**
	 * Create an instance of     {@link GetMyTaskModelIdsResponse     }
	 */
	public GetMyTaskModelIdsResponse createGetMyTaskModelIdsResponse() {
		return new GetMyTaskModelIdsResponse();
	}

	/**
	 * Create an instance of     {@link GetProfileById     }
	 */
	public GetProfileById createGetProfileById() {
		return new GetProfileById();
	}

	/**
	 * Create an instance of     {@link GetProfileByIdResponse     }
	 */
	public GetProfileByIdResponse createGetProfileByIdResponse() {
		return new GetProfileByIdResponse();
	}

	/**
	 * Create an instance of     {@link GetProfileByKey     }
	 */
	public GetProfileByKey createGetProfileByKey() {
		return new GetProfileByKey();
	}

	/**
	 * Create an instance of     {@link GetProfileByKeyResponse     }
	 */
	public GetProfileByKeyResponse createGetProfileByKeyResponse() {
		return new GetProfileByKeyResponse();
	}

	/**
	 * Create an instance of     {@link GetTaskModel     }
	 */
	public GetTaskModel createGetTaskModel() {
		return new GetTaskModel();
	}

	/**
	 * Create an instance of     {@link GetTaskModelResponse     }
	 */
	public GetTaskModelResponse createGetTaskModelResponse() {
		return new GetTaskModelResponse();
	}

	/**
	 * Create an instance of     {@link TaskModelDto     }
	 */
	public TaskModelDto createTaskModelDto() {
		return new TaskModelDto();
	}

	/**
	 * Create an instance of     {@link SubstitutionProfileDto     }
	 */
	public SubstitutionProfileDto createSubstitutionProfileDto() {
		return new SubstitutionProfileDto();
	}

	/**
	 * Create an instance of     {@link SubstitutionProfileResponse     }
	 */
	public SubstitutionProfileResponse createSubstitutionProfileResponse() {
		return new SubstitutionProfileResponse();
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link CreateSubstitutionProfile     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "createSubstitutionProfile")
	public JAXBElement<CreateSubstitutionProfile> createCreateSubstitutionProfile(CreateSubstitutionProfile value) {
		return new JAXBElement<CreateSubstitutionProfile>(_CreateSubstitutionProfile_QNAME,
				CreateSubstitutionProfile.class, null, value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link CreateSubstitutionProfileResponse     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "createSubstitutionProfileResponse")
	public JAXBElement<CreateSubstitutionProfileResponse> createCreateSubstitutionProfileResponse(
			CreateSubstitutionProfileResponse value) {
		return new JAXBElement<CreateSubstitutionProfileResponse>(_CreateSubstitutionProfileResponse_QNAME,
				CreateSubstitutionProfileResponse.class, null, value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link DeleteProfile     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "deleteProfile")
	public JAXBElement<DeleteProfile> createDeleteProfile(DeleteProfile value) {
		return new JAXBElement<DeleteProfile>(_DeleteProfile_QNAME, DeleteProfile.class, null, value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link DeleteProfileResponse     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "deleteProfileResponse")
	public JAXBElement<DeleteProfileResponse> createDeleteProfileResponse(DeleteProfileResponse value) {
		return new JAXBElement<DeleteProfileResponse>(_DeleteProfileResponse_QNAME, DeleteProfileResponse.class, null,
				value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link GetAllProfiles     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getAllProfiles")
	public JAXBElement<GetAllProfiles> createGetAllProfiles(GetAllProfiles value) {
		return new JAXBElement<GetAllProfiles>(_GetAllProfiles_QNAME, GetAllProfiles.class, null, value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link GetAllProfilesResponse     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getAllProfilesResponse")
	public JAXBElement<GetAllProfilesResponse> createGetAllProfilesResponse(GetAllProfilesResponse value) {
		return new JAXBElement<GetAllProfilesResponse>(_GetAllProfilesResponse_QNAME, GetAllProfilesResponse.class,
				null, value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link GetMyTaskModelIds     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getMyTaskModelIds")
	public JAXBElement<GetMyTaskModelIds> createGetMyTaskModelIds(GetMyTaskModelIds value) {
		return new JAXBElement<GetMyTaskModelIds>(_GetMyTaskModelIds_QNAME, GetMyTaskModelIds.class, null, value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link GetMyTaskModelIdsResponse     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getMyTaskModelIdsResponse")
	public JAXBElement<GetMyTaskModelIdsResponse> createGetMyTaskModelIdsResponse(GetMyTaskModelIdsResponse value) {
		return new JAXBElement<GetMyTaskModelIdsResponse>(_GetMyTaskModelIdsResponse_QNAME,
				GetMyTaskModelIdsResponse.class, null, value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link GetProfileById     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getProfileById")
	public JAXBElement<GetProfileById> createGetProfileById(GetProfileById value) {
		return new JAXBElement<GetProfileById>(_GetProfileById_QNAME, GetProfileById.class, null, value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link GetProfileByIdResponse     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getProfileByIdResponse")
	public JAXBElement<GetProfileByIdResponse> createGetProfileByIdResponse(GetProfileByIdResponse value) {
		return new JAXBElement<GetProfileByIdResponse>(_GetProfileByIdResponse_QNAME, GetProfileByIdResponse.class,
				null, value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link GetProfileByKey     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getProfileByKey")
	public JAXBElement<GetProfileByKey> createGetProfileByKey(GetProfileByKey value) {
		return new JAXBElement<GetProfileByKey>(_GetProfileByKey_QNAME, GetProfileByKey.class, null, value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link GetProfileByKeyResponse     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getProfileByKeyResponse")
	public JAXBElement<GetProfileByKeyResponse> createGetProfileByKeyResponse(GetProfileByKeyResponse value) {
		return new JAXBElement<GetProfileByKeyResponse>(_GetProfileByKeyResponse_QNAME, GetProfileByKeyResponse.class,
				null, value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link GetTaskModel     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getTaskModel")
	public JAXBElement<GetTaskModel> createGetTaskModel(GetTaskModel value) {
		return new JAXBElement<GetTaskModel>(_GetTaskModel_QNAME, GetTaskModel.class, null, value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link GetTaskModelResponse     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getTaskModelResponse")
	public JAXBElement<GetTaskModelResponse> createGetTaskModelResponse(GetTaskModelResponse value) {
		return new JAXBElement<GetTaskModelResponse>(_GetTaskModelResponse_QNAME, GetTaskModelResponse.class, null,
				value);
	}

	/**
	 * Create an instance of     {@link JAXBElement     }       {@code     <}       {@link TaskModelDto     }       {@code     >}    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "taskModelDto")
	public JAXBElement<TaskModelDto> createTaskModelDto(TaskModelDto value) {
		return new JAXBElement<TaskModelDto>(_TaskModelDto_QNAME, TaskModelDto.class, null, value);
	}

	/**
	 * Create an instance of               {@link GetAllUserGroup               }
	 */
	public GetAllUserGroup createGetAllUserGroup() {
		return new GetAllUserGroup();
	}

	/**
	 * Create an instance of               {@link GetAllUserGroupResponse               }
	 */
	public GetAllUserGroupResponse createGetAllUserGroupResponse() {
		return new GetAllUserGroupResponse();
	}

	/**
	 * Create an instance of               {@link GetAllUserRole               }
	 */
	public GetAllUserRole createGetAllUserRole() {
		return new GetAllUserRole();
	}

	/**
	 * Create an instance of               {@link GetAllUserRoleResponse               }
	 */
	public GetAllUserRoleResponse createGetAllUserRoleResponse() {
		return new GetAllUserRoleResponse();
	}

	/**
	 * Create an instance of               {@link GetAllUsers               }
	 */
	public GetAllUsers createGetAllUsers() {
		return new GetAllUsers();
	}

	/**
	 * Create an instance of               {@link GetAllUsersResponse               }
	 */
	public GetAllUsersResponse createGetAllUsersResponse() {
		return new GetAllUsersResponse();
	}

	/**
	 * Create an instance of               {@link GetLoggedInUser               }
	 */
	public GetLoggedInUser createGetLoggedInUser() {
		return new GetLoggedInUser();
	}

	/**
	 * Create an instance of               {@link GetLoggedInUserResponse               }
	 */
	public GetLoggedInUserResponse createGetLoggedInUserResponse() {
		return new GetLoggedInUserResponse();
	}

	/**
	 * Create an instance of               {@link GetUserDetailsAssignedInGroup               }
	 */
	public GetUserDetailsAssignedInGroup createGetUserDetailsAssignedInGroup() {
		return new GetUserDetailsAssignedInGroup();
	}

	/**
	 * Create an instance of               {@link GetUserDetailsAssignedInGroupResponse               }
	 */
	public GetUserDetailsAssignedInGroupResponse createGetUserDetailsAssignedInGroupResponse() {
		return new GetUserDetailsAssignedInGroupResponse();
	}

	/**
	 * Create an instance of               {@link GetUserDetailsByUserId               }
	 */
	public GetUserDetailsByUserId createGetUserDetailsByUserId() {
		return new GetUserDetailsByUserId();
	}

	/**
	 * Create an instance of               {@link GetUserDetailsByUserIdResponse               }
	 */
	public GetUserDetailsByUserIdResponse createGetUserDetailsByUserIdResponse() {
		return new GetUserDetailsByUserIdResponse();
	}

	/**
	 * Create an instance of               {@link GetUserEmailByuserId               }
	 */
	public GetUserEmailByuserId createGetUserEmailByuserId() {
		return new GetUserEmailByuserId();
	}

	/**
	 * Create an instance of               {@link GetUserEmailByuserIdResponse               }
	 */
	public GetUserEmailByuserIdResponse createGetUserEmailByuserIdResponse() {
		return new GetUserEmailByuserIdResponse();
	}

	/**
	 * Create an instance of               {@link GetUserGroupByuserId               }
	 */
	public GetUserGroupByuserId createGetUserGroupByuserId() {
		return new GetUserGroupByuserId();
	}

	/**
	 * Create an instance of               {@link GetUserGroupByuserIdResponse               }
	 */
	public GetUserGroupByuserIdResponse createGetUserGroupByuserIdResponse() {
		return new GetUserGroupByuserIdResponse();
	}

	/**
	 * Create an instance of               {@link GetUserRoleByuserId               }
	 */
	public GetUserRoleByuserId createGetUserRoleByuserId() {
		return new GetUserRoleByuserId();
	}

	/**
	 * Create an instance of               {@link GetUserRoleByuserIdResponse               }
	 */
	public GetUserRoleByuserIdResponse createGetUserRoleByuserIdResponse() {
		return new GetUserRoleByuserIdResponse();
	}

	/**
	 * Create an instance of               {@link GetUsersAssignedInGroup               }
	 */
	public GetUsersAssignedInGroup createGetUsersAssignedInGroup() {
		return new GetUsersAssignedInGroup();
	}

	/**
	 * Create an instance of               {@link GetUsersAssignedInGroupResponse               }
	 */
	public GetUsersAssignedInGroupResponse createGetUsersAssignedInGroupResponse() {
		return new GetUsersAssignedInGroupResponse();
	}

	/**
	 * Create an instance of               {@link GetUsersByRole               }
	 */
	public GetUsersByRole createGetUsersByRole() {
		return new GetUsersByRole();
	}

	/**
	 * Create an instance of               {@link GetUsersByRoleResponse               }
	 */
	public GetUsersByRoleResponse createGetUsersByRoleResponse() {
		return new GetUsersByRoleResponse();
	}

	/**
	 * Create an instance of               {@link GroupInfoDto               }
	 */
	public GroupInfoDto createGroupInfoDto() {
		return new GroupInfoDto();
	}

	/**
	 * Create an instance of               {@link RoleInfoDto               }
	 */
	public RoleInfoDto createRoleInfoDto() {
		return new RoleInfoDto();
	}

	/**
	 * Create an instance of               {@link UserDetailsDto               }
	 */
	public UserDetailsDto createUserDetailsDto() {
		return new UserDetailsDto();
	}

	/**
	 * Create an instance of               {@link UserGroupDto               }
	 */
	public UserGroupDto createUserGroupDto() {
		return new UserGroupDto();
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetAllUserGroup               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getAllUserGroup")
	public JAXBElement<GetAllUserGroup> createGetAllUserGroup(GetAllUserGroup value) {
		return new JAXBElement<GetAllUserGroup>(_GetAllUserGroup_QNAME, GetAllUserGroup.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetAllUserGroupResponse               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getAllUserGroupResponse")
	public JAXBElement<GetAllUserGroupResponse> createGetAllUserGroupResponse(GetAllUserGroupResponse value) {
		return new JAXBElement<GetAllUserGroupResponse>(_GetAllUserGroupResponse_QNAME, GetAllUserGroupResponse.class,
				null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetAllUserRole               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getAllUserRole")
	public JAXBElement<GetAllUserRole> createGetAllUserRole(GetAllUserRole value) {
		return new JAXBElement<GetAllUserRole>(_GetAllUserRole_QNAME, GetAllUserRole.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetAllUserRoleResponse               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getAllUserRoleResponse")
	public JAXBElement<GetAllUserRoleResponse> createGetAllUserRoleResponse(GetAllUserRoleResponse value) {
		return new JAXBElement<GetAllUserRoleResponse>(_GetAllUserRoleResponse_QNAME, GetAllUserRoleResponse.class,
				null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetAllUsers               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getAllUsers")
	public JAXBElement<GetAllUsers> createGetAllUsers(GetAllUsers value) {
		return new JAXBElement<GetAllUsers>(_GetAllUsers_QNAME, GetAllUsers.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetAllUsersResponse               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getAllUsersResponse")
	public JAXBElement<GetAllUsersResponse> createGetAllUsersResponse(GetAllUsersResponse value) {
		return new JAXBElement<GetAllUsersResponse>(_GetAllUsersResponse_QNAME, GetAllUsersResponse.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetLoggedInUser               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getLoggedInUser")
	public JAXBElement<GetLoggedInUser> createGetLoggedInUser(GetLoggedInUser value) {
		return new JAXBElement<GetLoggedInUser>(_GetLoggedInUser_QNAME, GetLoggedInUser.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetLoggedInUserResponse               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getLoggedInUserResponse")
	public JAXBElement<GetLoggedInUserResponse> createGetLoggedInUserResponse(GetLoggedInUserResponse value) {
		return new JAXBElement<GetLoggedInUserResponse>(_GetLoggedInUserResponse_QNAME, GetLoggedInUserResponse.class,
				null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUserDetailsAssignedInGroup               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUserDetailsAssignedInGroup")
	public JAXBElement<GetUserDetailsAssignedInGroup> createGetUserDetailsAssignedInGroup(
			GetUserDetailsAssignedInGroup value) {
		return new JAXBElement<GetUserDetailsAssignedInGroup>(_GetUserDetailsAssignedInGroup_QNAME,
				GetUserDetailsAssignedInGroup.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUserDetailsAssignedInGroupResponse               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUserDetailsAssignedInGroupResponse")
	public JAXBElement<GetUserDetailsAssignedInGroupResponse> createGetUserDetailsAssignedInGroupResponse(
			GetUserDetailsAssignedInGroupResponse value) {
		return new JAXBElement<GetUserDetailsAssignedInGroupResponse>(_GetUserDetailsAssignedInGroupResponse_QNAME,
				GetUserDetailsAssignedInGroupResponse.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUserDetailsByUserId               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUserDetailsByUserId")
	public JAXBElement<GetUserDetailsByUserId> createGetUserDetailsByUserId(GetUserDetailsByUserId value) {
		return new JAXBElement<GetUserDetailsByUserId>(_GetUserDetailsByUserId_QNAME, GetUserDetailsByUserId.class,
				null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUserDetailsByUserIdResponse               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUserDetailsByUserIdResponse")
	public JAXBElement<GetUserDetailsByUserIdResponse> createGetUserDetailsByUserIdResponse(
			GetUserDetailsByUserIdResponse value) {
		return new JAXBElement<GetUserDetailsByUserIdResponse>(_GetUserDetailsByUserIdResponse_QNAME,
				GetUserDetailsByUserIdResponse.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUserEmailByuserId               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUserEmailByuserId")
	public JAXBElement<GetUserEmailByuserId> createGetUserEmailByuserId(GetUserEmailByuserId value) {
		return new JAXBElement<GetUserEmailByuserId>(_GetUserEmailByuserId_QNAME, GetUserEmailByuserId.class, null,
				value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUserEmailByuserIdResponse               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUserEmailByuserIdResponse")
	public JAXBElement<GetUserEmailByuserIdResponse> createGetUserEmailByuserIdResponse(
			GetUserEmailByuserIdResponse value) {
		return new JAXBElement<GetUserEmailByuserIdResponse>(_GetUserEmailByuserIdResponse_QNAME,
				GetUserEmailByuserIdResponse.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUserGroupByuserId               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUserGroupByuserId")
	public JAXBElement<GetUserGroupByuserId> createGetUserGroupByuserId(GetUserGroupByuserId value) {
		return new JAXBElement<GetUserGroupByuserId>(_GetUserGroupByuserId_QNAME, GetUserGroupByuserId.class, null,
				value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUserGroupByuserIdResponse               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUserGroupByuserIdResponse")
	public JAXBElement<GetUserGroupByuserIdResponse> createGetUserGroupByuserIdResponse(
			GetUserGroupByuserIdResponse value) {
		return new JAXBElement<GetUserGroupByuserIdResponse>(_GetUserGroupByuserIdResponse_QNAME,
				GetUserGroupByuserIdResponse.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUserRoleByuserId               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUserRoleByuserId")
	public JAXBElement<GetUserRoleByuserId> createGetUserRoleByuserId(GetUserRoleByuserId value) {
		return new JAXBElement<GetUserRoleByuserId>(_GetUserRoleByuserId_QNAME, GetUserRoleByuserId.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUserRoleByuserIdResponse               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUserRoleByuserIdResponse")
	public JAXBElement<GetUserRoleByuserIdResponse> createGetUserRoleByuserIdResponse(
			GetUserRoleByuserIdResponse value) {
		return new JAXBElement<GetUserRoleByuserIdResponse>(_GetUserRoleByuserIdResponse_QNAME,
				GetUserRoleByuserIdResponse.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUsersAssignedInGroup               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUsersAssignedInGroup")
	public JAXBElement<GetUsersAssignedInGroup> createGetUsersAssignedInGroup(GetUsersAssignedInGroup value) {
		return new JAXBElement<GetUsersAssignedInGroup>(_GetUsersAssignedInGroup_QNAME, GetUsersAssignedInGroup.class,
				null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUsersAssignedInGroupResponse               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUsersAssignedInGroupResponse")
	public JAXBElement<GetUsersAssignedInGroupResponse> createGetUsersAssignedInGroupResponse(
			GetUsersAssignedInGroupResponse value) {
		return new JAXBElement<GetUsersAssignedInGroupResponse>(_GetUsersAssignedInGroupResponse_QNAME,
				GetUsersAssignedInGroupResponse.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUsersByRole               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUsersByRole")
	public JAXBElement<GetUsersByRole> createGetUsersByRole(GetUsersByRole value) {
		return new JAXBElement<GetUsersByRole>(_GetUsersByRole_QNAME, GetUsersByRole.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GetUsersByRoleResponse               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getUsersByRoleResponse")
	public JAXBElement<GetUsersByRoleResponse> createGetUsersByRoleResponse(GetUsersByRoleResponse value) {
		return new JAXBElement<GetUsersByRoleResponse>(_GetUsersByRoleResponse_QNAME, GetUsersByRoleResponse.class,
				null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link GroupInfoDto               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "groupInfoDto")
	public JAXBElement<GroupInfoDto> createGroupInfoDto(GroupInfoDto value) {
		return new JAXBElement<GroupInfoDto>(_GroupInfoDto_QNAME, GroupInfoDto.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link RoleInfoDto               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "roleInfoDto")
	public JAXBElement<RoleInfoDto> createRoleInfoDto(RoleInfoDto value) {
		return new JAXBElement<RoleInfoDto>(_RoleInfoDto_QNAME, RoleInfoDto.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link UserDetailsDto               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "userDetailsDto")
	public JAXBElement<UserDetailsDto> createUserDetailsDto(UserDetailsDto value) {
		return new JAXBElement<UserDetailsDto>(_UserDetailsDto_QNAME, UserDetailsDto.class, null, value);
	}

	/**
	 * Create an instance of               {@link JAXBElement               }                           {@code               <}                           {@link UserGroupDto               }                           {@code               >}              }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "userGroupDto")
	public JAXBElement<UserGroupDto> createUserGroupDto(UserGroupDto value) {
		return new JAXBElement<UserGroupDto>(_UserGroupDto_QNAME, UserGroupDto.class, null, value);
	}

	/**
	 * Create an instance of                   {@link CustomAttributeDto                   }
	 */
	public CustomAttributeDto createCustomAttributeDto() {
		return new CustomAttributeDto();
	}

	/**
	 * Create an instance of                   {@link CustomAttributeDto.CustomAttribute                   }
	 */
	public CustomAttributeDto.CustomAttribute createCustomAttributeDtoCustomAttribute() {
		return new CustomAttributeDto.CustomAttribute();
	}

	/**
	 * Create an instance of                   {@link GetCustomAttributes                   }
	 */
	public GetCustomAttributes createGetCustomAttributes() {
		return new GetCustomAttributes();
	}

	/**
	 * Create an instance of                   {@link GetCustomAttributesResponse                   }
	 */
	public GetCustomAttributesResponse createGetCustomAttributesResponse() {
		return new GetCustomAttributesResponse();
	}

	/**
	 * Create an instance of                   {@link CustomAttributeDto.CustomAttribute.Entry                   }
	 */
	public CustomAttributeDto.CustomAttribute.Entry createCustomAttributeDtoCustomAttributeEntry() {
		return new CustomAttributeDto.CustomAttribute.Entry();
	}

	/**
	 * Create an instance of                   {@link JAXBElement                   }                                   {@code                   <}                                   {@link GetCustomAttributes                   }                                   {@code                   >}                  }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getCustomAttributes")
	public JAXBElement<GetCustomAttributes> createGetCustomAttributes(GetCustomAttributes value) {
		return new JAXBElement<GetCustomAttributes>(_GetCustomAttributes_QNAME, GetCustomAttributes.class, null, value);
	}

	/**
	 * Create an instance of                   {@link JAXBElement                   }                                   {@code                   <}                                   {@link GetCustomAttributesResponse                   }                                   {@code                   >}                  }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getCustomAttributesResponse")
	public JAXBElement<GetCustomAttributesResponse> createGetCustomAttributesResponse(
			GetCustomAttributesResponse value) {
		return new JAXBElement<GetCustomAttributesResponse>(_GetCustomAttributesResponse_QNAME,
				GetCustomAttributesResponse.class, null, value);
	}

	/**
	 * Create an instance of                     {@link AddNote                     }
	 */
	public AddNote createAddNote() {
		return new AddNote();
	}

	/**
	 * Create an instance of                     {@link AddNoteResponse                     }
	 */
	public AddNoteResponse createAddNoteResponse() {
		return new AddNoteResponse();
	}

	/**
	 * Create an instance of                     {@link ClaimAndDelegate                     }
	 */
	public ClaimAndDelegate createClaimAndDelegate() {
		return new ClaimAndDelegate();
	}

	/**
	 * Create an instance of                     {@link ClaimAndDelegateResponse                     }
	 */
	public ClaimAndDelegateResponse createClaimAndDelegateResponse() {
		return new ClaimAndDelegateResponse();
	}

	/**
	 * Create an instance of                     {@link ClaimTask                     }
	 */
	public ClaimTask createClaimTask() {
		return new ClaimTask();
	}

	/**
	 * Create an instance of                     {@link ClaimTaskResponse                     }
	 */
	public ClaimTaskResponse createClaimTaskResponse() {
		return new ClaimTaskResponse();
	}

	/**
	 * Create an instance of                     {@link Complete                     }
	 */
	public Complete createComplete() {
		return new Complete();
	}

	/**
	 * Create an instance of                     {@link CompleteResponse                     }
	 */
	public CompleteResponse createCompleteResponse() {
		return new CompleteResponse();
	}

	/**
	 * Create an instance of                     {@link Delegate                     }
	 */
	public Delegate createDelegate() {
		return new Delegate();
	}

	/**
	 * Create an instance of                     {@link DelegateResponse                     }
	 */
	public DelegateResponse createDelegateResponse() {
		return new DelegateResponse();
	}

	/**
	 * Create an instance of                     {@link GetNotes                     }
	 */
	public GetNotes createGetNotes() {
		return new GetNotes();
	}

	/**
	 * Create an instance of                     {@link GetNotesResponse                     }
	 */
	public GetNotesResponse createGetNotesResponse() {
		return new GetNotesResponse();
	}

	/**
	 * Create an instance of                     {@link Nominate                     }
	 */
	public Nominate createNominate() {
		return new Nominate();
	}

	/**
	 * Create an instance of                     {@link NominateResponse                     }
	 */
	public NominateResponse createNominateResponse() {
		return new NominateResponse();
	}

	/**
	 * Create an instance of                     {@link Release                     }
	 */
	public Release createRelease() {
		return new Release();
	}

	/**
	 * Create an instance of                     {@link ReleaseResponse                     }
	 */
	public ReleaseResponse createReleaseResponse() {
		return new ReleaseResponse();
	}

	/**
	 * Create an instance of                     {@link NoteDto                     }
	 */
	public NoteDto createNoteDto() {
		return new NoteDto();
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link AddNote                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "addNote")
	public JAXBElement<AddNote> createAddNote(AddNote value) {
		return new JAXBElement<AddNote>(_AddNote_QNAME, AddNote.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link AddNoteResponse                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "addNoteResponse")
	public JAXBElement<AddNoteResponse> createAddNoteResponse(AddNoteResponse value) {
		return new JAXBElement<AddNoteResponse>(_AddNoteResponse_QNAME, AddNoteResponse.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link ClaimAndDelegate                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "claimAndDelegate")
	public JAXBElement<ClaimAndDelegate> createClaimAndDelegate(ClaimAndDelegate value) {
		return new JAXBElement<ClaimAndDelegate>(_ClaimAndDelegate_QNAME, ClaimAndDelegate.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link ClaimAndDelegateResponse                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "claimAndDelegateResponse")
	public JAXBElement<ClaimAndDelegateResponse> createClaimAndDelegateResponse(ClaimAndDelegateResponse value) {
		return new JAXBElement<ClaimAndDelegateResponse>(_ClaimAndDelegateResponse_QNAME,
				ClaimAndDelegateResponse.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link ClaimTask                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "claimTask")
	public JAXBElement<ClaimTask> createClaimTask(ClaimTask value) {
		return new JAXBElement<ClaimTask>(_ClaimTask_QNAME, ClaimTask.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link ClaimTaskResponse                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "claimTaskResponse")
	public JAXBElement<ClaimTaskResponse> createClaimTaskResponse(ClaimTaskResponse value) {
		return new JAXBElement<ClaimTaskResponse>(_ClaimTaskResponse_QNAME, ClaimTaskResponse.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link Complete                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "complete")
	public JAXBElement<Complete> createComplete(Complete value) {
		return new JAXBElement<Complete>(_Complete_QNAME, Complete.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link CompleteResponse                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "completeResponse")
	public JAXBElement<CompleteResponse> createCompleteResponse(CompleteResponse value) {
		return new JAXBElement<CompleteResponse>(_CompleteResponse_QNAME, CompleteResponse.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link Delegate                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "delegate")
	public JAXBElement<Delegate> createDelegate(Delegate value) {
		return new JAXBElement<Delegate>(_Delegate_QNAME, Delegate.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link DelegateResponse                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "delegateResponse")
	public JAXBElement<DelegateResponse> createDelegateResponse(DelegateResponse value) {
		return new JAXBElement<DelegateResponse>(_DelegateResponse_QNAME, DelegateResponse.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link GetNotes                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getNotes")
	public JAXBElement<GetNotes> createGetNotes(GetNotes value) {
		return new JAXBElement<GetNotes>(_GetNotes_QNAME, GetNotes.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link GetNotesResponse                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "getNotesResponse")
	public JAXBElement<GetNotesResponse> createGetNotesResponse(GetNotesResponse value) {
		return new JAXBElement<GetNotesResponse>(_GetNotesResponse_QNAME, GetNotesResponse.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link Nominate                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "nominate")
	public JAXBElement<Nominate> createNominate(Nominate value) {
		return new JAXBElement<Nominate>(_Nominate_QNAME, Nominate.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link NominateResponse                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "nominateResponse")
	public JAXBElement<NominateResponse> createNominateResponse(NominateResponse value) {
		return new JAXBElement<NominateResponse>(_NominateResponse_QNAME, NominateResponse.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link Release                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "release")
	public JAXBElement<Release> createRelease(Release value) {
		return new JAXBElement<Release>(_Release_QNAME, Release.class, null, value);
	}

	/**
	 * Create an instance of                     {@link JAXBElement                     }                                       {@code                     <}                                       {@link ReleaseResponse                     }                                       {@code                     >}                    }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "releaseResponse")
	public JAXBElement<ReleaseResponse> createReleaseResponse(ReleaseResponse value) {
		return new JAXBElement<ReleaseResponse>(_ReleaseResponse_QNAME, ReleaseResponse.class, null, value);
	}

	/**
	 * Create an instance of                         {@link CancelProcess                         }
	 */
	public CancelProcess createCancelProcess() {
		return new CancelProcess();
	}

	/**
	 * Create an instance of                         {@link CancelProcessResponse                         }
	 */
	public CancelProcessResponse createCancelProcessResponse() {
		return new CancelProcessResponse();
	}

	/**
	 * Create an instance of                         {@link JAXBElement                         }                                               {@code                         <}                                               {@link CancelProcess                         }                                               {@code                         >}                        }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "cancelProcess")
	public JAXBElement<CancelProcess> createCancelProcess(CancelProcess value) {
		return new JAXBElement<CancelProcess>(_CancelProcess_QNAME, CancelProcess.class, null, value);
	}

	/**
	 * Create an instance of                         {@link JAXBElement                         }                                               {@code                         <}                                               {@link CancelProcessResponse                         }                                               {@code                         >}                        }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "cancelProcessResponse")
	public JAXBElement<CancelProcessResponse> createCancelProcessResponse(CancelProcessResponse value) {
		return new JAXBElement<CancelProcessResponse>(_CancelProcessResponse_QNAME, CancelProcessResponse.class, null,
				value);
	}

	/**
	 * Create an instance of                           {@link LoggedInUser                           }
	 */
	public LoggedInUser createLoggedInUser() {
		return new LoggedInUser();
	}

	/**
	 * Create an instance of                           {@link LoggedInUserResponse                           }
	 */
	public LoggedInUserResponse createLoggedInUserResponse() {
		return new LoggedInUserResponse();
	}

	/**
	 * Create an instance of                           {@link JAXBElement                           }                                                   {@code                           <}                                                   {@link LoggedInUser                           }                                                   {@code                           >}                          }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "loggedInUser")
	public JAXBElement<LoggedInUser> createLoggedInUser(LoggedInUser value) {
		return new JAXBElement<LoggedInUser>(_LoggedInUser_QNAME, LoggedInUser.class, null, value);
	}

	/**
	 * Create an instance of                           {@link JAXBElement                           }                                                   {@code                           <}                                                   {@link LoggedInUserResponse                           }                                                   {@code                           >}                          }
	 */
	@XmlElementDecl(namespace = "http://incture.com/pmc/poadapter/services/", name = "loggedInUserResponse")
	public JAXBElement<LoggedInUserResponse> createLoggedInUserResponse(LoggedInUserResponse value) {
		return new JAXBElement<LoggedInUserResponse>(_LoggedInUserResponse_QNAME, LoggedInUserResponse.class, null,
				value);
	}

}
