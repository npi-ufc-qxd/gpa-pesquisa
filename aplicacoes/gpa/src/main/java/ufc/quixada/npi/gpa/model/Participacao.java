package ufc.quixada.npi.gpa.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	@OneToOne
	@JoinColumn(name="pessoa_externa_id")
	private PessoaExterna participanteExterno;
	
	@Enumerated(EnumType.STRING)
	private TipoParticipacao tipo;
	
	@Column(columnDefinition="boolean")
	private boolean externo;
	
	private Integer mesInicio;
	private Integer anoInicio;
	private Integer mesTermino;
	private Integer anoTermino;
	private BigDecimal bolsaValorMensal;
	private Integer cargaHorariaMensal;
	
	
	public boolean isExterno() {
		return externo;
	}

	public void setExterno(boolean isExterno) {
		this.externo = isExterno;
	}

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

	
	public TipoParticipacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoParticipacao tipo) {
		this.tipo = tipo;
	}

	public PessoaExterna getParticipanteExterno() {
		return participanteExterno;
	}

	public void setParticipanteExterno(PessoaExterna participanteExterno) {
		this.participanteExterno = participanteExterno;
	}

	@Override
	public String toString() {
		return "Participacao [id=" + id + ", projeto=" + projeto + ", participante=" + participante + ", mesInicio=" + mesInicio
				+ ", anoInicio=" + anoInicio + ", mesTermino=" + mesTermino + ", anoTermino=" + anoTermino + ", bolsaValorMensal="
				+ bolsaValorMensal + ", cargaHorariaMensal=" + cargaHorariaMensal + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Participacao)){
			return false;
		}
		Participacao participacao = (Participacao) obj;
		return toString().equals(participacao.toString());
	}
	
	public enum TipoParticipacao{
		ALUNO("ALUNO"),PESQUISADOR("PESQUISADOR"),DOCENTE("DOCENTE"),TECNICO_ADMINISTRATIVO("TÉCNICO ADMINISTRATIVO");		
		String descricao;
		private TipoParticipacao(String descricao){
			this.descricao = descricao;
		}
		public String getDescricao(){
			return this.descricao;
		}
	}
}
