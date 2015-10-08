package ufc.quixada.npi.gpa.service.impl;

import java.util.Calendar;

import javax.inject.Named;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ufc.quixada.npi.gpa.model.Participacao;

@Named
public class ParticipacaoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Participacao.class.equals(clazz);
	}

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


	private void validaMes(String campo, Integer valor, Errors errors) {
		if (valor == null) {
			ValidationUtils.rejectIfEmpty(errors, campo, "projeto.campoNulo");
		} else {
			if (valor < 1 || valor > 12) {
				errors.rejectValue(campo, "participacao.campoIntervaloMes");
			}
		}
	}

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
