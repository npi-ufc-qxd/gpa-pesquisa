package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.FonteFinanciamento;

public interface FonteFinanciamentoService {

	public List<FonteFinanciamento> getFontesFinanciamento();
	
	public FonteFinanciamento getFonteFinanciamento(Long fonteFinanciamentoId);

	public void cadastrar(FonteFinanciamento fonteFinanciamento);

	public void remover(FonteFinanciamento fonteFinanciamento);
}
