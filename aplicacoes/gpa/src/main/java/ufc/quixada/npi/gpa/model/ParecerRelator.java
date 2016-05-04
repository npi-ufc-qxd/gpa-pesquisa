package ufc.quixada.npi.gpa.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import ufc.quixada.npi.gpa.model.ParecerTecnico.StatusPosicionamento;

@Entity
public class ParecerRelator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private StatusPosicionamento status;
	
	@DateTimeFormat
	private Date data;
	
	@Column(columnDefinition = "TEXT")
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name = "relator_id")
	private Pessoa relator;
	
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
	private List<Pendencia> restricoes;
	
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Pessoa getRelator() {
		return relator;
	}

	public void setRelator(Pessoa relator) {
		this.relator = relator;
	}

	public List<Pendencia> getRestricoes() {
		return restricoes;
	}
	
	public void addRestricao(Pendencia restricao){
		if(this.restricoes == null){
			this.restricoes = new ArrayList<Pendencia>();
		}
		this.restricoes.add(restricao);
	}
}
