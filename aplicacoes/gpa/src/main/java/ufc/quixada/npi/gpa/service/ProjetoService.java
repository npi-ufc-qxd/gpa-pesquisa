package ufc.quixada.npi.gpa.service;

import java.util.List;
import java.util.Map;

import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Projeto;

public interface ProjetoService {
	
	Map<String, String> cadastrar(Projeto projeto);
	
	Map<String, String> atualizar(Projeto projeto);
	
	void remover(Projeto projeto);
	
	Map<String, String> submeter(Projeto projeto);
	
	Projeto getProjetoById(Long id);

	List<Projeto> getProjetosSubmetidos();

	List<Projeto> getProjetosAtribuidos();

	List<Projeto> getProjetosByUsuario(Long id);
	
	List<Projeto> getProjetosByParticipante(Long id);

	List<Projeto> getProjetosAvaliadosDoUsuario(Long id);

	List<Projeto> getProjetosAvaliados();

	List<Projeto> getProjetosAguardandoParecer(Long id);

	Map<String, String> atribuirParecerista(Projeto projeto, Parecer parecer);
	
	Map<String, String> emitirParecer(Projeto projeto);
	
	Map<String, String> avaliar(Projeto projeto);
	
}
