package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_DOCUMENTO_INEXISTENTE;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PERMISSAO_NEGADA;


import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.service.DocumentoService;
import ufc.quixada.npi.gpa.service.PessoaService;

@Controller
@RequestMapping("documento")
public class DocumentoController {
	
	@Inject
	private DocumentoService documentoService;
	
	@Inject
	private PessoaService pessoaService;
	
	@RequestMapping(value = "/{id-projeto}/{id-arquivo}", method = RequestMethod.GET)
	public HttpEntity<byte[]> getArquivo(@PathVariable("id-projeto") Long idProjeto, 
			@PathVariable("id-arquivo") Long idArquivo, HttpServletResponse response, 
			HttpSession session, Authentication authentication) {

		Documento documento = documentoService.getDocumento(idArquivo);
		byte[] arquivo = documentoService.getArquivo(documento);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "attachment; filename=" + documento.getNomeOriginal().replace(" ", "_"));
		headers.setContentLength(arquivo.length);

		return new HttpEntity<byte[]>(arquivo, headers);
	}
	
	@RequestMapping(value = "/{id-arquivo}", method = RequestMethod.GET)
	public HttpEntity<byte[]> getArquivoEdicao(@PathVariable("id-arquivo") Long idArquivo, HttpServletResponse response, 
			HttpSession session, Authentication authentication) {

		Documento documento = documentoService.getDocumento(idArquivo);
		byte[] arquivo = documentoService.getArquivo(documento);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "attachment; filename=" + documento.getNomeOriginal().replace(" ", "_"));
		headers.setContentLength(arquivo.length);

		return new HttpEntity<byte[]>(arquivo, headers);
	}

	
	@RequestMapping(value = "/excluir/{id}", method = RequestMethod.POST)
	@ResponseBody public  ModelMap excluir(@PathVariable("id") Long id, HttpSession session, Authentication authentication) {
		ModelMap model = new ModelMap();
		Documento documento = documentoService.getDocumento(id);
		if(documento == null) {
			model.addAttribute("result", "erro");
			model.addAttribute("mensagem", MENSAGEM_DOCUMENTO_INEXISTENTE);
			return model;
		}
		Pessoa pessoa = pessoaService.getPessoa(authentication.getName());
		if(!pessoa.equals(documento.getProjeto().getAutor()) || !documento.getProjeto().getStatus().equals(StatusProjeto.NOVO)) {
			model.addAttribute("result", "erro");
			model.addAttribute("mensagem", MENSAGEM_PERMISSAO_NEGADA);
			return model;
		}
		documentoService.remover(documento);
		model.addAttribute("result", "ok");
		return model;
	}
}
