package ufc.quixada.npi.gpa.service;

import java.util.List;

import br.ufc.quixada.npi.ldap.model.Usuario;

public interface AdministracaoService {
	
	public abstract Usuario getUsuariosByCpf(String cpf);

	public abstract List<Usuario> getUsuariosByNomeOuCpf(String nome);

	public abstract List<Usuario> removeUsuario(List<Usuario> usuarios,
			Usuario usuario);
}
