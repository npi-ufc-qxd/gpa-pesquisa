package ufc.quixada.npi.gpa.model;

import java.util.Date;
import java.math.BigDecimal;

public class ProjetoAprovadoRelatorio {
	
	private Long id;
	private String nomeCoordenador;
	private String nomeProjeto;
	private Date dataInicio;
	private Date dataTermino;
	private int qtdBolsas;
	private BigDecimal valorTotalBolsas;
	
	public String getNomeCoordenador() {
		return nomeCoordenador;
	}
	public void setNomeCoordenador(String nomeCoordenador) {
		this.nomeCoordenador = nomeCoordenador;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public String getNomeProjeto() {
		return nomeProjeto;
	}
	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}
	public int getQtdBolsas() {
		return qtdBolsas;
	}
	public void setQtdBolsas(int qtdBolsas) {
		this.qtdBolsas = qtdBolsas;
	}
	public Date getDataTermino() {
		return dataTermino;
	}
	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}
	public BigDecimal getValorTotalBolsas() {
		return valorTotalBolsas;
	}
	public void setValorTotalBolsas(BigDecimal valorTotalBolsas) {
		this.valorTotalBolsas = valorTotalBolsas;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
