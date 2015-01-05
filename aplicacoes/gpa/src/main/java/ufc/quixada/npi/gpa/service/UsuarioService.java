package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Pessoa;

public interface UsuarioService {

	Pessoa getUsuarioByLogin(String login);
	
	Pessoa getUsuarioById(Long id);

	List<Pessoa> getPareceristas(Long id);

	boolean isDiretor(Pessoa usuario);

	Pessoa getDiretor();

	List<Pessoa> getParticipantes(Pessoa usuario);

	List<Pessoa> getParticipantesProjetos();
	
}
