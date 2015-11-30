package ufc.quixada.npi.gpa.model;

import java.math.BigDecimal;
import java.util.List;

public class Relatorio {
	
	private String nomeUsuario;
	private Integer anoConsulta;
	private Integer cargaHorariaTotalUsuario;
	private BigDecimal valorTotalBolsasUsuario;
	
	private List<ProjetoAprovadoRelatorio> projetosAprovados;
	private List<ProjetoReprovadoRelatorio> projetosReprovados;
	private List<ProjetoPorPessoaRelatorio> projetosPorPessoa;

	public List<ProjetoAprovadoRelatorio> getProjetosAprovados() {
		return projetosAprovados;
	}

	public void setProjetosAprovados(List<ProjetoAprovadoRelatorio> projetos) {
		this.projetosAprovados = projetos;
	}

	public List<ProjetoReprovadoRelatorio> getProjetosReprovados() {
		return projetosReprovados;
	}

	public void setProjetosReprovados(List<ProjetoReprovadoRelatorio> projetosReprovados) {
		this.projetosReprovados = projetosReprovados;
	}
	
	public List<ProjetoPorPessoaRelatorio> getProjetosPorPessoa() {
		return projetosPorPessoa;
	}

	public void setProjetosPorPessoa(List<ProjetoPorPessoaRelatorio> projetosPorPessoa) {
		this.projetosPorPessoa = projetosPorPessoa;
	} 
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public Integer getAnoConsulta() {
		return anoConsulta;
	}
	public void setAnoConssulta(Integer ano) {
		this.anoConsulta = ano;
	}
	public Integer getCargaHorariaTotalUsuario() {
		return cargaHorariaTotalUsuario;
	}
	public void setCargaHorariaTotalUsuario(Integer cargaHorariaTotal) {
		this.cargaHorariaTotalUsuario = cargaHorariaTotal;
	}
	public BigDecimal getValorTotalBolsasUsuario() {
		return valorTotalBolsasUsuario;
	}
	public void setValorTotalBolsasUsuario(BigDecimal valorTotalBolsas) {
		this.valorTotalBolsasUsuario = valorTotalBolsas;
	}

	public void setAnoConsulta(Integer ano) {
		// TODO Auto-generated method stub
		
	}

	

}
