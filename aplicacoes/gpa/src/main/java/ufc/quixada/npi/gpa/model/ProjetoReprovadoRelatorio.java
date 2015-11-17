package ufc.quixada.npi.gpa.model;

import java.util.Date;

public class ProjetoReprovadoRelatorio {
	private Long id;
	private String nomeProjeto;
	private String nomeCoordenador;
	private Date dataDeSubimissao;
	private Date dataDeAvaliacao;

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nome) {
		this.nomeProjeto = nome;
	}

	public String getNomeCoordenador() {
		return nomeCoordenador;
	}

	public void setNomeCoordenador(String nomeCoordenador) {
		this.nomeCoordenador = nomeCoordenador;
	}

	public Date getDataDeSubimissao() {
		return dataDeSubimissao;
	}

	public void setDataDeSubimissao(Date subimissao) {
		this.dataDeSubimissao = subimissao;
	}

	public Date getDataDeAvaliacao() {
		return dataDeAvaliacao;
	}

	public void setDataDeAvaliacao(Date dataDeAvaliacao) {
		this.dataDeAvaliacao = dataDeAvaliacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	
}
