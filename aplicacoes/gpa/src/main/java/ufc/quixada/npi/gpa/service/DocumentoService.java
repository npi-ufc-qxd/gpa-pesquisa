package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Documento;


public interface DocumentoService {
	
	void salvar(Documento documento);
	
	void salvar(List<Documento> documentos);
	
	Documento getDocumento(Long id);
	
	void remover(Documento documento);
	
}
