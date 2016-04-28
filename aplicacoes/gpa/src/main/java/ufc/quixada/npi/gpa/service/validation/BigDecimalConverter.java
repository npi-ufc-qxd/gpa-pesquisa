package ufc.quixada.npi.gpa.service.validation;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.persistence.AttributeConverter;

public class BigDecimalConverter implements AttributeConverter<String, BigDecimal> {

	@Override
	public BigDecimal convertToDatabaseColumn(String valor) {
		valor = valor.replace("R$ ", "");
		valor = valor.replaceAll(".", "");
		valor = valor.replace(",", ".");
		Double valorReal = Double.valueOf(valor);
		return BigDecimal.valueOf(valorReal);
	}

	@Override
	public String convertToEntityAttribute(BigDecimal valor) {
		DecimalFormat format = new DecimalFormat();
		return format.format(valor);
	}

}
