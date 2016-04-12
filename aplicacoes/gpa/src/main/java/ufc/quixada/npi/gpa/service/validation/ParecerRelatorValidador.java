package ufc.quixada.npi.gpa.service.validation;

import javax.inject.Named;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ufc.quixada.npi.gpa.model.ParecerRelator;
import ufc.quixada.npi.gpa.model.ParecerTecnico;

@Named
public class ParecerRelatorValidador implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		
		return ParecerRelator.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//TODO
	}
	
	public void validateAtribuirRelator(Object target, Errors errors){
		
		ValidationUtils.rejectIfEmpty(errors, "relator", "projeto.campoNulo");
	}
}
