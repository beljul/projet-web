$(function () {
	$('#tabs a').click(function (e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	/*Disable not allowed pages access*/ 
	var msg = "Vous n'êtes pas autorisé à accéder à cette fonctionnalité"
	$('li.disabled a').attr("href", "#").attr("title",msg);	
})
 $(window).on('resize',function(){
	 if($( window ).width() > 750) {
		 console.log("tiit");
	 }
	 else {
		 $('button#sub-menu-display.navbar-toggle').click();		 
	 }
 });

  