package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_ERRO_UPLOAD;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PARECERISTA_ATRIBUIDO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PERMISSAO_NEGADA;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_AVALIADO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_INEXISTENTE;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_ATRIBUIR_PARECERISTA;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_AVALIAR_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_INICIAL_DIRECAO;
import static ufc.quixada.npi.gpa.utils.Constants.REDIRECT_PAGINA_INICIAL_DIRECAO;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.Evento;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.service.ProjetoService;
import ufc.quixada.npi.gpa.service.impl.NotificacaoService;

@Controller
@RequestMapping("direcao")
public class DirecaoController {
	
	@Inject
	private ProjetoService projetoService;
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private NotificacaoService notificacaoService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listar(Model model, HttpSession session) {
		model.addAttribute("projetosSubmetidos", projetoService.getProjetosSubmetidos());
		model.addAttribute("projetosAvaliados", projetoService.getProjetosAvaliados());
		model.addAttribute("participantes", pessoaService.getParticipantesProjetos());
		return PAGINA_INICIAL_DIRECAO;

	}
	
	@RequestMapping(value = "/atribuir-parecerista/{id-projeto}", method = RequestMethod.GET)
	public String atribuirPareceristaForm(@PathVariable("id-projeto") Long projetoId, Model model, RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjetoById(projetoId);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		if (projeto.getStatus() != StatusProjeto.SUBMETIDO) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}

		model.addAttribute("projeto", projeto);
		model.addAttribute("usuarios", pessoaService.getPareceristas(projeto.getAutor().getId()));
		return PAGINA_ATRIBUIR_PARECERISTA;
	}
	
	@RequestMapping(value = "/atribuir-parecerista", method = RequestMethod.POST)
	public String atribuirParecerista(@RequestParam("prazo") @DateTimeFormat(pattern = "dd/MM/yyyy") Date prazo, @RequestParam("observacao") String observacao, @RequestParam("projetoId") Long projetoId, 
			@RequestParam("pareceristaId") Long pareceristaId, Model model, RedirectAttributes redirectAttributes) {

		Projeto projeto = projetoService.getProjetoById(projetoId);
		Pessoa parecerista = pessoaService.getPessoaById(pareceristaId);
		
		Parecer parecer = new Parecer();
		parecer.setDataAtribuicao(new Date());
		parecer.setObservacao(observacao);
		parecer.setParecerista(parecerista);
		parecer.setPrazo(prazo);
		
		Map<String, String> resultado = projetoService.atribuirParecerista(projeto, parecer);
		if(!resultado.isEmpty()) {
			// TODO: criar validador de atribuição de parecerista
			//buildValidacoesModel(resultado, model);
			model.addAttribute("projeto", projeto);
			model.addAttribute("usuarios", pessoaService.getPareceristas(projeto.getAutor().getId()));
			return PAGINA_ATRIBUIR_PARECERISTA;
		}
		
		redirectAttributes.addFlashAttribute("info", MENSAGEM_PARECERISTA_ATRIBUIDO);
		notificacaoService.notificar(projeto, Evento.ATRIBUICAO_PARECERISTA);
		return REDIRECT_PAGINA_INICIAL_DIRECAO;

	}
	
	@RequestMapping(value = "/avaliar/{id}", method = RequestMethod.GET)
	public String avaliarForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		Projeto projeto = projetoService.getProjetoById(id);
		if (projeto == null || !projeto.getStatus().equals(StatusProjeto.AGUARDANDO_AVALIACAO)) {
			redirect.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		model.addAttribute("projeto", projeto);
		return PAGINA_AVALIAR_PROJETO;
	}
	
	@RequestMapping(value = "/avaliar", method = RequestMethod.POST)
	public String avaliar(@RequestParam("id") Long id, @RequestParam("avaliacao") StatusProjeto avaliacao, 
			@RequestParam("ata") MultipartFile ata, @RequestParam("oficio") MultipartFile oficio, 
			@RequestParam("observacao")String observacao, Model model, RedirectAttributes redirect) {
		Documento ataDocumento = null;
		Documento oficioDocumento = null;
		try {
			if (ata != null && ata.getBytes() != null && ata.getBytes().length != 0) {
				ataDocumento = new Documento();
				ataDocumento.setArquivo(ata.getBytes());
				ataDocumento.setNome(ata.getOriginalFilename());
				ataDocumento.setExtensao(ata.getContentType());
			}
			if (oficio != null && oficio.getBytes() != null && oficio.getBytes().length != 0) {
				oficioDocumento = new Documento();
				oficioDocumento.setArquivo(oficio.getBytes());
				oficioDocumento.setNome(oficio.getOriginalFilename());
				oficioDocumento.setExtensao(oficio.getContentType());
			}
		} catch (IOException e) {
			model.addAttribute("erro", MENSAGEM_ERRO_UPLOAD);
			return PAGINA_AVALIAR_PROJETO;
		}
		
		Projeto projeto = projetoService.getProjetoById(id);
		projeto.setStatus(avaliacao);
		projeto.setAta(ataDocumento);
		projeto.setOficio(oficioDocumento);
		projeto.setObservacaoAvaliacao(observacao);
		Map<String, String> resultado = projetoService.avaliar(projeto);
		if(resultado.isEmpty()) {
			redirect.addFlashAttribute("info", MENSAGEM_PROJETO_AVALIADO);
			notificacaoService.notificar(projeto, Evento.AVALIACAO);
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
		}
		model.addAttribute("projeto", projeto);
		
		// TODO: criar validação de avaliação de projeto
		//buildValidacoesModel(resultado, model);
		return PAGINA_AVALIAR_PROJETO;
	}

}
