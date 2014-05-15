var select = $( "#sprint-duration" );
var slider = $("<div id='slider'></div>").insertAfter(select).slider( {
	min : 1,
	max : 6,	
	range : "min",
	value : select[0].selectedIndex + 1,
	slide : function(event, ui) {
		select[0].selectedIndex = ui.value - 1;
	}
});
$("#sprint-duration").change(function() {
	slider.slider("value", this.selectedIndex + 1);
});
$('form').on("keydown",'input.product-dev',function(e){	
	if(e.target === this && e.keyCode === 13) {
		e.preventDefault();
		e.stopPropagation();
		var inputDev = $(this);
		var newDev = inputDev.clone().autocomplete({
			source:'/User/jsonSearch?email=kev',	
			response: function( ul, item ) {
				console.log(item);
			}
		});
		inputDev.after(newDev.val(""));
		newDev.focus();		
	}	
});
$( ".product-dev" ).autocomplete({
	source:'/User/jsonSearch?email=kev',	
	response: function( ul, item ) {
		console.log(item);
	}
});