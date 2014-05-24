/*
	JS from http://jsfiddle.net/jhfrench/GpdgF/
*/

$(function () {
    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
    $('.tree li.parent_li > span').on('click', function (e) {
        var children = $(this).parent('li.parent_li').find(' > ul > li');
        if (children.is(":visible")) {
            children.hide('fast');
            $(this).attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
        } else {
            children.show('fast');
            $(this).attr('title', 'Collapse this branch').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
        }
        e.stopPropagation();
    });
    
    $('.tree .sprint').on('click', function (e) {
		$('.sprint').removeClass('selected');
		$('.release').removeClass('selected');
		
		var sprint = $(this);    	
    	sprint.addClass('selected');
    	var release = $(this).closest('li.parent_li').find(' > span');
    	release.addClass('selected');
    	
    	
    	var currentReleaseSprint = new Object();
    	currentReleaseSprint["releaseName"] = release.attr('data-release');
    	currentReleaseSprint["sprintID"] = sprint.attr('data-sprint');
    	$.ajax({
			url: "/Product/setCurrentReleaseSprint",
			type:"POST",
			data:currentReleaseSprint
		})
		.success(function(){
			$('#releaseSelected').text(currentReleaseSprint["releaseName"]);
			$('#sprintSelected').text('Sprint ' + sprint.attr('data-sprintCurID') + ' - ' + sprint.parent().attr('title'));
			
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
});