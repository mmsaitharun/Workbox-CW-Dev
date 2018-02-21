jQuery.sap.declare('com.incture.Component');
sap.ui.core.UIComponent.extend('com.incture.Component', {
	metadata:{
		includes:[
		          "./css/style.css", 
		          "./css/dark.css", 
		          "./util/formatter.js",
		          "./util/utility.js"
		          ],
		          routing : {
		        	  config: {
		        		  viewType: "XML",
		        		  viewPath: "com.incture.adminpage",
		        		  targetAggregation: "pages",
		        		  targetControl :"idAppControl",
		        		  clearTarget: false
		        	  },
		        	  routes : [{
		        	      pattern:"",
		        	      name:"adminPage",
		        	      view:"adminPage"
		        	  }]
		          }
	}
});

com.incture.Component.prototype.init = function(){
	jQuery.sap.require("sap.ui.core.routing.History");
	jQuery.sap.require("sap.m.routing.RouteMatchedHandler");
	sap.ui.core.UIComponent.prototype.init.apply(this);
	var router = this.getRouter();
	this.routeHandler = new sap.m.routing.RouteMatchedHandler(router);
	router.initialize();
};

com.incture.Component.prototype.destroy = function(){
	if(this.routeHandler){
		this.routeHandler.destroy(); 
	}
	sap.ui.core.UIComponent.destroy.apply(this,arguments);
};

com.incture.Component.prototype.createContent = function(){
	this.view = sap.ui.view({
		id : "idAppControl",
		viewName : "com.incture.adminpage.App",
		type:sap.ui.core.mvc.ViewType.XML});
	return this.view;
}
