/*
 * Sort of requirements during assignment
 * to a specific sprint
 */
$( ".sortable" ).sortable({
	connectWith:".sortable",
	dropOnEmpty: true 
});
$( ".sortable" ).disableSelection();

/*
 * Record requirements to the sprint selected
 */
$("input#save-assign").on("click",function(){
	var requirementsId = new Object();
	var i = 0;
	$("#requirement-sprint").children().each(function(){
		requirementsId[i] = $(this).attr('data-id');
		i = i+1;
	});
	var listReq = new Object();
	listReq["requirements"] = requirementsId; 
	// Ajax request to add each requirement
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

/*
 * Reload all requirements if an other sprint
 * has been selected during the assignment
 */
$(".sprint").on("click", function() {
	var listReq = new Object();
	listReq["sprintId"] = $(this).attr('data-sprint');
	// Ajax request to get requirements
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
	// Ajax request to get requirements not already assigned
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