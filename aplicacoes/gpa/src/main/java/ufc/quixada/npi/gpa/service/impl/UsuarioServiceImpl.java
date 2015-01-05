package ufc.quixada.npi.gpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.UsuarioService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;

@Named
public class UsuarioServiceImpl implements UsuarioService {

	@Inject
	private GenericRepository<Pessoa> usuarioRepository;

	@Override
	public Pessoa getUsuarioByLogin(String login) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("login", login);
		return usuarioRepository.find(QueryType.JPQL,
				"from Pessoa where login = :login", params).get(0);
	}

	@Override
	public List<Pessoa> getPareceristas(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return usuarioRepository.find(QueryType.JPQL,
				"from Pessoa u where u.id != :id", params);
	}

	@Override
	public boolean isDiretor(Pessoa usuario) {
		List<Papel> papeis = usuario.getPapeis();
		for (Papel p : papeis) {
			if (p.getNome().equals("ROLE_DIRETOR")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Pessoa getDiretor() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("papel", "DIRETOR");
		return usuarioRepository.findFirst("select pe Pessoa pe, pe.papeis pa where pa.nome = :papel", params);
	}

	
	@Override
	public List<Pessoa> getParticipantes(Pessoa usuario) {
		List<Pessoa> participantes = usuarioRepository.find(Pessoa.class);
		participantes.remove(usuario);
		return participantes;
	}

	@Override
	public List<Pessoa> getParticipantesProjetos() {
		List<Pessoa> participantes = usuarioRepository.find(QueryType.JPQL,
				"select distinct proj.participantes from Projeto proj", null);
		return participantes;
	}

	@Override
	public Pessoa getUsuarioById(Long id) {
		return usuarioRepository.find(Pessoa.class, id);
	}

}
