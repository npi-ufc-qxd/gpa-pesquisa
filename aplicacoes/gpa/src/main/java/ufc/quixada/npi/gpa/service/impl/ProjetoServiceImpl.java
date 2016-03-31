package ufc.quixada.npi.gpa.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.service.DocumentoService;
import ufc.quixada.npi.gpa.service.ProjetoService;

@Named
public class ProjetoServiceImpl implements ProjetoService {

	@Inject
	private GenericRepository<Projeto> projetoRepository;

	@Inject
	private GenericRepository<Participacao> participacaoRepository;

	@Inject
	private GenericRepository<Parecer> parecerRepository;
	
	@Inject
	private DocumentoService documentoService;

	@Override
	public void cadastrar(Projeto projeto) {
		projeto.setStatus(StatusProjeto.NOVO);
		projetoRepository.save(projeto);

		String codigo = geraCodigoProjeto(projeto.getId());
		projeto.setCodigo(codigo);
		projetoRepository.update(projeto);
	}

	@Override
	public void atualizar(Projeto projeto) {
		projeto.setStatus(StatusProjeto.NOVO);
		projetoRepository.update(projeto);
	}

	@Override
	public void submeter(Projeto projeto) {
		projeto.setStatus(StatusProjeto.SUBMETIDO);
		projeto.setSubmissao(new Date());
		projetoRepository.update(projeto);
	}

	@Override
	public void atribuirParecerista(Projeto projeto, Parecer parecer) {
		parecerRepository.save(parecer);
		projeto.setParecer(parecer);
		projeto.setStatus(StatusProjeto.AGUARDANDO_PARECER);
		projetoRepository.update(projeto);
	}

	@Override
	public void emitirParecer(Projeto projeto) {		
		projeto.setStatus(StatusProjeto.AGUARDANDO_AVALIACAO);
		projetoRepository.update(projeto);
	}

	@Override
	public void avaliar(Projeto projeto) {			
		projeto.setAvaliacao(new Date());
		projetoRepository.update(projeto);
	}

	@Override
	public void remover(Projeto projeto) {
		projetoRepository.delete(projeto);
		documentoService.removerPastaProjeto(projeto.getCodigo());
	}

	@Override
	public List<Projeto> getProjetosSubmetidos() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("submetido", StatusProjeto.SUBMETIDO);
		params.put("aguardando_parecer", StatusProjeto.AGUARDANDO_PARECER);
		params.put("aguardando_avaliacao", StatusProjeto.AGUARDANDO_AVALIACAO);
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where status = :submetido or status = :aguardando_parecer or status = :aguardando_avaliacao",
				params);
	}
	
	@Override
	public List<Projeto> getProjetosSubmetidos(Long idCoordenador) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idCoordenador);
		params.put("submetido", StatusProjeto.SUBMETIDO);
		params.put("aguardando_parecer", StatusProjeto.AGUARDANDO_PARECER);
		params.put("aguardando_avaliacao", StatusProjeto.AGUARDANDO_AVALIACAO);
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where ((status = :submetido) OR (status = :aguardando_parecer) OR (status = :aguardando_avaliacao)) AND (coordenador.id = :id)",
				params);
	}

	@Override
	public List<Projeto> getProjetosNaoAvaliados(Long idCoordenador) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idCoordenador);
		params.put("novo", StatusProjeto.NOVO);
		params.put("submetido", StatusProjeto.SUBMETIDO);
		params.put("aguardando_parecer", StatusProjeto.AGUARDANDO_PARECER);
		params.put("aguardando_avaliacao", StatusProjeto.AGUARDANDO_AVALIACAO);
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where ((status = :novo) OR (status = :submetido) OR (status = :aguardando_parecer) OR (status = :aguardando_avaliacao)) AND (coordenador.id = :id)",
				params);
	}
	
	@Override
	public List<Projeto> getProjetosAvaliados() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("aprovado", StatusProjeto.APROVADO);
		params.put("reprovado", StatusProjeto.REPROVADO);
		params.put("aprovado_restricao", StatusProjeto.APROVADO_COM_RESTRICAO);
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where status = :aprovado OR status = :reprovado OR status = :aprovado_restricao", params);
	}

	@Override
	public List<Projeto> getProjetosAvaliados(Long idCoordenador) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idCoordenador);
		params.put("aprovado", StatusProjeto.APROVADO);
		params.put("reprovado", StatusProjeto.REPROVADO);
		params.put("aprovado_restricao", StatusProjeto.APROVADO_COM_RESTRICAO);

		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where ((status = :aprovado) OR (status = :reprovado) OR (status = :aprovado_restricao)) AND (coordenador.id = :id)",
				params);
	}

	@Override
	public Projeto getProjeto(Long id) {
		return projetoRepository.find(Projeto.class, id);
	}

	@Override
	public List<Projeto> getProjetos(Long idCoordenador) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idCoordenador);
		return projetoRepository.find(QueryType.JPQL, "from Projeto where coordenador.id = :id", params);
	}

	@Override
	public List<Projeto> getProjetosByParticipante(Long idParticipante) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idParticipante);
		return projetoRepository.find(QueryType.JPQL,
				"select distinct proj FROM Projeto as proj JOIN proj.participacoes part WHERE part.participante.id = :id and proj.status != 'NOVO'",
				params);
	}

	@Override
	public List<Projeto> getProjetos(StatusProjeto status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		return projetoRepository.find(QueryType.JPQL, "from Projeto where status = :status", params);
	}

	@Override
	public List<Projeto> getProjetos(Long idCoordenador, StatusProjeto status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idCoordenador);
		params.put("status", status);
		return projetoRepository.find(QueryType.JPQL, "from Projeto where coordenador.id = :id and status = :status", params);
	}

	@Override
	public List<Projeto> getProjetosAguardandoParecer(Long idParecerista) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idParecerista);
		params.put("aguardando_parecer", StatusProjeto.AGUARDANDO_PARECER);
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where parecer.parecerista.id = :id and status = :aguardando_parecer", params);
	}
	
	@Override
	public List<Projeto> getProjetosParecerEmitido(Long idParecerista) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idParecerista);
		params.put("aguardando_avaliacao", StatusProjeto.AGUARDANDO_AVALIACAO);
		params.put("aprovado", StatusProjeto.APROVADO);
		params.put("reprovado", StatusProjeto.REPROVADO);
		params.put("aprovado_restricao", StatusProjeto.APROVADO_COM_RESTRICAO);

		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where ((status = :aguardando_avaliacao) OR (status = :aprovado) OR (status = :reprovado) OR (status = :aprovado_restricao)) AND (parecer.parecerista.id = :id)",
				params);
	}

	@Override
	public List<Participacao> getParticipacoes(Long idPessoa) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idPessoa);
		params.put("statusAprovado", StatusProjeto.APROVADO);
		params.put("statusAprovadoRestricao", StatusProjeto.APROVADO_COM_RESTRICAO);
		List<Participacao> lista = participacaoRepository.find(QueryType.JPQL,
				"select distinct part FROM Participacao as part WHERE part.participante.id = :id and (part.projeto.status = :statusAprovado OR part.projeto.status = :statusAprovadoRestricao) and part.projeto.coordenador.id != :id", params);
		return lista;
	}

	@Override
	public void removerParticipacao(Projeto projeto, Participacao participacao) {
		if (projeto.getParticipacoes().contains(participacao)) {
			projeto.removerParticipacao(participacao);
			participacaoRepository.delete(participacao);
		}
	}

	@Override
	public Participacao getParticipacao(Long id) {
		return participacaoRepository.find(Participacao.class, id);
	}

	@Override
	public List<Participacao> getParticipacoesByProjeto(Long idProjeto) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idProjeto);
		List<Participacao> lista = participacaoRepository.find(QueryType.JPQL,
				"select distinct part FROM Participacao as part " + "WHERE part.projeto.id = :id ", params);
		return lista;
	}

	private String geraCodigoProjeto(Long id) {
		NumberFormat formatador = new DecimalFormat("#000");
		return "PESQ" + formatador.format(id);
	}

	@Override
	public boolean isParticipante(Pessoa pessoa, Projeto projeto) {
		for (Participacao participacao : projeto.getParticipacoes()) {
			if (participacao.getParticipante().equals(pessoa)) {
				return true;
			}
		}
		return false;
	}
	
	public List<Projeto> getProjetosCoordenaAprovadosAtualmente(Long idCoordenador) {
		Map<String, Object> params = new HashMap<>();
		params.put("idCoordenador", idCoordenador);
		params.put("statusAprovado", StatusProjeto.APROVADO);
		params.put("statusAprovadoRestricao", StatusProjeto.APROVADO_COM_RESTRICAO);
		
		return projetoRepository.find(QueryType.JPQL, 
				"FROM Projeto WHERE coordenador.id = :idCoordenador AND termino >= current_date() AND (status = :statusAprovado OR status = :statusAprovadoRestricao) ", 
				params);
	}
	
	public List<Projeto> getProjetosCoordenouAprovadosAtualmente(Long idCoordenador) {
		Map<String, Object> params = new HashMap<>();
		params.put("idCoordenador", idCoordenador);
		params.put("statusAprovado", StatusProjeto.APROVADO);
		params.put("statusAprovadoRestricao", StatusProjeto.APROVADO_COM_RESTRICAO);
		
		return projetoRepository.find(QueryType.JPQL, 
				"FROM Projeto WHERE coordenador.id = :idCoordenador AND termino < current_date() AND (status = :statusAprovado OR status = :statusAprovadoRestricao)", 
				params);
	}
	
	public List<Projeto> getProjetosParticipaAprovadosAtualmente(Long idCoordenador) {
		Map<String, Object> params = new HashMap<>();
		params.put("idCoordenador", idCoordenador);
		params.put("statusAprovado", StatusProjeto.APROVADO);
		params.put("statusAprovadoRestricao", StatusProjeto.APROVADO_COM_RESTRICAO);
		
		return projetoRepository.find(QueryType.JPQL, 
				"SELECT proj FROM Projeto AS proj JOIN proj.participacoes part WHERE part.participante.id = :idCoordenador AND proj.coordenador.id <> :idCoordenador AND (proj.status = :statusAprovado OR proj.status = :statusAprovadoRestricao) AND termino >= current_date()",
				params);
	}
	
	public List<Projeto> getProjetosParticipouAprovadosAtualmente(Long idCoordenador) {
		Map<String, Object> params = new HashMap<>();
		params.put("idCoordenador", idCoordenador);
		params.put("statusAprovado", StatusProjeto.APROVADO);
		params.put("statusAprovadoRestricao", StatusProjeto.APROVADO_COM_RESTRICAO);
		
		return projetoRepository.find(QueryType.JPQL,
				"SELECT proj FROM Projeto AS proj JOIN proj.participacoes part WHERE part.participante.id = :idCoordenador AND proj.coordenador.id <> :idCoordenador AND (proj.status = :statusAprovado OR proj.status = :statusAprovadoRestricao) AND termino < current_date()",
				params);
	}
}

