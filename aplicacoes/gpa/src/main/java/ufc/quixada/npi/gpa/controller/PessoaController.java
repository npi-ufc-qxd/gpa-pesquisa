package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.*;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.ProjetoService;
import ufc.quixada.npi.gpa.service.UsuarioService;

@Controller
@RequestMapping("usuario")
public class PessoaController {
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private ProjetoService projetoService;

	@RequestMapping(value = "/{id}/detalhes")
	public String getDetalhes(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		Pessoa usuario = usuarioService.getUsuarioById(id);
		if (usuario == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_USUARIO_NAO_ENCONTRADO);
			return REDIRECT_PAGINA_LISTAR_PROJETO;

		} else {
			model.addAttribute("usuario", usuario);
			model.addAttribute("projetos", projetoService.getProjetosByParticipante(usuario.getId()));
			return PAGINA_DETALHES_USUARIO;
		}

	}

}
