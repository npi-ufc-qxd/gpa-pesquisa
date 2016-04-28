package ufc.quixada.npi.gpa.service;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.Evento;

public interface Observer {
	
	void notificar(Projeto projeto, Evento evento, Pessoa pessoa);

}
