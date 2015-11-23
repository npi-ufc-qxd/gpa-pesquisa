package ufc.quixada.npi.gpa.controller;

import java.security.Principal;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.utils.Constants;


@Controller
public class GPAController {
	@Inject
	private PessoaService pessoaService;
	
	@RequestMapping(value = "/sessaousuario", method = RequestMethod.GET)
	public String insereNomeSessao(HttpSession session, Authentication authentication){
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService.getPessoa(authentication.getName());
			session.setAttribute(Constants.USUARIO_LOGADO, pessoa.getNome());
		}
		return Constants.REDIRECT_PAGINA_LISTAR_PROJETO;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "erro", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("erro", "Usuário e/ou senha inválidos!");
		}
		return "login";
	}

	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
		model.addAttribute("erro", "Usuário e/ou senha inválidos!");
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String acessoNegado(ModelMap model, Principal user) {
		if (user != null) {
			model.addAttribute("message", "Olá, " + user.getName() 
			+ ", você não tem permissão para acessar essa página!");
		} else {
			model.addAttribute("message", 
			"Você não tem permissão para acessar essa página!");
		}
		return "403";
	}
	
	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String paginaInexistente(ModelMap model, Principal user) {
		model.addAttribute("message", "Oops, página não encontrada.");
		return "404";
	}
	
	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public String erroServidor(ModelMap model, Principal user) {
		model.addAttribute("message", "Ops, o site teve um erro técnico.");
		return "500";
	}
		
	
	@RequestMapping(value = "/500", method = RequestMethod.POST)
	public String erroServidorPost(ModelMap model, Principal user) {
		model.addAttribute("erro", "Ocorreu um erro, contate o administrador do sistema");
		return "login";
	}
}