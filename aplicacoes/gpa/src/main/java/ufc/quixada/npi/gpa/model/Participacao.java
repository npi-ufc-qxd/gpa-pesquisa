package ufc.quixada.npi.gpa.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Participacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="projeto_id")
	private Projeto projeto;
	
	@OneToOne
	private Pessoa participante;
	
	private Integer mesInicio;
	private Integer anoInicio;
	private Integer mesTermino;
	private Integer anoTermino;
	private BigDecimal bolsaValorMensal;
	private Integer cargaHorariaMensal;
	
	public Integer getMesInicio() {
		return mesInicio;
	}

	public void setMesInicio(Integer mesInicio) {
		this.mesInicio = mesInicio;
	}

	public Integer getAnoInicio() {
		return anoInicio;
	}

	public void setAnoInicio(Integer anoInicio) {
		this.anoInicio = anoInicio;
	}

	public Integer getMesTermino() {
		return mesTermino;
	}

	public void setMesTermino(Integer mesTermino) {
		this.mesTermino = mesTermino;
	}

	public Integer getAnoTermino() {
		return anoTermino;
	}

	public void setAnoTermino(Integer anoTermino) {
		this.anoTermino = anoTermino;
	}
	
	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public BigDecimal getBolsaValorMensal() {
		return bolsaValorMensal;
	}

	public void setBolsaValorMensal(BigDecimal bolsaValorMensal) {
		this.bolsaValorMensal = bolsaValorMensal;
	}

	public Integer getCargaHorariaMensal() {
		return cargaHorariaMensal;
	}

	public void setCargaHorariaMensal(Integer cargaHorariaMensal) {
		this.cargaHorariaMensal = cargaHorariaMensal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getParticipante() {
		return participante;
	}

	public void setParticipante(Pessoa participante) {
		this.participante = participante;
	}
	
	@Override
	public String toString(){
		return (this.participante != null ? this.participante.getNome() : "Sem Participante") + ";"
				+ (this.projeto != null ? this.projeto.getNome() : "Sem Projeto") + ";"
				+ mesInicio + ";"
				+ anoInicio + ";"
				+ mesTermino + ";"
				+ anoTermino + ";"
				+ bolsaValorMensal + ";"
				+ cargaHorariaMensal;
	}
	
}
