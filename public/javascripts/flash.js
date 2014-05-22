$(function(){
    var flash=$("#message-flash");
    if(flash.length > 0 ){
        if(flash.hasClass("auto-hide"))
            flash.hide().slideDown(1000).delay(5000).slideUp(1000);
        else{
            flash.hide().slideDown(1000);
            flash.find('.flash-close').click(function(e){
                e.preventDefault();
                flash.slideUp(1000);
            })
            }
    }
});