package ufc.quixada.npi.gpa.service.impl;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ufc.quixada.npi.gpa.model.Projeto;

/**
 * Validação da classe {@link Projeto} com a interface {@link Validator}.<br>
 * *Mensagens de validação com suporte a internacionalização em "resources/WEB-INF/messages..."
 * 
 * @author 00056726198
 */
public class ProjetoValidator implements Validator {

	/**
	 * Valida apenas instâncias de {@link Projeto}.
	 * @param classe {@link Class}
	 * @return {@link Boolean}
	 */
	@Override
	public boolean supports(Class<?> classe) {
		return Projeto.class.equals(classe);
	}
	
	@Override
	public void validate(Object target, Errors errors) {		
		Projeto p = (Projeto) target;
		
		validateCadastro(p, errors);
	}	
	
	/**
	 * Valida formulário de cadastro de {@link Projeto}
	 * @param projeto {@link Projeto}
	 * @param errors {@link Errors}
	 */
	protected void validateCadastro(Projeto projeto, Errors errors){
		validaCampoMin("nome", projeto.getNome(), 2, errors);
		validaCampoMin("descricao", projeto.getDescricao(), 5, errors);
	}
	
	
	/**
	 * Valida submissão do formulário de {@link Projeto}.
	 * @param projeto {@link Projeto}
	 * @param errors {@link Errors}
	 */
	public void validateSubmissao(Projeto projeto, Errors errors){
		validaCampoMin("nome", projeto.getNome(), 2, errors);
		validaCampoMin("descricao", projeto.getDescricao(), 5, errors);
		
		validaCampoObrigatorio("inicio", errors);
		validaCampoObrigatorio("termino", errors);
		validaCampoObrigatorio("cargaHoraria", errors);
		validaCampoObrigatorio("local", errors);
		validaCampoObrigatorio("atividades", errors);
		validaCampoObrigatorio("atividades", errors);
		validaCampoObrigatorio("documentos", errors);
	}
	
	
	/**
	 * Regra de validação para campos com as seguintes restrições:<br>
	 * - Não nulo, Mínimo de {@link Integer} caracteres.
	 * 
	 * @param campo {@link String} Campo do formulário.
	 * @param valor {@link String} Valor do campo do formulário.
	 * @param min {@link Integer} Número mínimo de caracteres.
	 * @param errors {@link Errors} Erros de validação.
	 */
	private void validaCampoMin(String campo, String valor, Integer min, Errors errors){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, campo, "projeto.campoNulo");
		
		if(valor.length() < min){
			errors.rejectValue(campo, "projeto.campoMinimo", new Object[]{min}, "projeto.campoMinimo");
		}
	}
	
	
	/**
	 * Regra de validação para campos obrigatórios.
	 * 
	 * @param campo {@link String} Campo do formulário.
	 * @param errors {@link Errors} Erros de validação.
	 */
	private void validaCampoObrigatorio(String campo, Errors errors){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, campo, "projeto.campoNulo");
	}
}
