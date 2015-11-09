package ufc.quixada.npi.gpa.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gpa.service.AdministracaoService;
import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;

@Named
public class AdministracaoServiceImpl implements AdministracaoService{

	@Inject
	private UsuarioService usuarioService;

	@Override
	public Usuario getUsuariosByCpf(String cpf) {
		return usuarioService.getByCpf(cpf);
	}

	@Override
	public List<Usuario> getUsuariosByNomeOuCpf(String nome) {
		return usuarioService.getByCpfOrNome(nome);
	}

}
