package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.utils.Constants.MENSAGEM_PARTICIPACAO_MESMO_PERIODO;

import java.time.YearMonth;
import java.util.List;

import javax.inject.Named;

import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.PessoaExterna;
import ufc.quixada.npi.gpa.service.ParticipacaoService;

@Named
public class ParticipacaoServiceImpl implements ParticipacaoService{

	@Override
	public void verificaIntervalosParticipacaoPessoa(Participacao participacao) {
		
		List<Participacao> participacoes = participacao.getProjeto().getParticipacoes();
		// Participação atual
		Pessoa participanteAtual = participacao.getParticipante();
		PessoaExterna participanteExternoAtual = participacao.getParticipanteExterno();
		YearMonth inicioPartAtual = YearMonth.of(participacao.getAnoInicio(), participacao.getMesInicio());
		YearMonth terminoPartAtual = YearMonth.of(participacao.getAnoTermino(), participacao.getMesTermino());

		for (Participacao part : participacoes) {
			if ((participanteAtual!=null && participanteAtual.equals(part.getParticipante()))
					|| (participanteExternoAtual!=null && participanteExternoAtual.equals(part.getParticipanteExterno()))) {
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

		
	}

}
