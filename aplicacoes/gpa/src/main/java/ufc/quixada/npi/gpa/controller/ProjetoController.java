package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.Comentario;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Parecer.StatusPosicionamento;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.service.ComentarioService;
import ufc.quixada.npi.gpa.service.DocumentoService;
import ufc.quixada.npi.gpa.service.ProjetoService;
import ufc.quixada.npi.gpa.service.UsuarioService;
import ufc.quixada.npi.gpa.utils.Constants;

@Controller
@RequestMapping("projeto")
public class ProjetoController {

	@Inject
	private ProjetoService projetoService;

	@Inject
	private UsuarioService usuarioService;

	@Autowired
	private ComentarioService comentarioService;
	
	@Autowired
	private DocumentoService documentoService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}
	
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model, HttpSession session) {
		Long idUsuarioLogado = getUsuarioLogado(session).getId();
		model.addAttribute("projetos", projetoService.getProjetosByUsuario(idUsuarioLogado));
		model.addAttribute("projetosAguardandoParecer", projetoService.getProjetosAguardandoParecer(idUsuarioLogado));
		model.addAttribute("projetosAvaliados", projetoService.getProjetosAvaliadosDoUsuario(idUsuarioLogado));

		if (usuarioService.isDiretor(getUsuarioLogado(session))) {
			model.addAttribute("projetosSubmetidos", projetoService.getProjetosSubmetidos());
			model.addAttribute("projetosAvaliados", projetoService.getProjetosAvaliados());
			model.addAttribute("participantes", usuarioService.getParticipantesProjetos());
			return PAGINA_LISTAR_PROJETO_DIRETOR;
		}
		return PAGINA_LISTAR_PROJETO;

	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.GET)
	public String cadastrarForm(Model model, HttpSession session) {   
		model.addAttribute("projeto", new Projeto());
		model.addAttribute("participantes", usuarioService.getParticipantes(getUsuarioLogado(session)));
		model.addAttribute("action", "cadastrar");
		return PAGINA_CADASTRAR_PROJETO;
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String cadastrar(@RequestParam(value = "idParticipantes", required = false) List<String> idParticipantes, @RequestParam("anexos") List<MultipartFile> anexos,
			@Valid Projeto projeto, BindingResult result, HttpSession session, RedirectAttributes redirect, Model model) {
		
		model.addAttribute("participantes", usuarioService.getParticipantes(getUsuarioLogado(session)));
		model.addAttribute("action", "cadastrar");
		if (result.hasErrors()) {
			return PAGINA_CADASTRAR_PROJETO;
		}
		
		projeto.setAutor(getUsuarioLogado(session));
		
		if(idParticipantes != null && !idParticipantes.isEmpty()) {
			List<Pessoa> participantes = new ArrayList<Pessoa>();
			for(String idParticipante : idParticipantes) {
				participantes.add(usuarioService.getUsuarioById(new Long(idParticipante)));
			}
			projeto.setParticipantes(participantes);
		}
		
		List<Documento> documentos = new ArrayList<Documento>();
		if(anexos != null && !anexos.isEmpty()) {
			for(MultipartFile anexo : anexos) {
				try {
					if(anexo.getBytes() != null && anexo.getBytes().length != 0) {
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
		
		Map<String, String> resultado = projetoService.cadastrar(projeto);
		if (!resultado.isEmpty()) {
			buildValidacoesBindingResult(resultado, result);
			return PAGINA_CADASTRAR_PROJETO;
		}
		
		if(!documentos.isEmpty()) {
			documentoService.salvar(documentos);
		}
		
		redirect.addFlashAttribute("info", MENSAGEM_PROJETO_CADASTRADO);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}

	@RequestMapping(value = "/{id}/detalhes")
	public String verDetalhes(@PathVariable("id") Long id, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjetoById(id);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = getUsuarioLogado(session);
		if (usuario.getId() == projeto.getAutor().getId() || usuarioService.isDiretor(usuario) || 
				(projeto.getParecer() != null && projeto.getParecer().getParecerista().getId() == usuario.getId())) {
			List<Comentario> comentarios = projeto.getComentarios();
			Collections.sort(comentarios, new Comparator<Comentario>() {
		        @Override
		        public int compare(Comentario  comentario1, Comentario  comentario2)
		        {
		            return  comentario1.getData().compareTo(comentario2.getData());
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

	@RequestMapping(value = "/{id}/editar", method = RequestMethod.GET)
	public String editarForm(@PathVariable("id") Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {		
		Projeto projeto = projetoService.getProjetoById(id);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = getUsuarioLogado(session);
		if (usuario.getId() == projeto.getAutor().getId() && projeto.getStatus().equals(StatusProjeto.NOVO)) {
			model.addAttribute("projeto", projeto);
			model.addAttribute("participantes", usuarioService.getParticipantes(usuario));
			model.addAttribute("action", "editar");
			return PAGINA_CADASTRAR_PROJETO;
		}

		redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}
	
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public String editar(@RequestParam(value = "idParticipantes", required = false) List<String> idParticipantes, @RequestParam("anexos") List<MultipartFile> anexos,
			@Valid Projeto projeto, BindingResult result, Model model, HttpSession session,
			RedirectAttributes redirect) {
		
		model.addAttribute("participantes", usuarioService.getParticipantes(getUsuarioLogado(session)));
		model.addAttribute("action", "cadastrar");
		if (result.hasErrors()) {
			return PAGINA_CADASTRAR_PROJETO;
		}
		
		projeto.setAutor(getUsuarioLogado(session));
		
		if(idParticipantes != null && !idParticipantes.isEmpty()) {
			List<Pessoa> participantes = new ArrayList<Pessoa>();
			for(String idParticipante : idParticipantes) {
				participantes.add(usuarioService.getUsuarioById(new Long(idParticipante)));
			}
			projeto.setParticipantes(participantes);
		}
		
		List<Documento> documentos = new ArrayList<Documento>();
		if(anexos != null && !anexos.isEmpty()) {
			for(MultipartFile anexo : anexos) {
				try {
					if(anexo.getBytes() != null && anexo.getBytes().length != 0) {
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
		
		Map<String, String> resultado = projetoService.atualizar(projeto);
		if (!resultado.isEmpty()) {
			buildValidacoesBindingResult(resultado, result);
			return PAGINA_CADASTRAR_PROJETO;
		}
		
		if(!documentos.isEmpty()) {
			documentoService.salvar(documentos);
		}
		
		redirect.addFlashAttribute("info", MENSAGEM_PROJETO_ATUALIZADO);
		return REDIRECT_PAGINA_LISTAR_PROJETO;
	}
	
	@RequestMapping(value = "/{id}/excluir")
	public String excluir(@PathVariable("id") Long id, HttpSession session, RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjetoById(id);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		Pessoa usuario = getUsuarioLogado(session);
		if (usuario.getId() == projeto.getAutor().getId() && projeto.getStatus().equals(StatusProjeto.NOVO)) {
			projetoService.remover(projeto);
			redirectAttributes.addFlashAttribute("info", MENSAGEM_PROJETO_REMOVIDO);
		} else {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
		}
		return REDIRECT_PAGINA_LISTAR_PROJETO;

	}
	
	@RequestMapping(value = "{id}/submeter", method = RequestMethod.GET)
	public String submeterForm(@PathVariable("id") Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjetoById(id);

		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		
		Pessoa usuario = getUsuarioLogado(session);
		if (usuario.getId() == projeto.getAutor().getId() && projeto.getStatus().equals(StatusProjeto.NOVO)) {
			Map<String, String> resultadoValidacao = projetoService.submeter(projeto);
			if(resultadoValidacao.isEmpty()) {
				redirectAttributes.addFlashAttribute("info", MENSAGEM_PROJETO_SUBMETIDO);
				return REDIRECT_PAGINA_LISTAR_PROJETO;
			}
			model.addAttribute("projeto", projeto);
			model.addAttribute("participantes", usuarioService.getParticipantes(usuario));
			model.addAttribute("alert", MENSAGEM_CAMPO_OBRIGATORIO_SUBMISSAO);
			return PAGINA_SUBMETER_PROJETO;
		} else {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
	}

	@RequestMapping(value = "submeter", method = RequestMethod.POST)
	public String submeter(@RequestParam(value = "idParticipantes", required = false) List<String> idParticipantes, @RequestParam("anexos") List<MultipartFile> anexos,
			@Valid Projeto projeto, Model model, HttpSession session, RedirectAttributes redirectAttributes) {		
		
		projeto.setAutor(getUsuarioLogado(session));
		if(idParticipantes != null && !idParticipantes.isEmpty()) {
			List<Pessoa> participantes = new ArrayList<Pessoa>();
			for(String idParticipante : idParticipantes) {
				participantes.add(usuarioService.getUsuarioById(new Long(idParticipante)));
			}
			projeto.setParticipantes(participantes);
		}
		List<Documento> documentos = new ArrayList<Documento>();
		if(anexos != null && !anexos.isEmpty()) {
			for(MultipartFile anexo : anexos) {
				try {
					if(anexo.getBytes() != null && anexo.getBytes().length != 0) {
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
		
		if(!documentos.isEmpty()) {
			documentoService.salvar(documentos);
		}
		projetoService.atualizar(projeto);
		projeto.setDocumentos(documentoService.getDocumentoByProjeto(projeto));
		Map<String, String> resultadoValidacao = projetoService.submeter(projeto);
		if(resultadoValidacao.isEmpty()) {
			redirectAttributes.addFlashAttribute("info", MENSAGEM_PROJETO_SUBMETIDO);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		
		model.addAttribute("projeto", projeto);
		model.addAttribute("participantes", usuarioService.getParticipantes(getUsuarioLogado(session)));
		buildValidacoesModel(resultadoValidacao, model);
		return PAGINA_SUBMETER_PROJETO;
	}
	
	@RequestMapping(value = "diretor/{projetoId}/atribuirParecerista", method = RequestMethod.GET)
	public String atribuirPareceristaForm(@PathVariable("projetoId") Long projetoId, Model model, RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjetoById(projetoId);
		if (projeto == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		if (projeto.getStatus() != StatusProjeto.SUBMETIDO) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_PERMISSAO_NEGADA);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}

		model.addAttribute("projeto", projeto);
		model.addAttribute("usuarios", usuarioService.getPareceristas(projeto.getAutor().getId()));
		return PAGINA_ATRIBUIR_PARECERISTA;
	}
	
	@RequestMapping(value = "diretor/atribuirParecerista", method = RequestMethod.POST)
	public String atribuirParecerista(@RequestParam("prazo") @DateTimeFormat(pattern = "dd/MM/yyyy") Date prazo, @RequestParam("observacao") String observacao, @RequestParam("projetoId") Long projetoId, 
			@RequestParam("pareceristaId") Long pareceristaId, Model model, RedirectAttributes redirectAttributes) {

		Projeto projeto = projetoService.getProjetoById(projetoId);
		Pessoa parecerista = usuarioService.getUsuarioById(pareceristaId);
		
		Parecer parecer = new Parecer();
		parecer.setDataAtribuicao(new Date());
		parecer.setObservacao(observacao);
		parecer.setParecerista(parecerista);
		parecer.setPrazo(prazo);
		
		Map<String, String> resultado = projetoService.atribuirParecerista(projeto, parecer);
		if(!resultado.isEmpty()) {
			buildValidacoesModel(resultado, model);
			model.addAttribute("projeto", projeto);
			model.addAttribute("usuarios", usuarioService.getPareceristas(projeto.getAutor().getId()));
			return PAGINA_ATRIBUIR_PARECERISTA;
		}
		
		redirectAttributes.addFlashAttribute("info", MENSAGEM_PARECERISTA_ATRIBUIDO);
		return REDIRECT_PAGINA_LISTAR_PROJETO;

	}

	@RequestMapping(value = "/{idProjeto}/emitirParecer", method = RequestMethod.GET)
	public String emitirParecerForm(@PathVariable("idProjeto") long idProjeto, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		Projeto projeto = projetoService.getProjetoById(idProjeto);
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
	
	@RequestMapping(value = "/{idProjeto}/emitirParecer", method = RequestMethod.POST)
	public String emitirParecer(@PathVariable("idProjeto") Long idProjeto, @RequestParam("parecer") String parecerTexto,
			@RequestParam("anexo") MultipartFile anexo, @RequestParam("posicionamento") StatusPosicionamento posicionamento, 
			Model model, RedirectAttributes redirectAttributes){

		Projeto projeto = projetoService.getProjetoById(idProjeto);
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
		Map<String, String> resultado = projetoService.emitirParecer(projeto);
		if(!resultado.isEmpty()) {
			buildValidacoesModel(resultado, model);
			model.addAttribute("projeto", projeto);
			model.addAttribute("posicionamento", StatusPosicionamento.values());
			return PAGINA_EMITIR_PARECER;
		}

		redirectAttributes.addFlashAttribute("info", MENSAGEM_PARECER_EMITIDO);
		return REDIRECT_PAGINA_LISTAR_PROJETO;

	}
	
	@RequestMapping(value = "/diretor/{id}/avaliar", method = RequestMethod.GET)
	public String avaliarForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		Projeto projeto = projetoService.getProjetoById(id);
		if (projeto == null || !projeto.getStatus().equals(StatusProjeto.AGUARDANDO_AVALIACAO)) {
			redirect.addFlashAttribute("erro", MENSAGEM_PROJETO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		model.addAttribute("projeto", projeto);
		return PAGINA_AVALIAR_PROJETO;
	}
	
	@RequestMapping(value = "/diretor/{id}/avaliar", method = RequestMethod.POST)
	public String avaliar(@PathVariable("id") Long id, @RequestParam("avaliacao") StatusProjeto avaliacao, 
			@RequestParam("ata") MultipartFile ata, @RequestParam("oficio") MultipartFile oficio, Model model, RedirectAttributes redirect) {
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
		Map<String, String> resultado = projetoService.avaliar(projeto);
		if(resultado.isEmpty()) {
			redirect.addFlashAttribute("info", MENSAGEM_PROJETO_AVALIADO);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		}
		model.addAttribute("projeto", projeto);
		buildValidacoesModel(resultado, model);
		return PAGINA_AVALIAR_PROJETO;
	}
	
	private void buildValidacoesBindingResult(Map<String, String> resultado, BindingResult result) {
		Set<String> keys = resultado.keySet();
		for (String key : keys) {
			result.rejectValue(key, "Repeat.titulo." + key, resultado.get(key));
		}
	}
	
	private void buildValidacoesModel(Map<String, String> resultado, Model model) {
		Set<String> keys = resultado.keySet();
		for (String key : keys) {
			model.addAttribute("erro_" + key, resultado.get(key));
		}
	}
	
	private Pessoa getUsuarioLogado(HttpSession session) {
		if (session.getAttribute(Constants.USUARIO_LOGADO) == null) {
			Pessoa usuario = usuarioService
					.getUsuarioByLogin(SecurityContextHolder.getContext()
							.getAuthentication().getName());
			session.setAttribute(Constants.USUARIO_LOGADO, usuario);
		}
		return (Pessoa) session.getAttribute(Constants.USUARIO_LOGADO);
	}

}
