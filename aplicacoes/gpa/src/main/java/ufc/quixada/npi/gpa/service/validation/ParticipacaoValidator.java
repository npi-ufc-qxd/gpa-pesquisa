package ufc.quixada.npi.gpa.service.validation;

import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;

/**
 * Validação da entidade {@link Participacao} com a interface {@link Validator}. <br>
 * *Mensagens de validação com suporte a internacionalização em "resources/WEB-INF/messages..."
 * 
 * @author 00056726198
 */
@Named
public class ParticipacaoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Participacao.class.equals(clazz);
	}

	/**
	 * Valida cadastro de {@link Participacao}
	 * 
	 * @param projeto {@link Participacao}
	 * @param errors {@link Errors}
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Participacao p = (Participacao) target;

		validaMes("mesInicio", p.getMesInicio(), errors);
		validaMes("mesTermino", p.getMesTermino(), errors);

		validaAno("anoInicio", p.getAnoInicio(), errors);
		validaAno("anoTermino", p.getAnoTermino(), errors);

		checaIntervaloDadas("mesInicio", "anoInicio", "mesTermino", "anoTermino", p, errors);

		ValidationUtils.rejectIfEmpty(errors, "cargaHorariaMensal", "projeto.campoNulo");
		ValidationUtils.rejectIfEmpty(errors, "bolsaValorMensal", "projeto.campoNulo");

		// Desenvolvimento #2368
		verificaParticipacaoDentroLimiteProjeto(p, errors);
		verificaIntervalosParticipacaoPessoa(p, errors);
	}

	/**
	 * Valida se o campo Mês é nulo e se o valor informado {@link Integer} está entre os intervalos possíveis do meses
	 * no ano (1/12).
	 * 
	 * @param campo {@link String}
	 * @param valor {@link Integer}
	 * @param errors {@link Errors}
	 */
	private void validaMes(String campo, Integer valor, Errors errors) {
		if (valor == null) {
			ValidationUtils.rejectIfEmpty(errors, campo, "projeto.campoNulo");
		} else {
			if (valor < 1 || valor > 12) {
				errors.rejectValue(campo, "participacao.campoIntervaloMes");
			}
		}
	}

	/**
	 * Valida se campo Ano é Nulo e se o valor informado é inferior ao ano atual do {@link Calendar}.
	 * 
	 * @param campo {@link String}
	 * @param valor {@link Integer}
	 * @param errors {@link Errors}
	 */
	private void validaAno(String campo, Integer valor, Errors errors) {
		if (valor == null) {
			ValidationUtils.rejectIfEmpty(errors, campo, "projeto.campoNulo");
		} else {
			int ano = Calendar.getInstance().get(Calendar.YEAR);
			if (valor < ano) {
				errors.rejectValue(campo, "participacao.campoIntervaloAno");
			}
		}
	}

	/**
	 * Valida intervalo entre (Mês/Ano) informados como {@link Integer}.
	 * 
	 * @param inicioMes {@link String}
	 * @param inicioAno {@link String}
	 * @param terminoMes {@link String}
	 * @param terminoAno {@link String}
	 * @param participacao {@link Participacao}
	 * @param errors {@link Errors}
	 */
	private void checaIntervaloDadas(String inicioMes, String inicioAno, String terminoMes, String terminoAno,
			Participacao participacao, Errors errors) {
		if (participacao.getMesInicio() == null || participacao.getAnoInicio() == null
				|| participacao.getMesTermino() == null || participacao.getAnoTermino() == null) {
			return;
		}

		if (participacao.getAnoInicio().intValue() > participacao.getAnoTermino().intValue()) {
			errors.rejectValue(inicioAno, "participacao.campoAnoInferior");
			errors.rejectValue(terminoAno, "participacao.campoAnoSuperior");
		} else if (participacao.getAnoInicio().equals(participacao.getAnoTermino())) {
			if (participacao.getMesInicio().intValue() > participacao.getMesTermino().intValue()) {
				errors.rejectValue(inicioMes, "participacao.campoMesInferior");
				errors.rejectValue(terminoMes, "participacao.campoMesSuperior");
			}
		}
	}

	/**
	 * Valida intervalo de participação dentro dos limites do projeto.
	 * 
	 * @param participacao {@link Participacao}
	 * @param errors {@link Errors}
	 */
	private void verificaParticipacaoDentroLimiteProjeto(Participacao participacao, Errors errors) {
		Date inicioProjeto = participacao.getProjeto().getInicio();
		Date terminoProjeto = participacao.getProjeto().getTermino();

		if (inicioProjeto == null || terminoProjeto == null) {
			errors.reject("participacao.definirInicioTermino", "participacao.definirInicioTermino");
		} else {
			Calendar inicioProjetoCal = Calendar.getInstance();
			Calendar terminoProjetoCal = Calendar.getInstance();
			inicioProjetoCal.setTime(inicioProjeto);
			terminoProjetoCal.setTime(terminoProjeto);

			YearMonth inicioParticipa = YearMonth.of(participacao.getAnoInicio(), participacao.getMesInicio());
			YearMonth terminoParticipa = YearMonth.of(participacao.getAnoTermino(), participacao.getMesTermino());

			// Atenção: Calendar.MONTH, indice começa em 0.
			YearMonth inicioProj = YearMonth.of(inicioProjetoCal.get(Calendar.YEAR),
					(inicioProjetoCal.get(Calendar.MONTH) + 1));
			YearMonth terminoProj = YearMonth.of(terminoProjetoCal.get(Calendar.YEAR),
					(terminoProjetoCal.get(Calendar.MONTH) + 1));

			// Inicio da participação não está entre inicio do projeto
			if (!inicioParticipa.isAfter(inicioProj)) {
				if (!inicioParticipa.equals(inicioProj)) {
					errors.rejectValue("mesInicio", "participacao.campoInicioAntesInicioProjeto");
				}
			} else if (!inicioParticipa.isBefore(terminoProj)) {
				if (!inicioParticipa.equals(terminoProj)) {
					errors.rejectValue("mesInicio", "participacao.campoInicioAntesInicioProjeto");
				}
			}

			// Termino da participação não está entre termino do projeto
			if (!terminoParticipa.isBefore(terminoProj)) {
				if (!terminoParticipa.equals(terminoProj)) {
					errors.rejectValue("mesTermino", "participacao.campoTerminoPosteriorTerminoProjeto");
				}
			} else if (!terminoParticipa.isAfter(inicioProj)) {
				if (!terminoParticipa.equals(inicioProj)) {
					errors.rejectValue("mesTermino", "participacao.campoTerminoPosteriorTerminoProjeto");
				}
			}
		}
	}

	/**
	 * Valida intervalos de participação por pessoa, não permitindo intercessão.
	 * 
	 * @param participacao {@link Participacao}
	 * @param errors {@link Errors}
	 */
	private void verificaIntervalosParticipacaoPessoa(Participacao participacao, Errors errors) {
		List<Participacao> participacoes = participacao.getProjeto().getParticipacoes();

		// Participação atual
		Pessoa participanteAtual = participacao.getParticipante();
		YearMonth inicioPartAtual = YearMonth.of(participacao.getAnoInicio(), participacao.getMesInicio());
		YearMonth terminoPartAtual = YearMonth.of(participacao.getAnoTermino(), participacao.getMesTermino());

		for (Participacao part : participacoes) {
			if (participanteAtual.equals(part.getParticipante())) {
				YearMonth inicio = YearMonth.of(part.getAnoInicio(), part.getMesInicio());
				YearMonth termino = YearMonth.of(part.getAnoTermino(), part.getMesTermino());

				if (inicioPartAtual.compareTo(termino) >= 0) {
					// Verifica se a o inicioPartAtual é maior que o termino registrado
					// Assim é possível dividir uma participação em 2 etapas, antes e depois do registrado.
					if (inicioPartAtual.compareTo(termino) > 0) {
						// pode cadastrar
					} else {
						errors.rejectValue("mesInicio", "participacao.validaIntervaloInicio");
					}
				} else {
					// Verifica se o termino da participação atual é maior que o inicio cadastradas
					if (terminoPartAtual.compareTo(inicio) < 0) {
						// pode cadastrar
					} else {
						errors.rejectValue("mesTermino", "participacao.validaIntervaloInicio");
					}
				}
			}
		}
	}
}
