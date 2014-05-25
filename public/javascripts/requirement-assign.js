$( ".sortable" ).sortable({
	connectWith:".sortable",
	dropOnEmpty: true 
});
$( ".sortable" ).disableSelection();
$("input#save-assign").on("click",function(){
	var requirementsId = new Object();
	var i = 0;
	$("#requirement-sprint").children().each(function(){
		requirementsId[i] = $(this).attr('data-id');
		i = i+1;
	});
	var listReq = new Object();
	listReq["requirements"] = requirementsId; 

	$.ajax({
		url: "/Sprint/addRequirements",
		type:"POST",
		data:listReq,
		beforeSend:function(){
		}
	})
	.success(function(){
		HTMLFlash_contextual("Les exigences ont été assignées au sprint courant","flashValidation",false);
	})
	.done(function(d) {
		console.log(d);
	})
	.fail(function() {
	alert( "error" );
	})
	.always(function() {
	});
});
$(".sprint").on("click", function() {
	var listReq = new Object();
	listReq["sprintId"] = $(this).attr('data-sprint'); 
	$.ajax({
		url: "/Sprint/getRequirements",
		type:"POST",
		data:listReq,
		beforeSend:function(){
		}
	})
	.success(function(data){
		console.log(data);
		$("#requirement-sprint").children().remove();
		for(var i in data) {
			$("<li>").attr("data-id", data[i].id)
			 .attr("class", "ui-state-default")
			 .html(data[i].content)
			 .appendTo("#requirement-sprint");
		}
	})
	.done(function(d) {
		console.log(d);
	})
	.fail(function() {
	alert( "error" );
	})
	.always(function() {
	});
	
	$.ajax({
		url: "/Sprint/getRequirementsAvailable",
		type:"POST",
		data:listReq,
		beforeSend:function(){
		}
	})
	.success(function(data){
		console.log(data);
		$("#requirement-available").children().remove();
		for(var i in data) {
			$("<li>").attr("data-id", data[i].id)
			 .attr("class", "ui-state-default")
			 .html(data[i].content)
			 .appendTo("#requirement-available");
		}
	})
	.done(function(d) {
		console.log(d);
	})
	.fail(function() {
	alert( "error" );
	})
	.always(function() {
	});
});