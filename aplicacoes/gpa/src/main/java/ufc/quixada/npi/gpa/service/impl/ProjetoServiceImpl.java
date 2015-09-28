package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_AUTOR_NAO_PARTICIPANTE;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_CAMPO_OBRIGATORIO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_DATA_INICIO_TERMINO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_DATA_TERMINO_FUTURA;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_SEM_PARTICIPANTES;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	public void remover(Projeto projeto) {
		projetoRepository.delete(projeto);
	}

	@Override
	public List<Projeto> getProjetosSubmetidos() {
		return projetoRepository
				.find(QueryType.JPQL,
						"from Projeto as p where p.status = 'SUBMETIDO' or p.status = 'AGUARDANDO_PARECER' or p.status = 'AGUARDANDO_AVALIACAO'",
						null);
	}

	@Override
	public List<Projeto> getProjetosAtribuidos() {
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto as p where p.status = 'AGUARDANDO_PARECER' ",
				null);
	}

	@Override
	public List<Projeto> getProjetosByUsuario(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where autor.id = :id", params);
	}

	@Override
	public List<Projeto> getProjetosByParticipante(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return projetoRepository
				.find(QueryType.JPQL,
						"select distinct proj FROM Projeto as proj JOIN proj.participacoes part WHERE part.participante.id = :id and proj.status != 'NOVO'",
						params);
	}
	
	@Override
	public Participacao getParticipacaoById(Long id) {
		return participacaoRepository.find(Participacao.class, id);
	}
	
	@Override
	public List<Participacao> getParticipacoesDePessoa(Long idPessoa) {
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
	public List<Participacao> getParticipacoesDoProjeto(Long idProjeto) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idProjeto);
		List<Participacao> lista = participacaoRepository
				.find(QueryType.JPQL,
						  "select distinct part FROM Participacao as part "
						+ "WHERE part.projeto.id = :id ",
						params);
		return lista;
	}

	@Override
	public List<Projeto> getProjetosAvaliadosDoUsuario(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return projetoRepository
				.find(QueryType.JPQL,
						"from Projeto as p where ((p.status = 'APROVADO') OR (p.status = 'REPROVADO') OR (p.status = 'APROVADO_COM_RESTRICAO')) AND (autor.id = :id)",
						params);
	}

	@Override
	public List<Projeto> getProjetosAvaliados() {
		return projetoRepository
				.find(QueryType.JPQL,
						"from Projeto as p where (p.status = 'APROVADO') OR (p.status = 'REPROVADO') OR (p.status = 'APROVADO_COM_RESTRICAO')",
						null);
	}

	@Override
	public List<Projeto> getProjetosAguardandoParecer(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return projetoRepository
				.find(QueryType.JPQL,
						"select p from Projeto as p where p.parecer.parecerista.id = :id and p.status = 'AGUARDANDO_PARECER'",
						params);
	}

	@Override
	public Projeto getProjetoById(Long id) {
		return projetoRepository.find(Projeto.class, id);
	}

	private String geraCodigoProjeto(Long id) {
		NumberFormat formatador = new DecimalFormat("#0000");
		return "PESQ" + formatador.format(id);
	}

	@Override
	public void submeter(Projeto projeto) {		
		projeto.setStatus(StatusProjeto.SUBMETIDO);
		projeto.setSubmissao(new Date());
		projetoRepository.update(projeto);
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
	public List<Projeto> getProjetosAprovados() {
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto as p where (p.status = 'APROVADO')", null);
	}

	@Override
	public List<Projeto> getProjetosReprovadosByUsuario(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return projetoRepository
				.find(QueryType.JPQL,
						"from Projeto as p where ((p.status = 'REPROVADO') and (autor.id = :id))",
						params);
	}

	@Override
	public List<Projeto> getProjetosByUsuarioCoordenou(Long id) {
		List<Projeto> projetos = new ArrayList<Projeto>();
		Date data = new Date();
		Calendar dataAtual = Calendar.getInstance();
		Calendar dataTermino = Calendar.getInstance();

		dataAtual.setTimeInMillis(data.getTime());

		for (Projeto projeto : getProjetosByUsuario(id)) {
			if (projeto.getTermino() != null) {
				dataTermino.setTimeInMillis(projeto.getTermino().getTime());
				if (dataAtual.getTimeInMillis() > dataTermino.getTimeInMillis()) {
					projetos.add(projeto);
				}
			}
		}
		return projetos;
	}

	@Override
	public List<Projeto> getProjetosByUsuarioParticipou(Long id) {

		List<Projeto> projetos = new ArrayList<Projeto>();
		Date data = new Date();
		Calendar dataAtual = Calendar.getInstance();
		Calendar dataTermino = Calendar.getInstance();

		dataAtual.setTimeInMillis(data.getTime());

		for (Projeto projeto : getProjetosByParticipante(id)) {
			if (projeto.getTermino() != null) {
				dataTermino.setTimeInMillis(projeto.getTermino().getTime());
				if (dataAtual.getTimeInMillis() > dataTermino.getTimeInMillis()) {
					projetos.add(projeto);
				}
			}
		}
		return projetos;
	}

	@Override
	public void removerParticipacao(Projeto projeto,	Participacao participacao) {
		if(projeto.getParticipacoes().contains(participacao)) {
			projeto.removerParticipacao(participacao);
			participacaoRepository.delete(participacao);
		}
	}
}
