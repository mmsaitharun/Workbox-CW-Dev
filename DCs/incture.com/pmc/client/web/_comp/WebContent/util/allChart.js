sap.ui.core.Control.extend('sap.dthree.allChart', {
	metadata: {
		properties: {
			width: {type: 'string', defaultValue: "100%"},
			height: {type: 'string', defaultValue: "100%"}
		}
	},

	init : function() {
		this.root = {};
	},

	setRoot : function(root) {
		this.root = root;
	},

	renderer : function(oRm, oControl) {
		oRm.write("<div");
		oRm.writeControlData(oControl);
//		oRm.addClass("sap-sharique-orgchart");
		oRm.writeClasses();
		oRm.write('>');
		oRm.write("</div>");
	},

	onAfterRendering: function() {

		var donuts = new DonutCharts();
		donuts.create("");

		function DonutCharts() {
			var charts = d3.select('#__xmlview2--donutPage');


			this.create = function(dataset) {
				var $charts = $('#__xmlview2--donutPage');
				var m = [20, 20, 30, 20],
				w = 560 - m[1] - m[3],
				h = 500 - m[0] - m[2];

				var svg = d3.select("#__xmlview2--donutPage").append("svg")
				.attr("width", w + m[1] + m[3])
				.attr("height", h + m[0] + m[2])

				margin = {top: 20, right: 20, bottom: 30, left: 40},
				width = +svg.attr("width") - margin.left - margin.right,
				height = +svg.attr("height") - margin.top - margin.bottom,
				g = svg.append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");

				var x = d3.scaleBand()
				.rangeRound([0, width])
				.paddingInner(0.05)
				.align(0.1);

				var y = d3.scaleLinear()
				.rangeRound([height, 0]);

				var z = d3.scaleOrdinal()
				.range(["#98abc5", "#8a89a6", "#7b6888", "#6b486b", "#a05d56", "#d0743c", "#ff8c00"]);

				d3.json("stocks.json", function(data) {
					var parse = d3.time.format("%b %Y").parse;

					data = [
					        {"State": "CA", "Under 5 Years": 2704659,"5 to 13 Years": 4499890,"14 to 17 Years": 2159981, "18 to 24 Years": 3853788,"total":13218318}
					        ,
					        {"State": "TX", "Under 5 Years": 2027307,"5 to 13 Years": 3277946,"14 to 17 Years": 1420518, "18 to 24 Years": 2454721,"total":13218319},
					        {"State": "NY", "Under 5 Years": 1208495,"5 to 13 Years": 2141490,"14 to 17 Years": 1058031, "18 to 24 Years": 1999120,"total":13218316}, 
					        {"State": "FL", "Under 5 Years": 1140516,"5 to 13 Years": 1938695,"14 to 17 Years": 925060, "18 to 24 Years": 1607297,"total":13218314},
					        {"State": "IL", "Under 5 Years": 894368,"5 to 13 Years": 1558919,"14 to 17 Years": 725973, "18 to 24 Years": 1311479,"total":13218315},
					        {"State": "PA", "Under 5 Years": 737462,"5 to 13 Years": 1345341,"14 to 17 Years": 679201, "18 to 24 Years": 1203944,"total":13218313}
					        ]             

					// Nest stock values by symbol.
					var keys =["Under 5 Years", "5 to 13 Years", "14 to 17 Years", "18 to 24 Years"];



					//  data.sort(function(a, b) { return b.total - a.total; });
					x.domain(data.map(function(d) { return d.State; }));
					y.domain([0, d3.max(data, function(d) { return d.total; })]).nice();
					z.domain(keys);

					g.append("g")
					.selectAll("g")
					.data(d3.stack().keys(keys)(data))
					.enter().append("g")
					.attr("fill", function(d) { return z(d.key); })
					.selectAll("rect")
					.data(function(d) { return d; })
					.enter().append("rect")
					.attr("x", function(d) { return x(d.data.State); })
					.attr("y", function(d) { return y(d[1]); })
					.attr("height", function(d) { return y(d[0]) - y(d[1]); })
					.attr("width", x.bandwidth());

					g.append("g")
					.attr("class", "axis")
					.attr("transform", "translate(0," + height + ")")
					.call(d3.axisBottom(x));

					g.append("g")
					.attr("class", "axis")
					.call(d3.axisLeft(y).ticks(null, "s"))
					.append("text")
					.attr("x", 2)
					.attr("y", y(y.ticks().pop()) + 0.5)
					.attr("dy", "0.32em")
					.attr("fill", "#000")
					.attr("font-weight", "bold")
					.attr("text-anchor", "start")
					.text("Population");

					var legend = g.append("g")
					.attr("font-family", "sans-serif")
					.attr("font-size", 10)
					.attr("text-anchor", "end")
					.selectAll("g")
					.data(keys.slice().reverse())
					.enter().append("g")
					.attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });

					legend.append("rect")
					.attr("x", width - 19)
					.attr("width", 19)
					.attr("height", 19)
					.attr("fill", z);

					legend.append("text")
					.attr("x", width - 24)
					.attr("y", 9.5)
					.attr("dy", "0.32em")
					.text(function(d) { return d; });
				});

			}        
		}
	},

});