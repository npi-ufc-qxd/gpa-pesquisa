package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Documento;


public interface DocumentoService {
	
	void salvar(Documento documento, String projetoCodigo);
	
	void salvar(List<Documento> documentos, String projetoCodigo);
	
	Documento getDocumento(Long id);
	
	void remover(Documento documento);
	
	void removerPastaProjeto(String codigoProjeto);
	
	byte[] getArquivo(Documento documento);
	
}
