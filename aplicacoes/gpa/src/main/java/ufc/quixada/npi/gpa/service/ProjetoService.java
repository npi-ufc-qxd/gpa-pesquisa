package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Participacao;
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
}
