package ufc.quixada.npi.gpa.service;

import java.util.List;
import java.util.Map;

import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Projeto;

public interface ProjetoService {
	
	void cadastrar(Projeto projeto);
	
	void atualizar(Projeto projeto);
	
	void remover(Projeto projeto);
	
	void submeter(Projeto projeto);
	
	Projeto getProjetoById(Long id);

	List<Projeto> getProjetosSubmetidos();

	List<Projeto> getProjetosAtribuidos();

	List<Projeto> getProjetosByUsuario(Long id);
	
	List<Projeto> getProjetosByParticipante(Long id);

	List<Projeto> getProjetosAvaliadosDoUsuario(Long id);

	List<Projeto> getProjetosAvaliados();
	
	List<Projeto> getProjetosAprovados();
	
	List<Projeto> getProjetosReprovadosByUsuario(Long id);
	
	List<Projeto> getProjetosByUsuarioCoordenou(Long id);
	
	List<Projeto> getProjetosByUsuarioParticipou(Long id);

	List<Projeto> getProjetosAguardandoParecer(Long id);

	Map<String, String> atribuirParecerista(Projeto projeto, Parecer parecer);
	
	Map<String, String> emitirParecer(Projeto projeto);
	
	Map<String, String> avaliar(Projeto projeto);

	List<Participacao> getParticipacoesDePessoa(Long idPessoa);

	void removerParticipacao(Projeto projeto, Participacao participacao);

	Participacao getParticipacaoById(Long idParticipacao);

	List<Participacao> getParticipacoesDoProjeto(Long idProjeto);
	
}
