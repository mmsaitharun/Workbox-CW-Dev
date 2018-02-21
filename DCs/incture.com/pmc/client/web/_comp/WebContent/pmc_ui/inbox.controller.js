sap.ui.define([
	'sap/ui/core/mvc/Controller'
], function (Controller) {
	"use strict";
sap.ui.controller("pmc_ui.inbox", {

/**
* Called when a controller is instantiated and its View controls (if available) are already created.
* Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
* @memberOf pmc_ui.inbox
*/
	onInit: function() {
		this.oRouter = sap.ui.core.UIComponent.getRouterFor(this);
		var that = this;
		this.getView().setModel(oUserDefaultModel,"oUserDefaultModel");
		this.oRouter.attachRoutePatternMatched(function(oEvent) {
			if (oEvent.getParameter("name") === "inbox") {
				oUserDefaultModel.getData().reportHeader = "UNIFIED INBOX";
				oUserDefaultModel.refresh(true);
				var dynaUrl = "/workbox/index.html";
				jQuery.sap.byId("contentFrameDiv").show();
				jQuery.sap.byId("contentFrameId").attr("src",dynaUrl);
				jQuery.sap.byId("contentFrameDiv").height("100%");
				window.onToProcessFlow= function (processId) {  
					oUserDefaultModel.getData().userNavigateScreen = "inbox";
					oUserDefaultModel.getData().processId = processId;
					oUserDefaultModel.refresh(true);
					that.oRouter.navTo("processFlow");
			    } 
			}
		});
	},
	
	onBackToLaunchPage: function(){
		oUserDefaultModel.getData().userNavigateScreen = "";
		oUserDefaultModel.refresh(true);
		this.oRouter.navTo("launchPage");
	},

/**
* Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
* (NOT before the first rendering! onInit() is used for that one!).
* @memberOf pmc_ui.inbox
*/
//	onBeforeRendering: function() {
//
//	},

/**
* Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
* This hook is the same one that SAPUI5 controls get after being rendered.
* @memberOf pmc_ui.inbox
*/
	onAfterRendering: function() {
		var dynaUrl = "/workbox/index.html";
		jQuery.sap.byId("contentFrameDiv").show();
		jQuery.sap.byId("contentFrameId").attr("src",dynaUrl);
		jQuery.sap.byId("contentFrameDiv").height("100%");
		sap.ui.core.UIComponent.getRouterFor(this).getView('pmc_ui.masterDetail').getController().onCollapseExapandPress(false);
	},

/**
* Called when the Controller is destroyed. Use this one to free resources and finalize activities.
* @memberOf pmc_ui.inbox
*/
//	onExit: function() {
//
//	}

});
});