package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.ERRO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_USUARIO_SENHA_INVALIDOS;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_LOGIN;
import static ufc.quixada.npi.gpa.utils.Constants.REDIRECT_PAGINA_LISTAR_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.USUARIO_LOGADO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_403;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_404;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_500;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_OCORREU_UM_ERRO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_ERRO_TECNICO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PAGINA_NAO_ENCONTRADA;

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


@Controller
public class GPAController {
	@Inject
	private PessoaService pessoaService;
	
	@RequestMapping(value = "/sessaousuario", method = RequestMethod.GET)
	public String insereNomeSessao(HttpSession session, Authentication authentication){
		if (session.getAttribute(USUARIO_LOGADO) == null) {
			Pessoa pessoa = pessoaService.getPessoa(authentication.getName());
			session.setAttribute(USUARIO_LOGADO, pessoa.getNome());
		}
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "erro", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute(ERRO, MENSAGEM_USUARIO_SENHA_INVALIDOS);
		}
		return PAGINA_LOGIN;
	}

	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
		model.addAttribute(ERRO, MENSAGEM_USUARIO_SENHA_INVALIDOS);
		return PAGINA_LOGIN;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return PAGINA_LOGIN;
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String acessoNegado(ModelMap model, Principal user) {
		if (user != null) {
			Pessoa pessoa = pessoaService.getPessoa(user.getName());
			model.addAttribute(MENSAGEM, "Olá, " + pessoa.getNome() 
			+ ", você não tem permissão para acessar essa página!");
		} else {
			model.addAttribute(MENSAGEM, 
			"Você não tem permissão para acessar essa página!");
		}
		return PAGINA_403;
	}
	
	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String paginaInexistente(ModelMap model, Principal user) {
		model.addAttribute(MENSAGEM, MENSAGEM_PAGINA_NAO_ENCONTRADA);
		return PAGINA_404;
	}
	
	@RequestMapping(value = "/500", method = RequestMethod.GET)
	public String erroServidor(ModelMap model, Principal user) {
		model.addAttribute(MENSAGEM, MENSAGEM_ERRO_TECNICO);
		return PAGINA_500;
	}
		
	
	@RequestMapping(value = "/500", method = RequestMethod.POST)
	public String erroServidorPost(ModelMap model, Principal user) {
		model.addAttribute(ERRO, MENSAGEM_OCORREU_UM_ERRO);
		return PAGINA_LOGIN;
	}
}