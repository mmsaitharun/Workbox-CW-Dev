jQuery.sap.declare("pmc.Component");
sap.ui.core.UIComponent.extend("pmc.Component", {
	metadata : {
		manifest : "json"
	},

	init : function() {
		jQuery.sap.require("sap.m.routing.RouteMatchedHandler");
		sap.ui.core.UIComponent.prototype.init.apply(this, arguments);
		var router = this.getRouter();
		this.routeHandler = new sap.m.routing.RouteMatchedHandler(router);
		router.initialize();
	}
});