package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PERMISSAO_NEGADA;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_OK;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_ERRO;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.service.DocumentoService;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.service.ProjetoService;

@Controller
@RequestMapping("documento")
public class DocumentoController {
	
	@Inject
	private DocumentoService documentoService;
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private ProjetoService projetoService;
	
	@RequestMapping(value = "/{id-projeto}/{id-arquivo}", method = RequestMethod.GET)
	public HttpEntity<byte[]> getArquivo(@PathVariable("id-projeto") Long idProjeto, 
			@PathVariable("id-arquivo") Long idArquivo) {

		Documento documento = documentoService.getDocumento(idArquivo);
		byte[] arquivo = documentoService.getArquivo(documento);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "attachment; filename=" + documento.getNomeOriginal().replace(" ", "_"));
		headers.setContentLength(arquivo.length);

		return new HttpEntity<byte[]>(arquivo, headers);
	}
	
	@RequestMapping(value = "/{id-arquivo}", method = RequestMethod.GET)
	public HttpEntity<byte[]> getArquivoEdicao(@PathVariable("id-arquivo") Long idArquivo) {

		Documento documento = documentoService.getDocumento(idArquivo);
		byte[] arquivo = documentoService.getArquivo(documento);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "attachment; filename=" + documento.getNomeOriginal().replace(" ", "_"));
		headers.setContentLength(arquivo.length);

		return new HttpEntity<byte[]>(arquivo, headers);
	}

	
	@RequestMapping(value = "/excluir/{id}", method = RequestMethod.POST)
	@ResponseBody public  ModelMap excluir(@PathVariable("id") Long id, @RequestParam("projetoId") Long projetoId, HttpSession session, Authentication authentication, @ModelAttribute ModelMap model) {
		Documento documento = documentoService.getDocumento(id);

		Pessoa pessoa = pessoaService.getPessoa(authentication.getName());
		Projeto projeto = projetoService.getProjeto(projetoId);
		if(!projeto.getCoordenador().equals(pessoa) || !projeto.getStatus().equals(StatusProjeto.NOVO)){
			model.addAttribute("result", "erro");
			model.addAttribute("mensagem", MENSAGEM_PERMISSAO_NEGADA);
			return model;
		}
		projeto.getDocumentos().remove(documento);
		projetoService.atualizar(projeto);
		model.addAttribute("result", "ok");
		return model;
	}
	
	@RequestMapping(value = "/excluir-arquivo-projeto/{idProjeto}", method = RequestMethod.POST)
	@ResponseBody public  ModelMap excluirArquivoProjeto(@PathVariable("idProjeto") Long idProjeto, Authentication authentication,@ModelAttribute ModelMap model) {
		Projeto projeto = projetoService.getProjeto(idProjeto);
		
		Pessoa pessoa = pessoaService.getPessoa(authentication.getName());
		if(!pessoa.equals(projeto.getCoordenador()) || !projeto.getStatus().equals(StatusProjeto.NOVO)) {
			model.addAttribute("result", MENSAGEM_ERRO);
			model.addAttribute("mensagem", MENSAGEM_PERMISSAO_NEGADA);
			return model;
		}
		
		projeto.setArquivoProjeto(null);
		projetoService.atualizar(projeto);
		
		model.addAttribute("result", MENSAGEM_OK);
		return model;
	}
}
