/*
 * Verification of login form
 */

$(function () {
	$("form[action='/user/register']").checkform({		
		CSSClass:"alert alert-danger",
		items:{
			password: {
				selector:'[name=firstPassword], [name=secondPassword]'
			}
		}
	});
	$("form[action='/secure/authenticate']").checkform({
		type:"flash",
		CSSClass:"flash window flashError",
		items:{
			mail: {
				selector:'[name=username]'
			},
			password: {
				selector:'[name=password]'
			}
		}
	});
});


