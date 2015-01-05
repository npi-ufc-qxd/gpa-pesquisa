package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Projeto;


public interface DocumentoService {
	
	void salvar(Documento documento);
	
	void salvar(List<Documento> documentos);
	
	Documento getDocumentoById(Long id);
	
	void remover(Documento documento);
	
	List<Documento> getDocumentoByProjeto(Projeto projeto);
	
}
