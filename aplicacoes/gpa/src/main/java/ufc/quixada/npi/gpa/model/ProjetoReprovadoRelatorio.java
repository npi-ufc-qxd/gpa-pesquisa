package ufc.quixada.npi.gpa.model;

import java.util.Date;

public class ProjetoReprovadoRelatorio {
	private String nomeProjeto;
	private String nomeCoordenador;
	private Date dataDeSubimissao;
	private Date dataDeAvaliacao;

	public String getNome() {
		return nomeProjeto;
	}

	public void setNome(String nome) {
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

	

	
}
