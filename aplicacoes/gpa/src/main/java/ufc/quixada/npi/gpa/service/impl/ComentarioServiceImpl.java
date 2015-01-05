package ufc.quixada.npi.gpa.service.impl;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ufc.quixada.npi.gpa.model.Comentario;
import ufc.quixada.npi.gpa.service.ComentarioService;
import br.ufc.quixada.npi.repository.GenericRepository;

@Named
public class ComentarioServiceImpl implements ComentarioService {

	@Autowired
	private GenericRepository<Comentario> comentarioRepository;
	
	@Override
	public void salvar(Comentario comentario) {
		comentarioRepository.save(comentario);
		
	}

}