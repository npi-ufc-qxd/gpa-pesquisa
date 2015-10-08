package ufc.quixada.npi.gpa.service.impl;

import java.util.Calendar;

import javax.inject.Named;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Projeto;

/**
 * Validação da entidade {@link Participacao} com a interface {@link Validator}.<br>
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
	 * Valida formulário de cadastro de {@link Participacao}
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
	}


	/**
	 * Valida se o campo Mês é nulo e se o valor informado {@link Integer} está entre os intervalos possíveis do meses no ano (1/12).
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
	private void checaIntervaloDadas(String inicioMes, String inicioAno, String terminoMes, String terminoAno, Participacao participacao, Errors errors) {
		if (participacao.getMesInicio() == null || participacao.getAnoInicio() == null || participacao.getMesTermino() == null || participacao.getAnoTermino() == null) {
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
}
