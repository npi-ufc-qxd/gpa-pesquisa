package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.ACTION;
import static ufc.quixada.npi.gpa.utils.Constants.ERRO;
import static ufc.quixada.npi.gpa.utils.Constants.INFO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_ERRO_UPLOAD;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PARECERISTA_ATRIBUIDO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PERMISSAO_NEGADA;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_HOMOLOGADO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_INEXISTENTE;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_ATRIBUIR_PARECERISTA;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_ATRIBUIR_RELATOR;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_DIRECAO_BUSCAR_PESSOA;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_HOMOLOGAR_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_INICIAL_DIRECAO;
import static ufc.quixada.npi.gpa.utils.Constants.PARECER;
import static ufc.quixada.npi.gpa.utils.Constants.PARTICIPANTES;
import static ufc.quixada.npi.gpa.utils.Constants.PESSOAS;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETOS_EM_TRAMITACAO;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETOS_HOMOLOGADOS;
import static ufc.quixada.npi.gpa.utils.Constants.REDIRECT_PAGINA_INICIAL_DIRECAO;
import static ufc.quixada.npi.gpa.utils.Constants.USUARIOS;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.Documento.TipoDocumento;
import ufc.quixada.npi.gpa.model.ParecerRelator;
import ufc.quixada.npi.gpa.model.ParecerTecnico;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.Evento;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.service.ProjetoService;
import ufc.quixada.npi.gpa.service.impl.NotificacaoService;
import ufc.quixada.npi.gpa.service.validation.ParecerRelatorValidador;
import ufc.quixada.npi.gpa.service.validation.ParecerTecnicoValidation;
import ufc.quixada.npi.gpa.service.validation.ProjetoValidator;
import ufc.quixada.npi.gpa.utils.Constants;

@Controller
@RequestMapping("direcao")
public class DirecaoController {
	
	@Inject
	private ProjetoService projetoService;
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private NotificacaoService notificacaoService;
	
	@Inject
	private ParecerTecnicoValidation parecerValidator;
	
	@Inject
	private ProjetoValidator projetoValidator;
	
	@Inject
	private ParecerRelatorValidador parecerRelatorValidador;
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listar(Model model, HttpSession session) {
		model.addAttribute(PROJETOS_EM_TRAMITACAO, projetoService.getProjetosEmTramitacao());
		model.addAttribute(PROJETOS_HOMOLOGADOS, projetoService.getProjetosHomologados());
		model.addAttribute(PARTICIPANTES, pessoaService.getParticipantesProjetos());
		return PAGINA_INICIAL_DIRECAO;

	}
	
	@RequestMapping(value = "/atribuir-parecerista/{id-projeto}", method = RequestMethod.GET)
	public String atribuirPareceristaForm(@PathVariable("id-projeto") Long projetoId, Model model, RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjeto(projetoId);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		if (projeto.getStatus() != StatusProjeto.SUBMETIDO && projeto.getStatus() != StatusProjeto.AGUARDANDO_PARECER) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		
		else {
			if (projeto.getStatus() == StatusProjeto.SUBMETIDO ) {
				model.addAttribute(ACTION, Constants.ATRIBUIR);
				model.addAttribute(PARECER, new ParecerTecnico());
			}
		
			else {
				model.addAttribute(ACTION, Constants.ALTERAR);
				model.addAttribute(PARECER, projeto.getParecer());
			}
		}

		model.addAttribute(PROJETO, projeto);
		model.addAttribute(USUARIOS, pessoaService.getPareceristas(projeto));
		return PAGINA_ATRIBUIR_PARECERISTA;
	}
	
	@RequestMapping(value = "/atribuir-parecerista", method = RequestMethod.POST)
	public String atribuirParecerista(@Valid @ModelAttribute("parecer") ParecerTecnico parecer, @RequestParam("projetoId") Long projetoId, 
			@RequestParam("action") String action, @RequestParam("pareceristaId") Long pareceristaId, Model model, BindingResult result, RedirectAttributes redirectAttributes) {

		Projeto projeto = projetoService.getProjeto(projetoId);
		Pessoa parecerista = pessoaService.getPessoa(pareceristaId);
		
		parecer.setDataAtribuicao(new Date());
		parecer.setParecerista(parecerista);
		
		parecerValidator.validateAtribuirParecerista(parecer, result);
		if(result.hasErrors()){
			model.addAttribute(PROJETO, projeto);
			model.addAttribute(USUARIOS, pessoaService.getPareceristas(projeto));
			model.addAttribute(ACTION, action);
			return PAGINA_ATRIBUIR_PARECERISTA;
		}
		
		if (action.equals(Constants.ALTERAR)) {
			notificacaoService.notificar(projeto, Evento.ALTERACAO_PARECERISTA);
			projetoService.alterarParecerista(parecer);
			
			redirectAttributes.addFlashAttribute(INFO, Constants.MENSAGEM_PARECERISTA_ALTERADO);
		}
		else if (action.equals(Constants.ATRIBUIR)) {
			projetoService.atribuirParecerista(projeto, parecer);
			
			redirectAttributes.addFlashAttribute(INFO, MENSAGEM_PARECERISTA_ATRIBUIDO);
		}
		
		notificacaoService.notificar(projeto, Evento.ATRIBUICAO_PARECERISTA);
		return REDIRECT_PAGINA_INICIAL_DIRECAO;
	}
	
	@RequestMapping(value = "/atribuir-relator/{id-projeto}", method = RequestMethod.GET)
	public String atribuirRelatorForm(@PathVariable("id-projeto") Long projetoId, Model model, RedirectAttributes redirectAttributes){
		Projeto projeto = projetoService.getProjeto(projetoId);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		
		if (!projeto.getStatus().equals(StatusProjeto.AGUARDANDO_AVALIACAO)) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
			
		if(projeto.getParecerRelator() == null){
			model.addAttribute(ACTION, Constants.ATRIBUIR);
			model.addAttribute(PARECER, new ParecerRelator());
			}
		else{
			model.addAttribute(ACTION, Constants.ALTERAR);
			model.addAttribute(PARECER, projeto.getParecerRelator());
			}
		
		model.addAttribute(PROJETO, projeto);
		model.addAttribute(USUARIOS, pessoaService.getPareceristas(projeto));
		return PAGINA_ATRIBUIR_RELATOR;
		
	}
	
	@RequestMapping(value = "/atribuir-relator", method = RequestMethod.POST)
	public String atribuirRelator(@Valid @ModelAttribute("parecer") ParecerRelator parecerRelator, @RequestParam("projetoId") Long projetoId, 
			@RequestParam("action") String action, @RequestParam("relatorId") Long relatorId, Model model, BindingResult result, RedirectAttributes redirectAttributes) {

		Projeto projeto = projetoService.getProjeto(projetoId);
		Pessoa relator = pessoaService.getPessoa(relatorId);
		
		parecerRelator.setRelator(relator);
		
		parecerRelatorValidador.validate(parecerRelator, result);
		if(result.hasErrors()){
			model.addAttribute(PROJETO, projeto);
			model.addAttribute(USUARIOS, pessoaService.getPareceristas(projeto));
			model.addAttribute(ACTION, action);
			return PAGINA_ATRIBUIR_RELATOR;
		}
		
		if (action.equals(Constants.ALTERAR)) {
			notificacaoService.notificar(projeto, Evento.ALTERACAO_RELATOR);
			projetoService.alterarRelator(parecerRelator);
			
			redirectAttributes.addFlashAttribute(INFO, Constants.MENSAGEM_RELATOR_ALTERADO);
		}
		else if (action.equals(Constants.ATRIBUIR)) {
			projetoService.atribuirRelator(projeto, parecerRelator);
			
			redirectAttributes.addFlashAttribute(INFO, Constants.MENSAGEM_RELATOR_ATRIBUIDO);
		}
		
		notificacaoService.notificar(projeto, Evento.ATRIBUICAO_RELATOR);
		return REDIRECT_PAGINA_INICIAL_DIRECAO;
	}
	
	@RequestMapping(value = "/homologar/{id}", method = RequestMethod.GET)
	public String homologarForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		Projeto projeto = projetoService.getProjeto(id);
		if (projeto == null) {
			redirect.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		
		if (!projeto.getStatus().equals(StatusProjeto.AGUARDANDO_HOMOLOGACAO)) {
			redirect.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		model.addAttribute(PROJETO, projeto);
		return PAGINA_HOMOLOGAR_PROJETO;
	}
	
	@RequestMapping(value = "/homologar", method = RequestMethod.POST)
	public String homologar(@RequestParam("id") Long id, @RequestParam("homologacaoParam") StatusProjeto homologacao, 
			@RequestParam("ataParam") MultipartFile ataParam, @RequestParam("oficioParam") MultipartFile oficioParam, 
			@RequestParam("observacao") String observacao, Model model, @Valid Projeto projeto, BindingResult result, RedirectAttributes redirect) {
					
		projeto = projetoService.getProjeto(id);
		
		if (projeto == null) {
			redirect.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		
		ProjetoController pc = new ProjetoController();
		if(!pc.setInfoDocumentos(ataParam, projeto, TipoDocumento.ATA_HOMOLOGACAO)) {
			model.addAttribute(ERRO, MENSAGEM_ERRO_UPLOAD);
			return PAGINA_HOMOLOGAR_PROJETO;
		}
		if(!pc.setInfoDocumentos(oficioParam, projeto, TipoDocumento.OFICIO_HOMOLOGACAO)) {
			model.addAttribute(ERRO, MENSAGEM_ERRO_UPLOAD);

			return PAGINA_HOMOLOGAR_PROJETO;
		}
		
		projeto.setStatus(homologacao);
		projeto.setObservacaoHomologacao(observacao);
		
		projetoValidator.validateHomologacao(projeto, result);
		if(result.hasErrors()){			
			model.addAttribute(PROJETO, projeto);
			return PAGINA_HOMOLOGAR_PROJETO;
		}
		
		projetoService.homologar(projeto);
		
		redirect.addFlashAttribute(INFO, MENSAGEM_PROJETO_HOMOLOGADO);
		notificacaoService.notificar(projeto, Evento.HOMOLOGACAO);
		return REDIRECT_PAGINA_INICIAL_DIRECAO;
	}
	@RequestMapping(value = "/buscar", method = RequestMethod.GET)
	public String paginaInicial(Model model, Authentication authentication) {

		List<Pessoa> pessoas = pessoaService.getAll();
		model.addAttribute(PESSOAS, pessoas);
		
		return PAGINA_DIRECAO_BUSCAR_PESSOA;
	}
}
