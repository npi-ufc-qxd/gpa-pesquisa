package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_CAMPO_OBRIGATORIO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_DATA_INICIO_TERMINO;
import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_DATA_TERMINO_FUTURA;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Projeto;
import ufc.quixada.npi.gpa.model.Projeto.StatusProjeto;
import ufc.quixada.npi.gpa.service.ProjetoService;
import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;

@Named
public class ProjetoServiceImpl implements ProjetoService {

	@Inject
	private GenericRepository<Projeto> projetoRepository;
	
	@Inject
	private GenericRepository<Parecer> parecerRepository;
	
	@Override
	public Map<String, String> cadastrar(Projeto projeto) {
		Map<String, String> resultado = new HashMap<String, String>();
		
		if(!projeto.isDataTerminoFutura()) {
			resultado.put("termino", MENSAGEM_DATA_TERMINO_FUTURA);
		}
		
		if(!projeto.isPeriodoValido()) {
			resultado.put("inicio", MENSAGEM_DATA_INICIO_TERMINO);
		}
		
		if (resultado.isEmpty()) {
			projeto.setStatus(StatusProjeto.NOVO);
			projetoRepository.save(projeto);

			String codigo = geraCodigoProjeto(projeto.getId());
			projeto.setCodigo(codigo);
			projetoRepository.update(projeto);
		}
		
		return resultado;
	}
	
	@Override
	public Map<String, String> atualizar(Projeto projeto) {
		Map<String, String> resultado = new HashMap<String, String>();
		
		if(!projeto.isDataTerminoFutura()) {
			resultado.put("termino", MENSAGEM_DATA_TERMINO_FUTURA);
		}
		
		if(!projeto.isPeriodoValido()) {
			resultado.put("inicio", MENSAGEM_DATA_INICIO_TERMINO);
		}
		
		if (resultado.isEmpty()) {
			projeto.setStatus(StatusProjeto.NOVO);
			projetoRepository.update(projeto);
		}
		
		return resultado;
	}
	
	@Override
	public void remover(Projeto projeto) {
		projetoRepository.delete(projeto);
	}

	@Override
	public List<Projeto> getProjetosSubmetidos() {
		return projetoRepository.find(QueryType.JPQL, "from Projeto as p where p.status = 'SUBMETIDO' or p.status = 'AGUARDANDO_PARECER' or p.status = 'AGUARDANDO_AVALIACAO'", null);
	}
	
	@Override
	public List<Projeto> getProjetosAtribuidos() {
		return projetoRepository.find(QueryType.JPQL, "from Projeto as p where p.status = 'AGUARDANDO_PARECER' ", null);
	}

	@Override
	public List<Projeto> getProjetosByUsuario(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return projetoRepository.find(QueryType.JPQL, "from Projeto where autor.id = :id", params);
	}
	
	@Override
	public List<Projeto> getProjetosByParticipante(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return projetoRepository.find(QueryType.JPQL, "select distinct p FROM Projeto p JOIN p.participantes pa WHERE pa.id = :id and p.status != 'NOVO'", params);
	}
	

	@Override
	public List<Projeto> getProjetosAvaliadosDoUsuario(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return projetoRepository.find(QueryType.JPQL, "from Projeto as p where ((p.status = 'APROVADO') OR (p.status = 'REPROVADO') OR (p.status = 'APROVADO_COM_RESTRICAO')) AND (autor.id = :id)" , params);
	}

	@Override
	public List<Projeto> getProjetosAvaliados() {
		return projetoRepository.find(QueryType.JPQL, "from Projeto as p where (p.status = 'APROVADO') OR (p.status = 'REPROVADO') OR (p.status = 'APROVADO_COM_RESTRICAO')" , null);
	}

	@Override
	public List<Projeto> getProjetosAguardandoParecer(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return projetoRepository.find(QueryType.JPQL, "select p from Projeto as p where p.parecer.parecerista.id = :id and p.status = 'AGUARDANDO_PARECER'" , params);
	}

	@Override
	public Projeto getProjetoById(Long id) {
		return projetoRepository.find(Projeto.class, id);
	}
	
	private String geraCodigoProjeto(Long id) {
		if (id < 10) {
			return "PESQ0" + id;
		} else {
			return "PESQ" + id;
		}
	}

	private Map<String, String> validarSubmissao(Projeto projeto) {
		Map<String, String> resultado = new HashMap<String, String>();
		
		if(projeto.getNome() == null || projeto.getNome().isEmpty()) {
			resultado.put("nome", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		
		if(projeto.getDescricao() == null || projeto.getDescricao().isEmpty()) {
			resultado.put("descricao", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		
		if(projeto.getInicio() == null) {
			resultado.put("inicio", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		
		if(!projeto.isPeriodoValido()) {
			resultado.put("inicio", MENSAGEM_DATA_INICIO_TERMINO);
		}
		
		if(projeto.getTermino() == null) {
			resultado.put("termino", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		
		if(!projeto.isDataTerminoFutura()) {
			resultado.put("termino", MENSAGEM_DATA_TERMINO_FUTURA);
		}
		
		if(projeto.getCargaHoraria() == null || projeto.getCargaHoraria() == 0) {
			resultado.put("cargaHoraria", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		
		if(projeto.getParticipantes() == null || projeto.getParticipantes().isEmpty()) {
			resultado.put("participantes", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		
		if(projeto.getLocal() == null || projeto.getLocal().isEmpty()) {
			resultado.put("local", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		
		if(projeto.getAtividades() == null || projeto.getAtividades().isEmpty()) {
			resultado.put("atividades", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		
		if(projeto.getDocumentos() == null || projeto.getDocumentos().isEmpty()) {
			resultado.put("documentos", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		
		
		return resultado;
	}

	@Override
	public Map<String, String> submeter(Projeto projeto) {
		Map<String, String> resultadoValidacao = this.validarSubmissao(projeto);
		if (resultadoValidacao.isEmpty()) {
			projeto.setStatus(StatusProjeto.SUBMETIDO);
			projeto.setSubmissao(new Date());
			projetoRepository.update(projeto);
		}
		return resultadoValidacao;
	}

	@Override
	public Map<String, String> atribuirParecerista(Projeto projeto, Parecer parecer) {
		Map<String, String> resultado = new HashMap<String, String>();
		if(parecer.getPrazo() == null) {
			resultado.put("prazo", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		if(resultado.isEmpty()) {
			parecerRepository.save(parecer);
			projeto.setParecer(parecer);
			projeto.setStatus(StatusProjeto.AGUARDANDO_PARECER);
			projetoRepository.update(projeto);
			
		}
		return resultado;
	}

	@Override
	public Map<String, String> emitirParecer(Projeto projeto) {
		Map<String, String> resultado = new HashMap<String, String>();
		if(projeto.getParecer().getParecer() == null || projeto.getParecer().getParecer().isEmpty()) {
			resultado.put("parecer", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		if(resultado.isEmpty()) {
			projeto.setStatus(StatusProjeto.AGUARDANDO_AVALIACAO);
			projetoRepository.update(projeto);
			
		}
		return resultado;
	}

	@Override
	public Map<String, String> avaliar(Projeto projeto) {
		Map<String, String> resultado = new HashMap<String, String>();
		if(projeto.getAta() == null) {
			resultado.put("ata", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		if(projeto.getOficio() == null) {
			resultado.put("oficio", MENSAGEM_CAMPO_OBRIGATORIO);
		}
		if(resultado.isEmpty()) {
			projeto.setAvaliacao(new Date());
			projetoRepository.update(projeto);
			projetoRepository.update(projeto);
		}
		return resultado;
	}

}