package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_CAMPO_OBRIGATORIO;

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
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.service.ProjetoService;

@Named
public class ProjetoServiceImpl implements ProjetoService {

	@Inject
	private GenericRepository<Projeto> projetoRepository;

	@Inject
	private GenericRepository<Participacao> participacaoRepository;

	@Inject
	private GenericRepository<Parecer> parecerRepository;

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
	public Map<String, String> atribuirParecerista(Projeto projeto, Parecer parecer) {
		Map<String, String> resultado = new HashMap<String, String>();
		if (parecer.getPrazo() == null) {
			resultado.put("prazo", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		if (resultado.isEmpty()) {
			parecerRepository.save(parecer);
			projeto.setParecer(parecer);
			projeto.setStatus(StatusProjeto.AGUARDANDO_PARECER);
			projetoRepository.update(projeto);

		}
		return resultado;
	}

	@Override
	public void emitirParecer(Projeto projeto) {		
		projeto.setStatus(StatusProjeto.AGUARDANDO_AVALIACAO);
		projetoRepository.update(projeto);
	}

	@Override
	public Map<String, String> avaliar(Projeto projeto) {
		Map<String, String> resultado = new HashMap<String, String>();
		if (projeto.getAta() == null) {
			resultado.put("ata", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		if (projeto.getOficio() == null) {
			resultado.put("oficio", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		if (resultado.isEmpty()) {
			projeto.setAvaliacao(new Date());
			projetoRepository.update(projeto);
			projetoRepository.update(projeto);
		}
		return resultado;
	}

	@Override
	public void remover(Projeto projeto) {
		projetoRepository.delete(projeto);
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
	public List<Projeto> getProjetosAvaliados() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("aprovado", StatusProjeto.APROVADO);
		params.put("reprovado", StatusProjeto.REPROVADO);
		params.put("aprovado_restricao", StatusProjeto.APROVADO_COM_RESTRICAO);
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where status = :aprovado OR status = :reprovado OR status = :aprovado_restricao", params);
	}

	@Override
	public List<Projeto> getProjetosAvaliados(Long idAutor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idAutor);
		params.put("aprovado", StatusProjeto.APROVADO);
		params.put("reprovado", StatusProjeto.REPROVADO);
		params.put("aprovado_restricao", StatusProjeto.APROVADO_COM_RESTRICAO);

		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where ((status = :aprovado) OR (status = :reprovado) OR (status = :aprovado_restricao)) AND (autor.id = :id)",
				params);
	}

	@Override
	public Projeto getProjeto(Long id) {
		return projetoRepository.find(Projeto.class, id);
	}

	@Override
	public List<Projeto> getProjetos(Long idAutor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idAutor);
		return projetoRepository.find(QueryType.JPQL, "from Projeto where autor.id = :id", params);
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
	public List<Projeto> getProjetos(Long idAutor, StatusProjeto status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idAutor);
		params.put("status", status);
		return projetoRepository.find(QueryType.JPQL, "from Projeto where autor.id = :id and status = :status", params);
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
	public List<Participacao> getParticipacoes(Long idPessoa) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idPessoa);
		List<Participacao> lista = participacaoRepository.find(QueryType.JPQL,
				"select distinct part FROM Participacao as part " + "WHERE part.participante.id = :id ", params);
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
		NumberFormat formatador = new DecimalFormat("#0000");
		return "PESQ" + formatador.format(id);
	}

}
