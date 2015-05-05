package ufc.quixada.npi.gpa.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.ProjetoPorDocenteRelatorio;
import ufc.quixada.npi.gpa.model.Relatorio;
import ufc.quixada.npi.gpa.service.ProjetoPorDocenteRelatorioService;

@Named
public class ProjetoPorDocenteRelatorioServiceImpl implements
		ProjetoPorDocenteRelatorioService {

	@Inject
	private ProjetoServiceImpl projetoService;

	private String nomeDocente;

	@Override
	public Integer getCargaHorariaTotal(
			List<ProjetoPorDocenteRelatorio> projetos) {
		Integer valorTotal = 0;
		for (ProjetoPorDocenteRelatorio projetoPorDocenteRelatorio : projetos) {
			valorTotal += projetoPorDocenteRelatorio.getHoras();
		}
		return valorTotal;
	}

	@Override
	public Double getValorTotalBolsas(List<ProjetoPorDocenteRelatorio> projetos) {
		Double valorTotal = 0.0;
		for (ProjetoPorDocenteRelatorio projetoPorDocenteRelatorio : projetos) {
			valorTotal += projetoPorDocenteRelatorio.getValBolsa();
		}

		return valorTotal;
	}

	@Override
	public List<ProjetoPorDocenteRelatorio> getProjetos(Long id, int ano) {
		List<ProjetoPorDocenteRelatorio> projetos = new ArrayList<ProjetoPorDocenteRelatorio>();
		List<Projeto> projetoParticipante = projetoService
				.getProjetosByParticipante(id);
		List<Projeto> projetoAutor = projetoService.getProjetosByUsuario(id);
		ProjetoPorDocenteRelatorio projetoPorDocenteRelatorio = new ProjetoPorDocenteRelatorio();
		Calendar calen = Calendar.getInstance();

		if (projetoParticipante != null) {
			for (Projeto projeto : projetoParticipante) {
				nomeDocente = projeto.getAutor().getNome();
				calen.setTimeInMillis(projeto.getInicio().getTime());

				if (calen.get(Calendar.YEAR) == ano) {
					projetoPorDocenteRelatorio.setHoras(projeto
							.getCargaHoraria());
					projetoPorDocenteRelatorio.setNome(projeto.getNome());
					projetoPorDocenteRelatorio.setValBolsa(projeto
							.getValorDaBolsa());
					projetoPorDocenteRelatorio.setVinculo("PARTICIPANTE");

					projetos.add(projetoPorDocenteRelatorio);
				}
			}
		} else if (projetoAutor != null) {
			for (Projeto projeto : projetoAutor) {
				nomeDocente = projeto.getAutor().getNome();
				calen.setTimeInMillis(projeto.getInicio().getTime());

				if (calen.get(Calendar.YEAR) == ano) {
					projetoPorDocenteRelatorio.setHoras(projeto
							.getCargaHoraria());
					projetoPorDocenteRelatorio.setNome(projeto.getNome());
					projetoPorDocenteRelatorio.setValBolsa(projeto
							.getValorDaBolsa());
					projetoPorDocenteRelatorio.setVinculo("AUTOR");

					projetos.add(projetoPorDocenteRelatorio);
				}
			}
		}

		return projetos;
	}

	@Override
	public List<Relatorio> getRelatorio(Long id, int ano) {
		Relatorio relatorio = new Relatorio();
		List<Relatorio> relatorios = new ArrayList<Relatorio>();
		List<ProjetoPorDocenteRelatorio> projetos = getProjetos(id, ano);

		relatorio.setNomeDoDocente(nomeDocente);
		relatorio.setProjetos(projetos);
		relatorio.setAnoDeConsulta(ano);
		relatorio.setCargaHorariaTotal(getCargaHorariaTotal(projetos));
		relatorio.setValorTotalDaBolsa(getValorTotalBolsas(projetos));

		relatorios.add(relatorio);

		return relatorios;
	}
}
