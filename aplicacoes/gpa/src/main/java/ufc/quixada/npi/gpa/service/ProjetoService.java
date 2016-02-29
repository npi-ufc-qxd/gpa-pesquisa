package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;

public interface ProjetoService {

	void cadastrar(Projeto projeto);

	void atualizar(Projeto projeto);

	void remover(Projeto projeto);

	void submeter(Projeto projeto);

	void atribuirParecerista(Projeto projeto, Parecer parecer);

	void emitirParecer(Projeto projeto);

	void avaliar(Projeto projeto);

	List<Projeto> getProjetosSubmetidos();
	
	List<Projeto> getProjetosSubmetidos(Long idAutor);
	
	List<Projeto> getProjetosNaoAvaliados(Long idAutor);

	List<Projeto> getProjetosAvaliados();

	List<Projeto> getProjetosAvaliados(Long idAutor);

	Projeto getProjeto(Long id);

	List<Projeto> getProjetos(Long idAutor);

	List<Projeto> getProjetosByParticipante(Long idParticipante);

	List<Projeto> getProjetos(StatusProjeto status);

	List<Projeto> getProjetos(Long idAutor, StatusProjeto status);

	List<Projeto> getProjetosAguardandoParecer(Long idParecerista);

	List<Participacao> getParticipacoes(Long idPessoa);

	void removerParticipacao(Projeto projeto, Participacao participacao);

	Participacao getParticipacao(Long idParticipacao);

	List<Participacao> getParticipacoesByProjeto(Long idProjeto);

	boolean isParticipante(Pessoa pessoa, Projeto projeto);
	
	/**
	 * Exibe lista de {@link Projeto} que o autor coordena, com status APROVADO e APROVADO_COM_RESTRICAO.
	 * <p>São exibidos registros com "termino >= now()".
	 * 
	 * @param idAutor {@link Long}
	 * @return {@link List} {@link Projeto}
	 */
	List<Projeto> getProjetosCoordenaAprovadosAtualmente(Long idAutor);
	
	/**
	 * Exibe lista de {@link Projeto} que o autor coordenou, com status APROVADO e APROVADO_COM_RESTRICAO.
	 * <p>São exibidos registros com "termino < now()".
	 * 
	 * @param idAutor {@link Long}
	 * @return {@link List} {@link Projeto}
	 */
	List<Projeto> getProjetosCoordenouAprovadosAtualmente(Long idAutor);
	
	/**
	 * Exibe lista de {@link Projeto} que o usuário participa mas não é autor, com status APROVADO e APROVADO_COM_RESTRICAO.
	 * <p>São exibidos registros com "termino >= now()".
	 * 
	 * @param idAutor {@link Long}
	 * @return {@link List} {@link Projeto}
	 */
	List<Projeto> getProjetosParticipaAprovadosAtualmente(Long idAutor);
	
	/**
	 * Exibe lista de {@link Projeto} que o usuário participou mas não é autor, com status APROVADO e APROVADO_COM_RESTRICAO.
	 * <p>São exibidos registros com "termino < now()".
	 * 
	 * @param idAutor {@link Long}
	 * @return {@link List} {@link Projeto}
	 */
	List<Projeto> getProjetosParticipouAprovadosAtualmente(Long idAutor);

}
