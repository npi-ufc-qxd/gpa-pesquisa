$(document).ready(function() {
	$('#confirm-vincular').on('show.bs.modal', function(e) {
		$(this).find('.modal-body').text('Tem certeza que deseja vincular papeis a \"' + $(e.relatedTarget).data('name') + '\"?'
				+' '+ $(e.relatedTarget).data('name') +' será cadastrada(o) automaticamente no sistema, caso confirme.');
		$(this).find('.btn-primary').attr('href', $(e.relatedTarget).data('href'));
	});
});