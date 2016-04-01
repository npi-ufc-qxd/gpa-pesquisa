package ufc.quixada.npi.gpa.model;

import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Projeto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo;

	@Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres")
	private String nome;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date inicio;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date termino;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date avaliacao;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date submissao;

	@Column(columnDefinition = "TEXT")
	@Size(min = 5, message = "A descrição deve ter no mínimo 5 caracteres")
	private String descricao;

	@ManyToOne
	private Pessoa coordenador;

	@Column(columnDefinition = "TEXT")
	private String atividades;

	private String local;
	
	@Enumerated(EnumType.STRING)
	private StatusProjeto status;
	
	@OneToMany(mappedBy = "projeto", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
	private List<Participacao> participacoes;

	@OneToMany(cascade = {CascadeType.REMOVE})
	@JoinTable(name = "projeto_documento", joinColumns = @JoinColumn(name = "projeto_id",referencedColumnName="id"),
	inverseJoinColumns = @JoinColumn(name = "documento_id",referencedColumnName="id"))
	private List<Documento> documentos;

	@OneToMany(mappedBy = "projeto", cascade = CascadeType.REMOVE)
	@OrderBy(value="data")
	private List<Comentario> comentarios;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	private Parecer parecer;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private Documento ata;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private Documento oficio;
	
	@Column(columnDefinition = "TEXT")
	private String observacaoAvaliacao;

	public List<Participacao> getParticipacoes() {
		return participacoes;
	}

	public void setParticipacoes(List<Participacao> participacoes) {
		this.participacoes = participacoes;
	}
	
	public Date getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Date avaliacao) {
		this.avaliacao = avaliacao;
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getTermino() {
		return termino;
	}

	public void setTermino(Date termino) {
		this.termino = termino;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAtividades() {
		return atividades;
	}

	public void setAtividades(String atividades) {
		this.atividades = atividades;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public StatusProjeto getStatus() {
		return status;
	}

	public void setStatus(StatusProjeto status) {
		this.status = status;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void addDocumento(Documento documento) {
		if(this.documentos == null){
			this.documentos = new ArrayList<>();
		}
		this.documentos.add(documento);
	}

	public Pessoa getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Pessoa coordenador) {
		this.coordenador = coordenador;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Date getSubmissao() {
		return submissao;
	}

	public void setSubmissao(Date submissao) {
		this.submissao = submissao;
	}

	public Parecer getParecer() {
		return parecer;
	}

	public void setParecer(Parecer parecer) {
		this.parecer = parecer;
	}

	public Documento getAta() {
		return ata;
	}

	public void setAta(Documento ata) {
		this.ata = ata;
	}

	public Documento getOficio() {
		return oficio;
	}

	public void setOficio(Documento oficio) {
		this.oficio = oficio;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Projeto) {
			Projeto other = (Projeto) obj;
			if (other != null && other.getId() != null && this.id != null
					&& other.getId().equals(this.id)) {
				return true;
			}
		}
		return false;
	}

	public List<Comentario> getComentarios() {
		
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public String getObservacaoAvaliacao() {
		return observacaoAvaliacao;
	}

	public void setObservacaoAvaliacao(String observacaoAvaliacao) {
		this.observacaoAvaliacao = observacaoAvaliacao;
	}

	

	@Override
	public String toString() {
		return "Projeto [id=" + id + ", codigo=" + codigo + ", nome=" + nome + ", inicio=" + inicio + ", termino="
				+ termino + ", descricao=" + descricao + ", atividades=" + atividades + ", local=" + local + "]";
	}



	public enum StatusProjeto {

		NOVO("NOVO"), SUBMETIDO("SUBMETIDO"), AGUARDANDO_PARECER("AGUARDANDO PARECER"), 
		AGUARDANDO_AVALIACAO("AGUARDANDO AVALIAÇÃO"), APROVADO("APROVADO"), REPROVADO("REPROVADO"),
		APROVADO_COM_RESTRICAO("APROVADO COM RESTRIÇÃO");
		
		private String descricao;

		private StatusProjeto(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return this.descricao;
		}
	}

	public enum Evento {
		SUBMISSAO, ATRIBUICAO_PARECERISTA, EMISSAO_PARECER, AVALIACAO
	}
	
	@Deprecated
	public boolean isDataTerminoFutura() {
		if (this.termino != null && comparaDatas(new Date(), this.termino) > 0) {
			return false;
		}
		return true;
	}
	
	@Deprecated
	public boolean isPeriodoValido() {
		if (this.termino != null && this.inicio != null && comparaDatas(this.inicio, this.termino) > 0) {
			return false;
		}
		return true;
	}
	
	@Deprecated
	private int comparaDatas(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		if (calendar1.get(Calendar.YEAR) > calendar2.get(Calendar.YEAR)) {
			return 1;
		} else if (calendar1.get(Calendar.YEAR) < calendar2.get(Calendar.YEAR)) {
			return -1;
		} else {
			if (calendar1.get(Calendar.MONTH) > calendar2.get(Calendar.MONTH)) {
				return 1;
			} else if (calendar1.get(Calendar.MONTH) < calendar2
					.get(Calendar.MONTH)) {
				return -1;
			} else {
				if (calendar1.get(Calendar.DAY_OF_MONTH) > calendar2
						.get(Calendar.DAY_OF_MONTH)) {
					return 1;
				} else if (calendar1.get(Calendar.DAY_OF_MONTH) < calendar2
						.get(Calendar.DAY_OF_MONTH)) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	public void adicionarParticipacao(Participacao participacao) {
        if (!getParticipacoes().contains(participacao)) {
            getParticipacoes().add(participacao);
        }
	}

	public void removerParticipacao(Participacao participacao) {
		this.participacoes.remove(participacao);
	}

}
