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
		    {className: "dt-center", "targets": [ 0, 4, 5, 6]},
            {"targets" : 4, "orderable" : false},
            {"targets" : 5, "orderable" : false},
            {"targets" : 6, "orderable" : false},
            { "width": "30%", "targets": [5,4]}
		],
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$('#projetos-homologados').DataTable({
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
	
	$('#projetos-homologados-diretor').DataTable({
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
	
	$('#projetos-parecer-emitido').DataTable({
		"order" : [[ 0, 'desc' ]],
		"columnDefs" : [ 
		    {className: "dt-center", "targets": [ 0, 1, 3, 4 ]},            
            {"targets" : 1, "orderable" : false},
		    {"targets" : 4, "orderable" : false}
		],
		"bAutoWidth": false,
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$('#projetos-aguardando-avaliacao').DataTable({
		"order" : [[ 0, 'desc' ]],
		"columnDefs" : [ 
		    {className: "dt-center", "targets": [0, 1]},
            {"targets" : 1, "orderable" : false},
            {"targets" : 3, "orderable" : false}
		],
		"bAutoWidth": false,
		"language": {
            "url": "/gpa-pesquisa/resources/js/Portuguese-Brasil.json"
        }
	});
	
	$('#projetos-avaliados').DataTable({
		"order" : [[ 0, 'desc' ]],
		"columnDefs" : [ 
		    {className: "dt-center", "targets": [0, 1, 3, 4]},
            {"targets" : 1, "orderable" : false},
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
		if($(e.relatedTarget).data('name') == "")
			$(this).find('.modal-body').text('Tem certeza de que deseja excluir o(a) participante \"' + $(e.relatedTarget).data('name-externo') + '\"?');
		else
			$(this).find('.modal-body').text('Tem certeza de que deseja excluir o(a) participante \"' + $(e.relatedTarget).data('name') + '\"?');
		$(this).find('.btn-danger').attr('href', $(e.relatedTarget).data('href'));
	});
	
	testaTabelaFonteFinanciamentoVazia();
	
	function testaTabelaFonteFinanciamentoVazia(){
		mensagem = "Nenhuma fonte de financiamento cadastrada.";
		if($('#table-fontes-financiamento tr').length){
			$('#div-mensagem').addClass('hidden');
		}else{
			mostraMensagem(mensagem);
		}
	}
	
	$('#button-mensagem').on('click', function(){
		$('#div-mensagem').addClass('hidden');
	});
	
	function mostraMensagem(mensagem){
		$('#mensagem').html(mensagem);
		$('#div-mensagem').removeClass('hidden');
	}
	
	$('#confirm-delete-fonte-financiamento').on('show.bs.modal', function(e) {
		$(this).find('.modal-body').text('Tem certeza de que deseja excluir a Fonte de Financiamento: \"' + $(e.relatedTarget).data('name') + '\"?');
		$(this).find('#button-delete-fonte-financiamento').attr('data-id', $(e.relatedTarget).data('id'));
	});
	
	$('#button-delete-fonte-financiamento').on('click', function(e) {
		e.preventDefault();
		var id = $(this).attr('data-id');
		var fonteFinanciamentoId = $('#id').val();
		$.ajax({
			type: "POST",
			url: "/gpa-pesquisa/administracao/fonte-financiamento/excluir/" + id,
			data:{
				fonteFinanciamentoId:fonteFinanciamentoId
			}
		})
		.success(function( result ) {
			if(result.result == 'ok') {
				$('#fonte-'+id).remove();
				testaTabelaFonteFinanciamentoVazia();
				$('#confirm-delete-fonte-financiamento').modal('hide');
			}else{
				$('#confirm-delete-fonte-financiamento').modal('hide');
				mostraMensagem(result.mensagem);
			}
			
		});
	});
	
	$('#confirm-delete-file').on('show.bs.modal', function(e) {
		$(this).find('.modal-body').text('Tem certeza de que deseja excluir o arquivo \"' + $(e.relatedTarget).data('name') + '\"?');
		$(this).find('#button-delete-file').attr('data-id', $(e.relatedTarget).data('id'));
	});
	
	$('#button-delete-file').on('click', function(e) {
		e.preventDefault();
		var id = $(this).attr('data-id');
		var projetoId = $('#id').val();
		$.ajax({
			type: "POST",
			url: "/gpa-pesquisa/documento/excluir/" + id,
			data:{
				projetoId:projetoId
			}
		})
		.success(function( result ) {
			if(result.result == 'ok') {
				$('#documento-'+id).remove();
			}
			$('#confirm-delete-file').modal('hide');
		});
	});
	
	$('#confirm-delete-p-file').on('show.bs.modal', function(e) {
		$(this).find('.modal-body').text('Tem certeza de que deseja excluir o arquivo \"' + $(e.relatedTarget).data('name') + '\"?');
		$(this).find('#button-delete-p-file').attr('data-idProjeto', $(e.relatedTarget).data('idprojeto'));
	});
	
	$('#button-delete-p-file').on('click', function(e) {
		e.preventDefault();
		var idProjeto = $(this).attr('data-idProjeto');
		$.ajax({
			type: "POST",
			url: "/gpa-pesquisa/documento/excluir-arquivo-projeto/" + idProjeto,
		})
		.success(function( result ) {
			if(result.result == 'ok') {
				$('#table-arquivo-projeto').remove();
				$('#campo-arquivo-projeto').removeClass('hidden');
			}
			$('#confirm-delete-p-file').modal('hide');
			
		});
	});
	
	if($('#table-arquivo-projeto').length){
		$('#campo-arquivo-projeto').addClass('hidden');
	}
	
	$("#participantes, #parecerista, #posicionamento, #avaliacao, #participante","#participanteExterno").select2({
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
                	notEmpty: {
                        message: 'Campo obrigatório'
                    },
                    stringLength: {
                        min: 2,
                        message: 'O nome deve ter no mínimo 2 caracteres'
                    }
        
                }
            },
            descricao: {
                validators: {
                	notEmpty: {
                        message: 'Campo obrigatório'
                    },
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
	
	$('#atribuirPareceristaForm, #emitirParecerForm').bootstrapValidator({
		group: '.form-item',
		excluded: ':disabled',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        }
    });
	
	//VINCULAR PARTICIPANTES
	$('#adicionarParticipacaoForm').bootstrapValidator({
		group: '.form-item',
		excluded: ':disabled',
        fields: {
            mesInicio: {
                validators: {
                	notEmpty: {
                        message: 'Campo obrigatório'
	                },
		            integer:{
			     		message: 'Digite um número válido'
		     	   	}
                }
            },
            anoInicio: {
            	validators: {
            		notEmpty: {
                        message: 'Campo obrigatório'
                    },
		            integer:{
			     		message: 'Digite um número válido'
		     	   	}
            	}
            },
            mesTermino: {
                validators: {
                   notEmpty: {
                        message: 'Campo obrigatório'
                    },
		            integer:{
			     		message: 'Digite um número válido'
		     	   	}
                }
            },
            anoTermino: {
            	validators: {
            		notEmpty: {
                        message: 'Campo obrigatório'
                    },
		            integer:{
			     		message: 'Digite um número válido'
		     	   	}
            	}
            },
            cargaHorariaMensal: {
                validators: {
                   notEmpty: {
                        message: 'Campo obrigatório'
                   },
            	   integer:{
            		   message: 'Digite um número válido'
            	   }	
                }
            },
            bolsaValorMensal: {
            	validators: {
	        		notEmpty: {
	                    message: 'Campo obrigatório'
	                },
		            numeric:{
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
	
	//Avaliar projeto
	$('#homologarProjetoForm').bootstrapValidator({
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
		group: '.form-item',
		feedbackIcons: {
        	invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	iInterInicio :{
        		validators:{
        			callback:{
        				callback: function(value, validator) {
        					var fInterInicio = validator.getFieldElements('fInterInicio').val();
	        				if(value != "" && fInterInicio != ""){
	        					fInterInicio = moment(fInterInicio, "YYYY-MM").format("YYYY-MM");
	        					var iInterInicio = moment(value, "YYYY-MM").format("YYYY-MM");
	        					if(moment(iInterInicio, "YYYY-MM").isAfter(moment(fInterInicio, "YYYY-MM"))){
	        						return {
	        			                valid: false,    // or false
	        			                message: 'A data de início deste intervalo deve ser anterior à data de termino'
	        			            }
	        					}
	        				}
	        				var iInterTermino = validator.getFieldElements('iInterTermino').val();
	        				if(value != "" && iInterTermino != ""){
        						iInterTermino = moment(iInterTermino, "YYYY-MM").format("YYYY-MM");
        						var iInterInicio = moment(value, "YYYY-MM").format("YYYY-MM");
        						if(moment(iInterInicio, "YYYY-MM").isAfter(moment(iInterTermino, "YYYY-MM"))) {
        							return {
	        			                valid: false,    
	        			                message: 'Esta data deve ser anterior a data inicial do Intervalo de Termino'
	        			            }
        						}
        					}
	        				return true;
        				}
        			}
        		}
        	},
        	fInterInicio :{
            	validators: {
            		callback: {
                        callback: function(value, validator) {
                        	var iInterInicio = validator.getFieldElements('iInterInicio').val();
                        	if(value != "" && iInterInicio != "") {
                        		iInterInicio = moment(iInterInicio, "YYYY-MM").format("YYYY-MM");
	                        	var fInterInicio = moment(value, "YYYY-MM").format("YYYY-MM");
	                        	if(moment(fInterInicio, "YYYY-MM").isBefore(moment(iInterInicio, "YYYY-MM"))) {
	                        		return {
	                        			valid: false,
	                        			message: 'A data de término deste intervalo deve ser posterior à data de início'
	                        		}
	                        	}
                        	}
                        	var fInterTermino = validator.getFieldElements('fInterTermino').val();
                        	if(value != "" && fInterTermino != ""){
                    			fInterTermino = moment(fInterTermino, "YYYY-MM").format("YYYY-MM");
                    			var fInterInicio = moment(value, "YYYY-MM").format("YYYY-MM");
                    			if(moment(fInterInicio, "YYYY-MM").isAfter(moment(fInterTermino, "YYYY-MM"))) {
	                        		return {
	                        			valid: false,
	                        			message:'Esta data deve ser posterior a data inicial do Intervalo de Termino'
	                        		}
	                        	}
                    		}
                        	return true;
                        }
                    }
            	}
        	},
        	iInterTermino:{
        		validators:{
        			callback:{
        				callback: function(value, validator){
        					var fInterTermino = validator.getFieldElements('fInterTermino').val();
        					if(value != "" && fInterTermino != ""){
        						fInterTermino = moment(fInterTermino, "YYYY-MM").format("YYYY-MM");
	                        	var iInterTermino = moment(value, "YYYY-MM").format("YYYY-MM");
	                        	if(moment(iInterTermino, "YYYY-MM").isAfter(moment(fInterTermino, "YYYY-MM"))) {
	                        		return {
	                        			valid: false,
	                        			message:'A data de início deste intervalo deve ser anterior à data de termino'
	                        			}
	                        	}
        					}
        					var iInterInicio = validator.getFieldElements('iInterInicio').val();
        					if(value != "" && iInterInicio != ""){
        						iInterInicio = moment(iInterInicio, "YYYY-MM").format("YYYY-MM");
        						var iInterTermino = moment(value, "YYYY-MM").format("YYYY-MM");
        						if(moment(iInterTermino, "YYYY-MM").isBefore(moment(iInterInicio, "YYYY-MM"))) {
	                        		return {
	                        			valid: false,
	                        			message:'Esta data deve ser posterior a data inicial do intervalo de Início'
	                        			}
	                        	}
        					}
        					return true;
        				}
        			}
        		}
        	},
        	fInterTermino: {
	        	validators:{
	        		callback: {
	                    callback: function(value, validator) {
	                    	var iInterTermino = validator.getFieldElements('iInterTermino').val();
	                    	if(value != "" && iInterTermino != "") {
	                    		iInterTermino = moment(iInterTermino, "YYYY-MM").format("YYYY-MM");
	                        	var fInterTermino = moment(value, "YYYY-MM").format("YYYY-MM");
	                        	if(moment(fInterTermino, "YYYY-MM").isBefore(moment(iInterTermino, "YYYY-MM"))) {
	                        		return {
	                        			valid: false,
	                        			message: 'A data de término deste intervalo deve ser posterior à data de início'
	                        		}
	                        	}
	                    	}
	                    	var fInterInicio = validator.getFieldElements('fInterInicio').val();
	                		if(value != "" && fInterInicio != ""){
	                			fInterInicio = moment(fInterInicio, "YYYY-MM").format("YYYY-MM");
	                			var fInterTermino = moment(value, "YYYY-MM").format("YYYY-MM");
	                			if(moment(fInterTermino, "YYYY-MM").isBefore(moment(fInterInicio, "YYYY-MM"))) {
	                				return {
	                        			valid: false,
	                        			message: 'Esta data deve ser posterior a data inicial do intervalo de Início'
	                				}
	                        	}
	                		}
	                    	return true;
	                    }
	                }
	        	}
	        }
        }
	});
	 $("#inicioRelatorioInicio, #terminoRelatorioInicio, #inicioRelatorioTermino, #terminoRelatorioTermino").datepicker({
			format : "yyyy-mm",
			todayBtn : "linked",
			language : "pt-BR",
			viewMode: "months", 
		    minViewMode: "months",
			todayHighlight : true
		}).on('changeDate', function(e) {
			$(this).datepicker('hide');
			$('#relatoriosAprovadosForm').bootstrapValidator('revalidateField', 'iInterInicio');
			$('#relatoriosAprovadosForm').bootstrapValidator('revalidateField', 'fInterInicio');
			$('#relatoriosAprovadosForm').bootstrapValidator('revalidateField', 'fInterTermino');
			$('#relatoriosAprovadosForm').bootstrapValidator('revalidateField', 'iInterTermino');
	    });
	 
		$('#relatoriosReprovadosForm').bootstrapValidator({
			group: '.form-item',
			feedbackIcons: {
	        	valid: 'glyphicon glyphicon-ok',
	        	invalid: 'glyphicon glyphicon-remove',
	        },
	        fields: {
	        	submissaoInicio :{
	            	validators: {
	            		callback: {
	                        message: 'A data de início deve ser anterior à data de término',
	                        callback: function(value, validator) {
	                        	var submissaoTermino = validator.getFieldElements('submissaoTermino').val();
	                        	if(value != "" && submissaoTermino != "") {
	                        		submissaoTermino = moment(submissaoTermino, "YYYY-MM").format("YYYY-MM");
		                        	var submissaoInicio = moment(value, "YYYY-MM").format("YYYY-MM");
		                        	if(moment(submissaoTermino, "YYYY-MM").isBefore(moment(submissaoInicio, "YYYY-MM"))) {
		                        		return false;
		                        	}
	                        	}
	                        	return true;
	                        }
	                    }
	            	}
	        	},
	        	submissaoTermino: {
		        	validators:{
		        		callback: {
		                    message: 'A data de término deve ser posterior à data de início',
		                    callback: function(value, validator) {
		                    	var submissaoInicio = validator.getFieldElements('submissaoInicio').val();
		                    	if(value != "" && submissaoInicio != "") {
		                    		submissaoInicio = moment(submissaoInicio, "YYYY-MM").format("YYYY-MM");
		                        	var submissaoTermino = moment(value, "YYYY-MM").format("YYYY-MM");
		                        	if(moment(submissaoInicio, "YYYY-MM").isAfter(moment(submissaoTermino, "YYYY-MM"))) {
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

	 $("#submissaoRelatorioInicio,#submissaoRelatoriTermino").datepicker({
			format : "yyyy-mm",
			todayBtn : "linked",
			language : "pt-BR",
			viewMode: "months", 
		    minViewMode: "months",
		    orientation: "top auto",
			todayHighlight : true
		}).on('changeDate', function(e) {
			$(this).datepicker('hide');
			$('#relatoriosReprovadosForm').bootstrapValidator('revalidateField', 'submissaoInicio');
			$('#relatoriosReprovadosForm').bootstrapValidator('revalidateField', 'submissaoTermino');
	    });

	 
	 $("#terminoRelatorioTermino, #inicioRelatorioTermino" ).datepicker({
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
	
	//ADM
	$('#confirm-vincular').on('show.bs.modal', function(e) {
		$(this).find('.modal-body').text('Tem certeza que deseja vincular papeis a \"' + $(e.relatedTarget).data('name') + '\"?'
				+' '+ $(e.relatedTarget).data('name') +' será cadastrada(o) automaticamente no sistema, caso confirme.');
		$(this).find('.btn-primary').attr('href', $(e.relatedTarget).data('href'));
	});
	
	$('#formBuscarPessoa').bootstrapValidator({
		fields: {
			busca: {
                validators: {
                	notEmpty: {
                        message: 'Digite um nome ou CPF para efetuar a busca.'
                    }
                }
            }
		}
	});
	
	$('a.back').click(function(){
		parent.history.back();
		return false;
	});
	
	
	$('#pessoaExternaCheckBox').change(function() {
	   if($(this).is(":checked")) {
		   $("#divParticipante").hide();
		   $("#divParticipanteExterno").show();
		   document.getElementById("externoBoolean").value=true;
	   } else {
		   $("#divParticipanteExterno").hide();
		   $("#divParticipante").show();
		   document.getElementById("externoBoolean").value=false;
	   }
	});
	/* MODAL */
	var modal = document.getElementById('cadastrarPessoaExternaModal');
	$("#cadastrarPessoaExternaBtn").click(function() {
	    modal.style.display = "block";
	});
	$("#fecharModalBtn").click(function(){
		modal.style.display = "none";
	});
	$("#cancelarModalBtn").click(function(){
		modal.style.display = "none";
	});
	var nomePessoaExterna = $("#inputNome");
	var cpfPessoaExterna = $("#inputCPF");
	var emailPessoaExterna = $("#inputEmail");
	$("#cadastrarPessoaExternaForm").bootstrapValidator({
		group: '.form-item',
		live: 'enabled',
		feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        excluded: ':disabled',
		fields: {
            inputNome: {
                validators: {
                    notEmpty: {
                        message: 'Nome obrigatório'
                    }
                }
            },inputCPF: {
                validators: {
                    notEmpty: {
                        message: 'Campo CPF não pode ficar vazio'
                    },
                    regexp: {
                    	regexp:/^([0-9]{3}\.?[0-9]{3}\.?[0-9]{3}\-?[0-9]{2}|[0-9]{2}\.?[0-9]{3}\.?[0-9]{3}\/?[0-9]{4}\-?[0-9]{2})$/,
                        message: 'CPF inválido'
                    },
                    stringLength: {
                        max:11
                    }
                }
            },
            inputEmail: {
                validators: {
                    notEmpty: {
                        message: 'Email não pode ficar vazio'
                    },
                    emailAddress: {
                        message: 'Email inválido'
                    }
                }
            }
		}
    });
	$("#submeterNovaPessoaExterna").on('click',function(e){
		e.preventDefault();
		$('#cadastrarPessoaExternaForm').bootstrapValidator('validate')
		if(cpfPessoaExterna.val().length){
			$.ajax({
				    url: "/gpa-pesquisa/pessoa/cadastrarExterno", 
				    type: 'POST', 
				    data: {
				    	nome: nomePessoaExterna.val(),
						cpf: cpfPessoaExterna.val(),
						email: emailPessoaExterna.val()
				    },			    
				    success: function(data, textStatus, xhr) {
				    	location.reload(true);
				    }
			});
		}
	});
	/* MODAL */
});
