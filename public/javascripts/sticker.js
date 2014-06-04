/*
 * Drop event used on dashboard for example
 */
$( ".sticker" ).draggable();
$( "#droppable" ).droppable({
	drop: function( event, ui ) {
			$( this )
				.addClass( "ui-state-highlight" )
				.find( "p" )
				.html( "Dropped!" );
	}
});