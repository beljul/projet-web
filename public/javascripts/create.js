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
var devCounters = 1;
$('form').on("keydown",'input.product-dev',function(e){	
	if(e.target === this && e.keyCode === 13) {
		e.preventDefault();
		e.stopPropagation();
		if($(this).val() != "") {
			var inputGrp = $(this).parent();
			var newGrp = inputGrp.clone();
			/*Place label link on the new input */
			inputGrp.prev("label").attr("for","dev" + (++devCounters));			
			var newDev = newGrp.find("input").customAutocomplete()
					  .val("").attr("id","dev" + devCounters)
					  .prev("label")
					  .attr("for","dev" + devCounters)
					  .text("Développeur " + devCounters);			
			inputGrp.after(newGrp);
			newDev.focus();
		}
	}	
});
var cache = {};
$.fn.customAutocomplete = function() {
	this.autocomplete({
		 source:"/User/jsonSearch",
		 focus: function( event, ui ) {
			console.log(ui.item.label);
			$(this).val( ui.item.label );
			return false;
		 },
		 dataType: 'json',
		 select: function( event, ui ) {
//			 $( "#project" ).val( ui.item.label );
//			 $( "#project-id" ).val( ui.item.value );
//			 $( "#project-description" ).html( ui.item.desc );
//			 $( "#project-icon" ).attr( "src", "images/" + ui.item.icon );
			 return false;		 
	}}).data( "ui-autocomplete" )._renderItem =  function( ul, item ) {
		$(this).data("dev-id",item.id)				
 		return $( "<li>" )
 			.attr( "data-value", item.value )
 			.append( $( "<a>" ).text( item.label ) )
 			.appendTo( ul );
	 	
	};
	return this;
}
$( ".product-dev" ).customAutocomplete();