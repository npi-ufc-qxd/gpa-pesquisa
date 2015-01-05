package ufc.quixada.npi.gpa.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ufc.quixada.npi.gpa.model.Comentario;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.service.ComentarioService;
import ufc.quixada.npi.gpa.service.ProjetoService;
import ufc.quixada.npi.gpa.service.UsuarioService;
import ufc.quixada.npi.gpa.utils.Constants;

@Controller
@RequestMapping("comentario")
public class ComentarioController {

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private ProjetoService projetoService;

	@Inject
	private ComentarioService comentarioService;

	@RequestMapping(value = "/comentar", method = RequestMethod.POST)
	@ResponseBody
	public Comentario adicionar(HttpServletRequest request, HttpSession session) {
		Comentario comentario = new Comentario();
		comentario.setTexto(request.getParameter("texto"));
		comentario.setData(new Date());
		Long id = Long.parseLong(request.getParameter("projetoId"));
		Projeto projeto = projetoService.getProjetoById(id);
		Pessoa autor = getUsuarioLogado(session);
		if(projeto == null || autor == null) {
			return null;
		}
		comentario.setAutor(autor);
		comentario.setProjeto(projeto);
		this.comentarioService.salvar(comentario);
		
		Pessoa autorResult = new Pessoa();
		autorResult.setNome(autor.getNome());
		Comentario comentarioResult = new Comentario();
		comentarioResult.setId(comentario.getId());
		comentarioResult.setData(comentario.getData());
		comentarioResult.setTexto(comentario.getTexto());
		comentarioResult.setAutor(autorResult);
		return comentarioResult;
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