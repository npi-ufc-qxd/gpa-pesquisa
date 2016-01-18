package ufc.quixada.npi.gpa.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.service.DocumentoService;
import br.ufc.quixada.npi.repository.GenericRepository;

@Named
public class DocumentoServiceImpl implements DocumentoService {
	
	@Autowired
	private GenericRepository<Documento> documentoRepository;

	@Override
	public void salvar(Documento documento) {
		String novoNome = System.currentTimeMillis() + "-"
				+ documento.getNome();
		documento.setNomeOriginal(novoNome);

		File homedir = new File(System.getProperty("user.home"));
		File dir = new File(homedir,"gpa-pesquisa-uploads");
		dir.mkdir();
		File subDir = new File(dir, documento.getProjeto().getNome());
		subDir.mkdir();

		try {
			File file = new File(subDir, documento.getNomeOriginal());
			FileOutputStream fop = new FileOutputStream(file);
			file.createNewFile();
			fop.write(documento.getArquivo());
			fop.flush();
			fop.close();

			documento.setCaminho("gpa-pesquisa-uploads/"
					+ documento.getProjeto().getNome() + "/"
					+ documento.getNomeOriginal());
			documentoRepository.save(documento);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	
	@Override
	public void salvar(List<Documento> documentos) {
		for(Documento documento : documentos) {
			String novoNome = System.currentTimeMillis()+"-"+documento.getNome();
			documento.setNomeOriginal(novoNome);
			
			File homedir = new File(System.getProperty("user.home"));
			File dir = new File(homedir,"gpa-pesquisa-uploads");
			dir.mkdir();
			File subDir = new File( dir, documento.getProjeto().getNome());
			subDir.mkdir();
			
			try {
				File file = new File(subDir, documento.getNomeOriginal());
				FileOutputStream fop = new FileOutputStream(file);
				file.createNewFile();
				fop.write(documento.getArquivo());
				fop.flush();
				fop.close();

				documento.setCaminho("gpa-pesquisa-uploads/"
						+ documento.getProjeto().getNome() + "/"
						+ documento.getNomeOriginal());
				documentoRepository.save(documento);
			} catch(IOException ex){
			    ex.printStackTrace();
			}
		}
	}

	@Override
	public Documento getDocumento(Long id) {
		return documentoRepository.find(Documento.class, id);
	}

	@Override
	public void remover(Documento documento) {
		documentoRepository.delete(documento);
		File homedir = new File(System.getProperty("user.home"));
		File dir = new File(homedir,"gpa-pesquisa-uploads");
		if(dir.isDirectory()){
			File subDir = new File(dir,documento.getProjeto().getNome());
			if(subDir.isDirectory()){
				File arquivo = new File(subDir,documento.getNomeOriginal());
				arquivo.delete();
			}
		}		
	}

	@Override
	public void removerPastaProjeto(String nomeProjeto) {
		File homedir = new File(System.getProperty("user.home"));
		File dir = new File(homedir,"gpa-pesquisa-uploads");
		File subDir = new File(dir,nomeProjeto);
		removerArquivos(subDir);
		
	}
	public void removerArquivos(File f) {
	     if (f.isDirectory()) {
	         File[] files = f.listFiles();
	         for (File file : files) {
	             removerArquivos(file);
	         }
	     }
	     f.delete();
	  }
	

}
