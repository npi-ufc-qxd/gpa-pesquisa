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
import ufc.quixada.npi.gpa.model.ParecerRelator;
import ufc.quixada.npi.gpa.model.ParecerTecnico;
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
	private GenericRepository<ParecerTecnico> parecerRepository;
	
	@Inject
	private GenericRepository<ParecerRelator> parecerRelatorRepository;
	
	@Inject
	private DocumentoService documentoService;

	@Override
	public void cadastrar(Projeto projeto) {
		projeto.setStatus(StatusProjeto.NOVO);
		projetoRepository.save(projeto);

		String codigo = geraCodigoProjeto(projeto.getId());
		projeto.setCodigo(codigo);
	}
	
	@Override
	public void update(Projeto projeto) {
		projetoRepository.update(projeto);
	}

	@Override
	public void submeter(Projeto projeto) {
		projeto.setStatus(StatusProjeto.SUBMETIDO);
		projeto.setSubmissao(new Date());
		projetoRepository.update(projeto);
	}
	
	@Override
	public void submeterPendencias(Projeto projeto) {
		projeto.setStatus(StatusProjeto.AGUARDANDO_PARECER);
		projetoRepository.update(projeto);
	}

	@Override
	public void submeterPendenciasRelator(Projeto projeto) {
		projeto.setStatus(StatusProjeto.AGUARDANDO_AVALIACAO);
		projetoRepository.update(projeto);
	}
	
	@Override
	public void atribuirParecerista(Projeto projeto, ParecerTecnico parecer) {
		projeto.setParecer(parecer);
		projeto.setStatus(StatusProjeto.AGUARDANDO_PARECER);
		projetoRepository.update(projeto);
	}
	
	@Override
	public void alterarParecerista(ParecerTecnico parecer) {
		parecerRepository.update(parecer);
	}

	@Override
	public void atribuirRelator(Projeto projeto, ParecerRelator parecerRelator){
		projeto.setParecerRelator(parecerRelator);
		projetoRepository.update(projeto);
	}
	
	@Override
	public void alterarRelator(ParecerRelator parecerRelator){
		parecerRelatorRepository.update(parecerRelator);
	}
	
	@Override
	public void emitirParecer(Projeto projeto) {		
		projeto.setStatus(StatusProjeto.AGUARDANDO_AVALIACAO);
		projetoRepository.update(projeto);
	}

	@Override
	public void homologar(Projeto projeto) {			
		projeto.setHomologacao(new Date());
		projetoRepository.update(projeto);
	}

	@Override
	public void remover(Projeto projeto) {
		projetoRepository.delete(projeto);
		documentoService.removerPastaProjeto(projeto.getCodigo());
	}

	@Override
	public List<Projeto> getProjetosEmTramitacao() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("novo", StatusProjeto.NOVO);
		params.put("aprovado", StatusProjeto.APROVADO);
		params.put("reprovado", StatusProjeto.REPROVADO);
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where status != :novo AND status != :aprovado AND status != :reprovado",
				params);
	}

	@Override
	public List<Projeto> getProjetosNaoHomologados(Long idCoordenador) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idCoordenador);
		params.put("aprovado", StatusProjeto.APROVADO);
		params.put("reprovado", StatusProjeto.REPROVADO);
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where (status != :aprovado AND status != :reprovado) AND (coordenador.id = :id)",
				params);
	}
	
	@Override
	public List<Projeto> getProjetosHomologados() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("aprovado", StatusProjeto.APROVADO);
		params.put("reprovado", StatusProjeto.REPROVADO);
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where status = :aprovado OR status = :reprovado", params);
	}

	@Override
	public List<Projeto> getProjetosHomologados(Long idCoordenador) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idCoordenador);
		params.put("aprovado", StatusProjeto.APROVADO);
		params.put("reprovado", StatusProjeto.REPROVADO);

		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where ((status = :aprovado) OR (status = :reprovado)) AND (coordenador.id = :id)",
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
		params.put("resolvendo_pendencias", StatusProjeto.RESOLVENDO_PENDENCIAS);
		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where parecer.parecerista.id = :id and (status = :aguardando_parecer or status =:resolvendo_pendencias)", params);
	}
	
	@Override
	public List<Projeto> getProjetosParecerEmitido(Long idParecerista) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idParecerista);
		params.put("aguardando_parecer", StatusProjeto.AGUARDANDO_PARECER);
		params.put("resolvendo_pendencias", StatusProjeto.RESOLVENDO_PENDENCIAS);

		return projetoRepository.find(QueryType.JPQL,
				"from Projeto where parecer.parecerista.id = :id AND (status != :aguardando_parecer and status != :resolvendo_pendencias)",
				params);
	}
	
	@Override
	public List<Projeto> getProjetosAguardandoAvaliacao(Long idRelator) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idRelator);
		params.put("aguardando_avaliacao", StatusProjeto.AGUARDANDO_AVALIACAO);
		return projetoRepository.find(QueryType.JPQL, "from Projeto where parecerRelator.relator.id = :id AND status = :aguardando_avaliacao" , params);
	};

	@Override
	public List<Projeto> getProjetosAvaliados(Long idRelator) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idRelator);
		params.put("aguardando_avaliacao", StatusProjeto.AGUARDANDO_AVALIACAO);
		return projetoRepository.find(QueryType.JPQL, "from Projeto where parecerRelator.relator.id = :id AND status != :aguardando_avaliacao" , params);
	}
	
	@Override
	public List<Participacao> getParticipacoes(Long idPessoa) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idPessoa);
		params.put("statusAprovado", StatusProjeto.APROVADO);
		List<Participacao> lista = participacaoRepository.find(QueryType.JPQL,
				"select distinct part FROM Participacao as part WHERE part.participante.id = :id and part.projeto.status = :statusAprovado and part.projeto.coordenador.id != :id", params);
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

	public List<Projeto> getProjetosCoordenaHomologadosAtualmente(Long idCoordenador) {
		Map<String, Object> params = new HashMap<>();
		params.put("idCoordenador", idCoordenador);
		params.put("statusAprovado", StatusProjeto.APROVADO);
		
		return projetoRepository.find(QueryType.JPQL, 
				"FROM Projeto WHERE coordenador.id = :idCoordenador AND termino >= current_date() AND status = :statusAprovado", 
				params);
	}
	
	public List<Projeto> getProjetosCoordenouHomologadosAtualmente(Long idCoordenador) {
		Map<String, Object> params = new HashMap<>();
		params.put("idCoordenador", idCoordenador);
		params.put("statusAprovado", StatusProjeto.APROVADO);
		
		return projetoRepository.find(QueryType.JPQL, 
				"FROM Projeto WHERE coordenador.id = :idCoordenador AND termino < current_date() AND status = :statusAprovado", 
				params);
	}
	
	public List<Projeto> getProjetosParticipaHomologadosAtualmente(Long idCoordenador) {
		Map<String, Object> params = new HashMap<>();
		params.put("idCoordenador", idCoordenador);
		params.put("statusAprovado", StatusProjeto.APROVADO);
		
		return projetoRepository.find(QueryType.JPQL, 
				"SELECT proj FROM Projeto AS proj JOIN proj.participacoes part WHERE part.participante.id = :idCoordenador AND proj.coordenador.id <> :idCoordenador AND proj.status = :statusAprovado AND termino >= current_date()",
				params);
	}
	
	public List<Projeto> getProjetosParticipouHomologadosAtualmente(Long idCoordenador) {
		Map<String, Object> params = new HashMap<>();
		params.put("idCoordenador", idCoordenador);
		params.put("statusAprovado", StatusProjeto.APROVADO);
		
		return projetoRepository.find(QueryType.JPQL,
				"SELECT proj FROM Projeto AS proj JOIN proj.participacoes part WHERE part.participante.id = :idCoordenador AND proj.coordenador.id <> :idCoordenador AND proj.status = :statusAprovado AND termino < current_date()",
				params);
	}
}

