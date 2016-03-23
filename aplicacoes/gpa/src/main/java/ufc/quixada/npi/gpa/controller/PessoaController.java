package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_USUARIO_NAO_ENCONTRADO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_DETALHES_PARTICIPANTE;
import static ufc.quixada.npi.gpa.utils.Constants.REDIRECT_PAGINA_LISTAR_PROJETO;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.service.ProjetoService;

@Controller
@RequestMapping("pessoa")
public class PessoaController {

	@Inject
	private PessoaService pessoaService;

	@Inject
	private ProjetoService projetoService;

	@RequestMapping(value = "/detalhes/{id}")
	public String getDetalhes(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		Pessoa pessoa = pessoaService.getPessoa(id);
		
		if (pessoa == null) {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_USUARIO_NAO_ENCONTRADO);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		} else {
			model.addAttribute("pessoa", pessoa);

			model.addAttribute("projetosCoordena", projetoService.getProjetosCoordenaAprovadosAtualmente(pessoa.getId()));
			model.addAttribute("projetosCoordenou", projetoService.getProjetosCoordenouAprovadosAtualmente(pessoa.getId()));
			model.addAttribute("projetosParticipa", projetoService.getProjetosParticipaAprovadosAtualmente(pessoa.getId()));
			model.addAttribute("projetosParticipou", projetoService.getProjetosParticipouAprovadosAtualmente(pessoa.getId()));

			return PAGINA_DETALHES_PARTICIPANTE;
		}
	}
}
