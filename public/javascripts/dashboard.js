$(function () {
	$('#tabs a').click(function (e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	$('li.disabled a').attr("href", "#");	
})
 $(window).on('resize',function(){
	 if($( window ).width() > 750) {
		 console.log("tiit");
	 }
	 else {
		 $('button#sub-menu-display.navbar-toggle').click();		 
	 }
 });

  