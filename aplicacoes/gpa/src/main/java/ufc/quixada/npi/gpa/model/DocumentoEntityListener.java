package ufc.quixada.npi.gpa.model;

import java.io.File;

import javax.persistence.PreRemove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ufc.quixada.npi.gpa.service.DocumentoService;

public class DocumentoEntityListener implements ApplicationContextAware{

	private static ApplicationContext context;
	
	@Autowired
	private DocumentoService documentoService;
	
	@PreRemove
	public void removeArquivo(Documento documento){
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

}
