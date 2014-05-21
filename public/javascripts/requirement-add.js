var reqCounters = 1;
$('form').on("keydown",'input.requirement-req',function(e){	
	if(e.target === this && e.keyCode === 13) {
		e.preventDefault();
		e.stopPropagation();
		if($(this).val() != "") {
			var grp = $(this).parent().parent();
			var newGrp = grp.clone();
			/*Place label link on the new input */
			grp.prev("label").attr("for","req" + (++reqCounters));			
			var newReq = newGrp.find("input")
								.val("")
								.attr("id","req" + reqCounters)
								.prev("label")
								.attr("for","req" + reqCounters)
								.text("Exigence " + reqCounters);
			console.log($("div.slider"));
	        newGrp.find("div.slider").remove();
			var select1 = newGrp.find("select.slider-duration")
				  				.attr("id","req-priority-" + reqCounters);
			var select2 = newGrp.find("select.slider-priority")
				  				.attr("id","req-duration-" + reqCounters);
			var slider1 = $("<div class=\"slider\" id=\"slider" + reqCounters + "1\"></div>")
							.insertAfter(select1).slider( {
								min : 1,
								max : 7,	
								range : "min",
								value : select1[0].selectedIndex + 1,
								slide : function(event, ui) {
									console.log(select1);
								select1[0].selectedIndex = ui.value - 1;
							}
							});
			select1.change(function() {
				console.log()
				slider1.slider("value", this.selectedIndex + 1);
			});
			var slider2 = $("<div id=\"slider" + reqCounters + "2\" class=\"slider\"></div>").insertAfter(select2).slider( {
				min : 1,
				max : 5,	
				range : "min",
				value : select2[0].selectedIndex + 1,
				slide : function(event, ui) {
					select2[0].selectedIndex = ui.value - 1;
				}
			});
			select2.change(function() {
				slider2.slider("value", this.selectedIndex + 1);
			});
			grp.after(newGrp);
			newReq.focus();
		}
	}	
});
var select1 = $( ".slider-duration" );
var slider1 = $("<div id=\"slider1\" class=\"slider\"></div>").insertAfter(select1).slider( {
	min : 1,
	max : 7,	
	range : "min",
	value : select1[0].selectedIndex + 1,
	slide : function(event, ui) {
		select1[0].selectedIndex = ui.value - 1;
	}
});
select1.change(function() {
	slider1.slider("value", this.selectedIndex + 1);
});
var select2 = $(".slider-priority");
var slider2 = $("<div id=\"slider2\" class=\"slider\"></div>").insertAfter(select2).slider( {
	min : 1,
	max : 5,	
	range : "min",
	value : select2[0].selectedIndex + 1,
	slide : function(event, ui) {
		select2[0].selectedIndex = ui.value - 1;
	}
});
select2.change(function() {
	slider2.slider("value", this.selectedIndex + 1);
});