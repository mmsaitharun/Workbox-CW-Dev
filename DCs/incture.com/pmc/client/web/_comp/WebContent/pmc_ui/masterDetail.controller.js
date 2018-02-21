sap.ui.define([
	'jquery.sap.global',
	'sap/ui/core/mvc/Controller',
	'sap/m/MessageBox'
], function (jQuery, Controller, MessageBox) {
	"use strict";
sap.ui.controller("pmc_ui.masterDetail", {
/**
* Called when a controller is instantiated and its View controls (if available) are already created.
* Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
* @memberOf pmc_ui.masterDetail
*/
	onInit: function() {
		this.oRouter = sap.ui.core.UIComponent.getRouterFor(this);
		this.getView().setModel(oUserDefaultModel,"oUserDefaultModel");
		var that = this;
		this.busy = new sap.m.BusyDialog();
		this.busy.open();
		this.oRouter.attachRoutePatternMatched(function(oEvent) {
			var id = that.getView().getAggregation("content")[0].getId().split("-")[0] + "--navList";
			var oCore = sap.ui.getCore().byId(id);
			var oUserDefaultData = oUserDefaultModel.getData();
			if (oUserDefaultModel.getData().userId == "") {
				that.getLoggedInUserDetail();
			}
			var oNavModel=new sap.ui.model.json.JSONModel();
			oNavModel.loadData("json/navPageDetails.json", null, false);
			that.getView().setModel(oNavModel,"oNavModel");
			that.getUserRoles();
			var oMailListModel = new sap.ui.model.json.JSONModel();
			that.getView().setModel(oMailListModel, "oMailListModel");
			

			if (oEvent.getParameter("name") === "masterDetail") {

			}
			else if (oEvent.getParameter("name") === "userWorkList") {
				oCore.setSelectedItem(oCore.getItems()[1]);
				that.onCollapseExapandPress(true);
				oUserDefaultData.displayBack = false;
				oUserDefaultData.navBackFrom = "";
				oUserDefaultData.selectedKey = ""; 
				oUserDefaultData.processName = "";
				oUserDefaultData.previousScreen = "processFlow";
			}
			else if (oEvent.getParameter("name") === "processAging") {
				oCore.setSelectedItem(oCore.getItems()[2]);
				that.onCollapseExapandPress(true);
				oUserDefaultData.navBackFrom = "";
				oUserDefaultData.previousScreen = "processAging";
				oUserDefaultData.displayBack = false;
			}
			else if (oEvent.getParameter("name") === "slaManagement") {
				oCore.setSelectedItem(oCore.getItems()[3]);
				that.onCollapseExapandPress(true);
				oUserDefaultData.navBackFrom = "";
				oUserDefaultData.selectedKey = ""; 
				oUserDefaultData.processName = "";
				oUserDefaultData.previousScreen = "slaManagement";
				oUserDefaultData.displayBack = false;
			}
			else if (oEvent.getParameter("name") === "ruleMaintenance") {
				oCore.setSelectedItem(oCore.getItems()[4]);
				that.onCollapseExapandPress(true);
				oUserDefaultData.navBackFrom = "";
				oUserDefaultData.selectedKey = ""; 
				oUserDefaultData.processName = "";
				oUserDefaultData.previousScreen = "ruleMaintenance";
				oUserDefaultData.displayBack = false;
			}
			else if (oEvent.getParameter("name") === "inbox") {
				oCore.setSelectedItem(oCore.getItems()[0]);
				that.onCollapseExapandPress(false);
				oUserDefaultData.navBackFrom = "";
				oUserDefaultData.selectedKey = ""; 
				oUserDefaultData.processName = "";
				oUserDefaultData.displayBack = false;
			}
			else if(oEvent.getParameter("name") === "userWorkLoad"){
				oUserDefaultData.displayBack = true;
				that.onCollapseExapandPress(true);
				oUserDefaultData.selectedKey = ""; 
				oUserDefaultData.processName = "";
				oUserDefaultData.navBackFrom = "userWorkLoad";
			}
			else if(oEvent.getParameter("name") === "processFlow"){
				oUserDefaultData.displayBack = true;
				that.onCollapseExapandPress(true);
				oUserDefaultData.navBackFrom = "processFlow";
			} else if(oEvent.getParameter("name") === "adminPage"){
				oCore.setSelectedItem(oCore.getItems()[5]);
				oUserDefaultData.displayBack = false;
				that.onCollapseExapandPress(true);
				oUserDefaultData.selectedKey = ""; 
				oUserDefaultData.processName = "";
				oUserDefaultData.navBackFrom = "adminPage";
			}
			oUserDefaultModel.refresh();
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
			oUserDefaultModel.setProperty('/userMailId', that.getView().getModel("oUserDetailModel").getData().emailId);
			oUserDefaultModel.refresh(true);
		});
		oUserDetailModel.attachRequestFailed(function(oEvent) {
		});
	},
	
	onClose : function(oEvent){
		console.log(oEvent);
		this.getView().byId("SplitApp").setMode(sap.m.SplitAppMode.HideMode)
	},
	
	onCollapseExapandPress: function (expanded) {
		var viewContent = this.getView().getAggregation("content")[0].getId().split("-")[0];
		var id = viewContent + "--SplitApp-Master";
		var oCore = sap.ui.getCore().byId(id);
		var sideNavigation = this.getView().byId('sideNavigation');
//		var expanded = !sideNavigation.getExpanded();
		if(expanded){
			oCore.setWidth("13.6rem");
		} else {
			oCore.setWidth("4%");
		}
//		sideNavigation.setExpanded(expanded);
		return viewContent + "--navList";
	},
	
	onNavPress : function(oEvent){
		var viewName = oEvent.getSource().getText();
		switch(viewName){
			case "Unified Inbox":{
				this.onInbox();
				break;
			}
			case "Task Management":{
				this.onWorkLoad();
				break;
			}
			case "Process Tracker":{
				this.onProcessAging();
				break;
			}
			case "Rules Board":{
				this.onsla();
				break;
			}
			case "Action Hub":{
				this.onRule();
				break;
			}
			case "Settings":{
				this.onAdmin();
				break;
			}
		};
	},
	
	onWorkLoad : function(){
		var id = this.getView().getAggregation("content")[0].getId().split("-")[0] + "--navList";
		var oCore = sap.ui.getCore().byId(id);
		var viewContent = this.onCollapseExapandPress(true);
		this.checkBeforeNavigation(id, "userWorkList");
	},
	
	onProcessAging : function(){
		var id = this.getView().getAggregation("content")[0].getId().split("-")[0] + "--navList";
		var oCore = sap.ui.getCore().byId(id);
		var viewContent = this.onCollapseExapandPress(true);
		this.checkBeforeNavigation(id, "processAging");
	},
	
	onsla : function(){
		var id = this.getView().getAggregation("content")[0].getId().split("-")[0] + "--navList";
		var oCore = sap.ui.getCore().byId(id);
		var viewContent = this.onCollapseExapandPress(true);
		this.checkBeforeNavigation(id, "slaManagement");
	},
	
	onInbox : function(){
		var id = this.getView().getAggregation("content")[0].getId().split("-")[0] + "--navList";
		var oCore = sap.ui.getCore().byId(id);
		var viewContent = this.onCollapseExapandPress(false);
		this.checkBeforeNavigation(id, "inbox");
	},
	
	onRule : function() {
		var id = this.getView().getAggregation("content")[0].getId().split("-")[0] + "--navList";
		var oCore = sap.ui.getCore().byId(id);
		var viewContent = this.onCollapseExapandPress(true);
		this.checkBeforeNavigation(id, "ruleMaintenance");
	}, 
	
	onAdmin: function(){
		var id = this.getView().getAggregation("content")[0].getId().split("-")[0] + "--navList";
		var oCore = sap.ui.getCore().byId(id);
		var viewContent = this.onCollapseExapandPress(true);
		this.checkBeforeNavigation(id, "adminPage");
	},
	
	checkBeforeNavigation: function(id, view){
		var that = this;
		var oCore = sap.ui.getCore().byId(id);
		var oUserDefaultData=oUserDefaultModel.getData();
		if (oUserDefaultData.reportHeader == "ACTION HUB" && oUserDefaultData.ruleChange) {
			sap.m.MessageBox.show(that.getView().getModel("i18n").getResourceBundle().getText("RULES_WARNING"),
					sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
					function(oEvt){
						if(oEvt==="NO"){
							oCore.setSelectedItem(oCore.getItems()[4]);
							return;
						}
						else if(oEvt==="YES"){
							oUserDefaultData.ruleChange = false;
							oUserDefaultData.userNavigateScreen="";
							oUserDefaultModel.refresh(true);
							that.oRouter.navTo(view,{});
						}
					},
					sap.m.MessageBox.Action.YES);
					return;
		} else if (oUserDefaultData.reportHeader == "RULES BOARD" && oUserDefaultData.slaUpdate) {
			sap.m.MessageBox.show(that.getView().getModel("i18n").getResourceBundle().getText("SLA_WARNING"),
					sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
					function(oEvt){
						if(oEvt==="NO"){
							oCore.setSelectedItem(oCore.getItems()[3]);
							return;
						}
						else if(oEvt==="YES"){
							oUserDefaultData.slaUpdate = false;
							oUserDefaultData.userNavigateScreen="";
							oUserDefaultModel.refresh(true);
							that.oRouter.navTo(view,{});
						}
					},
					sap.m.MessageBox.Action.YES);
					return;
		} else if (oUserDefaultData.reportHeader == "SETTINGS" && oUserDefaultData.settingsChange) {
			sap.m.MessageBox.show("All the changes will be lost. Do you still want to go further?",
					sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
					function(oEvt){
						if(oEvt==="NO"){
							oCore.setSelectedItem(oCore.getItems()[5]);
							return;
						}
						else if(oEvt==="YES"){
							oUserDefaultData.userNavigateScreen="";
							oUserDefaultModel.refresh(true);
							that.oRouter.navTo(view,{});
						}
					},
					sap.m.MessageBox.Action.YES);
					return;
		} else {
			this.oRouter.navTo(view,{});
		}
	},
	
	onEmailClick:function(){
		if (!this.mailTo) {
			this.mailTo = sap.ui.xmlfragment("idGeneralMail",
					"pmc.fragments.mailTo", this);
			this.getView().addDependent(this.mailTo);
		}
		var data = oUserDefaultModel.getData();
		data.mailSubject = "";
		data.mailBody = "";
		data.mailTo = "";
		data.btnText = "Send";
		data.sendNotification = true;
		oUserDefaultModel.refresh();
		var oMultiInput = sap.ui.getCore().byId('idGeneralMail--idMultiInput');
		oMultiInput.removeAllTokens();
		oMultiInput.setValue("");
		oMultiInput.addValidator( function(args) {
			var text = args.text;
			var regex = /^[a-zA-Z0-9._-]+@([a-zA-Z0-9.-]+\.)+[a-zA-Z0-9.-]{2,4}$/ ;
			if (text.match(regex)) {
				return new sap.m.Token({ key : text, text : text});
			} else {
				toastMessage("Enter a valid mail ID");
			}
		});
		this.mailTo.open();
	},

	onMailFragmentClose:function(){
		if(this.mailTo){
			this.mailTo.close();
			this.busy.close();
		}
		//this.mailTo.destroy()
	},
	
	onUserClick: function(oEvent) {
		var oView = this.getView();
        this.oDialog = oView.byId("userDialog");
        if (!this.oDialog) {
        	this.oDialog = sap.ui.xmlfragment("pmc.fragments.userPreference", this);
           oView.addDependent(this.oDialog);
        }
        this.oDialog.setModel(oUserDefaultModel, "oUserDefaultModel");
        this.oDialog.openBy(oEvent.getSource());
	},
	
	onSettings: function() {
		/*if (!this._oSettingDialog) {
			this._oSettingDialog = sap.ui.xmlfragment("pmc.fragments.appSetting", this);
			this.getView().addDependent(this._oSettingDialog);
		}
		this._oSettingDialog.open();*/
		oUserDefaultModel.setProperty("/toPersonalizeTab",true);
		oUserDefaultModel.setProperty("/selectedConfig","personalization");
		oUserDefaultModel.setProperty("/selectedSubConfig","User Profile");
		oUserDefaultModel.setProperty("/selectedTableType","User Profile");
		oUserDefaultModel.refresh();
		this.oRouter.navTo("adminPage");
	},
	
	onSettingChanges: function() {
		this.onSettingClose();
	},
	
	onSkinSettingChange: function() {
		replaceTheChange();
		if (sap.ui.core.UIComponent.getRouterFor(this)) {
			var hash = sap.ui.core.UIComponent.getRouterFor(this)._oRouter._prevMatchedRequest;
			if (hash == "processAging") {
				sap.ui.core.UIComponent.getRouterFor(this).getView('pmc_ui.processAging').getController().updateGraphProperty();
			} else if (hash.includes("userWorkLoad")) {
				sap.ui.core.UIComponent.getRouterFor(this).getView('pmc_ui.userWorkLoad').getController().updateGraphProperty();
			}
		}
		document.getElementById('contentFrameId').contentWindow.replaceTheChange();
	},
	
	onSettingClose: function() {
		this._oSettingDialog.close();
	},
		
	onNotificationClick: function(oEvent) {
		var oView = this.getView();
		this.oNotificationDialog = oView.byId("userDialog");
		var oNotificationModel=new sap.ui.model.json.JSONModel();
		var data = [{
			"title":"Notification 1"
		},{
			"title":"Notification 12"
		},{
			"title":"Notification 142"
		},{
			"title":"Notification 1346"
		}];
		oNotificationModel.setData({"notificationListDto":data});
		oNotificationModel.refresh();
        if (!this.oNotificationDialog) {
        	this.oNotificationDialog = sap.ui.xmlfragment("pmc.fragments.notificationList", this);
           oView.addDependent(this.oNotificationDialog);
        }
        this.getView().setModel(oNotificationModel, "oNotificationModel");
        this.oNotificationDialog.openBy(oEvent.getSource());
        this.getView().$().find(".userListClass").parent().addClass("flexItemCustomClass")
	},

/**
* Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
* (NOT before the first rendering! onInit() is used for that one!).
* @memberOf pmc_ui.masterDetail
*/
//	onBeforeRendering: function() {
//
//	},

/**
* Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
* This hook is the same one that SAPUI5 controls get after being rendered.
* @memberOf pmc_ui.masterDetail
*/
	onAfterRendering: function() {
		var id = this.getView().getAggregation("content")[0].getId().split("-")[0] + "--SplitApp-Master";
//		sap.ui.getCore().byId(id).setWidth("15.69rem");
		this.busy.close();
	},
	
	onBackToLaunchPage: function(){
		var oUserDefaultData=oUserDefaultModel.getData();  
		oUserDefaultData.userNavigateScreen = "";
		oUserDefaultData.selectedKey = "";
		oUserDefaultData.processName = "";
		oUserDefaultModel.refresh(true);
		this.oRouter.navTo("launchPage");
	},
	
	onNavBack: function(){
		var navBackFrom = oUserDefaultModel.getData().navBackFrom;
		if(navBackFrom === "userWorkLoad"){
			this.onNavBackFromUL();
		}
		else if(navBackFrom === "processFlow"){
			this.onNavBackFromPL();
		}
	},
	
	/*  Process Flow   */
	onNavBackFromPL: function(){
		var oUserDefaultData=oUserDefaultModel.getData();
		var oldScreen = oUserDefaultData.userNavigateScreen;
		var userId = oUserDefaultData.userId;
		if(oldScreen==="userWorkList") {
			this.oRouter.navTo("userWorkList");
		} else if (oldScreen==="userWorkLoad"){
			this.oRouter.navTo("userWorkLoad",{
				ContextPath : userId
			});
		} else if (oldScreen==="processAging"){
			this.oRouter.navTo("processAging");
		} else {
			this.oRouter.navTo("inbox");
		}
	},
	
	/*  user workLoad   */
	onNavBackFromUL: function(){
		this.oRouter.navTo("userWorkList");
	},
	
	onChangeSugg : function(oEvent){
		var that = this;
		var oUserDefaultData = oUserDefaultModel.getData();
		var getInput = oEvent.getSource();
		var value = oEvent.getSource().getValue();
		if (value) {
			var oMailListModel = that.getView().getModel("oMailListModel");
			if (jQuery.isEmptyObject(oMailListModel.getData())) {
				oMailListModel.loadData("/pmc/poadapter/ume/searchUser/*",null,true);
				oMailListModel.attachRequestCompleted(function(oEvt) { 
					var data = oMailListModel.getData();
					if (!(data.userDto instanceof Array)) {
						data.userDto = [data.userDto];
					}
					oMailListModel.setSizeLimit(data.userDto.length);
					oMailListModel.refresh();
					getInput.bindAggregation("suggestionItems", {
						path: "oMailListModel>/userDto",
						template: new sap.ui.core.ListItem({
							text: "{oMailListModel>emailId}",
							additionalText: "{oMailListModel>firstName} {oMailListModel>lastName}"
						})
					});
					that.fnMultiFilters(value, getInput);
				});
			} else {
				that.fnMultiFilters(value, getInput);
			}
		}
	},
	
	fnMultiFilters: function(value, getInput){
		var aFilters;
		if (value) {
			var oFilter1 = new sap.ui.model.Filter("firstName", sap.ui.model.FilterOperator.Contains, value);
			var oFilter2 = new sap.ui.model.Filter("lastName", sap.ui.model.FilterOperator.Contains, value);
			var oFilter3 = new sap.ui.model.Filter("loginId", sap.ui.model.FilterOperator.Contains, value);
			aFilters = new sap.ui.model.Filter({
				filters: [oFilter1,oFilter2,oFilter3],
				and: false
			});
		}
		getInput.getBinding("suggestionItems").filter(aFilters);
	},
	
	onLogout:function(){
		this.oRouter.navTo("launchPage");
		var url="/webdynpro/resources/sap.com/tc~lm~itsam~ui~mainframe~wd/_wd_execute_logout";
		var loc = window.location.href;
		$.ajax({
			method: "GET",
			url: url,
			cache:false,
			async:false,
			complete:function(arg){
				localStorage.clear();
				location.reload(true);
				window.open(loc,"_self");
				location.reload(true);
			},
			error:function(){

			}
		});
	},
	
	onSendEmail: function(oEvent){
		var that = this;
		this.busy.open();
		var data = oUserDefaultModel.getData();
		var oView = this.getView();
		var oMultiInput = sap.ui.getCore().byId('idGeneralMail--idMultiInput');
		var inputValue = oMultiInput.getValue();
		if (inputValue != "") {
			toastMessage("Enter valid mail ID");
			this.busy.close();
			return;
		}
		var mailTokenList = oMultiInput.getTokens();
		if (mailTokenList.length == 0){
			toastMessage("Enter mail ID");
			this.busy.close();
			return;
		}
		if (data.mailSubject == "") {
			sap.m.MessageBox.show("Do you want to send this message without subject?",
				sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
				function(oEvt){
				if(oEvt==="NO"){
					that.busy.close();
					return;
				}
				else if(oEvt==="YES"){
					that.sendMail(data,oMultiInput);
				}
			},sap.m.MessageBox.Action.YES);
		} else {
			that.sendMail(data,oMultiInput);
		}
	},
	
	sendMail : function(data,oMultiInput) {
		var that = this;
		var oMailLoadModel = new sap.ui.model.json.JSONModel();
		that.getView().setModel(oMailLoadModel, "oMailLoadModel");
		var oHeader = {
				"Content-Type" : "application/json;charset=utf-8"
		};
		var mailList = [];
		var mailTokenList = oMultiInput.getTokens();
		for(var i in mailTokenList)
		{
			mailList.push(mailTokenList[i].getText());
		}
		var aData = { 
				"mailTo" : mailList.join(),
				"mailSubject" :data.mailSubject, 
				"mailBody" : data.mailBody,
				}
		oMailLoadModel.loadData("/appone/pmc/notification/sendMail",JSON.stringify(aData), true, "POST",false, false, oHeader);
		oMailLoadModel.attachRequestCompleted(function(oEvent) {
			that.busy.close();
			that.onMailFragmentClose();
			toastMessage(that.getView().getModel("oMailLoadModel").getData().message);
		});
		oMailLoadModel.attachRequestFailed(function(oEvent) {
			that.busy.close();
			toastMessage(that.getView().getModel("oMailLoadModel").getData().message);
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
			/*var oNavModel = that.getView().getModel("oNavModel");
			var oNavData = oNavModel.getData().navigationPages;
			var lookup = {};
			for (var j in userDto) {
				if(userDto[j].value == "true") {
					lookup[userDto[j].key] = userDto[j].key;
				}
			}
			var newNavData = [];
			for (var i in oNavData) {
				if (typeof lookup[oNavData[i].roleName] != 'undefined') {
					newNavData.push(oNavData[i]);
				}
				if (oNavData[i].roleName == "") {
					newNavData.push(oNavData[i]);
				}
			}
			oNavModel.setData({"navigationPages":newNavData});
			oNavModel.refresh();*/
		});
		oUserRoleModel.attachRequestFailed(function(oEvent) {
		});
	},

/**
* Called when the Controller is destroyed. Use this one to free resources and finalize activities.
* @memberOf pmc_ui.masterDetail
*/
//	onExit: function() {
//
//	}
});
});