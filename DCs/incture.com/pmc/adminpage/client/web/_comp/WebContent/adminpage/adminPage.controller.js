sap.ui.define([
               "sap/m/MessageBox",
               "sap/ui/core/mvc/Controller"
               ], function(MessageBox,Controller) {
	return Controller.extend("com.incture.adminpage.adminPage", {

		/**
		 * Called when a controller is instantiated and its View controls (if available) are already created.
		 * Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
		 * @memberOf adminpage.adminPage
		 */
		onInit: function() {
			var oConfigModel = new sap.ui.model.json.JSONModel();
			this.getView().setModel(oConfigModel,"oConfigModel");
			var oPageModel = new sap.ui.model.json.JSONModel();
			this.getView().setModel(oPageModel,"oPageModel");
			this.fnProcessNames();
			this.getData();
		},

		getData: function(){
			var that = this;
			var oView = that.getView();
			var oInitialModel = new sap.ui.model.json.JSONModel();
			oView.setModel(oInitialModel, "oInitialModel");
			oInitialModel.loadData("/appone/pmc/admin/configurations",null,true);
			oInitialModel.attachRequestCompleted(function(oEvent) {
				var initialModel=oView.getModel("oInitialModel");
				var initialData = initialModel.getData();
				if (initialData){
					if (!initialData.processConfigDtos) {
						initialData.processConfigDtos = [{
							processName:"",
							processDisplayName:"",
							labelName:"",
							userGroupList:[],
							userGroup:"",
							roleList:[],
							roleGroup:"",
							processConfigId:1
						}];
					}
					if (!initialData.workloadRangeDtos) {
						initialData.workloadRangeDtos = [{
							"loadType": "HIGH",
							"lowLimit": ""
						}, {
							"highLimit": "",
							"loadType": "MEDIUM",
							"lowLimit": ""
						}, {
							"highLimit": "",
							"loadType": "LOW",
							"lowLimit": "0"
						}];
					}
					var processConfigDtos = initialData.processConfigDtos;
					var workloadRangeDtos = initialData.workloadRangeDtos;
					var agingReportConfigDtos = initialData.agingReportConfigDtos;
					var agingRangeConfigDto = initialData.agingRangeConfigDto;
					if (!(agingRangeConfigDto instanceof Array)) {
						agingRangeConfigDto = [agingRangeConfigDto];
					}
					if (!(agingReportConfigDtos instanceof Array)) {
						agingReportConfigDtos = [agingReportConfigDtos];
					}
					if (!(processConfigDtos instanceof Array)) {
						processConfigDtos = [processConfigDtos];
					}
					if (!(workloadRangeDtos instanceof Array)) {
						workloadRangeDtos = [workloadRangeDtos];
					}
					for (var i=0; i<processConfigDtos.length; i++) {
						if (processConfigDtos[i]) {
							if (processConfigDtos[i].userGroup) {
								processConfigDtos[i].userGroupList = processConfigDtos[i].userGroup.split(",");
							}
							if (processConfigDtos[i].roleGroup) {
								processConfigDtos[i].roleList = processConfigDtos[i].roleGroup.split(",");
							}
						}
					}
					var processCount = parseInt(initialData.processCount);
					var taskCount = parseInt(initialData.taskCount);
					var taskStatusCount = parseInt(initialData.taskStatusCount);
					initialModel.refresh();
					/*var pages = ["Process Name Configuration","Workload Configuration","Process Ageing Range Configuration","Task Ageing Range Configuration","Task Status Graph Range Configuration"];*/
					var pages = ["Process Name Configuration","Workload Configuration","Process Ageing Range Configuration","Task Ageing Range Configuration","Task Status Graph Range Configuration"];
					var pageData = [];
					for (var i=0;i<pages.length;i++) {
						var obj = {};
						obj.name = pages[i];
						if(i == 0) {
							obj.type = "Accept";
						} else {
							obj.type = "Reject";
						}
						if (pages[i] == "Process Name Configuration") {
							obj.key = "pnc";
						}else if (pages[i] == "Workload Configuration") {
							obj.key = "wc";
						}else if (pages[i] == "Process Ageing Range Configuration") {
							obj.key = "parc";
						}else if (pages[i] == "Task Ageing Range Configuration") {
							obj.key = "tarc";
						}else if (pages[i] == "Task Status Graph Range Configuration") {
							obj.key = "tsgc";
						}
						pageData.push(obj);
					}
					var data={
							processConfigDtos:processConfigDtos,
							countDetails:{
								hmax : "infinity",
								hmin : workloadRangeDtos[0].lowLimit,
								nmax : workloadRangeDtos[1].highLimit,
								nmin : workloadRangeDtos[1].lowLimit,
								lmax : workloadRangeDtos[2].highLimit,
								lmin : workloadRangeDtos[2].lowLimit
							},
							agingReportConfigDtos:agingReportConfigDtos,
							agingRangeConfigDto:agingRangeConfigDto,
							processCount:processCount,
							taskCount:taskCount,
							taskStatusCount:taskStatusCount
					};
					oView.getModel("oPageModel").setData({roleBasedPages:pageData});
					oView.getModel("oPageModel").refresh();
					oView.getModel("oConfigModel").setData(data);
					oView.getModel("oConfigModel").refresh();
				}
			});
			oInitialModel.attachRequestFailed(function(oEvent) {
				that.busy.close();
				toastMessage(oView.getModel("oInitialModel").getData().message.message);
			});
		},

		onAddProcessNamePress: function(){
			var configModel = this.getView().getModel("oConfigModel");
			var data = configModel.getData().processConfigDtos;
			data.push({
				processName:"",
				processDisplayName:"",
				labelName:"",
				userGroupList:[],
				userGroup:"",
				roleList:[],
				roleGroup:"",
				processConfigId:data[data.length-1].processConfigId+1
			});
			configModel.refresh();
		},

		onProcessNameSelection:function(oEvent){
			var configModel = this.getView().getModel("oConfigModel");
			var data = configModel.getData().processConfigDtos;
			var count=0;
			if (oEvent.getSource().getSelectedKey() == "") {
				return;
			}
			for (var i=0;i<data.length;i++) {
				if (data[i].processName == oEvent.getSource().getSelectedKey()){
					count++;
				}
				if (count >= 2) { 
					sap.m.MessageToast.show("Process Name can be configured only once");
					oEvent.getSource().setSelectedKey("");
					return;
				}
			}
		},

		onSavePress: function(){
			var that = this;
			if (that.validation() == true) {
				MessageBox.show("Are you sure you want to save the all configurations?",
						MessageBox.Icon.QUESTION," ",[MessageBox.Action.YES, MessageBox.Action.NO],
						function(oEvt){
					if(oEvt==="NO"){
						return;
					}
					else if(oEvt==="YES"){
						that.onSubmit(that);
					}
				},
				MessageBox.Action.YES);
			}
		},

		validation: function(){
			var configModel = this.getView().getModel("oConfigModel").getData();
			//Process Name 
			var processConfigDtos = configModel.processConfigDtos;
			for(var i = 0; i < processConfigDtos.length; i++){
				if (processConfigDtos[i].processName == ""){
					sap.m.MessageToast.show("Fill all the entries in Process Name Configuration");
					return;
				}
			}
			//Workload Count
			var countDetails = configModel.countDetails;
			if (countDetails.hmin == "" || countDetails.nmax == "" || countDetails.nmin == "" || countDetails.lmax == "") {
				sap.m.MessageToast.show("Enter all workload counts");
				return;
			}
			if (parseInt(countDetails.nmax) < parseInt(countDetails.nmin)) {
				sap.m.MessageToast.show("Normal maximum count cannot be less than Normal minimum count");
				return;
			}
			//Common Range Validation function
			var agingReportConfigDtos = configModel.agingReportConfigDtos;
			var pHigherSegment, tHigherSegment;
			for(var i = 0; i < agingReportConfigDtos.length; i++){
				if (parseInt(agingReportConfigDtos[i].id) % 100 == 1) {
					if (agingReportConfigDtos[i].lowerSegment != "0") {
						sap.m.MessageToast.show("Ranges should start from zero in "+agingReportConfigDtos[i].reportName);
						return;
					}
				}
				if (agingReportConfigDtos[i].lowerSegment == "" || agingReportConfigDtos[i].higherSegment == "") {
					sap.m.MessageToast.show("Fill all range values of "+agingReportConfigDtos[i].reportName);
					return;
				}
				if (parseInt(agingReportConfigDtos[i].lowerSegment) > parseInt(agingReportConfigDtos[i].higherSegment)) {
					sap.m.MessageToast.show("Range From cannot be greater than Range To in " + agingReportConfigDtos[i].reportName);
					return;
				}
				if (agingReportConfigDtos[i].reportName == "process aging") {
					pHigherSegment = agingReportConfigDtos[i].higherSegment;
					if (!paHigher) {
						var paHigher = agingReportConfigDtos[i].higherSegment;
					} else {
						if (parseInt(paHigher)+1 != agingReportConfigDtos[i].lowerSegment) {
							sap.m.MessageToast.show("Range From should be next number of Range To of previous range in "  + agingReportConfigDtos[i].reportName );
							return;
						}
					}
					paHigher = agingReportConfigDtos[i].higherSegment;
				} else if (agingReportConfigDtos[i].reportName == "task Status Graph") {
					tHigherSegment = agingReportConfigDtos[i].higherSegment;
					if (!tsgHigher) {
						var tsgHigher = agingReportConfigDtos[i].higherSegment;
					} else {
						if (parseInt(tsgHigher)+1 != agingReportConfigDtos[i].lowerSegment) {
							sap.m.MessageToast.show("Range From should be next number of Range To of previous range in "  + agingReportConfigDtos[i].reportName );
							return;
						}
					}
					tsgHigher = agingReportConfigDtos[i].higherSegment;
				} else {
					if (!taHigher) {
						var taHigher = agingReportConfigDtos[i].higherSegment;
					} else {
						if (parseInt(taHigher)+1 != agingReportConfigDtos[i].lowerSegment) {
							sap.m.MessageToast.show("Range From should be next number of Range To of previous range in "  + agingReportConfigDtos[i].reportName );
							return;
						}
					}
					taHigher = agingReportConfigDtos[i].higherSegment;
				}
			}
			var agingRangeConfigDto = configModel.agingRangeConfigDto;
			for(var i = 0; i < agingRangeConfigDto.length; i++) {
				if (agingRangeConfigDto[i].agingRange == "") {
					sap.m.MessageToast.show("Fill the Header range");
					return;
				}
				if (agingRangeConfigDto[i].reportName == "process aging range") {
					if (agingRangeConfigDto[i].agingRange != pHigherSegment) {
						sap.m.MessageToast.show("Ranges should match the header range");
						return;
					}
				} else {
					if (agingRangeConfigDto[i].agingRange != tHigherSegment) {
						sap.m.MessageToast.show("Ranges should match the header range");
						return;
					}
				}
			}
			return true;
		},

		fnProcessNames: function(){
			var that = this;
			var oView = that.getView();
			var oProcessNameModel = new sap.ui.model.json.JSONModel();
			oView.setModel(oProcessNameModel, "oProcessNameModel");
			oProcessNameModel.loadData("/appone/pmc/config/labels",null,true);
			oProcessNameModel.attachRequestCompleted(function(oEvent) {
				var processNameModel = oView.getModel("oProcessNameModel");
				var processNameData =  processNameModel.getData();
				if (processNameData){
					if (!(processNameData.processConfigDto instanceof Array)) {
						processNameData.processConfigDto = [processNameData.processConfigDto];
					}
					//that.getGroupNames(processNameData.processConfigDto[0].userList);
					that.getGroupsRoles();
					processNameData.processConfigDto.unshift({processName:""}); 
					processNameModel.refresh();
				}
			});
		},

		getGroupsRoles: function(){
			var that = this;
			var oUserGroupModel = new sap.ui.model.json.JSONModel();
			that.getView().setModel(oUserGroupModel, "oUserGroupModel");
			oUserGroupModel.loadData("/appone/pmc/user/groups",null,true);
			oUserGroupModel.attachRequestCompleted(function(oEvent) {
				var userGroupDto = oUserGroupModel.getData().userGroupDto;
				if (!(userGroupDto instanceof Array)) {
					userGroupDto = [userGroupDto];
				}
				oUserGroupModel.setProperty("/userGroupDto", userGroupDto);
			});
			var oRoleModel = new sap.ui.model.json.JSONModel();
			that.getView().setModel(oRoleModel, "oRoleModel");
			oRoleModel.loadData("/appone/pmc/user/roles",null,true);
			oRoleModel.attachRequestCompleted(function(oEvent) {
				var roleInfoDto = oRoleModel.getData().roleInfoDto;
				if (!(roleInfoDto instanceof Array)) {
					roleInfoDto = [roleInfoDto];
				}
				that.getView().getModel("oRoleModel").refresh();
			});
		},

		onUserGroupSelect: function(oEvent){
			var oConfigModel = this.getView().getModel("oConfigModel");
			var oConfigObject = oEvent.getSource().getBindingContext('oConfigModel').getObject();
			var userGroupList = oConfigObject.userGroupList;
			oConfigObject.userGroup = "";
			for (var i = 0; i < userGroupList.length;i++){
				if (i > 0) {
					oConfigObject.userGroup += ","
				}
				oConfigObject.userGroup += userGroupList[i];
			}
		},
		
		onRoleSelect: function(oEvent){
			var oConfigModel = this.getView().getModel("oConfigModel");
			var oConfigObject = oEvent.getSource().getBindingContext('oConfigModel').getObject();
			var roleList = oConfigObject.roleList;
			oConfigObject.roleGroup = "";
			for (var i = 0; i < roleList.length;i++){
				if (i > 0) {
					oConfigObject.roleGroup += ","
				}
				oConfigObject.roleGroup += roleList[i];
			}
		},
		
		onDeletePress : function(oEvent){
			var that = this;
			var index = oEvent.getSource().getBindingContext('oConfigModel').sPath.split("/")[2];
			var processConfigDtos = this.getView().getModel("oConfigModel").getData().processConfigDtos;
			MessageBox.show("Are you sure you want to delete the process configuration?",
					MessageBox.Icon.QUESTION," ",[MessageBox.Action.YES, MessageBox.Action.NO],
					function(oEvt){
				if(oEvt==="NO"){
					return;
				}
				else if(oEvt==="YES"){
					var oDeleteModel = new sap.ui.model.json.JSONModel();
					that.getView().setModel(oDeleteModel,"oDeleteModel");
					oDeleteModel.loadData("/appone/pmc/admin/delete/" + processConfigDtos[index].processName ,null,true, "POST",false, false);
					oDeleteModel.attachRequestCompleted(function(oEvent) {
						processConfigDtos.splice(index,1);
						that.getView().getModel("oConfigModel").refresh();
					});
					oDeleteModel.attachRequestFailed(function(oEvent) {
						//req failed
					});
				}
			},
			MessageBox.Action.YES);
		},

		isNumber: function(oEvent){
			var input = oEvent.getSource().getValue();
			var regex = "^[1-9]([0-9]*)$";
			if(input){
				if(!input.match(regex)) {
					if (input.length < 2){
						oEvent.getSource()._lastValue = "";
						oEvent.getSource().setValue("");
					} else {
						input = oEvent.getSource()._lastValue;
						oEvent.getSource().setValue(input);
					}
				} else{
					oEvent.getSource()._lastValue = input;
				}
				return input;
			}
			else {
				return "";
			}
		},

		countAutoPopulate:function(oEvent){
			var input = oEvent.getSource().getValue();
			var name=oEvent.getSource().getName();
			var countDetails = this.getView().getModel("oConfigModel").getData().countDetails;
			if (input) {
				input = this.isNumber(oEvent);
				if (parseInt(input) > 0) {
					if (name == "hmin"){
						if (parseInt(input)-1 > 0) {
							countDetails.hmin = input;
							countDetails.nmax = parseInt(input)-1;
						} else {
							sap.m.MessageToast.show("Count should be greater than one");
							oEvent.getSource().setValue("");
						}
					} else if (name == "nmax"){
						countDetails.nmax = input;
						countDetails.hmin = parseInt(input)+1;
					} else if (name == "nmin"){
						if (parseInt(input)-1 > 0) {
							countDetails.nmin = input;
							countDetails.lmax = parseInt(input)-1;
						} else {
							sap.m.MessageToast.show("Count should be greater than one");
							oEvent.getSource().setValue("");
						}
					} else if (name == "lmax"){
						countDetails.lmax = input;
						countDetails.nmin = parseInt(input)+1;
					}
				} else {
					sap.m.MessageToast.show("Count should be greater than zero");
				}
			} else {
				if (name == "hmin"){
					countDetails.nmax = "";
				} else if (name == "nmax"){
					countDetails.hmin = "";
				} else if (name == "nmin"){
					countDetails.lmax = "";
				} else if (name == "lmax"){
					countDetails.nmin = "";
				}
			}
			this.getView().getModel("oConfigModel").refresh();
		},

		rangeCheck: function(oEvent){
			var input = oEvent.getSource().getValue();
			var object = oEvent.getSource().getBindingContext('oConfigModel').getObject();
			var name = oEvent.getSource().getName();
			if (input) {
				input = this.isNumber(oEvent);
				if (parseInt(input) >= 0) {
					if (name == "lowerSegment") {
						object.lowerSegment = input;
					} else if (name == "higherSegment") {
						object.higherSegment = input;
					}
				}
			}
			object.displayName = object.lowerSegment + " - " + object.higherSegment;
			this.getView().getModel("oConfigModel").refresh();
		},

		onSubmit : function(that) {
			var configData = that.getView().getModel("oConfigModel").getData();
			var initialData = that.getView().getModel("oInitialModel").getData();
			initialData.processConfigDtos = configData.processConfigDtos;
			initialData.workloadRangeDtos[0].lowLimit = configData.countDetails.hmin;
			initialData.workloadRangeDtos[1].highLimit = configData.countDetails.nmax;
			initialData.workloadRangeDtos[1].lowLimit = configData.countDetails.nmin;
			initialData.workloadRangeDtos[2].highLimit = configData.countDetails.lmax;
			initialData.workloadRangeDtos[2].lowLimit = configData.countDetails.lmin;
			initialData.processCount = configData.processCount;
			initialData.taskCount = configData.taskCount;
			initialData.taskStatusCount = configData.taskStatusCount;
			initialData.agingReportConfigDtos = configData.agingReportConfigDtos;
			initialData.agingRangeConfigDto =  configData.agingRangeConfigDto;
			that.getView().getModel("oInitialModel").refresh();
			/*initialData.agingRangeConfigDto =  [{
				"agingRange": configData.processAgeingDto.rangeWithinDays,
				"reportId": "401",
				"reportName": "process aging range"
			}, {
				"agingRange": configData.taskGraphDto.rangeWithinDays,
				"reportId": "402",
				"reportName": "task Status Graph range"
			}];*/
			var oUpdateModel = new sap.ui.model.json.JSONModel();
			this.getView().setModel(oUpdateModel,"oUpdateModel");
			var oHeader = {
					"Content-Type" : "application/json;charset=utf-8"
			};
			var aData = that.getView().getModel("oInitialModel").getData();
			oUpdateModel.loadData("/appone/pmc/admin/configurations",JSON.stringify(aData), true, "POST",false, false, oHeader);
			oUpdateModel.attachRequestCompleted(function(oEvent) {

			});
			oUpdateModel.attachRequestFailed(function(oEvent) {

			});
		},
		
		onSettings: function() {
			if (!this._oSettingDialog) {
				this._oSettingDialog = sap.ui.xmlfragment("com.incture.fragments.appSetting", this);
				this.getView().addDependent(this._oSettingDialog);
			}
			this._oSettingDialog.open();
		},
		
		onSkinSettingChange: function() {
			replaceTheChange();
		},
		
		onSettingClose: function() {
			this._oSettingDialog.close();
		},
		
		onRangeTableAddPress: function(oEvent){
			var oConfigModel = this.getView().getModel("oConfigModel");
			var oConfigData = oConfigModel.getData();
			var object = oEvent.getSource().getBindingContext('oPageModel').getObject();
			var data = oConfigData.agingReportConfigDtos;
			var lowerSegment,id,reportName;
			var key = object.key;
			if (key == "parc") {
				id = parseInt(oConfigData.processCount)+101;
				reportName = "process aging";
			} else if (key == "tarc") {
				id = parseInt(oConfigData.taskCount)+201;
				reportName = "task aging";
			} else if (key == "tsgc") {
				id = parseInt(oConfigData.taskStatusCount)+301;
				reportName = "task Status Graph";
			}
			for (var i = data.length-1; i>= 0;i--) {
				if (data[i].reportName == reportName) {
					if (data[i].higherSegment != "") {
						lowerSegment = parseInt(data[i].higherSegment)+1;
					} else {
						sap.m.MessageToast.show("Fill all range values");
						return;
					}
					if (data[i].lowerSegment == "") {
						sap.m.MessageToast.show("Fill all range values");
						return;
					}
					break;
				}
			}
			if (key == "parc") {
				oConfigData.processCount++;
			} else if (key == "tarc") {
				oConfigData.taskCount++;
			} else if (key == "tsgc") {
				oConfigData.taskStatusCount++;
			}
			data.push({
				id: id,
				lowerSegment: lowerSegment,
				higherSegment: "",
				displayName: "",
				reportName: reportName
			});
			this.getView().getModel("oConfigModel").refresh();
		},
			
		onPageChanged: function(oEvent){
			var currIndex = oEvent.getSource().getActivePage().split("-").pop();
			this.changePageModel(currIndex);
		},
		
		onPress: function(oEvent) {
			var index = oEvent.getSource().sId.split("-").pop();
			this.changePageModel(index);
			var carousel = this.getView().byId("idCarousel");
			var idArray = carousel.getActivePage().split("-");
			idArray.pop();
			var pageId = idArray.join("-")+"-"+index;
			carousel.setActivePage(pageId);
		},
		
		changePageModel: function(index){
			var data = this.getView().getModel("oPageModel").getData().roleBasedPages;
			for (var i=0; i<data.length; i++) {
				if(i == index) {
					data[i].type = "Accept";
				} else {
					data[i].type = "Reject";
				}
			}
			this.getView().getModel("oPageModel").refresh();
		},
		
		/**
		 * Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
		 * (NOT before the first rendering! onInit() is used for that one!).
		 * @memberOf adminpage.adminPage
		 */
//		onBeforeRendering: function() {

//		},

		/**
		 * Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
		 * This hook is the same one that SAPUI5 controls get after being rendered.
		 * @memberOf adminpage.adminPage
		 */
//		onAfterRendering: function() {

//		},

		/**
		 * Called when the Controller is destroyed. Use this one to free resources and finalize activities.
		 * @memberOf adminpage.adminPage
		 */
//		onExit: function() {

//		}

	});
});