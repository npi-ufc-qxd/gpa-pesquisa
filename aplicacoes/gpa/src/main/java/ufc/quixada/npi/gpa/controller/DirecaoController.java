package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_ERRO_UPLOAD;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PARECERISTA_ATRIBUIDO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PERMISSAO_NEGADA;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_AVALIADO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_INEXISTENTE;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_USUARIO_NAO_ENCONTRADO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_ATRIBUIR_PARECERISTA;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_AVALIAR_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_DIRECAO_BUSCAR_PESSOA;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_INICIAL_DIRECAO;
import static ufc.quixada.npi.gpa.utils.Constants.REDIRECT_PAGINA_BUSCAR_PARTICIPANTE;
import static ufc.quixada.npi.gpa.utils.Constants.REDIRECT_PAGINA_INICIAL_DIRECAO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_ATRIBUIR_RELATOR;

import java.io.IOException;
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

import ufc.quixada.npi.gpa.model.Documento;
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
		model.addAttribute("projetosSubmetidos", projetoService.getProjetosSubmetidos());
		model.addAttribute("projetosAvaliados", projetoService.getProjetosAvaliados());
		model.addAttribute("participantes", pessoaService.getParticipantesProjetos());
		return PAGINA_INICIAL_DIRECAO;

	}
	
	@RequestMapping(value = "/atribuir-parecerista/{id-projeto}", method = RequestMethod.GET)
	public String atribuirPareceristaForm(@PathVariable("id-projeto") Long projetoId, Model model, RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjeto(projetoId);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		if (projeto.getStatus() != StatusProjeto.SUBMETIDO && projeto.getStatus() != StatusProjeto.AGUARDANDO_PARECER) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		
		else {
			if (projeto.getStatus() == StatusProjeto.SUBMETIDO ) {
				model.addAttribute("action", Constants.ATRIBUIR_PARECERISTA);
				model.addAttribute("parecer", new ParecerTecnico());
			}
		
			else {
				model.addAttribute("action", Constants.ALTERAR_PARECERISTA);
				model.addAttribute("parecer", projeto.getParecer());
			}
		}

		model.addAttribute("projeto", projeto);
		model.addAttribute("usuarios", pessoaService.getPareceristas(projeto));
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
			model.addAttribute("projeto", projeto);
			model.addAttribute("usuarios", pessoaService.getPareceristas(projeto));
			model.addAttribute("action", action);
			return PAGINA_ATRIBUIR_PARECERISTA;
		}
		
		if (action.equals(Constants.ALTERAR_PARECERISTA)) {
			notificacaoService.notificar(projeto, Evento.ALTERACAO_PARECERISTA);
			projetoService.alterarParecerista(parecer);
			
			redirectAttributes.addFlashAttribute("info", Constants.MENSAGEM_PARECERISTA_ALTERADO);
		}
		else if (action.equals(Constants.ATRIBUIR_PARECERISTA)) {
			projetoService.atribuirParecerista(projeto, parecer);
			
			redirectAttributes.addFlashAttribute("info", MENSAGEM_PARECERISTA_ATRIBUIDO);
		}
		
		notificacaoService.notificar(projeto, Evento.ATRIBUICAO_PARECERISTA);
		return REDIRECT_PAGINA_INICIAL_DIRECAO;
	}
	
	@RequestMapping(value = "/atribuir-relator/{id-projeto}", method = RequestMethod.GET)
	public String atribuirRelatorForm(@PathVariable("id-projeto") Long projetoId, Model model, RedirectAttributes redirectAttributes){
		Projeto projeto = projetoService.getProjeto(projetoId);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		
		if (!projeto.getStatus().equals(StatusProjeto.AGUARDANDO_AVALIACAO)) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		
		if (projeto.getStatus() == StatusProjeto.AGUARDANDO_AVALIACAO ) {
				
			if(projeto.getParecerRelator() == null){
			model.addAttribute("action", Constants.ATRIBUIR_RELATOR);
			model.addAttribute("parecer", new ParecerRelator());
			}
			else{
				model.addAttribute("action", Constants.ALTERAR_RELATOR);
				model.addAttribute("parecer", projeto.getParecerRelator());
			}
		}
		
		model.addAttribute("projeto", projeto);
		model.addAttribute("usuarios", pessoaService.getPareceristas(projeto));
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
			model.addAttribute("projeto", projeto);
			model.addAttribute("usuarios", pessoaService.getPareceristas(projeto));
			model.addAttribute("action", action);
			return PAGINA_ATRIBUIR_RELATOR;
		}
		
		if (action.equals(Constants.ALTERAR_RELATOR)) {
			notificacaoService.notificar(projeto, Evento.ALTERACAO_RELATOR);
			projetoService.alterarRelator(parecerRelator);
			
			redirectAttributes.addFlashAttribute("info", Constants.MENSAGEM_RELATOR_ALTERADO);
		}
		else if (action.equals(Constants.ATRIBUIR_RELATOR)) {
			projetoService.atribuirRelator(projeto, parecerRelator);
			
			redirectAttributes.addFlashAttribute("info", Constants.MENSAGEM_RELATOR_ATRIBUIDO);
		}
		
		notificacaoService.notificar(projeto, Evento.ATRIBUICAO_RELATOR);
		return REDIRECT_PAGINA_INICIAL_DIRECAO;
	}
	
	@RequestMapping(value = "/avaliar/{id}", method = RequestMethod.GET)
	public String avaliarForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		Projeto projeto = projetoService.getProjeto(id);
		if (projeto == null || !projeto.getStatus().equals(StatusProjeto.AGUARDANDO_AVALIACAO)) {
			redirect.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		model.addAttribute("projeto", projeto);
		return PAGINA_AVALIAR_PROJETO;
	}
	
	@RequestMapping(value = "/avaliar", method = RequestMethod.POST)
	public String avaliar(@RequestParam("id") Long id, @RequestParam("avaliacaoParam") StatusProjeto avaliacao, 
			@RequestParam("ataParam") MultipartFile ataParam, @RequestParam("oficioParam") MultipartFile oficioParam, 
			@RequestParam("observacao") String observacao, Model model, @Valid Projeto projeto, BindingResult result, RedirectAttributes redirect) {
				
		Documento ataDocumento = null;
		Documento oficioDocumento = null;
		
		try {
			if (ataParam != null && ataParam.getBytes() != null && ataParam.getBytes().length != 0) {
				ataDocumento = new Documento();
				ataDocumento.setArquivo(ataParam.getBytes());
				ataDocumento.setNome(ataParam.getOriginalFilename());
				ataDocumento.setExtensao(ataParam.getContentType());
			}
			if (oficioParam != null && oficioParam.getBytes() != null && oficioParam.getBytes().length != 0) {
				oficioDocumento = new Documento();
				oficioDocumento.setArquivo(oficioParam.getBytes());
				oficioDocumento.setNome(oficioParam.getOriginalFilename());
				oficioDocumento.setExtensao(oficioParam.getContentType());
			}
		} catch (IOException e) {
			model.addAttribute("erro", MENSAGEM_ERRO_UPLOAD);
			return PAGINA_AVALIAR_PROJETO;
		}
		
		projeto = projetoService.getProjeto(id);
		projeto.setStatus(avaliacao);
		projeto.setAta(ataDocumento);
		projeto.setOficio(oficioDocumento);
		projeto.setObservacaoAvaliacao(observacao);
		
		projetoValidator.validateAvaliacao(projeto, result);
		if(result.hasErrors()){			
			model.addAttribute("projeto", projeto);
			return PAGINA_AVALIAR_PROJETO;
		}
		
		projetoService.avaliar(projeto);
		
		redirect.addFlashAttribute("info", MENSAGEM_PROJETO_AVALIADO);
		notificacaoService.notificar(projeto, Evento.AVALIACAO);
		return REDIRECT_PAGINA_INICIAL_DIRECAO;
	}
	@RequestMapping(value = "/buscar", method = RequestMethod.GET)
	public String paginaInicial(Model model, Authentication authentication) {
		return PAGINA_DIRECAO_BUSCAR_PESSOA;
	}
	
	@RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public String buscarPessoa(@RequestParam("busca") String busca, Model model,
			RedirectAttributes redirectAttributes) {
		
		model.addAttribute("busca", busca);
		List<Pessoa> pessoas = pessoaService.getUsuariosByNomeOuCpf(busca);
		if (!pessoas.isEmpty()) {
			model.addAttribute("pessoas", pessoas);
		} else {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_USUARIO_NAO_ENCONTRADO);
			return REDIRECT_PAGINA_BUSCAR_PARTICIPANTE;
		}
		return PAGINA_DIRECAO_BUSCAR_PESSOA;
	}
}
