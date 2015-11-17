package ufc.quixada.npi.gpa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.service.PapelService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;

@Named
public class PapelServiceImpl implements PapelService{
	
	@Inject
	private GenericRepository<Papel> papelRepository;
	
	@Override
	public List<Papel> atualizaStatus(List<Papel> papeis) {
		List<Papel> papeisBanco = getPapeis();
		for (Papel papelBanco : papeisBanco) {
			for (Papel papelUsuario : papeis) {
				if(papelBanco.equals(papelUsuario)){
					papelBanco.setStatus(true);
				}
			}
		}
		return papeisBanco;
	}

	@Override
	public List<Papel> getPapeis() {
		return papelRepository.find(Papel.class);
	}

	@Override
	public Papel find(Long id) {
		return papelRepository.find(Papel.class, id);
	}

	@Override
	public Papel find(String papel) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("papel", papel);
		List<Papel> papeis = papelRepository.find(QueryType.JPQL, "from Papel where nome = :papel",params);
		if(papeis !=null && !papeis.isEmpty()){
			return papeis.get(0);
		}
		return null;
	}
	

}
