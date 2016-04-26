package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Log;
import ufc.quixada.npi.gpa.model.ParecerRelator;
import ufc.quixada.npi.gpa.model.ParecerTecnico;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;

public interface ProjetoService {

	void cadastrar(Projeto projeto);
	
	void update(Projeto projeto);

	void remover(Projeto projeto);

	void submeter(Projeto projeto);
	
	void submeterPendencias(Projeto projeto);
	
	void submeterPendenciasRelator(Projeto projeto);

	void atribuirParecerista(Projeto projeto, ParecerTecnico parecer);
	
	void alterarParecerista(ParecerTecnico parecer);
	
	void atribuirRelator(Projeto projeto, ParecerRelator parecerRelator);
	
	void alterarRelator(ParecerRelator parecerRelator);

	void emitirParecer(Projeto projeto);

	void homologar(Projeto projeto);

	List<Projeto> getProjetosEmTramitacao();
	
	List<Projeto> getProjetosNaoHomologados(Long idCoordenador);

	List<Projeto> getProjetosHomologados();

	List<Projeto> getProjetosHomologados(Long idCoordenador);

	Projeto getProjeto(Long id);

	List<Projeto> getProjetos(Long idCoordenador);

	List<Projeto> getProjetosByParticipante(Long idParticipante);

	List<Projeto> getProjetos(StatusProjeto status);

	List<Projeto> getProjetos(Long idCoordenador, StatusProjeto status);

	List<Projeto> getProjetosAguardandoParecer(Long idParecerista);
	
	List<Projeto> getProjetosParecerEmitido(Long idParecerista);
	
	List<Projeto> getProjetosAguardandoAvaliacao(Long idUsuarioLogado);
	
	List<Projeto> getProjetosAvaliados(Long idRelator);

	List<Participacao> getParticipacoes(Long idPessoa);

	void removerParticipacao(Projeto projeto, Participacao participacao);

	Participacao getParticipacao(Long idParticipacao);

	List<Participacao> getParticipacoesByProjeto(Long idProjeto);
	
	void salvarLog(Log log);

	boolean isParticipante(Pessoa pessoa, Projeto projeto);
	
	/**
	 * Exibe lista de {@link Projeto} que o autor coordena, com status APROVADO e APROVADO_COM_RESTRICAO.
	 * <p>São exibidos registros com "termino >= now()".
	 * 
	 * @param idCoordenador {@link Long}
	 * @return {@link List} {@link Projeto}
	 */
	List<Projeto> getProjetosCoordenaHomologadosAtualmente(Long idCoordenador);
	
	/**
	 * Exibe lista de {@link Projeto} que o autor coordenou, com status APROVADO e APROVADO_COM_RESTRICAO.
	 * <p>São exibidos registros com "termino < now()".
	 * 
	 * @param idCoordenador {@link Long}
	 * @return {@link List} {@link Projeto}
	 */
	List<Projeto> getProjetosCoordenouHomologadosAtualmente(Long idCoordenador);
	
	/**
	 * Exibe lista de {@link Projeto} que o usuário participa mas não é coordenador, com status APROVADO e APROVADO_COM_RESTRICAO.
	 * <p>São exibidos registros com "termino >= now()".
	 * 
	 * @param idCoordenador {@link Long}
	 * @return {@link List} {@link Projeto}
	 */
	List<Projeto> getProjetosParticipaHomologadosAtualmente(Long idCoordenador);
	
	/**
	 * Exibe lista de {@link Projeto} que o usuário participou mas não é coordenador, com status APROVADO e APROVADO_COM_RESTRICAO.
	 * <p>São exibidos registros com "termino < now()".
	 * 
	 * @param idCoordenador {@link Long}
	 * @return {@link List} {@link Projeto}
	 */

	List<Projeto> getProjetosParticipouHomologadosAtualmente(Long idCoordenador);


}
