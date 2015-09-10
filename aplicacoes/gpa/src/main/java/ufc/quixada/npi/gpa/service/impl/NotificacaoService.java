package ufc.quixada.npi.gpa.service.impl;

import java.util.List;

import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.Evento;
import ufc.quixada.npi.gpa.service.Observer;

public class NotificacaoService {
	
	private List<Observer> observadores;

	public void notificar(Projeto projeto, Evento evento) {
		for(Observer observer : observadores) {
			observer.notificar(projeto, evento);
		}
	}

	public void setObservadores(List<Observer> observadores) {
		this.observadores = observadores;
	}

}
