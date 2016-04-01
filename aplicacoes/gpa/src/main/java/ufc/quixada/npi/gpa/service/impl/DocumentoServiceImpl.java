package ufc.quixada.npi.gpa.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import static ufc.quixada.npi.gpa.utils.Constants.PASTA_DOCUMENTOS_GPA;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.service.DocumentoService;
import br.ufc.quixada.npi.repository.GenericRepository;

@Named
public class DocumentoServiceImpl implements DocumentoService {

	@Autowired
	private GenericRepository<Documento> documentoRepository;

	@Override
	public void salvar(Documento documento, String projetoCodigo) {
		String novoNome = System.currentTimeMillis()+"_"+documento.getNome();
		documento.setNomeOriginal(novoNome);

		File subDir = new File(PASTA_DOCUMENTOS_GPA, projetoCodigo);
		subDir.mkdirs();

		try {
			File file = new File(subDir, documento.getNomeOriginal());
			FileOutputStream fop = new FileOutputStream(file);
			file.createNewFile();
			fop.write(documento.getArquivo());
			fop.flush();
			fop.close();
			
			String path = file.getPath();
			path = path.replaceAll("\\\\", "/");
			documento.setCaminho(path);
			documentoRepository.save(documento);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void salvar(List<Documento> documentos, String projetoCodigo) {
		for (Documento documento : documentos) {
			salvar(documento, projetoCodigo);
		}
	}

	@Override
	public Documento getDocumento(Long id) {
		return documentoRepository.find(Documento.class, id);
	}

	@Override
	public void remover(Documento documento) {
		documentoRepository.delete(documento);
		File file = new File(documento.getCaminho());
		removerArquivos(file);
	}

	@Override
	public void removerPastaProjeto(String codigoProjeto) {
		File subDir = new File(PASTA_DOCUMENTOS_GPA, codigoProjeto);
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

	public byte[] getArquivo(Documento documento) {
		FileInputStream fileInputStream = null;
		File file = new File(documento.getCaminho());
		byte[] bFile = new byte[(int) file.length()];

		try {
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bFile;
	}

}
