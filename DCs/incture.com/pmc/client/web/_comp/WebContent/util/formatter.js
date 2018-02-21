jQuery.sap.declare("pmc.util.formatter");

pmc.util.formatter = {
		fnGetTheWorkLoadColor : function(oValue) {
			this.getParent().getParent().removeStyleClass("highWorkLoadUserBoxStyle");
			this.getParent().getParent().removeStyleClass("lowWorkLoadUserBoxStyle");
			this.getParent().getParent().removeStyleClass("normalWorkLoadUserBoxStyle");
			if (oValue && parseInt(oValue, 10) >= oUserDefaultModel.getData().lowLoadLowLimit && parseInt(oValue, 10) <= oUserDefaultModel.getData().lowLoadHighLimit) {
				oUserDefaultModel.getData().lowWorkLoadCount = oUserDefaultModel.getData().lowWorkLoadCount + 1;
				this.getParent().getParent().addStyleClass("lowWorkLoadUserBoxStyle");
				this.getBindingContext("oUserListModel").getObject().taskCount = parseInt(oValue, 10);
			} else if (oValue && parseInt(oValue, 10) >= oUserDefaultModel.getData().normalLoadLowLimit && parseInt(oValue, 10) <= oUserDefaultModel.getData().normalLoadHighLimit) {
				oUserDefaultModel.getData().normalWorkLoadCount = oUserDefaultModel.getData().normalWorkLoadCount + 1;
				this.getParent().getParent().addStyleClass("normalWorkLoadUserBoxStyle");
				this.getBindingContext("oUserListModel").getObject().taskCount = parseInt(oValue, 10);
			} else if (oValue && parseInt(oValue, 10) >= oUserDefaultModel.getData().highLoadLowLimit) {
				oUserDefaultModel.getData().highWorkLoadCount = oUserDefaultModel.getData().highWorkLoadCount + 1;
				this.getParent().getParent().addStyleClass("highWorkLoadUserBoxStyle");
				this.getBindingContext("oUserListModel").getObject().taskCount = parseInt(oValue, 10);
			}
			oUserDefaultModel.getData().totalWorkLoadCount = oUserDefaultModel.getData().highWorkLoadCount+ oUserDefaultModel.getData().normalWorkLoadCount + oUserDefaultModel.getData().lowWorkLoadCount;
			return oValue;
		},

		fnGetTheTotalUserCount: function(oHigh, oNormal, oLow) {
			var oValue = oHigh + oNormal + oLow;
			return oValue;
		},

		fnGetTheTaskStatusIcon: function(oValue) {
			if (oValue && (oValue === "READY" || oValue === "RESERVED" || oValue === "STARTED" || oValue === "IN_PROGRESS")) {
				return "images/inProgress.png";
			} else {
				return "images/completed.png";
			}
		},

		fnShowForwardButton: function(oValue) {
			if (oValue && (oValue === "READY" || oValue === "RESERVED")) {
				return true;
			} else {
				return false;
			}
		},

		fnGetTheTaskColor: function(oValue, oTaskType) {
			if (oValue) {
				this.removeStyleClass("completedTaskStyle");
				this.removeStyleClass("inProgressTaskStyle");
				this.removeStyleClass("completedAutomatedTaskStyle");
				this.removeStyleClass("inProgressAutomatedTaskStyle");
				if (oTaskType === "Human" && oValue === "COMPLETED") {
					this.addStyleClass("completedTaskStyle");
				} else if (oTaskType === "Human" && oValue != "COMPLETED") {
					this.addStyleClass("inProgressTaskStyle");
				} else if (oTaskType === "Automated" && oValue === "COMPLETED") {
					this.addStyleClass("completedAutomatedTaskStyle");
				} else {
					this.addStyleClass("inProgressAutomatedTaskStyle");
				}
				return true;
			} else {
				return false;
			}
		},

		fnGetTaskCompletionUser:function(oValue) {
			if (oValue && oValue === "COMPLETED") {
				return true;
			} else {
				return false;
			}
		},

		fnGetUserGroup:function(oValue) {
			if (oValue && oValue === "COMPLETED") {
				return false;
			} else {
				return true;
			}
		},
		
		fnDisplayForwardedBy:function(oValue) {
			if (oValue) {
				return true;
			} else {
				return false;
			}
		},
		
		fnDisplayReject: function(oValue) {
			if (oValue && oValue=="Reject") {
				return true;
			} else {
				return false;
			}
		},

		fnShowUserGroup:function(oValue) {
			if (oValue) {
				return true;
			} else {
				return false;
			}
		},

		fnShowReadyTaskNumbers:function(oValue) {
			if (oValue && oValue === "READY") {
				return true;
			} else {
				return false;
			}
		},

		fnShowReservedTaskNumbers:function(oValue) {
			if (oValue && oValue === "RESERVED") {
				return true;
			} else {
				return false;
			}
		},

		fnShowAllNumbers:function(oValue) {
			if (oValue && oValue === "ALL") {
				return true;
			} else {
				return false;
			}
		},

		fnGetUserPic:function(oValue) {
			if (oValue) {
				return "data:image/png;base64," + oValue;
			} else {
				return "images/userIcon.png";
			}
		},

		fnFormatDate:function(oDate) {
			var returnValue = "";
			if (oDate) {
				var date = oDate.split("T")[0].split("-");
				var oValue = date[1];
				var month = getMonth(oValue);
				returnValue = date[2] + " " + month + " " + date[0];
				this.getBindingContext("oUserWorkLoadModel").getObject().date = returnValue;
			}
			return returnValue;
		},

		fnFormatCreateDate:function (oDate) {
			var returnValue = "";
			if (oDate) {
				var date = oDate.split("T")[0].split("-");
				var oValue = date[1];
				var month = getMonth(oValue);
				returnValue = date[2] + " " + month + " " + date[0];
				this.getBindingContext("oTaskAgeingModel").getObject().date = returnValue;
			}
			return returnValue;
		},

		fnShowDateFormat:function (oValue, oStatus) {
			if (oStatus && (oStatus === "READY" || oStatus === "RESERVED" || oStatus === "STARTED" || oStatus === "IN_PROGRESS")) {
				return "IN PROGRESS";
			} else {
				if (oValue) {
					if (oStatus && oStatus === "COMPLETED") {
						return "COMPLETED on " + oValue;
					} else {
						return "PROCESS STARTED on " + oValue;
					}
				} else {
					return "";
				}
			}
		},
		
		fnShowDueDateFormat:function (oValue, oStatus) {
			if (oStatus && (oStatus === "READY" || oStatus === "RESERVED")) {
				if (oValue) {
					return "Due By " + oValue + " ";
				}
				return "";
			} else {
				return "";
			}
		},

		fnShowStartDateFormat:function (oValue){
			if (oValue) {
				var date = oValue.split("T")[0].split("-");
				var month = getMonth(date[1]);
				return "STARTED on " + date[2] + " " + month + " " + date[0];
			} else {
				return "";
			}
		},

		fnDisplayDayFormat:function (oValue) {
			if (oValue) {
				var month = "";
				if (oValue.split("-").length > 2) {
					month = getMonth(oValue.split("-")[1]);
					return oValue.split("-")[0] + " " + month + " " + "20"
					+ oValue.split("-")[2];
				} else {
					return oValue;
				}
			} else {
				return "";
			}
		},

		fnGetTheAlignment:function (oValue) {
			if (oValue && oValue === "Process Name") {
				return 'Begin';
			} else {
				return 'End';
			}
		},
		
		fnGetTheWidth:function (oValue) {
			if (oValue && oValue === "Process Name") {
				return "10rem";
			} else {
				return "auto";
			}
		},

		fnGetTaskAgeingAlignment:function(oValue) {
			if (oValue && oValue === "User Name") {
				return 'Begin';
			} else {
				return 'End';
			}
		},
		
		fnGetTaskAgeingWidth:function(oValue) {
			if (oValue && oValue === "User Name") {
				return "10rem";
			} else {
				return "auto";
			}
		},

		getVisibleColumn:function(oValue) {
			if (oValue && oValue === "name") {
				return false;
			} else {
				return true;
			}
		},

		fnGetFooterAlignment:function(oValue) {
			if (oValue && oValue === "Total") {
				return 'Begin';
			} else {
				return 'End';
			}
		},
		
		fnGetTheFooterWidth:function (oValue) {
			if (oValue && oValue === "Total") {
				return "10rem";
			} else {
				return "auto";
			}
		},

		ordinal_suffix_of:function(i) {
			var j = i % 10, k = i % 100;
			if (j === 1 && k != 11) {
				return i + "st";
			}
			if (j === 2 && k != 12) {
				return i + "nd";
			}
			if (j === 3 && k != 13) {
				return i + "rd";
			}
			return i + "th";
		},

		fnGetProcessStartDate:function(oValue) {
			if (oValue) {
				var date = oValue.split("T")[0].split("-");
				var oValue = date[1];
				var month = getMonth(oValue);
				return date[2] + " " + month + " " + date[0];
			} else {
				return "";
			}
		},

		fnShowStausOfProcess:function(oValue) {
			/*this.removeStyleClass("processFlowOpenStatusStyle");
			this.removeStyleClass("processFlowClosedStatusStyle");*/
			if (oValue && oValue === "IN_PROGRESS") {
				//this.addStyleClass("processFlowOpenStatusStyle");
				return "IN PROGRESS";
			} else {
				//this.addStyleClass("processFlowClosedStatusStyle");
				return "CLOSED";
			}
		},

		fnHeaderStyle:function(oValue){
			var ua = navigator.userAgent;
			var browerEx = ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
			browerEx = browerEx[2] ? [browerEx[1], browerEx[2]] : [navigator.appName, navigator.appVersion, '-?'];
			if ((tem = ua.match(/version\/(\d+)/i)) !=null) {
				browerEx.splice(1,1,tem[1]);
			}
			if (browerEx[0] == "Firefox"){
				this.addStyleClass("reportHeadingFireFoxStyle");
			} else {
				this.addStyleClass("reportHeadingStyle");
			}
			return oValue;
		},

		fnSlaExistCheck:function(oValue){
			if (oValue && (oValue == true || oValue == "true")){
				return true;

			} else {
				return false;
			}
		},

		fnStatusOnOff:function(oValue){
			if (oValue) {
				if (oValue == "Active") {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		},

		fnDelSwitch:function(oValue){
			if (!oValue) {
				return true;
			} else {
				return false;
			}
		},

		fnShowSwitch:function(oValue){
			if (oValue) {
				return true;
			} else {
				return false;
			}
		},

		fnEnableSwitch:function(oValue){
			if (oValue) {
				return true;
			} else {
				return false;
			}
		}, 

		fnTaskNameDisable:function(oValue){
			if (oValue && oValue == "Create") {
				return true;
			} else {
				return false;
			}
		},

		fnTaskNameEnable:function(oValue){
			if (oValue && oValue == "Create") {
				return false;
			} else {
				return true;
			}
		},

		fnGetEditable:function(oChange, oStatus, oSLACount) {
			if(!oSLACount){
				return false;
			}
			if (oChange == "Delete" || oChange == "CreDel"  || oStatus == "Paused") {
				return false;
			} else {
				return true;
			}
		},

		fnDisplayUserData:function(oValue){
			if (oValue && oValue === "Human") {
				return true;
			} else {
				return false;
			}
		},
		
		displayEndRangeNo: function(oReport){
			var oValue = this.getParent().getBindingContext("oConfigModel").getObject().reportName;
			if (oValue == "PROCESS AGING" && oReport == "process aging range") {
				return true;
			} else if (oValue == "TASK STATUS" && oReport == "task Status Graph range") {
				return true;
			}
			return false;
		}, 
		
		displayLabel : function(oValue){
			if (oValue == "ALL") {
				return false;
			}
			return true;
		},
		
		fnTextUnderline: function(oType){
			if (oType == "Accept") {
				return true;
			}
			return false;
		},
		
		displayPrevBtn: function(oValue){
			if (oValue == true) {
				return false;
			}
			return true;
		},
		
		fnDisplayAuthConfig: function(oValue) {
			if (oValue && oValue === "authorization") {
				return true;
			}
			return false;
		},
		
		fnDisplayGeneralConfig: function(oValue) {
			if (oValue && oValue === "configuration") {
				return true;
			}
			return false;
		},
		
		fnDisplayProfileConfig: function(oValue) {
			if (oValue && oValue === "personalization") {
				return true;
			}
			return false;
		},
		
		displayProcessConfigScreen: function(oValue) {
			if (oValue && oValue == "Process Detail") {
				return true;
			} else {
				return false;
			}
		},
		
		displayWorkLoadConfigScreen: function(oValue) {
			if (oValue && oValue == "Workload") {
				return true;
			} else {
				return false;
			}
		},
		
		displayUserAccessConfigScreen: function(oValue){
			if (oValue && oValue == "User Access") {
				return true;
			} else {
				return false;
			}
		},
		
		displayProcessAgeConfigScreen: function(oValue) {
			if (oValue && oValue == "Ranges" ) {
				//|| oValue == "Task" ||  oValue == "Task Status"
				return true;
			} else {
				return false;
			}
		},
		
		displayTaskAgeConfigScreen: function(oValue) {
			if (oValue && oValue == "Task") {
				return true;
			} else {
				return false;
			}
		},
		
		displayTaskStatusConfigScreen: function(oValue) {
			if (oValue && oValue == "Task Status") {
				return true;
			} else {
				return false;
			}
		},
		
		showGrpTbl: function(oValue) {
			if (oValue == "User Group") {
				return true;
			}
			return false;
		},
		
		showRoleTbl: function(oValue) {
			if (oValue == "User Role") {
				return true;
			}
			return false;
		},
		
		showUserTbl: function(oValue) {
			if (oValue == "User Name") {
				return true;
			}
			return false;
		},
		
		changeClassSideList: function(subConfig,config){
			this.removeStyleClass("processListItemBoxActiveStyleClass");
			if (subConfig == config) {
				this.addStyleClass("processListItemBoxActiveStyleClass");
			}
			return true;
		},
		
		fnWB_DisplayMailSendBtn: function(oValue){
			if (oValue) {
				return true;
			}
			return false;
		},
		
		fnWB_DisplayMailSubmitBtn: function(oValue){
			if (oValue) {
				return false;
			}
			return true;
		},
		
		displayStatus: function(oValue) {
			var returnValue = "";
			if (oValue == "READY") {
				returnValue = "New";
			} else if (oValue == "RESERVED") {
				returnValue = "In Progress";
			} else {
				returnValue = oValue;
			}
			this.getBindingContext("oTaskAgeingModel").getObject().newStatus = returnValue;
			return returnValue;
		},
		
		fnLightThemeActive: function(oValue) {
			this.removeStyleClass("selectedThemeClass");
			if (oValue == "light") {
				this.addStyleClass("selectedThemeClass");
			}
			return true;
		},
		
		fnDarkThemeActive: function(oValue) {
			this.removeStyleClass("selectedThemeClass");
			if (oValue == "dark") {
				this.addStyleClass("selectedThemeClass");
			}
			return true;
		},
		
		fnGetInfoImage: function(oValue){
			if (oValue == "dark") {
				return "images/info.png";
			} else {
				return "images/info_dark.png";
			}
			
		},
		
		fnGetDisplayReportName : function(oValue){
			if ( oValue == "PROCESS AGING") {
				return "Process";
			} else if ( oValue == "TASK STATUS" ) {
				return "Task Status";
			} else if ( oValue == "TASK AGING" ){
				return "Task"
			}
		}
};