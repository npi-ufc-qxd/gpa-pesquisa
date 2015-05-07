package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Pessoa;

public interface PessoaService {

	Pessoa getUsuarioByCpf(String cpf);
	
	Pessoa getUsuarioById(Long id);

	List<Pessoa> getPareceristas(Long id);

	boolean isDiretor(Pessoa usuario);

	Pessoa getDiretor();

	List<Pessoa> getParticipantes(Pessoa usuario);
	
	List<Pessoa> getParticipantesProjetos();
	
	List<Papel> getPapeis(String cpf);
	
}
