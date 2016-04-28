package ufc.quixada.npi.gpa.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.ufc.quixada.npi.repository.GenericRepository;
import ufc.quixada.npi.gpa.model.FonteFinanciamento;
import ufc.quixada.npi.gpa.service.FonteFinanciamentoService;

@Named
public class FonteFinanciamentoServiceImpl implements FonteFinanciamentoService {

	@Inject
	private GenericRepository<FonteFinanciamento> fonteFinanciamentoRepository;
	
	@Override
	public List<FonteFinanciamento> getFontesFinanciamento() {
		return fonteFinanciamentoRepository.find(FonteFinanciamento.class);
	}
	
	@Override
	public FonteFinanciamento getFonteFinanciamento(Long id){
		return fonteFinanciamentoRepository.find(FonteFinanciamento.class, id);
	}

	@Override
	public void cadastrar(FonteFinanciamento fonteFinanciamento) {
		fonteFinanciamentoRepository.save(fonteFinanciamento);
	}

	@Override
	public void remover(FonteFinanciamento fonteFinanciamento) {
		fonteFinanciamentoRepository.delete(fonteFinanciamento);
	}

}
