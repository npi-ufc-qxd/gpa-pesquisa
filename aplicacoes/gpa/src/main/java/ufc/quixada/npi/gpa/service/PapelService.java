package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Papel;

public interface PapelService {
	
	public abstract List<Papel> atualizaStatus(List<Papel> papeis);
	
	public abstract List<Papel> getPapeis();

	public abstract Papel find(Long id);

	public abstract Papel find(String string);
}
