package ufc.quixada.npi.gpa.service.impl;

import java.util.ArrayList;
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

	@Override
	public List<Usuario> removeUsuario(List<Usuario> usuarios, Usuario usuario) {
		List<Usuario> users = new ArrayList<Usuario>();
		for (Usuario usuarioLocal : usuarios) {
			if(usuarioLocal.getCpf()!= null){
				if(!usuarioLocal.getCpf().equals(usuario.getCpf())){
					users.add(usuarioLocal);
				}
			}
		}
		return users;
	}

}
