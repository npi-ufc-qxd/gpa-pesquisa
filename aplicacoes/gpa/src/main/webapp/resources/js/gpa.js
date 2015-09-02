$(document).ready(function() {
	
	// Página Listar Projetos (Diretor)
	
	$('#projetos-diretor').DataTable({
		"order" : [[ 0, 'asc' ]],
		"columnDefs" : [ 
            {"targets" : 2, "orderable" : false},
            {"targets" : 3, "orderable" : false}
		],
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$('#projetos-avaliados').DataTable({
		"order" : [[ 0, 'asc' ]],
		"columnDefs" : [ 
            {"targets" : 2, "orderable" : false}
		],
		"bAutoWidth": false,
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$('#projetos-em-participacao').DataTable({
		"order" : [[ 0, 'asc' ]],
		"columnDefs" : [ 
            {"targets" : 2, "orderable" : false},
            {"targets" : 4, "orderable" : false}
		],
		"bAutoWidth": false,
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$('#projetos-em-tramitacao').DataTable({
		"order" : [[ 0, 'asc' ]],
		"columnDefs" : [ 
            {"targets" : 2, "orderable" : false},
            {"targets" : 5, "orderable" : false},
            {"targets" : 6, "orderable" : false}
		],
		"bAutoWidth": false,
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$("#inicio, #termino").datepicker({
		format : "dd/mm/yyyy",
		todayBtn : "linked",
		language : "pt-BR",
		todayHighlight : true,
	}).on('changeDate', function(e) {
		$(this).datepicker('hide');
        $('#adicionarProjetoForm, #submeterProjetoForm').bootstrapValidator('revalidateField', 'inicio');
    });
	
	// Usado para não apagar a máscara e enviar somente o valor para o servidor
	$("#adicionarProjetoForm, #submeterProjetoForm").submit(function() {
		$('#valorDaBolsa').val($('#bolsa').maskMoney('unmasked')[0]);
	});
	
	// Máscaras
    $('[name="bolsa"]').maskMoney({prefix:'R$ ', showSymbol:true, thousands:'.', decimal:','});
    if($('[name="bolsa"]').val() != '') {
    	$('[name="bolsa"]').maskMoney('mask');
    }
    
    $("#anexos").fileinput({
    	uploadUrl: "/file-upload-batch/2",
    	showUpload:false,
    	showRemove: false,
    	language: 'pt-BR',
    	uploadAsync: false,
    	layoutTemplates: {
	        actions: '<div class="file-actions">\n' +
	        '    <div class="file-footer-buttons">\n' +
	        '        {delete}' +
	        '    </div>\n' +
	        '    <div class="clearfix"></div>\n' +
	        '</div>'
    	}
    });
    
	
	$('#confirm-submit').on('show.bs.modal', function(e) {
		$(this).find('.modal-body').text('Tem certeza de que deseja submeter o projeto \"' + $(e.relatedTarget).data('name') + '\"?');
		$(this).find('.btn-primary').attr('href', $(e.relatedTarget).data('href'));
	});
	
	$('#confirm-delete').on('show.bs.modal', function(e) {
		$(this).find('.modal-body').text('Tem certeza de que deseja excluir o projeto \"' + $(e.relatedTarget).data('name') + '\"?');
		$(this).find('.btn-danger').attr('href', $(e.relatedTarget).data('href'));
	});
	
	$("#participantes, #parecerista, #posicionamento, #avaliacao").select2({
   	 	placeholder: "Buscar...",
   	 	dropdownCssClass: "bigdrop"
    });
	
	// Validações de formulários
	
	$('#adicionarProjetoForm').bootstrapValidator({
		group: '.form-item',
        feedbackIcons: {
            valid: false,
        	invalid: 'glyphicon'
        },
        fields: {
            nome: {
                validators: {
                    stringLength: {
                        min: 2,
                        message: 'O nome deve ter no mínimo 2 caracteres'
                    }
                }
            },
            descricao: {
                validators: {
                    stringLength: {
                        min: 5,
                        message: 'A descrição deve ter no mínimo 5 caracteres'
                    }
                }
            },
            quantidadeBolsa: {
            	validators: {
            		integer: {
                        message: 'Digite um número válido'
                    }
            	}
            },
            cargaHoraria: {
            	validators: {
            		integer: {
                        message: 'Digite um número válido'
                    }
            	}
            },
            inicio :{
            	validators: {
            		callback: {
                        message: 'A data de início deve ser anterior à data de término',
                        callback: function(value, validator) {
                        	var termino = validator.getFieldElements('termino').val();
                        	if(value != "" && termino != "") {
                        		termino = moment(termino, "DD/MM/YYYY").format("DD/MM/YYYY");
	                        	var inicio = moment(value, "DD/MM/YYYY").format("DD/MM/YYYY");
	                        	if(moment(termino, "DD/MM/YYYY").isBefore(moment(inicio, "DD/MM/YYYY"))) {
	                        		return false;
	                        	}
                        	}
                        	return true;
                        }
                    }
            	}
            }
            
        }
    });
	
});