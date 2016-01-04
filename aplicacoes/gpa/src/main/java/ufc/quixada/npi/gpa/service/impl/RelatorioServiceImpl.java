package ufc.quixada.npi.gpa.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.model.ProjetoAprovadoRelatorio;
import ufc.quixada.npi.gpa.model.ProjetoPorPessoaRelatorio;
import ufc.quixada.npi.gpa.model.ProjetoReprovadoRelatorio;
import ufc.quixada.npi.gpa.model.Relatorio;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.service.RelatorioService;

@Named
public class RelatorioServiceImpl implements RelatorioService {

	@Inject
	private ProjetoServiceImpl projetoService;

	@Inject
	private PessoaService pessoaService;

	@Inject
	private GenericRepository<Projeto> projetoRepository;
	
	@Override
	public List<Projeto> getProjetosIntervalosAprovados(String iInterInicio, String fInterInicio, String iInterTermino, String fInterTermino){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder iniIntInicio = new StringBuilder();
		StringBuilder terIntInicio = new StringBuilder();
		StringBuilder iniIntTermino = new StringBuilder();
		StringBuilder terIntTermino = new StringBuilder();
		boolean vazio = true;
		
		if (!iInterInicio.isEmpty()) {
			iniIntInicio = iniIntInicio.append("inicio >= TO_DATE(:iInterInicio, 'yyyy-mm')");
			params.put("iInterInicio", iInterInicio);
			vazio = false;
		}
		
		if (!fInterInicio.isEmpty()) {
			if(!iInterInicio.isEmpty()) {
				terIntInicio.append(" and ");
			}
			terIntInicio.append("inicio <= TO_DATE(:fInterInicio, 'yyyy-mm')");
			params.put("fInterInicio", fInterInicio);
			vazio = false;
		}
		
		if (!iInterTermino.isEmpty()) {
			iniIntTermino.append("termino >= TO_DATE(:iInterTermino, 'yyyy-mm')");
			params.put("iInterTermino", iInterTermino);
			vazio = false;
		}
		
		if (!fInterTermino.isEmpty()) {
			if(!iInterTermino.isEmpty()) {
				terIntTermino.append(" and ");
			}
			terIntTermino.append("termino <= TO_DATE(:fInterTermino, 'yyyy-mm')");
			params.put("fInterTermino", fInterTermino);
			vazio = false;
		}
		
		StringBuilder consulta = new StringBuilder("from Projeto p where (status = 'APROVADO' or status = 'APROVADO_COM_RESTRICAO')");
		
		if (!vazio) {
			consulta.append(" and (");
			consulta.append(iniIntInicio);
			consulta.append(terIntInicio);
			if ((!iInterTermino.isEmpty() || !fInterTermino.isEmpty()) && (!iInterInicio.isEmpty() || !fInterInicio.isEmpty())) {
				consulta.append(" and ");
			}
			consulta.append(iniIntTermino);
			consulta.append(terIntTermino);
			consulta.append(")");
		}
		return projetoRepository.find(QueryType.JPQL, consulta.toString(), params);
	}
	
	@Override
	public Relatorio getProjetosAprovadosRelatorio(String iInterInicio, String fInterInicio, String iInterTermino, String fInterTermino) {
		List<ProjetoAprovadoRelatorio> projetosAprovadosRelatorio = new ArrayList<ProjetoAprovadoRelatorio>();
		List<Projeto> projetos = this.getProjetosIntervalosAprovados( iInterInicio,  fInterInicio,  iInterTermino,  fInterTermino);

		for (Projeto p : projetos) {
			ProjetoAprovadoRelatorio projetoAprovado = new ProjetoAprovadoRelatorio();
			projetoAprovado.setId(p.getId());
			projetoAprovado.setNomeCoordenador(p.getAutor().getNome());
			projetoAprovado.setNomeProjeto(p.getNome());
			projetoAprovado.setDataInicio(p.getInicio());
			projetoAprovado.setDataTermino(p.getTermino());
			int qtdBolsas = 0;
			BigDecimal valorTotal = new BigDecimal(0);
			for (Participacao pt : p.getParticipacoes()) {
				int mesesParticipacao = 0;
				if (pt.getAnoInicio().equals(pt.getAnoTermino()))
					mesesParticipacao = pt.getMesTermino() - pt.getMesInicio() + 1;
				else {
					int anosCompletos = pt.getAnoTermino() - pt.getAnoInicio() - 1;
					mesesParticipacao = 13 - pt.getMesInicio() + (anosCompletos * 12) + pt.getMesTermino();
				}
				BigDecimal valorDecimal = new BigDecimal(mesesParticipacao);
				if (!pt.getBolsaValorMensal().equals(0)) {
					qtdBolsas++;
					valorDecimal = valorDecimal.multiply(pt.getBolsaValorMensal());
					valorTotal = valorTotal.add(valorDecimal);
				}
			}
			projetoAprovado.setQtdBolsas(qtdBolsas);
			projetoAprovado.setValorTotalBolsas(valorTotal);
			projetosAprovadosRelatorio.add(projetoAprovado);
		}
		Relatorio r = new Relatorio();
		r.setProjetosAprovados(projetosAprovadosRelatorio);
		return r;
	}
	
	@Override
	public List<Projeto> getProjetosIntervaloReprovados(String submissao_inicio, String submissao_termino) {
		if (!submissao_inicio.isEmpty()) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("submissao_inicio", submissao_inicio);
			params.put("submissao_termino", submissao_termino);
			return projetoRepository.find(QueryType.JPQL,
					"from Projeto p where status = 'REPROVADO' and p.submissao between TO_DATE (:submissao_inicio, 'yyyy-mm') and TO_DATE (:submissao_termino, 'yyyy-mm')", params);
		}
		List<Projeto> projetosBusca = new ArrayList<Projeto>();
		projetosBusca = projetoService.getProjetos(StatusProjeto.REPROVADO);
		List<Projeto> projetos = projetosBusca;
		return projetos;
	}

	@Override
	public Relatorio getProjetosReprovadosRelatorio(String submissao_inicio,String submissao_termino) {
		List<ProjetoReprovadoRelatorio> projetosReprovadosRelatorio = new ArrayList<ProjetoReprovadoRelatorio>();
		List <Projeto> projetos = this.getProjetosIntervaloReprovados(submissao_inicio, submissao_termino);
		
		for(Projeto p:projetos){
			ProjetoReprovadoRelatorio projetoReprovado = new ProjetoReprovadoRelatorio();
			projetoReprovado.setId(p.getId());
			projetoReprovado.setNomeProjeto(p.getNome());
			projetoReprovado.setNomeCoordenador(p.getAutor().getNome());
			projetoReprovado.setDataDeSubimissao(p.getSubmissao());
			projetoReprovado.setDataDeAvaliacao(p.getAvaliacao());
			projetosReprovadosRelatorio.add(projetoReprovado);
		}
		
		Relatorio relatorio = new Relatorio();
		relatorio.setProjetosReprovados(projetosReprovadosRelatorio);
		return relatorio;
	}

	@Override
	public List<Projeto> getProjetosIntervaloPorPessoa(Long id, String ano) {
		if (!ano.isEmpty()) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			params.put("ano", Integer.parseInt(ano));
			return projetoRepository.find(QueryType.JPQL,
					"select distinct proj FROM Projeto proj JOIN proj.participacoes part WHERE part.participante.id = :id and (proj.status = 'APROVADO' or proj.status = 'APROVADO_COM_RESTRICAO') and (:ano between year(proj.inicio) and year(proj.termino))"
					, params);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return projetoRepository.find(QueryType.JPQL,
				"select distinct proj FROM Projeto as proj JOIN proj.participacoes part WHERE part.participante.id = :id and (proj.status = 'APROVADO' or proj.status = 'APROVADO_COM_RESTRICAO') ",
				params);
	}

	@Override
	public Relatorio getProjetosPorPessoa(Long id, String ano) {
		List<ProjetoPorPessoaRelatorio> projetosPorPessoaRelatorio = new ArrayList<ProjetoPorPessoaRelatorio>();
		List<Projeto> projetos = this.getProjetosIntervaloPorPessoa(id, ano);
		Relatorio relatorio = new Relatorio();
		relatorio.setNomeUsuario(pessoaService.getPessoa(id).getNome());
		if (!ano.isEmpty())
			relatorio.setAnoConsulta(Integer.parseInt(ano));
		BigDecimal valorTotal = new BigDecimal(0);
		for (Projeto projeto : projetos) {
			ProjetoPorPessoaRelatorio projetoPorPessoa = new ProjetoPorPessoaRelatorio();
			projetoPorPessoa.setId(projeto.getId());
			projetoPorPessoa.setNomeProjeto(projeto.getNome());
			if (projeto.getAutor().getId() == id)
				projetoPorPessoa.setVinculo("COORDENADOR");
			else
				projetoPorPessoa.setVinculo("PARTICIPANTE");
			BigDecimal valorBolsa = new BigDecimal(0);
			for (Participacao participacao : projeto.getParticipacoes()) {
				if (participacao.getParticipante().getId().equals(id)) {
					Integer mesesParticiacao = 0;
					if(ano.equals(participacao.getAnoInicio().toString()) && ano.equals(participacao.getAnoTermino().toString()))
						mesesParticiacao = participacao.getMesTermino() - participacao.getMesInicio() + 1;
					else if(!ano.equals(participacao.getAnoInicio().toString()) && ano.equals(participacao.getAnoTermino().toString()))
						mesesParticiacao = participacao.getMesTermino();
					else if(ano.equals(participacao.getAnoInicio().toString()) && !ano.equals(participacao.getAnoTermino().toString()))
						mesesParticiacao = 13 - participacao.getMesInicio();
					else
						mesesParticiacao = 12;
					projetoPorPessoa.setCargaHoraria(participacao.getCargaHorariaMensal() * mesesParticiacao);
					BigDecimal valorMesesParticipacao = new BigDecimal(mesesParticiacao);
					valorMesesParticipacao = valorMesesParticipacao.multiply(participacao.getBolsaValorMensal());
					valorBolsa = valorBolsa.add(valorMesesParticipacao);
					break;
				}
			}
			projetoPorPessoa.setValorBolsa(valorBolsa);
			valorTotal = valorTotal.add(valorBolsa);
			projetosPorPessoaRelatorio.add(projetoPorPessoa);
		}
		
		relatorio.setValorTotalBolsasUsuario(valorTotal);
		int cargaHorariaTotal = 0;
		for (ProjetoPorPessoaRelatorio pp : projetosPorPessoaRelatorio) {
			cargaHorariaTotal += pp.getCargaHoraria();
		}
		
		relatorio.setCargaHorariaTotalUsuario(cargaHorariaTotal);
		relatorio.setProjetosPorPessoa(projetosPorPessoaRelatorio);
		return relatorio;
	}

}
