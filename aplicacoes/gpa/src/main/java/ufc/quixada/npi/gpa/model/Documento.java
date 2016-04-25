package ufc.quixada.npi.gpa.model;


import java.util.Date;

import javax.persistence.CascadeType;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



@Entity
@EntityListeners(DocumentoEntityListener.class)
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nomeOriginal;
	
	private String nome;
	
	private String caminhoArquivo;
	
	private String extensao;
	@ManyToOne
	private Pessoa pessoa;
	
	private Date data;
	
	@Column(insertable = false, updatable = false)
	private byte[] arquivo;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	public Pessoa getPessoa(){
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa){
		this.pessoa = pessoa;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeOriginal() {
		return nomeOriginal;
	}

	public void setNomeOriginal(String nomeOriginal) {
		this.nomeOriginal = nomeOriginal;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}
	
	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public String getCaminho() {
		return caminhoArquivo;
	}

	public void setCaminho(String caminho) {
		this.caminhoArquivo = caminho;
	}
	
	public enum TipoDocumento {
		ARQUIVO_PROJETO, ANEXO, DOCUMENTO_PARECER, ATA_HOMOLOGACAO, OFICIO_HOMOLOGACAO
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Documento) {
			Documento other = (Documento) obj;
			if (other != null && other.getId() != null && this.id != null && other.getId().equals(this.id)) {
				return true;
			}
		}
		return false;
	}
	
}

