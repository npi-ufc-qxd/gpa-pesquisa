
package ufc.quixada.npi.gpa.service.validation;

import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Projeto;

/**
 * Validação da classe {@link Projeto} com a interface {@link Validator}.<br>
 * *Mensagens de validação com suporte a internacionalização em "resources/WEB-INF/messages..."
 * 
 * @author 00056726198
 */
@Named
public class ProjetoValidator implements Validator {

	/**
	 * Valida apenas instâncias de {@link Projeto}.
	 * 
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
	 * 
	 * @param projeto {@link Projeto}
	 * @param errors {@link Errors}
	 */
	protected void validateCadastro(Projeto projeto, Errors errors) {
		validaCampoMin("nome", projeto.getNome(), 2, errors);
		validaCampoMin("descricao", projeto.getDescricao(), 5, errors);

		Map<String, Date> datas = new HashMap<>();
		datas.put("inicio", projeto.getInicio());
		datas.put("termino", projeto.getTermino());
		validaCampoData(datas, true, errors);
		
		validaDataParticipacoes(projeto, errors);
	}


	/**
	 * Valida submissão do formulário de {@link Projeto}.
	 * 
	 * @param projeto {@link Projeto}
	 * @param errors {@link Errors}
	 */
	public void validateSubmissao(Projeto projeto, Errors errors) {
		validaCampoMin("nome", projeto.getNome(), 2, errors);
		validaCampoMin("descricao", projeto.getDescricao(), 5, errors);

		validaCampoObrigatorio("local", errors);
		validaCampoObrigatorio("atividades", errors);
		validaCampoObrigatorio("documentos", errors);
		validaCampoObrigatorio("arquivoProjeto", errors);
		validaCampoObrigatorio("valorProjeto", errors);

		Map<String, Date> datas = new HashMap<>();
		datas.put("inicio", projeto.getInicio());
		datas.put("termino", projeto.getTermino());
		validaCampoData(datas, false, errors);
		
		validaValorProjeto(projeto, errors);
		validaFontesFinanciamento(projeto, errors);

		validaParticipacaoCoordenador(projeto, errors);
	}

	private void validaFontesFinanciamento(Projeto projeto, Errors errors) {
		if(projeto.getFontesFinanciamento().isEmpty()) {
			errors.rejectValue("fontesFinanciamento", "projeto.campoNulo");
		}
	}

	/**
	 * Valida submissão do formulário de Avaliação.
	 * 
	 * @param target
	 * @param errors
	 */
	public void validateHomologacao(Object target, Errors errors) {
		Projeto p = (Projeto) target;

		if (p.getAta() == null)
			errors.rejectValue("ata", "projeto.campoNulo");
		if (p.getOficio() == null)
			errors.rejectValue("oficio", "projeto.campoNulo");
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
	private void validaCampoMin(String campo, String valor, Integer min, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, campo, "projeto.campoNulo");

		if (valor.length() < min) {
			errors.rejectValue(campo, "projeto.campoMinimo", new Object[] { min }, "projeto.campoMinimo");
		}
	}

	/**
	 * Regra de validação para campos obrigatórios.
	 * 
	 * @param campo {@link String} Campo do formulário.
	 * @param errors {@link Errors} Erros de validação.
	 */
	private void validaCampoObrigatorio(String campo, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, campo, "projeto.campoNulo");
	}

	/**
	 * Valida campos de Início e Término.<br>
	 * - ByPass em campos nulos, Valida se a data de Início antecede a data de Término.
	 * 
	 * @param campos {@link HashMap} Index[0] = Inicio, Index[1] = Término.
	 * @param nullPass {@link Boolean} True = Permite campos nulos, False = Valida campos nulos.
	 * @param errors {@link Errors}
	 */
	private void validaCampoData(Map<String, Date> campos, Boolean nullPass, Errors errors) {
		if (!nullPass) {
			if (campos.get("inicio") == null) {
				ValidationUtils.rejectIfEmpty(errors, campos.keySet().toArray()[0].toString(), "projeto.campoNulo");
			}
			if (campos.get("termino") == null) {
				ValidationUtils.rejectIfEmpty(errors, campos.keySet().toArray()[1].toString(), "projeto.campoNulo");
			}
		}

		try {
			Date dataInicio = campos.get("inicio");
			Date dataTermino = campos.get("termino");

			if (dataInicio.after(dataTermino)) {
				errors.rejectValue(campos.keySet().toArray()[0].toString(), "projeto.campoDataAntecedente");
			}
			if (dataInicio.equals(dataTermino)) {
				errors.rejectValue(campos.keySet().toArray()[0].toString(), "projeto.campoDataIguais");
			}
		} catch (Exception e) {
			System.out.println("Datas == null, " + e);
		}
	}

	//Validando se o valor do projeto é maior que o somatório das bolsas dos participantes
	private void validaValorProjeto(Projeto projeto, Errors errors) {
		if(projeto.getValorProjeto() != null) {
			float valorProjeto = projeto.getValorProjeto().floatValue();
			float valorTotalParticipantes = 0;
			float valorParticipante = 0;
			
			List<Participacao> participantes = projeto.getParticipacoes();
			for (Participacao participacao : participantes) {
				float bolsaParticipante = participacao.getBolsaValorMensal().intValue();
				int anostrabalhados = (participacao.getAnoTermino()- participacao.getAnoInicio())*12;
				float mesesTrabalhados = participacao.getMesTermino() - participacao.getMesInicio() + 1;
				valorParticipante = bolsaParticipante * (mesesTrabalhados + anostrabalhados);
				valorTotalParticipantes = valorTotalParticipantes + valorParticipante;
			}
					
			if (valorTotalParticipantes > valorProjeto) {
				errors.reject("projeto.valorProjetoInsuficiente", "projeto.valorProjetoInsuficiente");
			}
		}
	}
	
	/**
	 * Valida se Coordenador do projeto faz parte dos participantes.
	 * 
	 * @param projeto {@link Projeto}
	 * @param errors {@link Errors}
	 */
	private void validaParticipacaoCoordenador(Projeto projeto, Errors errors) {
		Boolean coordenadorEstaParticipando = false;
		Long coordenadorId = projeto.getCoordenador().getId();

		List<Participacao> participantes = projeto.getParticipacoes();
		for (Participacao participacao : participantes) {
			if (!participacao.isExterno() && participacao.getParticipante().getId().equals(coordenadorId)){
				coordenadorEstaParticipando = true;
			}
		}

		if (!coordenadorEstaParticipando) {
			errors.reject("projeto.coordenadorNaoParticipante", "projeto.coordenadorNaoParticipante");
		}
	}
	
	private void validaDataParticipacoes(Projeto projeto, Errors errors) {
		
		List<Participacao> participantes = projeto.getParticipacoes();
		
		if(participantes != null) {
			
			if( projeto.getInicio() == null || projeto.getTermino() == null) {
				errors.reject("projeto.periodoAbrangerParticipacoes", "projeto.periodoAbrangerParticipacoes");
				return;
			}
			
			Date dataInicio = projeto.getInicio();
			Date dataTermino = projeto.getTermino();
			
			Calendar inicioProjetoCal = Calendar.getInstance();
			Calendar terminoProjetoCal = Calendar.getInstance();
			inicioProjetoCal.setTime(dataInicio);
			terminoProjetoCal.setTime(dataTermino);
			
			// Atenção: Calendar.MONTH, indice começa em 0.
			YearMonth inicioProj = YearMonth.of(inicioProjetoCal.get(Calendar.YEAR),
					(inicioProjetoCal.get(Calendar.MONTH) + 1));
			YearMonth terminoProj = YearMonth.of(terminoProjetoCal.get(Calendar.YEAR),
					(terminoProjetoCal.get(Calendar.MONTH) + 1));
			
			for(Participacao participante : participantes) {
				YearMonth inicioParticipacao = YearMonth.of(participante.getAnoInicio(),
						participante.getMesInicio());
				YearMonth TerminoParticipacao = YearMonth.of(participante.getAnoTermino(),
						participante.getMesTermino());
				
				if (inicioProj.isAfter(inicioParticipacao) || terminoProj.isBefore(TerminoParticipacao)) {
					errors.reject("projeto.periodoAbrangerParticipacoes",
						"projeto.periodoAbrangerParticipacoes");
					return;
				}
			}
		}
	}
}
