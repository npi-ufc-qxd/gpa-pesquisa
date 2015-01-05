package ufc.quixada.npi.gpa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Parecer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private StatusPosicionamento status;
	
	@Column(columnDefinition = "TEXT")
	private String observacao;
	
	@Column(columnDefinition = "TEXT")
	private String parecer;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataAtribuicao;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataRealizacao;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date prazo;
	
	@ManyToOne
	@JoinColumn(name="documento_id")
	private Documento documento;
	
	@ManyToOne
	@JoinColumn(name = "parecerista_id")
	private Pessoa parecerista;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusPosicionamento getStatus() {
		return status;
	}

	public void setStatus(StatusPosicionamento status) {
		this.status = status;
	}

	public String getObservacao() {
		return observacao;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataAtribuicao() {
		return dataAtribuicao;
	}

	public void setDataAtribuicao(Date dataAtribuicao) {
		this.dataAtribuicao = dataAtribuicao;
	}

	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public Date getPrazo() {
		return prazo;
	}

	public void setPrazo(Date prazo) {
		this.prazo = prazo;
	}

	public Pessoa getParecerista() {
		return parecerista;
	}

	public void setParecerista(Pessoa parecerista) {
		this.parecerista = parecerista;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	@Override
	public String toString() {
		return "Parecer [id=" + id + ", status=" + status + ", comentario="
				+ observacao
				+ ", dataAtribuicao=" + dataAtribuicao + ", dataRealizacao="
				+ dataRealizacao + ", prazo=" + prazo + "]";
	}


	public enum StatusPosicionamento {
		FAVORAVEL("FAVORÁVEL") , NAO_FAVORAVEL("NÃO FAVORÁVEL");
		
		private String descricao;

		private StatusPosicionamento(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return this.descricao;
		}
	}
}


