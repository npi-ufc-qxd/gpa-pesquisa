package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_RELATORIOS;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Relatorio;
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
	public String aprovados(ModelMap model, @RequestParam(value = "inicio", required = false) String inicio,
			@RequestParam(value = "termino", required = false) String termino, RedirectAttributes redirectAttributes,
			HttpSession session) {
		Relatorio relatorio = relatorioService.getProjetosAprovadosRelatorio(inicio, termino);
		model.addAttribute("tipoRelatorio", "aprovados");
		model.addAttribute("relatorio", relatorio);
		model.addAttribute("data_de_inicio", inicio);
		model.addAttribute("data_de_termino", termino);
		model.addAttribute("data_pesquisa", relatorioService.getMomento());
		return PAGINA_RELATORIOS;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String visualizarRelatorios(Model model, HttpSession session, Authentication authentication) {
		Pessoa pessoa = pessoaService.getPessoa(authentication.getName());
		model.addAttribute("participantes", pessoaService.getParticipantes(pessoa));
		model.addAttribute("pessoas", pessoaService.getAll());
		return PAGINA_RELATORIOS;
	}

	@RequestMapping(value = "/reprovados", method = RequestMethod.GET)
	public String reprovados(ModelMap model, @RequestParam(value = "submissao-inicio", required = false) String submissao_inicio,
			@RequestParam(value="submissao-termino",required= false) String submissao_termino,
			RedirectAttributes redirectAttributes, HttpSession session) {
		Relatorio relatorio = relatorioService.getProjetosReprovadosRelatorio(submissao_inicio,submissao_termino);
		model.addAttribute("tipoRelatorio", "reprovados");
		model.addAttribute("relatorio", relatorio);
		model.addAttribute("data_de_submissao", submissao_inicio);
		model.addAttribute("data_pesquisa", relatorioService.getMomento());
		return PAGINA_RELATORIOS;
	}

	@RequestMapping(value = "/por-pessoa", method = RequestMethod.GET)
	public String porPessoa(ModelMap model, @RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "ano", required = false) String ano, RedirectAttributes redirectAttributes, HttpSession session) {
		Relatorio relatorio = relatorioService.getProjetosPorPessoa(id, ano);
		model.addAttribute("tipoRelatorio", "por-pessoa");
		model.addAttribute("relatorio", relatorio );
		model.addAttribute("data_pesquisa", relatorioService.getMomento());
		return PAGINA_RELATORIOS;
	}
}