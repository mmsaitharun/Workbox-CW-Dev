sap.ui.define([
               "sap/ui/core/mvc/Controller"
               ], function(Controller) {

	return Controller.extend("pmc_ui.launchPage", {

/**
* Called when a controller is instantiated and its View controls (if available) are already created.
* Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
* @memberOf pmc_ui.launchPage
*/
	onInit: function() {
		this.oRouter = sap.ui.core.UIComponent.getRouterFor(this);
		this.getView().setModel(oUserDefaultModel,"oUserDefaultModel");
		var that = this;
		this.oRouter.attachRoutePatternMatched(function(oEvent) {
			if (oEvent.getParameter("name") === "launchPage") {
				$("#contentFrameDiv").hide();
				oUserDefaultModel.getData().userNavigateScreen = "";
				oUserDefaultModel.refresh(true);
				that.getLoggedInUserDetail();
				that.getUserRoles();
			}
		});
	},
	
	getLoggedInUserDetail: function(){
		var that = this;
		var oUserDetailModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(oUserDetailModel,"oUserDetailModel");
		oUserDetailModel.loadData("/pmc/poadapter/ume/logon",null,true);
		oUserDetailModel.attachRequestCompleted(function(oEvent) {
			oUserDefaultModel.setProperty('/loggedInUser', that.getView().getModel("oUserDetailModel").getData().displayName);
			oUserDefaultModel.setProperty('/loggedInUserId', that.getView().getModel("oUserDetailModel").getData().userId);
			oUserDefaultModel.refresh(true);
		});
		oUserDetailModel.attachRequestFailed(function(oEvent) {
		});
	},
	
/**
* Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
* (NOT before the first rendering! onInit() is used for that one!).
* @memberOf pmc_ui.launchPage
*/
//	onBeforeRendering: function() {
//
//	},

/**
* Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
* This hook is the same one that SAPUI5 controls get after being rendered.
* @memberOf pmc_ui.launchPage
*/
	onAfterRendering: function() {
		this.getView().byId("UserWorkListId").attachBrowserEvent("click", 
				function(oEvent) {
			var routerObject = this.getParent().getParent().getParent().getController().oRouter;
			routerObject.navTo("userWorkList",{});
		});
		this.getView().byId("processAgingId").attachBrowserEvent("click", 
				function(oEvent) {
			var routerObject = this.getParent().getParent().getParent().getController().oRouter;
			routerObject.navTo("processAging",{});
		});
		this.getView().byId("slaManagementId").attachBrowserEvent("click", 
				function(oEvent) {
			var routerObject = this.getParent().getParent().getParent().getController().oRouter;
			routerObject.navTo("slaManagement",{});
		});
		this.getView().byId("ruleMaintenanceId").attachBrowserEvent("click", 
				function(oEvent) {
			var routerObject = this.getParent().getParent().getParent().getController().oRouter;
			routerObject.navTo("ruleMaintenance",{});
		});
		this.getView().byId("inboxId").attachBrowserEvent("click", 
				function(oEvent) {
			var routerObject = this.getParent().getParent().getParent().getController().oRouter;
			routerObject.navTo("inbox",{});
		});
		this.getView().byId("adminConsoleId").attachBrowserEvent("click", 
				function(oEvent) {
			var routerObject = this.getParent().getParent().getParent().getController().oRouter;
			routerObject.navTo("adminPage",{});
		});
	},
	
	getUserRoles:function(){
		var that = this;
		var oUserRoleModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(oUserRoleModel,"oUserRoleModel");
		oUserRoleModel.loadData("/appone/pmc/user/pmcUserRole",null,true);
		oUserRoleModel.attachRequestCompleted(function(oEvent) {
			var userDto = oUserRoleModel.getData().roleMap.entry;
			if (!(userDto instanceof Array)) {
				data.userDto = [userDto];
			}
			oUserRoleModel.refresh();
		});
		oUserRoleModel.attachRequestFailed(function(oEvent) {
		});
	},

/**
* Called when the Controller is destroyed. Use this one to free resources and finalize activities.
* @memberOf pmc_ui.launchPage
*/
//	onExit: function() {
//
//	}
	});
});