package ufc.quixada.npi.gpa.observer;



import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import ufc.quixada.npi.gpa.model.Log;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.Evento;
import ufc.quixada.npi.gpa.service.Observer;
import ufc.quixada.npi.gpa.service.ProjetoService;

public class LogObserver implements Observer {
	
	@Inject
	private ProjetoService projetoService;
	
	@Override
	public void notificar(Projeto projeto, Evento evento, Pessoa pessoa){
		List<Log> logs = new ArrayList<Log>();
		Log log = new Log();
		log.setAutor(pessoa.getNome());
		log.setProjeto(projeto);
		
		switch (evento) {
			case CADASTRO_PROJETO:
				log.setDescricao("Cadastro do projeto");
				break;
			case EDICAO_PROJETO:
				log.setDescricao("Edição dos dados do projeto");
				break;
			case ADICAO_PARTICIPANTE:
				log.setDescricao("Adicionou Participante");
				break;
			case REMOCAO_PARTICIPANTE:
				log.setDescricao("Removeu Participante");
				break;
			case SUBMISSAO:
				log.setDescricao("Submissão do projeto");
				break;
			case ATRIBUICAO_PARECERISTA:
				log.setDescricao("Atribuiu parecerista ao projeto");
				break;
			case EMISSAO_PARECER:
				log.setDescricao("Emitiu parecer");
				break;
			case EMISSAO_AVALIACAO_RELATOR:
				log.setDescricao("Emissão da Avaliação do relator");
				break;
			case RESOLUCAO_RESTRICAO:
				log.setDescricao("Resolução da restrição");
				break;
			case SUBMISSAO_RESOLUCAO_RESTRICAO:
				log.setDescricao("Submissão da resolução da restrição");
				break;
			case ALTERACAO_PARECERISTA:
				log.setDescricao("Alteração de parecerista");
				break;
			case RESOLUCAO_PENDENCIAS:
				log.setDescricao("Resolução de pendências");
				break;
			case SUBMISSAO_RESOLUCAO_PENDENCIAS:
				log.setDescricao("Submissão da resolução de pendências");
				break;
			case ATRIBUICAO_RELATOR:
				log.setDescricao("Atribuiu relator ao projeto");
				break;
			case ALTERACAO_RELATOR:
				log.setDescricao("Alteração de relator");
				break;
			case HOMOLOGACAO:
				log.setDescricao("Homologação do projeto");
				break;
		}
		
		projetoService.salvarLog(log);
		if(projeto.getLogs() != null){
			logs = projeto.getLogs();
		}
		logs.add(log);
		projeto.setLogs(logs);
	    
	}
}
