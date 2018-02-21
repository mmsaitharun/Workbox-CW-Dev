var oUserDefaultModel = new sap.ui.model.json.JSONModel();
sap.ui.getCore().setModel(oUserDefaultModel, "oUserDefaultModel");
oUserDefaultModel.getData().highWorkLoadCount = 0;
oUserDefaultModel.getData().normalWorkLoadCount = 0;
oUserDefaultModel.getData().lowWorkLoadCount = 0;
oUserDefaultModel.getData().totalWorkLoadCount = 0;
oUserDefaultModel.getData().highLoadHighLimit = 0;
oUserDefaultModel.getData().highLoadLowLimit = 0;
oUserDefaultModel.getData().normalLoadHighLimit = 0;
oUserDefaultModel.getData().normalLoadLowLimit = 0;
oUserDefaultModel.getData().lowLoadHighLimit = 0;
oUserDefaultModel.getData().lowLoadLowLimit = 0;
oUserDefaultModel.getData().enable = true;
oUserDefaultModel.getData().userListVisible = false;
oUserDefaultModel.getData().userNavigateScreen = "";
oUserDefaultModel.getData().userTaskStatus = "";
oUserDefaultModel.getData().userWorkLoadType = "";
oUserDefaultModel.getData().userTaskCount = "";
oUserDefaultModel.getData().userId = "";
oUserDefaultModel.getData().processId = "";
oUserDefaultModel.getData().sort = false;
oUserDefaultModel.getData().selectedKey = "";
oUserDefaultModel.getData().processName = "";
oUserDefaultModel.getData().reportHeader = "";
oUserDefaultModel.getData().previousScreen = "";
oUserDefaultModel.getData().ruleChange = false;
oUserDefaultModel.getData().ruleProcessKey = "";
oUserDefaultModel.getData().slaUpdate = false;
oUserDefaultModel.getData().userList = false;
oUserDefaultModel.getData().theme = "dark";
oUserDefaultModel.getData().selectedTheme = "dark";
oUserDefaultModel.getData().selectedSubConfig = "Process Detail";
oUserDefaultModel.getData().selectedConfig = "general";
oUserDefaultModel.getData().selectedTableType = "process";
oUserDefaultModel.getData().confInfoImage = "";

function toastMessage(message) {
	sap.m.MessageToast.show(message, {
		duration : 10000,
		width : "20em",
		my : "center center",
		at : "center center",
		of : window,
		offset : "0 0",
		collision : "fit fit",
		onClose : null,
		autoClose : true,
		animationTimingFunction : "ease",
		animationDuration : 1000,
		closeOnBrowserNavigation : true
	});
}

function getSlaTimings(oCount, oUnit){
	if(!oCount){
		return 0;
	} else {
		if (oUnit == "minute") {
			return oCount;
		} else if (oUnit == "hours") {
			return (oCount * 60);
		} else if (oUnit == "days") {
			return (oCount * 60 * 24);
		} 
	}
}

function getMonth(oValue) {
	var month = "";
	switch (oValue) {
	case "01":
		month = "Jan";
		break;
	case "02":
		month = "Feb";
		break;
	case "03":
		month = "Mar";
		break;
	case "04":
		month = "Apr";
		break;
	case "05":
		month = "May";
		break;
	case "06":
		month = "Jun";
		break;
	case "07":
		month = "Jul";
		break;
	case "08":
		month = "Aug";
		break;
	case "09":
		month = "Sept";
		break;
	case "10":
		month = "Oct";
		break;
	case "11":
		month = "Nov";
		break;
	case "12":
		month = "Dec";
		break;
	default:
		month = "";
	}
	return month;
}

function replaceTheChange(){
	var themeType = oUserDefaultModel.getProperty("/theme");
	var cssFile = "";
	if (themeType == "dark") {
		cssFile = "CSS/light.css";
		oUserDefaultModel.setProperty("/theme", "light");
	} else {
		cssFile = "CSS/dark.css"
		oUserDefaultModel.setProperty("/theme", "dark");
	}
	var oldlink = document.getElementsByTagName("link").item(5);
    var newlink = document.createElement("link");
    newlink.setAttribute("rel", "stylesheet");
    newlink.setAttribute("type", "text/css");
    newlink.setAttribute("href", cssFile);
    document.getElementsByTagName("head").item(0).replaceChild(newlink, oldlink);
} 