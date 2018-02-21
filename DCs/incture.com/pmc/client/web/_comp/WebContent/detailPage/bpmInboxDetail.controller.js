jQuery.sap.require("sap.m.MessageBox");
sap.ui.controller("detailPage.bpmInboxDetail", {

	/**
	 * Called when a controller is instantiated and its View controls (if available) are already created.
	 * Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
	 * @memberOf pmcinbox.bpmInbox
	 */
	onInit: function() {
		var that=this;
		var oModifyModel = new sap.ui.model.json.JSONModel();
		that.getView().setModel(oModifyModel, "oModifyModel");

		var oDetailModel = new sap.ui.model.json.JSONModel();
		that.getView().setModel(oDetailModel,"oDetailModel");

		that.onLoad();

	},
	onSubmit : function(oEvent){
		var that = this;
		var headerData = that.getView().getModel("oDetailModel").getData();
		var data={
				"requesterId":headerData.requesterId,
				"requestId":headerData.requestId,
				"vendorId":headerData.vendorId
		}

		this.getTaskID(data,headerData);

	},
	getDataOnLoad : function(taskId){
		var that=this;
		var	oDataModel	= that.getView().getModel("oDataModel");
		path="/InputData";
		oUrlParams = "$expand="+this.getView().byId("process").getSelectedKey();
		oDataModel.read(path, null, oUrlParams, true, function(oData){
			that.openTask(oData);
			console.log(oData);

		}, function(oError){
			sap.m.MessageToast.show("Error");
		});
	},
	openTask : function(oData){
		var that = this;
		var key = this.getView().byId("process").getSelectedKey();
		if( key === "PoHeader"){
			data  = oData.results[0].PoHeader;
		}
		else if(key === "InvoiceContext"){
			data  = oData.results[0].InvoiceContext;
		}
		else if(key === "PriceData"){
			data  = oData.results[0].PriceData;
		}
		that.getView().getModel("oDetailModel").setData(data);
		that.getView().getModel("oDetailModel").getData().process= this.getView().byId("process")._getSelectedItemText();
		that.getView().getModel("oDetailModel").refresh();
	//	that.getItemData();
	},
	getItemData : function(){
		var that =this;
		that.getView().getModel("oDetailModel").getData().items=[{"quantity":"1234","itemNo":"10","price":"1234","material":"200000012"},{"quantity":"34","itemNo":"20","price":"434","material":"200010456"}];
		that.getView().getModel("oDetailModel").refresh();
	},

	onAdd : function(){
		var that= this;
		var obj=[{"quantity":"","itemNo":"","price":"","material":"","isNew":true}];
		that.getView().getModel("oDetailModel").getData().items=obj.concat(that.getView().getModel("oDetailModel").getData().items);
		that.getView().getModel("oDetailModel").refresh();
	},
	getTaskID : function(modelData,headerData){
		var that = this;
		var processUrl = "/bpmodata/startprocess.svc/incture.com/" ;
		var expandUrl = "ProcessStartEvent,ProcessStartEvent/::";
		var startData = {};
		var data = {};
		if(this.getView().byId("process").getSelectedKey() === "PoHeader"){
			processUrl = processUrl	+ "pmc~wb~bpm/PurchaseOrderPrc";
			modelData.prId= headerData.prId;
			modelData.contractNo= headerData.contractNo;
			data["::StartPoProcess"] = modelData;
			expandUrl = expandUrl+"StartPoProcess";
		}
		else if(this.getView().byId("process").getSelectedKey() === "InvoiceContext"){
			processUrl = processUrl	+ "pmc~wb~invoice~bpm/InvoiceValPrc";
			modelData.invoiceNo =  headerData.invoiceNo;
			modelData.prId= headerData.prId;
			modelData.goodsReceiptNo= headerData.goodsReceiptNo;
			data["::StartInvoicePrc"] = modelData;
			expandUrl = expandUrl+"StartInvoicePrc";
		}
		else if (this.getView().byId("process").getSelectedKey() === "PriceData"){
			processUrl = processUrl	+ "pmc~priceup~bpm1/ProductPricePrc";
			modelData.productId =  headerData.productId;
			modelData.material =  headerData.material;
			modelData.plant =  headerData.plant;
			data["::StartPricePrc"] = modelData;
			expandUrl = expandUrl+"StartPricePrc";
		}

		oDModel = new sap.ui.model.odata.ODataModel(processUrl,true);
		oDModel.setCountSupported(false);
		oDModel.setDefaultBindingMode(sap.ui.model.BindingMode.TwoWay);
		startData["ProcessStartEvent"] = data;
		oDModel.read("/StartData",
				null, {
			"$expand": expandUrl
		}, false);
		oDModel.create("/StartData",startData,null, function(oData,oResponse){
			if(oResponse.statusCode === 201){
				sap.m.MessageToast.show("Submitted successfully with RequestId "+ modelData.requestId);
				that.onLoad();
			}

		}, function(oError){
			sap.m.MessageToast.show("Error");
		});
		
	},
	onLoad:function(){
		var that=this;
		var taskId = jQuery.sap.getUriParameters().get("taskId");
		var src = jQuery.sap.getUriParameters().get("src");
		if(taskId){
			//taskId="3e94cf053a1c11e7a7d40000007c8652";
			var oDataModel = new sap.ui.model.odata.ODataModel("");
			oDataModel.oMetadata.sUrl="/bpmodata/taskdata.svc/"+taskId+"/$metadata";
			oDataModel.refreshMetadata();
			oDataModel.sServiceUrl="/bpmodata/taskdata.svc/"+taskId;
			that.getView().setModel(oDataModel,"oDataModel");

			that.getView().getModel("oModifyModel").getData().editable=false;
			that.getView().getModel("oModifyModel").getData().screen1=false;
			that.getView().getModel("oModifyModel").getData().screen2=true;
			that.getView().getModel("oModifyModel").refresh();
			this.getView().byId("process").setSelectedKey(src);
			that.getDataOnLoad(taskId);
		}
		else{
			that.getView().getModel("oModifyModel").getData().screen1=true;
			that.getView().getModel("oModifyModel").getData().screen2=false;
			that.getView().getModel("oModifyModel").getData().editable=true;
			that.getView().getModel("oModifyModel").refresh();

			var obj={
					"requesterId":"",
					"requestId":"",
					"mode":"",
					"prId":"",
					"invoiceNo":"",
					"productId":"",
					"vendorId":"",
					"contractNo":"",
					"goodsReceiptNo":"",
					"material":"",
					"plant":""
			}
			this.getView().byId("process").setSelectedKey("InvoiceContext");
			that.getView().getModel("oDetailModel").setData(obj);
		}
		this.getView().getModel("oDetailModel").getData().process= this.getView().byId("process")._getSelectedItemText();
		that.getView().getModel("oDetailModel").refresh();

	},
	onAction : function(oEvent){
		var that=this;
		var action=oEvent.getSource().getText();
		var msg="Are you sure you want to "+action+"?";
		sap.m.MessageBox.show(
				msg, {
					icon: sap.m.MessageBox.Icon.ALERT,
					title: "",
					actions: [sap.m.MessageBox.Action.OK ,sap.m.MessageBox.Action.CANCEL],
					onClose: function(oAction) { 
						if(oAction=="OK"){
							var taskId = jQuery.sap.getUriParameters().get("taskId");
							//  taskId="3e94cf053a1c11e7a7d40000007c8652";
							that.claimTask(taskId,action);
						}
						else if(oAction=="CANCEL"){

						}
					}
				}
		);
	},

	claimTask:function(taskId,action){
		var that = this;

		var url = "Claim?InstanceID='" + taskId + "'&$format=json";
		/* Claim the Task */	
		var oDataModel1 = new sap.ui.model.odata.ODataModel("/bpmodata/tasks.svc/", true);
		oDataModel1.create(url, null, null, function(oData,oResponse) {
			sap.m.MessageToast.show("Claimed successfully");
			that.closeBPM(taskId,action);

		},
		function(oEvent) {

		});	
	},
	closeBPM : function(taskId,action){


		if(action == "Approve"){
			approved=true;
		}
		else{
			approved=false;
		}

		var that = this;
		var processUrl = "/bpmodata/taskdata.svc/"+taskId+"/";
		oDModel = new sap.ui.model.odata.ODataModel(processUrl,true);

		oDModel.setCountSupported(false);
		oDModel.setDefaultBindingMode(sap.ui.model.BindingMode.TwoWay);
		var headerData = that.getView().getModel("oDetailModel").getData();
		var modelData={
				"requesterId":headerData.requesterId,
				"requestId":headerData.requestId,
				"mode":headerData.mode,
				"prId":headerData.prId,
				"isApproved":approved,
				"invoiceNo":headerData.invoiceNo,
				"productId" :headerData.productId
		}

		var data = {};

		data[this.getView().byId("process").getSelectedKey()] = modelData;
		oDModel.read("/OutputData",
				null, {
			"$expand": this.getView().byId("process").getSelectedKey()
		}, false);
		oDModel.create("/OutputData",data,null, function(oData,oResponse){
			if(oResponse.statusCode === 201){
				sap.m.MessageToast.show("Submitted successfully");
				//setTimeout(function(){ that.onBackToHomePage(); }, 3000);
				//that.onLoad();
				parent.callClose();
			}

		}, function(oError){
			sap.m.MessageToast.show("Error");
		});


	},

	onProcessChange : function(oEvent){
		this.getView().getModel("oDetailModel").getData().process= oEvent.getSource()._getSelectedItemText();
		this.getView().getModel("oDetailModel").refresh();
	},
	/**
	 * Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
	 * (NOT before the first rendering! onInit() is used for that one!).
	 * @memberOf pmcinbox.bpmInbox
	 */
//	onBeforeRendering: function() {

//	},

	/**
	 * Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
	 * This hook is the same one that SAPUI5 controls get after being rendered.
	 * @memberOf pmcinbox.bpmInbox
	 */
//	onAfterRendering: function() {

//	},
	isNew : function(oValue){
		if(oValue){
			return true;
		}
		else
			return false;
	},
	isOld : function(oValue){
		if(oValue){
			return false;
		}
		else
			return true;
	},
	isTableVisible : function(screen,editable){

		if(!screen&&!editable){
			return true;
		}
		else
			return screen;
	},
	isPO : function(value){
		if(value === "Purchase Order"){
			return true;	
		}
		else{
			return false;
		}
	},
	isInvoice:function(value){
		if(value === "Invoice Validation"){
			return true;	
		}
		else{
			return false;
		}
	},
	isPU:function(value){
		if(value === "Price Update"){
			return true;	
		}
		else{
			return false;
		}
	},
	isInvoicePU:function(value){
		if(value === "Invoice Validation"){
			return true;	
		}
		else if(value === "Price Update"){
			return true;	
		}
		else{
			return false;
		}
	},
	setKey : function(oValue){
		if(oValue === "Purchase Order"){
			return "PO Number" ;	
		}
		else if(oValue === "Invoice Validation"){
			return "Invoice No";	
		}
		else if (oValue === "Price Update"){
			return "Product Id";	
		}
	},
	
	setCustomKey : function(oValue){
		if(oValue === "Purchase Order"){
			return "Contract No" ;	
		}
		else if(oValue === "Invoice Validation"){
			return "Receipt No";	
		}
		else if (oValue === "Price Update"){
			return "Material Id";	
		}
	},
	
	setCustomKey2 : function(oValue){
		 if(oValue === "Invoice Validation"){
			return "Product Id";	
		}
		else if (oValue === "Price Update"){
			return "Plant";	
		}
	}

	/**
	 * Called when the Controller is destroyed. Use this one to free resources and finalize activities.
	 * @memberOf pmcinbox.bpmInbox
	 */
//	onExit: function() {

//	}

});