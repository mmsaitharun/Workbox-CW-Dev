jQuery.sap.declare("com.incture.util.formatter");

com.incture.util.formatter = {
		
		displayRangeNo : function(oValue, oReport){
			oValue = parseInt(oValue)%100;
			return oValue;
		},
		
		displayRangeItem: function(oValue, oReport){
			if (oValue == "parc" && oReport == "process aging") {
				return true;
			} else if (oValue == "tarc" && oReport == "task aging") {
				return true;
			} else if (oValue == "tsgc" && oReport == "task Status Graph") {
				return true;
			}
			return false;
		},
		
		displayEndRangeNo: function(oValue, oReport){
			if (oValue == "parc" && oReport == "process aging range") {
				return true;
			} else if (oValue == "tsgc" && oReport == "task Status Graph range") {
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
		
		displayRangeFragment: function(oKey){
			if (oKey == "parc" || oKey == "tarc" || oKey == "tsgc") {
				return true;
			}
			return false;
		},
		
		displayProcessNameFragment: function(oKey){
			if (oKey == "pnc") {
				return true;
			}
			return false;
		},
		
		displayWorkloadFragment: function(oKey){
			if (oKey == "wc") {
				return true;
			}
			return false;
		},
		
		displayTextRange: function(oValue) {
			if (oValue == "parc" || oValue == "tsgc") {
				return true;
			}
			return false;
		},
		
		displayInputRange: function(oValue) {
			if (oValue == "tarc") {
				return true;
			}
			return false;
		},	
};