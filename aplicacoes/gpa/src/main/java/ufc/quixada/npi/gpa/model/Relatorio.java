package ufc.quixada.npi.gpa.model;

import java.util.List;

public class Relatorio {

	private String nomeDoDocente;
	private Integer anoDeConsulta;
	private Integer cargaHorariaTotal;
	private Double valorTotalDaBolsa;
	private List<ProjetoPorDocenteRelatorio> projetos;

	public String getNomeDoDocente() {
		return nomeDoDocente;
	}

	public void setNomeDoDocente(String nomeDoDocente) {
		this.nomeDoDocente = nomeDoDocente;
	}

	public Integer getAnoDeConsulta() {
		return anoDeConsulta;
	}

	public void setAnoDeConsulta(Integer anoDeConsulta) {
		this.anoDeConsulta = anoDeConsulta;
	}

	public Integer getCargaHorariaTotal() {
		return cargaHorariaTotal;
	}

	public void setCargaHorariaTotal(Integer cargaHorariaTotal) {
		this.cargaHorariaTotal = cargaHorariaTotal;
	}

	public Double getValorTotalDaBolsa() {
		return valorTotalDaBolsa;
	}

	public void setValorTotalDaBolsa(Double valorTotalDaBolsa) {
		this.valorTotalDaBolsa = valorTotalDaBolsa;
	}

	public List<ProjetoPorDocenteRelatorio> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<ProjetoPorDocenteRelatorio> projetos) {
		this.projetos = projetos;
	}

}
