package ufc.quixada.npi.gpa.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.model.ProjetoAprovadoRelatorio;
import ufc.quixada.npi.gpa.model.ProjetoPorPessoaRelatorio;
import ufc.quixada.npi.gpa.model.ProjetoReprovadoRelatorio;
import ufc.quixada.npi.gpa.model.Relatorio;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.service.RelatorioService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;

@Named
public class RelatorioServiceImpl implements
		RelatorioService {

	@Inject
	private ProjetoServiceImpl projetoService;

	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private GenericRepository<Projeto> projetoRepository;
	
	@Override
	public List<Projeto> getProjetosIntervalosAprovados(StatusProjeto status, String inicio, String termino){
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(!inicio.isEmpty() && !termino.isEmpty()){
			params.put("status", status);
			params.put("ini", inicio);
			params.put("ter", termino);
			return projetoRepository.find(QueryType.JPQL,
					"from Projeto where status = :status and ((inicio between TO_DATE (:ini, 'yyyy-mm') and TO_DATE (:ter, 'yyyy-mm')) or (termino between TO_DATE (:ini, 'yyyy-mm') and TO_DATE (:ter, 'yyyy-mm') or (inicio < TO_DATE (:ini, 'yyyy-mm') and termino > TO_DATE (:ter, 'yyyy-mm'))))", params);
		}
		if(inicio.isEmpty() && !termino.isEmpty()){
			params.put("status", status);	
			params.put("ter", termino);
			return projetoRepository.find(QueryType.JPQL, "from Projeto where status = :status and (inicio <= TO_DATE (:ter, 'yyyy-mm') or termino <= TO_DATE (:ter, 'yyyy-mm'))"
					, params);
		}
		if(!inicio.isEmpty() && termino.isEmpty()){
			params.put("status", status);	
			params.put("ini", inicio);
			return projetoRepository.find(QueryType.JPQL,
					"from Projeto where status = :status and (inicio >= TO_DATE (:ini, 'yyyy-mm') or termino >= TO_DATE (:ini, 'yyyy-mm'))", params);
		}
		
		List<Projeto> projetosBusca = new ArrayList<Projeto>();
		projetosBusca = projetoService.getProjetos(status);
		List<Projeto> projetos = projetosBusca;
		return projetos;
		
	}
	
	
	@Override
	public Relatorio getProjetosAprovadosRelatorio(String inicio, String termino) {
		List<ProjetoAprovadoRelatorio> projetosAprovadosRelatorio = new ArrayList<ProjetoAprovadoRelatorio>();
		List<Projeto> projetos = this.getProjetosIntervalosAprovados(StatusProjeto.APROVADO, inicio, termino);
		
		for(Projeto p:projetos){
			ProjetoAprovadoRelatorio projetoAprovado = new ProjetoAprovadoRelatorio();
			projetoAprovado.setId(p.getId());
			projetoAprovado.setNomeCoordenador(p.getAutor().getNome());
			projetoAprovado.setNomeProjeto(p.getNome());
			projetoAprovado.setDataInicio(p.getInicio());
			projetoAprovado.setDataTermino(p.getTermino());
			int qtdBolsas =0;
			BigDecimal valorTotal = new BigDecimal(0);
			for(Participacao pt:p.getParticipacoes()){
				int mesesParticipacao =0;
				if(pt.getAnoInicio().equals(pt.getAnoTermino()))
					mesesParticipacao = pt.getMesTermino() - pt.getMesInicio() + 1;
				else{
					int anosCompletos = pt.getAnoTermino() - pt.getAnoInicio() -1;
					mesesParticipacao = 13 - pt.getMesInicio()+ (anosCompletos * 12) + pt.getMesTermino();} 
				BigDecimal valorDecimal = new BigDecimal(mesesParticipacao);
				if(!pt.getBolsaValorMensal().equals(0)){
					qtdBolsas++;
					valorDecimal = valorDecimal.multiply(pt.getBolsaValorMensal());
					valorTotal = valorTotal.add(valorDecimal);}
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
	public List<Projeto> getProjetosIntervaloReprovados(StatusProjeto status, String submissao){
		if(!submissao.isEmpty()){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", status);
			params.put("submissao", submissao);
			System.out.println(submissao);
			return projetoRepository.find(QueryType.JPQL,
					"from Projeto where status = :status and to_char(submissao, 'yyyy-mm') = :submissao", params);
		}
		List<Projeto> projetosBusca = new ArrayList<Projeto>();
		projetosBusca = projetoService.getProjetos(status);
		List<Projeto> projetos = projetosBusca;
		return projetos;
	}

	@Override
	public Relatorio getProjetosReprovadosRelatorio(String submissao) {
		List<ProjetoReprovadoRelatorio> projetosReprovadosRelatorio = new ArrayList<ProjetoReprovadoRelatorio>();
		List <Projeto> projetos = this.getProjetosIntervaloReprovados(StatusProjeto.REPROVADO, submissao);
		for(Projeto p:projetos){
			ProjetoReprovadoRelatorio projetoReprovado = new ProjetoReprovadoRelatorio();
			projetoReprovado.setId(p.getId());
			projetoReprovado.setNomeProjeto(p.getNome());
			projetoReprovado.setNomeCoordenador(p.getAutor().getNome());
			projetoReprovado.setDataDeSubimissao(p.getSubmissao());
			projetoReprovado.setDataDeAvaliacao(p.getAvaliacao());
			projetosReprovadosRelatorio.add(projetoReprovado);
		}
		Relatorio r = new Relatorio();
		r.setProjetosReprovados(projetosReprovadosRelatorio);
		return r;
	}
	
	@Override
	public List<Projeto> getProjetosIntervaloPorPessoa(Long id, String ano){
		if(!ano.isEmpty()){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			params.put("ano", Integer.parseInt(ano));
			return projetoRepository.find(QueryType.JPQL,
					"select distinct proj FROM Projeto as proj JOIN proj.participacoes part WHERE part.participante.id = :id and proj.status = 'APROVADO' and year(proj.inicio) <= :ano and year(proj.termino) >= :ano)"
					, params);
			/*return projetoRepository.find(QueryType.JPQL,
					"select distinct proj FROM Projeto as proj JOIN proj.participacoes part WHERE part.participante.id = :id and proj.status == 'APROVADO' and ( inicio = TO_DATE (:ano, 'yyyy') or termino = TO_DATE (:ano, 'yyyy') or (inicio < TO_DATE (:ano, 'yyyy') and termino > TO_DATE (:ano, 'yyyy'))"
					, params);*/
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return projetoRepository.find(QueryType.JPQL,
					"select distinct proj FROM Projeto as proj JOIN proj.participacoes part WHERE part.participante.id = :id and proj.status = 'APROVADO' "
				, params);
	}

	@Override
	public Relatorio getProjetosPorPessoa(Long id, String ano) {
		List<ProjetoPorPessoaRelatorio> projetosPorPessoaRelatorio = new ArrayList<ProjetoPorPessoaRelatorio>();
		List<Projeto> projetos = this.getProjetosIntervaloPorPessoa(id, ano);
		Relatorio r = new Relatorio();
		r.setNomeUsuario(pessoaService.getPessoa(id).getNome());
		if(!ano.isEmpty())
			r.setAnoConsulta(Integer.parseInt(ano));
		BigDecimal valorTotal = new BigDecimal(0);
		for(Projeto p:projetos){
			ProjetoPorPessoaRelatorio projetoPorPessoa = new ProjetoPorPessoaRelatorio();
			projetoPorPessoa.setId(p.getId());
			projetoPorPessoa.setNomeProjeto(p.getNome());
			if(p.getAutor().getId() == id)
				projetoPorPessoa.setVinculo("COORDENADOR");
			else
				projetoPorPessoa.setVinculo("PARTICIPANTE");
			for(Participacao pt:p.getParticipacoes()){
				if(pt.getParticipante().getId().equals(id)){
					int mesesParticiacao = 0;
					if(pt.getAnoInicio().equals(pt.getAnoTermino()))
						mesesParticiacao = pt.getMesTermino() - pt.getMesInicio() + 1;
					else{
						int anosCompletos = pt.getAnoTermino() - pt.getAnoInicio() -1;
						mesesParticiacao = 13 - pt.getMesInicio()+ (anosCompletos * 12) + pt.getMesTermino(); 
					} 
					projetoPorPessoa.setCargaHoraria(pt.getCargaHorariaMensal()*mesesParticiacao);
					BigDecimal valorDecimal = new BigDecimal(mesesParticiacao);
					valorDecimal=valorDecimal.multiply(pt.getBolsaValorMensal());
					valorTotal=valorTotal.add(valorDecimal);
					projetoPorPessoa.setValorBolsa(valorDecimal);
					break;
				}
			}
			projetosPorPessoaRelatorio.add(projetoPorPessoa);
		}
		r.setValorTotalBolsasUsuario(valorTotal);
		int cargaHorariaTotal = 0;
		for(ProjetoPorPessoaRelatorio pp:projetosPorPessoaRelatorio){
			cargaHorariaTotal += pp.getCargaHoraria();
		}
		r.setCargaHorariaTotalUsuario(cargaHorariaTotal);
		r.setProjetosPorPessoa(projetosPorPessoaRelatorio);
		return r;
	}
}
