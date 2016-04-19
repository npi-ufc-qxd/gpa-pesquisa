package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PARTICIPACAO_MESMO_PERIODO;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import br.ufc.quixada.npi.enumeration.QueryType;
import br.ufc.quixada.npi.repository.GenericRepository;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.service.ParticipacaoService;

@Named
public class ParticipacaoServiceImpl implements ParticipacaoService{
	
	@Inject
	private GenericRepository<Participacao> participacaoRepository;
	
	@Override
	public void verificaIntervalosParticipacaoPessoa(Participacao participacao, Long idProjeto) {
		List<Participacao> participacoes = new ArrayList<>();
		
		if(participacao.isExterno()){
			participacoes = getParticipacoesPorPessoaByProjeto(idProjeto, participacao.getParticipanteExterno().getId());
		}else{
			participacoes = getParticipacoesPorPessoaByProjeto(idProjeto, participacao.getParticipante().getId());
		}
		// Participação atual
		YearMonth inicioPartAtual = YearMonth.of(participacao.getAnoInicio(), participacao.getMesInicio());
		YearMonth terminoPartAtual = YearMonth.of(participacao.getAnoTermino(), participacao.getMesTermino());

		for (Participacao part : participacoes) {
				YearMonth inicio = YearMonth.of(part.getAnoInicio(), part.getMesInicio());
				YearMonth termino = YearMonth.of(part.getAnoTermino(), part.getMesTermino());

				if (inicioPartAtual.compareTo(termino) >= 0) {
					// Verifica se a o inicioPartAtual é maior que o termino registrado
					// Assim é possível dividir uma participação em 2 etapas, antes e depois do registrado.
					if (!(inicioPartAtual.compareTo(termino) > 0)) {
						throw new IllegalArgumentException(MENSAGEM_PARTICIPACAO_MESMO_PERIODO);
					}
				} else {
					// Verifica se o termino da participação atual é maior que o inicio cadastradas
					if (!(terminoPartAtual.compareTo(inicio) < 0)) {
						throw new IllegalArgumentException(MENSAGEM_PARTICIPACAO_MESMO_PERIODO);
					} 
				}
		}

		
	}
	
	private List<Participacao> getParticipacoesPorPessoaByProjeto(Long idProjeto, Long idPessoa){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idProjeto);
		params.put("idPessoa", idPessoa);
		return participacaoRepository.find(QueryType.JPQL,
					"select distinct part FROM Participacao as part " + "WHERE part.projeto.id = :id AND (part.participanteExterno.id = :idPessoa OR part.participante.id =:idPessoa)", params);
	}

}
