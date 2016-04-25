package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_ERRO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_ERRO_ATUALIZAR_PAPEIS;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_ERRO_PERSISTIR_USUARIO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_ERRO_VINCULAR_PAPEIS;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_FONTE_DE_FINANCIAMENTO_CADASTRADA;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_FONTE_DE_FINANCIAMENTO_INEXISTENTE;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_OK;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_SUCESSO_VINCULAR_PAPEIS;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_USUARIO_NAO_ENCONTRADO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_ADMINISTRACAO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_ADMINISTRACAO_FONTES_DE_FINANCIAMENTO;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_ADMINISTRACAO_VINCULAR_PAPEL;
import static ufc.quixada.npi.gpa.utils.Constants.PAPEL_COORDENACAO;
import static ufc.quixada.npi.gpa.utils.Constants.REDIRECT_PAGINA_ADMINISTRACAO_FONTES_DE_FINANCIAMENTO;
import static ufc.quixada.npi.gpa.utils.Constants.REDIRECT_PAGINA_INICIAL_ADMINISTRACAO;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.ldap.model.Usuario;
import ufc.quixada.npi.gpa.model.FonteFinanciamento;
import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.AdministracaoService;
import ufc.quixada.npi.gpa.service.FonteFinanciamentoService;
import ufc.quixada.npi.gpa.service.PapelService;
import ufc.quixada.npi.gpa.service.PessoaService;

@Controller
@RequestMapping("administracao")
public class AdministracaoController {

	@Inject
	private AdministracaoService administracaoService;

	@Inject
	private PessoaService pessoaService;

	@Inject
	private PapelService papelService;

	@Inject
	private FonteFinanciamentoService fonteFinanciamentoService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String paginaInicial(Model model) {
		List<Pessoa> pessoas = pessoaService.getAll();
		model.addAttribute("pessoas", pessoas);
		model.addAttribute("action", "buscar");
		return PAGINA_ADMINISTRACAO;
	}

	@RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public String buscarPessoa(@RequestParam("busca") String busca, ModelMap map, RedirectAttributes redirectAttributes,
			Authentication authentication) {

		map.addAttribute("busca", busca);

		List<Usuario> usuarios = administracaoService.getUsuariosByNomeOuCpf(busca);
		if (!usuarios.isEmpty()) {
			map.addAttribute("pessoas", usuarios);
			map.addAttribute("action", "resultado");
		} else {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_USUARIO_NAO_ENCONTRADO);
			return REDIRECT_PAGINA_INICIAL_ADMINISTRACAO;
		}
		return PAGINA_ADMINISTRACAO;
	}

	@RequestMapping(value = "/pessoa/vincular/{cpf}", method = RequestMethod.GET)
	public String vincularPapel(@PathVariable("cpf") String cpf, Model model, RedirectAttributes redirect,
			Authentication authentication) {
		Usuario usuario = administracaoService.getUsuariosByCpf(cpf);
		if (usuario == null) {
			redirect.addFlashAttribute("erro", MENSAGEM_USUARIO_NAO_ENCONTRADO);
			return REDIRECT_PAGINA_INICIAL_ADMINISTRACAO;
		} else {
			Pessoa pessoa = pessoaService.getPessoa(cpf);
			if (pessoa == null) {
				pessoa = new Pessoa();
				pessoa.setCpf(usuario.getCpf());
				Papel papel = papelService.find(PAPEL_COORDENACAO);
				pessoa.addPapel(papel);
				try {
					pessoaService.save(pessoa);
				} catch (Exception e) {
					redirect.addFlashAttribute("erro", MENSAGEM_ERRO_PERSISTIR_USUARIO);
					return REDIRECT_PAGINA_INICIAL_ADMINISTRACAO;
				}
				pessoa = pessoaService.getPessoa(pessoa.getId());
			}
			model.addAttribute("pessoa", pessoa);
			model.addAttribute("papeis", papelService.atualizaStatus(pessoa.getPapeis()));
		}
		return PAGINA_ADMINISTRACAO_VINCULAR_PAPEL;
	}

	@RequestMapping(value = "/pessoa/vincular", method = RequestMethod.POST)
	public String vincular(@ModelAttribute("pessoa") Pessoa pessoa, Model model, RedirectAttributes redirect,
			BindingResult result) {

		if (result.hasErrors()) {
			redirect.addFlashAttribute("erro", MENSAGEM_ERRO_VINCULAR_PAPEIS);
			return "redirect:/administracao/pessoa/vincular/" + pessoa.getCpf();
		}
		Pessoa oldPessoa = pessoaService.getPessoa(pessoa.getCpf());

		oldPessoa = pessoaService.vincularPapeis(pessoa, oldPessoa);

		try {
			pessoaService.update(oldPessoa);
		} catch (Exception e) {
			redirect.addFlashAttribute("erro", MENSAGEM_ERRO_ATUALIZAR_PAPEIS);
			return "redirect:/administracao/pessoa/vincular/" + pessoa.getCpf();
		}
		redirect.addFlashAttribute("info", MENSAGEM_SUCESSO_VINCULAR_PAPEIS);
		return "redirect:/administracao/pessoa/vincular/" + pessoa.getCpf();
	}

	@RequestMapping(value = "/fonte-financiamento/mostrar", method = RequestMethod.GET)
	public String mostrarFontesFinanciamento(ModelMap model) {
		List<FonteFinanciamento> fontesFinanciamento = fonteFinanciamentoService.getFontesFinanciamento();

		model.addAttribute("fonteFinanciamento", new FonteFinanciamento());
		model.addAttribute("fontesFinanciamento", fontesFinanciamento);

		return PAGINA_ADMINISTRACAO_FONTES_DE_FINANCIAMENTO;
	}

	@RequestMapping(value = "/fonte-financiamento/cadastrar", method = RequestMethod.POST)
	public String cadastrarFonteFinanciamento(
			@ModelAttribute("fonteFinanciamento") FonteFinanciamento fonteFinanciamento, RedirectAttributes redirect) {

		fonteFinanciamentoService.cadastrar(fonteFinanciamento);

		redirect.addFlashAttribute("info", MENSAGEM_FONTE_DE_FINANCIAMENTO_CADASTRADA);

		return REDIRECT_PAGINA_ADMINISTRACAO_FONTES_DE_FINANCIAMENTO;
	}
	
	@RequestMapping(value = "/fonte-financiamento/excluir/{id}", method = RequestMethod.POST)
	@ResponseBody public ModelMap excluirFonteFinanciamento(@PathVariable("id") Long fonteFinanciamentoId, @ModelAttribute ModelMap model) {
		FonteFinanciamento fonteFinanciamento = fonteFinanciamentoService.getFonteFinanciamento(fonteFinanciamentoId);
		
		if(fonteFinanciamento == null){
			model.addAttribute("result", MENSAGEM_ERRO);
			model.addAttribute("mensagem", MENSAGEM_FONTE_DE_FINANCIAMENTO_INEXISTENTE);
			return model;
		}
		
		fonteFinanciamentoService.remover(fonteFinanciamento);
		
		model.addAttribute("result", MENSAGEM_OK);
		
		return model;
	}
}
