package ufc.quixada.npi.gpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.PessoaService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;

@Named
public class PessoaServiceImpl implements PessoaService {

	@Inject
	private GenericRepository<Pessoa> pessoaRepository;
	
	@Inject
	private GenericRepository<Papel> papelRepository;

	@Override
	public Pessoa getUsuarioByCpf(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);
		return pessoaRepository.find(QueryType.JPQL,
				"from Pessoa where cpf = :cpf", params).get(0);
	}

	@Override
	public List<Pessoa> getPareceristas(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return pessoaRepository.find(QueryType.JPQL,
				"from Pessoa u where u.id != :id", params);
	}

	@Override
	public boolean isDiretor(Pessoa pessoa) {
		List<Papel> papeis = getPapeis(pessoa.getCpf());
		for (Papel p : papeis) {
			if (p.getNome().equals("DIRETOR")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Pessoa getDiretor() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("papel", "DIRETOR");
		return pessoaRepository.findFirst("select pe Pessoa pe, pe.papeis pa where pa.nome = :papel", params);
	}

	
	@Override
	public List<Pessoa> getParticipantes(Pessoa usuario) {
		List<Pessoa> participantes = pessoaRepository.find(Pessoa.class);
		participantes.remove(usuario);
		return participantes;
	}

	@Override
	public List<Pessoa> getParticipantesProjetos() {
		List<Pessoa> participantes = pessoaRepository.find(QueryType.JPQL,
				"select distinct proj.participantes from Projeto proj", null);
		return participantes;
	}

	@Override
	public Pessoa getUsuarioById(Long id) {
		return pessoaRepository.find(Pessoa.class, id);
	}

	@Override
	public List<Papel> getPapeis(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);
		return papelRepository.find(QueryType.JPQL, "select p.papeis FROM Pessoa p WHERE p.cpf = :cpf", params);
	}

}
