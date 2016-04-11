package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.utils.Constants.PASTA_DOCUMENTOS_GPA;

import java.io.File;
import java.io.FileInputStream;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufc.quixada.npi.repository.GenericRepository;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.service.DocumentoService;

@Named
public class DocumentoServiceImpl implements DocumentoService {

	@Autowired
	private GenericRepository<Documento> documentoRepository;

	@Override
	public Documento getDocumento(Long id) {
		return documentoRepository.find(Documento.class, id);
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
