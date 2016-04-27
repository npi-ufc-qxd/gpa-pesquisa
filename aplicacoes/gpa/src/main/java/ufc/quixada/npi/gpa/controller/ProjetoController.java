package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.ACTION;
import static ufc.quixada.npi.gpa.utils.Constants.ALERT;
import static ufc.quixada.npi.gpa.utils.Constants.ANO;
import static ufc.quixada.npi.gpa.utils.Constants.CADASTRAR;
import static ufc.quixada.npi.gpa.utils.Constants.EDITAR;
import static ufc.quixada.npi.gpa.utils.Constants.ERRO;
import static ufc.quixada.npi.gpa.utils.Constants.FONTES_FINANCIAMENTO;
import static ufc.quixada.npi.gpa.utils.Constants.INFO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_ERRO_UPLOAD;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PARECER_EMITIDO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PARTICIPACAO_REMOVIDA;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PERMISSAO_NEGADA;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_ATUALIZADO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_CADASTRADO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_INEXISTENTE;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_REMOVIDO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PROJETO_SUBMETIDO;
import static ufc.quixada.npi.gpa.utils.Constants.OLD_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_CADASTRAR_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_DETALHES_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_EMITIR_PARECER;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_EMITIR_PARECER_RELATOR;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_LISTAR_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_SUBMETER_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_UPLOAD_DOCUMENTOS_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PARECER;
import static ufc.quixada.npi.gpa.utils.Constants.PARTICIPACAO;
import static ufc.quixada.npi.gpa.utils.Constants.PARTICIPACAO_EXTERNA;
import static ufc.quixada.npi.gpa.utils.Constants.PARTICIPACOES_EM_PROJETOS;
import static ufc.quixada.npi.gpa.utils.Constants.PARTICIPANTES;
import static ufc.quixada.npi.gpa.utils.Constants.PERMISSAO;
import static ufc.quixada.npi.gpa.utils.Constants.PERMISSAO_COORDENADOR;
import static ufc.quixada.npi.gpa.utils.Constants.PERMISSAO_PARECERISTA;
import static ufc.quixada.npi.gpa.utils.Constants.PERMISSAO_PARTICIPANTE;
import static ufc.quixada.npi.gpa.utils.Constants.PERMISSAO_RELATOR;
import static ufc.quixada.npi.gpa.utils.Constants.PESSOAS;
import static ufc.quixada.npi.gpa.utils.Constants.PESSOAS_EXTERNAS;
import static ufc.quixada.npi.gpa.utils.Constants.POSICIONAMENTO;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETOS;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETOS_AGUARDANDO_AVALIACAO;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETOS_AGUARDANDO_PARECER;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETOS_AVALIADOS;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETOS_HOMOLOGADOS;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETOS_NAO_HOMOLOGADOS;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETOS_PARECER_EMITIDO;
import static ufc.quixada.npi.gpa.utils.Constants.REDIRECT_PAGINA_LISTAR_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.REDIRECT_PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
import static ufc.quixada.npi.gpa.utils.Constants.TIPOS_DE_PARTICIPACAO;
import static ufc.quixada.npi.gpa.utils.Constants.USUARIO;
import static ufc.quixada.npi.gpa.utils.Constants.VALIDACAO;

import java.io.IOException;
import java.math.RoundingMode;
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
import ufc.quixada.npi.gpa.model.Documento.TipoDocumento;
import ufc.quixada.npi.gpa.model.ParecerRelator;
import ufc.quixada.npi.gpa.model.ParecerTecnico;
import ufc.quixada.npi.gpa.model.ParecerTecnico.StatusPosicionamento;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Participacao.TipoParticipacao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.PessoaExterna;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.Evento;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.service.ComentarioService;
import ufc.quixada.npi.gpa.service.FonteFinanciamentoService;
import ufc.quixada.npi.gpa.service.ParticipacaoService;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.service.ProjetoService;
import ufc.quixada.npi.gpa.service.impl.NotificacaoService;
import ufc.quixada.npi.gpa.service.validation.ParecerTecnicoValidation;
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
	private ParecerTecnicoValidation parecerValidator;

	@Autowired
	private ComentarioService comentarioService;

	@Inject
	private ParticipacaoService participacaoService;
	
	@Inject
	private FonteFinanciamentoService fonteFinanciamentoService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listar(Model model, Authentication authentication) {
		Long idUsuarioLogado = pessoaService.getPessoa(authentication.getName()).getId();
		model.addAttribute(PROJETOS, projetoService.getProjetos(idUsuarioLogado));
		model.addAttribute(PROJETOS_NAO_HOMOLOGADOS, projetoService.getProjetosNaoHomologados(idUsuarioLogado));
		model.addAttribute(PARTICIPACOES_EM_PROJETOS, projetoService.getParticipacoes(idUsuarioLogado));
		model.addAttribute(PROJETOS_AGUARDANDO_PARECER, projetoService.getProjetosAguardandoParecer(idUsuarioLogado));
		model.addAttribute(PROJETOS_PARECER_EMITIDO, projetoService.getProjetosParecerEmitido(idUsuarioLogado));
		model.addAttribute(PROJETOS_AGUARDANDO_AVALIACAO, projetoService.getProjetosAguardandoAvaliacao(idUsuarioLogado));
		model.addAttribute(PROJETOS_AVALIADOS, projetoService.getProjetosAvaliados(idUsuarioLogado));
		model.addAttribute(PROJETOS_HOMOLOGADOS, projetoService.getProjetosHomologados(idUsuarioLogado));
		
		return PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.GET)
	public String cadastrarForm(Model model, HttpSession session) {
		model.addAttribute(PROJETO, new Projeto());
		model.addAttribute(FONTES_FINANCIAMENTO, fonteFinanciamentoService.getFontesFinanciamento());
		model.addAttribute(ACTION, CADASTRAR);
		return PAGINA_CADASTRAR_PROJETO;
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String cadastrar(@RequestParam("arquivo_projeto") MultipartFile arquivoProjeto, 
			@Valid Projeto projeto, BindingResult result, RedirectAttributes redirect, 
			Authentication authentication, Model model) {
		
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		projetoValidator.validate(projeto, result);

		if (result.hasErrors()) {
			model.addAttribute(ACTION, CADASTRAR);
			return PAGINA_CADASTRAR_PROJETO;
		}

		projeto.setCoordenador(pessoaService.getPessoa(authentication.getName()));
		projetoService.cadastrar(projeto);
		notificacaoService.notificar(projeto, Evento.CADASTRO_PROJETO, usuario);
		
		if(projeto.getValorProjeto() != null) {
			projeto.setValorProjeto(projeto.getValorProjeto().setScale(2, RoundingMode.FLOOR));
		}

		if(!setInfoDocumentos(arquivoProjeto, projeto, TipoDocumento.ARQUIVO_PROJETO, usuario)){
			model.addAttribute(ERRO, MENSAGEM_ERRO_UPLOAD);

			return PAGINA_CADASTRAR_PROJETO;
		}
		
		projetoService.update(projeto);

		
		redirect.addFlashAttribute(INFO, MENSAGEM_PROJETO_CADASTRADO);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}
	
	@RequestMapping(value="/uploadDocumento/{id}", method = RequestMethod.GET)
	public String uploadArquivoForm(@PathVariable("id") Long id, Model model,
			HttpSession session, RedirectAttributes redirectAttributes, Authentication authentication){
		Projeto projeto = projetoService.getProjeto(id);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (usuarioPodeEditarProjeto(projeto, usuario)) {
			model.addAttribute(PROJETO, projeto);
			return PAGINA_UPLOAD_DOCUMENTOS_PROJETO;
		}
		redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}
	
	@RequestMapping(value="/uploadDocumentos/{id}", method = RequestMethod.POST)
	public String uploadArquivos(@RequestParam("anexos") MultipartFile[] anexos, 
			@PathVariable("id") Long id, Model model, HttpSession session, RedirectAttributes redirect, Authentication authentication){
		Projeto projeto = projetoService.getProjeto(id);
		
		if (anexos != null && anexos.length != 0) {
			for (MultipartFile anexo : anexos) {
				if(!setInfoDocumentos(anexo, projeto, TipoDocumento.ANEXO, pessoaService.getPessoa(authentication.getName()))) {
					model.addAttribute(ERRO, MENSAGEM_ERRO_UPLOAD);
					return PAGINA_UPLOAD_DOCUMENTOS_PROJETO;
				}
			}
		}
		
		projetoService.update(projeto);
		model.addAttribute(PROJETO, projeto);
		return PAGINA_UPLOAD_DOCUMENTOS_PROJETO;
	}

	@RequestMapping(value = "/detalhes/{id}")
	public String verDetalhes(@PathVariable("id") Long id, Model model, HttpSession session,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		Projeto projeto = projetoService.getProjeto(id);

		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}

		model.addAttribute(PROJETO, projeto);
		Pessoa pessoa = pessoaService.getPessoa(authentication.getName());

		if(projeto.getCoordenador().equals(pessoa)){
			model.addAttribute(PERMISSAO,PERMISSAO_COORDENADOR);
			return PAGINA_DETALHES_PROJETO;	
		}
		
		if (pessoa.isDirecao()) {
			model.addAttribute("permissao", "direcao");
			return PAGINA_DETALHES_PROJETO;
		}

		if (projeto.getParecer().getParecerista().equals(pessoa)
				&& projeto.getStatus().equals(StatusProjeto.AGUARDANDO_PARECER)) {
			model.addAttribute(PERMISSAO, PERMISSAO_PARECERISTA);
			return PAGINA_DETALHES_PROJETO;
		}
		
		if(projeto.getParecerRelator().getRelator().equals(pessoa)
				&& projeto.getStatus().equals(StatusProjeto.AGUARDANDO_AVALIACAO)){
				model.addAttribute(PERMISSAO, PERMISSAO_RELATOR);
				return PAGINA_DETALHES_PROJETO;
		}

		if (projeto.getStatus().equals(StatusProjeto.APROVADO)) {
			for (Participacao participacao : projeto.getParticipacoes()) {
				if (pessoa.equals(participacao.getParticipante())) {
					model.addAttribute(PERMISSAO, PERMISSAO_PARTICIPANTE);
					return PAGINA_DETALHES_PROJETO;
				}
			}
		}
		redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
	public String editarForm(@PathVariable("id") Long id, Model model, HttpSession session,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		Projeto projeto = projetoService.getProjeto(id);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (usuarioPodeEditarProjeto(projeto, usuario)) {
			model.addAttribute(PROJETO, projeto);
			model.addAttribute(FONTES_FINANCIAMENTO, fonteFinanciamentoService.getFontesFinanciamento());
			model.addAttribute(ACTION, EDITAR);
			return PAGINA_CADASTRAR_PROJETO;
		}

		redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public String editar(@RequestParam("arquivo_projeto") MultipartFile arquivoProjeto, 
			@Valid Projeto projeto, BindingResult result, Model model, HttpSession session,
			RedirectAttributes redirect, Authentication authentication) {
		model.addAttribute(ACTION, EDITAR);
		
		if (result.hasErrors()) {
			if (result.hasGlobalErrors()) {
				model.addAttribute(VALIDACAO, result);
			}
			return PAGINA_CADASTRAR_PROJETO;
		}

		Projeto oldProjeto = projetoService.getProjeto(projeto.getId());
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		oldProjeto.setCoordenador(usuario);
		oldProjeto = updateProjetoFields(oldProjeto, projeto);

		projetoValidator.validate(oldProjeto, result);
		
		if(!setInfoDocumentos(arquivoProjeto, oldProjeto, TipoDocumento.ARQUIVO_PROJETO, usuario)) {
			model.addAttribute(ERRO, MENSAGEM_ERRO_UPLOAD);
			return PAGINA_CADASTRAR_PROJETO;
		}

		projetoService.update(oldProjeto);
		notificacaoService.notificar(projeto, Evento.EDICAO_PROJETO, usuario);
		redirect.addFlashAttribute(INFO, MENSAGEM_PROJETO_ATUALIZADO);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}
	
	@RequestMapping(value = "/participacoes/{id}", method = RequestMethod.GET)
	public String listarParticipacoes(@PathVariable("id") Long id, Model model,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		
		Projeto projeto = projetoService.getProjeto(id);
		model.addAttribute(TIPOS_DE_PARTICIPACAO, TipoParticipacao.values());
		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}

		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (!usuarioPodeEditarProjeto(projeto, usuario)) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Calendar calendario = Calendar.getInstance();
		model.addAttribute(ANO, calendario.get(Calendar.YEAR));
		model.addAttribute(PROJETO, projeto);
		model.addAttribute(PARTICIPACAO, new Participacao());
		model.addAttribute(PARTICIPACAO_EXTERNA, new PessoaExterna());
		model.addAttribute(PESSOAS_EXTERNAS, pessoaService.getAllPessoaExterna());
		model.addAttribute(PESSOAS, pessoaService.getAll());
		return PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
	}

	@RequestMapping(value = "/participacoes/{idProjeto}", method = RequestMethod.POST)
	public String adicionarParticipacao(@PathVariable("idProjeto") Long idProjeto,
			@RequestParam(value = "participanteSelecionado", required = true) Long idParticipanteSelecionado,
			@RequestParam(value = "participanteExternoSelecionado", required = false) Long idParticipanteExternoSelecionado,
			Participacao participacao, HttpSession session, Model model, 
			BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {

		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		Projeto projeto = projetoService.getProjeto(idProjeto);
		model.addAttribute(TIPOS_DE_PARTICIPACAO,TipoParticipacao.values());
		model.addAttribute(PESSOAS_EXTERNAS, pessoaService.getAllPessoaExterna());

		model.addAttribute(TIPOS_DE_PARTICIPACAO, TipoParticipacao.values());
		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		
		if (!usuarioPodeEditarProjeto(projeto, usuario)) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		if(participacao.isExterno()){
			if(idParticipanteExternoSelecionado==null){
				redirectAttributes.addFlashAttribute(ERRO, "Cadastrar ou selecionar pessoa externa primeiro!");
				return REDIRECT_PAGINA_VINCULAR_PARTICIPANTES_PROJETO + idProjeto;
			}
			participacao.setParticipanteExterno(pessoaService.getPessoaExterna(idParticipanteExternoSelecionado));
		}else{
			participacao.setParticipante(pessoaService.getPessoa(idParticipanteSelecionado));
		}
		participacao.setProjeto(projeto);
		participacaoValidator.validate(participacao, result);
		if (result.hasErrors()) {
			model.addAttribute(PROJETO, projeto);
			model.addAttribute(PESSOAS, pessoaService.getAll());
			model.addAttribute(VALIDACAO, result);
			return PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
		}
		try {
			participacaoService.verificaIntervalosParticipacaoPessoa(participacao, projeto.getId());
		} catch (IllegalArgumentException e) {
			model.addAttribute(ERRO, e.getMessage());
			model.addAttribute(PROJETO, projeto);
			model.addAttribute(PESSOAS, pessoaService.getAll());
			return PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
		}
		projeto.adicionarParticipacao(participacao);
		projetoService.update(projeto);
		notificacaoService.notificar(projeto, Evento.ADICAO_PARTICIPANTE, usuario);

		Calendar calendario = Calendar.getInstance();
		model.addAttribute(ANO, calendario.get(Calendar.YEAR));
		model.addAttribute(USUARIO, usuario);
		model.addAttribute(PROJETO, projeto);
		model.addAttribute(PARTICIPACAO, new Participacao());
		model.addAttribute(PESSOAS, pessoaService.getAll());
		return PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
	}

	@RequestMapping(value = "/participacoes/{idProjeto}/excluir/{idParticipacao}")
	public String excluirParticipacao(@PathVariable("idProjeto") Long idProjeto,
			@PathVariable("idParticipacao") Long idParticipacao, HttpSession session, Model model,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		Projeto projeto = projetoService.getProjeto(idProjeto);
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		
		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		
		if (!usuarioPodeEditarProjeto(projeto, usuario)) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
			return PAGINA_VINCULAR_PARTICIPANTES_PROJETO;
		}
		Participacao participacao = projetoService.getParticipacao(idParticipacao);
		projetoService.removerParticipacao(projeto, participacao);
		notificacaoService.notificar(projeto, Evento.REMOCAO_PARTICIPANTE, usuario);
		redirectAttributes.addFlashAttribute(INFO, MENSAGEM_PARTICIPACAO_REMOVIDA);

		model.addAttribute(PROJETO, projeto);
		model.addAttribute(PARTICIPACAO, new Participacao());
		model.addAttribute(PESSOAS, pessoaService.getAll());
		model.addAttribute(PESSOAS_EXTERNAS, pessoaService.getAllPessoaExterna());
		return REDIRECT_PAGINA_VINCULAR_PARTICIPANTES_PROJETO + idProjeto;
	}

	private boolean usuarioPodeEditarProjeto(Projeto projeto, Pessoa usuario) {
		return (usuario.getId() == projeto.getCoordenador().getId() && (projeto.getStatus().equals(StatusProjeto.NOVO) || 
				projeto.getStatus().equals(StatusProjeto.RESOLVENDO_PENDENCIAS) || projeto.getStatus().equals(StatusProjeto.RESOLVENDO_RESTRICOES)));
	}

	@RequestMapping(value = "/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, HttpSession session, RedirectAttributes redirectAttributes,
			Authentication authentication) {
		Projeto projeto = projetoService.getProjeto(id);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (usuarioPodeEditarProjeto(projeto, usuario)) {
			projetoService.remover(projeto);
			redirectAttributes.addFlashAttribute(INFO, MENSAGEM_PROJETO_REMOVIDO);
		} else {
			redirectAttributes.addFlashAttribute(INFO, MENSAGEM_PERMISSAO_NEGADA);
		}
		return REDIRECT_PAGINA_LISTAR_PROJETO;

	}

	@RequestMapping(value = "/submeter/{id}", method = RequestMethod.GET)
	public String submeterForm(HttpSession session, @PathVariable("id") Long id, RedirectAttributes redirectAttributes,
			Model model, Authentication authentication) {
		
		Projeto projeto = projetoService.getProjeto(id);
		
		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}

		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (usuarioPodeEditarProjeto(projeto, usuario)) {

			// Adicionando o @ModelAttribute ao BindingResult
			BindingResult result = new BeanPropertyBindingResult(projeto, PROJETO);
			projetoValidator.validateSubmissao(projeto, result);

			if (result.hasErrors()) {
				model.addAttribute(PROJETO, projeto);
				model.addAttribute(ALERT, Constants.MENSAGEM_CAMPO_OBRIGATORIO_SUBMISSAO);

				if (result.hasGlobalErrors()) {
					model.addAttribute(VALIDACAO, result);
				}
				
				model.addAttribute(FONTES_FINANCIAMENTO, fonteFinanciamentoService.getFontesFinanciamento());
				
				return PAGINA_SUBMETER_PROJETO;
			} else if (projeto.getStatus().equals(StatusProjeto.RESOLVENDO_PENDENCIAS)) {
				projetoService.submeterPendencias(projeto);
				
				redirectAttributes.addFlashAttribute(INFO, Constants.MENSAGEM_PROJETO_RESOLUCAO_PENDENCIAS);
				notificacaoService.notificar(projeto, Evento.SUBMISSAO_RESOLUCAO_PENDENCIAS, usuario);
				return REDIRECT_PAGINA_LISTAR_PROJETO;
			} else if (projeto.getStatus().equals(StatusProjeto.RESOLVENDO_RESTRICOES)) {
				projetoService.submeterPendenciasRelator(projeto);
				
				redirectAttributes.addFlashAttribute(INFO, Constants.MENSAGEM_PROJETO_RESOLUCAO_PENDENCIAS);
				notificacaoService.notificar(projeto, Evento.SUBMISSAO_RESOLUCAO_PENDENCIAS, usuario);
				return REDIRECT_PAGINA_LISTAR_PROJETO;
			} else {
				projetoService.submeter(projeto);

				redirectAttributes.addFlashAttribute(INFO, MENSAGEM_PROJETO_SUBMETIDO);
				notificacaoService.notificar(projeto, Evento.SUBMISSAO, usuario);
				return REDIRECT_PAGINA_LISTAR_PROJETO;
			}
		} else {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
	}

	@RequestMapping(value = "submeter", method = RequestMethod.POST)
	public String submeter(@RequestParam("anexos") List<MultipartFile> anexos,
			@RequestParam("arquivo_projeto") MultipartFile arquivoProjeto, @Valid Projeto projeto,
			Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		Projeto oldProjeto = projetoService.getProjeto(projeto.getId());
		oldProjeto.setCoordenador(usuario);
		oldProjeto = updateProjetoFields(oldProjeto, projeto);

		if (anexos != null && !anexos.isEmpty()) {
			for (MultipartFile anexo : anexos) {
				if(!setInfoDocumentos(anexo, oldProjeto, TipoDocumento.ANEXO, usuario)) {
					model.addAttribute(ERRO, MENSAGEM_ERRO_UPLOAD);
					return PAGINA_CADASTRAR_PROJETO;
				} 
			}
		}

		if(!setInfoDocumentos(arquivoProjeto, oldProjeto, TipoDocumento.ARQUIVO_PROJETO, usuario)) {
			model.addAttribute(ERRO, MENSAGEM_ERRO_UPLOAD);
			model.addAttribute(FONTES_FINANCIAMENTO, fonteFinanciamentoService.getFontesFinanciamento());
			return PAGINA_SUBMETER_PROJETO;
		}
		
		projetoService.update(oldProjeto);

		BindingResult result = new BeanPropertyBindingResult(oldProjeto, OLD_PROJETO);
		projetoValidator.validateSubmissao(oldProjeto, result);
		
		if (result.hasErrors()) {
			model.addAttribute(PROJETO, oldProjeto);
			model.addAttribute(PARTICIPANTES, pessoaService.getParticipantes(usuario));
			model.addAttribute(VALIDACAO, result);
			model.addAttribute(FONTES_FINANCIAMENTO, fonteFinanciamentoService.getFontesFinanciamento());
			return PAGINA_SUBMETER_PROJETO;

		} else if (oldProjeto.getStatus().equals(StatusProjeto.RESOLVENDO_PENDENCIAS)) {
			projetoService.submeterPendencias(oldProjeto);
			
			redirectAttributes.addFlashAttribute(INFO, Constants.MENSAGEM_PROJETO_RESOLUCAO_PENDENCIAS);
			notificacaoService.notificar(oldProjeto, Evento.SUBMISSAO_RESOLUCAO_PENDENCIAS, usuario);
			
		} else if (oldProjeto.getStatus().equals(StatusProjeto.RESOLVENDO_RESTRICOES)) {
			projetoService.submeterPendenciasRelator(oldProjeto);
			
			redirectAttributes.addFlashAttribute(INFO, Constants.MENSAGEM_PROJETO_RESOLUCAO_PENDENCIAS);
			notificacaoService.notificar(oldProjeto, Evento.SUBMISSAO_RESOLUCAO_PENDENCIAS, usuario);
			
		} else {
			projetoService.submeter(oldProjeto);

			redirectAttributes.addFlashAttribute(INFO, MENSAGEM_PROJETO_SUBMETIDO);
			notificacaoService.notificar(projeto, Evento.SUBMISSAO, usuario);
		}
		
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/emitir-parecer/{id-projeto}", method = RequestMethod.GET)
	public String emitirParecerForm(@PathVariable("id-projeto") long idProjeto, Model model, HttpSession session,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		Projeto projeto = projetoService.getProjeto(idProjeto);

		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		if (!projeto.getStatus().equals(StatusProjeto.AGUARDANDO_PARECER)) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (!usuario.equals(projeto.getParecer().getParecerista())) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		model.addAttribute(PROJETO, projeto);
		model.addAttribute(POSICIONAMENTO, StatusPosicionamento.values());
		model.addAttribute(PARECER, new ParecerTecnico());
		return PAGINA_EMITIR_PARECER;
	}

	@RequestMapping(value = "/emitir-parecer", method = RequestMethod.POST)
	public String emitirParecer(@RequestParam("id-projeto") Long idProjeto, @RequestParam("anexo") MultipartFile anexo,
			@RequestParam("posicionamento") StatusPosicionamento posicionamento, Model model,
			@Valid ParecerTecnico parecer, BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {

		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		Projeto projeto = projetoService.getProjeto(idProjeto);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}

		projeto.getParecer().setDataRealizacao(new Date());
		projeto.getParecer().setStatus(posicionamento);
		projeto.getParecer().setParecer(parecer.getParecer());
		
		if(!setInfoDocumentos(anexo, projeto, TipoDocumento.DOCUMENTO_PARECER, usuario)) {
			model.addAttribute(ERRO, MENSAGEM_ERRO_UPLOAD);
			return PAGINA_EMITIR_PARECER;
		}

		parecerValidator.validate(projeto.getParecer(), result);
		if (result.hasErrors()) {
			model.addAttribute(PROJETO, projeto);
			model.addAttribute(POSICIONAMENTO, StatusPosicionamento.values());
			return PAGINA_EMITIR_PARECER;
		}

		projetoService.emitirParecer(projeto);

		redirectAttributes.addFlashAttribute(INFO, MENSAGEM_PARECER_EMITIDO);
		notificacaoService.notificar(projeto, Evento.EMISSAO_PARECER, usuario);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}
	
	@RequestMapping(value = "/avaliar/{id-projeto}", method = RequestMethod.GET)
	public String avaliarForm(@PathVariable("id-projeto") long idProjeto, Model model, HttpSession session,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		Projeto projeto = projetoService.getProjeto(idProjeto);

		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		if (!projeto.getStatus().equals(StatusProjeto.AGUARDANDO_AVALIACAO)) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		if (!usuario.equals(projeto.getParecerRelator().getRelator())) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		model.addAttribute(PROJETO, projeto);
		model.addAttribute(POSICIONAMENTO, StatusPosicionamento.values());
		model.addAttribute(PARECER, new ParecerRelator());
		return PAGINA_EMITIR_PARECER_RELATOR;
	}
	
	@RequestMapping(value = "/avaliar", method = RequestMethod.POST)
	public String avaliar(@RequestParam("id-projeto") Long idProjeto,
			@RequestParam("posicionamento") StatusPosicionamento posicionamento,
			@RequestParam("observacao") String observacao, Model model, RedirectAttributes redirectAttributes) {
		
		Projeto projeto = projetoService.getProjeto(idProjeto);
		
		if(projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		
		projeto.getParecerRelator().setData(new Date());
		projeto.getParecerRelator().setStatus(posicionamento);
		projeto.getParecerRelator().setObservacao(observacao);
		projeto.setStatus(StatusProjeto.AGUARDANDO_HOMOLOGACAO);
		
		projetoService.update(projeto);
		
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

	private Projeto updateProjetoFields(Projeto oldProjeto, Projeto newProjeto) {
		oldProjeto.setDescricao(newProjeto.getDescricao());
		oldProjeto.setInicio(newProjeto.getInicio());
		oldProjeto.setLocal(newProjeto.getLocal());
		oldProjeto.setNome(newProjeto.getNome());
		oldProjeto.setAtividades(newProjeto.getAtividades());
		oldProjeto.setTermino(newProjeto.getTermino());
		oldProjeto.setValorProjeto(newProjeto.getValorProjeto());
		oldProjeto.setFonteFinanciamento(newProjeto.getFonteFinanciamento());
		return oldProjeto;
	}

	@RequestMapping(value = "/solicitar-resolucao-pendencias/{id-projeto}")
	public String SolicitarResolucaoPendencias(@PathVariable("id-projeto") Long idProjeto, RedirectAttributes redirectAttributes, Authentication authentication) {

		Pessoa usuario = pessoaService.getPessoa(authentication.getName());
		Projeto projeto = projetoService.getProjeto(idProjeto);
		
		if (projeto == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		
		if (projeto.getStatus().equals(StatusProjeto.AGUARDANDO_PARECER)) {
			projeto.setStatus(StatusProjeto.RESOLVENDO_PENDENCIAS);
			notificacaoService.notificar(projeto, Evento.RESOLUCAO_PENDENCIAS, usuario);
			projetoService.update(projeto);

			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		
		if(projeto.getStatus().equals(StatusProjeto.AGUARDANDO_AVALIACAO)){
			projeto.setStatus(StatusProjeto.RESOLVENDO_RESTRICOES);
			notificacaoService.notificar(projeto, Evento.RESOLUCAO_RESTRICAO, usuario);
			projetoService.update(projeto);

			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}

		redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	public boolean setInfoDocumentos(MultipartFile arquivo, Projeto projeto, TipoDocumento tipo, Pessoa pessoa) {
		try {
			if(arquivo.getBytes() != null && arquivo.getBytes().length != 0) {
				Documento documento = new Documento();
				documento.setArquivo(arquivo.getBytes());
				documento.setNome(arquivo.getOriginalFilename());
				documento.setNomeOriginal(String.valueOf(System.currentTimeMillis()) + "_" + documento.getNome());
				documento.setExtensao(arquivo.getContentType());
				documento.setCaminho(projeto.getCaminhoArquivos() + "/" + documento.getNomeOriginal());
				documento.setPessoa(pessoa);
				documento.setData(new Date());
				
				switch (tipo) {
				case DOCUMENTO_PARECER:
					projeto.getParecer().setDocumento(documento);
					break;
					
				case ARQUIVO_PROJETO:
					projeto.setArquivoProjeto(documento);
					break;
					
				case ANEXO:
					projeto.addDocumento(documento);
					break;
					
				case ATA_HOMOLOGACAO:
					projeto.setAta(documento);
					break;
					
				case OFICIO_HOMOLOGACAO:
					projeto.setOficio(documento);
					break;
					
				default:
					break;
				}
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}