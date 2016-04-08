package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_ERRO_UPLOAD;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PARECER_EMITIDO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PARTICIPACAO_REMOVIDA;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PERMISSAO_NEGADA;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_ATUALIZADO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_CADASTRADO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_INEXISTENTE;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_REMOVIDO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_SUBMETIDO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_CADASTRAR_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_DETALHES_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_EMITIR_PARECER;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_LISTAR_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_SUBMETER_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.REDIRECT_PAGINA_LISTAR_PROJETO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.Comentario;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Parecer.StatusPosicionamento;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Participacao.TipoParticipacao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.Evento;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.service.ComentarioService;
import ufc.quixada.npi.gpa.service.DocumentoService;
import ufc.quixada.npi.gpa.service.ParticipacaoService;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.service.ProjetoService;
import ufc.quixada.npi.gpa.service.impl.NotificacaoService;
import ufc.quixada.npi.gpa.service.validation.ParecerValidation;
import ufc.quixada.npi.gpa.service.validation.ParticipacaoValidator;
import ufc.quixada.npi.gpa.service.validation.ProjetoValidator;
import ufc.quixada.npi.gpa.utils.Constants;

@Controller
@RequestMapping("projeto")
public class ProjetoController {

	@Inject
	private ProjetoService projetoService;

	@Inject
	private PessoaService pessoaService;

	@Inject
	private NotificacaoService notificacaoService;

	@Inject
	private ProjetoValidator projetoValidator;
	
	@Inject
	private ParticipacaoValidator participacaoValidator;
	
	@Inject
	private ParecerValidation parecerValidator;

	@Autowired
	private ComentarioService comentarioService;

	@Autowired
	private DocumentoService documentoService;
	
	@Inject
	private ParticipacaoService participacaoService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listar(Model model, Authentication authentication) {
		Long idUsuarioLogado = pessoaService.getPessoa(authentication.getName()).getId();
		model.addAttribute("projetos", projetoService.getProjetos(idUsuarioLogado));
		model.addAttribute("projetosNaoAvaliados", projetoService.getProjetosNaoAvaliados(idUsuarioLogado));
		model.addAttribute("participacoesEmProjetos", projetoService.getParticipacoes(idUsuarioLogado));
		model.addAttribute("projetosAguardandoParecer", projetoService.getProjetosAguardandoParecer(idUsuarioLogado));
		model.addAttribute("projetosParecerEmitido", projetoService.getProjetosParecerEmitido(idUsuarioLogado));
		model.addAttribute("projetosAvaliados", projetoService.getProjetosAvaliados(idUsuarioLogado));

		return PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.GET)
	public String cadastrarForm(Model model, HttpSession session) {
		model.addAttribute("projeto", new Projeto());
		model.addAttribute("action", "cadastrar");
		return PAGINA_CADASTRAR_PROJETO;
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String cadastrar(@RequestParam("anexos") MultipartFile[] anexos, @Valid Projeto projeto,
			BindingResult result, HttpSession session, RedirectAttributes redirect, 
			Authentication authentication, Model model) {

		model.addAttribute("action", "cadastrar");

		projetoValidator.validate(projeto, result);

		if (result.hasErrors()) {
			return PAGINA_CADASTRAR_PROJETO;
		}

		projeto.setCoordenador(pessoaService.getPessoa(authentication.getName()));

		List<Documento> documentos = new ArrayList<Documento>();
		if (anexos != null && anexos.length != 0) {
			for (MultipartFile anexo : anexos) {
				try {
					if (anexo.getBytes() != null && anexo.getBytes().length != 0) {
						Documento documento = new Documento();
						documento.setArquivo(anexo.getBytes());
						documento.setNome(anexo.getOriginalFilename());
						documento.setExtensao(anexo.getContentType());
						documentos.add(documento);
					}
				} catch (IOException e) {
					model.addAttribute("erro", MENSAGEM_ERRO_UPLOAD);
					return PAGINA_CADASTRAR_PROJETO;
				}
			}
		}

		for (Documento documento : documentos) {
			projeto.addDocumento(documento);
		}
		projetoService.cadastrar(projeto);
		
		redirect.addFlashAttribute("info", MENSAGEM_PROJETO_CADASTRADO);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/detalhes/{id}")
	public String verDetalhes(@PathVariable("id") Long id, Model model, HttpSession session,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		Projeto projeto = projetoService.getProjeto(id);
		
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		
		model.addAttribute("projeto",projeto);
		Pessoa pessoa = pessoaService.getPessoa(authentication.getName());
		if(pessoa.isDirecao()){
			model.addAttribute("permissao","direcao");
			return PAGINA_DETALHES_PROJETO;
		}
		
		if(projeto.getCoordenador().equals(pessoa)){
			model.addAttribute("permissao","coordenador");
			return PAGINA_DETALHES_PROJETO;	
		}
		
		if(projeto.getParecer().getParecerista().equals(pessoa)
			&& projeto.getStatus().equals(StatusProjeto.AGUARDANDO_PARECER)){
			model.addAttribute("permissao","parecerista");
			return PAGINA_DETALHES_PROJETO;
		}
		
		if (projeto.getStatus().equals(StatusProjeto.APROVADO) 
					|| (projeto.getStatus().equals(StatusProjeto.APROVADO_COM_RESTRICAO))) {
				for (Participacao participacao : projeto.getParticipacoes()) {
					if (pessoa.equals(participacao.getParticipante())) {
						model.addAttribute("permissao", "participante");
						return PAGINA_DETALHES_PROJETO;
					}
				}
		}
		redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
	public String editarForm(@PathVariable("id") Long id, Model model, HttpSession session,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		Projeto projeto = projetoService.getProjeto(id);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (usuarioPodeEditarProjeto(projeto, usuario)) {
			model.addAttribute("projeto", projeto);
			model.addAttribute("action", "editar");
			return PAGINA_CADASTRAR_PROJETO;
		}

		redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/participacoes/{id}", method = RequestMethod.GET)
	public String listarParticipacoes(@PathVariable("id") Long id, Model model, 
			HttpSession session, RedirectAttributes redirectAttributes, Authentication authentication) {
		Projeto projeto = projetoService.getProjeto(id);
		model.addAttribute("tiposDeParticipacao",TipoParticipacao.values());
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (!usuarioPodeEditarProjeto(projeto, usuario)) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Calendar calendario = Calendar.getInstance();
		model.addAttribute("ano", calendario.get(Calendar.YEAR));
		model.addAttribute("projeto", projeto);
		model.addAttribute("participacao", new Participacao());
		model.addAttribute("pessoas", pessoaService.getAll());
		return PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
	}

	@RequestMapping(value = "/participacoes/{idProjeto}", method = RequestMethod.POST)
	public String adicionarParticipacao(@PathVariable("idProjeto") Long idProjeto,
			@RequestParam(value = "participanteSelecionado", required = true) Long idParticipanteSelecionado,
			Participacao participacao, HttpSession session, Model model, 
			BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {

		Projeto projeto = projetoService.getProjeto(idProjeto);
		model.addAttribute("tiposDeParticipacao",TipoParticipacao.values());
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (!usuarioPodeEditarProjeto(projeto, usuario)) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		
		participacao.setParticipante(pessoaService.getPessoa(idParticipanteSelecionado));
		participacao.setProjeto(projeto);
		
		participacaoValidator.validate(participacao, result);
		if(result.hasErrors()){			
			model.addAttribute("projeto", projeto);
			model.addAttribute("pessoas", pessoaService.getAll());
			model.addAttribute("validacao", result);
			return PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
		}
		try {
			participacaoService.verificaIntervalosParticipacaoPessoa(participacao);
		} catch (IllegalArgumentException e) {
			model.addAttribute("erro", e.getMessage());
			model.addAttribute("projeto", projeto);
			model.addAttribute("pessoas", pessoaService.getAll());
			return PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
		}
		projeto.adicionarParticipacao(participacao);
		projetoService.atualizar(projeto);	
		
		Calendar calendario = Calendar.getInstance();
		model.addAttribute("ano", calendario.get(Calendar.YEAR));
		model.addAttribute("usuario", usuario);
		model.addAttribute("projeto", projeto);
		model.addAttribute("participacao", new Participacao()); 
		model.addAttribute("pessoas", pessoaService.getAll());
		return PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
	}

	@RequestMapping(value = "/participacoes/{idProjeto}/excluir/{idParticipacao}")
	public String excluirParticipacao(@PathVariable("idProjeto") Long idProjeto,
			@PathVariable("idParticipacao") Long idParticipacao, HttpSession session, 
			Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
		Projeto projeto = projetoService.getProjeto(idProjeto);

		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (!usuarioPodeEditarProjeto(projeto, usuario)) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
		}
		Participacao participacao = projetoService.getParticipacao(idParticipacao);
		projetoService.removerParticipacao(projeto, participacao);

		redirectAttributes.addFlashAttribute("info", MENSAGEM_PARTICIPACAO_REMOVIDA);

		model.addAttribute("projeto", projeto);
		model.addAttribute("participacao", new Participacao());
		model.addAttribute("pessoas", pessoaService.getAll());
		return "redirect:/" + PAGINA_VINCULAR_PARTICIPANTES_PROJETO + "/" + idProjeto;
	}

	private boolean usuarioPodeEditarProjeto(Projeto projeto, Pessoa usuario) {
		return (usuario.getId() == projeto.getCoordenador().getId() && projeto.getStatus().equals(StatusProjeto.NOVO));
	}

	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public String editar(@RequestParam("anexos") List<MultipartFile> anexos, @Valid Projeto projeto,
			BindingResult result, Model model, HttpSession session, RedirectAttributes redirect,
			Authentication authentication) {
		model.addAttribute("action", "editar");

		if (result.hasErrors()) {
			if(result.hasGlobalErrors()){
				model.addAttribute("validacao", result);
			}
			return PAGINA_CADASTRAR_PROJETO;
		}
		
		Projeto oldProjeto = projetoService.getProjeto(projeto.getId());
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		oldProjeto.setCoordenador(usuario);
		oldProjeto = updateProjetoFields(oldProjeto, projeto);
		
		List<Documento> documentos = new ArrayList<Documento>();
		if (anexos != null && !anexos.isEmpty()) {
			for (MultipartFile anexo : anexos) {
				try {
					if (anexo.getBytes() != null && anexo.getBytes().length != 0) {
						Documento documento = new Documento();
						documento.setArquivo(anexo.getBytes());
						documento.setNome(anexo.getOriginalFilename());
						documento.setExtensao(anexo.getContentType());
						documentos.add(documento);
					}
				} catch (IOException e) {
					model.addAttribute("erro", MENSAGEM_ERRO_UPLOAD);
					return PAGINA_CADASTRAR_PROJETO;
				}
			}
		}
		
		projetoValidator.validate(oldProjeto, result);

		for (Documento documento : documentos) {
			oldProjeto.addDocumento(documento);
		}
		projetoService.cadastrar(oldProjeto);
		redirect.addFlashAttribute("info", MENSAGEM_PROJETO_ATUALIZADO);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, HttpSession session, RedirectAttributes redirectAttributes,
			Authentication authentication) {
		Projeto projeto = projetoService.getProjeto(id);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (usuarioPodeEditarProjeto(projeto, usuario)) {
			projetoService.remover(projeto);
			redirectAttributes.addFlashAttribute("info", MENSAGEM_PROJETO_REMOVIDO);
		} else {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
		}
		return REDIRECT_PAGINA_LISTAR_PROJETO;

	}

	@RequestMapping(value = "/submeter/{id}", method = RequestMethod.GET)
	public String submeterForm(HttpSession session, @PathVariable("id") Long id, RedirectAttributes redirectAttributes, 
			Model model, Authentication authentication) {
		Projeto projeto = projetoService.getProjeto(id);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}

		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (usuarioPodeEditarProjeto(projeto, usuario)) {
			
			// Adicionando o @ModelAttribute ao BindingResult
		    BindingResult result = new BeanPropertyBindingResult(projeto, "projeto");
			projetoValidator.validateSubmissao(projeto, result);

			if (result.hasErrors()) {
				model.addAttribute("projeto", projeto);
				model.addAttribute("alert", Constants.MENSAGEM_CAMPO_OBRIGATORIO_SUBMISSAO);
				
				if(result.hasGlobalErrors()){
					model.addAttribute("validacao", result);
				}
				return PAGINA_SUBMETER_PROJETO;
			} else {
				projetoService.submeter(projeto);

				redirectAttributes.addFlashAttribute("info", MENSAGEM_PROJETO_SUBMETIDO);
				notificacaoService.notificar(projeto, Evento.SUBMISSAO);
				return REDIRECT_PAGINA_LISTAR_PROJETO;
			}
		} else {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
	}

	@RequestMapping(value = "submeter", method = RequestMethod.POST)
	public String submeter(@RequestParam("anexos") List<MultipartFile> anexos, @Valid Projeto projeto,
			BindingResult result, Model model, HttpSession session, RedirectAttributes redirectAttributes,
			Authentication authentication) {
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		Projeto oldProjeto = projetoService.getProjeto(projeto.getId());
		oldProjeto.setCoordenador(usuario);
		oldProjeto = updateProjetoFields(oldProjeto, projeto);

		List<Documento> documentos = new ArrayList<Documento>();
		if (anexos != null && !anexos.isEmpty()) {
			for (MultipartFile anexo : anexos) {
				try {
					if (anexo.getBytes() != null && anexo.getBytes().length != 0) {
						Documento documento = new Documento();
						documento.setArquivo(anexo.getBytes());
						documento.setNome(anexo.getOriginalFilename());
						documento.setExtensao(anexo.getContentType());
						documentos.add(documento);
					}
				} catch (IOException e) {
					model.addAttribute("erro", MENSAGEM_ERRO_UPLOAD);
					return PAGINA_CADASTRAR_PROJETO;
				}
			}
		}
		if (!documentos.isEmpty()) {
			documentoService.salvar(documentos, oldProjeto.getCodigo());
		}

		// Recupera os anexos já cadastrados para realizar validação
		for (Documento documento : documentos) {
			oldProjeto.addDocumento(documento);
		}
		
		projetoValidator.validateSubmissao(oldProjeto, result);
		if (result.hasErrors()) {
			model.addAttribute("projeto", oldProjeto);
			model.addAttribute("participantes", pessoaService.getParticipantes(usuario));
			model.addAttribute("validacao", result);
			return PAGINA_SUBMETER_PROJETO;

		} else {
			projetoService.atualizar(oldProjeto);
			projetoService.submeter(oldProjeto);

			redirectAttributes.addFlashAttribute("info", MENSAGEM_PROJETO_SUBMETIDO);
			notificacaoService.notificar(projeto, Evento.SUBMISSAO);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
	}

	@RequestMapping(value = "/emitir-parecer/{id-projeto}", method = RequestMethod.GET)
	public String emitirParecerForm(@PathVariable("id-projeto") long idProjeto, Model model, HttpSession session,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		Projeto projeto = projetoService.getProjeto(idProjeto);

		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		if (!projeto.getStatus().equals(StatusProjeto.AGUARDANDO_PARECER)) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (!usuario.equals(projeto.getParecer().getParecerista())) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		model.addAttribute("projeto", projeto);
		model.addAttribute("posicionamento", StatusPosicionamento.values());
		model.addAttribute("parecer", new Parecer());
		return PAGINA_EMITIR_PARECER;
	}

	@RequestMapping(value = "/emitir-parecer", method = RequestMethod.POST)
	public String emitirParecer(@RequestParam("id-projeto") Long idProjeto, 
			@RequestParam("anexo") MultipartFile anexo,
			@RequestParam("posicionamento") StatusPosicionamento posicionamento, Model model,
			@Valid Parecer parecer, BindingResult result, RedirectAttributes redirectAttributes) {

		Projeto projeto = projetoService.getProjeto(idProjeto);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		
		projeto.getParecer().setDataRealizacao(new Date());
		projeto.getParecer().setStatus(posicionamento);
		projeto.getParecer().setParecer(parecer.getParecer());
		try {
			if (anexo.getBytes() != null && anexo.getBytes().length != 0) {
				Documento documento = new Documento();
				documento.setArquivo(anexo.getBytes());
				documento.setNome(anexo.getOriginalFilename());
				documento.setExtensao(anexo.getContentType());
				documentoService.salvar(documento, projeto.getCodigo());
				projeto.getParecer().setDocumento(documento);
				projeto.addDocumento(documento);
			}
		} catch (IOException e) {
			model.addAttribute("erro", MENSAGEM_ERRO_UPLOAD);
			return PAGINA_EMITIR_PARECER;
		}
				
		parecerValidator.validate(projeto.getParecer(), result);
		if(result.hasErrors()){
			model.addAttribute("projeto", projeto);
			model.addAttribute("posicionamento", StatusPosicionamento.values());
			return PAGINA_EMITIR_PARECER;
		}
		
		projetoService.emitirParecer(projeto);

		redirectAttributes.addFlashAttribute("info", MENSAGEM_PARECER_EMITIDO);
		notificacaoService.notificar(projeto, Evento.EMISSAO_PARECER);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/comentar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Model comentar(HttpServletRequest request, HttpSession session, Model model,
			Authentication authentication) {
		Comentario comentario = new Comentario();
		comentario.setTexto(request.getParameter("texto"));
		comentario.setData(new Date());
		Long id = Long.parseLong(request.getParameter("projetoId"));
		Projeto projeto = projetoService.getProjeto(id);
		Pessoa autor = pessoaService.getPessoa(authentication.getName());
		if (projeto == null || autor == null) {
			return null;
		}
		comentario.setAutor(autor);
		comentario.setProjeto(projeto);
		this.comentarioService.salvar(comentario);

		Comentario comentarioResult = new Comentario();
		comentarioResult.setId(comentario.getId());
		comentarioResult.setData(comentario.getData());
		comentarioResult.setTexto(comentario.getTexto());
		model.addAttribute("comentario", comentarioResult);
		model.addAttribute("autor", autor.getNome());
		return model;
	}
	
	private Projeto updateProjetoFields(Projeto oldProjeto, Projeto newProjeto){		
		oldProjeto.setDescricao(newProjeto.getDescricao());
		oldProjeto.setInicio(newProjeto.getInicio());
		oldProjeto.setLocal(newProjeto.getLocal());
		oldProjeto.setNome(newProjeto.getNome());
		oldProjeto.setAtividades(newProjeto.getAtividades());
		oldProjeto.setTermino(newProjeto.getTermino());
		return oldProjeto;
	}

}
