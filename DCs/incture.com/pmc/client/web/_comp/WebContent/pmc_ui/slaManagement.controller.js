sap.ui.define([
	'jquery.sap.global',
	'sap/ui/core/mvc/Controller',
	'sap/m/MessageBox'
], function (jQuery, Controller, MessageBox) {
	"use strict";
sap.ui.controller("pmc_ui.slaManagement", {

/**
* Called when a controller is instantiated and its View controls (if available) are already created.
* Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
* @memberOf pmc_ui.slaManagement
*/
	onInit: function() {
		this.oRouter = sap.ui.core.UIComponent.getRouterFor(this);
		var that = this;
		this.busy = new sap.m.BusyDialog();
		this.getView().setModel(oUserDefaultModel,"oUserDefaultModel");
		var oModel=new sap.ui.model.json.JSONModel();
		oModel.loadData("json/timeUnits.json", null, false);
		this.getView().setModel(oModel,"oModel");
		this.oRouter.attachRoutePatternMatched(function(oEvent) {
			if (oEvent.getParameter("name") === "slaManagement") {
				var oUserDefaultData=oUserDefaultModel.getData();
				oUserDefaultData.reportHeader = "RULES BOARD";
				oUserDefaultData.processNamePos = 0;
				oUserDefaultModel.refresh(true);
				var processName = "";
				that.fnProcessSlaCount(processName, oUserDefaultData.processNamePos);
			}
		});
	},
	
	fnProcessSlaCount: function(processName, processNamePos){
		var that = this;
		var oView = that.getView();
		that.busy.open();
		var oProcessSlaCount = new sap.ui.model.json.JSONModel();
		oView.setModel(oProcessSlaCount, "oProcessSlaCount");
		oProcessSlaCount.loadData("/appone/pmc/sla/process",null,true);
		oProcessSlaCount.attachRequestCompleted(function(oEvent) {
			var processSlaCount=oView.getModel("oProcessSlaCount");
			var processSlaCountData = processSlaCount.getData();
			if (processSlaCountData){
				if (!(processSlaCountData.slaProcessNames instanceof Array)) {
					processSlaCountData.slaProcessNames = [processSlaCountData.slaProcessNames];
				}
				processSlaCount.refresh();
				that.busy.close();
				if (!processName) {
					processName = processSlaCountData.slaProcessNames[0].processName;
				}
				that.getView().byId("idProcessList").getItems()[processNamePos].addStyleClass("processListItemBoxActiveStyleClass");
				that.fnSlaDetails(processName);
			}
		});
		oProcessSlaCount.attachRequestFailed(function(oEvent) {
			that.busy.close();
			toastMessage(oView.getModel("oProcessSlaCount").getData().responseMessage.message);
		});
	},
	
	fnSlaDetails: function(processName){
		var that = this;
		var oView = that.getView();
		that.busy.open();
		var oSlaDetailModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oSlaDetailModel, "oSlaDetailModel");
		oSlaDetailModel.loadData("/appone/pmc/sla/details/"+processName,null,true);
		oSlaDetailModel.attachRequestCompleted(function(oEvent) {
			var slaDetailModel=oView.getModel("oSlaDetailModel");
			var slaDetailData = slaDetailModel.getData();
			if (slaDetailData){
				if (!(slaDetailData.slaList instanceof Array)) {
					slaDetailData.slaList = [slaDetailData.slaList];
				}
				slaDetailModel.refresh(true);
				that.busy.close();
			} else {
				that.busy.close();
				toastMessage(slaDetailData.responseMessage.message);
			}
		});
		oSlaDetailModel.attachRequestFailed(function(oEvent) {
			that.busy.close();
			toastMessage(oView.getModel("oSlaDetailModel").getData().responseMessage.message);
		});
	},
	
	onBackToLaunchPage: function(){
		oUserDefaultModel.getData().userNavigateScreen = "";
		oUserDefaultModel.refresh(true);
		this.oRouter.navTo("launchPage");
	},
	
	onSubmit: function(){
		var that = this;
		var validation = this.fnValidationOnSLA();
		if(validation){
			sap.m.MessageBox.show(that.getView().getModel("i18n").getResourceBundle().getText("UPDATE_SLA"),
					sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
					function(oEvt){
						if(oEvt==="NO"){
							return;
						}
						else if(oEvt==="YES"){
							var lineItemData = that.updateTheStatus();
							var aData = that.getView().getModel("oSlaDetailModel").getData();
							if ((aData.slaCount != aData.slaCountOld) || (aData.slaUnit != aData.slaUnitOld)) {
								aData.isUpdated = true;
							}
							aData.slaList = lineItemData;
							that.onSLAUpdate(aData);
						}
					},
					sap.m.MessageBox.Action.YES);
					return;
		}
	},
	
	fnValidationOnSLA: function(){
		var that = this;
		var i18n = that.getView().getModel("i18n").getResourceBundle();
		var slaCount = 0;
		var prioritySlaCount = 0;
		var slaData = that.getView().getModel("oSlaDetailModel").getData();
		var taskSlaData = slaData.slaList;
		if ((slaData.slaCount && !slaData.slaUnit) || (!slaData.slaCount && slaData.slaUnit)) {
			toastMessage(i18n.getText("PROCESS_SLA_WARNING"));
			return false;
		}
		for(var i = 0; i < taskSlaData.length; i++){
			if((taskSlaData[i].slaCount && !taskSlaData[i].slaUnit) || (taskSlaData[i].urgentSlaCount && !taskSlaData[i].urgentSlaUnit) || 
					(!taskSlaData[i].slaCount && taskSlaData[i].slaUnit) || (!taskSlaData[i].urgentSlaCount && taskSlaData[i].urgentSlaUnit)){
				toastMessage(i18n.getText("TASK_SLA_WARNING"));
				return false;
			}
			var slaTime = getSlaTimings(parseInt(taskSlaData[i].slaCount), taskSlaData[i].slaUnit);
			var prioritySlaTime = getSlaTimings(parseInt(taskSlaData[i].urgentSlaCount), taskSlaData[i].urgentSlaUnit);
			if (taskSlaData[i].slaCount && taskSlaData[i].urgentSlaCount) {
				if (prioritySlaTime > slaTime) {
					toastMessage(i18n.getText("PRIORITY_NORMAL_SLA_VALUE_CHECK"));
					return false;
				}
			}
			slaCount = slaCount + slaTime;
			prioritySlaCount = prioritySlaCount + prioritySlaTime;
		}
		if (slaData.slaCount) {
			var processLevelSla = getSlaTimings(parseInt(slaData.slaCount), slaData.slaUnit);
			if(slaCount > processLevelSla){
				toastMessage(i18n.getText("TASK_PROCESS_SLA_VALUE_CHECK"));
				return false;
			}
		}
		return true;
	},
	
	updateTheStatus: function(){
		var processSlaData = this.getView().getModel("oSlaDetailModel").getData().slaList;
		var slaList = [];
		for(var i = 0; i < processSlaData.length; i++){
			if((processSlaData[i].slaCount != processSlaData[i].slaCountOld) || (processSlaData[i].slaUnit != processSlaData[i].slaUnitOld) || 
					(processSlaData[i].urgentSlaCount != processSlaData[i].urgentSlaCountOld) || (processSlaData[i].urgentSlaUnit != processSlaData[i].urgentSlaUnitOld)) {
				slaList.push(processSlaData[i]);
			}
		}
		return slaList;
	},
	
	onSLAUpdate: function(aData){
		var that = this;
		this.busy.open();
		var oSLAUpdateModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(oSLAUpdateModel,"oSLAUpdateModel");
		var oHeader = {
				"Content-Type" : "application/json;charset=utf-8"
			};

		oSLAUpdateModel.loadData("/appone/pmc/sla/updateSla",JSON.stringify(aData), true, "POST",false, false, oHeader);
		oSLAUpdateModel.attachRequestCompleted(function(oEvent) {
			that.busy.close();
			oUserDefaultModel.getData().slaUpdate = false;
			oUserDefaultModel.refresh(true);
			that.onReset();
		});
		oSLAUpdateModel.attachRequestFailed(function(oEvent) {
			that.busy.close();
		});
	},
	
	onReset: function(){
		var that = this;
		var processName = that.getView().getModel("oSlaDetailModel").getData().processName;
		if (oUserDefaultModel.getData().slaUpdate) {
			sap.m.MessageBox.show(that.getView().getModel("i18n").getResourceBundle().getText("SLA_WARNING"),
					sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
					function(oEvt){
						if(oEvt==="NO"){
							return;
						}
						else if(oEvt==="YES"){
							oUserDefaultModel.getData().slaUpdate = false;
							oUserDefaultModel.refresh(true);
							that.fnProcessSlaCount(processName, oUserDefaultModel.getData().processNamePos);
						}
					},
					sap.m.MessageBox.Action.YES);
					return;
		} else {
			oUserDefaultModel.getData().ruleChange = false;
			oUserDefaultModel.refresh(true);
			this.fnProcessSlaCount(processName, oUserDefaultModel.getData().processNamePos);
		}
	},
	
	onUpdateSLA: function(){
		oUserDefaultModel.getData().slaUpdate = true;
		oUserDefaultModel.refresh(true);
	},
	
	onValidateTheRule: function(oEvent){
		var input = oEvent.getSource().getValue();
		if(input==""){
			oEvent.getSource().getParent().getItems()[1].setSelectedKey("");
			return;
		}
		var regex = "^[1-9]([0-9]*)$";
		if (input) {
			if(!input.match(regex)) {
				oEvent.getSource().setValue("");
				toastMessage(this.getView().getModel("i18n").getResourceBundle().getText("INVALID_NUMBER"));
			} else{
				oUserDefaultModel.getData().slaUpdate = true;
				oUserDefaultModel.refresh(true);
			}
		} else {
			oEvent.getSource().setValue("");
			oUserDefaultModel.getData().slaUpdate = true;
			oUserDefaultModel.refresh(true);
		}
	},

/**
* Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
* (NOT before the first rendering! onInit() is used for that one!).
* @memberOf pmc_ui.slaManagement
*/
//	onBeforeRendering: function() {
//
//	},

/**
* Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
* This hook is the same one that SAPUI5 controls get after being rendered.
* @memberOf pmc_ui.slaManagement
*/
	onAfterRendering: function() {
		var that = this;
		var oUserDefaultData = oUserDefaultModel.getData();
		this.getView().byId("processSlaList").attachBrowserEvent("click", 
		function(oEvent) {
			var self = this;
			var processList = that.getView().byId("idProcessList").getItems();
			var processNamePos = this.getBindingContext('oProcessSlaCount').sPath.split("/")[2];
			var processName = this.getBindingContext('oProcessSlaCount').getObject().processName;
			if (oUserDefaultModel.getData().slaUpdate) {
				sap.m.MessageBox.show(that.getView().getModel("i18n").getResourceBundle().getText("SLA_WARNING"),
						sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
						function(oEvt){
							if(oEvt==="NO"){
								return;
							}
							else if(oEvt==="YES"){
								oUserDefaultModel.getData().slaUpdate = false;
								for(var i = 0; i < processList.length; i++){
									processList[i].removeStyleClass("processListItemBoxActiveStyleClass");
								}
								oUserDefaultData.processNamePos = processNamePos;
								oUserDefaultModel.refresh(true);
								self.addStyleClass("processListItemBoxActiveStyleClass");
								that.fnProcessSlaCount(processName, oUserDefaultModel.getData().processNamePos);
							}
						},
						sap.m.MessageBox.Action.YES);
						return;
			} else {
				oUserDefaultData.slaUpdate = false;
				oUserDefaultData.processNamePos = processNamePos;
				oUserDefaultModel.refresh(true);
				for(var i = 0; i < processList.length; i++){
					processList[i].removeStyleClass("processListItemBoxActiveStyleClass");
				}
				this.addStyleClass("processListItemBoxActiveStyleClass");
				that.fnProcessSlaCount(processName, oUserDefaultData.processNamePos);
			}
		});
	},
/**
* Called when the Controller is destroyed. Use this one to free resources and finalize activities.
* @memberOf pmc_ui.slaManagement
*/
//	onExit: function() {
//
//	}

});
});