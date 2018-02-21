sap.ui.define([
	'jquery.sap.global',
	'sap/ui/core/mvc/Controller',
	'sap/ui/model/json/JSONModel',
	'sap/m/Button',
	'sap/m/Dialog'
], function (jQuery, Controller, JSONModel, Button, Dialog) {
	"use strict";
sap.ui.controller("pmc_ui.userWorkList", {

/**
* Called when a controller is instantiated and its View controls (if available) are already created.
* Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
* @memberOf pmc_ui.userWorkList
*/
	onInit: function() {
		this.oRouter = sap.ui.core.UIComponent.getRouterFor(this);
		var that = this;
		var oView = that.getView();
		this.noOfPages = 0;
		this.pageNumber = 1;
		this.processCount = 0;
		this.busy = new sap.m.BusyDialog();
		this.busy.open();
		oView.setModel(oUserDefaultModel,"oUserDefaultModel");
		oView.byId("idTaskTypeBtn").setSelectedKey("RESERVED");
		oUserDefaultModel.getData().userTaskStatus = "RESERVED";
		oView.byId("idGroupNameSelection").setSelectedKey("Everyone");
//		this.getWorkLoadRange();
		this.getProcessNames();
//		this.getGroupNames();
//		this.setDate();
		oUserDefaultModel.getData().userListVisible = false;
		oUserDefaultModel.refresh(true);
		this.oRouter.attachRoutePatternMatched(function(oEvent) {
			if (oEvent.getParameter("name") === "userWorkList") {
				oView.byId("PMC_TASK_AGING_TABLE").setVisible(false);
				oView.byId("userListGrid").setVisible(true);
				oView.byId("idReportSwitch").setSelectedKey("LIST");
				oView.byId("sortingTask").setVisible(true);
				oView.byId("taskDownload").setVisible(false);
				oView.byId("idWorkLoadRange").setVisible(true);
				oUserDefaultModel.getData().userNavigateScreen = "userWorkList";
				oUserDefaultModel.getData().previousScreen = "userWorkList";
				oUserDefaultModel.getData().userListVisible = false;
				oUserDefaultModel.getData().reportHeader = "TASK MANAGEMENT";
//				oUserDefaultModel.getData().enable = true;
				oUserDefaultModel.refresh(true);
				if (oView.getModel("oProcessNameModel").getData().processConfigDtos && !oView.byId("idDynamicLabel").getText()){
					oView.byId("idDynamicLabel").setText(oView.getModel("oProcessNameModel").getData().processConfigDtos[0].labelName);
				}
				if (oUserDefaultModel.getData().userList) {
					/*var filterBar = oView.byId("idSearchFilterBar");
					filterBar.setFilterBarExpanded(true);
					filterBar.search();*/
					that.fnUserData();
				}
			}
		});
		var oTaskAgeingModel = new sap.ui.model.json.JSONModel();
		var paginatedModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oTaskAgeingModel, "oTaskAgeingModel");
		oView.setModel(paginatedModel, "paginatedModel");
		oTaskAgeingModel.setSizeLimit(500);
		oTaskAgeingModel.attachRequestCompleted(function(oEvent) {
			that.busy.close();
			var taskAgeningModel = oView.getModel("oTaskAgeingModel");
			if (taskAgeningModel.getData() && taskAgeningModel.getData().message.statusCode==="0"){
				if (!(taskAgeningModel.oData.tasks instanceof Array)) {
					taskAgeningModel.oData.tasks = [taskAgeningModel.oData.tasks];
				}
				taskAgeningModel.refresh(true);
				that.onDialogCreation();
			} else {
				toastMessage(taskAgeningModel.getData().responseMessage.message);
			}
		});
		oTaskAgeingModel.attachRequestFailed(function(oEvent) {
			this.refresh(true);
			var taskAgeningModel = oView.getModel("oTaskAgeingModel");
			that.busy.close();
			toastMessage(taskAgeningModel.getData().responseMessage.message);
		});
	},
	
	/**********************Start - Loading All User Groups*****************************/
	getGroupNames: function(userList){
		if (!(userList instanceof Array)) {
			userList = [userList];
		}
		var that = this;
		var oUserGroupModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(oUserGroupModel, "oUserGroupModel");
		var userGroupDto = [];
		for(var i=0; i<userList.length; i++){
			var obj = {"groupName":userList[i]};
			userGroupDto.push(obj);
		}
		oUserGroupModel.setProperty("/userGroupDto", userGroupDto);
	},
	/**********************End - Loading All User Groups*****************************/
	
	/**********************Start - Loading the workload threshold values*****************************/
	getWorkLoadRange: function(){
		var that = this;
		var oWorkLoadRangeModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(oWorkLoadRangeModel, "oWorkLoadRangeModel");
		oWorkLoadRangeModel.loadData("/appone/pmc/config/workloadrange",null,true);
		oWorkLoadRangeModel.attachRequestCompleted(function(oEvent) {
			var workLoadRange = oWorkLoadRangeModel.getData().workloadRangeDtos;
			var oUserDefaultData = oUserDefaultModel.getData();
			if (workLoadRange) {
				for(var i=0; i<workLoadRange.length; i++){
					if (workLoadRange[i].loadType == "HIGH"){
						oUserDefaultData.highLoadHighLimit = parseInt(workLoadRange[i].highLimit,10);
						oUserDefaultData.highLoadLowLimit = parseInt(workLoadRange[i].lowLimit,10);
					} else if (workLoadRange[i].loadType == "MEDIUM"){
						oUserDefaultData.normalLoadHighLimit = parseInt(workLoadRange[i].highLimit,10);
						oUserDefaultData.normalLoadLowLimit = parseInt(workLoadRange[i].lowLimit,10);
					} else if (workLoadRange[i].loadType == "LOW"){
						oUserDefaultData.lowLoadHighLimit = parseInt(workLoadRange[i].highLimit,10);
						oUserDefaultData.lowLoadLowLimit = parseInt(workLoadRange[i].lowLimit,10);
					}
				}
				oUserDefaultModel.refresh(true);
				/*var filterBar = that.getView().byId("idSearchFilterBar");
				filterBar.setFilterBarExpanded(true);
				filterBar.search();*/
				that.fnUserData();
			} else {
				toastMessage(oWorkLoadRangeModel.getData().responseMessage.message);
			}
			
		});
	},
	/**********************End - Loading the workload threshold values*****************************/
	
	/**********************Start - Loading Process Names*****************************/
	getProcessNames: function(){
		var that = this;
		var oView = that.getView();
		var oProcessNameModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oProcessNameModel, "oProcessNameModel");
		oProcessNameModel.loadData("/appone/pmc/config/labels",null,true);
		oProcessNameModel.attachRequestCompleted(function(oEvent) {
			var processNameModel = oView.getModel("oProcessNameModel");
			var processNameData =  processNameModel.getData();
			if (processNameData && processNameData.processConfigDtos){
				if (!(processNameData.processConfigDtos instanceof Array)) {
					processNameData.processConfigDtos = [processNameData.processConfigDtos];
				}
				processNameModel.refresh();
				if (processNameData.processConfigDtos[0].labelName) {
					oUserDefaultModel.getData().enable = true;
					//oView.byId("idDynamicLabel").setLabel(processNameData.processConfigDtos[0].labelName);
					oView.byId("idDynamicLabel").setText(processNameData.processConfigDtos[0].labelName);
					oView.byId("idDynamicField").setValue("");
				} else {
					oUserDefaultModel.getData().enable = false;
				}
				oView.byId("idRequestId").setValue("");
				oUserDefaultModel.getData().userList = true;
				oUserDefaultModel.refresh(true);
				that.getGroupNames(processNameData.processConfigDtos[0].userList);
				that.getWorkLoadRange();
			} else {
				toastMessage(processNameData.responseMessage.message);
			}
		});
	},
	/**********************End - Loading Process Names*****************************/
	
	/**********************Start - On Search of Task type*****************************/
	onSearch: function(oEvent){
		var statusType = oEvent.getSource().getText().split(" ")[0].toUpperCase();
		if (statusType == "IN") {
			statusType = "RESERVED";
		} else if (statusType == "NEW") {
			statusType = "READY";
		} else {
			statusType = "ALL";
		}
		this.getView().byId("idTaskTypeBtn").setSelectedKey(statusType);
		oUserDefaultModel.getData().userTaskStatus = statusType;
		oUserDefaultModel.refresh(true);
		/*var filterBar = this.getView().byId("idSearchFilterBar");
		filterBar.search();*/
		this.fnUserData();
	},
	/**********************End - On Search of Task type*****************************/
	
	/**********************Start - Load User data*****************************/
	fnUserData: function(oEvent){
		this.busy.open();
		var that = this;
		var oUserDefaultData = oUserDefaultModel.getData();
		oUserDefaultData.userListVisible = true;
		oUserDefaultData.highWorkLoadCount = 0;
		oUserDefaultData.normalWorkLoadCount = 0;
		oUserDefaultData.lowWorkLoadCount = 0;
		oUserDefaultData.totalWorkLoadCount = 0;
		oUserDefaultModel.refresh(true);
		var oUserListModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(oUserListModel, "oUserListModel");
		var dynamicValue = "";
		var processName = this.getView().byId("idProcessNameSelection").getSelectedKey();
		var groupName = this.getView().byId("idGroupNameSelection").getSelectedKey();
		var requestId = this.getView().byId("idRequestId").getValue();
		var taskType = this.getView().byId("idTaskTypeBtn").getSelectedKey();
		var noDataControl = this.getView().byId("idNoDataText");
		var i18n = that.getView().getModel("i18n").getResourceBundle();
		if (this.getView().byId("idDynamicField").getVisible()){
			dynamicValue = this.getView().byId("idDynamicField").getValue();
		}
		
		var oHeader = {
			"Content-Type" : "application/json;charset=utf-8"
		};
		var aData = {
				"processName" : processName,
				"groupName" : groupName,
				"labelName" : dynamicValue,
				"requestId" : requestId,
				"taskStatus" : taskType
		};
		oUserDefaultModel.searchData = aData;
		
		if (this.getView().byId("idReportSwitch").getSelectedKey() == "AGEING"){
			this.onLoadTableData(aData);
			noDataControl.setVisible(false);
		} else {
			oUserListModel.loadData("/appone/pmc/userload/heatmap",JSON.stringify(aData), true, "POST",false, false, oHeader);
			oUserListModel.attachRequestCompleted(function(oEvent) {
				that.busy.close();
				var userListModel = that.getView().getModel("oUserListModel");
				if (userListModel.getData().userWorkloadDtos){
					noDataControl.setVisible(false);
					if (!(userListModel.oData.userWorkloadDtos instanceof Array)) {
						userListModel.oData.userWorkloadDtos = [userListModel.oData.userWorkloadDtos];
					}
					userListModel.refresh();
					oUserDefaultModel.refresh(true);
					that.onSortingUser();
				}else{
					oUserDefaultModel.refresh(true);
					if (taskType!="ALL"){
						noDataControl.setVisible(true).setText("There are no "+taskType+" task(s) for the selected process");
					} else {
						noDataControl.setVisible(true).setText(i18n.getText("NO_TASKS"));
					}
					toastMessage(i18n.getText("NO_RESULTS_FOUND_SEARCH"));
				}
			});
			oUserListModel.attachRequestFailed(function(oEvent) {
				that.busy.close();
				oUserDefaultModel.refresh(true);
				if (taskType!="ALL"){
					noDataControl.setVisible(true).setText("There are no "+taskType+" task(s) for the selected process");
				} else {
					noDataControl.setVisible(true).setText(i18n.getText("NO_TASKS"));
				}
				toastMessage(i18n.getText("NO_RESULTS_FOUND_SEARCH"));
			});
		}
	},
	/**********************End - Load User data*****************************/
	
	/**********************Start - Sorting of User data*****************************/
	onSortingUser: function(){
		var oView = this.getView();
		var oUserDefaultData = oUserDefaultModel.getData();
		var highWorkLoadCount = oUserDefaultData.highWorkLoadCount;
		var normalWorkLoadCount = oUserDefaultData.normalWorkLoadCount;
		var lowWorkLoadCount = oUserDefaultData.lowWorkLoadCount;
		var totalWorkLoadCount = oUserDefaultData.totalWorkLoadCount;
		var sortingType = oView.byId("sortingTask").getSelectedKey();;
		var oGrid = oView.byId("userListGrid");
		var oBinding = oGrid.getBinding("content");
		var aSorters = [];
		var sPath = "";
		var bDescending = "";
		if (sortingType == "Ascending tasks"){
			sPath = "taskCount";
			bDescending = false;
		} else if (sortingType == "Descending tasks"){
			sPath = "taskCount";
			bDescending = true;
		} else if (sortingType == "Alphabetical"){
			sPath = "userName";
			bDescending = false;
		} else {
			this.fnUserData();
		}
		aSorters.push(new sap.ui.model.Sorter(sPath, bDescending));
		oBinding.sort(aSorters);
		oUserDefaultData.highWorkLoadCount = highWorkLoadCount;
		oUserDefaultData.normalWorkLoadCount = normalWorkLoadCount;
		oUserDefaultData.lowWorkLoadCount = lowWorkLoadCount;
		oUserDefaultData.totalWorkLoadCount = totalWorkLoadCount;
		oUserDefaultModel.refresh(true);
	},
	/**********************End - Sorting of User data*****************************/
	
	/**********************Start - Method on Process name selection*****************************/
	onProcessNameSelection: function(oEvent){
		var oView=this.getView();
		var oProcessNameModel = oView.getModel("oProcessNameModel");
		var oUserDefaultModel = oView.getModel("oUserDefaultModel");
		var selectedProcess = oEvent.getSource().getSelectedItem().getBindingContext("oProcessNameModel").getObject();
		var userList = selectedProcess.userList;
		var selectedKey = oEvent.getSource().getSelectedKey();
		var label = selectedProcess.labelName;
		if (label) {
			oUserDefaultModel.getData().enable = true;
			oView.byId("idDynamicLabel").setText(label);
			oView.byId("idDynamicField").setValue("");
		} else {
			oUserDefaultModel.getData().enable = false;
		}
		oView.byId("idRequestId").setValue("");
		oProcessNameModel.refresh(true);
		oUserDefaultModel.refresh(true);
		this.getGroupNames(userList);
	},
	/**********************End - Method on Process name selection*****************************/
	
	/**********************Start - Setting today's date*****************************/
	setDate : function() {
		var oUserDefaultModel = this.getView().getModel("oUserDefaultModel");
		var oUserDefaultData = oUserDefaultModel.getData();
		var today = new Date();
		var dateFormat = sap.ui.core.format.DateFormat.getDateInstance({
			pattern : "dd/MM/yyyy"
		});
		var DisplayDate = dateFormat.format(today);
		var dateValueFormat = sap.ui.core.format.DateFormat.getDateInstance({
			pattern : "yyyy-MM-dd"
		});
		var ValueDate = dateValueFormat.format(today);
		oUserDefaultData.DisplayDate = DisplayDate;
		oUserDefaultData.date = ValueDate;
		oUserDefaultModel.refresh(true);
	},
	/**********************End - Setting today's date*****************************/
	
/**
* Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
* (NOT before the first rendering! onInit() is used for that one!).
* @memberOf pmc_ui.userWorkList
*/
//	onBeforeRendering: function() {
//
//	},

/**
* Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
* This hook is the same one that SAPUI5 controls get after being rendered.
* @memberOf pmc_ui.userWorkList
*/
	onAfterRendering: function() {
		this.busy.close();
		this.onEnter();
		//this.filterBarCSS();
		this.getView().byId("eachUserDetail").attachBrowserEvent("click", 
				function(oEvent) {
					var workLoad = this.aCustomStyleClasses[0].split("Work")[0].toUpperCase() + " WORKLOAD";
					oUserDefaultModel.getData().userWorkLoadType = workLoad;
					var userId = this.getBindingContext('oUserListModel').getObject().userId;
					oUserDefaultModel.getData().userTaskCount = this.getBindingContext('oUserListModel').getObject().noOfTask;
					oUserDefaultModel.refresh(true);
					var routerObject = this.getParent().getParent().getParent().getParent().getController().oRouter;
					routerObject.navTo("userWorkLoad",{
						ContextPath : userId
					});
				}
		);
		
		/*var filterBar = this.getView().byId("idSearchFilterBar");
		filterBar._toggleHideShow = function() {
			this.setFilterBarExpanded(!this.getFilterBarExpanded());
			var bVisible = this.getFilterBarExpanded();
			if (bVisible) {
				filterBar._oSearchButton.setVisible(true);
			} else {
				filterBar._oSearchButton.setVisible(false);
			}
		}*/
	},
	
	/**********************Start - On Enter method*****************************/
	onEnter:function(){
		var that = this;
		var dynamicField = that.getView().byId("idDynamicField");
		var requestID = that.getView().byId("idRequestId");
		dynamicField.onsapenter=(function(oEvent) {
			if (navigator.onLine){
				sap.m.InputBase.prototype.onsapenter.apply(dynamicField,arguments);
				that.fnUserData();
			} else {
				setTimeout(function(){
					dynamicField.focus();
				}, 1200);
			}
		}).bind(that);	
		
		requestID.onsapenter=(function(oEvent) {
			if (navigator.onLine){
				sap.m.InputBase.prototype.onsapenter.apply(requestID,arguments);
				that.fnUserData();
			} else {
				setTimeout(function(){
					requestID.focus();
				}, 1200);
			}
		}).bind(that);
	},
	/**********************End - On Enter method*****************************/
	
	filterBarCSS:function(){
		var filterBar = this.getView().byId("idSearchFilterBar");
		filterBar._oFiltersButton.setVisible(false);
		filterBar._oHideShowButton.setType("Default").addStyleClass("sapUiSizeCompact");
		filterBar._oSearchButton.setText("Search").addStyleClass("sapUiSizeCompact").setWidth("5rem");
		filterBar._oClearButtonOnFB.setVisible(false);
	},
	
	onBackToLaunchPage: function(){
		oUserDefaultModel.getData().userNavigateScreen = "";
		oUserDefaultModel.refresh(true);
		this.oRouter.navTo("launchPage");
	},
	
	onSwitchReport: function(oEvent){
		var oView = this.getView();
		var selectedReport = oEvent.getSource().getSelectedKey();
		oView.byId("idReportSwitch").setSelectedKey(selectedReport);
		if (selectedReport == "LIST"){
			oView.byId("userListGrid").setVisible(true);
			oView.byId("PMC_TASK_AGING_TABLE").setVisible(false);
			oView.byId("idWorkLoadRange").setVisible(true);
			oView.byId("sortingTask").setVisible(true);
			oView.byId("taskDownload").setVisible(false);
			
			
		} else if (selectedReport == "AGEING"){
			oView.byId("userListGrid").setVisible(false);
			oView.byId("PMC_TASK_AGING_TABLE").setVisible(true);
			oView.byId("idWorkLoadRange").setVisible(false);
			oView.byId("sortingTask").setVisible(false);
			oView.byId("taskDownload").setVisible(true);
		}
		this.fnUserData();
	},

	/**********************Start - Dynamic table creation*****************************/
	
	onLoadTableData: function(searchParams){
		var that = this;
		var oView = that.getView();
		var url = "/appone/pmc/task/ageing";
		var oData={};
		if(searchParams.processName != ""){
			oData.processName=searchParams.processName
		}
		if(searchParams.groupName != ""){
			oData.userGroup=searchParams.groupName
		}
		if(searchParams.taskStatus != ""){
			oData.status=searchParams.taskStatus
		}
		if(searchParams.requestId != ""){
			oData.requestId=searchParams.requestId
		}
		if(searchParams.labelName != ""){
			oData.labelValue=searchParams.labelName
		}
		this.busy.open();
		var oTaskAgingLoadModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oTaskAgingLoadModel,"oTaskAgingLoadModel");
		var oTaskAgingTableModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oTaskAgingTableModel,"oTaskAgingTableModel");
		oTaskAgingLoadModel.loadData(url,oData,true);
		oTaskAgingLoadModel.attachRequestCompleted(function(oEvent) {
			that.busy.close();
			var taskAgingLoadModel = oView.getModel("oTaskAgingLoadModel");
			var taskAgingTableModel = oView.getModel("oTaskAgingTableModel");
			var taskAgingLoadData = taskAgingLoadModel.getData();
			if(taskAgingLoadData && taskAgingLoadData.responseMessage.statusCode != "1"){
				taskAgingTableModel.setData(taskAgingLoadData.ageingTable);
				var taskAgingTableData = taskAgingTableModel.getData();
				if (!(taskAgingTableData.headerMap.entry instanceof Array)) {
					taskAgingTableData.headerMap.entry = [taskAgingTableData.headerMap.entry];
				}
				if (!(taskAgingTableData.tupleDtos instanceof Array)) {
					taskAgingTableData.tupleDtos = [taskAgingTableData.tupleDtos];
				}
				taskAgingTableModel.refresh();
				that.createTableJson();
			} else {
				taskAgingTableModel.setData({});
				toastMessage(taskAgingLoadData.responseMessage.message);
			}
		});
		oTaskAgingLoadModel.attachRequestFailed(function(oEvent) {
			that.busy.close();
			oView.getModel("oTaskAgingTableModel").setData({});
			toastMessage(oView.getModel("i18n").getResourceBundle().getText("NO_RESULTS_FOUND_SEARCH"));
		});
	},
	
	createTableJson: function(){
		var oTaskAgingTableModel = this.getView().getModel("oTaskAgingTableModel");
		var tableData = oTaskAgingTableModel.getData().tupleDtos;
		var tableDto = [];
		for(var i=0; i<tableData.length; i++){
			var obj = {};
			obj.userName = tableData[i]["userName"];
			var range = tableData[i].dataMap.entry;
			if (!(range instanceof Array)) {
				range = [range];
			}
			for(var j=0; j<range.length; j++){
				obj[range[j].key] = range[j].value;
			}
			obj.count = tableData[i]["count"];
			obj.name = tableData[i]["name"];
			tableDto.push(obj);
		}
		if (!(tableDto instanceof Array)) {
			tableDto = [tableDto];
		}
		var oTaskAgingTableData = oTaskAgingTableModel.getData();
		oTaskAgingTableData.tupleDtos = tableDto;
		oTaskAgingTableData.headerDtos = oTaskAgingTableData.headerMap.entry;
		oTaskAgingTableData.headerDtos.push({"key":"name", "value":0});
		oTaskAgingTableModel.refresh(true);
		this.createTable();
	},
	
	createTable: function(){
		var that = this;
		var oTaskAgingTable = this.getView().byId("PMC_TASK_AGING_TABLE");
		var oTaskAgingTableModel = this.getView().getModel("oTaskAgingTableModel");
		oTaskAgingTable.bindAggregation("columns",
				"oTaskAgingTableModel>/headerDtos", function(index, context) {
					return new sap.m.Column({
							header : new sap.m.Text({
								text : pmc.util.formatter.fnDisplayDayFormat(context.getObject().key),
								wrapping : true
						}).addStyleClass("userWorkLoadTableHeaderLblStyle"),
						hAlign: pmc.util.formatter.fnGetTaskAgeingAlignment(context.getObject().key),
						visible: pmc.util.formatter.getVisibleColumn(context.getObject().key),
						width: pmc.util.formatter.fnGetTaskAgeingWidth(context.getObject().key)
					});
				});
		oTaskAgingTable.bindItems("oTaskAgingTableModel>/tupleDtos", function(index, context) {
	        var obj = context.getObject();
	        var row = new sap.m.ColumnListItem();
	        for(var k in obj) {
	        	row.addCell(new sap.m.Link({
	        		enabled: that.onEnableLink(obj[k]),
	        		text: obj[k],
	        		tooltip: obj[k],
	        		customData: new sap.ui.core.CustomData({
	        			"key":k,
	        			"value":k,
	        		}),
	        		press:function(oEvent){
	        			that.onProcessTypeClick(oEvent);
	        		}
	        	}).addStyleClass("taskAgeingLink"));
	        }
	        return row;
	    });
		oTaskAgingTableModel.refresh(true);
	},
	
	onEnableLink: function(oValue){
		if(oValue == 0){
			return false;
		} else {
			return true;
		}
	},
	
	/**********************End - Dynamic table creation*****************************/
	
	onProcessTypeClick: function(oEvent){
		this.busy.open();
		this.processCount = oEvent.getSource().getText();
		if(isNaN(this.processCount)){
			this.processCount = oEvent.getSource().getBindingContext("oTaskAgingTableModel").getObject().count;
		}
		var oUserDefaultData = oUserDefaultModel.getData();
		oUserDefaultData.selectedKey = oEvent.getSource().getCustomData()[0].getValue();
		oUserDefaultData.userName = oEvent.getSource().getBindingContext("oTaskAgingTableModel").getObject().name;
		this.onLoadingProcessList();
	},
	
	onLoadingProcessList: function(){
		var fromDay = "";
		var toDay = "";
		var selectedKey = oUserDefaultModel.getData().selectedKey;
		var userName = oUserDefaultModel.getData().userName;
		var oTaskAgeingModel = this.getView().getModel("oTaskAgeingModel");
		var taskType = this.getView().byId("idTaskTypeBtn").getSelectedKey();
		var processName = this.getView().byId("idProcessNameSelection").getSelectedKey();
		oTaskAgeingModel.setData({});
		oTaskAgeingModel.refresh(true);
		var today = new Date();
		var date = today.getDate();
		if (selectedKey == "userName"){
			toDay = -1;
			fromDay = -1;
			oUserDefaultModel.getData().taskListTitle = "All Open Tasks";
		} else {
			toDay = parseInt(selectedKey.split('-')[0].trim(), 10);
			fromDay = parseInt(selectedKey.split('-')[1].trim(), 10);
			oUserDefaultModel.getData().taskListTitle = "Open Tasks ("+toDay+"-"+fromDay+")";
		}
		oUserDefaultModel.refresh();
		var oHeader = {
			"Content-Type" : "application/json;charset=utf-8"
		};
		//"page" : this.pageNumber,
		var aData = {
				"owner" : userName,
				"startDayFrom" : fromDay,
				"startDayTo" : toDay,
				"processName":processName,
				"taskStatus": taskType,
				"requestId" : oUserDefaultModel.searchData.requestId,
				"labelValue" : oUserDefaultModel.searchData.labelName
		};
		oTaskAgeingModel.loadData("/appone/pmc/task/manager",JSON.stringify(aData), true, "POST",false, false, oHeader);
	},
	
	taskListDownload:function(oEvent){
		//this._oDownloadDialog.close();
		var that = this;
		this.busy.open();
		var fromDay = "";
		var toDay = "";
		var selectedKey = oUserDefaultModel.getData().selectedKey;
		var userName = oUserDefaultModel.getData().userName;
		var taskType = this.getView().byId("idTaskTypeBtn").getSelectedKey();
		var processName = this.getView().byId("idProcessNameSelection").getSelectedKey();
		var today = new Date();
		var date = today.getDate();
		if (selectedKey == "userName"){
			toDay = -1;
			fromDay = -1;
		} else {
			toDay = parseInt(selectedKey.split('-')[0].trim(), 10);
			fromDay = parseInt(selectedKey.split('-')[1].trim(), 10);
		}
		var oDownloadLoadModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(oDownloadLoadModel, "oDownloadLoadModel");
		var oHeader = {
				"Content-Type" : "application/json;charset=utf-8"
		};
		var aData = {
				"reportName":"Task Manager",
				"fileFormate":"Excel",
				"userId" : userName,
				"startDayFrom" : fromDay,
				"startDayTo" : toDay,
				"processName":processName,
				"status": taskType,
				"requestId" : oUserDefaultModel.searchData.requestId,
				"labelValue" : oUserDefaultModel.searchData.labelName
		};
		oDownloadLoadModel.loadData("/appone/pmc/report/download",JSON.stringify(aData), true, "POST",false, false, oHeader);
		oDownloadLoadModel.attachRequestCompleted(function(oEvent) {
			that.busy.close();
			if (that.getView().getModel("oDownloadLoadModel").getData()){
				var Base64 = oDownloadLoadModel.getData()["base64"];
				var filename = oDownloadLoadModel.getData()["filename"];
				if(!jQuery.isEmptyObject(oDownloadLoadModel.getData())){
					var u8_2 = new Uint8Array(atob(Base64).split("").map(function(c) {
						return c.charCodeAt(0);
					}));
					var a = document.createElement("a");
					setTimeout(function(){
						document.body.appendChild(a);
						a.style = "display: none";
						var blob = new Blob( [ u8_2 ], {type: 'application/vnd.ms-excel'});
						try{
							var url = window.URL.createObjectURL(blob);
							a.href = url;
							a.download = filename+".xlsx";
							a.click();
							window.URL.revokeObjectURL(url);
						}
						catch(e){
							console.log(e);
						}
					}, 100); 
				}
				else{
				}
			}else{
			}
		});
		oDownloadLoadModel.attachRequestFailed(function(oEvent) {
			that.busy.close();
			toastMessage(that.getView().getModel("i18n").getResourceBundle().getText("DOWNLOAD_FAILURE"));
		});
	},
	
	/**********************Start - Creation of Process List Dialog*****************************/
	onDialogCreation: function(){
		//oUserDefaultModel.getData().taskListTitle = "";
		if (!this._oTaskDetailsDialog) {
			var that = this;
			var _TaskListFragment = sap.ui.xmlfragment('idTaskAgingReport','pmc.fragments.taskList', that);
			this._oTaskDetailsDialog = new Dialog({
				title: "{oUserDefaultModel>/taskListTitle}",
				"class":"dialogStyleClass",
				contentWidth: "80%",
				resizable: true,
				beginButton: new Button({
					text: 'Close',
					type: 'Accept',
					press: function (oEvent) {
						that._oTaskDetailsDialog.close();
						that.pageNumber = 1;
					}
				}).addStyleClass("fragmentCloseBtn sapUiSizeCompact")
			}).addStyleClass("dialogClass");
			this._oTaskDetailsDialog.addContent(_TaskListFragment);
			this.getView().addDependent(this._oTaskDetailsDialog);
		}
		this._oTaskDetailsDialog.open();
		var totalCount = this.processCount;
		var perPageView = 20;
		sap.ui.getCore().byId("idTaskAgingReport--idPrevButton").setEnabled(false);
		sap.ui.getCore().byId("idTaskAgingReport--idNextButton").setEnabled(true);
		var pageCount = parseInt(totalCount / perPageView);
		if(totalCount % perPageView !== 0){
			pageCount = pageCount + 1;
		}
		this.noOfPages = pageCount;
		var array = [];
		if(pageCount > 5){
			pageCount = 5;
		} else {
			sap.ui.getCore().byId("idTaskAgingReport--idNextButton").setEnabled(false);
		}
		for(var i = 1; i <= pageCount; i++){
			var object = {"text" : i};
			array.push(object);
		}
		this.getView().getModel("paginatedModel").setProperty('/array', array);
		sap.ui.getCore().byId("idTaskAgingReport--idCurrentPage").setText("Page : " + this.pageNumber);
		this.onFilterTableData();
	},
	/**********************End - Creation of Process List Dialog*****************************/
	
	/**********************Start - Table data filtering*****************************/
	onFilterTableData: function(){
		var searchData = this.getView().getModel("oTaskAgeingModel").getData();			
		var filters = [];			
		if (searchData.requestId){			
			var oFilter1 = new sap.ui.model.Filter("requestId","Contains",searchData.requestId);			
			filters.push(oFilter1);			
		}			
		if (searchData.processDisplayName){			
			var oFilter2 = new sap.ui.model.Filter("processDisplayName","Contains",searchData.processDisplayName);			
			filters.push(oFilter2);			
		}			
		if (searchData.subject){			
			var oFilter3 = new sap.ui.model.Filter("taskSubject","Contains",searchData.subject);			
			filters.push(oFilter3);			
		}			
		if (searchData.createdAt){	
			var oFilter4 = new sap.ui.model.Filter("date","Contains",searchData.createdAt);			
			filters.push(oFilter4);			
		}	
		if (searchData.owner){			
			var oFilter5 = new sap.ui.model.Filter("owners","Contains",searchData.owner);			
			filters.push(oFilter5);			
		}
		if (searchData.newStatus){			
			var oFilter6 = new sap.ui.model.Filter("newStatus","Contains",searchData.newStatus);			
			filters.push(oFilter6);			
		}			
		var tableId = sap.ui.getCore().byId("idTaskAgingReport--taskListTable");			
		tableId.getBinding("items").filter(filters,sap.ui.model.FilterType.Application);
	},
	/**********************End - Table data filtering*****************************/
	
	onSort: function(){
		var sortingType = oUserDefaultModel.getData().sort;
		var oTable = sap.ui.getCore().byId("idTaskAgingReport--taskListTable");
		var oBinding = oTable.getBinding("items");
		var aSorters = [];
		var sPath = "createdDate";
		var bDescending = "";
		if (sortingType){
			oUserDefaultModel.getData().sort = false;
			bDescending = false;
		} else {
			oUserDefaultModel.getData().sort = true;
			bDescending = true;
		}
		oUserDefaultModel.refresh(true);
		aSorters.push(new sap.ui.model.Sorter(sPath, bDescending));
		oBinding.sort(aSorters);
	},
	
	/**********************Start - Navigating to Process Flow screen*****************************/
	onNavigateToTaskScreen: function(oEvent){
		var processId = oEvent.getSource().getBindingContext("oTaskAgeingModel").getObject().requestId;
		oUserDefaultModel.getData().processId =  oEvent.getSource().getBindingContext("oTaskAgeingModel").getObject().eventId;
		this.oRouter.navTo("processFlow",{
			processId : processId
		});
	},
	/**********************End - Navigating to Process Flow screen*****************************/
	
	onScrollLeft: function(){
		var oCore = sap.ui.getCore();
		oCore.byId("idTaskAgingReport--idPrevButton").setEnabled(true);
		oCore.byId("idTaskAgingReport--idNextButton").setEnabled(true);
		var paginatedData = this.getView().getModel("paginatedModel").getData().array;
		var startarray = paginatedData[0].text - 1;
		var tab = 0;
		var array = [];
		if(startarray === 1){
			oCore.byId("idTaskAgingReport--idPrevButton").setEnabled(false);
		} 
		tab = startarray+4;
		for(var i=startarray; i<=tab; i++){
			var object = {"text":i};
			array.push(object);
		}
		var paginatedModel = new sap.ui.model.json.JSONModel();
		paginatedModel.setProperty('/array', array);
		this.getView().setModel(paginatedModel, "paginatedModel");
	},
	
	onScrollRight: function(){
		var oCore = sap.ui.getCore();
		oCore.byId("idTaskAgingReport--idPrevButton").setEnabled(true);
		oCore.byId("idTaskAgingReport--idNextButton").setEnabled(true);
		var paginatedData = this.getView().getModel("paginatedModel").getData().array;
		var startarray = paginatedData[0].text + 1;
		var tab = 0;
		var array = [];
		if((startarray+5) <= this.noOfPages){
			tab = startarray+4;
		} else {
			tab = this.noOfPages;
			oCore.byId("idTaskAgingReport--idNextButton").setEnabled(false);
		}
		for(var i=startarray; i<=tab; i++){
			var object = {"text":i};
			array.push(object);
		}
		var paginatedModel = new sap.ui.model.json.JSONModel();
		paginatedModel.setProperty('/array', array);
		this.getView().setModel(paginatedModel, "paginatedModel");
	},
	
	onPageClick: function(oEvent){
		var selectedPage = oEvent.getSource().getText();
		this.pageNumber = selectedPage;
		this.onLoadingProcessList();
	},
	
/*	onTaskDownload: function(oEvent){
		if (!this._oTaskDownloadDialog) {
			this._oTaskDownloadDialog = sap.ui.xmlfragment('idDownload','pmc.fragments.downloadAs', this);
			this.getView().addDependent(this._oTaskDownloadDialog);
		}
		this._oTaskDownloadDialog.open();
	},*/
	
	onTaskDownload:function(oEvent){
		//this._oTaskDownloadDialog.close();
		var that = this;
		this.busy.open();
		var oDownloadLoadModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(oDownloadLoadModel, "oDownloadLoadModel");
		var oHeader = {
				"Content-Type" : "application/json;charset=utf-8"
		};
		var aData = {
				"reportName":"Task Aeging",
				"fileFormate":"Excel",
				"userId" : oUserDefaultModel.getData().userId,
				"status" : oUserDefaultModel.searchData.taskStatus,
				"processName" : oUserDefaultModel.searchData.processName,
				"requestId" : oUserDefaultModel.searchData.requestId,
				"labelValue" : oUserDefaultModel.searchData.labelName,
				"userGroup" :  this.getView().byId("idGroupNameSelection").getSelectedKey()
		};
		oDownloadLoadModel.loadData("/appone/pmc/report/download",JSON.stringify(aData), true, "POST",false, false, oHeader);
		oDownloadLoadModel.attachRequestCompleted(function(oEvent) {
			that.busy.close();
			if (that.getView().getModel("oDownloadLoadModel").getData()){
				var Base64 = oDownloadLoadModel.getData()["base64"];
				var filename = oDownloadLoadModel.getData()["filename"];
				if(!jQuery.isEmptyObject(oDownloadLoadModel.getData())){
					var u8_2 = new Uint8Array(atob(Base64).split("").map(function(c) {
						return c.charCodeAt(0);
					}));
					var a = document.createElement("a");
					setTimeout(function(){
					document.body.appendChild(a);
					a.style = "display: none";
					var blob = new Blob( [ u8_2 ], { type: 'application/vnd.ms-excel'});
					try{
					var url = window.URL.createObjectURL(blob);
					a.href = url;
					a.download = filename+".xlsx";
					a.click();
					window.URL.revokeObjectURL(url);
					}
					catch(e){
						console.log(e);
					}
					}, 100);  
				}
				else{
				}
			}else{
			}
		});
		oDownloadLoadModel.attachRequestFailed(function(oEvent) {
			that.busy.close();
			toastMessage(that.getView().getModel("i18n").getResourceBundle().getText("DOWNLOAD_FAILURE"));
		});
	}

/**
* Called when the Controller is destroyed. Use this one to free resources and finalize activities.
* @memberOf pmc_ui.userWorkList
*/
//	onExit: function() {
//
//	}
	});
});