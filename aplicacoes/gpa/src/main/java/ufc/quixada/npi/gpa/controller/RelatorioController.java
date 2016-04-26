package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_RELATORIOS;
import static ufc.quixada.npi.gpa.utils.Constants.TIPO_RELATORIO;
import static ufc.quixada.npi.gpa.utils.Constants.RELATORIO;
import static ufc.quixada.npi.gpa.utils.Constants.DATA_PESQUISA;
import static ufc.quixada.npi.gpa.utils.Constants.INICIO_INTERVALO_INICIO;
import static ufc.quixada.npi.gpa.utils.Constants.TERMINO_INTERVALO_INICIO;
import static ufc.quixada.npi.gpa.utils.Constants.INICIO_INTERVALO_TERMINO;
import static ufc.quixada.npi.gpa.utils.Constants.TERMINO_INTERVALO_TERMINO;
import static ufc.quixada.npi.gpa.utils.Constants.PARTICIPANTES;
import static ufc.quixada.npi.gpa.utils.Constants.PESSOAS;
import static ufc.quixada.npi.gpa.utils.Constants.APROVADOS;
import static ufc.quixada.npi.gpa.utils.Constants.REPROVADOS;
import static ufc.quixada.npi.gpa.utils.Constants.POR_PESSOA;
import static ufc.quixada.npi.gpa.utils.Constants.DATA_INICIO_INTERVALO;
import static ufc.quixada.npi.gpa.utils.Constants.DATA_TERMINO_INTERVALO;

import java.util.Date;

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
	public String aprovados(ModelMap model, @RequestParam(value = "iInterInicio", required = false) String iInterInicio,
			@RequestParam(value = "fInterInicio", required = false) String fInterInicio, @RequestParam(value = "iInterTermino", required = false) String iInterTermino, 
			 @RequestParam(value = "fInterTermino", required = false) String fInterTermino, RedirectAttributes redirectAttributes,
			HttpSession session) {
		Relatorio relatorio = relatorioService.getProjetosAprovadosRelatorio(iInterInicio, fInterInicio, iInterTermino, fInterTermino);
		model.addAttribute(TIPO_RELATORIO, APROVADOS);
		model.addAttribute(RELATORIO, relatorio);
		model.addAttribute(DATA_PESQUISA, new Date());
		model.addAttribute(INICIO_INTERVALO_INICIO, iInterInicio);
		model.addAttribute(TERMINO_INTERVALO_INICIO, fInterInicio);
		model.addAttribute(INICIO_INTERVALO_TERMINO, iInterTermino);
		model.addAttribute(TERMINO_INTERVALO_TERMINO, fInterTermino);
		return PAGINA_RELATORIOS;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String visualizarRelatorios(Model model, HttpSession session, Authentication authentication) {
		Pessoa pessoa = pessoaService.getPessoa(authentication.getName());
		model.addAttribute(PARTICIPANTES, pessoaService.getParticipantes(pessoa));
		model.addAttribute(PESSOAS, pessoaService.getAll());
		return PAGINA_RELATORIOS;
	}

	@RequestMapping(value = "/reprovados", method = RequestMethod.GET)
	public String reprovados(ModelMap model, @RequestParam(value = "submissaoInicio", required = false) String submissao_inicio,
			@RequestParam(value="submissaoTermino",required= false) String submissao_termino,
			RedirectAttributes redirectAttributes, HttpSession session) {
		Relatorio relatorio = relatorioService.getProjetosReprovadosRelatorio(submissao_inicio,submissao_termino);
		model.addAttribute(TIPO_RELATORIO, REPROVADOS);
		model.addAttribute(RELATORIO, relatorio);
		model.addAttribute(DATA_INICIO_INTERVALO, submissao_inicio);
		model.addAttribute(DATA_TERMINO_INTERVALO, submissao_termino);
		model.addAttribute(DATA_PESQUISA, new Date());
		return PAGINA_RELATORIOS;
	}

	@RequestMapping(value = "/por-pessoa", method = RequestMethod.GET)
	public String porPessoa(ModelMap model, @RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "ano", required = false) String ano, RedirectAttributes redirectAttributes, HttpSession session) {
		Relatorio relatorio = relatorioService.getProjetosPorPessoa(id, ano);
		model.addAttribute(TIPO_RELATORIO, POR_PESSOA);
		model.addAttribute(RELATORIO, relatorio );
		model.addAttribute(DATA_PESQUISA, new Date());
		return PAGINA_RELATORIOS;
	}
}
