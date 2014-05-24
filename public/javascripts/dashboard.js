$(function () {
	$('#tabs a').click(function (e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	$('li.disabled a').attr("href", "#");
	HTMLFlash_contextual("mevoici", "validation", false);
})
  

  