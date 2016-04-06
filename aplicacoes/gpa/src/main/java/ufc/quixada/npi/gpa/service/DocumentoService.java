package ufc.quixada.npi.gpa.service;

import java.io.File;
import java.util.List;

import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Projeto;


public interface DocumentoService {
	
	void salvar(Documento documento, Projeto projeto);
	
	void salvar(List<Documento> documentos, Projeto projeto);
	
	Documento getDocumento(Long id);
	
	void removerPastaProjeto(String codigoProjeto);
	
	public void removerArquivos(File f);
	
	byte[] getArquivo(Documento documento);
	
}
