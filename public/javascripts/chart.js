$(function () {
	var chart = AmCharts.makeChart("chartdiv", {
	    "type": "serial",
		"theme": "none",
	    "pathToImages": "http://www.amcharts.com/lib/3/images/",
	    "dataProvider": [{
	        "lineColor": "#b7e021",
	        "date": "2012-01-01",
	        "duration": 408
	    }, {
	        "date": "2012-01-02",
	        "duration": 482
	    }, {
	        "date": "2012-01-03",
	        "duration": 562
	    }, {
	        "date": "2012-01-04",
	        "duration": 379
	    }, {
	        "lineColor": "#fbd51a",
	        "date": "2012-01-05",
	        "duration": 501
	    }, {
	        "date": "2012-01-06",
	        "duration": 443
	    }, {
	        "date": "2012-01-07",
	        "duration": 405
	    }, {
	        "date": "2012-01-08",
	        "duration": 309,
	        "lineColor": "#2498d2"
	    }, {
	        "date": "2012-01-09",
	        "duration": 287
	    }, {
	        "date": "2012-01-10",
	        "duration": 485
	    }, {
	        "date": "2012-01-11",
	        "duration": 890
	    }, {
	        "date": "2012-01-12",
	        "duration": 810
	    }],
	    "balloon": {
	        "cornerRadius": 6
	    },
	    "valueAxes": [{
	        "duration": "mm",
	        "durationUnits": {
	            "hh": "h ",
	            "mm": "min"
	        },
	        "axisAlpha": 0
	    }],
	    "graphs": [{
	        "bullet": "square",
	        "bulletBorderAlpha": 1,
	        "bulletBorderThickness": 1,
	        "fillAlphas": 0.3,
	        "fillColorsField": "lineColor",
	        "legendValueText": "[[value]]",
	        "lineColorField": "lineColor",
	        "title": "duration",
	        "valueField": "duration"
	    }],
	    "chartScrollbar": {},
	    "chartCursor": {
	        "categoryBalloonDateFormat": "YYYY MMM DD",
	        "cursorAlpha": 0,
	        "zoomable": false
	    },
	    "dataDateFormat": "YYYY-MM-DD",
	    "categoryField": "date",
	    "categoryAxis": {
	        "dateFormats": [{
	            "period": "DD",
	            "format": "DD"
	        }, {
	            "period": "WW",
	            "format": "MMM DD"
	        }, {
	            "period": "MM",
	            "format": "MMM"
	        }, {
	            "period": "YYYY",
	            "format": "YYYY"
	        }],
	        "parseDates": true,
	        "autoGridCount": false,
	        "axisColor": "#555555",
	        "gridAlpha": 0,
	        "gridCount": 50
	    }
	});
})