package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.ProjetoPorDocenteRelatorio;
import ufc.quixada.npi.gpa.model.Relatorio;

public interface ProjetoPorDocenteRelatorioService {

	Integer getCargaHorariaTotal(List<ProjetoPorDocenteRelatorio> projetos);

	Double getValorTotalBolsas(List<ProjetoPorDocenteRelatorio> projetos);

	List<ProjetoPorDocenteRelatorio> getProjetos(Long id, Integer ano);
	
	Relatorio getRelatorio(Long id, Integer ano);

}
