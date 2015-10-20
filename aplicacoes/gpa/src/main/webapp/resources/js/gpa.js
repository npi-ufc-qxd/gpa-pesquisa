$(document).ready(function() {
	
	// Página Listar Projetos (Diretor)
	
	$('#meus-projetos').DataTable({
		"order" : [[ 0, 'desc' ]],
		"columnDefs" : [ 
            {"targets" : 3, "orderable" : false},
            {"targets" : 4, "orderable" : false}
		],
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$('#minhas-participacoes').DataTable({
		"order" : [[ 0, 'desc' ]],
		"columnDefs" : [ 
            {"targets" : 4, "orderable" : false},
            {"targets" : 5, "orderable" : false},
            {"targets" : 6, "orderable" : false},
            {"targets" : 7, "orderable" : false}
		],
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$('#projetos-avaliados').DataTable({
		"order" : [[ 0, 'desc' ]],
		"columnDefs" : [ 
            {"targets" : 2, "orderable" : false}
		],
		"bAutoWidth": false,
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$('#participantes-projetos').DataTable({
		"order" : [[ 0, 'asc' ]],
		"bAutoWidth": false,
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$('#projetos-aguardando-parecer').DataTable({
		"order" : [[ 0, 'desc' ]],
		"columnDefs" : [ 
            {"targets" : 2, "orderable" : false},
            {"targets" : 3, "orderable" : false},
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
            {"targets" : 3, "orderable" : false},
            {"targets" : 6, "orderable" : false}
		],
		"bAutoWidth": false,
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$("#inicio, #termino, #prazo").datepicker({
		format : "dd/mm/yyyy",
		todayBtn : "linked",
		language : "pt-BR",
		todayHighlight : true,
	}).on('changeDate', function(e) {
		$(this).datepicker('hide');
        $('#adicionarProjetoForm, #submeterProjetoForm, #atribuirPareceristaForm').bootstrapValidator('revalidateField', this.id);
    });
	
	$(".anexo").fileinput({
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
    
    if($('.anexoSubmeter').length){
    	if($('#table-anexos').find('tr').length){
    		$('#anexos').removeAttr('required');
    	}
    }
    $('.anexoSubmeter').change(function(){
    	$('#anexos').attr('required', 'true');
    });
    
	
	$('#confirm-submit').on('show.bs.modal', function(e) {
		$(this).find('.modal-body').text('Tem certeza de que deseja submeter o projeto \"' + $(e.relatedTarget).data('name') + '\"?');
		$(this).find('.btn-primary').attr('href', $(e.relatedTarget).data('href'));
	});
	
	$('#confirm-delete').on('show.bs.modal', function(e) {
		$(this).find('.modal-body').text('Tem certeza de que deseja excluir o projeto \"' + $(e.relatedTarget).data('name') + '\"?');
		$(this).find('.btn-danger').attr('href', $(e.relatedTarget).data('href'));
	});
	
	$('#confirm-delete-participacao').on('show.bs.modal', function(e) {
		$(this).find('.modal-body').text('Tem certeza de que deseja excluir o(a) participante \"' + $(e.relatedTarget).data('name') + '\"?');
		$(this).find('.btn-danger').attr('href', $(e.relatedTarget).data('href'));
	});
	
	$('#confirm-delete-file').on('show.bs.modal', function(e) {
		$(this).find('.modal-body').text('Tem certeza de que deseja excluir o arquivo \"' + $(e.relatedTarget).data('name') + '\"?');
		$(this).find('#button-delete-file').attr('data-id', $(e.relatedTarget).data('id'));
	});
	
	$('#button-delete-file').on('click', function(e) {
		e.preventDefault();
		var id = $(this).attr('data-id');
		$.ajax({
			type: "POST",
			url: "/gpa-pesquisa/documento/ajax/remover/" + id
		})
		.success(function( result ) {
			if(result.result == 'ok') {
				$('#documento-'+id).remove();
			}
			$('#confirm-delete-file').modal('hide');
		});
	});
	
	$("#participantes, #parecerista, #posicionamento, #avaliacao, #participante").select2({
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
	
	$('#submeterProjetoForm').bootstrapValidator({
		group: '.form-item',
		excluded: ':disabled',
        fields: {
            nome: {
                validators: {
                    stringLength: {
                        min: 5,
                        message: 'O nome deve ter no mínimo 5 caracteres'
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
	
	$('#atribuirPareceristaForm, #emitirParecerForm, #avaliarProjetoForm, #adicionarParticipacaoForm').bootstrapValidator({
		group: '.form-item',
		excluded: ':disabled',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        }
    });
	
	$('#comentarForm').bootstrapValidator({
		group: '.form-item',
		fields: {
			comentario: {
                validators: {
                	notEmpty: {
                        message: 'Escreva o seu comentário'
                    }
                }
            }
		}
	});
	
	// Comentários
	$('#comentar').click(function(e){
		e.preventDefault();
		var texto = $('#comentario').val();
	    var projetoId = $('#projetoId').val();
	    
	    $('#comentarForm').bootstrapValidator('validate')
	    
	    if(texto.length){
	    	$.ajax({
		    	type: "POST",
		        url: '/gpa-pesquisa/projeto/comentar',
		        data : {
		        	texto : texto,
		        	projetoId : projetoId
				}
		    })
		    .success(function(result) {
		    	if(result.comentario.id) {
		    		var data = moment(result.comentario.data).format('DD/MM/YYYY');
		    		var hora = moment(result.comentario.data).format('HH:mm');
		    		$('#comentario').val('');
		    		$('#comentarForm').bootstrapValidator('resetForm');
		    		$('#comentarios').append(
	    				'<div class="panel panel-default">' +
							'<div class="panel-heading">' + result.autor +
								'<span class="date-comment">' + data + ' - ' + hora + '</span>' +
							'</div>' +
							'<div class="panel-body">' + result.comentario.texto + '</div>' +
						'</div>'
		    		);
		    	}
		    });
	    }
	});
});
