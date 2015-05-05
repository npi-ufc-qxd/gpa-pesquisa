package ufc.quixada.npi.gpa.model;

public class ProjetoPorDocenteRelatorio {
	private String nome;
	private String vinculo;
	private Integer horas;
	private Double valorBolsa;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getVinculo() {
		return vinculo;
	}

	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}

	public Integer getHoras() {
		return horas;
	}

	public void setHoras(Integer horas) {
		this.horas = horas;
	}

	public Double getValBolsa() {
		return valorBolsa;
	}

	public void setValBolsa(Double valBolsa) {
		this.valorBolsa = valBolsa;
	}
}
