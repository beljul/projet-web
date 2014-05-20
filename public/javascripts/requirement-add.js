var reqCounters = 1;
$('form').on("keydown",'input.requirement-req',function(e){	
	if(e.target === this && e.keyCode === 13) {
		e.preventDefault();
		e.stopPropagation();
		if($(this).val() != "") {
			var inputGrp = $(this).parent();
			var newGrp = inputGrp.clone();
			/*Place label link on the new input */
			inputGrp.prev("label").attr("for","req" + (++reqCounters));			
			var newReq = newGrp.find("input").val("").attr("id","req" + reqCounters)
					  .prev("label")
					  .attr("for","req" + reqCounters)
					  .text("Exigence " + reqCounters);			
			inputGrp.after(newGrp);
			newReq.focus();
		}
	}	
});