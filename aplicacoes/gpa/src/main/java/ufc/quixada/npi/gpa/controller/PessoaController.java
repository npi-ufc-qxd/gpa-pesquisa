package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.ERRO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_USUARIO_NAO_ENCONTRADO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_DETALHES_PARTICIPANTE;
import static ufc.quixada.npi.gpa.utils.Constants.PESSOA;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETOS_COORDENA;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETOS_COORDENOU;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETOS_PARTICIPA;
import static ufc.quixada.npi.gpa.utils.Constants.PROJETOS_PARTICIPOU;
import static ufc.quixada.npi.gpa.utils.Constants.REDIRECT_PAGINA_LISTAR_PROJETO;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.PessoaExterna;
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
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_USUARIO_NAO_ENCONTRADO);
			return REDIRECT_PAGINA_LISTAR_PROJETO;
		} else {
			model.addAttribute(PESSOA, pessoa);

			model.addAttribute(PROJETOS_COORDENA, projetoService.getProjetosCoordenaHomologadosAtualmente(pessoa.getId()));
			model.addAttribute(PROJETOS_COORDENOU, projetoService.getProjetosCoordenouHomologadosAtualmente(pessoa.getId()));
			model.addAttribute(PROJETOS_PARTICIPA, projetoService.getProjetosParticipaHomologadosAtualmente(pessoa.getId()));
			model.addAttribute(PROJETOS_PARTICIPOU, projetoService.getProjetosParticipouHomologadosAtualmente(pessoa.getId()));

			return PAGINA_DETALHES_PARTICIPANTE;
		}
	}
	
	@RequestMapping(value= "/cadastrarExterno", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Model cadastrarPessoaExterna(HttpServletRequest request, Model model){
		PessoaExterna pessoaExterna = new PessoaExterna();
		if(request.getParameter("nome").equals("") || request.getParameter("email").equals("") || request.getParameter("cpf").equals(""))
			return null;
		pessoaExterna.setNome(request.getParameter("nome"));
		pessoaExterna.setEmail(request.getParameter("email"));
		pessoaExterna.setCpf(request.getParameter("cpf"));
		pessoaService.savePessoaExterna(pessoaExterna);
		return model.addAttribute("pessoaExterna", pessoaExterna);
	}
}
