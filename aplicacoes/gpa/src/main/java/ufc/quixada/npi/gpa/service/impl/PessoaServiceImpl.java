package ufc.quixada.npi.gpa.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.PapelService;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.utils.Constants;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;
import br.ufc.quixada.npi.repository.GenericRepository;

@Named
public class PessoaServiceImpl implements PessoaService {

	@Inject
	private GenericRepository<Pessoa> pessoaRepository;
	
	@Inject
	private GenericRepository<Papel> papelRepository;
	
	@Inject 
	private PapelService papelService;
	
	@Inject 
	private UsuarioService usuarioService;

	@Override
	public Pessoa getPessoa(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);
		List<Pessoa> pessoas = pessoaRepository.find(QueryType.JPQL, "from Pessoa where cpf = :cpf",params);
		if(pessoas !=null && !pessoas.isEmpty()){
			return pessoas.get(0);
		}
		return null;
	}

	@Override
	public List<Pessoa> getPareceristas(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return pessoaRepository.find(QueryType.JPQL, "from Pessoa where id != :id", params);
	}

	@Override
	public Pessoa getDirecao() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("papel", Constants.PAPEL_DIRECAO);
		return pessoaRepository.findFirst(QueryType.JPQL, "select pe from Pessoa pe, Papel pa where pa.nome = :papel and pa member of pe.papeis", params, 0);
	}
	
	@Deprecated 
	@Override
	public List<Pessoa> getParticipantes(Pessoa usuario) {
		List<Pessoa> participantes = pessoaRepository.find(Pessoa.class);
		participantes.remove(usuario);
		return participantes;
	}
	
	@Override
	public List<Pessoa> getAll() {
		return pessoaRepository.find(Pessoa.class);
	}

	@Deprecated 
	@Override
	public List<Pessoa> getParticipantesProjetos() {
		List<Pessoa> participantes = pessoaRepository.find(QueryType.JPQL,
				"select distinct part.participante from Participacao part", null);
		return participantes;
	}

	@Override
	public Pessoa getPessoa(Long id) {
		return pessoaRepository.find(Pessoa.class, id);
	}

	@Override
	public List<Papel> getPapeis(String cpf) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cpf", cpf);
		return papelRepository.find(QueryType.JPQL, "select p.papeis FROM Pessoa p WHERE p.cpf = :cpf", params);
	}

	@Override
	public List<Pessoa> getPessoasByUsuarios(List<Usuario> usuarios) {
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		for (Usuario usuario : usuarios) {
			if(usuario.getCpf() != null){
				pessoas.add(getPessoa(usuario.getCpf()));
			}
		}
		return pessoas;
	}

	@Override
	public void update(Pessoa pessoa) {
		pessoaRepository.update(pessoa);
	}

	@Override
	public void save(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
	}

	@Override
	public Pessoa vincularPapeis(Pessoa pessoa, Pessoa oldPessoa) {
		Papel papelCoordenador = papelService.find(Constants.PAPEL_COORDENACAO);
		oldPessoa.setPapeis(new ArrayList<Papel>());;
		oldPessoa.addPapel(papelCoordenador);
		
		if(pessoa.getPapeis()!= null){
			for (Papel papel : pessoa.getPapeis()) {
				if(papel.getId() != null){
					papel = papelService.find(papel.getId());
					oldPessoa.addPapel(papel);
				}
			}
		}
		return oldPessoa;
	}

	@Override
	public List<Pessoa> getUsuariosByNomeOuCpf(String busca) {
		List<Usuario> usuarios = usuarioService.getByCpfOrNome(busca);
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		for (Usuario usuario : usuarios) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cpf", usuario.getCpf());
			Pessoa pessoa = pessoaRepository.findFirst(QueryType.JPQL, "from Pessoa where cpf = :cpf",params, 0);
			if(pessoa !=null && !pessoas.contains(pessoa) ){
				pessoas.add(pessoa);
			}
		}
		return pessoas;
	}

}
