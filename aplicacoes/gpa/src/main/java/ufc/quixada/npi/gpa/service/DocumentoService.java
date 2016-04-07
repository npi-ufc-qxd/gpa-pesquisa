package ufc.quixada.npi.gpa.service;

import java.io.File;
import java.util.List;

import ufc.quixada.npi.gpa.model.Documento;


public interface DocumentoService {
	
	void salvar(Documento documento, String projetoCodigo);

	void salvar(List<Documento> documentos, String projetoCodigo);
	
	Documento getDocumento(Long id);
	
	void removerPastaProjeto(String codigoProjeto);
	
	public void removerArquivos(File f);
	
	byte[] getArquivo(Documento documento);
	
}
