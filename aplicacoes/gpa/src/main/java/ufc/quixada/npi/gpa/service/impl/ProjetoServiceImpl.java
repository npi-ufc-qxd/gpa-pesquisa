package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_AUTOR_NAO_PARTICIPANTE;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_CAMPO_OBRIGATORIO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_DATA_INICIO_TERMINO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_DATA_TERMINO_FUTURA;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_SEM_PARTICIPANTES;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.service.ProjetoService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;

@Named
public class ProjetoServiceImpl implements ProjetoService {

	@Inject
	private GenericRepository<Projeto> projetoRepository;
	
	@Inject
	private GenericRepository<Participacao> participacaoRepository;

	@Inject
	private GenericRepository<Parecer> parecerRepository;

	@Override
	public Map<String, String> cadastrar(Projeto projeto) {
		Map<String, String> resultado = new HashMap<String, String>();

		if (!projeto.isDataTerminoFutura()) {
			resultado.put("termino", MENSAGEM_DATA_TERMINO_FUTURA);
		}

		if (!projeto.isPeriodoValido()) {
			resultado.put("inicio", MENSAGEM_DATA_INICIO_TERMINO);
		}

		if (resultado.isEmpty()) {
			projeto.setStatus(StatusProjeto.NOVO);
			projetoRepository.save(projeto);

			String codigo = geraCodigoProjeto(projeto.getId());
			projeto.setCodigo(codigo);
			projetoRepository.update(projeto);
		}

		return resultado;
	}

	@Override
	public Map<String, String> atualizar(Projeto projeto) {
		Map<String, String> resultado = new HashMap<String, String>();

		if (!projeto.isDataTerminoFutura()) {
			resultado.put("termino", MENSAGEM_DATA_TERMINO_FUTURA);
		}

		if (!projeto.isPeriodoValido()) {
			resultado.put("inicio", MENSAGEM_DATA_INICIO_TERMINO);
		}

		if (resultado.isEmpty()) {
			projeto.setStatus(StatusProjeto.NOVO);
			projetoRepository.update(projeto);
		}

		return resultado;
	}
	
	@Override
	public Map<String, String> submeter(Projeto projeto) {
		Map<String, String> resultadoValidacao = this.validarSubmissao(projeto);
		if (resultadoValidacao.isEmpty()) {
			projeto.setStatus(StatusProjeto.SUBMETIDO);
			projeto.setSubmissao(new Date());
			projetoRepository.update(projeto);
		}
		return resultadoValidacao;
	}

	@Override
	public Map<String, String> atribuirParecerista(Projeto projeto,
			Parecer parecer) {
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
	public Map<String, String> emitirParecer(Projeto projeto) {
		Map<String, String> resultado = new HashMap<String, String>();
		if (projeto.getParecer().getParecer() == null
				|| projeto.getParecer().getParecer().isEmpty()) {
			resultado.put("parecer", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		if (resultado.isEmpty()) {
			projeto.setStatus(StatusProjeto.AGUARDANDO_AVALIACAO);
			projetoRepository.update(projeto);

		}
		return resultado;
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
		return projetoRepository
				.find(QueryType.JPQL,
						"from Projeto where status = :submetido or status = :aguardando_parecer or status = :aguardando_avaliacao",
						params);
	}
	
	@Override
	public List<Projeto> getProjetosAvaliados() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("aprovado", StatusProjeto.APROVADO);
		params.put("reprovado", StatusProjeto.REPROVADO);
		params.put("aprovado_restricao", StatusProjeto.APROVADO_COM_RESTRICAO);
		return projetoRepository
				.find(QueryType.JPQL,
						"from Projeto where status = :aprovado OR status = :reprovado OR status = :aprovado_restricao",
						params);
	}
	
	@Override
	public List<Projeto> getProjetosAvaliados(Long idAutor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idAutor);
		params.put("aprovado", StatusProjeto.APROVADO);
		params.put("reprovado", StatusProjeto.REPROVADO);
		params.put("aprovado_restricao", StatusProjeto.APROVADO_COM_RESTRICAO);
		
		return projetoRepository
				.find(QueryType.JPQL,
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
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where autor.id = :id", params);
	}

	@Override
	public List<Projeto> getProjetosByParticipante(Long idParticipante) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idParticipante);
		return projetoRepository
				.find(QueryType.JPQL,
						"select distinct proj FROM Projeto as proj JOIN proj.participacoes part WHERE part.participante.id = :id and proj.status != 'NOVO'",
						params);
	}
	
	@Override
	public List<Projeto> getProjetos(StatusProjeto status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		return projetoRepository
				.find(QueryType.JPQL,
						"from Projeto where status = :status", params);
	}
	
	@Override
	public List<Projeto> getProjetos(Long idAutor, StatusProjeto status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idAutor);
		params.put("status", status);
		return projetoRepository
				.find(QueryType.JPQL,
						"from Projeto where autor.id = :id and status = :status", params);
	}
	
	@Override
	public List<Projeto> getProjetosAguardandoParecer(Long idParecerista) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idParecerista);
		params.put("aguardando_parecer", StatusProjeto.AGUARDANDO_PARECER);
		return projetoRepository
				.find(QueryType.JPQL,
						"from Projeto where parecer.parecerista.id = :id and status = :aguardando_parecer",
						params);
	}
	
	@Override
	public List<Participacao> getParticipacoes(Long idPessoa) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idPessoa);
		List<Participacao> lista = participacaoRepository
				.find(QueryType.JPQL,
						  "select distinct part FROM Participacao as part "
						+ "WHERE part.participante.id = :id ",
						params);
		return lista;
	}
	
	@Override
	public void removerParticipacao(Projeto projeto,	Participacao participacao) {
		if(projeto.getParticipacoes().contains(participacao)) {
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
		List<Participacao> lista = participacaoRepository
				.find(QueryType.JPQL,
						  "select distinct part FROM Participacao as part "
						+ "WHERE part.projeto.id = :id ",
						params);
		return lista;
	}

	private String geraCodigoProjeto(Long id) {
		NumberFormat formatador = new DecimalFormat("#0000");
		return "PESQ" + formatador.format(id);
	}

	private Map<String, String> validarSubmissao(Projeto projeto) {
		Map<String, String> resultado = new HashMap<String, String>();

		if (projeto.getNome() == null || projeto.getNome().isEmpty()) {
			resultado.put("nome", MENSAGEM_CAMPO_OBRIGATORIO);
		}

		if (projeto.getDescricao() == null || projeto.getDescricao().isEmpty()) {
			resultado.put("descricao", MENSAGEM_CAMPO_OBRIGATORIO);
		}

		if (projeto.getInicio() == null) {
			resultado.put("inicio", MENSAGEM_CAMPO_OBRIGATORIO);
		}

		if (!projeto.isPeriodoValido()) {
			resultado.put("inicio", MENSAGEM_DATA_INICIO_TERMINO);
		}

		if (projeto.getTermino() == null) {
			resultado.put("termino", MENSAGEM_CAMPO_OBRIGATORIO);
		}

		if (!projeto.isDataTerminoFutura()) {
			resultado.put("termino", MENSAGEM_DATA_TERMINO_FUTURA);
		}
		
		if (projeto.getParticipacoes().isEmpty()){
			resultado.put("participacoes", MENSAGEM_SEM_PARTICIPANTES);
		} else {
			boolean ehParticipante = false;
			for(Participacao p : projeto.getParticipacoes()){
				ehParticipante = ehParticipante || p.getParticipante().equals(projeto.getAutor()); 
			}
			if(!ehParticipante) resultado.put("participacoes", MENSAGEM_AUTOR_NAO_PARTICIPANTE);
		}

		if (projeto.getLocal() == null || projeto.getLocal().isEmpty()) {
			resultado.put("local", MENSAGEM_CAMPO_OBRIGATORIO);
		}

		if (projeto.getAtividades() == null
				|| projeto.getAtividades().isEmpty()) {
			resultado.put("atividades", MENSAGEM_CAMPO_OBRIGATORIO);
		}

		if (projeto.getDocumentos() == null
				|| projeto.getDocumentos().isEmpty()) {
			resultado.put("documentos", MENSAGEM_CAMPO_OBRIGATORIO);
		}

		return resultado;
	}
	
}
