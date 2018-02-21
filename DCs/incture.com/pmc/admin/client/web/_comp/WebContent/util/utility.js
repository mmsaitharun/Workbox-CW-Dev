var oUserDefaultModel = new sap.ui.model.json.JSONModel();
sap.ui.getCore().setModel(oUserDefaultModel, "oUserDefaultModel");
oUserDefaultModel.getData().userId = "";
oUserDefaultModel.getData().theme = "dark";

function replaceTheChange(){
	var themeType = oUserDefaultModel.getProperty("/theme");
	var cssFile = "";
	if (themeType == "dark") {
		cssFile = "css/light.css";
		oUserDefaultModel.setProperty("/theme", "light");
	} else {
		cssFile = "css/dark.css"
		oUserDefaultModel.setProperty("/theme", "dark");
	}
	var oldlink = document.getElementsByTagName("link").item(3);
    var newlink = document.createElement("link");
    newlink.setAttribute("rel", "stylesheet");
    newlink.setAttribute("type", "text/css");
    newlink.setAttribute("href", cssFile);
    document.getElementsByTagName("head").item(0).replaceChild(newlink, oldlink);
} 