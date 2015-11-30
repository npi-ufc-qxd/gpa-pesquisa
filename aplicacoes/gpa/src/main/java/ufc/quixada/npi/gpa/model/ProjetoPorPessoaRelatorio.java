package ufc.quixada.npi.gpa.model;

import java.math.BigDecimal;

public class ProjetoPorPessoaRelatorio {

	private Long id;
	private String nomeProjeto;
	private String vinculo;
	private Integer cargaHoraria;
	private BigDecimal valorBolsa;
	public String getNomeProjeto() {
		return nomeProjeto;
	}
	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}
	public String getVinculo() {
		return vinculo;
	}
	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}
	public Integer getCargaHoraria() {
		return cargaHoraria;
	}
	public void setCargaHoraria(Integer cargaHorariaTotal) {
		this.cargaHoraria = cargaHorariaTotal;
	}
	public BigDecimal getValorTotalBolsa() {
		return valorBolsa;
	}
	public void setValorBolsa(BigDecimal valorTotalBolsa) {
		this.valorBolsa = valorTotalBolsa;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
