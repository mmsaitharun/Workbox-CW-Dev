sap.ui.define([
	'jquery.sap.global',
	'sap/ui/core/Fragment',
	'sap/ui/core/mvc/Controller',
	'sap/ui/model/json/JSONModel',
	'sap/m/Button',
	'sap/m/Dialog'
], function (jQuery, Fragment, Controller, JSONModel, Button,Dialog) {
	"use strict";
sap.ui.controller("pmc_ui.processAging", {

/**
* Called when a controller is instantiated and its View controls (if available) are already created.
* Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
* @memberOf pmc_ui.processAging
*/
	onInit: function() {
		this.oRouter = sap.ui.core.UIComponent.getRouterFor(this);
		var that = this;
		this.noOfPages = 0;
		this.pageNumber = 1;
		this.processCount = 0;
		this.busy = new sap.m.BusyDialog();
		this.getProcessNames();
		this.getView().setModel(oUserDefaultModel,"oUserDefaultModel");
		this.oRouter.attachRoutePatternMatched(function(oEvent) {
			var oUserDefaultData=oUserDefaultModel.getData();
			if (oEvent.getParameter("name") === "processAging") {
				oUserDefaultData.userNavigateScreen = "processAging";
				oUserDefaultData.reportHeader = "PROCESS TRACKER";
				oUserDefaultModel.refresh(true);
				if (oUserDefaultData.selectedKey && oUserDefaultData.processName){
					that.busy.open();
					that.onLoadingProcessList();
					that.updateGraphProperty();
				}
				else{
					that.getView().byId("idReportTab").setSelectedKey("week");
					//that.getView().byId("idSelectedProcessName").setSelectedKey("ALL");
					that.updateGraphProperty();
					that.onLoadTableData();
				}
			}
		});
		var oUserWorkLoadModel = new sap.ui.model.json.JSONModel();
		var paginatedModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(oUserWorkLoadModel, "oUserWorkLoadModel");
		this.getView().setModel(paginatedModel, "paginatedModel");
		oUserWorkLoadModel.setSizeLimit(500);
		oUserWorkLoadModel.attachRequestCompleted(function(oEvent) {
			that.busy.close();
			var userWorkLoadModel=that.getView().getModel("oUserWorkLoadModel");
			var userWorkLoadData=userWorkLoadModel.getData();
			if (userWorkLoadData && userWorkLoadData.responseMessage.statusCode==="0"){
				if (!(userWorkLoadData.processEventsList instanceof Array)) {
					userWorkLoadData.processEventsList = [userWorkLoadData.processEventsList];
				}
				userWorkLoadModel.refresh(true);
				that.onDialogCreation(userWorkLoadData.processEventsList[0].processDisplayName);
			} else {
				toastMessage(userWorkLoadData.responseMessage.message);
			}
		});
		oUserWorkLoadModel.attachRequestFailed(function(oEvent) {
			this.refresh(true);
			that.busy.close();
			toastMessage(that.getView().getModel("oUserWorkLoadModel").getData().responseMessage.message);
		});
	},
	
	/**********************Start - Loading Process Names*****************************/
	getProcessNames: function(){
		var that = this;
		var oProcessNameModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(oProcessNameModel, "oProcessNameModel");
		oProcessNameModel.loadData("/appone/pmc/config/labels",null,false);
		oProcessNameModel.attachRequestCompleted(function(oEvent) {
			var processNameModel=that.getView().getModel("oProcessNameModel");
			var processNameData = processNameModel.getData();
			if (processNameData && processNameData.processConfigDtos){
				if (!(processNameData.processConfigDtos instanceof Array)) {
					processNameData.processConfigDtos = [processNameData.processConfigDtos];
				}
				processNameModel.refresh();
			} else {
				toastMessage(processNameData.responseMessage.message);
			}
		});
	},
	/**********************End - Loading Process Names*****************************/
	
	/**********************Start - Dynamic table creation*****************************/
	onLoadTableData: function(){
		var that = this;
		var oView=that.getView();
		this.busy.open();
		//var range = oView.byId("idRangeBtn").getSelectedKey();
		var range = oView.byId("idReportTab").getSelectedKey();
		var processName = this.getView().byId("idSelectedProcessName").getSelectedKey();
		var oProcessAgingLoadModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oProcessAgingLoadModel,"oProcessAgingLoadModel");
		var oProcessAgingTableModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oProcessAgingTableModel,"oProcessAgingTableModel");
		var oProcessAgingGraphModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oProcessAgingGraphModel,"oProcessAgingGraphModel");
		var url = "/appone/pmc/process/ageing?";
		if(range && range!=null){
			url = url +"type="+range;
		}
		if(processName && processName!=null && processName!="ALL"){
			if(url.includes("type")){
				url = url +"&";
			}
				url = url + "process="+processName;
		}
		oProcessAgingLoadModel.loadData(url,null,true);
		oProcessAgingLoadModel.attachRequestCompleted(function(oEvent) {
			that.busy.close();
			var processAgingLoadModel=that.getView().getModel("oProcessAgingLoadModel");
			var processAgingTableModel=that.getView().getModel("oProcessAgingTableModel");
			var processAgingGraphModel=that.getView().getModel("oProcessAgingGraphModel");
			var processAgingLoadData = processAgingLoadModel.getData();
			if(processAgingLoadData && processAgingLoadData.responseMessage.statusCode != "1"){
				oView.byId("PMC_PROCESS_AGING_GRAPH").setVisible(true);
				processAgingTableModel.setData(processAgingLoadData.ageingTable);
				processAgingGraphModel.setProperty("/ageingGraph",processAgingLoadData.ageingGraph);
				var processAgingTableData = processAgingTableModel.getData();
				var processAgingGraphData = processAgingGraphModel.getData();
				if (!(processAgingTableData.headerMap.entry instanceof Array)) {
					processAgingTableData.headerMap.entry = [processAgingTableData.headerMap.entry];
				}
				if (!(processAgingTableData.tupleDtos instanceof Array)) {
					processAgingTableData.tupleDtos = [processAgingTableData.tupleDtos];
				}
				if (!(processAgingGraphData.ageingGraph instanceof Array)) {
					processAgingGraphData.ageingGraph = [processAgingGraphData.ageingGraph];
				}
				processAgingTableModel.refresh();
				processAgingGraphModel.refresh();
				that.createTableJson();
				that.createGraph(range);
			} else {
				processAgingTableModel.setData({});
				processAgingGraphModel.setData({});
				oView.byId("PMC_PROCESS_AGING_GRAPH").setVisible(false);
				toastMessage(processAgingLoadModel.getData().responseMessage.message);
			}
		});
		oProcessAgingLoadModel.attachRequestFailed(function(oEvent) {
			that.busy.close();
			oView.getModel("oProcessAgingTableModel").setData({});
			oView.getModel("oProcessAgingGraphModel").setData({});
			oView.byId("PMC_PROCESS_AGING_GRAPH").setVisible(false);
			toastMessage(oView.getModel("i18n").getResourceBundle().getText("NO_RESULTS_FOUND_SEARCH"));
		});
	},
	
	createTableJson: function(){
		var oProcessAgingTableModel = this.getView().getModel("oProcessAgingTableModel");
		var oProcessAgingTableData = oProcessAgingTableModel.getData();
		var tableData = oProcessAgingTableData.tupleDtos;
		var tableDto = [];
		for(var i=0; i<tableData.length; i++){
			var obj = {};
			obj.processName = tableData[i]["name"];
			obj.processDisplayName = tableData[i]["processDisplayName"];
			var range = tableData[i].dataMap.entry;
			if (!(range instanceof Array)) {
				range = [range];
			}
			for(var j=0; j<range.length; j++){
				obj[range[j].key] = range[j].value;
			}
			obj.count = tableData[i]["count"];
			tableDto.push(obj);
		}
		var totalValue = oProcessAgingTableData.tupleDtos[tableDto.length - 1].dataMap.entry;
		if (!(totalValue instanceof Array)) {
			totalValue = [totalValue];
		}
		totalValue.push({"value":tableDto[tableDto.length-1].count,"key":"count"});
		totalValue.unshift({"value":"Total","key":"count"});
		tableDto.pop();
		if (!(tableDto instanceof Array)) {
			tableDto = [tableDto];
		}
		oProcessAgingTableData.tupleDtos = tableDto;
		oProcessAgingTableData.totalDtos = totalValue;
		oProcessAgingTableData.headerDtos = oProcessAgingTableData.headerMap.entry;
		oProcessAgingTableModel.refresh(true);
		this.createTable();
	},
	
	createTable: function(){
		var that = this;
		var oView = this.getView();
		var oProcessAgingTable = oView.byId("PMC_PROCESS_AGING_TABLE");
		var oProcessAgingTotalTable = oView.byId("PMC_PROCESS_AGING_TOTAL_TABLE");
		var oProcessAgingTableModel = oView.getModel("oProcessAgingTableModel");
		oProcessAgingTable.bindAggregation("columns",
				"oProcessAgingTableModel>/headerDtos", function(index, context) {
					return new sap.m.Column({
							header : new sap.m.Text({
								text : pmc.util.formatter.fnDisplayDayFormat(context.getObject().key),
								wrapping : true
						}).addStyleClass("userWorkLoadTableHeaderLblStyle"),
						hAlign: pmc.util.formatter.fnGetTheAlignment(context.getObject().key),
						width: pmc.util.formatter.fnGetTheWidth(context.getObject().key)
					});
				});
		oProcessAgingTable.bindItems("oProcessAgingTableModel>/tupleDtos", function(index, context) {
	        var obj = context.getObject();
	        var row = new sap.m.ColumnListItem();
	        for(var k in obj) {
	        	var customData = "";
	        	if (k == "processDisplayName") {
	        		customData = "processName";
	        	} else {
	        		customData = k;
	        	}
	        	if (k != "processName") {
	        		row.addCell(new sap.m.Link({
		        		enabled: that.onEnableLink(obj[k]),
		        		text: obj[k],
		        		tooltip: obj[k],
		        		customData: new sap.ui.core.CustomData({
		        			"key":customData,
		        			"value":customData,
		        		}),
		        		press:function(oEvent){
		        			that.onProcessTypeClick(oEvent);
		        		}
		        	}).addStyleClass("taskAgeingLink"));
	        	}
	        }
	        return row;
	    });
		
		oProcessAgingTotalTable.bindAggregation("columns",
				"oProcessAgingTableModel>/totalDtos", function(index, context) {
					return new sap.m.Column({
							header : new sap.m.Text({
							text : context.getObject().value,
							wrapping : true
						}).addStyleClass("userWorkLoadTableHeaderLblStyle"),
						hAlign: pmc.util.formatter.fnGetFooterAlignment(context.getObject().value),
						width: pmc.util.formatter.fnGetTheFooterWidth(context.getObject().value)
					});
				});
		oProcessAgingTableModel.refresh(true);
	},
	
	onEnableLink: function(oValue){
		if(oValue == 0){
			return false;
		} else {
			return true;
		}
	},
	
	/**********************End - Dynamic table creation*****************************/
	
	/**********************Start - Creation of Process List Dialog*****************************/
	onDialogCreation: function(processName){
		var oCore = sap.ui.getCore();
		var oView = this.getView();
		if (!this._oProcessDetailsDialog) {
			var that = this;
			var _processListFragment = sap.ui.xmlfragment('idProcessAgingReport','pmc.fragments.processList', that);
			this._oProcessDetailsDialog = new Dialog({
				title: processName,
				"class":"dialogStyleClass",
				contentWidth: "80%",
				resizable: true,
				beginButton: new Button({
					text: 'Close',
					type: 'Accept',
					press: function (oEvent) {
						oUserDefaultModel.getData().previousScreen = "processAging";
						oUserDefaultModel.refresh();
						that._oProcessDetailsDialog.close();
						that.pageNumber = 1;
					}
				}).addStyleClass("fragmentCloseBtn sapUiSizeCompact")
			}).addStyleClass("dialogClass");
			this._oProcessDetailsDialog.addContent(_processListFragment);
			this.getView().addDependent(this._oProcessDetailsDialog);
		}
		this._oProcessDetailsDialog.setTitle(processName);
		this._oProcessDetailsDialog.open();
		if(oUserDefaultModel.getData().previousScreen != "processFlow"){
			var totalCount = this.processCount;
			var perPageView = 20;
			this.pageNumber = 1;
			oCore.byId("idProcessAgingReport--idPrevButton").setEnabled(false);
			oCore.byId("idProcessAgingReport--idNextButton").setEnabled(true);
			var pageCount = parseInt(totalCount / perPageView);
			if(totalCount % perPageView !== 0){
				pageCount = pageCount + 1;
			}
			this.noOfPages = pageCount;
			var array = [];
			if(pageCount > 5){
				pageCount = 5;
			} else {
				oCore.byId("idProcessAgingReport--idNextButton").setEnabled(false);
			}
			for(var i = 1; i <= pageCount; i++){
				var object = {"text" : i};
				array.push(object);
			}
			this.getView().getModel("paginatedModel").setProperty('/array', array);
		}
		oCore.byId("idProcessAgingReport--idCurrentPage").setText("Page : " + this.pageNumber);
		this.onFilterTableData();
	},
	/**********************End - Creation of Process List Dialog*****************************/
	
	onProcessTypeClick: function(oEvent){
		this.busy.open();
		this.processCount = oEvent.getSource().getText();
		var oUserDefaultData = oUserDefaultModel.getData();
		var processAgingTableModel=oEvent.getSource().getBindingContext("oProcessAgingTableModel");
		if(isNaN(this.processCount)){
			this.processCount = processAgingTableModel.getObject().count;
		}
		oUserDefaultData.selectedKey = oEvent.getSource().getCustomData()[0].getValue();
		oUserDefaultData.processName = processAgingTableModel.getObject().processName;
		this.onLoadingProcessList();
	},
	
	onLoadingProcessList: function(){
		var fromDay = "";
		var toDay = "";
		var oUserDefaultData = oUserDefaultModel.getData();
		var selectedKey = oUserDefaultData.selectedKey;
		var processName = oUserDefaultData.processName;
		var range = this.getView().byId("idReportTab").getSelectedKey();
		var oUserWorkLoadModel = this.getView().getModel("oUserWorkLoadModel");
		oUserWorkLoadModel.setData({});
		oUserWorkLoadModel.refresh(true);
		var today = new Date();
		if (selectedKey == "processName" || selectedKey == "count"){
			toDay = 0;
			if (range === "week"){
				fromDay = 6;
			} else {
				var oProcessAgingTableModel = this.getView().getModel("oProcessAgingTableModel");
				var headerDto = oProcessAgingTableModel.getData().headerDtos;
				var lastRange = parseInt(headerDto[headerDto.length-2].key.split("-").pop());
				fromDay = lastRange;
			}
		} else {
			if (range === "week"){
				var todayDate = (today.getMonth()+1)+"/"+today.getDate()+"/"+today.getFullYear();
				var date1 = new Date(todayDate);
				var date2 = new Date(selectedKey);
				var timeDiff = Math.abs(date2.getTime() - date1.getTime());
				var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
				toDay = diffDays;
				fromDay = diffDays;
			} else {
				toDay = selectedKey.split('-')[0].trim();
				fromDay = selectedKey.split('-')[1].trim();
			}
		}
		var oHeader = {
			"Content-Type" : "application/json;charset=utf-8"
		};
		var aData = {
				"processName" : processName,
				"startDayFrom" : fromDay,
				"startDayTo" : toDay,
				"page" : this.pageNumber
		};
		oUserWorkLoadModel.loadData("/appone/pmc/process/by/duration",JSON.stringify(aData), true, "POST",false, false, oHeader);
	},
	
	/**********************Start - Graph Creation*****************************/
	createGraph: function(range){
		var oProcessAgingGraph = this.getView().byId("PMC_PROCESS_AGING_GRAPH");
		var rangeType = "";
		var maxValue = 5;
		if(range == "week"){
			rangeType = "Dates";
		} else {
			rangeType = "Days";
		}
		oProcessAgingGraph.destroyFeeds();
		var oProcessAgingGraphModel = this.getView().getModel("oProcessAgingGraphModel");
		var processDisplayName = "";
		var graphData = oProcessAgingGraphModel.getData().ageingGraph;
		for(var i=0; i<graphData.length; i++){
			if (graphData[i].processDisplayName){
				processDisplayName = graphData[i].processDisplayName;
				break;
			}
		}
		for(var i=0; i<graphData.length; i++){
			if(!graphData[i].processDisplayName){
				graphData[i].processDisplayName = processDisplayName;
				graphData[i].noOfProcess = 0;
			}
		}
		var oDataset = new sap.viz.ui5.data.FlattenedDataset({
			dimensions : [{
				name : rangeType,
				value : "{range}"},{
					name : 'Process Name',
					value : "{processDisplayName}"}],
					measures : [{
						name : 'Process Count',
						value : '{noOfProcess}'}
					],
					data : {
						path : "/ageingGraph"
					}
		});		
		oProcessAgingGraph.setDataset(oDataset);
		oProcessAgingGraphModel.refresh(true);
		oProcessAgingGraph.setModel(oProcessAgingGraphModel);	

		var feedValueAxis = new sap.viz.ui5.controls.common.feeds.FeedItem({
			'uid': "valueAxis",
			'type': "Measure",
			'values': ["Process Count"]
		}),
		feedCategoryAxis = new sap.viz.ui5.controls.common.feeds.FeedItem({
			'uid': "categoryAxis",
			'type': "Dimension",
			'values': [rangeType]
		}),
		feedCategoryAxis1 = new sap.viz.ui5.controls.common.feeds.FeedItem({
			'uid': "color",
			'type': "Dimension",
			'values': ["Process Name"]
		});
		oProcessAgingGraph.addFeed(feedValueAxis);
		oProcessAgingGraph.addFeed(feedCategoryAxis);
		oProcessAgingGraph.addFeed(feedCategoryAxis1);
		var tableData = this.getView().getModel("oProcessAgingTableModel").getData().headerMap.entry;
		for(var i=0; i<tableData.length; i++){
			if (parseInt(tableData[i].value) > parseInt(maxValue)){
				maxValue = parseInt(tableData[i].value);
			}
		}
		oProcessAgingGraph.setVizProperties({
			plotArea:{primaryScale:{fixedRange: true, maxValue: maxValue},dataLabel: {
				visible: false
			}}
		});
	},
	
	/*onLoadWeekGraphData: function(){
		this.getView().byId("idRangeBtn").setSelectedKey("week");
		this.onLoadTableData();
	},
	
	onLoadMonthGraphData: function(){
		this.getView().byId("idRangeBtn").setSelectedKey("month");
		this.onLoadTableData();
	},*/
	
	
	
	/**********************End - Graph Creation*****************************/
	
	/**********************Start - Table data filtering*****************************/
	onFilterTableData: function(){
		var searchData = this.getView().getModel("oUserWorkLoadModel").getData();
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
			var oFilter3 = new sap.ui.model.Filter("subject","Contains",searchData.subject);
			filters.push(oFilter3);
		}
		if (searchData.startedAt){
			var oFilter4 = new sap.ui.model.Filter("date","Contains",searchData.startedAt);
			filters.push(oFilter4);
		}
		if (searchData.startedByDisplayName){
			var oFilter5 = new sap.ui.model.Filter("startedByDisplayName","Contains",searchData.startedByDisplayName);
			filters.push(oFilter5);
		}
		var tableId = sap.ui.getCore().byId("idProcessAgingReport--processListTable");
		tableId.getBinding("items").filter(filters,sap.ui.model.FilterType.Application);
	},
	/**********************End - Table data filtering*****************************/
	
	/**********************Start - Navigating to Process Flow screen*****************************/
	onNavigateToTaskScreen: function(oEvent){
		oUserDefaultModel.getData().processId =  oEvent.getSource().getBindingContext("oUserWorkLoadModel").getObject().processId;
		this.oRouter.navTo("processFlow");
	},
	/**********************End - Navigating to Process Flow screen*****************************/

/**
* Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
* (NOT before the first rendering! onInit() is used for that one!).
* @memberOf pmc_ui.processAging
*/
//	onBeforeRendering: function() {
//
//	},

/**
* Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
* This hook is the same one that SAPUI5 controls get after being rendered.
* @memberOf pmc_ui.processAging
*/
//	onAfterRendering: function() {
//		
//	},
	
	updateGraphProperty: function() {
		var themeType = oUserDefaultModel.getProperty("/theme");
		var textColor = "black";
		var bgColor = "white";
		var shadowColor = "#f7f7f7"
		if (themeType == "dark") {
			textColor = "white";
			shadowColor = "#989696";
			bgColor = "#333030";
		}
		var fillRate = this.fillRate = this.getView().byId("PMC_PROCESS_AGING_GRAPH");
		fillRate.setVizProperties(
				{
					title:{
						visible:false
					},
					valueAxis: {
						title: {
							visible: true
						}
					},
					categoryAxis: {
						label:{
							visible:true,
							style:{
								color: textColor
							}
						},
						title: {
							visible: true,
							style:{
								color: textColor
							}
						},
						hoverShadow:{
							color: shadowColor
						}
					},
					legendGroup: {
						layout: {
							position: "right"
						}
					},
					legend:{
						label:{
							style:{
								color: textColor
							}
						},
						hoverShadow:{
							color: shadowColor
						},
						title:{
							style:{
								color: textColor
							}
						}
					},
					general:{
						background:{
							color: bgColor
						}
					},
					plotArea:{
						background:{
							color: bgColor
						},
						dataLabel:{
							style:{
								color: textColor
							}
						}
					},
					valueAxis:{
						label:{
							visible:true,
							style:{
								color: textColor
							}
						},
						title: {
							visible: true,
							style:{
								color: textColor
							}
						}
					}});
		var preDisGraph = this.getView().byId("PMC_PROCESS_AGING_GRAPH");
		var scales = [{
			'feed': 'color',
			'palette': ['#36a6e3','#55b49b','#67bb3a','#e3df11','#fdc400','#bde335','#e3c835']
		}];
		preDisGraph.setVizScales(scales);
	},
	
	onSort: function(){
		var sortingType = oUserDefaultModel.getData().sort;
		var oTable = sap.ui.getCore().byId("idProcessAgingReport--processListTable");
		var oBinding = oTable.getBinding("items");
		var aSorters = [];
		var sPath = "startedAt";
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
	
	onBackToLaunchPage: function(){
		var oUserDefaultData = oUserDefaultModel.getData();
		oUserDefaultData.userNavigateScreen = "";
		oUserDefaultData.selectedKey = "";
		oUserDefaultData.processName = "";
		oUserDefaultModel.refresh(true);
		this.oRouter.navTo("launchPage");
	},
	
	onScrollLeft: function(){
		var oCore = sap.ui.getCore();
		oCore.byId("idProcessAgingReport--idPrevButton").setEnabled(true);
		oCore.byId("idProcessAgingReport--idNextButton").setEnabled(true);
		var paginatedData = this.getView().getModel("paginatedModel").getData().array;
		var selectedPage = parseInt(this.pageNumber);
		var startValue = parseInt(paginatedData[0].text);
		var startNumber = 1;
		var array = [];
		if((startValue - 1) === 1){
			startNumber = 1;
			oCore.byId("idProcessAgingReport--idPrevButton").setEnabled(false);
		} else {
			startNumber = selectedPage - 3;
		}
		for(var i = startNumber; i <= (startNumber+4); i++){
			var object = {"text" : i};
			array.push(object);
		}
		this.getView().getModel("paginatedModel").setProperty('/array', array);
		this.pageNumber = parseInt(this.pageNumber) - 1;
		this.onLoadingProcessList();
	},
	
	onScrollRight: function(){
		var oCore = sap.ui.getCore();
		oCore.byId("idProcessAgingReport--idPrevButton").setEnabled(true);
		oCore.byId("idProcessAgingReport--idNextButton").setEnabled(true);
		var paginatedData = this.getView().getModel("paginatedModel").getData().array;
		var selectedPage = parseInt(this.pageNumber);
		var startNumber = 1;
		var array = [];
		if (selectedPage > 2) {
			if ((selectedPage + 3) >= this.noOfPages) {
				oCore.byId("idProcessAgingReport--idNextButton").setEnabled(false);
				startNumber = parseInt(this.noOfPages) - 4;
			} else {
				startNumber = selectedPage - 1;
			}
		} else {
			oCore.byId("idProcessAgingReport--idPrevButton").setEnabled(false);
		}
		for(var i = startNumber; i <= (startNumber+4); i++){
			var object = {"text" : i};
			array.push(object);
		}
		this.getView().getModel("paginatedModel").setProperty('/array', array);
		this.pageNumber = parseInt(this.pageNumber) + 1;
		this.onLoadingProcessList();
	},
	
	onPageClick: function(oEvent){
		var selectedPage = oEvent.getSource().getText();
		oUserDefaultModel.getData().previousScreen = "processFlow";
		this.pageNumber = selectedPage;
		this.onLoadingProcessList();
	},
	
	onCancelPress:function(oEvent){
		var i18n = this.getView().getModel("i18n").getResourceBundle();
		var table = sap.ui.getCore().byId("idProcessAgingReport--processListTable");
		if (table.getSelectedItems().length == 0){
			toastMessage(i18n.getText("SELECT_TO_CANCEL"));
			return;
		}
		var that = this;
		sap.m.MessageBox.show(i18n.getText("CANCEL_CONFIRMATION"),
				sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
				function(oEvt){
					if(oEvt === "NO"){
						return;
					}
					else if(oEvt === "YES"){
					that.onProcessCancel();
					}
				},
				sap.m.MessageBox.Action.YES);
	},
	
	onProcessCancel: function(){
		var that = this;
		var table = sap.ui.getCore().byId("idProcessAgingReport--processListTable");
		var contexts = table.getSelectedContextPaths();
		var oUserWorkLoadModel= this.getView().getModel("oUserWorkLoadModel");
		var listData = oUserWorkLoadModel.getData().processEventsList;
		var index;
		var instancesList = [];
		for(var i= 0 ;i<contexts.length;i++){
			index = contexts[i].split("/")[2];
			instancesList.push(listData[index].processId)
		}
		var i18nBundle = that.getView().getModel("i18n").getResourceBundle();
		that.busy.open();
		var oProcessLoadModel = new sap.ui.model.json.JSONModel();
		that.getView().setModel(oProcessLoadModel, "oProcessLoadModel");
		var oHeader = {
				"Content-Type" : "application/json;charset=utf-8"
		};
		var aData = {
				"processInstanceList": instancesList
		};
		oProcessLoadModel.loadData("/appone/pmc/processAction/cancel",JSON.stringify(aData), true, "POST",false, false, oHeader);
		oProcessLoadModel.attachRequestCompleted(function(oEvent) {
			that.busy.close();
			var processLoadModel=that.getView().getModel("oProcessLoadModel")
			var processLoadData = processLoadModel.getData();
			if (processLoadData && processLoadData.statusCode!="1"){
				sap.m.MessageBox.show(that.getView().getModel("i18n").getResourceBundle().getText("CANCEL_PROCESS_SUCCESS"),
						sap.m.MessageBox.Icon.SUCCESS," ",[sap.m.MessageBox.Action.OK],
						function(oEvt){
					if(oEvt==="OK"){
						table.removeSelections();
						that.onLoadingProcessList();
						that.onLoadTableData();
					}
				},
				sap.m.MessageBox.Action.YES);
			}
			 else {
				toastMessage(i18nBundle.getText("CANCEL_FAILURE"));   
			}
		});
		oProcessLoadModel.attachRequestFailed(function(oEvent) {
			that.busy.close();
			toastMessage(i18nBundle.getText("CANCEL_FAILURE"));   
		});
	},
	
	onProcessAgingDownload:function(oEvent){
		var that = this;
		this.busy.open();
		var oView = this.getView();
		var range = oView.byId("idReportTab").getSelectedKey();
		var processName = oView.byId("idSelectedProcessName").getSelectedKey();
		var oDownloadLoadModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(oDownloadLoadModel, "oDownloadLoadModel");
		var oHeader = {
				"Content-Type" : "application/json;charset=utf-8"
		};
		var aData = {
				"reportName":"Process Aeging",
				"fileFormate":"Excel",
				"processName" : processName,
				"graphType" : range
		};
		oDownloadLoadModel.loadData("/appone/pmc/report/download",JSON.stringify(aData), true, "POST",false, false, oHeader);
		oDownloadLoadModel.attachRequestCompleted(function(oEvent) {
			that.busy.close();
			if (oView.getModel("oDownloadLoadModel").getData()){
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
			toastMessage(oView.getModel("i18n").getResourceBundle().getText("DOWNLOAD_FAILURE"));
		});
	},
	
	fragmentDownload: function(){
		var that = this;
		var oView = this.getView();
		var fromDay = "";
		var toDay = "";
		var selectedKey = oUserDefaultModel.getData().selectedKey;
		var processName = oUserDefaultModel.getData().processName;
		var oListDownloadLoadModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oListDownloadLoadModel, "oListDownloadLoadModel");
		var range = oView.byId("idReportTab").getSelectedKey();
		var today = new Date();
		if (selectedKey == "processName" || selectedKey == "count"){
			toDay = 0;
			if (range === "week"){
				fromDay = 6;
			} else {
				fromDay = 29;
			}
		} else {
			if (range === "week"){
				var todayDate = (today.getMonth()+1)+"/"+today.getDate()+"/"+today.getFullYear();
				var date1 = new Date(todayDate);
				var date2 = new Date(selectedKey);
				var timeDiff = Math.abs(date2.getTime() - date1.getTime());
				var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
				toDay = diffDays;
				fromDay = diffDays;
			} else {
				toDay = selectedKey.split('-')[0].trim();
				fromDay = selectedKey.split('-')[1].trim();
			}
		}
		var oHeader = {
			"Content-Type" : "application/json;charset=utf-8"
		};
		var aData = {
				"reportName":"Process By Duration",
				"fileFormate":"Excel",
				"processName" : processName,
				"startDayFrom" : fromDay,
				"startDayTo" : toDay
		};
		oListDownloadLoadModel.loadData("/appone/pmc/report/download",JSON.stringify(aData), true, "POST",false, false, oHeader);
		oListDownloadLoadModel.attachRequestCompleted(function(oEvent) {
			that.busy.close();
			if (that.getView().getModel("oListDownloadLoadModel").getData()){
				var Base64 = oListDownloadLoadModel.getData()["base64"];
				var filename = oListDownloadLoadModel.getData()["filename"];
				if(!jQuery.isEmptyObject(oListDownloadLoadModel.getData())){
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
		oListDownloadLoadModel.attachRequestFailed(function(oEvent) {
			that.busy.close();
			toastMessage(that.getView().getModel("i18n").getResourceBundle().getText("DOWNLOAD_FAILURE"));
		});
		}
	
/**
* Called when the Controller is destroyed. Use this one to free resources and finalize activities.
* @memberOf pmc_ui.processAging
*/
//	onExit: function() {
//
//	}
	});
});