package ufc.quixada.npi.gpa.service;

import java.util.List;

import br.ufc.quixada.npi.ldap.model.Usuario;
import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.PessoaExterna;
import ufc.quixada.npi.gpa.model.Projeto;

public interface PessoaService {

	Pessoa getPessoa(String cpf);
	
	Pessoa getPessoa(Long id);

	List<Pessoa> getPareceristas(Projeto projeto);

	Pessoa getDirecao();
	
	List<Pessoa> getAllDirecao();

	List<Pessoa> getParticipantes(Pessoa pessoa);
	
	List<Pessoa> getParticipantesProjetos();
	
	List<Papel> getPapeis(String cpf);
	
	List<Pessoa> getAll();

	List<Pessoa> getPessoasByUsuarios(List<Usuario> usuarios);

	void update(Pessoa oldPessoa);

	void save(Pessoa pessoa);

	Pessoa vincularPapeis(Pessoa pessoa, Pessoa oldPessoa);

	List<Pessoa> getUsuariosByNomeOuCpf(String busca);

	void savePessoaExterna(PessoaExterna pessoaExterna);
	
	List<PessoaExterna> getAllPessoaExterna();
	
	PessoaExterna getPessoaExterna(Long id);
}
