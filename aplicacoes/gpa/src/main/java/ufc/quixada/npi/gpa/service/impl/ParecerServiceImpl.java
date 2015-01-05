package ufc.quixada.npi.gpa.service.impl;

import javax.inject.Named;

import br.ufc.quixada.npi.repository.GenericRepository;
import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.service.ParecerService;

@Named
public class ParecerServiceImpl implements ParecerService {
	
	private GenericRepository<Parecer> parecerRespository;

	@Override
	public Parecer getParecerById(Long id) {
		return parecerRespository.find(Parecer.class, id);
	}

}
