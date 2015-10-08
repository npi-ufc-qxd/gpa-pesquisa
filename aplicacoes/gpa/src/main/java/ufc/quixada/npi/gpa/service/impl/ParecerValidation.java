package ufc.quixada.npi.gpa.service.impl;

import javax.inject.Named;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ufc.quixada.npi.gpa.model.Parecer;

/**
 * Validação da classe {@link Parecer} com a interface {@link Validator}.<br>
 * *Mensagens de validação com suporte a internacionalização em
 * "resources/WEB-INF/messages..."
 * 
 * @author 00056726198
 */
@Named
public class ParecerValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Parecer.class.equals(clazz);
	}

	/**
	 * Valida formulário de emissão de parecer.
	 * 
	 * @param target {@link Parecer}
	 * @param errors {@link Errors}
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Parecer p = (Parecer) target;

		validaCampoMin("parecer", p.getParecer(), 5, errors);
	}

	/**
	 * Regra de validação para campos com as seguintes restrições:<br>
	 * - Não nulo, Mínimo de {@link Integer} caracteres.
	 * 
	 * @param campo {@link String} Campo do formulário.
	 * @param valor {@link String} Valor do campo do formulário.
	 * @param min {@link Integer} Número mínimo de caracteres.
	 * @param error {@link Errors} Erros de validação.
	 */
	private void validaCampoMin(String campo, String valor, Integer min, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, campo, "projeto.campoNulo");

		if (valor.length() < min) {
			errors.rejectValue(campo, "projeto.campoMinimo", new Object[]{min}, "projeto.campoMinimo");
		}
	}
}
