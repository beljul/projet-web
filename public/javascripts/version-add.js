var select = $( ".slider-nbSprints" );
var slider = $("<div id=\"slider\" class=\"slider\"></div>").insertAfter(select).slider( {
	min : 1,
	max : 10,	
	range : "min",
	value : select[0].selectedIndex + 1,
	slide : function(event, ui) {
		select[0].selectedIndex = ui.value - 1;
	}
});
select.change(function() {
	slider.slider("value", this.selectedIndex + 1);
});