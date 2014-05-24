$( ".sortable" ).sortable({
	connectWith:".sortable",
	dropOnEmpty: true 
});
$( ".sortable" ).disableSelection();
$("input#save-order").on("click",function(){
	var requirementsId = new Object();	
	$(".sortable").children().each(function(){
		var newPriority = $(this).parent().attr("data-priority");
		requirementsId[$(this).attr('data-id')] = newPriority;
	});
	var listReq = new Object();
	listReq["requirements"] = requirementsId; 

	$.ajax({
		url: "/Product/changeRequirementsOrder",
		type:"POST",
		data:listReq,
		beforeSend:function(){
		}
	})
	.success(function(){
		window.location.replace("/Application/dashboard");
	})
	.done(function(d) {
		console.log(d);
	})
	.fail(function() {
	alert( "error" );
	})
	.always(function() {
	alert( "complete" );
	});
});