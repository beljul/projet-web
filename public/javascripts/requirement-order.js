$( "#sortable" ).sortable();
$( "#sortable" ).disableSelection();
$("input#save-order").on("click",function(){
	var requirementsId = [];	
	$("#sortable").children().each(function(){
		requirementsId.push($(this).attr("data-id"));
	});	
	$.ajax({
		url: "/User/jsonSearch",
		data:requirementsId;
		beforeSend:function(){
		}
	})
	.done(function() {
	alert( "success" );
	})
	.fail(function() {
	alert( "error" );
	})
	.always(function() {
	alert( "complete" );
	});
});