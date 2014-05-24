$(function(){
	__HTMLFlash_initAnimation(false);
});
/**
 * Build event on a evenutal flash message
 * @param ajax true if called by an ajax success response
 * @return
 */
function __HTMLFlash_initAnimation(ajax){
	var flash;
	if(!ajax)
		flash=$("div.flash");
	else
		flash=$("div.flash.ajax");
	console.log(flash);
    if(flash.length > 0 ){
        if(flash.hasClass("auto-hide")){
            flash.hide().slideDown(1000).delay(5000).slideUp({
            	duration:1000,
            	complete:function(){
            				flash.remove();
            			}
            });                   
        }
        else{
            flash.hide().slideDown(1000);
            flash.find('.flash-close').click(function(e){
                e.preventDefault();
                flash.slideUp({
                	duration:1000,
                	complete:function(){
                		flash.remove();
                	}
                });
                
            });
       }
    }
}

/**
 * Create a contextual message in the ul#scrum-menu
 * @param message
 * @param style    error, validation, warning or information
 * @param closable if true, a link will be displayed to close the flash
 * 					if false,the flash will disappeared automatically
 * @return
 */
function HTMLFlash_contextual(message, style, closable){
	__HTMLFlash_init(message, style, closable, window, $('#navigation'));
}

/**
 * Create a window flash message 
 * @param message
 * @param style    error, validation, warning or information
 * @param closable if true, a link will be displayed to close the flash
 * 					if false,the flash will disappeared automatically
 * @return
 */
function HTMLFlash_screen(message, style, closable){
	__HTMLFlash_init(message, style, closable, true, $('body'));
}

/**
 * Initialize a flash message with the given parameters
 * @param message
 * @param style
 * @param closable
 * @param window if true, it will be a window message, contextual otherwise
 * @param subject define in which element the message has to be prepend
 * @return
 */
function __HTMLFlash_init(message, style, closable, window, subject) {
	closableCSS = closable===true?'':'auto-hide';
	window = window === true?'window':'';
	var html =	'<div class="ajax flash ' + window + ' '
					+ style + ' ' + closableCSS + '">'
    if(closable === true) {
    	html += '<a href="#" class="flash-close">x</a>';
    }	 
	html += message + '</div>';
	var div = $(html).css('display','block');
	subject.append(div);
	__HTMLFlash_initAnimation(true);
}