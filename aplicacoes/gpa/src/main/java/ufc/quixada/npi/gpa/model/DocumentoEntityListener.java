package ufc.quixada.npi.gpa.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ufc.quixada.npi.gpa.service.DocumentoService;

public class DocumentoEntityListener implements ApplicationContextAware{

	private static ApplicationContext context;
	
	@Autowired
	private DocumentoService documentoService;
	
	@PrePersist
	public void salvarArquivo(Documento documento){
		context.getAutowireCapableBeanFactory().autowireBean(this);
		
		String caminhoDiretorio = getDiretorioDocumento(documento);
		File diretorio = new File(caminhoDiretorio);
		diretorio.mkdirs();
		
		try {
			File arquivo = new File(diretorio, documento.getNomeOriginal());
			FileOutputStream fop = new FileOutputStream(arquivo);
			arquivo.createNewFile();
			fop.write(documento.getArquivo());
			fop.flush();
			fop.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	@PreRemove
	public void removerArquivo(Documento documento){
		context.getAutowireCapableBeanFactory().autowireBean(this);
		
		File file = new File(documento.getCaminho());
		documentoService.removerArquivos(file);
	}
	
	public ApplicationContext getApplicationContext(){
		return context;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) {
		context = ctx;
	}
	
	private String getDiretorioDocumento(Documento documento) {
		String diretorio = documento.getCaminho();
		diretorio = diretorio.replace("/"+documento.getNomeOriginal(), "");
		return diretorio;
	}

}
