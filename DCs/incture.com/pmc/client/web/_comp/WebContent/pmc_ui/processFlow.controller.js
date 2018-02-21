sap.ui.define([
               'jquery.sap.global',
               'sap/ui/core/Fragment',
               'sap/ui/core/mvc/Controller',
               'sap/ui/model/json/JSONModel'
               ], function (jQuery, Fragment, Controller, JSONModel) {
	"use strict";
	sap.ui.controller("pmc_ui.processFlow", {

		/**
		 * Called when a controller is instantiated and its View controls (if available) are already created.
		 * Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
		 * @memberOf pmc_ui.processFlow
		 */
		onInit: function() {
			this.oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			var that = this;
			this.busy = new sap.m.BusyDialog();
			this.getView().setModel(oUserDefaultModel,"oUserDefaultModel");
			this.oRouter.attachRoutePatternMatched(function(oEvent) {
				if (oEvent.getParameter("name") === "processFlow") {
					oUserDefaultModel.getData().previousScreen = "processFlow";
					var oListModel = new sap.ui.model.json.JSONModel();
					that.getView().setModel(oListModel, "oListModel");
					var oSubListModel = new sap.ui.model.json.JSONModel();
					that.getView().setModel(oSubListModel, "oSubListModel");
					var processId = oUserDefaultModel.getData().processId;
					that.fnProcessDetails(processId);
					that.loadMessageData();
				}
			});
			this.collaborationTagKey = [];
		},

		/**********************Start - Loading Process Details*****************************/
		fnProcessDetails: function(processId){
			var that = this;
			var i18n = that.getView().getModel("i18n").getResourceBundle();
			this.busy.open();
			processId = processId.split("/").join("%2f");
			var oProcessDetailModel = new sap.ui.model.json.JSONModel();
			this.getView().setModel(oProcessDetailModel, "oProcessDetailModel");
			oProcessDetailModel.loadData("/appone/pmc/process/details/"+processId, null,true);
			oProcessDetailModel.attachRequestCompleted(function(oEvent) {
				that.busy.close();
				var processDetailModel=that.getView().getModel("oProcessDetailModel");
				if (processDetailModel.getData()){
					processDetailModel.refresh();
					that.fnTaskDetails(processId);
				} else {
					toastMessage(i18n.getText("NO_RESULTS_FOUND"));
				}
			});
			oProcessDetailModel.attachRequestFailed(function(oEvent) {
				that.busy.close();
				toastMessage(i18n.getText("NO_RESULTS_FOUND"));
			});
		},
		/**********************End - Loading Process Details*****************************/

		/**********************Start - Loading Task Details*****************************/
		fnTaskDetails: function(processId){
			var that = this;
			var i18n = that.getView().getModel("i18n").getResourceBundle();
			this.busy.open();
			var oProcessTaskModel = new sap.ui.model.json.JSONModel();
			this.getView().setModel(oProcessTaskModel, "oProcessTaskModel");
			oProcessTaskModel.loadData("/appone/pmc/task/details/"+processId, null,true);
			oProcessTaskModel.attachRequestCompleted(function(oEvent) {
				that.busy.close();
				var processTaskModel=that.getView().getModel("oProcessTaskModel");
				var processTaskData = processTaskModel.getData();
				if (processTaskData){
					if (!(processTaskData.taskEventDtos instanceof Array)) {
						processTaskData.taskEventDtos = [processTaskData.taskEventDtos];
					}
					var startedAtInString = that.getView().getModel("oProcessDetailModel").getData().startedAtInString;
					var startedProcess = {"completedAtInString" : startedAtInString};
					var data = processTaskModel.oData.taskEventDtos;
					data.unshift(startedProcess);
					processTaskModel.refresh();
				} else {
					toastMessage(i18n.getText("NO_RESULTS_FOUND"));
				}
			});
			oProcessTaskModel.attachRequestFailed(function(oEvent) {
				that.busy.close();
				toastMessage(i18n.getText("NO_RESULTS_FOUND"));
			});
		},
		/**********************End - Loading Task Details*****************************/

		onNavigatingBack: function(){
			var oldScreen = oUserDefaultModel.getData().userNavigateScreen;
			var userId = oUserDefaultModel.getData().userId;
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

		onBackToLaunchPage: function(){
			var oUserDefaultData = oUserDefaultModel.getData();
			oUserDefaultData.userNavigateScreen = "";
			oUserDefaultData.selectedKey = "";
			oUserDefaultData.processName = "";
			oUserDefaultModel.refresh(true);
			this.oRouter.navTo("launchPage");
		},

		onDelegatePress: function(oEvent){
			var that=this;
			var i18n = that.getView().getModel("i18n").getResourceBundle();
			//that.busy.open();
			that.delegateIndex=oEvent.getSource().sId.split("-")[2];
			if (!that.forwardTask) {
				that.forwardTask = sap.ui.xmlfragment(
						"pmc.fragments.Delegate", that);
				that.getView().addDependent(that.forwardTask);
			}
			sap.ui.getCore().byId("idDelegateSearch").setValue("");
			//that.forwardTask.open();
			var oButton = oEvent.getSource();
			jQuery.sap.delayedCall(0, this, function () {
				that.forwardTask.openBy(oButton);
			});
		},

		onDelegateFragmentClose: function(){
			if(this.forwardTask){
				this.forwardTask.close();
				this.busy.close();
			}
		},
		
		onChangeSugg : function(oEvent) {
			var that = this;
			var oUserDefaultData = oUserDefaultModel.getData();
			var value = oEvent.getSource().getValue();
			if (value) {
				var oSubListModel = that.getView().getModel("oSubListModel");
				if (jQuery.isEmptyObject(oSubListModel.getData())) {
					this.busy.open();
					oSubListModel.loadData("/appone/pmc/user/users/*",null,true);
					oSubListModel.attachRequestCompleted(function(oEvt) { 
						var data = oSubListModel.getData();
						if (!(data.userDtos instanceof Array)) {
							data.userDtos = [data.userDtos];
						}
						oSubListModel.setSizeLimit(data.userDtos.length);
						data.userDtos = data.userDtos.filter(function(e) {
							return e.loginId != oUserDefaultData.loggedInUserId;
						}
						);
						oSubListModel.refresh();
						sap.ui.getCore().byId("idDelegateSearch").bindAggregation("suggestionItems", {
							path: "oSubListModel>/userDtos",
							template: new sap.ui.core.ListItem({
								text: "{oSubListModel>firstName} {oSubListModel>lastName}  - {oSubListModel>loginId}"
							})
						});
						that.fnMultiFilters(value);
					});
					that.busy.close();
				} else {
					that.fnMultiFilters(value);
				}
			}
		},
		
		fnMultiFilters: function(value){
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
			sap.ui.getCore().byId("idDelegateSearch").getBinding("suggestionItems").filter(aFilters);
		},

		onUserSelect:function(oEvent){
			//var index=oEvent.getParameter("listItem").getBindingContextPath().split("/")[2];
			var that=this;
			that.busy.open();
			if (this.selectedId != sap.ui.getCore().byId("idDelegateSearch").getValue()) {
				toastMessage("Select a user from suggestions"); 
				sap.ui.getCore().byId("idDelegateSearch").setValue("");
				that.busy.close();
				return;
			}
			sap.m.MessageBox.show(that.getView().getModel("i18n").getResourceBundle().getText("NOMINATE_CONFIRMATION"),
					sap.m.MessageBox.Icon.QUESTION," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
					function(oEvt){
				if(oEvt==="NO"){
					that.busy.close();
					return;
				}
				else if(oEvt==="YES"){
					that.onDelegate();
				}
			},
			sap.m.MessageBox.Action.YES);
		},

		onSelectSuggestion: function(oEvent) {
			this.selectedId = oEvent.getParameters().selectedItem.getText();
		},

		onDelegate:function(){
			var that=this;
			var i18n = that.getView().getModel("i18n").getResourceBundle();
			//var selectedId=that.getView().getModel("oDelegateSearchModel").getData().ownersList[index].taskOwner;
			var selectedId = sap.ui.getCore().byId("idDelegateSearch").getValue().split(" ").pop();
			var userName = sap.ui.getCore().byId("idDelegateSearch").getValue().split("-")[0]; 
			var eventId = that.getView().getModel("oProcessTaskModel").getData().taskEventDtos[that.delegateIndex].eventId;
			var requestId = this.getView().getModel("oProcessDetailModel").getData().requestId;
			var instanceList = [{
				"eventId": eventId,
				"requestId": requestId
			}];
			var oParams ={
					"instanceList": instanceList,
					"userId": selectedId,
					"userDisplayName": userName
			};
			var oHeader = {
					"Content-Type" : "application/json; charset=utf-8"
			};
			var oDelegateTaskModel=new sap.ui.model.json.JSONModel();
			that.getView().setModel(oDelegateTaskModel, "oDelegateTaskModel");
			oDelegateTaskModel.loadData("/appone/pmc/workboxAction/nominate", JSON.stringify(oParams), true, "POST", false, false, oHeader);
			oDelegateTaskModel.refresh();
			oDelegateTaskModel.attachRequestCompleted(function(oEvent){
				that.busy.close();
				var delegateTaskModel=that.getView().getModel("oDelegateTaskModel");
				if (delegateTaskModel.getData().status == "SUCCESS"){
					that.onDelegateFragmentClose();
					sap.m.MessageBox.show(delegateTaskModel.getData().message+i18n.getText("BACK_MSG"),
							sap.m.MessageBox.Icon.SUCCESS," ",[sap.m.MessageBox.Action.OK],
							function(oEvt){
						if(oEvt==="OK"){
							that.onNavigatingBack();							}
					},
					sap.m.MessageBox.Action.YES);
				} else {
					toastMessage(i18n.getText("NOMINATE_FAILURE"));   
					that.onDelegateFragmentClose();
				}
				that.getView().getModel("oUserDefaultModel").getData().selectedUserForDelegate = "";
				that.getView().getModel("oUserDefaultModel").refresh();
			});
			oDelegateTaskModel.attachRequestFailed(function(oEvent) {
				that.busy.close();
				toastMessage(i18n.getText("NOMINATE_FAILURE"));   
			});
		},

		onCancelPress:function(oEvent){
			var that = this;
			if(that.getView().getModel("oProcessDetailModel").getData().status === "IN_PROGRESS"){
				sap.m.MessageBox.show(that.getView().getModel("i18n").getResourceBundle().getText("CANCEL_CONFIRMATION"),
						sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
						function(oEvt){
					if(oEvt==="NO"){
						return;
					}
					else if(oEvt==="YES"){
						that.onProcessCancel();
					}
				},
				sap.m.MessageBox.Action.YES);
			}
		},

		onE2EView: function() {
			var that = this;
			that.busy.open();
			if (!this._oE2EViewDialog) {
				this._oE2EViewDialog = sap.ui.xmlfragment("pmc.fragments.processFlowView", this);
				this.getView().addDependent(this._oE2EViewDialog);
			}
			this._oE2EViewDialog.open();
			var processName = this.getView().getModel("oProcessDetailModel").getData().name;
			var processDisplayName= this.getView().getModel("oProcessDetailModel").getData().processDisplayName;
			var url ="/appone/pmc/e2e/view/"+processName;
			var oEndToEndFlowModel = new sap.ui.model.json.JSONModel();
			that.getView().setModel(oEndToEndFlowModel, "oEndToEndFlowModel");
			oEndToEndFlowModel.loadData(url, null,true);
			oEndToEndFlowModel.attachRequestCompleted(function(oEvent) {
				that.busy.close();
				var endToEndFlowModel = that.getView().getModel("oEndToEndFlowModel");
				if (endToEndFlowModel.getData()) {
					endToEndFlowModel.setProperty("/processName", processName);
					endToEndFlowModel.setProperty("/processDisplayName", processDisplayName);
				} else {
					toastMessage(i18n.getText("NO_RESULTS_FOUND"));
				}
			});
			oEndToEndFlowModel.attachRequestFailed(function(oEvent) {
				that.busy.close();
				toastMessage(i18n.getText("NO_RESULTS_FOUND"));
			});
		},

		onClosingTheE2Eview: function() {
			this._oE2EViewDialog.close();
		},

		onProcessCancel: function(){
			var that = this;
			var i18n = that.getView().getModel("i18n").getResourceBundle();
			that.busy.open();
			var oProcessLoadModel = new sap.ui.model.json.JSONModel();
			that.getView().setModel(oProcessLoadModel, "oProcessLoadModel");
			var oHeader = {
					"Content-Type" : "application/json;charset=utf-8"
			};
			var aData = {
					"processInstanceList": [oUserDefaultModel.getData().processId]
			};
			oProcessLoadModel.loadData("/appone/pmc/processAction/cancel",JSON.stringify(aData), true, "POST",false, false, oHeader);
			oProcessLoadModel.attachRequestCompleted(function(oEvent) {
				that.busy.close();
				var processLoadModel=that.getView().getModel("oProcessLoadModel");
				var processLoadData = processLoadModel.getData();
				if (processLoadData && processLoadData.statusCode!="1"){
					toastMessage(processLoadData.message);  

					sap.m.MessageBox.show(that.getView().getModel("i18n").getResourceBundle().getText("CANCEL_PROCESS_SUCCESS"),
							sap.m.MessageBox.Icon.SUCCESS," ",[sap.m.MessageBox.Action.OK],
							function(oEvt){
						if(oEvt==="OK"){
							that.onNavigatingBack();							}
					},
					sap.m.MessageBox.Action.YES);
				}

				else {
					toastMessage(i18n.getText("CANCEL_FAILURE"));   
				}
			});
			oProcessLoadModel.attachRequestFailed(function(oEvent) {
				that.busy.close();
				toastMessage(i18n.getText("CANCEL_FAILURE"));   
			});
		},

		onRelease: function(oEvent){
			var that = this;
			var i18n = that.getView().getModel("i18n").getResourceBundle();
			that.busy.open();
			var index=oEvent.getSource().sId.split("-")[2];
			var oReleaseModel = new sap.ui.model.json.JSONModel();
			var eventId=that.getView().getModel("oProcessTaskModel").getData().taskEventDtos[index].eventId;
			this.getView().setModel(oReleaseModel, "oReleaseModel");
			var oHeader = {
					"Content-Type" : "application/json;charset=utf-8"
			};
			var aData = {
					"taskInstanceId": eventId
			};
			oReleaseModel.loadData("/appone/pmc/workboxAction/release",JSON.stringify(aData), true, "POST",false, false, oHeader);
			oReleaseModel.attachRequestCompleted(function(oEvent) {
				that.busy.close();
				var releaseModel=that.getView().getModel("oReleaseModel");
				if (releaseModel.getData()){
					toastMessage(releaseModel.getData().message);               
				} else {
					toastMessage(i18n.getText("RELEASE_FAILURE"));   
				}
			});
			oReleaseModel.attachRequestFailed(function(oEvent) {
				that.busy.close();
				toastMessage(i18n.getText("RELEASE_FAILURE"));   
			});
		},
		
		loadMessageData : function() {
			var that=this;
			var processId = oUserDefaultModel.getData().processId;
			var oMessageModel = new sap.ui.model.json.JSONModel();
			this.getView().setModel(oMessageModel, "oMessageModel");
			oMessageModel.loadData("/appone/pmc/collaboration/getMessageDetails?processId="+processId,null,true);
			oMessageModel.attachRequestCompleted(function(oEvent) {	
				var oMessageModel = that.getView().getModel("oMessageModel");	
				var oMessageTaskData = oMessageModel.getData();
				if(!(jQuery.isEmptyObject(oMessageTaskData.responseDtos.messageDtos))) {
				if (oMessageTaskData) {
					if (!(oMessageTaskData.responseDtos.messageDtos instanceof Array)) {
						oMessageTaskData.responseDtos.messageDtos = [oMessageTaskData.responseDtos.messageDtos];
					}	
				}
				}
				oMessageModel.refresh();
			});
			oMessageModel.attachRequestFailed(function(oEvent) {
				toastMessage(that.getView().getModel("oMessageModel").getData().responseMessage.message);
			});
		},
		
		onAddMessage : function() {
			var that = this;
			var oMessageData = this.getView().byId("idProcessTextMessage").getValue();
			if (oMessageData) {
				var oHeader = {
						"Content-Type" : "application/json;charset=utf-8"
				};
				var oMessageModel = new sap.ui.model.json.JSONModel();
				this.getView().setModel(oMessageModel, "oMessageModel");
				var loggedInId = oUserDefaultModel.getData().loggedInUserId;
				var loggedInUser = oUserDefaultModel.getData().loggedInUser;
				var processId = oUserDefaultModel.getData().processId;
				var textMessage = oMessageData;
				var taggedUserId = [];
				for(var i = 0 ; i < this.collaborationTagKey.length; i++)
					{
					for(var j = i+1 ; j< this.collaborationTagKey.length; j++)
						{
						if(this.collaborationTagKey[i].userId === this.collaborationTagKey[j].userId) {
							this.collaborationTagKey.splice(j,1);
						}
						}
					}
				for(var i = 0 ; i < this.collaborationTagKey.length ; i++) {
					if(textMessage.includes(this.collaborationTagKey[i].displayName)) {
						taggedUserId.push(this.collaborationTagKey[i].userId);
					}
				}
				var aData = {
					 "processId":processId,
					 "eventId":"",	
					 "message":oMessageData,
					 "userId":loggedInId,
					 "userDisplayName" :loggedInUser,
					 "taggedUserId" : taggedUserId
					 }
				oMessageModel.loadData("/appone/pmc/collaboration/createRecord",JSON.stringify(aData), true, "POST",false, false, oHeader);
				oMessageModel.attachRequestCompleted(function(oEvent) {
					that.loadMessageData();
					that.collaborationTagKey = [];
				});
				oMessageModel.attachRequestFailed(function(oEvent) {
					toastMessage(that.getView().getModel("oMessageModel").getData().message);
				});
			} else {
				toastMessage("Please add a message");
			}
		},
		
		onChangeTagSugg: function(oEvent) {
			var that = this;
			var oUserDefaultData = oUserDefaultModel.getData();
			var textMessage = oEvent.getSource().getValue();
			if (textMessage.split("@").length > 1) {
				var tagIndex = textMessage.split("@").pop();
				if (tagIndex && tagIndex.split(" ").length == 1 && tagIndex.length > 2) {
					var oTagListModel = new sap.ui.model.json.JSONModel();
					that.getView().setModel(oTagListModel, "oTagListModel");
					if (jQuery.isEmptyObject(oTagListModel.getData())) {
						oTagListModel.loadData("/pmc/poadapter/ume/searchUser/*",null,true);
						oTagListModel.attachRequestCompleted(function(oEvent) {
							var data = oTagListModel.getData();
							if (!(data.userDto instanceof Array)) {
								data.userDto = [data.userDto];
							}
							oTagListModel.setSizeLimit(data.userDto.length);
							data.userDto = data.userDto.filter(function(e) {
								return e.loginId != oUserDefaultData.loggedInUserId;
							}
							);
							oTagListModel.refresh();
						});
					}				
					var idMessage = this.getView().byId("idProcessTextMessage");
					idMessage.bindAggregation("suggestionItems","/userDto",new sap.ui.core.Item({
						text:"{firstName} {lastName}",
						key:"{loginId}"
					}));
					idMessage.setModel(oTagListModel);
					var aFilters = [];
					if (tagIndex) {
						var oFilter1 = new sap.ui.model.Filter("firstName", sap.ui.model.FilterOperator.Contains, tagIndex);
						var oFilter2 = new sap.ui.model.Filter("lastName", sap.ui.model.FilterOperator.Contains, tagIndex);
						//var oFilter3 = new sap.ui.model.Filter("loginId", sap.ui.model.FilterOperator.Contains, tagIndex);
						aFilters = new sap.ui.model.Filter({
							filters: [oFilter1,oFilter2],
							and: false
						});
					}
					oEvent.getSource().getBinding("suggestionItems").filter(aFilters);
					oEvent.getSource().setShowSuggestion(true);
				} else {
					oEvent.getSource().setShowSuggestion(false);
					oEvent.getSource().setShowSuggestion(true);
				}
			} else {
				oEvent.getSource().setShowSuggestion(false);
				oEvent.getSource().setShowSuggestion(true);
			}
			this.getView().byId("idProcessTextMessage").attachBrowserEvent("keydown keyup keypress",
					function(oEvent) {
				var keyCode = oEvent.keyCode;
				if(keyCode == 40 || keyCode == 38) {
			this.setValue(this.value);
				} else if(keyCode == 8) {
					this.value = this.getValue();
				} else if(oEvent.key.length == 1 ) {
					this.value = this.getValue();
					that.currentText = this.getValue();
				}
			});
			},
			
		onSuggestionItemSelected: function(oEvent){
			var collaborationObj = {};
			var text = oEvent.getSource().getValue();
			var selectedSuggestion = oEvent.getParameter("selectedItem").getText();
			var suggestionKey = oEvent.getParameter("selectedItem").getKey();
			collaborationObj.displayName = selectedSuggestion;
			collaborationObj.userId = suggestionKey;
			this.collaborationTagKey.push(collaborationObj);
			var array = text.split("@");
			array.pop();
			text = array.join("@");
			text += "@";
			oEvent.getSource().setValue(text + selectedSuggestion);
			},

		/**
		 * Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
		 * (NOT before the first rendering! onInit() is used for that one!).
		 * @memberOf pmc_ui.processFlow
		 */
//		onBeforeRendering: function() {

//		},

		/**
		 * Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
		 * This hook is the same one that SAPUI5 controls get after being rendered.
		 * @memberOf pmc_ui.processFlow
		 */
		onAfterRendering: function() {
		
			/*this.getView().byId("processTaskBox").attachBrowserEvent("click", 
				function(oEvent) {
					var status = this.getBindingContext('oProcessTaskModel').getObject().status;
					if(status && status != "COMPLETED"){
						var eventId = this.getBindingContext('oProcessTaskModel').getObject().eventId;
						var eventArray = eventId.split('/');
						var taskId = eventArray[eventArray.length - 1];
						window.open("/testui/index.html")
					}
				}
		);*/
		}

		/**
		 * Called when the Controller is destroyed. Use this one to free resources and finalize activities.
		 * @memberOf pmc_ui.processFlow
		 */
//		onExit: function() {

//		}
	});
});