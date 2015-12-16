package ufc.quixada.npi.gpa.service;

import java.util.Date;
import java.util.List;

import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Relatorio;

public interface RelatorioService {

	Relatorio getProjetosAprovadosRelatorio(String iInterInicio, String fInterInicio, String iInterTermino, String fInterTermino);
	
	Relatorio getProjetosReprovadosRelatorio(String submissao_inicio,String submissao_termino);

	Relatorio getProjetosPorPessoa(Long id, String ano);
	
	List<Projeto> getProjetosIntervalosAprovados(String iInterInicio, String fInterInicio, String iInterTermino, String fInterTermino);
	
	List<Projeto> getProjetosIntervaloReprovados(String submissao_inicio, String submissao_termino);
	
	List<Projeto> getProjetosIntervaloPorPessoa(Long id, String ano);

	Date getMomento();

}