$(function () {
	$.ajax({
		url: "/Document/getRequirements",
		type:"GET"
	})
	.success(function(requirements){
		var data = [];
		var i = 0;
		for(content in requirements) {
			var row = {};
			row["Exigence"] = content;
			row["Durée"] = requirements[content];
			data.push(row);
		}
		var chart = AmCharts.makeChart("chartdiv", {
		    "type": "pie",	
			"theme": "none",
		    "legend": {
		        "markerType": "circle",
		        "position": "right",
				"marginRight": 80,		
				"autoMargins": false
		    },
		    "dataProvider": data,
		    "valueField": "Durée",
		    "titleField": "Exigence",
		    "balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
		    "exportConfig": {
		        "menuTop":"0px",
		        "menuItems": [{
		            "icon": '/lib/3/images/export.png',
		            "format": 'png'
		        }]
		    }
		});
	})
	.done(function(d) {
		console.log(d);
	})
	.fail(function() {
		alert( "error" );
	})
	.always(function() {
		});
	
	
})