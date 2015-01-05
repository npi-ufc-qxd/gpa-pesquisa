package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.service.DocumentoService;
import ufc.quixada.npi.gpa.service.ProjetoService;
import ufc.quixada.npi.gpa.service.UsuarioService;
import ufc.quixada.npi.gpa.utils.Constants;

@Controller
@RequestMapping("documento")
public class DocumentoController {
	
	@Inject
	private DocumentoService documentoService;
	
	@Inject
	private ProjetoService projetoService;
	
	@Inject
	private UsuarioService usuarioService;
	
	@RequestMapping(value = "/{idProjeto}/{idArquivo}", method = RequestMethod.GET)
	public void getArquivo(@PathVariable("idProjeto") Long idProjeto, @PathVariable("idArquivo") Long idArquivo, HttpServletResponse response, HttpSession session) {
		try {
			Projeto projeto = projetoService.getProjetoById(idProjeto);
			Documento documento = documentoService.getDocumentoById(idArquivo);
			if(documento != null && projeto != null && (getUsuarioLogado(session).equals(projeto.getAutor()) || 
					usuarioService.isDiretor(getUsuarioLogado(session)) || (projeto.getParecer() != null && getUsuarioLogado(session).equals(projeto.getParecer().getParecerista())))) {
				InputStream is = new ByteArrayInputStream(documento.getArquivo());
				response.setContentType(documento.getExtensao());
				response.setHeader("Content-Disposition", "attachment; filename=" + documento.getNome());
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (IOException ex) {
		}
	}
	
	@RequestMapping(value = "/ajax/remover/{id}", method = RequestMethod.POST)
	@ResponseBody public  ModelMap excluirDocumento(@PathVariable("id") Long id, HttpSession session) {
		ModelMap model = new ModelMap();
		Documento documento = documentoService.getDocumentoById(id);
		if(documento == null) {
			model.addAttribute("result", "erro");
			model.addAttribute("mensagem", MENSAGEM_DOCUMENTO_INEXISTENTE);
			return model;
		}
		if(!getUsuarioLogado(session).equals(documento.getProjeto().getAutor()) || !documento.getProjeto().getStatus().equals(StatusProjeto.NOVO)) {
			model.addAttribute("result", "erro");
			model.addAttribute("mensagem", MENSAGEM_PERMISSAO_NEGADA);
			return model;
		}
		documentoService.remover(documento);
		model.addAttribute("result", "ok");
		return model;
	}
	
	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa usuario = usuarioService
					.getUsuarioByLogin(SecurityContextHolder.getContext()
							.getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, usuario);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}

}
