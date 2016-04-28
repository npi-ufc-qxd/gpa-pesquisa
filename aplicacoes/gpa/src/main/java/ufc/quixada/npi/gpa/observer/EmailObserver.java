package ufc.quixada.npi.gpa.observer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.MessagingException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import br.ufc.quixada.npi.model.Email;
import br.ufc.quixada.npi.service.EmailService;
import ufc.quixada.npi.gpa.model.Pessoa;
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
	private static final String CORPO_ATRIBUICAO_PARECERISTA_DIRETOR = "email.corpo.atribuicao_parecerista.diretor";

	private static final String CORPO_ALTERACAO_PARECERISTA_PARECERISTA = "email.corpo.alteracao_parecerista.parecerista";
	private static final String CORPO_ALTERACAO_PARECERISTA_COORDENADOR = "email.corpo.alteracao_parecerista.coordenador";
	private static final String CORPO_ALTERACAO_PARECERISTA_DIRETOR = "email.corpo.alteracao_parecerista.diretor";

	private static final String CORPO_EMISSAO_PARECER_COORDENADOR = "email.corpo.emissao_parecer.coordenador";
	private static final String CORPO_EMISSAO_PARECER_PARECERISTA = "email.corpo.emissao_parecer.parecerista";
	private static final String CORPO_EMISSAO_PARECER_DIRETOR = "email.corpo.emissao_parecer.diretor";

	private static final String CORPO_ATRIBUICAO_RELATOR_RELATOR = "email.corpo.atribuicao_relator_relator";
	private static final String CORPO_ATRIBUICAO_RELATOR_COORDENADOR = "email.corpo.atribuicao_relator_coordenador";
	private static final String CORPO_ATRIBUICAO_RELATOR_DIRETOR = "email.corpo.atribuicao_relator_diretor";

	private static final String CORPO_ALTERACAO_RELATOR_RELATOR = "email.corpo.alteracao_relator_relator";
	private static final String CORPO_ALTERACAO_RELATOR_COORDENADOR = "email.corpo.alteracao_relator_coordenador";
	private static final String CORPO_ALTERACAO_RELATOR_DIRETOR = "email.corpo.alteracao_relator_diretor";

	private static final String CORPO_AVALIACAO_RELATOR_RELATOR = "email.corpo.avaliacao_relator_relator";
	private static final String CORPO_AVALIACAO_RELATOR_COORDENADOR = "email.corpo.avaliacao_relator_coordenador";
	private static final String CORPO_AVALIACAO_RELATOR_DIRETOR = "email.corpo.avaliacao_relator_diretor";

	private static final String CORPO_HOMOLOGACAO = "email.corpo.homologacao";

	private static final String CORPO_RESOLUCAO_PENDENCIAS = "email.corpo.resolucao_pendencia";
	private static final String CORPO_SUBMISSAO_RESOLUCAO_PENDENCIAS = "email.corpo.submissao_resolucao_pendencia";

	private static final String CORPO_RESOLUCAO_RESTRICAO = "email.corpo.resolucao_restricao";
	private static final String CORPO_SUBMISSAO_RESOLUCAO_RESTRICAO = "email.corpo.submissao_resolucao_restricao";

	private static final String NOME_PROJETO = "#NOME_PROJETO#";
	private static final String NOME_PARECERISTA = "#NOME_PARECERISTA#";
	private static final String NOME_COORDENADOR = "#NOME_COORDENADOR#";
	private static final String NOME_RELATOR = "#NOME_RELATOR#";
	private static final String PRAZO = "#PRAZO#";
	private static final String STATUS_HOMOLOGACAO = "#STATUS_HOMOLOGACAO#";

	@Override
	public void notificar(Projeto projeto, Evento evento, Pessoa pessoa) {
		try {
			Resource resource = new ClassPathResource("/notification.properties");
			final Properties properties = PropertiesLoaderUtils.loadProperties(resource);

			if (properties.getProperty("email.ativo").equals("true")) {
				final Evento eventoCopy = evento;
				final String emailCoordenador = projeto.getCoordenador().getEmail();
				final String emailParecerista = projeto.getParecer() != null ? projeto.getParecer().getParecerista().getEmail() : "";
				final String emailRelator = projeto.getParecerRelator() != null ? projeto.getParecerRelator().getRelator().getEmail() : "";
				final String nomeCoordenador = projeto.getCoordenador().getNome();
				final String nomeParecerista = projeto.getParecer() != null ? projeto.getParecer().getParecerista().getNome() : "";
				final String nomeRelator = projeto.getParecerRelator() != null ? projeto.getParecerRelator().getRelator().getNome() : "";
				final String nomeProjeto = new StringBuilder().append(projeto.getCodigo()).append(" - ").append(projeto.getNome()).toString();
				final String subject = properties.getProperty(ASSUNTO).replace(NOME_PROJETO, nomeProjeto);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				final String prazo = projeto.getParecer() != null ? dateFormat.format(projeto.getParecer().getPrazo()) : "";
				final String statusProjeto = projeto.getStatus().getDescricao();

				Runnable enviarEmail = new Runnable() {
					@Override
					public void run() {
						String body = null;
						Email email = new Email();
						String emailGPA = "naoresponda@gpapesquisa.com";
						boolean emailDiretores = false;

						switch (eventoCopy) {
						case SUBMISSAO:
							body = properties.getProperty(CORPO_SUBMISSAO).replaceAll(NOME_PROJETO, nomeProjeto)
									.replaceAll(NOME_COORDENADOR, nomeCoordenador);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailCoordenador);
							emailDiretores = true;
							
							break;

						case ATRIBUICAO_PARECERISTA:
							body = properties.getProperty(CORPO_ATRIBUICAO_PARECERISTA_COORDENADOR)
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
							emailDiretores = true;
							
							break;

						case ALTERACAO_PARECERISTA:
							body = properties.getProperty(CORPO_ALTERACAO_PARECERISTA_PARECERISTA)
									.replaceAll(NOME_PROJETO, nomeProjeto);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailParecerista);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							body = properties.getProperty(CORPO_ALTERACAO_PARECERISTA_COORDENADOR)
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
							body = properties.getProperty(CORPO_ALTERACAO_PARECERISTA_DIRETOR)
									.replaceAll(NOME_PROJETO, nomeProjeto).replaceAll(NOME_PARECERISTA, nomeParecerista)
									.replaceAll(PRAZO, prazo);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							emailDiretores = true;
							
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
							emailDiretores = true;
							
							break;

						case ATRIBUICAO_RELATOR:
							body = properties.getProperty(CORPO_ATRIBUICAO_RELATOR_RELATOR).replaceAll(NOME_PROJETO,
									nomeProjeto);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailRelator);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							body = properties.getProperty(CORPO_ATRIBUICAO_RELATOR_COORDENADOR)
									.replaceAll(NOME_PROJETO, nomeProjeto).replaceAll(NOME_RELATOR, nomeRelator);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailCoordenador);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							body = properties.getProperty(CORPO_ATRIBUICAO_RELATOR_DIRETOR)
									.replaceAll(NOME_PROJETO, nomeProjeto).replaceAll(NOME_RELATOR, nomeRelator);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							emailDiretores = true;
							
							break;

						case ALTERACAO_RELATOR:
							body = properties.getProperty(CORPO_ALTERACAO_RELATOR_RELATOR).replaceAll(NOME_PROJETO,
									nomeProjeto);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailRelator);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							body = properties.getProperty(CORPO_ALTERACAO_RELATOR_COORDENADOR)
									.replaceAll(NOME_PROJETO, nomeProjeto).replaceAll(NOME_RELATOR, nomeRelator);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailCoordenador);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							body = properties.getProperty(CORPO_ALTERACAO_RELATOR_DIRETOR)
									.replaceAll(NOME_PROJETO, nomeProjeto).replaceAll(NOME_RELATOR, nomeRelator);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							emailDiretores = true;
							
							break;

						case EMISSAO_AVALIACAO_RELATOR:
							body = properties.getProperty(CORPO_AVALIACAO_RELATOR_RELATOR).replaceAll(NOME_PROJETO,
									nomeProjeto);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailRelator);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							body = properties.getProperty(CORPO_AVALIACAO_RELATOR_COORDENADOR)
									.replaceAll(NOME_PROJETO, nomeProjeto).replaceAll(NOME_RELATOR, nomeRelator);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailCoordenador);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							body = properties.getProperty(CORPO_AVALIACAO_RELATOR_DIRETOR)
									.replaceAll(NOME_PROJETO, nomeProjeto).replaceAll(NOME_RELATOR, nomeRelator);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							emailDiretores = true;
							break;

						case HOMOLOGACAO:
							body = properties.getProperty(CORPO_HOMOLOGACAO)
								.replaceAll(NOME_PROJETO, nomeProjeto)
								.replaceAll(STATUS_HOMOLOGACAO, statusProjeto);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailCoordenador);
							emailDiretores = true;
							
							break;

						case RESOLUCAO_PENDENCIAS:
							body = properties.getProperty(CORPO_RESOLUCAO_PENDENCIAS).replaceAll(NOME_PROJETO,
									nomeProjeto);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailCoordenador);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							break;

						case SUBMISSAO_RESOLUCAO_PENDENCIAS:
							body = properties.getProperty(CORPO_SUBMISSAO_RESOLUCAO_PENDENCIAS)
									.replaceAll(NOME_PROJETO, nomeProjeto)
									.replaceAll(NOME_COORDENADOR, nomeCoordenador);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailParecerista);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							break;

						case RESOLUCAO_RESTRICAO:
							body = properties.getProperty(CORPO_RESOLUCAO_RESTRICAO).replaceAll(NOME_PROJETO,
									nomeProjeto);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailCoordenador);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							break;

						case SUBMISSAO_RESOLUCAO_RESTRICAO:
							body = properties.getProperty(CORPO_SUBMISSAO_RESOLUCAO_RESTRICAO).replaceAll(NOME_PROJETO,
									nomeProjeto).replaceAll(NOME_COORDENADOR, nomeCoordenador);
							email.setFrom(emailGPA);
							email.setSubject(subject);
							email.setText(body);
							email.setTo(emailParecerista);
							try {
								emailService.sendEmail(email);
							} catch (MessagingException e) {

							}
							break;

						}
						
						if(emailDiretores) {
							List<Pessoa> diretores = pessoaService.getAllDirecao();
							if(!diretores.isEmpty()) {
								for(Pessoa diretor : diretores) {
									email.setTo(diretor.getEmail());
								}
								try {
									emailService.sendEmail(email);
								} catch (MessagingException e) {
								}
							}
						}
					}
				};

				Thread threadEnviarEmail = new Thread(enviarEmail);
				threadEnviarEmail.start();
			}
		} catch (IOException ex) {
		}
	}
}