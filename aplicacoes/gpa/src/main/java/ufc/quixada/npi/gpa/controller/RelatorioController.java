package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_RELATORIOS;
import static ufc.quixada.npi.gpa.utils.Constants.USUARIO_LOGADO;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.service.RelatorioService;

@Controller
@RequestMapping("direcao/relatorios")
public class RelatorioController {
	
	@Inject
	private RelatorioService relatorioService;
	
	@Inject
	private PessoaService pessoaService;
	
	@RequestMapping(value = "/aprovados", method = RequestMethod.GET)
	public String Aprovados(ModelMap model, @RequestParam(value = "inicio", required = false) String inicio, 
			@RequestParam(value = "termino", required = false) String termino, HttpSession session) {
		model.addAttribute("projetosAprovados", relatorioService.getProjetosAprovadosRelatorio(inicio, termino).getProjetosAprovados());
		model.addAttribute("relatorio", relatorioService.getProjetosAprovadosRelatorio(inicio, termino));
		return PAGINA_RELATORIOS;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String visualizarRelatorios(Model model, HttpSession session) {
		model.addAttribute("participantes", pessoaService.getParticipantes(getUsuarioLogado(session)));
		return PAGINA_RELATORIOS;
	}
	
	@RequestMapping(value = "/reprovados", method = RequestMethod.GET)
	public String Reprovados(ModelMap model, @RequestParam(value = "submissao", required = false) String submissao, HttpSession session){
		model.addAttribute("projetosReprovados", relatorioService.getProjetosReprovadosRelatorio(submissao).getProjetosReprovados());
		model.addAttribute("relatorio", relatorioService.getProjetosReprovadosRelatorio(submissao));
		return PAGINA_RELATORIOS;
	}
	
	@RequestMapping(value = "/p-pessoa", method = RequestMethod.GET)
	public String PorPessoa(ModelMap model, @RequestParam(value = "id", required = true) Long id,@RequestParam(value = "ano", required = false) String ano, HttpSession session){
		model.addAttribute("projetosPorPessoa", relatorioService.getProjetosPorPessoa(id, ano).getProjetosPorPessoa());
		model.addAttribute("relatorio", relatorioService.getProjetosPorPessoa(id, ano));
		return PAGINA_RELATORIOS;
	}
	
	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(USUARIO_LOGADO) == null) {
			Pessoa usuario = pessoaService
					.getPessoa(SecurityContextHolder.getContext()
							.getAuthentication().getName());
			session.setAttribute(USUARIO_LOGADO, usuario);
		}
		return (Pessoa) session.getAttribute(USUARIO_LOGADO);
	}

}
