package ufc.quixada.npi.gpa.model;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ProjetoEntityListener implements ApplicationContextAware{

	private static ApplicationContext context;
	
	/**
	 * Verifica se a fonte de financiamento foi instanciada mas está com o id nulo
	 * dessa forma o objeto recebe o valor nulo também.
	 * 
	 * Isso acontece quando o usuario tenta cadastrar um projeto não financiado.
	 * 
	 * @param fonteFinanciamento
	 */
	@PrePersist
	@PreUpdate
	public void verificaFonteFinanciamentoNula(Projeto projeto){
		context.getAutowireCapableBeanFactory().autowireBean(this);
		
		if(projeto.getFonteFinanciamento() != null && projeto.getFonteFinanciamento().getId() == null){
			projeto.setFonteFinanciamento(null);
		}
	}
	
	public ApplicationContext getApplicationContext(){
		return context;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

}
