$('.sortable').sortable({
	connectWith:'.sortable'
})
$( ".sortable" ).disableSelection();
$( ".slider" ).slider({
	change:function(e,ui){
		$(this).siblings('span.badge').text(ui.value + '%');
		$(this).parent().attr("data-win-rate",ui.value);
	},
	step: 5 
});
$( ".slider" ).each(function(){
	$(this).slider( "option", "value",$(this).parent().attr("data-win-rate"));
});
$(".sticker span.display-info").on("click", function(){
	$(this).siblings('div.complementary-info').toggle({
			duration:200
		}
	);
});
$("#save-progression").on("click", function(){
	var newStates = new Object();
	var newWinRates = new Object();
	$('ul.task-section li').each(function(){
		newStates[$(this).attr("data-id")] = $(this).parent().attr("data-state");
		newWinRates[$(this).attr("data-id")] = $(this).attr("data-win-rate");
	});
	var states = new Object(); 
	states["states"] = newStates;
	states["winrates"] = newWinRates;
	console.log(states);
	$.ajax({
		url:"/sprint/saveProgression",
		data:states
	})
	.success(function(e){
		 HTMLFlash_contextual("Avancement sauvegardé","flashValidation", false);
	});
		
});