$(document).ready(function() {
	$('#avaliarProjetoForm').bootstrapValidator({
		feedbackIcons: {
        	valid: 'glyphicon glyphicon-ok',
            validating: 'glyphicon glyphicon-refresh'
	    },
		fields: {
			ataParam: {
                validators: {
                	notEmpty: {
                        message: 'Por favor, insira um arquivo.'
                    }
                }
            },
            oficioParam: {
                validators: {
                	notEmpty: {
                        message: 'Por favor, insira um arquivo.'
                    }
                }
            }
		}
	});
});