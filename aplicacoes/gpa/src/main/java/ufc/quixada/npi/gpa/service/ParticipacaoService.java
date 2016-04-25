package ufc.quixada.npi.gpa.service;

import ufc.quixada.npi.gpa.model.Participacao;

public interface ParticipacaoService{
	
	/**
	 * Valida intervalos de participação por pessoa, não permitindo intercessão.
	 * 
	 */
	public abstract void verificaIntervalosParticipacaoPessoa(Participacao participacao, Long idProjeto);
}
