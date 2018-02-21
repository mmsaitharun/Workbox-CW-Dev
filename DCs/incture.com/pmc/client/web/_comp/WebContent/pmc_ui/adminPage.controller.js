sap.ui.define([
               "sap/m/MessageBox",
               "sap/ui/core/mvc/Controller"
               ], function(MessageBox,Controller) {
	return Controller.extend("pmc_ui.adminPage", {
		/**
		 * Called when a controller is instantiated and its View controls (if available) are already created.
		 * Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
		 * @memberOf pmc_ui.adminPage
		 */
	onInit: function() {
		var that = this;
		var oView = that.getView();
		that.busy = new sap.m.BusyDialog();
		var oConfigModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oConfigModel,"oConfigModel");
		var oPageModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oPageModel,"oPageModel");
		oView.setModel(oUserDefaultModel,"oUserDefaultModel");
		that.oRouter = sap.ui.core.UIComponent.getRouterFor(this);
		that.oRouter.attachRoutePatternMatched(function(oEvent) {
			if (oEvent.getParameter("name") === "adminPage") {
				oUserDefaultModel.getData().userNavigateScreen = "adminPage";
				oUserDefaultModel.getData().previousScreen = "adminPage";
				oUserDefaultModel.getData().reportHeader = "SETTINGS";
				if (!oUserDefaultModel.getProperty("/toPersonalizeTab")){
					oUserDefaultModel.setProperty("/selectedConfig","authorization");
					oUserDefaultModel.setProperty("/selectedSubConfig","User Access");
					oUserDefaultModel.setProperty("/selectedTableType","User Access");
				}
				oUserDefaultModel.setProperty("/toPersonalizeTab",false);
				oUserDefaultModel.getData().deletedRanges = undefined;
				oUserDefaultModel.refresh(true);
				that.busy = new sap.m.BusyDialog();
				that.fnProcessNames();
				that.getGroupsRoles();
				oConfigModel.setData({});
				oConfigModel.refresh();
				that.getData();
			}
		});
	},
	
	//To get the data when page loads or reset
	getData: function(){
		var that = this;
		var oView = that.getView();
		this.busy.open();
		var oInitialModel = new sap.ui.model.json.JSONModel();
		oView.setModel(oInitialModel, "oInitialModel");
		oInitialModel.loadData("/appone/pmc/admin/configurations",null,true);
		oInitialModel.attachRequestCompleted(function(oEvent) {
			oUserDefaultModel.setProperty("/settingsChange", false);
			oUserDefaultModel.refresh();
			that.busy.close();
			var initialModel=oView.getModel("oInitialModel");
			var initialData = initialModel.getData();
			if (initialData){
				if (!initialData.processConfigDtos) {
					initialData.processConfigDtos = [];
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
				var reportDto = initialData.reportDto;
				if (!reportDto) {
					reportDto = [{
						"reportDtoList": [],
						"reportName": "PROCESS AGING"
					}, {
						"reportDtoList": [],
						"reportName": "TASK AGING"
					}, {
						"reportDtoList": [],
						"reportName": "TASK STATUS"
					}]
				}
				for (var j in reportDto) {
					var reportDtoList = reportDto[j].reportDtoList;
					if (reportDtoList && !(reportDtoList instanceof Array)) {
						reportDtoList = [reportDtoList];
					} else if(!reportDtoList) {
						reportDtoList = [];
					}
				} 
				var agingRangeConfigDto = initialData.agingRangeConfigDto;
				if (agingRangeConfigDto && !(agingRangeConfigDto instanceof Array)) {
					agingRangeConfigDto = [agingRangeConfigDto];
				} else if(!agingRangeConfigDto) {
					initialData.agingRangeConfigDto = [{
						"agingRange": "30",
						"reportName": "process aging range"
					},
					{
						"agingRange": "30",
						"reportName": "task Status Graph range"
					}];
					agingRangeConfigDto = initialData.agingRangeConfigDto;
				} else if (agingRangeConfigDto.length == 1) {
					if (agingRangeConfigDto[0].reportName == "process aging range") {
						var temp = {
							"agingRange": "30",
							"reportName": "task Status Graph range"
						};
						agingRangeConfigDto.push(temp);
					} else {
						var temp = {
								"agingRange": "30",
								"reportName": "process aging range"
						};
						agingRangeConfigDto.push(temp);
					}
				}
				
				if (reportDto && !(reportDto instanceof Array)) {
					reportDto = [reportDto];
				}
				if (processConfigDtos && !(processConfigDtos instanceof Array)) {
					processConfigDtos = [processConfigDtos];
				}
				processConfigDtos = processConfigDtos.filter(function(e) {
					return e.processName != "ALL";
				});
				if (workloadRangeDtos && !(workloadRangeDtos instanceof Array)) {
					workloadRangeDtos = [workloadRangeDtos];
				}
				var processAll;
				for (var i=0; i<processConfigDtos.length; i++) {
					if (processConfigDtos[i]) {
						if (processConfigDtos[i].userGroup) {
							processConfigDtos[i].userGroupList = processConfigDtos[i].userGroup.split(", ");
						}
						if (processConfigDtos[i].roleGroup) {
							processConfigDtos[i].roleList = processConfigDtos[i].roleGroup.split(", ");
						}
					}
					if (processConfigDtos[i].processName == "ALL") {
						processAll = processConfigDtos[i];
						processConfigDtos.splice(i,1);
						i--;
					}
				}
				if (processAll) {
					processConfigDtos.push(processAll);
				}
				var processCount = parseInt(initialData.processCount);
				var taskCount = parseInt(initialData.taskCount);
				var taskStatusCount = parseInt(initialData.taskStatusCount);
				initialModel.refresh();
				/*var pages = ["Process Name Configuration","Workload Configuration","Process Ageing Range Configuration","Task Ageing Range Configuration","Task Status Graph Range Configuration"];*/
				var pages = ["Process Detail","Workload","Process Ageing Range","Task Ageing Range","Task Status Graph Range"];
				var pages = ["Authorization","Configuration","Personalization"];
				var pageData = [];
				for (var i=0;i<pages.length;i++) {
					var obj = {};
					obj.name = pages[i];
					if (pages[i] == "Authorization") {
						obj.key = "authorization";
						obj.shortName = "Authorization";
					} else if (pages[i] == "Configuration") {
						obj.key = "configuration";
						obj.shortName = "Configuration";
					} else if (pages[i] == "Personalization") {
						obj.key = "personalization";
						obj.shortName = "Personalization";
					}
					pageData.push(obj);
				}
				var authConfig = [{
					"config": "User Access",
					"key": "authorization"
				}];
				var generalConfig = [{
					"config": "Process Detail",
					"key": "configuration"
				}, {
					"config": "Workload",
					"key": "configuration"
				},{
					"config": "Ranges",
					"key": "configuration"
				}];
				var personalConfig = [{
					"config": "User Profile",
					"key": "personalization"
				}];
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
						reportDto:reportDto,
						agingRangeConfigDto:agingRangeConfigDto,
						processCount:processCount,
						taskCount:taskCount,
						taskStatusCount:taskStatusCount,
						generalConfig:generalConfig,
						authConfig:authConfig,
						personalConfig:personalConfig
				};
				oView.getModel("oPageModel").setData({roleBasedPages:pageData});
				oView.getModel("oPageModel").refresh();
				oView.getModel("oConfigModel").setData(data);
				oView.getModel("oConfigModel").refresh();
				that.setLastDelete();
			} else {
				sap.m.MessageToast.show(oInitialModel.getData().message);
			}
		});
		oInitialModel.attachRequestFailed(function(oEvent) {
			that.busy.close();
			sap.m.MessageToast.show("Failed to load data");
		});
	},

	//Fn to add new process configuration
	onAddProcessNamePress: function(){
		if (this.validation() == true) {
			oUserDefaultModel.setProperty("/settingsChange", true);
			oUserDefaultModel.refresh();
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
				laneCount:"3",
				isNew: true
			});
			configModel.refresh();
		}
	},

	onProcessNameSelection:function(oEvent){
		oUserDefaultModel.setProperty("/settingsChange", true);
		oUserDefaultModel.refresh();
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
		if (that.validation(true) == true) {
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

	validation: function(isSave){
		var selectedSubConfig = oUserDefaultModel.getData().selectedSubConfig;
		var configModel = this.getView().getModel("oConfigModel").getData();
		//Process Name 
		if (selectedSubConfig == "Process Detail" || isSave) {
			var processConfigDtos = configModel.processConfigDtos;
			for(var i = 0; i < processConfigDtos.length; i++){
				if (processConfigDtos[i].processName == "" && processConfigDtos[i].isDeleted != true){
					sap.m.MessageToast.show("Fill all the entries in Process Detail");
					return;
				}
			}
		}
		//Workload Count
		if (selectedSubConfig == "Workload" || isSave) {
			var countDetails = configModel.countDetails;
			if (countDetails.hmin == "" || countDetails.nmax == "" || countDetails.nmin == "" || countDetails.lmax == "") {
				sap.m.MessageToast.show("Enter all workload counts");
				return;
			}
			if (parseInt(countDetails.nmax) < parseInt(countDetails.nmin)) {
				sap.m.MessageToast.show("Normal workload limit cannot be lesser than low workload limit");
				return;
			}
		}
		//Common Range Validation function
		if (selectedSubConfig == "Ranges" || isSave) {
			var reportDto = configModel.reportDto;
			var pHigherSegment, tHigherSegment, tsHigherSegment;
			var paCount = 0,taCount = 0,tsgCount = 0;
			for (var j = 0; j < reportDto.length; j++) {
				var reportDtoList = reportDto[j].reportDtoList;
				var reportName = reportDto[j].reportName;
				var count = 0;
				for (var i = 0; i < reportDtoList.length; i++) {
					if (count == 0) {
						count++;
						if (reportDtoList[i].lowerSegment != "0") {
							sap.m.MessageToast.show("Ranges should start from zero in " + reportDtoList[i].reportName);
							return;
						}
					}
					if (reportDtoList[i].lowerSegment == "" || reportDtoList[i].higherSegment == "") {
						sap.m.MessageToast.show("Fill all range values of " + reportDtoList[i].reportName);
						return;
					}
					if (parseInt(reportDtoList[i].lowerSegment) > parseInt(reportDtoList[i].higherSegment)) {
						sap.m.MessageToast.show("Range From cannot be greater than Range To in " + reportDtoList[i].reportName);
						return;
					}
					if (reportName == "PROCESS AGING") {
						pHigherSegment = reportDtoList[i].higherSegment;
					} else if (reportName == "TASK STATUS"){
						tHigherSegment = reportDtoList[i].higherSegment;
					}
					if (i !=0 && parseInt(reportDtoList[i-1].higherSegment) + 1 != parseInt(reportDtoList[i].lowerSegment)) {
						sap.m.MessageToast.show("Range From should be next number of Range To of previous range in "  + reportDtoList[i].reportName );
						return;
					}
				}
			}
			var agingRangeConfigDto = configModel.agingRangeConfigDto;
			for(var i = 0; i < agingRangeConfigDto.length; i++) {
				if (agingRangeConfigDto[i].agingRange == "") {
					sap.m.MessageToast.show("Fill the Header range in " + agingRangeConfigDto[i].reportName);
					return;
				}
				if (agingRangeConfigDto[i].reportName == "process aging range") {
					if (agingRangeConfigDto[i].agingRange != pHigherSegment) {
						sap.m.MessageToast.show("Ranges should match the header range in " + agingRangeConfigDto[i].reportName);
						return;
					}
				} else {
					if (agingRangeConfigDto[i].agingRange != tHigherSegment) {
						sap.m.MessageToast.show("Ranges should match the header range in " + agingRangeConfigDto[i].reportName);
						return;
					}
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
		oProcessNameModel.loadData("/appone/pmc/config/adminLabels",null,true);
		oProcessNameModel.attachRequestCompleted(function(oEvent) {
			var processNameModel = oView.getModel("oProcessNameModel");
			var processNameData =  processNameModel.getData();
			if (processNameData && processNameData.processConfigDtos){
				if (!(processNameData.processConfigDtos instanceof Array)) {
					processNameData.processConfigDtos = [processNameData.processConfigDtos];
				}
				processNameData.processConfigDtos = processNameData.processConfigDtos.filter(function(e) {
					return e.processName != "ALL";
				});
				processNameData.processConfigDtos.unshift({processName:""}); 
				processNameModel.refresh();
			} else {
				toastMessage(processNameData.responseMessage.message);
			}
		});
		oProcessNameModel.attachRequestFailed(function(oEvent) {
			this.busy.close();
			sap.m.MessageToast.show("Failed to load processs names");
		});
	},

	getGroupsRoles: function(){
		var that = this;
		var oUserGroupModel = new sap.ui.model.json.JSONModel();
		that.getView().setModel(oUserGroupModel, "oUserGroupModel");
		oUserGroupModel.loadData("/appone/pmc/user/groups",null,true);
		oUserGroupModel.attachRequestCompleted(function(oEvent) {
			var userGroupDtos = oUserGroupModel.getData().userGroupDtos;
			if (!(userGroupDtos instanceof Array)) {
				userGroupDtos = [userGroupDtos];
			}
			userGroupDtos = userGroupDtos.filter(function(e) {
				return e.groupName != "ALL";
			});
			oUserGroupModel.setProperty("/userGroupDtos", userGroupDtos);
		});
		oUserGroupModel.attachRequestFailed(function(oEvent) {
			this.busy.close();
			sap.m.MessageToast.show("Failed to load user groups");
		});
		var oRoleModel = new sap.ui.model.json.JSONModel();
		that.getView().setModel(oRoleModel, "oRoleModel");
		oRoleModel.loadData("/appone/pmc/user/roles",null,true);
		oRoleModel.attachRequestCompleted(function(oEvent) {
			var roleInfoDtos = oRoleModel.getData().roleInfoDtos;
			if (!(roleInfoDtos instanceof Array)) {
				roleInfoDtos = [roleInfoDtos];
			}
			that.getView().getModel("oRoleModel").refresh();
		});
		oRoleModel.attachRequestFailed(function(oEvent) {
			this.busy.close();
			sap.m.MessageToast.show("Failed to load roles");
		});
		var oAllUserModel = new sap.ui.model.json.JSONModel();
		that.getView().setModel(oAllUserModel, "oAllUserModel");
		oAllUserModel.loadData("/pmc/poadapter/ume/searchUser/*",null,true);
		oAllUserModel.attachRequestCompleted(function(oEvent) {
			var userDto = oAllUserModel.getData().userDto;
			if (!(userDto instanceof Array)) {
				userDto = [userDto];
			}
			that.getView().getModel("oAllUserModel").setSizeLimit(userDto.length);
			that.getView().getModel("oAllUserModel").refresh();
		});
		oAllUserModel.attachRequestFailed(function(oEvent) {
			this.busy.close();
			sap.m.MessageToast.show("Failed to load users");
		});
	},

	onUserGroupSelect: function(oEvent){
		var oConfigModel = this.getView().getModel("oConfigModel");
		var oConfigObject = this.configObj;
		var userGroupList = oConfigObject.userGroupList;
		oConfigObject.userGroup = "";
		for (var i = 0; i < userGroupList.length;i++){
			if (i > 0) {
				oConfigObject.userGroup += ", "
			}
			oConfigObject.userGroup += userGroupList[i];
		}
		oUserDefaultModel.setProperty("/settingsChange", true);
		oUserDefaultModel.refresh();
	},
	
	onRoleSelect: function(oEvent){
		var oConfigModel = this.getView().getModel("oConfigModel");
		var oConfigObject = oEvent.getSource().getBindingContext('oConfigModel').getObject();
		var roleList = oConfigObject.roleList;
		oConfigObject.roleGroup = "";
		for (var i = 0; i < roleList.length;i++){
			if (i > 0) {
				oConfigObject.roleGroup += ", "
			}
			oConfigObject.roleGroup += roleList[i];
		}
		oUserDefaultModel.setProperty("/settingsChange", true);
		oUserDefaultModel.refresh();
	},
	
	//on deleting one process configuration
	onDeletePress : function(oEvent){
		var that = this;
		var object = oEvent.getSource().getBindingContext('oConfigModel').getObject();
		var index = oEvent.getSource().getBindingContext('oConfigModel').sPath.split("/").pop();
		var processConfigDtos = that.getView().getModel("oConfigModel").getData().processConfigDtos;
		MessageBox.show("Are you sure you want to delete the process configuration?",
				MessageBox.Icon.QUESTION," ",[MessageBox.Action.YES, MessageBox.Action.NO],
				function(oEvt){
			if(oEvt==="NO"){
				return;
			}
			else if(oEvt==="YES"){
				/*var oDeleteModel = new sap.ui.model.json.JSONModel();
				that.getView().setModel(oDeleteModel,"oDeleteModel");
				oDeleteModel.loadData("/appone/pmc/admin/delete/" + processConfigDtos[index].processName ,null,true, "POST",false, false);
				oDeleteModel.attachRequestCompleted(function(oEvent) {
					processConfigDtos.splice(index,1);
					that.getView().getModel("oConfigModel").refresh();
					that.getData();
				});
				oDeleteModel.attachRequestFailed(function(oEvent) {
					sap.m.MessageToast.show("Failed to delete data");
				});*/
				object.isDeleted = true;
				/*if (object.isNew == true) {
					processConfigDtos.splice(index,1);
				}*/
				that.getView().getModel("oConfigModel").refresh();
				oUserDefaultModel.setProperty("/settingsChange", true);
				oUserDefaultModel.refresh();
			}
		},
		MessageBox.Action.YES);
	},

	//To check whether the input character is a positive integer
	isNumber: function(oEvent){
		oUserDefaultModel.setProperty("/settingsChange", true);
		oUserDefaultModel.refresh();
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

	//workload configuration validation
	countAutoPopulate: function(oEvent){
		oUserDefaultModel.setProperty("/settingsChange", true);
		oUserDefaultModel.refresh();
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

	//common ranges validation
	rangeCheck: function(oEvent){
		oUserDefaultModel.setProperty("/settingsChange", true);
		oUserDefaultModel.refresh();
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
		initialData.reportDto = configData.reportDto;
		if (oUserDefaultModel.getData().deletedRanges) {
			var deletedRanges = oUserDefaultModel.getData().deletedRanges;
			var reportDto = initialData.reportDto;
			for (var i in reportDto) {
				if (reportDto[i].reportName == "PROCESS AGING") {
					if(deletedRanges.process && deletedRanges.process.length > 0) {
						reportDto[i].reportDtoList = reportDto[i].reportDtoList.concat(deletedRanges.process);
					}
				} else if (reportDto[i].reportName == "TASK AGING") {
					if(deletedRanges.task && deletedRanges.task.length > 0) {
						reportDto[i].reportDtoList = reportDto[i].reportDtoList.concat(deletedRanges.task);
					}
				} else if (reportDto[i].reportName == "TASK STATUS") {
					if(deletedRanges.taskStatus && deletedRanges.taskStatus.length > 0) {
						reportDto[i].reportDtoList = reportDto[i].reportDtoList.concat(deletedRanges.taskStatus);
					}
				}
			}
		}
		initialData.agingRangeConfigDto =  configData.agingRangeConfigDto;
		that.getView().getModel("oInitialModel").refresh();
		var oUpdateModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(oUpdateModel,"oUpdateModel");
		var oHeader = {
				"Content-Type" : "application/json;charset=utf-8"
		};
		var aData = that.getView().getModel("oInitialModel").getData();
		oUpdateModel.loadData("/appone/pmc/admin/configurations",JSON.stringify(aData), true, "POST",false, false, oHeader);
		oUpdateModel.attachRequestCompleted(function(oEvent) {
			if (oEvent.getSource().getData().message == "SUCCESS") {
				sap.m.MessageToast.show("Configurations updated successsfully");
				that.getData();
				oUserDefaultModel.getData().deletedRanges = undefined;
				oUserDefaultModel.refresh();
			}
		});
		oUpdateModel.attachRequestFailed(function(oEvent) {
			sap.m.MessageToast.show("Failed to update configurations");
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
		var key = oEvent.getSource().getBindingContext("oConfigModel").getObject().reportName;
		var lowerSegment,id,reportName,rangeReport,range;
		if (key == "PROCESS AGING") {
			reportName = "process aging";
			rangeReport = "process aging range";
		} else if (key == "TASK AGING") {
			reportName = "task aging";
		} else if (key == "TASK STATUS") {
			reportName = "task Status Graph";
			rangeReport = "task Status Graph range";
		}
		for (var i = 0; i<oConfigData.agingRangeConfigDto.length; i++) {
			if (oConfigData.agingRangeConfigDto[i].reportName == rangeReport) {
				range = oConfigData.agingRangeConfigDto[i].agingRange;
				break;
			}
		}
		var data;
		var gData = oConfigData.reportDto;
		for (var i = 0; i < gData.length; i++) {
			if ( gData[i].reportName == key ) {
				data = gData[i].reportDtoList;
				if (data && data.length > 0) {
					if (range && i > 0 && parseInt(data[data.length-1].higherSegment) >= parseInt(range)) {
						sap.m.MessageToast.show("Range cannot exceed the limit selected");
						return;
					}
					for (var j = data.length-1; j>= 0;j--) {
						if (data[j].reportName == reportName) {
							if (data[j].higherSegment != "") {
								lowerSegment = parseInt(data[j].higherSegment)+1;
							} else {
								sap.m.MessageToast.show("Fill all range values");
								return;
							}
							if (data[j].lowerSegment == "") {
								sap.m.MessageToast.show("Fill all range values");
								return;
							}
							break;
						}
					}
				} else {
					data = [];
					gData[i].reportDtoList = data;
					lowerSegment = "0";
				}
			}
		}
		if (!lowerSegment) {
			lowerSegment = "0";
		}
		data.push({
			lowerSegment: lowerSegment,
			higherSegment: "",
			displayName: "",
			reportName: reportName,
			visible: true
		});
		oUserDefaultModel.setProperty("/settingsChange", true);
		oUserDefaultModel.refresh();
		this.getView().getModel("oConfigModel").refresh();
		this.setLastDelete();
	},
	
	//fn when tab changes
	onConfigSelection: function(oEvent) {
		var selectedConfig = oEvent.getSource().getSelectedKey();
		if(this.validation()) {
			var config = "";
			var subConfig = "";
			var selectedTableType = "";
			var filters = [];
			if (selectedConfig === "authorization") {
				config = "authorization";
				subConfig = "User Access";
				selectedTableType = "User Access";
			} else if (selectedConfig === "configuration") {
				config = "configuration";
				subConfig = "Process Detail";
				selectedTableType = "process";
			} else if (selectedConfig === "personalization") {
				config = "personalization";
				subConfig = "User Profile";
				selectedTableType = "User Profile";
			}
			oUserDefaultModel.setProperty("/selectedConfig",config);
			oUserDefaultModel.setProperty("/selectedSubConfig",subConfig);
			oUserDefaultModel.setProperty("/selectedTableType",selectedTableType);
		} else {
			var key = oUserDefaultModel.getProperty("/selectedSubConfig");
			if (key == "User Access") {
				key = "authorization";
			} else if (key == "Process Detail" || key == "Workload" ||  key == "Ranges" ) {
				key = "configuration";
			} else if (key == "User Profile"){
				key = "personalization";
			}
			oEvent.getSource().setSelectedKey(key);
		}
	},

	onReset: function(){
		var that = this;
		if (oUserDefaultModel.getData().settingsChange) {
			sap.m.MessageBox.show(that.getView().getModel("i18n").getResourceBundle().getText("RULES_WARNING"),
					sap.m.MessageBox.Icon.WARNING," ",[sap.m.MessageBox.Action.YES, sap.m.MessageBox.Action.NO],
					function(oEvt){
				if(oEvt==="NO"){
					return;
				}
				else if(oEvt==="YES"){
					that.reset(that);
				}
			},
			sap.m.MessageBox.Action.YES);
			return;
		} else {
			that.reset(that);
		}
	},
	
	reset: function(that) {
		that.getView().getModel("oConfigModel").setData({});
		that.getView().getModel("oConfigModel").refresh();
		that.callSettingsFunction();
		//oUserDefaultModel.getData().theme = "dark";
		oUserDefaultModel.getData().deletedRanges = undefined;
		oUserDefaultModel.refresh();
		that.getData();
	},
	
	//when ranges are edited
	onRangeChange: function(oEvent) {
		var value = oEvent.getSource().getValue();
		var currObject = oEvent.getSource().getBindingContext('oConfigModel').getObject();
		var report = currObject.reportName;
		if (currObject.isLast) {
			var agingRangeConfigDto = this.getView().getModel("oConfigModel").getData().agingRangeConfigDto;
			for (var i = 0; i< agingRangeConfigDto.length; i++) {
				if ((agingRangeConfigDto[i].reportName == "process aging range" && report == "process aging") || (agingRangeConfigDto[i].reportName == "task Status Graph range" && report == "task Status Graph")) {
					if (parseInt(value) > parseInt(agingRangeConfigDto[i].agingRange)) {
						sap.m.MessageToast.show("The range should not exceed the limit chosen in " + report);
						oEvent.getSource().setValue(currObject.higherSegment);
						return;
					}
				}
			}
		}
		this.rangeCheck(oEvent);
		var currIndex = parseInt(oEvent.getSource().getBindingContext('oConfigModel').sPath.split("/").pop());
		var reportDto = this.getView().getModel("oConfigModel").getData().reportDto;
		if (report == "process aging") {
			report = "PROCESS AGING";
		} else if (report == "task aging") {
			report = "TASK AGING";
		} else if (report == "task Status Graph") {
			report = "TASK STATUS";
		}
		for (var k = 0; k < reportDto.length; k++) {
			var reportName = reportDto[k].reportName;
			if (reportName == report) {
				var configData = reportDto[k].reportDtoList;
				if (configData.length - 1 != currIndex) {
					configData[currIndex+1].lowerSegment = parseInt(configData[currIndex].higherSegment) + 1;
					configData[currIndex+1].displayName = configData[currIndex+1].lowerSegment + " - " + configData[currIndex+1].higherSegment;
					break;
				}
			}
		}
		this.getView().getModel("oConfigModel").refresh();
	},
	
	/**
	 * Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
	 * (NOT before the first rendering! onInit() is used for that one!).
	 * @memberOf pmc_ui.adminPage
	 */
//	onBeforeRendering: function() {

//	},

	/**
	 * Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
	 * This hook is the same one that SAPUI5 controls get after being rendered.
	 * @memberOf pmc_ui.adminPage
	 */
	
	onAfterRendering: function() {
		var that = this;
		this.getView().byId("idAuthConfigItem").attachBrowserEvent("click", 
				function(oEvent) {
			if(that.validation()) {
				var configName = this.getBindingContext('oConfigModel').getObject().config;
				oUserDefaultModel.setProperty("/selectedSubConfig",configName);
				if (configName == "User Access") {
					subConfig = "User Access";
				} else {
					subConfig = "User Access";
				}
				oUserDefaultModel.setProperty("/selectedTableType",subConfig);
			}
		});
		this.getView().byId("idAgeingConfigItem").attachBrowserEvent("click", 
				function(oEvent) {
			if(that.validation()) {
				var subConfig = "";
				var configName = this.getBindingContext('oConfigModel').getObject().config;
				oUserDefaultModel.setProperty("/selectedSubConfig",configName);
				if (configName == "Process Detail") {
					subConfig = "process";
				} else if (configName == "Workload") {
					subConfig = "workload";
				} else if (configName == "Process") {
					subConfig = "process aging";
				} else if (configName == "Task") {
					subConfig = "task aging";
				} else if (configName == "Task Status") {
					subConfig = "task Status Graph";
				} else if (configName == "Ranges") {
					subConfig = "range";
				}
				oUserDefaultModel.setProperty("/selectedTableType",subConfig);
			}
		});
		this.getView().byId("idProfileListItem").attachBrowserEvent("click", 
				function(oEvent) {
			if(that.validation()) {
				var configName = this.getBindingContext('oConfigModel').getObject().config;
				oUserDefaultModel.setProperty("/selectedSubConfig",configName);
				oUserDefaultModel.setProperty("/selectedTableType","User Profile");
			}
		});
		this.getView().byId("idLightThemeBox").attachBrowserEvent("click", 
				function(oEvent) {
			this.addStyleClass("selectedThemeClass");
			this.getParent().getItems()[2].removeStyleClass("selectedThemeClass");
			oUserDefaultModel.setProperty("/selectedTheme","light");
			//oUserDefaultModel.setProperty("/settingsChange", true);
			oUserDefaultModel.refresh();
		});
		this.getView().byId("idDarkThemeBox").attachBrowserEvent("click", 
				function(oEvent) {
			this.addStyleClass("selectedThemeClass");
			this.getParent().getItems()[1].removeStyleClass("selectedThemeClass");
			oUserDefaultModel.setProperty("/selectedTheme","dark");
			//oUserDefaultModel.setProperty("/settingsChange", true);
			oUserDefaultModel.refresh();
		});
	},
	
	//on click of information icon
	onClickHelpLink: function(oEvent){
		var that = this;
		var configType = oUserDefaultModel.getProperty("/selectedTableType");
		var image = "";
		if (configType == "range"){
			configType = oEvent.getSource().getParent().getContent()[0].getText();
		}
		if (configType == "Process") {
			image = "images/processAgeingConfig.JPG";
		} else if (configType == "Task Status") {
			image = "images/taskStatusGraphConfig.JPG";
		} else if (configType == "Task") {
			image = "images/taskAgeingConfig.JPG";
		} else if (configType == "process") {
			image = "images/processConfig.JPG";
		} else {
			image = "images/workLoadLegendConfig.JPG";
		}
		oUserDefaultModel.setProperty("/confInfoImage", image);
		if (!that._oConfInfoDialog) {
			that._oConfInfoDialog = sap.ui.xmlfragment("pmc.fragments.configInfo", that);
			that.getView().addDependent(that._oConfInfoDialog);
		}
		that._oConfInfoDialog.openBy(oEvent.getSource());
		that._oConfInfoDialog.setModel(oUserDefaultModel, "oUserDefaultModel");
	},
	
	onClickSelectGroup: function(oEvent){
		this.busy.open();
		var configObj = oEvent.getSource().getBindingContext('oConfigModel').getObject();
		this.configObj = configObj;
		if (!this._oGroupList) {
			this._oGroupList = sap.ui.xmlfragment("pmc.fragments.groupList", this);
			this.getView().addDependent(this._oGroupList);
		}
		//this._oGroupList.fireSearch();
		this._oGroupList.open();
		var data = this.getView().getModel("oUserGroupModel").getData().userGroupDtos;
		var groupList = configObj.userGroupList;
		var lookup = {};
		for (var j in groupList) {
			lookup[groupList[j]] = groupList[j];
		}
		for (var i in data) {
			if (typeof lookup[data[i].groupName] != 'undefined') {
				data[i].isSelected=true;
				data.unshift(data.splice(i,1)[0]);
			} else {
				data[i].isSelected=false;
			}
		}  
		this.getView().getModel("oUserGroupModel").refresh();
		this.busy.close();
		oUserDefaultModel.setProperty("/settingsChange", true);
		oUserDefaultModel.refresh();
		//sap.ui.getCore().byId("idGrpTable").attachSelectionChange(this.onUserGroupSelect(oEvent));
	},
	
	onUserGrpClose: function(){
		this._oGroupList.close();
		this.busy.close();
	},
	
	onUserGrpSubmit: function(oEvent){
		this.busy.open();
		var oConfigModel = this.getView().getModel("oConfigModel");
		var oConfigObject = this.configObj;
		var userGroupList = oConfigObject.userGroupList;
		var userGroupList = [];
		var selectedItems = sap.ui.getCore().byId("idGrpList").getSelectedItems();
		for(var i in selectedItems) {
			userGroupList.push(selectedItems[i].getBindingContext("oUserGroupModel").getObject().groupName);
		}
		oConfigObject.userGroupList = userGroupList;
		oConfigObject.userGroup = "";
		for (var i = 0; i < userGroupList.length;i++){
			if (i > 0) {
				oConfigObject.userGroup += ", "
			}
			oConfigObject.userGroup += userGroupList[i];
		}
		this.getView().getModel("oConfigModel").refresh();
		this.onUserGrpClose();
		this.busy.close();
		oUserDefaultModel.setProperty("/settingsChange", true);
		oUserDefaultModel.refresh();
	},
	
	handleSearch: function(oEvent){
		var sValue = oEvent.getSource().getValue();
		if (!sValue) {
			sValue = "";
		}
		/*var title = oEvent.getSource().getTitle();
		if (title == "User Group") {
			var filterName = "groupName";
		} else if (title == "Selected Users") {
			var filterName = "userId";
		} else if (title == "User Role") {
			var filterName = "roleUniqName";
		} else if (title == "User Name") {
			var filterName = "userId";
		}*/
		var oFilters = [];
		if (sap.ui.getCore().byId("idGrpList").getVisible()) {
			var oSource = sap.ui.getCore().byId("idGrpList");
			oFilters = [new sap.ui.model.Filter("groupName", sap.ui.model.FilterOperator.Contains, sValue)];
		} else if (sap.ui.getCore().byId("idUsersList").getVisible()) {
			var oSource = sap.ui.getCore().byId("idUsersList");
			oFilters = [new sap.ui.model.Filter("userId", sap.ui.model.FilterOperator.Contains, sValue)];
		}
		/*var oFilter = new sap.ui.model.Filter(filterName, sap.ui.model.FilterOperator.Contains, sValue);
		var oBinding = oEvent.getSource().getBinding("items");
		oBinding.filter([oFilter]);*/
		var oBinding = oSource.getBinding("items");
		oBinding.filter(oFilters);
	},
	
	handleGrpSearch: function(oEvent){
		var sValue = oEvent.getSource().getValue();
		var oFilters = [];
		if (sap.ui.getCore().byId("idGrpTbl").getVisible()) {
			var oSource = sap.ui.getCore().byId("idGrpTbl");
			oFilters = [new sap.ui.model.Filter("groupName", sap.ui.model.FilterOperator.Contains, sValue)];
		} else if (sap.ui.getCore().byId("idRoleTable").getVisible()) {
			var oSource = sap.ui.getCore().byId("idRoleTable");
			oFilters = [new sap.ui.model.Filter("roleUniqName", sap.ui.model.FilterOperator.Contains, sValue)];
		} else if (sap.ui.getCore().byId("idUserTable").getVisible()) {
			var oSource = sap.ui.getCore().byId("idUserTable");
			var oFilter1 = new sap.ui.model.Filter("firstName", sap.ui.model.FilterOperator.Contains, sValue);
			var oFilter2 = new sap.ui.model.Filter("lastName", sap.ui.model.FilterOperator.Contains, sValue);
			var oFilter3 = new sap.ui.model.Filter("loginId", sap.ui.model.FilterOperator.Contains, sValue);
			oFilters = new sap.ui.model.Filter({
			    filters: [oFilter1,oFilter2,oFilter3],
			    and: false
			  });
		}
		var oBinding = oSource.getBinding("items");
		oBinding.filter(oFilters);
	},
	
	onSelectionGo: function(oEvent){
		var selectedCriteria = oEvent.getSource().getParent().getItems()[1].getSelectedKey();
		oUserDefaultModel.setProperty("/selectedCriteria",selectedCriteria);
		this.busy.open();
		var configObj = oEvent.getSource().getBindingContext('oConfigModel').getObject();
		this.obj = configObj;
		if (!this._oAccessList) {
			this._oAccessList = sap.ui.xmlfragment("pmc.fragments.userAccessSelect", this);
			this.getView().addDependent(this._oAccessList);
		}
		this._oAccessList.open();
		var oSource = "";
		if (sap.ui.getCore().byId("idGrpTbl").getVisible()) {
			oSource = sap.ui.getCore().byId("idGrpTbl");
		} else if (sap.ui.getCore().byId("idRoleTable").getVisible()) {
			oSource = sap.ui.getCore().byId("idRoleTable");
		} else if (sap.ui.getCore().byId("idUserTable").getVisible()) {
			oSource = sap.ui.getCore().byId("idUserTable");
		}
		oSource.removeSelections();
		sap.ui.getCore().byId("idSearchFld").setValue("");
		sap.ui.getCore().byId("idSearchFld").fireSearch();
		this.busy.close();
	},
	
	onSelectionCriteriaSubmit: function(oEvent){
		this.busy.open();
		var that = this;
		var selectedCriteria = oUserDefaultModel.getData().selectedCriteria;
		var obj = this.obj;
		var userRoleList = [];
		if (selectedCriteria == "User Group") {
			var selectedItems = sap.ui.getCore().byId("idGrpTbl").getSelectedItems();
			var selectedCount = selectedItems.length;
			for(var i in selectedItems) {
				var grpName = selectedItems[i].getBindingContext("oUserGroupModel").getObject().groupName;
				var oGroupUsersModel = new sap.ui.model.json.JSONModel();
				that.getView().setModel(oGroupUsersModel, "oGroupUsersModel");
				oGroupUsersModel.loadData("/appone/pmc/user/getUsersAssignedInGroup/"+grpName,null,true);
				oGroupUsersModel.attachRequestCompleted(function(oEvent) {
					var userDto = oGroupUsersModel.getData().userDto;
					if (userDto) {
						if (!(userDto instanceof Array)) {
							userDto = [userDto];
						}
						that.getView().getModel("oGroupUsersModel").refresh();
						for (var j in userDto) {
							userRoleList.push(userDto[j].loginId);
						}
						if (!obj.userRole) {
							obj.userRole = "";
						}
						for (var k = 0; k < userRoleList.length;k++){
							if (k > 0 || obj.userRole!="") {
								obj.userRole += ", "
							}
							obj.userRole += userRoleList[k];
						}
						that.getView().getModel("oConfigModel").refresh();
						if(selectedCount == (parseInt(i)+1)) {
							that.onSelectionCriteriaClose();
							that.busy.close();
						} 
						oUserDefaultModel.setProperty("/settingsChange", true);
						oUserDefaultModel.refresh();
					} else {
						sap.m.MessageToast.show("No Users");
						that.onSelectionCriteriaClose();
						that.busy.close();
					}
				});
				oGroupUsersModel.attachRequestFailed(function(oEvent) {
					that.busy.close();
					sap.m.MessageToast.show("Failed to load users");
				});
			}
		} else if (selectedCriteria == "User Role") {
			var selectedItems = sap.ui.getCore().byId("idRoleTable").getSelectedItems();
			var selectedCount = selectedItems.length;
			for(var i in selectedItems) {
				var roleName = selectedItems[i].getBindingContext("oRoleModel").getObject().roleUniqName;
				var oRoleUsersModel = new sap.ui.model.json.JSONModel();
				that.getView().setModel(oRoleUsersModel, "oRoleUsersModel");
				oRoleUsersModel.loadData("/appone/pmc/user/usersByRole/"+roleName,null,true);
				oRoleUsersModel.attachRequestCompleted(function(oEvent) {
					var userDto = oRoleUsersModel.getData().userDetailsDtos;
					if (userDto) {
						if (!(userDto instanceof Array)) {
							userDto = [userDto];
						}
						that.getView().getModel("oRoleUsersModel").refresh();
						for (var j in userDto) {
							userRoleList.push(userDto[j].userId);
						}
						if (!obj.userRole) {
							obj.userRole = "";
						}
						for (var k = 0; k < userRoleList.length;k++){
							if (k > 0 || obj.userRole!="") {
								obj.userRole += ", "
							}
							obj.userRole += userRoleList[k];
						}
						that.getView().getModel("oConfigModel").refresh();
						if(selectedCount == (parseInt(i)+1)) {
							that.onSelectionCriteriaClose();
							that.busy.close();
						} 
						oUserDefaultModel.setProperty("/settingsChange", true);
						oUserDefaultModel.refresh();
					} else {
						sap.m.MessageToast.show("No Users");
						that.onSelectionCriteriaClose();
						that.busy.close();
					}
				});
				oRoleUsersModel.attachRequestFailed(function(oEvent) {
					that.busy.close();
					sap.m.MessageToast.show("Failed to load users");
				});
			}
		} else if (selectedCriteria == "User Name") {
			var selectedItems = sap.ui.getCore().byId("idUserTable").getSelectedItems();
			for(var i in selectedItems) {
				userRoleList.push(selectedItems[i].getBindingContext("oAllUserModel").getObject().loginId);
			}
			if (!obj.userRole) {
				obj.userRole = "";
			}
			for (var k = 0; k < userRoleList.length;k++){
				if (k > 0 || obj.userRole!="") {
					obj.userRole += ", "
				}
				obj.userRole += userRoleList[k];
			}
			that.getView().getModel("oConfigModel").refresh();
			that.onSelectionCriteriaClose();
			that.busy.close();
			oUserDefaultModel.setProperty("/settingsChange", true);
			oUserDefaultModel.refresh();
		}
		that.onSelectionCriteriaClose();
		that.busy.close();
	},
	
	onSelectionCriteriaClose: function(){
		this._oAccessList.close();
		this.busy.close();
	},
	
	onSelectedUsersLinkPress: function(oEvent){
		this.busy.open();
		var that = this;
		var configObj = oEvent.getSource().getBindingContext('oConfigModel').getObject();
		this.userObj = configObj;
		var userRole = configObj.userRole;
		if (userRole && userRole != "") {
			var userList = userRole.split(", ");
			var lookup = [];
			for (var j in userList) {
				var obj = {};
				obj["userId"] = userList[j];
				lookup.push(obj);
			}
			var oSelectedUsersModel = new sap.ui.model.json.JSONModel();
			that.getView().setModel(oSelectedUsersModel, "oSelectedUsersModel");
			oSelectedUsersModel.setData({"userList":lookup});
			if (!this._oUsersList) {
				this._oUsersList = sap.ui.xmlfragment("pmc.fragments.selectedUsersList", this);
				this.getView().addDependent(this._oUsersList);
			}
			this._oUsersList.open();
			//sap.ui.getCore().byId("idSelectedUsersTable").selectAll();
		} else {
			sap.m.MessageToast.show("No users are selected");
		}
		this.busy.close();
	},
	
	onSelectedUsersSubmit: function(oEvent){
		this.busy.open();
		var oConfigObject = this.userObj;
		var userGroupList = [];
		var selectedItems = sap.ui.getCore().byId("idUsersList").getSelectedItems();
		for(var i in selectedItems) {
			userGroupList.push(selectedItems[i].getBindingContext("oSelectedUsersModel").getObject().userId);
		}
		var users = "";
		for (var i = 0; i < userGroupList.length;i++){
			if (i > 0) {
				users += ", "
			}
			users += userGroupList[i];
		}
		oConfigObject.userRole = users;
		this.getView().getModel("oConfigModel").refresh();
		this.onUserListClose();
		this.busy.close();
		oUserDefaultModel.setProperty("/settingsChange", true);
		oUserDefaultModel.refresh();
	},
	
	onUserListClose: function(){
		this._oUsersList.close();
		this.busy.close();
	},
	
	onDeleteRow: function(oEvent){
		var oBindingContext = oEvent.getSource().getBindingContext("oConfigModel");
		var configModelPath = oBindingContext.getPath();
		var index = configModelPath.split("/").pop();
		var configModel = this.getView().getModel("oConfigModel");
		var configData = configModel.getData().reportDto;
		var oObject = oBindingContext.getObject();
		var reportName = oObject.reportName;
		if (!oUserDefaultModel.getData().deletedRanges){
			oUserDefaultModel.getData().deletedRanges = {};
		}
		var oUserDefaultData = oUserDefaultModel.getData().deletedRanges;
		var deletedItems;
		if (reportName == "task aging") {
			configModel.getData().taskCount--;
			report = "TASK AGING";
			if (!oUserDefaultData.task){
				oUserDefaultData.task = [];
			}
			deletedItems = oUserDefaultData.task;
		} else if (reportName == "process aging") {
			configModel.getData().processCount--;
			report = "PROCESS AGING";
			if (!oUserDefaultData.process){
				oUserDefaultData.process = [];
			}
			deletedItems = oUserDefaultData.process;
		} else {
			configModel.getData().taskCount--;
			report = "TASK STATUS";
			if (!oUserDefaultData.taskStatus){
				oUserDefaultData.taskStatus = [];
			}
			deletedItems = oUserDefaultData.taskStatus;
		}
		for (var i in configData) {
			if (configData[i].reportName == report) {
				configData = configData[i].reportDtoList;
				break;
			}
		}
		var temp = configData.splice(index,1);
		temp[0].isDeleted = true;
		deletedItems.push(temp[0]);
		oUserDefaultModel.refresh();
		configModel.refresh();
		this.setLastDelete();
		oUserDefaultModel.setProperty("/settingsChange", true);
		oUserDefaultModel.refresh();
	},
	
	setLastDelete: function(){
		var oConfigModel = this.getView().getModel("oConfigModel");
		var reportDto = oConfigModel.getData().reportDto;
		var processLastInd,taskLastInd,taskStatusLastInd;
		for (var j in reportDto) {
			var i = 0;
			var reportDtoList = reportDto[j].reportDtoList;
			for (i; i < reportDtoList.length-1; i++) {
				reportDtoList[i].isLast = false;
			}
			reportDtoList[i].isLast = true;
		}
		oConfigModel.refresh();
	},
	
	callSettingsFunction: function(){
		var defaultData = oUserDefaultModel.getData();
		if (defaultData.theme != defaultData.selectedTheme) {
			sap.ui.controller("pmc_ui.masterDetail").onSkinSettingChange();
		}
	},
	
	onClickItem: function(oEvent){
		if (oEvent.getSource().getSelected()) {
			oEvent.getSource().setSelected(false);
		} else {
			oEvent.getSource().setSelected(true);
		}
	},
	
	onChangeAnything: function() {
		oUserDefaultModel.setProperty("/settingsChange", true);
		oUserDefaultModel.refresh();
	}
	
//	onClosingConfigInfo: function(){
//		this._oConfInfoDialog.close();
//	},
	
	/**
	 * Called when the Controller is destroyed. Use this one to free resources and finalize activities.
	 * @memberOf pmc_ui.adminPage
	 */
//	onExit: function() {

//	}
	
	});
});