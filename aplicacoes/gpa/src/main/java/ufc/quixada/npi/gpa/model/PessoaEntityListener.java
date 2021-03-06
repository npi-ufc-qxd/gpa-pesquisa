package ufc.quixada.npi.gpa.model;

import javax.persistence.PostLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;

public class PessoaEntityListener implements ApplicationContextAware {
	
	private static ApplicationContext context;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostLoad
	public void loadProfessor(Pessoa pessoa) {
		context.getAutowireCapableBeanFactory().autowireBean(this);
		
		Usuario usuario = usuarioService.getByCpf(pessoa.getCpf());
		pessoa.setNome(usuario.getNome());
		pessoa.setEmail(usuario.getEmail());
	}
	
	public ApplicationContext getApplicationContext() {
        return context;
    }
 
    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }

}
