package ufc.quixada.npi.gpa.observer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.MessagingException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import br.ufc.quixada.npi.model.Email;
import br.ufc.quixada.npi.service.EmailService;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.Evento;
import ufc.quixada.npi.gpa.service.Observer;
import ufc.quixada.npi.gpa.service.PessoaService;

public class EmailObserver implements Observer {

	@Inject
	private EmailService emailService;

	@Inject
	private PessoaService pessoaService;

	private static final String ASSUNTO = "email.assunto";
	private static final String CORPO_SUBMISSAO = "email.corpo.submissao";
	private static final String CORPO_ATRIBUICAO_PARECERISTA_COORDENADOR = "email.corpo.atribuicao_parecerista.coordenador";
	private static final String CORPO_ATRIBUICAO_PARECERISTA_PARECERISTA = "email.corpo.atribuicao_parecerista.parecerista";
	private static final String CORPO_ALTERACAO_PARECERISTA_PARECERISTA = "email.corpo.alteracao_parecerista.parecerista";
	private static final String CORPO_ALTERACAO_PARECERISTA_COORDENADOR = "email.corpo.alteracao_parecerista.coordenador";
	private static final String CORPO_ATRIBUICAO_PARECERISTA_DIRETOR = "email.corpo.atribuicao_parecerista.diretor";
	private static final String CORPO_EMISSAO_PARECER_COORDENADOR = "email.corpo.emissao_parecer.coordenador";
	private static final String CORPO_EMISSAO_PARECER_PARECERISTA = "email.corpo.emissao_parecer.parecerista";
	private static final String CORPO_EMISSAO_PARECER_DIRETOR = "email.corpo.emissao_parecer.diretor";
	private static final String CORPO_AVALIACAO_DIRETOR = "email.corpo.avaliacao";
	private static final String CORPO_RESOLUCAO_PENDENCIAS = "email.corpo.resolucao_pendencia";

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

			if (properties.getProperty("email.ativo").equals("true")) {
				final Evento eventoCopy = evento;
				final String emailDiretor = pessoaService.getDirecao().getEmail();
				final String emailCoordenador = projeto.getCoordenador().getEmail();
				final String emailParecerista = projeto.getParecer() != null ? projeto.getParecer().getParecerista().getEmail() : "";
				final String nomeCoordenador = projeto.getCoordenador().getNome();
				final String nomeParecerista = projeto.getParecer() != null ? projeto.getParecer().getParecerista().getNome() : "";
				final String nomeProjeto = new StringBuilder().append(projeto.getCodigo()).append(" - ") .append(projeto.getNome()).toString();
				final String subject = properties.getProperty(ASSUNTO).replace(NOME_PROJETO, nomeProjeto);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				final String prazo = projeto.getParecer() != null ? dateFormat.format(projeto.getParecer().getPrazo()) : "";

				Runnable enviarEmail = new Runnable() {
					@Override
					public void run() {
						String body = null;
						Email email = new Email();
						String emailGPA = "naoresponda@gpapesquisa.com";

						switch (eventoCopy) {
						case SUBMISSAO:
							body = properties.getProperty(CORPO_SUBMISSAO).replaceAll(NOME_PROJETO, nomeProjeto)
									.replaceAll(NOME_COORDENADOR, nomeCoordenador);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailCoordenador, emailDiretor);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							break;

						case ATRIBUICAO_PARECERISTA:
							body = properties.getProperty(CORPO_ATRIBUICAO_PARECERISTA_COORDENADOR)
									.replaceAll(NOME_PROJETO, nomeProjeto);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailCoordenador);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}

							body = properties.getProperty(CORPO_ATRIBUICAO_PARECERISTA_PARECERISTA)
									.replaceAll(NOME_PROJETO, nomeProjeto).replaceAll(PRAZO, prazo);

							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailParecerista);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}

							body = properties.getProperty(CORPO_ATRIBUICAO_PARECERISTA_DIRETOR)
									.replaceAll(NOME_PROJETO, nomeProjeto)
									.replaceAll(NOME_PARECERISTA, nomeParecerista);

							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailDiretor);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							break;

						case EMISSAO_PARECER:
							body = properties.getProperty(CORPO_EMISSAO_PARECER_COORDENADOR)
									.replaceAll(NOME_PROJETO, nomeProjeto)
									.replaceAll(NOME_PARECERISTA, nomeParecerista);

							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailCoordenador);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							body = properties.getProperty(CORPO_EMISSAO_PARECER_PARECERISTA).replaceAll(NOME_PROJETO,
									nomeProjeto);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailParecerista);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							body = properties.getProperty(CORPO_EMISSAO_PARECER_DIRETOR)
									.replaceAll(NOME_PROJETO, nomeProjeto)
									.replaceAll(NOME_PARECERISTA, nomeParecerista);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailDiretor);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							break;

						case AVALIACAO:
							body = properties.getProperty(CORPO_AVALIACAO_DIRETOR).replaceAll(NOME_PROJETO, nomeProjeto)
									.replaceAll(STATUS_AVALIACAO, "Status da avaliação");
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailDiretor, emailCoordenador);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							break;
						
						case ALTERACAO_PARECERISTA:
							body = properties.getProperty(CORPO_ALTERACAO_PARECERISTA_PARECERISTA).replaceAll(NOME_PROJETO, nomeProjeto);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailParecerista);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							
							body = properties.getProperty(CORPO_ALTERACAO_PARECERISTA_COORDENADOR).replaceAll(NOME_PROJETO, nomeProjeto);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailCoordenador);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							break;
						case RESOLUSAO_PENDENCIAS:
							body = properties.getProperty(CORPO_RESOLUCAO_PENDENCIAS).replaceAll(NOME_PROJETO, nomeProjeto);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailCoordenador);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {
								
							}
						}
					}
				};

				Thread threadEnviarEmail = new Thread(enviarEmail);
				threadEnviarEmail.start();
			}
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}
}