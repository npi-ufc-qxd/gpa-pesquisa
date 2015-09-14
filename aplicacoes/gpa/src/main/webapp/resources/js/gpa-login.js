$(document).ready(function() {

	$('#loginForm').bootstrapValidator({
		group: '.form-item'
    });
	
	/*$('#loginForm').submit(function(){
		$('#loginForm').bootstrapValidator('validate');
	});
	
	$('#loginForm').bootstrapValidator({
		group: '.form-item',
		fields: {
			j_username: {
                validators: {
                	notEmpty: {
                        message: 'Campo obrigatório'
                    }
                }
            },
            j_password: {
                validators: {
                	notEmpty: {
                        message: 'Campo obrigatório'
                    }
                }
            }
		}
	});*/
	
});