package ufc.quixada.npi.gpa.service;

import java.io.File;

import ufc.quixada.npi.gpa.model.Documento;


public interface DocumentoService {
	
	Documento getDocumento(Long id);
	
	void removerPastaProjeto(String codigoProjeto);
	
	public void removerArquivos(File f);
	
	byte[] getArquivo(Documento documento);
	
}
