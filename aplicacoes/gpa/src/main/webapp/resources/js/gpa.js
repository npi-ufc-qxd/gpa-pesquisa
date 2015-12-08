$(document).ready(function() {
	
	$('#meus-projetos').DataTable({
		"order" : [[ 0, 'desc' ]],
		"columnDefs" : [ 
		    {className: "dt-center", "targets": [ 0, 3,]},            
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
		    {className: "dt-center", "targets": [ 0, 1, 4, 5, 6, 7]},
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
		    {className: "dt-center", "targets": [ 0, 3, 4]},            
            {"targets" : 3, "orderable" : false},
		    {"targets" : 4, "orderable" : false}
		],
		"bAutoWidth": false,
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$('#projetos-avaliados-diretor').DataTable({
		"order" : [[ 0, 'desc' ]],
		"columnDefs" : [ 
		    {className: "dt-center", "targets": [ 0, 3]},            
            {"targets" : 3, "orderable" : false}
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
		    {className: "dt-center", "targets": [0, 1, 3, 4]},
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
		    {className: "dt-center", "targets": [0, 3]},             
            {"targets" : 3, "orderable" : false},
            {"targets" : 6, "orderable" : false}
		],
		"bAutoWidth": false,
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	// Página Vincular Participantes
	$('#participacoes-projeto').DataTable({
		"order" : [[ 0, 'desc' ]],
		"columnDefs" : [ 
		    {className: "dt-center", "targets": [ 0]},
            {"targets" : 1, "orderable" : false},
            {"targets" : 2, "orderable" : false},
            {"targets" : 3, "orderable" : false},
            {"targets" : 4, "orderable" : false},
            {"targets" : 5, "orderable" : false}
		],
		"searching":false,
		"paging":false,
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$('#participantes-table').DataTable({
		
		"searching":false,
		"paging":false,
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
			url: "/gpa-pesquisa/documento/excluir/" + id
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
	
	$('#atribuirPareceristaForm, #emitirParecerForm, #adicionarParticipacaoForm').bootstrapValidator({
		group: '.form-item',
		excluded: ':disabled',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        }
    });
	
	//Avaliar projeto
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
	
	// Persiste aba com link na URL
	var abaAtual = $('.nav-tabs');
	abaAtual.on('click', 'a', function(e){
		var $this = $(this);
		e.preventDefault();
		window.location.hash = $this.attr('href');
		$this.tab('show');
	});
	function atualizaHash() {
		abaAtual.find('a[href="'+ window.location.hash +'"]').tab('show');
	}
	$(window).bind('hashchange', atualizaHash);
	if(window.location.hash){
		atualizaHash();
	}
	
	$('.participanteCoordena').DataTable({
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        },
        "columnDefs": [ 
            {"targets": 2, "orderable": false},
            {"targets": 3, "orderable": false}
		],
		"autoWidth": false
	});
	
	$('.participanteParticipa').DataTable({
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        },
        "columnDefs": [ 
            {"targets": 3, "orderable": false},
            {"targets": 4, "orderable": false}
		],
		"autoWidth": false
	});

	//RELATORIOS
	$("#form_aprovados").hide();
	$("#form_reprovados").hide();
	$("#form_p-pessoa").hide();
		
	$("#relatorio").change(function() {
		$("#form_aprovados").hide();
		$("#form_reprovados").hide();
		$("#form_p-pessoa").hide();
		var opcao_select = $("#relatorio option:selected").text();
		if (opcao_select == "PROJETOS APROVADOS") {
			$("#form_aprovados").slideToggle("slow");
		}
		if (opcao_select == "PROJETOS REPROVADOS") {
			$("#form_reprovados").slideToggle("slow");
		}
		if (opcao_select == "PROJETOS POR USUÁRIO") {
			$("#form_p-pessoa").slideToggle("slow");
		}
	});
	$('#relatoriosAprovadosForm').bootstrapValidator({
        feedbackIcons: {
        	valid: 'glyphicon glyphicon-ok',
        	invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	inicio :{
            	validators: {
            		callback: {
                        message: 'A data de início deve ser anterior à data de término',
                        callback: function(value, validator) {
                        	var termino = validator.getFieldElements('termino').val();
                        	if(value != "" && termino != "") {
                        		termino = moment(termino, "YYYY-MM").format("YYYY-MM");
	                        	var inicio = moment(value, "YYYY-MM").format("YYYY-MM");
	                        	if(moment(termino, "YYYY-MM").isBefore(moment(inicio, "YYYY-MM"))) {
	                        		return false;
	                        	}
                        	}
                        	return true;
                        }
                    }
            	}
        	},
	        termino: {
	        	validators:{
	        		callback: {
	                    message: 'A data de término deve ser posterior à data de início',
	                    callback: function(value, validator) {
	                    	var inicio = validator.getFieldElements('inicio').val();
	                    	if(value != "" && inicio != "") {
	                    		inicio = moment(inicio, "YYYY-MM").format("YYYY-MM");
	                        	var termino = moment(value, "YYYY-MM").format("YYYY-MM");
	                        	if(moment(inicio, "YYYY-MM").isAfter(moment(termino, "YYYY-MM"))) {
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
	 $("#inicioRelatorio").datepicker({
			format : "yyyy-mm",
			todayBtn : "linked",
			language : "pt-BR",
			viewMode: "months", 
		    minViewMode: "months",
			todayHighlight : true
		}).on('changeDate', function(e) {
			$(this).datepicker('hide');
			$('#relatoriosAprovadosForm').bootstrapValidator('revalidateField', 'inicio');
			$('#relatoriosAprovadosForm').bootstrapValidator('revalidateField', 'termino');
	    });
	 
	 $("#submissaoRelatorio").datepicker({
			format : "yyyy-mm",
			todayBtn : "linked",
			language : "pt-BR",
			viewMode: "months", 
		    minViewMode: "months",
			todayHighlight : true
		}).on('changeDate', function(e) {
			$(this).datepicker('hide');
	    });
	 
	 $("#terminoRelatorio").datepicker({
			format : "yyyy-mm",
			todayBtn : "linked",
			language : "pt-BR",
			viewMode: "months", 
		    minViewMode: "months",
			todayHighlight : true
		}).on('changeDate', function(e) {
			$(this).datepicker('hide');
			$('#relatoriosAprovadosForm').bootstrapValidator('revalidateField', 'inicio');
			$('#relatoriosAprovadosForm').bootstrapValidator('revalidateField', 'termino');
	    });
	 $("#anoRelatorio").datepicker({
			format : "yyyy",
			todayBtn : "linked",
			language : "pt-BR",
			viewMode: "years", 
		    minViewMode: "years",
			todayHighlight : true
		}).on('changeDate', function(e) {
			$(this).datepicker('hide');
	    });
	
	$('#busca-adm').DataTable({
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        },
        "columnDefs": [ 
            {"targets": 1, "orderable": false},
            {"targets": 2, "orderable": false}
		],
		"autoWidth": false
	});
	$('#busca-participante').DataTable({
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        },
        "columnDefs": [ 
            {"targets": 1, "orderable": false},
		],
		"autoWidth": false
	});
	
	//Relatórios por pessoa
	$('#relatoriosPPessoaForm').bootstrapValidator({
		fields: {
			id: {
                validators: {
                	notEmpty: {
                        message: 'Selecione um nome para efetuar a busca.'
                    }
                }
            }
		}
	});
});
