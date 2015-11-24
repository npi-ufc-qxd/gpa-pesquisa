package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.utils.Constants.*;
import static ufc.quixada.npi.gpa.utils.Constants.PAGINA_ADMINISTRACAO_VINCULAR_PAPEL;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.AdministracaoService;
import ufc.quixada.npi.gpa.service.PapelService;
import ufc.quixada.npi.gpa.service.PessoaService;
import br.ufc.quixada.npi.ldap.model.Usuario;

@Controller
@RequestMapping("administracao")
public class AdministracaoController {
	
	@Inject
	private AdministracaoService administracaoService;
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private PapelService papelService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String paginaInicial(Model model) {
		return PAGINA_ADMINISTRACAO;
	}
	
	@RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public String buscarPessoa(@RequestParam("busca") String busca, ModelMap map,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		
		map.addAttribute("busca", busca);
		
		List<Usuario> usuarios = administracaoService.getUsuariosByNomeOuCpf(busca);
		if (!usuarios.isEmpty()) {
			map.addAttribute("pessoas", usuarios);
		} else {
			redirectAttributes.addFlashAttribute("erro", MENSAGEM_USUARIO_NAO_ENCONTRADO);
			return REDIRECT_PAGINA_INICIAL_ADMINISTRACAO;
		}
		return PAGINA_ADMINISTRACAO;
	}
	
	@RequestMapping(value = "/pessoa/vincular/{cpf}", method = RequestMethod.GET)
	public String vincularPapel(@PathVariable("cpf") String cpf, Model model,
			RedirectAttributes redirect, Authentication authentication) {
		Usuario usuario = administracaoService.getUsuariosByCpf(cpf);
		if(usuario == null){
			redirect.addFlashAttribute("erro", MENSAGEM_USUARIO_NAO_ENCONTRADO);
			return REDIRECT_PAGINA_INICIAL_ADMINISTRACAO;
		}else{
			Pessoa pessoa = pessoaService.getPessoa(cpf);
			if(pessoa == null){
				pessoa = new Pessoa();
				pessoa.setCpf(usuario.getCpf());
				Papel papel = papelService.find(PAPEL_COORDENACAO);
				pessoa.addPapel(papel);
				try{
					pessoaService.save(pessoa);
				}catch(Exception e){
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
	public String vincular(@ModelAttribute("pessoa") Pessoa pessoa, Model model,
			RedirectAttributes redirect, BindingResult result) {
		
		if(result.hasErrors()){
			redirect.addFlashAttribute("erro", MENSAGEM_ERRO_VINCULAR_PAPEIS);
			return "redirect:/administracao/pessoa/vincular/"+pessoa.getCpf();
		}
		Pessoa oldPessoa = pessoaService.getPessoa(pessoa.getCpf());
		
		oldPessoa = pessoaService.vincularPapeis(pessoa, oldPessoa);
		
		try {
			pessoaService.update(oldPessoa);	
		} catch (Exception e) {
			redirect.addFlashAttribute("erro", MENSAGEM_ERRO_ATUALIZAR_PAPEIS);
			return "redirect:/administracao/pessoa/vincular/"+pessoa.getCpf();
		}
		redirect.addFlashAttribute("info", MENSAGEM_SUCESSO_VINCULAR_PAPEIS);
		return "redirect:/administracao/pessoa/vincular/"+pessoa.getCpf();
	}
}
