package ufc.quixada.npi.gpa.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.ProjetoPorDocenteRelatorio;
import ufc.quixada.npi.gpa.model.Relatorio;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.service.ProjetoPorDocenteRelatorioService;

@Named
public class ProjetoPorDocenteRelatorioServiceImpl implements
		ProjetoPorDocenteRelatorioService {

	@Inject
	private ProjetoServiceImpl projetoService;

	@Inject
	private PessoaService pessoaService;

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
			valorTotal += projetoPorDocenteRelatorio.getValorBolsa();
		}

		return valorTotal;
	}

	@Override
	public List<ProjetoPorDocenteRelatorio> getProjetos(Long id, Integer ano) {
		List<ProjetoPorDocenteRelatorio> projetos = new ArrayList<ProjetoPorDocenteRelatorio>();
		List<Projeto> projetoParticipante = projetoService
				.getProjetosByParticipante(id);
		List<Projeto> projetoAutor = projetoService.getProjetosByUsuario(id);
		
		Calendar calen = Calendar.getInstance();

		if (!projetoParticipante.isEmpty()) {
			for (Projeto projeto : projetoParticipante) {
				calen.setTimeInMillis(projeto.getInicio().getTime());

				if ((calen.get(Calendar.YEAR) == ano && !projetos.contains(projeto)) || (ano == 0 && !projetos.contains(projeto))) {
					ProjetoPorDocenteRelatorio projetoPorDocenteRelatorio = new ProjetoPorDocenteRelatorio();
//					projetoPorDocenteRelatorio.setHoras(projeto
//							.getCargaHoraria());
					projetoPorDocenteRelatorio.setNome(projeto.getNome());
//					projetoPorDocenteRelatorio.setValorBolsa(projeto
//							.getValorDaBolsa());
					projetoPorDocenteRelatorio.setVinculo("PARTICIPANTE");

					projetos.add(projetoPorDocenteRelatorio);
				}
			}
		}
		if (!projetoAutor.isEmpty()) {
			for (Projeto projeto : projetoAutor) {
				calen.setTimeInMillis(projeto.getInicio().getTime());

				if ((calen.get(Calendar.YEAR) == ano && !projetos.contains(projeto)) || (ano == 0 && !projetos.contains(projeto))) {
					ProjetoPorDocenteRelatorio projetoPorDocenteRelatorio = new ProjetoPorDocenteRelatorio();
//					projetoPorDocenteRelatorio.setHoras(projeto
//							.getCargaHoraria());
					projetoPorDocenteRelatorio.setNome(projeto.getNome());
//					projetoPorDocenteRelatorio.setValorBolsa(projeto
//							.getValorDaBolsa());
					projetoPorDocenteRelatorio.setVinculo("AUTOR");

					projetos.add(projetoPorDocenteRelatorio);
				}
			}
		}

		return projetos;
	}

	@Override
	public Relatorio getRelatorio(Long id, Integer ano) {
		Relatorio relatorio = new Relatorio();

		List<ProjetoPorDocenteRelatorio> projetos = getProjetos(id, ano);

		relatorio.setNomeDoDocente(pessoaService.getPessoaById(id).getNome());
		relatorio.setProjetos(projetos);
		relatorio.setAnoDeConsulta(ano);
		relatorio.setCargaHorariaTotal(getCargaHorariaTotal(projetos));
		relatorio.setValorTotalDaBolsa(getValorTotalBolsas(projetos));

		return relatorio;
	}
}
