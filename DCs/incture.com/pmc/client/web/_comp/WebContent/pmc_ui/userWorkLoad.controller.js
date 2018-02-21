sap.ui.define([
               'jquery.sap.global',
               'sap/ui/core/Fragment',
               'sap/ui/core/mvc/Controller',
               'sap/ui/model/json/JSONModel'
               ], function (jQuery, Fragment, Controller, JSONModel) {
	"use strict";
	return Controller.extend("pmc_ui.userWorkLoad", {

		/**
		 * Called when a controller is instantiated and its View controls (if available)
		 * are already created. Can be used to modify the View before it is displayed,
		 * to bind event handlers and do other one-time initialization.
		 * 
		 * @memberOf pmc_ui.userWorkLoad
		 */
		onInit: function() {
			this.oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			var that = this;
			var oView = that.getView();
			this.noOfPages = 0;
			this.pageNumber = 1;
			oView.setModel(oUserDefaultModel,"oUserDefaultModel");
			this.busy = new sap.m.BusyDialog();
			this.oRouter.attachRoutePatternMatched(function(oEvent) {
				if (oEvent.getParameter("name") === "userWorkLoad") {
					oUserDefaultModel.getData().userNavigateScreen = "userWorkLoad";
					oUserDefaultModel.refresh(true);
					oView.byId("workLoadTypeTab").setSelectedKey("userDetailsTab");
					oView.byId("userWorkLoadGraphFlex").setVisible(false);
					oView.byId("usertaskStatusFlex").setVisible(true);
					var userId = oEvent.getParameter("arguments").ContextPath;
					oUserDefaultModel.getData().userId = userId;
					oUserDefaultModel.refresh(true);
					that.onGetUserDetails(userId);
					that.updateGraphProperty();
					that.onTableCreation(userId);
					if (!that.processList) {
						that.processList = sap.ui.xmlfragment('idUserWorkLoadReport','pmc.fragments.processList', that);
					}
					oView.addDependent(that.processList);
					var panel=oView.byId("userWorkLoadPanel");
					panel.addContent(that.processList);
				}
			});
		},

		/***********************Start - Handling the tab selection*****************************/
		handleTabSelect: function(oEvent){
			var selectedTab = oEvent.getSource().getSelectedKey();
			if (selectedTab == "userDetailsTab"){
				this.onTableCreation(oUserDefaultModel.getData().userId);
			} else if(selectedTab == "userDetailsGraphTab"){
				oUserDefaultModel.getData().previousScreen = "graphView";
				this.getView().byId("idUserRangeBtn").setSelectedKey("WEEK");
				this.getView().byId("idGraphTypeBtn").setState(false);
				this.onchangeGraph();
			}
		},
		/*** ********************End - Handling the tab selection*****************************/

		/*** ********************Start - Handling the Range filter in the graph*****************************/
		onRangeChange: function(oEvent){
			var rangeValue = oEvent.getSource().getText().split(" ")[1].toLowerCase();
			var range = "";
			if (rangeValue == "7"){
				range = "week";
				this.getView().byId("idUserRangeBtn").setSelectedKey("WEEK");
			} else {
				range = "month";
				this.getView().byId("idUserRangeBtn").setSelectedKey("MONTH");
			}
			this.onchangeGraph();
		},

		onchangeGraph: function(){
			var status = oUserDefaultModel.getData().userTaskStatus;
			var range = this.getView().byId("idUserRangeBtn").getSelectedKey();
			this.onLoadColumnGraphData(range.toLowerCase());
		},
		/**********************End - Handling the Range filter in the graph*****************************/

		/**********************Start - Getting the user process details*****************************/
		onGetUserDetails: function(userId){
			var that = this;
			var oView = that.getView();
			var oUserDetailsModel = new sap.ui.model.json.JSONModel();
			oView.setModel(oUserDetailsModel, "oUserDetailsModel");
			oUserDetailsModel.loadData("/appone/pmc/user/info/"+userId,null,true);
			oUserDetailsModel.attachRequestCompleted(function(oEvent) {
				if (oView.getModel("oUserDetailsModel").getData()){
					oView.getModel("oUserDetailsModel").refresh();
				}
			});
		},
		/**********************End - Getting the user process details*****************************/

		/**********************Start - Creation of Process List Dynamic table*****************************/
		onTableCreation: function(userId){
			this.busy.open();
			var that = this;
			var oView = that.getView();
			var oCore = sap.ui.getCore();
			var paginatedModel = new sap.ui.model.json.JSONModel();
			var oUserWorkLoadModel = new sap.ui.model.json.JSONModel();
			oView.setModel(oUserWorkLoadModel, "oUserWorkLoadModel");
			oUserWorkLoadModel.setSizeLimit(10000);
			if(oUserDefaultModel.getData().previousScreen === "userWorkList"){
				this.pageNumber = 1;
			}
			var oHeader = {
					"Content-Type" : "application/json;charset=utf-8"
			};
			var aData = {
					"userId" : userId,
					"status" : oUserDefaultModel.searchData.taskStatus,
					"processName" : oUserDefaultModel.searchData.processName,
					"requestId" : oUserDefaultModel.searchData.requestId,
					"labelValue" : oUserDefaultModel.searchData.labelName,
					"page" : this.pageNumber
			};
			oUserWorkLoadModel.loadData("/appone/pmc/process/by/taskowner",JSON.stringify(aData), true, "POST",false, false, oHeader);
			oUserWorkLoadModel.attachRequestCompleted(function(oEvent) {
				that.busy.close();
				var userWorkLoadModel = oView.getModel("oUserWorkLoadModel");
				if (userWorkLoadModel.getData() && userWorkLoadModel.getData().responseMessage.statusCode==="0"){
					if(oUserDefaultModel.getData().previousScreen === "userWorkList"){
						var totalCount = userWorkLoadModel.getData().count;
						oCore.byId("idUserWorkLoadReport--idPrevButton").setEnabled(false);
						oCore.byId("idUserWorkLoadReport--idNextButton").setEnabled(true);
						var perPageView = 20;
						var pageCount = parseInt(totalCount / perPageView);
						if(totalCount % perPageView !== 0){
							pageCount = pageCount + 1;
						}
						that.noOfPages = pageCount;
						var array = [];
						if(pageCount > 5){
							pageCount = 5;
						} else {
							oCore.byId("idUserWorkLoadReport--idNextButton").setEnabled(false);
						}
						for(var i = 1; i <= pageCount; i++){
							var object = {"text" : i};
							array.push(object);
						}
						paginatedModel.setProperty('/array', array);
						oView.setModel(paginatedModel, "paginatedModel");
					}
					oCore.byId("idUserWorkLoadReport--idCurrentPage").setText("Page : " + that.pageNumber);
					if (!(userWorkLoadModel.oData.processEventsList instanceof Array)) {
						userWorkLoadModel.oData.processEventsList = [userWorkLoadModel.oData.processEventsList];
					}
					userWorkLoadModel.refresh();
				}else{
					toastMessage(userWorkLoadModel.getData().responseMessage.message);
				}
			});
			oUserWorkLoadModel.attachRequestFailed(function(oEvent) {
				that.busy.close();
				toastMessage(oView.getModel("oUserWorkLoadModel").getData().responseMessage.message);
			});
		},
		/**********************End - Creation of Process List Dynamic table*****************************/

		/**********************Start - Navigating to Process Flow screen*****************************/
		onNavigateToTaskScreen: function(oEvent){
			oUserDefaultModel.getData().processId =  oEvent.getSource().getBindingContext("oUserWorkLoadModel").getObject().processId;
			this.oRouter.navTo("processFlow");
		},
		/**********************End - Navigating to Process Flow screen*****************************/

		/**********************Start - Navigating back to User List*****************************/
		onNavigateToList: function(){
			this.oRouter.navTo("userWorkList");
		},
		/**********************End - Navigating back to User List*****************************/

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
			var tableId = sap.ui.getCore().byId("idUserWorkLoadReport--processListTable");
			tableId.getBinding("items").filter(filters,sap.ui.model.FilterType.Application);
		},
		
		/**********************End - Table data filtering*****************************/
		onSort: function(){
			var oUserDefaultData = oUserDefaultModel.getData();
			var sortingType = oUserDefaultData.sort;
			var oTable = sap.ui.getCore().byId("idUserWorkLoadReport--processListTable");
			var oBinding = oTable.getBinding("items");
			var aSorters = [];
			var sPath = "startedAt";
			var bDescending = "";
			if (sortingType){
				oUserDefaultData.sort = false;
				bDescending = false;
			} else {
				oUserDefaultData.sort = true;
				bDescending = true;
			}
			oUserDefaultModel.refresh(true);
			aSorters.push(new sap.ui.model.Sorter(sPath, bDescending));
			oBinding.sort(aSorters);
		},

		/**
		 * Similar to onAfterRendering, but this hook is invoked before the controller's
		 * View is re-rendered (NOT before the first rendering! onInit() is used for
		 * that one!).
		 * 
		 * @memberOf pmc_ui.userWorkLoad
		 */
//		onBeforeRendering: function() {

//		},

		/**
		 * Called when the View has been rendered (so its HTML is part of the document).
		 * Post-rendering manipulations of the HTML could be done here. This hook is the
		 * same one that SAPUI5 controls get after being rendered.
		 * 
		 * @memberOf pmc_ui.userWorkLoad
		 */
//		onAfterRendering: function() {
//			
//		},
		
		updateGraphProperty: function() {
			var themeType = oUserDefaultModel.getProperty("/theme");
			var textColor = "black";
			var shadowColor = "#f7f7f7";
			var backGround = "#ffffff";
			if (themeType == "dark") {
				textColor = "white";
				shadowColor = "#989696";
				backGround = "#333030";
			}
			var fillRate = this.getView().byId("userWorkLoadGraph");
			fillRate.setVizProperties(
			{
				title:{
					visible:false
				},
				legendGroup: {
					layout: {
						position: "right"
					}
				},
				legend:{
					visible: true,
					hoverShadow:{
						color: shadowColor
					},
					label:{
						style:{
							color: textColor
						}
					},
					title:{
						style:{
							color: textColor
						}
					}
				},
				general:{
					background:{
						color: backGround
					}
				},
				plotArea:{
					background:{
						color:backGround
					},
					dataLabel:{
						style:{
							color: textColor
						},
						type: "value",
						visible:true
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
			var preDisGraph = this.getView().byId("userWorkLoadGraph");
			var scales = [{
				'feed': 'color',
				'palette': ['#f3af5e','#65BB3C']

			}];
			preDisGraph.setVizScales(scales);

			var fillRateOCG = this.getView().byId("PMC_TASK_STATUS_GRAPH");
			fillRateOCG.setVizProperties(
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
								color: "transparent"
							}
						},
						plotArea:{
							background:{
								color:"transparent"
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
			var preDisGraphOCG = this.getView().byId("PMC_TASK_STATUS_GRAPH");
			var scalesOCG = [{
				'feed': 'color',
				'palette': ['#f3af5e','#65BB3C']

			}];
			preDisGraphOCG.setVizScales(scalesOCG);
		},

		onLoadColumnGraphData: function(range){
			var that = this;
			var oView = that.getView();
			this.busy.open();
			var oSwitch =  oView.byId("idGraphTypeBtn");
			var oTaskStatusLoadModel = new sap.ui.model.json.JSONModel();
			oView.setModel(oTaskStatusLoadModel,"oTaskStatusLoadModel");
			var oTaskStatusGraphModel = new sap.ui.model.json.JSONModel();
			oView.setModel(oTaskStatusGraphModel,"oTaskStatusGraphModel");
			var oHeader = {
					"Content-Type" : "application/json;charset=utf-8"
			};
			var aData = {
					"userId" :  oUserDefaultModel.getData().userId,
					"status" : oUserDefaultModel.searchData.taskStatus,
					"processName" : oUserDefaultModel.searchData.processName,
					"requestId" : oUserDefaultModel.searchData.requestId,
					"labelValue" : oUserDefaultModel.searchData.labelName,
					"graphType" : range
			};

			oTaskStatusLoadModel.loadData("/appone/pmc/userload/status/graph",JSON.stringify(aData), true, "POST",false, false, oHeader);
			oTaskStatusLoadModel.attachRequestCompleted(function(oEvent) {
				that.busy.close();
				var taskStatusLoadModel = oView.getModel("oTaskStatusLoadModel");
				var taskStatusGraphModel = oView.getModel("oTaskStatusGraphModel");
				var taskStatusLoadData = taskStatusLoadModel.getData();
				var taskStatusGraphData = taskStatusGraphModel.oData;
				if(taskStatusLoadData.taskCountDetail && taskStatusLoadData.responseMessage.statusCode != "0"){
					that.getView().byId("usertaskStatusFlex").setVisible(true);
					if (range == "month") {
						taskStatusLoadData.taskCountDetail.entry.reverse();
					}
					taskStatusGraphModel.setProperty("/statusGraph",taskStatusLoadData.taskCountDetail.entry);
					if (!(taskStatusGraphData.statusGraph instanceof Array)) {
						taskStatusGraphData.statusGraph = [taskStatusGraphData.statusGraph];
					}
					oView.getModel("oTaskStatusGraphModel").refresh();
					if(!oSwitch.getState()){
						oView.byId("userWorkLoadGraphFlex").setVisible(false);
						oView.byId("usertaskStatusFlex").setVisible(true);
					}
					else{
						oView.byId("usertaskStatusFlex").setVisible(false);
						oView.byId("userWorkLoadGraphFlex").setVisible(true);
					}
					that.createGraph(range);
					that.onPieChatCreation();
				} else {
					oView.getModel("oTaskStatusGraphModel").setData({});
					oView.byId("usertaskStatusFlex").setVisible(false);
					oView.byId("userWorkLoadGraphFlex").setVisible(false);
					toastMessage(taskStatusLoadData.responseMessage.message);
				}
			});
			oTaskStatusLoadModel.attachRequestFailed(function(oEvent) {
				that.busy.close();
				oView.getModel("oTaskStatusGraphModel").setData({});
				oView.byId("usertaskStatusFlex").setVisible(false);
				oView.byId("userWorkLoadGraphFlex").setVisible(false);
				toastMessage(oView.getModel("i18n").getResourceBundle().getText("NO_RESULTS_FOUND_SEARCH"));
			});
		},
		
		/**********************Start - Creation of Process Graph*****************************/
		onPieChatCreation: function() {
			var oDayGraphDataModel = new sap.ui.model.json.JSONModel();
			this.getView().setModel(oDayGraphDataModel, "oDayGraphDataModel");
			oDayGraphDataModel.setProperty("/graphData", "");
			var oTaskStatusGraphData = this.getView().getModel("oTaskStatusLoadModel").getProperty("/taskCountDetail").entry;
			var graphData = [];
			var closedCount = 0;
			var openCount = 0;
			for(var i = 0; i < oTaskStatusGraphData.length; i++){
				closedCount = closedCount + parseInt(oTaskStatusGraphData[i].value.closed);
				openCount = openCount + parseInt(oTaskStatusGraphData[i].value.open);
			}
			graphData.push({"status": "Open", "count": openCount});
			graphData.push({"status": "Closed", "count": closedCount});
			oDayGraphDataModel.setProperty("/graphData", graphData);
		},
		/**********************End - Creation of Process Graph*****************************/

		/** ********************Start - Graph Creation**************************** */
		createGraph: function(range){
			var oTaskStatusGraph = this.getView().byId("PMC_TASK_STATUS_GRAPH");
			var rangeType = "";
			if(range == "week"){
				rangeType = "Dates";
			} else {
				rangeType = "Days";
			}
			oTaskStatusGraph.destroyFeeds();
			var oTaskStatusGraphModel = this.getView().getModel("oTaskStatusGraphModel");
			var graphData = oTaskStatusGraphModel.getData().statusGraph;
			var list =[];
			var maxValue = 5;

			for( var i=0;i<graphData.length;i++){
				var open = graphData[i].value.open;
				var closed = graphData[i].value.closed;
				var obj = {
						"range" : graphData[i].key,
						"processName" : "Open",
						"noOfProcess" : open
				};
				var obj1 = {
						"range" : graphData[i].key,
						"processName" : "Closed",
						"noOfProcess" : closed
				};
				list.push(obj);
				list.push(obj1);
				if(parseInt(closed) > maxValue){
					maxValue = parseInt(closed) ;
				}
				if(parseInt(open) > maxValue){
					maxValue = parseInt(open) ;
				}

			}

			oTaskStatusGraphModel.getData().statusGraph = list ;
			oTaskStatusGraphModel.refresh();
			var oDataset = new sap.viz.ui5.data.FlattenedDataset({
				dimensions : [{
					name : rangeType,
					value : "{range}"},{
						name : 'Task Status',
						value : "{processName}"}],
						measures : [{
							name : 'Task Count',
							value : '{noOfProcess}'}
						],
						data : {
							path : "/statusGraph"
						}
			});		
			oTaskStatusGraph.setDataset(oDataset);
			oTaskStatusGraphModel.refresh(true);
			oTaskStatusGraph.setModel(oTaskStatusGraphModel);	

			var feedValueAxis = new sap.viz.ui5.controls.common.feeds.FeedItem({
				'uid': "valueAxis",
				'type': "Measure",
				'values': ["Task Count"]
			}),
			feedCategoryAxis = new sap.viz.ui5.controls.common.feeds.FeedItem({
				'uid': "categoryAxis",
				'type': "Dimension",
				'values': [rangeType]
			}),
			feedCategoryAxis1 = new sap.viz.ui5.controls.common.feeds.FeedItem({
				'uid': "color",
				'type': "Dimension",
				'values': ["Task Status"]
			});
			oTaskStatusGraph.addFeed(feedValueAxis);
			oTaskStatusGraph.addFeed(feedCategoryAxis);
			oTaskStatusGraph.addFeed(feedCategoryAxis1);
			oTaskStatusGraph.setVizProperties({
				plotArea:{primaryScale:{fixedRange: true, maxValue: maxValue 
				}}});
			var scales = [{
				'feed': 'color',
				'palette': ['#f3af5e','#65BB3C']

			}];
			oTaskStatusGraph.setVizScales(scales);
		},

		onBackToLaunchPage: function(){
			oUserDefaultModel.getData().userNavigateScreen = "";
			oUserDefaultModel.refresh(true);
			this.oRouter.navTo("launchPage");
		},

		onScrollLeft: function(){
			var oCore = sap.ui.getCore();
			oCore.byId("idUserWorkLoadReport--idPrevButton").setEnabled(true);
			oCore.byId("idUserWorkLoadReport--idNextButton").setEnabled(true);
			var paginatedData = this.getView().getModel("paginatedModel").getData().array;
			var selectedPage = parseInt(this.pageNumber);
			var startValue = parseInt(paginatedData[0].text);
			var startNumber = 1;
			var array = [];
			if((startValue - 1) === 1){
				startNumber = 1;
				oCore.byId("idUserWorkLoadReport--idPrevButton").setEnabled(false);
			} else {
				startNumber = selectedPage - 3;
			}
			for(var i = startNumber; i <= (startNumber+4); i++){
				var object = {"text" : i};
				array.push(object);
			}
			this.getView().getModel("paginatedModel").setProperty('/array', array);
			this.pageNumber = parseInt(this.pageNumber) - 1;
			this.onTableCreation(oUserDefaultModel.getData().userId);
		},

		onScrollRight: function(){
			var oCore = sap.ui.getCore();
			oCore.byId("idUserWorkLoadReport--idPrevButton").setEnabled(true);
			oCore.byId("idUserWorkLoadReport--idNextButton").setEnabled(true);
			var paginatedData = this.getView().getModel("paginatedModel").getData().array;
			var selectedPage = parseInt(this.pageNumber);
			var startNumber = 1;
			var array = [];
			if (selectedPage > 2) {
				if ((selectedPage + 3) >= this.noOfPages) {
					oCore.byId("idUserWorkLoadReport--idNextButton").setEnabled(false);
					startNumber = parseInt(this.noOfPages) - 4;
				} else {
					startNumber = selectedPage - 1;
				}
			} else {
				oCore.byId("idUserWorkLoadReport--idPrevButton").setEnabled(false);
			}
			for(var i = startNumber; i <= (startNumber+4); i++){
				var object = {"text" : i};
				array.push(object);
			}
			this.getView().getModel("paginatedModel").setProperty('/array', array);
			this.pageNumber = parseInt(this.pageNumber) + 1;
			this.onTableCreation(oUserDefaultModel.getData().userId);
		},

		onPageClick: function(oEvent){
			var selectedPage = oEvent.getSource().getText();
			oUserDefaultModel.getData().previousScreen = "paginationButton";
			this.pageNumber = selectedPage;
			this.onTableCreation(oUserDefaultModel.getData().userId);
		},
		/*wordLoadDownload: function(oEvent){
			if (!this._oDownloadDialog) {
				this._oDownloadDialog = sap.ui.xmlfragment('idWLDownload','fragments.downloadAs', this);
				this.getView().addDependent(this._oDownloadDialog);
			}
			this._oDownloadDialog.open();
		},*/
		fragmentDownload:function(oEvent){
			//this._oDownloadDialog.close();
			var that = this;
			this.busy.open();
			var oDownloadLoadModel = new sap.ui.model.json.JSONModel();
			this.getView().setModel(oDownloadLoadModel, "oDownloadLoadModel");
			var oHeader = {
					"Content-Type" : "application/json;charset=utf-8"
			};
			var aData = {
					"reportName":"User workload",
					"fileFormate":"Excel",
					"userId" : oUserDefaultModel.getData().userId,
					"status" : oUserDefaultModel.searchData.taskStatus,
					"processName" : oUserDefaultModel.searchData.processName,
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
		
		onRadioBtnSelect: function(oEvent){
			var index=oEvent.getSource().sId.split("-")[oEvent.getSource().sId.split("-").length-1]
			this.processId=this.getView().getModel("oUserWorkLoadModel").getData().processEventsList[index].processId;
		},
		onCancelPress:function(oEvent){
			var i18n = this.getView().getModel("i18n").getResourceBundle();
			var table = sap.ui.getCore().byId("idUserWorkLoadReport--processListTable");
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
			var table = sap.ui.getCore().byId("idUserWorkLoadReport--processListTable");
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
									 that.onTableCreation(oUserDefaultModel.getData().userId);
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
		
		onPressUserProfile: function(){
			if (!this._oProfileDialog) {
				this._oProfileDialog = sap.ui.xmlfragment('pmc.fragments.userProfile', this);
				this.getView().addDependent(this._oProfileDialog);
			}
			this._oProfileDialog.open();
		},
		
		onProfileClose: function(){
			if (this._oProfileDialog) {
				this._oProfileDialog.close();
			}
		}


		/**
		 * Called when the Controller is destroyed. Use this one to free resources and
		 * finalize activities.
		 * 
		 * @memberOf pmc_ui.userWorkLoad
		 */
//		onExit: function() {

//		}
	});
});