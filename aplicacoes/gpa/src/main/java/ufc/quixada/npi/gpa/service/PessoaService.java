package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Pessoa;

public interface PessoaService {

	Pessoa getPessoa(String cpf);
	
	Pessoa getPessoa(Long id);

	List<Pessoa> getPareceristas(Long id);

	Pessoa getDirecao();

	List<Pessoa> getParticipantes(Pessoa pessoa);
	
	List<Pessoa> getParticipantesProjetos();
	
	List<Papel> getPapeis(String cpf);
	
	List<Pessoa> getAll();
	
}
