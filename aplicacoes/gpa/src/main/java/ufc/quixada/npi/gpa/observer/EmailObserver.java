package ufc.quixada.npi.gpa.observer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.inject.Inject;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.Evento;
import ufc.quixada.npi.gpa.service.Observer;
import ufc.quixada.npi.gpa.service.UsuarioService;
import ufc.quixada.npi.gpa.service.impl.EmailService;


public class EmailObserver implements Observer {
	
	@Inject
	private EmailService emailService;
	
	@Inject
	private UsuarioService usuarioService;
	
	private static final String ASSUNTO = "email.assunto";
	private static final String CORPO_SUBMISSAO = "email.corpo.submissao";
	private static final String CORPO_ATRIBUICAO_PARECERISTA_COORDENADOR = "email.corpo.atribuicao_parecerista.coordenador";
	private static final String CORPO_ATRIBUICAO_PARECERISTA_PARECERISTA = "email.corpo.atribuicao_parecerista.parecerista";
	private static final String CORPO_ATRIBUICAO_PARECERISTA_DIRETOR = "email.corpo.atribuicao_parecerista.diretor";
	private static final String CORPO_EMISSAO_PARECER_COORDENADOR = "email.corpo.emissao_parecer.coordenador";
	private static final String CORPO_EMISSAO_PARECER_PARECERISTA = "email.corpo.emissao_parecer.parecerista";
	private static final String CORPO_EMISSAO_PARECER_DIRETOR = "email.corpo.emissao_parecer.diretor";
	private static final String CORPO_AVALIACAO_DIRETOR = "email.corpo.avaliacao";
	
	private static final String NOME_PROJETO = "#NOME_PROJETO#";
	private static final String NOME_PARECERISTA = "#NOME_PARECERISTA#";
	private static final String NOME_COORDENADOR = "#NOME_COORDENADOR#";
	private static final String PRAZO = "#PRAZO#";
	private static final String STATUS_AVALIACAO = "#STATUS_AVALIACAO#";

	@Override
	public void notificar(Projeto projeto, Evento evento) {
		try {
			Resource resource = new ClassPathResource("/notification.properties");
			final Properties properties = PropertiesLoaderUtils.loadProperties(resource);
			
			if(properties.getProperty("email.ativo").equals("true")) {
				final Evento eventoCopy = evento;
				final String emailDiretor = usuarioService.getDiretor().getEmail();
				final String emailCoordenador = projeto.getAutor().getEmail();
				final String emailParecerista = projeto.getParecer() != null ? 
						projeto.getParecer().getParecerista().getEmail() : "";
				final String nomeCoordenador = projeto.getAutor().getNome();
				final String nomeParecerista = projeto.getParecer() != null ? 
						projeto.getParecer().getParecerista().getNome() : "";
				final String nomeProjeto = new StringBuilder().append(projeto.getCodigo()).append(" - ").append(projeto.getNome()).toString();
				final String subject = properties.getProperty(ASSUNTO).replace(NOME_PROJETO, nomeProjeto);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				final String prazo = projeto.getParecer() != null ? 
						dateFormat.format(projeto.getParecer().getPrazo()) : "";
				
				Runnable enviarEmail = new Runnable() {
					
					@Override
					public void run() {
						switch (eventoCopy) {
						case SUBMISSAO:
							String body = properties.getProperty(CORPO_SUBMISSAO).replaceAll(NOME_PROJETO, nomeProjeto)
									.replaceAll(NOME_COORDENADOR, nomeCoordenador);
							emailService.sendMail(emailCoordenador, subject, body);
							emailService.sendMail(emailDiretor, subject, body);
							break;
						
						case ATRIBUICAO_PARECERISTA:
							body = properties.getProperty(CORPO_ATRIBUICAO_PARECERISTA_COORDENADOR).replaceAll(NOME_PROJETO, nomeProjeto);
							emailService.sendMail(emailCoordenador, subject, body);
							
							body = properties.getProperty(CORPO_ATRIBUICAO_PARECERISTA_PARECERISTA).replaceAll(NOME_PROJETO, nomeProjeto)
									.replaceAll(PRAZO, prazo);
							emailService.sendMail(emailParecerista, subject, body);
							
							body = properties.getProperty(CORPO_ATRIBUICAO_PARECERISTA_DIRETOR).replaceAll(NOME_PROJETO, nomeProjeto)
									.replaceAll(NOME_PARECERISTA, nomeParecerista);
							emailService.sendMail(emailDiretor, subject, body);
							
							break;
							
						case EMISSAO_PARECER:
							body = properties.getProperty(CORPO_EMISSAO_PARECER_COORDENADOR).replaceAll(NOME_PROJETO, nomeProjeto)
									.replaceAll(NOME_PARECERISTA, nomeParecerista);
							emailService.sendMail(emailCoordenador, subject, body);
							
							body = properties.getProperty(CORPO_EMISSAO_PARECER_PARECERISTA).replaceAll(NOME_PROJETO, nomeProjeto);
							emailService.sendMail(emailParecerista, subject, body);
							
							body = properties.getProperty(CORPO_EMISSAO_PARECER_DIRETOR).replaceAll(NOME_PROJETO, nomeProjeto)
									.replaceAll(NOME_PARECERISTA, nomeParecerista);
							emailService.sendMail(emailDiretor, subject, body);
							
							break;
							
						case AVALIACAO:
							body = properties.getProperty(CORPO_AVALIACAO_DIRETOR).replaceAll(NOME_PROJETO, nomeProjeto)
									.replaceAll(STATUS_AVALIACAO, "Status da avaliação");
							emailService.sendMail(emailCoordenador, subject, body);
							emailService.sendMail(emailDiretor, subject, body);
							break;
					}
						
					}
				};
				
				Thread threadEnviarEmail = new Thread(enviarEmail);
				threadEnviarEmail.start();
				
			}
		} catch(IOException ex) {
			
		}
		
	}

}
