$(document).ready(function() {
	
	// Página Listar Projetos (Diretor)
	
	$('#projetos-diretor').DataTable({
		"order" : [[ 0, 'asc' ]],
		"columnDefs" : [ 
            {"targets" : 2, "orderable" : false},
            {"targets" : 3, "orderable" : false}
		],
		"oLanguage": {  
    		"sEmptyTable": "Nenhum registro encontrado",
        	 "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
        	 "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
        	 "sInfoFiltered": "(Filtrados de _MAX_ registros)",
        	 "sInfoPostFix": "",
        	 "sInfoThousands": ".",
        	 "sLengthMenu": "Resultados por página: _MENU_",
        	 "sLoadingRecords": "Carregando...",
        	 "sProcessing": "Processando...",
        	 "sZeroRecords": "Nenhum registro encontrado",
        	 "sSearch": "Buscar: ",
        	 "oPaginate": {
        	        "sNext": "Próximo",
        	        "sPrevious": "Anterior",
        	        "sFirst": "Primeiro",
        	        "sLast": "Último"
        	    },
        	 "oAria": {
    		 	"sSortAscending": ": Ordenar colunas de forma ascendente",
    	        "sSortDescending": ": Ordenar colunas de forma descendente"
    	    }
    	 }
	});
	
	$('#projetos-avaliados').DataTable({
		"order" : [[ 0, 'asc' ]],
		"columnDefs" : [ 
            {"targets" : 2, "orderable" : false}
		],
		"bAutoWidth": false,
		"oLanguage": {  
    		"sEmptyTable": "Nenhum registro encontrado",
        	 "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
        	 "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
        	 "sInfoFiltered": "(Filtrados de _MAX_ registros)",
        	 "sInfoPostFix": "",
        	 "sInfoThousands": ".",
        	 "sLengthMenu": "Resultados por página: _MENU_",
        	 "sLoadingRecords": "Carregando...",
        	 "sProcessing": "Processando...",
        	 "sZeroRecords": "Nenhum registro encontrado",
        	 "sSearch": "Buscar: ",
        	 "oPaginate": {
        	        "sNext": "Próximo",
        	        "sPrevious": "Anterior",
        	        "sFirst": "Primeiro",
        	        "sLast": "Último"
        	    },
        	 "oAria": {
    		 	"sSortAscending": ": Ordenar colunas de forma ascendente",
    	        "sSortDescending": ": Ordenar colunas de forma descendente"
    	    }
    	 }
	});
	
	$('#projetos-em-participacao').DataTable({
		"order" : [[ 0, 'asc' ]],
		"columnDefs" : [ 
            {"targets" : 2, "orderable" : false},
            {"targets" : 4, "orderable" : false}
		],
		"bAutoWidth": false,
		"oLanguage": {  
    		"sEmptyTable": "Nenhum registro encontrado",
        	 "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
        	 "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
        	 "sInfoFiltered": "(Filtrados de _MAX_ registros)",
        	 "sInfoPostFix": "",
        	 "sInfoThousands": ".",
        	 "sLengthMenu": "Resultados por página: _MENU_",
        	 "sLoadingRecords": "Carregando...",
        	 "sProcessing": "Processando...",
        	 "sZeroRecords": "Nenhum registro encontrado",
        	 "sSearch": "Buscar: ",
        	 "oPaginate": {
        	        "sNext": "Próximo",
        	        "sPrevious": "Anterior",
        	        "sFirst": "Primeiro",
        	        "sLast": "Último"
        	    },
        	 "oAria": {
    		 	"sSortAscending": ": Ordenar colunas de forma ascendente",
    	        "sSortDescending": ": Ordenar colunas de forma descendente"
    	    }
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
		"oLanguage": {  
    		"sEmptyTable": "Nenhum registro encontrado",
        	 "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
        	 "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
        	 "sInfoFiltered": "(Filtrados de _MAX_ registros)",
        	 "sInfoPostFix": "",
        	 "sInfoThousands": ".",
        	 "sLengthMenu": "Resultados por página: _MENU_",
        	 "sLoadingRecords": "Carregando...",
        	 "sProcessing": "Processando...",
        	 "sZeroRecords": "Nenhum registro encontrado",
        	 "sSearch": "Buscar: ",
        	 "oPaginate": {
        	        "sNext": "Próximo",
        	        "sPrevious": "Anterior",
        	        "sFirst": "Primeiro",
        	        "sLast": "Último"
        	    },
        	 "oAria": {
    		 	"sSortAscending": ": Ordenar colunas de forma ascendente",
    	        "sSortDescending": ": Ordenar colunas de forma descendente"
    	    }
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
	
});