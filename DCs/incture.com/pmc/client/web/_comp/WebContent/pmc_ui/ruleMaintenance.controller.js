sap.ui.define([
	'jquery.sap.global',
	'sap/ui/core/mvc/Controller',
	'sap/m/MessageBox'
], function (jQuery, Controller, MessageBox) {
	"use strict";
sap.ui.controller("pmc_ui.ruleMaintenance", {

/**
* Called when a controller is instantiated and its View controls (if available) are already created.
* Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
* @memberOf pmc_ui.ruleMaintenance
*/
	onInit: function() {
		var that = this;
		var oView = that.getView();
		this.busy = new sap.m.BusyDialog();
		this.oRouter = sap.ui.core.UIComponent.getRouterFor(this);
		oView.setModel(oUserDefaultModel,"oUserDefaultModel");
		var oModel=new sap.ui.model.json.JSONModel();
		oModel.loadData("json/timeUnits.json", null, false);
		oView.setModel(oModel,"oModel");
		this.oRouter.attachRoutePatternMatched(function(oEvent) {
			if (oEvent.getParameter("name") === "ruleMaintenance") {
				oUserDefaultModel.getData().reportHeader = "ACTION HUB";
				oUserDefaultModel.refresh(true);
				oView.byId("processNameColumn").setVisible(true);
				oView.byId("taskNameColumn").setVisible(false);
				oView.byId("idTaskTypeBtn").setSelectedKey("PROCESS");
				that.fnProcessNames();
				var oMailModel = new sap.ui.model.json.JSONModel();
				that.getView().setModel(oMailModel, "oMailModel");
			}
		});
	},
	
	handleProcessTaskSelection:function(oEvent){
		var afilters = [];
		var afilter;
		var selectedTab = oEvent.getSource().getSelectedKey();
		var oView = this.getView();
		var taskSelection = true;
		var validation = this.fnValidationOnRule(taskSelection);
		if(validation){
			if (selectedTab == "PROCESS") {
				oView.byId("processNameColumn").setVisible(true);
				oView.byId("taskNameColumn").setVisible(false);
				afilter = new sap.ui.model.Filter("type",sap.ui.model.FilterOperator.EQ,"process");
			} else {
				oView.byId("processNameColumn").setVisible(false);
				oView.byId("taskNameColumn").setVisible(true);
				afilter = new sap.ui.model.Filter("type",sap.ui.model.FilterOperator.EQ,"task");
			}
			afilters.push(afilter);
			var binding = oView.byId("idRuleTable").getBinding("items");
			binding.filter(afilters, "Application");
			oView.getModel("oRuleDataModel").refresh(true);
		} else {
			if (selectedTab == "PROCESS") {
				oView.byId("idTaskTypeBtn").setSelectedKey("TASK");
			} else {
				oView.byId("idTaskTypeBtn").setSelectedKey("PROCESS");
			}
		}
	},
	
	fnProcessNames: function(){
		var that = this;
		var oView = that.getView();
		that.busy.open();
		var oProcessNameModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oProcessNameModel, "oProcessNameModel");
		oProcessNameModel.loadData("/appone/pmc/sla/process",null,true);
		oProcessNameModel.attachRequestCompleted(function(oEvent) {
			var processNameModel=oView.getModel("oProcessNameModel");
			var processNameData = processNameModel.getData();
			if (processNameData){
				if (!(processNameData.slaProcessNames instanceof Array)) {
					processNameData.slaProcessNames = [processNameData.slaProcessNames];
				}
				processNameModel.refresh();
				that.busy.close();
				oView.byId("idRuleProcessName").setSelectedKey(processNameData.slaProcessNames[0].processName);
				oUserDefaultModel.getData().ruleProcessKey = processNameData.slaProcessNames[0].processName;
				that.fnRuleDetails(processNameData.slaProcessNames[0].processName);
			}
		});
		oProcessNameModel.attachRequestFailed(function(oEvent) {
			that.busy.close();
			toastMessage(oView.getModel("oProcessNameModel").getData().responseMessage.message);
		});
	},
	
	fnRuleDetails: function(processName){
		var that = this;
		var oView = that.getView();
		that.busy.open();
		var oRuleDataModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oRuleDataModel, "oRuleDataModel");
		oRuleDataModel.loadData("/appone/pmc/rules/details/"+processName,null,true);
		oRuleDataModel.attachRequestCompleted(function(oEvent) {
			var ruleDataModel=oView.getModel("oRuleDataModel");
			var ruleDataData=ruleDataModel.getData();
			if (ruleDataData && ruleDataData.message.statusCode == "0"){
				if (!(ruleDataData.ruleManagementDtos instanceof Array)) {
					ruleDataData.ruleManagementDtos = [ruleDataModel.oData.ruleManagementDtos];
				}
				if (!(ruleDataData.taskList instanceof Array)) {
					ruleDataData.taskList = [ruleDataData.taskList];
				}
				var ruleData = ruleDataData.ruleManagementDtos;
				for(var i = 0; i < ruleData.length; i++) {
					ruleData[i].statusEnabled = true;
					ruleData[i].change = "Read";
					ruleData[i].statusOld = ruleData[i].status;
					ruleData[i].actionOld = ruleData[i].action;
				}
				var filterKey = oView.byId("idTaskTypeBtn").getSelectedKey().toLowerCase();
				var afilters = [];
				var afilter;
				afilter = new sap.ui.model.Filter("type",sap.ui.model.FilterOperator.EQ,filterKey);
				afilters.push(afilter);
				var binding = oView.byId("idRuleTable").getBinding("items");
				binding.filter(afilters, "Application");
				ruleDataModel.refresh();
				that.busy.close();
			} else {
				that.busy.close();
				toastMessage(ruleDataData.responseMessage.message);
			}
		});
		oRuleDataModel.attachRequestFailed(function(oEvent) {
			that.busy.close();
			toastMessage(oView.getModel("oRuleDataModel").getData().responseMessage.message);
		});
	},
	
	onAddingNewRule: function(){
		var oView = this.getView();
		var validation = this.fnValidationOnRule();
		if(validation){
			var ruleDataModel=oView.getModel("oRuleDataModel");
			var ruleDataData = ruleDataModel.getData();
			var rulesData = ruleDataModel.getData().ruleManagementDtos;
			var obj = {
					"name" : oView.byId("idRuleProcessName").getSelectedKey(),
					"processDisplayName" : oView.byId("idRuleProcessName").getSelectedItem().getText(),
					"statusEnabled" : true,
					"status" : "Active",
					"statusOld" : "Active",
					"change" : "Create",
					"type" : oView.byId("idTaskTypeBtn").getSelectedKey()
			}
			if (oView.byId("idTaskTypeBtn").getSelectedKey() == "TASK") {
				if (ruleDataData.taskList[0]) {
					obj.taskName = ruleDataData.taskList[0].taskName;
					obj.slaCount = ruleDataData.taskList[0].slaCount;
					obj.slaUnit = ruleDataData.taskList[0].slaUnit;
				} else {
					toastMessage("There are no tasks under this process");
					return;
				}
			} else {
				var processName = oView.byId("idRuleProcessName").getSelectedKey();
				var processData = oView.getModel("oProcessNameModel").oData.slaProcessNames;
				for(var i=0; i<processData.length; i++){
					if (processData[i].processName == processName) {
						obj.slaCount = processData[i].slaCount;
						obj.slaUnit = processData[i].slaUnit;
						break;
					}
				}
			}
			if (!rulesData) {
				var ruleManagementDtos = []
				ruleDataModel.setProperty("/ruleManagementDtos", ruleManagementDtos);
				ruleDataData.ruleManagementDtos.push(obj);
			} else {
				rulesData.unshift(obj);
			}
			ruleDataModel.refresh(true);
			oUserDefaultModel.getData().ruleChange = true;
			oUserDefaultModel.refresh(true);
		}
	},
	
	onProcessNameSelection: function(oEvent){
		var that = this;
		var processName = oEvent.getSource().getSelectedKey();
		var oUserDefaultData = oUserDefaultModel.getData();
		if (oUserDefaultData.ruleChange) {
			sap.m.MessageBox.show(that.getView().getModel("i18n").getResourceBundle().getText("RULES_WARNING"),
					sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
					function(oEvt){
						if(oEvt==="NO"){
							if (oUserDefaultData.ruleProcessKey != processName) {
								that.getView().byId("idRuleProcessName").setSelectedKey(oUserDefaultModel.getData().ruleProcessKey);
							}
							return;
						}
						else if(oEvt==="YES"){
							oUserDefaultData.ruleChange = false;
							oUserDefaultData.ruleProcessKey = processName;
							oUserDefaultModel.refresh(true);
							that.fnRuleDetails(processName);
						}
					},
					sap.m.MessageBox.Action.YES);
					return;
		} else {
			oUserDefaultData.ruleChange = false;
			oUserDefaultData.ruleProcessKey = processName;
			oUserDefaultModel.refresh(true);
			this.fnRuleDetails(processName);
		}
	},
	
	onResetTheRule: function(){
		var that = this;
		var processName = this.getView().byId("idRuleProcessName").getSelectedKey();
		var oUserDefaultData = oUserDefaultModel.getData();
		if (oUserDefaultModel.getData().ruleChange) {
			sap.m.MessageBox.show(that.getView().getModel("i18n").getResourceBundle().getText("RULES_WARNING"),
					sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
					function(oEvt){
						if(oEvt==="NO"){
							return;
						}
						else if(oEvt==="YES"){
							oUserDefaultData.ruleChange = false;
							oUserDefaultModel.refresh(true);
							that.fnRuleDetails(processName);
						}
					},
					sap.m.MessageBox.Action.YES);
					return;
		} else {
			oUserDefaultData.ruleChange = false;
			oUserDefaultModel.refresh(true);
			this.fnRuleDetails(processName);
		}
	},
	
	onDelete: function() {
		var oView = this.getView();
		var selectedItems = oView.byId("idRuleTable").getSelectedItems();
		var i18n = oView.getModel("i18n").getResourceBundle();
		if (selectedItems.length <= 0) {
			toastMessage(i18n.getText("SELECT_TO_DELETE"));
		} else {
			for(var i = 0; i < selectedItems.length; i++){
				var selectedLine = selectedItems[i].getBindingContext("oRuleDataModel").getObject();
				if (selectedLine.change != "Create" && selectedLine.change != "CreDel") {
					selectedLine.change = "Delete";
				} else {
					selectedLine.change = "CreDel";
				}
				selectedLine.statusEnabled = false;
			}
			toastMessage(i18n.getText("RULES_MARKED_SUCCESS"));
			oView.byId("idRuleTable").removeSelections()
			oView.getModel("oRuleDataModel").refresh();
			oUserDefaultModel.getData().ruleChange = true;
			oUserDefaultModel.refresh(true);
		}
	},
	
	onSwitchChange: function(oEvent){
		var status = oEvent.getSource().getState();
		var uiRuleStatus = "Paused";
		if (status) {
			uiRuleStatus = "Active";
		}
		var ruleDataModel=oEvent.getSource().getBindingContext("oRuleDataModel");
		var ruleDataObject = ruleDataModel.getObject();
		ruleDataObject.status = uiRuleStatus;
		var change = ruleDataObject.change;
		if (change && change == "Create") {
			ruleDataObject.change = "Create";
		} else {
			ruleDataObject.change = "Update";
		}
		this.getView().getModel("oRuleDataModel").refresh(true);
		oUserDefaultModel.getData().ruleChange = true;
		oUserDefaultModel.refresh(true);
	},
	
	onSubmit: function(){
		var that = this;
		var validation = this.fnValidationOnRule();
		if(validation){
			sap.m.MessageBox.show(that.getView().getModel("i18n").getResourceBundle().getText("RULE_UPDATE_WARNING"),
					sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
					function(oEvt){
						if(oEvt==="NO"){
							return;
						}
						else if(oEvt==="YES"){
							that.updateTheStatus();
							that.onRuleUpdate();
						}
					},
					sap.m.MessageBox.Action.YES);
					return;
		}
	},
	
	fnValidationOnRule: function(taskSelection){
		var oView = this.getView();
		var i18n = oView.getModel("i18n").getResourceBundle();
		var selectedKey = oView.byId("idTaskTypeBtn").getSelectedKey();
		if (taskSelection) {
			if (selectedKey == "PROCESS") {
				selectedKey = "TASK";
			} else { 
				selectedKey = "PROCESS";
			}
		}
		var ruleData = oView.getModel("oRuleDataModel").getData().ruleManagementDtos;
		if (ruleData) {
			for(var i = 0; i < ruleData.length; i++){
				if (ruleData[i].status == "Active" && ruleData[i].change != "CreDel" && ruleData[i].change != "Delete" && ruleData[i].type == selectedKey) {
					if (!ruleData[i].thresholdLimitCount || !ruleData[i].thresholdLimitUnit || !ruleData[i].action) {
						toastMessage(i18n.getText("THRESHOLD_UNIT_ACTION_SET_CHECK"));
						return;
					}
					if(ruleData[i].slaCount && ruleData[i].thresholdLimitCount){
						var slaCount = getSlaTimings(parseInt(ruleData[i].slaCount), ruleData[i].slaUnit).toString();
						var thresholdCount = getSlaTimings(parseInt(ruleData[i].thresholdLimitCount), ruleData[i].thresholdLimitUnit).toString();
						if (parseInt(thresholdCount) > parseInt(slaCount)) {
							toastMessage(i18n.getText("THRESHOLD_SLA_VALUE_CHECK"));
							return;
						}
					}
				}
			}
			return true;
		} else {
			return true;
		}
	},
	
	updateTheStatus: function(){
		var ruleData = this.getView().getModel("oRuleDataModel").getData().ruleManagementDtos;
		for(var i = 0; i < ruleData.length; i++){
			if (ruleData[i].change == "Read") {
				if ((ruleData[i].thresholdLimitCount != ruleData[i].thresholdLimitCount) || 
						(ruleData[i].status != ruleData[i].statusOld) || 
						(ruleData[i].thresholdLimitUnit != ruleData[i].thresholdLimitCount) || 
						(ruleData[i].action != ruleData[i].actionOld)) {
					ruleData[i].change = "Update";
				}
			}
		}
		this.getView().getModel("oRuleDataModel").refresh(true);
	},
	
	onRuleUpdate: function(){
		var that = this;
		this.busy.open();
		var oRuleUpdateModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(oRuleUpdateModel,"oRuleUpdateModel");
		var oHeader = {
				"Content-Type" : "application/json;charset=utf-8"
			};
		
		var aData = { 
				"ruleManagementDto" : this.getView().getModel("oRuleDataModel").getData().ruleManagementDtos
			}

		oRuleUpdateModel.loadData("/appone/pmc/rules/updateRules",JSON.stringify(aData), true, "POST",false, false, oHeader);
		oRuleUpdateModel.attachRequestCompleted(function(oEvent) {
			oUserDefaultModel.getData().ruleChange = false;
			oUserDefaultModel.refresh(true);
			that.busy.close();
			that.onResetTheRule();
		});
		oRuleUpdateModel.attachRequestFailed(function(oEvent) {
			that.busy.close();
		});
	},
	
	onUnDelete: function(oEvent) {
		var oView = this.getView();
		var selectedItems = oView.byId("idRuleTable").getSelectedItems();
		if (selectedItems.length <= 0) {
			toastMessage(oView.getModel("i18n").getResourceBundle().getText("SELECT_TO_REVERT"));
		} else {
			for(var i = 0; i < selectedItems.length; i++){
				var selectedLine = selectedItems[i].getBindingContext("oRuleDataModel").getObject();
				if (selectedLine.change == "CreDel" || selectedLine.change == "Create") {
					selectedLine.change = "Create";
				} else {
					selectedLine.change = "Read";
				}
				selectedLine.statusEnabled = true;
			}
			oView.byId("idRuleTable").removeSelections()
			oView.getModel("oRuleDataModel").refresh(true);
			oUserDefaultModel.getData().ruleChange = true;
			oUserDefaultModel.refresh(true);
		}
	},
	
	onValidateRule: function(oEvent){
		var input = oEvent.getSource().getValue();
		var regex = "^[1-9]([0-9]*)$";
		if (input) {
			if(!input.match(regex)) {
				oEvent.getSource().setValue("");
				toastMessage(this.getView().getModel("i18n").getResourceBundle().getText("INVALID_NUMBER"));
				return;
			} else {
				oUserDefaultModel.getData().ruleChange = true;
				oUserDefaultModel.refresh(true);
			}
		} else {
			oEvent.getSource().setValue("");
			oUserDefaultModel.getData().ruleChange = true;
			oUserDefaultModel.refresh(true);
		}
	},
	
	onUpdateRule: function(){
		oUserDefaultModel.getData().ruleChange = true;
		oUserDefaultModel.refresh(true);
	},
	
	onUpdateAction: function(oEvent){
		var that=this;
		var oUserDefaultData=oUserDefaultModel.getData();
		oUserDefaultData.ruleChange = true;
		oUserDefaultModel.refresh(true);
		if (oEvent.getSource().getSelectedKey() != "") {
			if (!that.mailTo) {
				that.mailTo = sap.ui.xmlfragment("idNotification",
						"pmc.fragments.mailTo", that);
				that.getView().addDependent(that.mailTo);
			}
			var oMultiInput = sap.ui.getCore().byId('idNotification--idMultiInput');
			oMultiInput.removeAllTokens();
			oMultiInput.setValue("");
			this.oObj=oEvent.getSource().getBindingContext('oRuleDataModel').getObject();
			oUserDefaultData.ruleId = this.oObj.ruleId;
			if (oEvent.getSource().getSelectedKey() == "Send Notification") {
				oUserDefaultData.sendNotification = true;
				oUserDefaultData.mailTo = this.oObj.emailIdList;
			} else {
				oUserDefaultData.sendNotification = false;
				oUserDefaultData.mailTo = oUserDefaultData.userMailId;
			}
			oUserDefaultData.mailSubject = this.oObj.emailSubject;
			oUserDefaultData.mailBody = this.oObj.emailBody;
			oUserDefaultData.btnText = "Submit";
			oUserDefaultModel.refresh();
			if(oUserDefaultData.mailTo && oUserDefaultData.mailTo != ""){
				var mailList = oUserDefaultData.mailTo.split(",");
				var tokenList = [];
				for (var i in mailList) {
					tokenList.push(new sap.m.Token({ key : mailList[i], text : mailList[i]}));
				}
				oMultiInput.setTokens(tokenList);
			}
			oMultiInput.addValidator( function(args) {
				var text = args.text;
				var regex = /^[a-zA-Z0-9._-]+@([a-zA-Z0-9.-]+\.)+[a-zA-Z0-9.-]{2,4}$/ ;
				if (text.match(regex)) {
					return new sap.m.Token({ key : text, text : text});
				} else {
					toastMessage("Enter a valid mail id");
				}
			});
			that.mailTo.open();
		}
	},
	
	onMailFragmentClose:function(){
		if(this.mailTo){
			this.mailTo.close();
			this.busy.close();
		}
	},

	onSelectTask: function(oEvent) {
		oUserDefaultModel.getData().ruleChange = true;
		oUserDefaultModel.refresh(true);
		var taskName = oEvent.getSource().getSelectedKey();
		var taskObject = oEvent.getSource().getBindingContext("oRuleDataModel").getObject();
		var ruleDataModel = this.getView().getModel("oRuleDataModel");
		var taskData = ruleDataModel.getData().taskList;
		for(var i=0; i<taskData.length; i++){
			if (taskData[i].taskName == taskName) {
				taskObject.slaCount = taskData[i].slaCount;
				taskObject.slaUnit = taskData[i].slaUnit;
				break;
			}
		}
		ruleDataModel.refresh();
	},
	
	onChangeSugg : function(oEvent){
		var that = this;
		var oUserDefaultData = oUserDefaultModel.getData();
		var getInput = oEvent.getSource();
		var value = oEvent.getSource().getValue();
		if (value) {
			var oMailListModel = that.getView().getModel("oMailModel");
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
	
	onSendEmail: function(oEvent){
		var that = this;
		var data = oUserDefaultModel.getData();
		var oView = this.getView();
		var dataObj = this.oObj;
		var oMultiInput = sap.ui.getCore().byId('idNotification--idMultiInput');
		var inputValue = oMultiInput.getValue();
		if (inputValue != "") {
			toastMessage("Enter valid mail ID");
			return;
		}
		var mailTokenList = oMultiInput.getTokens();
		if (mailTokenList.length == 0){
			toastMessage("Enter mail ID");
			this.busy.close();
			return;
		}
		if (!data.mailSubject || data.mailSubject == "") {
			sap.m.MessageBox.show("Do you want to send this message without subject?",
				sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
				function(oEvt){
				if(oEvt==="NO"){
					return;
				}
				else if(oEvt==="YES"){
					that.sendMail(dataObj,oMultiInput);
				}
			},sap.m.MessageBox.Action.YES);
		} else {
			that.sendMail(dataObj,oMultiInput);
		}
	},
	
	sendMail : function(dataObj,oMultiInput){
		var data = oUserDefaultModel.getData();
		var mailTokenList = oMultiInput.getTokens();
		var mailList = [];
		for (var i in mailTokenList) {
			mailList.push(mailTokenList[i].getText());
		}
		dataObj.emailIdList = mailList.join();
		dataObj.emailSubject = data.mailSubject;
		dataObj.emailBody = data.mailBody;
		dataObj.ruleId = data.ruleId;
		this.getView().getModel("oRuleDataModel").refresh();
		this.onMailFragmentClose();
	},

/**
* Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
* (NOT before the first rendering! onInit() is used for that one!).
* @memberOf pmc_ui.ruleMaintenance
*/
//	onBeforeRendering: function() {
//
//	},

/**
* Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
* This hook is the same one that SAPUI5 controls get after being rendered.
* @memberOf pmc_ui.ruleMaintenance
*/
//	onAfterRendering: function() {
//
//	},

/**
* Called when the Controller is destroyed. Use this one to free resources and finalize activities.
* @memberOf pmc_ui.ruleMaintenance
*/
//	onExit: function() {
//
//	}

});
});