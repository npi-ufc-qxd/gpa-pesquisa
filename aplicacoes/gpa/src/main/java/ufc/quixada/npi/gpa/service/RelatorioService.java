package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Relatorio;

public interface RelatorioService {

	Relatorio getProjetosAprovadosRelatorio(String inicio, String termino);

	Relatorio getProjetosReprovadosRelatorio(String submissao);

	Relatorio getProjetosPorPessoa(Long id, String ano);
	
	List<Projeto> getProjetosIntervalosAprovados(String inicio, String termino);
	
	List<Projeto> getProjetosIntervaloReprovados(String submissao);
	
	List<Projeto> getProjetosIntervaloPorPessoa(Long id, String ano);
	
}
