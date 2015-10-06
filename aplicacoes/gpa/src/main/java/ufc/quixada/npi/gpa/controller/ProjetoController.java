package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_CAMPO_OBRIGATORIO_SUBMISSAO;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.Comentario;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Parecer.StatusPosicionamento;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.Evento;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.service.ComentarioService;
import ufc.quixada.npi.gpa.service.DocumentoService;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.service.ProjetoService;
import ufc.quixada.npi.gpa.service.impl.NotificacaoService;
import ufc.quixada.npi.gpa.service.impl.ParticipacaoValidator;
import ufc.quixada.npi.gpa.service.impl.ProjetoValidator;
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

	@Autowired
	private ComentarioService comentarioService;

	@Autowired
	private DocumentoService documentoService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listar(Model model, HttpSession session) {
		Long idUsuarioLogado = getUsuarioLogado(session).getId();
		model.addAttribute("projetos", projetoService.getProjetos(idUsuarioLogado));
		model.addAttribute("participacoesEmProjetos", projetoService.getParticipacoes(idUsuarioLogado));
		model.addAttribute("projetosAguardandoParecer", projetoService.getProjetosAguardandoParecer(idUsuarioLogado));
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
			BindingResult result, HttpSession session, RedirectAttributes redirect, Model model) {

		model.addAttribute("action", "cadastrar");

		projetoValidator.validate(projeto, result);

		if (result.hasErrors()) {
			return PAGINA_CADASTRAR_PROJETO;
		}

		projeto.setAutor(getUsuarioLogado(session));

		List<Documento> documentos = new ArrayList<Documento>();
		if (anexos != null && anexos.length != 0) {
			for (MultipartFile anexo : anexos) {
				try {
					if (anexo.getBytes() != null && anexo.getBytes().length != 0) {
						Documento documento = new Documento();
						documento.setArquivo(anexo.getBytes());
						documento.setNome(anexo.getOriginalFilename());
						documento.setExtensao(anexo.getContentType());
						documento.setProjeto(projeto);
						documentos.add(documento);
					}
				} catch (IOException e) {
					model.addAttribute("erro", MENSAGEM_ERRO_UPLOAD);
					return PAGINA_CADASTRAR_PROJETO;
				}
			}
		}

		projetoService.cadastrar(projeto);

		if (!documentos.isEmpty()) {
			documentoService.salvar(documentos);
		}

		redirect.addFlashAttribute("info", MENSAGEM_PROJETO_CADASTRADO);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/detalhes/{id}")
	public String verDetalhes(@PathVariable("id") Long id, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjeto(id);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa pessoa = getUsuarioLogado(session);
		if (pessoa.getId() == projeto.getAutor().getId() || pessoa.isDirecao()
				|| (projeto.getParecer() != null && projeto.getParecer().getParecerista().getId() == pessoa.getId())) {
			List<Comentario> comentarios = projeto.getComentarios();
			Collections.sort(comentarios, new Comparator<Comentario>() {
				@Override
				public int compare(Comentario comentario1, Comentario comentario2) {
					return comentario1.getData().compareTo(comentario2.getData());
				}
			});
			projeto.setComentarios(comentarios);
			model.addAttribute("projeto", projeto);
			return PAGINA_DETALHES_PROJETO;
		} else {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
	}

	@RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
	public String editarForm(@PathVariable("id") Long id, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjeto(id);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = getUsuarioLogado(session);
		if (usuarioPodeEditarProjeto(projeto, usuario)) {
			model.addAttribute("projeto", projeto);
			model.addAttribute("action", "editar");
			return PAGINA_CADASTRAR_PROJETO;
		}

		redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/participacoes/{id}", method = RequestMethod.GET)
	public String listarParticipacoes(@PathVariable("id") Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjeto(id);
		Pessoa usuario = getUsuarioLogado(session);

		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		if (!usuarioPodeEditarProjeto(projeto, usuario)) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}

		model.addAttribute("projeto", projeto);
		model.addAttribute("participacao", new Participacao());
		model.addAttribute("pessoas", pessoaService.getAll());
		return PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
	}

	@RequestMapping(value = "/participacoes/{id}", method = RequestMethod.POST)
	public String adicionarParticipacao(@PathVariable("id") Long id,
			@RequestParam(value = "participanteSelecionado", required = true) String idParticipanteSelecionado,
			Participacao participacao, HttpSession session, Model model, BindingResult result, RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjeto(id);
		Pessoa usuario = getUsuarioLogado(session);

		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		if (!usuarioPodeEditarProjeto(projeto, usuario)) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		
		participacaoValidator.validate(participacao, result);
		if(result.hasErrors()){			
			model.addAttribute("projeto", projeto);
			model.addAttribute("pessoas", pessoaService.getAll());
			return PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
		}

		participacao.setParticipante(pessoaService.getPessoa(new Long(idParticipanteSelecionado)));

		projeto.adicionarParticipacao(participacao);
		projetoService.atualizar(projeto);

		model.addAttribute("projeto", projeto);
		model.addAttribute("participacao", new Participacao());
		model.addAttribute("pessoas", pessoaService.getAll());
		return PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
	}

	@RequestMapping(value = "/participacoes/{idProjeto}/excluir/{idParticipacao}")
	public String excluirParticipacao(@PathVariable("idProjeto") Long idProjeto,
			@PathVariable("idParticipacao") Long idParticipacao, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjeto(idProjeto);

		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = getUsuarioLogado(session);
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
		return (usuario.getId() == projeto.getAutor().getId() && projeto.getStatus().equals(StatusProjeto.NOVO));
	}

	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public String editar(@RequestParam("anexos") List<MultipartFile> anexos, @Valid Projeto projeto,
			BindingResult result, Model model, HttpSession session, RedirectAttributes redirect) {

		model.addAttribute("action", "editar");

		projetoValidator.validate(projeto, result);

		if (result.hasErrors()) {
			return PAGINA_CADASTRAR_PROJETO;
		}

		projeto.setAutor(getUsuarioLogado(session));

		List<Documento> documentos = new ArrayList<Documento>();
		if (anexos != null && !anexos.isEmpty()) {
			for (MultipartFile anexo : anexos) {
				try {
					if (anexo.getBytes() != null && anexo.getBytes().length != 0) {
						Documento documento = new Documento();
						documento.setArquivo(anexo.getBytes());
						documento.setNome(anexo.getOriginalFilename());
						documento.setExtensao(anexo.getContentType());
						documento.setProjeto(projeto);
						documentos.add(documento);
					}
				} catch (IOException e) {
					model.addAttribute("erro", MENSAGEM_ERRO_UPLOAD);
					return PAGINA_CADASTRAR_PROJETO;
				}
			}
		}

		if (!documentos.isEmpty()) {
			documentoService.salvar(documentos);
		}

		projetoService.atualizar(projeto);

		redirect.addFlashAttribute("info", MENSAGEM_PROJETO_ATUALIZADO);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, HttpSession session, RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjeto(id);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = getUsuarioLogado(session);
		if (usuarioPodeEditarProjeto(projeto, usuario)) {
			projetoService.remover(projeto);
			redirectAttributes.addFlashAttribute("info", MENSAGEM_PROJETO_REMOVIDO);
		} else {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
		}
		return REDIRECT_PAGINA_LISTAR_PROJETO;

	}

	@RequestMapping(value = "/submeter/{id}", method = RequestMethod.GET)
	public String submeterForm(HttpSession session, @PathVariable("id") Long id, @ModelAttribute Projeto projeto,
			BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		projeto = projetoService.getProjeto(id);

		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}

		Pessoa usuario = getUsuarioLogado(session);
		if (usuarioPodeEditarProjeto(projeto, usuario)) {
			projetoValidator.validateSubmissao(projeto, result);

			if (result.hasErrors()) {
				model.addAttribute("projeto", projeto);
				model.addAttribute("alert", MENSAGEM_CAMPO_OBRIGATORIO_SUBMISSAO);
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
			BindingResult result, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

		projeto.setAutor(getUsuarioLogado(session));

		List<Documento> documentos = new ArrayList<Documento>();
		if (anexos != null && !anexos.isEmpty()) {
			for (MultipartFile anexo : anexos) {
				try {
					if (anexo.getBytes() != null && anexo.getBytes().length != 0) {
						Documento documento = new Documento();
						documento.setArquivo(anexo.getBytes());
						documento.setNome(anexo.getOriginalFilename());
						documento.setExtensao(anexo.getContentType());
						documento.setProjeto(projeto);
						documentos.add(documento);
					}
				} catch (IOException e) {
					model.addAttribute("erro", MENSAGEM_ERRO_UPLOAD);
					return PAGINA_CADASTRAR_PROJETO;
				}
			}
		}

		if (!documentos.isEmpty()) {
			documentoService.salvar(documentos);
		}

		// Executado após validação: projetoService.atualizar(projeto);
		List<Participacao> participacoes = projetoService.getParticipacoesByProjeto(projeto.getId());
		projeto.setParticipacoes(participacoes);

		projetoValidator.validateSubmissao(projeto, result);

		if (result.hasErrors()) {
			model.addAttribute("projeto", projeto);
			model.addAttribute("participantes", pessoaService.getParticipantes(getUsuarioLogado(session)));
			return PAGINA_SUBMETER_PROJETO;

		} else {
			projetoService.atualizar(projeto);
			projetoService.submeter(projeto);

			redirectAttributes.addFlashAttribute("info", MENSAGEM_PROJETO_SUBMETIDO);
			notificacaoService.notificar(projeto, Evento.SUBMISSAO);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
	}

	@RequestMapping(value = "/emitir-parecer/{id-projeto}", method = RequestMethod.GET)
	public String emitirParecerForm(@PathVariable("id-projeto") long idProjeto, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjeto(idProjeto);

		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		if (!projeto.getStatus().equals(StatusProjeto.AGUARDANDO_PARECER)) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		if (!getUsuarioLogado(session).equals(projeto.getParecer().getParecerista())) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		model.addAttribute("projeto", projeto);
		model.addAttribute("posicionamento", StatusPosicionamento.values());
		return PAGINA_EMITIR_PARECER;
	}

	@RequestMapping(value = "/emitir-parecer", method = RequestMethod.POST)
	public String emitirParecer(@RequestParam("id-projeto") Long idProjeto,
			@RequestParam("parecer") String parecerTexto, @RequestParam("anexo") MultipartFile anexo,
			@RequestParam("posicionamento") StatusPosicionamento posicionamento, Model model,
			RedirectAttributes redirectAttributes) {

		Projeto projeto = projetoService.getProjeto(idProjeto);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		projeto.getParecer().setDataRealizacao(new Date());
		projeto.getParecer().setStatus(posicionamento);
		projeto.getParecer().setParecer(parecerTexto);
		if (anexo != null) {
			try {
				Documento documento = new Documento();
				documento.setArquivo(anexo.getBytes());
				documento.setNome(anexo.getOriginalFilename());
				documento.setExtensao(anexo.getContentType());
				documentoService.salvar(documento);
				projeto.getParecer().setDocumento(documento);
			} catch (IOException e) {
				model.addAttribute("erro", MENSAGEM_ERRO_UPLOAD);
				return PAGINA_EMITIR_PARECER;
			}
		}

		// TODO: criar validador de emissão de parecer
		Map<String, String> resultado = projetoService.emitirParecer(projeto);
		if (!resultado.isEmpty()) {
			// buildValidacoesModel(resultado, model);
			model.addAttribute("projeto", projeto);
			model.addAttribute("posicionamento", StatusPosicionamento.values());
			return PAGINA_EMITIR_PARECER;
		}

		redirectAttributes.addFlashAttribute("info", MENSAGEM_PARECER_EMITIDO);
		notificacaoService.notificar(projeto, Evento.EMISSAO_PARECER);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/comentar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Model comentar(HttpServletRequest request, HttpSession session, Model model) {
		Comentario comentario = new Comentario();
		comentario.setTexto(request.getParameter("texto"));
		comentario.setData(new Date());
		Long id = Long.parseLong(request.getParameter("projetoId"));
		Projeto projeto = projetoService.getProjeto(id);
		Pessoa autor = getUsuarioLogado(session);
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

	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa usuario = pessoaService.getPessoa(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, usuario);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}
}
