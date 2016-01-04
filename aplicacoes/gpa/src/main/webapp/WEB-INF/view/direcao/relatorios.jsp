<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Relatórios</title>
	<jsp:include page="../modulos/header-estrutura.jsp" />
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Relatórios</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<c:if test="${empty relatorio}">
						<h5>
							<label class="col-sm-2 control-label">Tipo de Relatório:</label>
						</h5>
						<div class="col-sm-6">
							<select id="relatorio" name="relatorioParam" class="form-control">
								<option></option>
								<option value="APROVADOS">PROJETOS APROVADOS</option>
								<option value="REPROVADOS">PROJETOS REPROVADOS</option>
								<option value="POR_USUARIO">PROJETOS POR USUÁRIO</option>
							</select>
						</div>
					</c:if>
				</div>

				<c:if test="${empty relatorio}">
					<%-- Form dos projetos aprovados--%>
					<hr>
					<div id="form_aprovados">

						<form:form id="relatoriosAprovadosForm"
							enctype="multipart/form-data"
							servletRelativeAction="relatorios/aprovados" method="GET"
							cssClass="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">Intervalo de Início:</label>
								<div class="form-item">
									<div class="col-sm-2">
										<input type="text" name="iInterInicio" id="inicioRelatorioInicio" class="form-control data">
									</div>
								</div>
								<div class="form-item">
									<div class="col-sm-2">
										<input type="text" name="fInterInicio" id="terminoRelatorioInicio" class="form-control data">
									</div>
								</div>
								<label class="col-sm-2 control-label">Intervalo de Término:</label>
								<div class="form-item">
									<div class="col-sm-2">
										<input type="text" name="iInterTermino" id="inicioRelatorioTermino" class="form-control data">
									</div>
								</div>
								<div class="form-item">
									<div class="col-sm-2">
										<input type="text" name="fInterTermino" id="terminoRelatorioTermino" class="form-control data">
									</div>
								</div>
							</div>
								<div class="controls">
									<input name="gerar" type="submit" class="btn btn-primary" value="Gerar"/>
								</div>

						</form:form>
					</div>
					<%-- form dos projetos reprovados--%>
					<div id="form_reprovados">
						<form:form id="relatoriosReprovadosForm"
							enctype="multipart/form-data"
							servletRelativeAction="relatorios/reprovados" method="GET"
							cssClass="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">Intervalo da
									submissão:</label>
								<div class="form-item">
									<div class="col-sm-2">
										<input type="text" name="submissaoInicio"
											id="submissaoRelatorioInicio" class="form-control data">
									</div>
								</div>
								<div class="form-item">
									<div class="col-sm-2">
										<input type="text" name="submissaoTermino"
											id="submissaoRelatoriTermino" class="form-control data">
									</div>
								</div>
								<button name="gerar" type="submit" class="btn btn-primary">Gerar</button>
							</div>
						</form:form>
					</div>
					<%-- form por pessoa--%>
					<div id="form_p-pessoa">
						<form:form id="relatoriosPPessoaForm"
							enctype="multipart/form-data"
							servletRelativeAction="relatorios/por-pessoa" method="GET"
							cssClass="form-horizontal">

							<div class="form-group col-sm-7">
								<div class="form-item">
									<label class="col-sm-1 control-label">Nome:</label>
									<div class="col-sm-11">
										<select id="select_pessoaRelatorio" name="id"
											class="form-control" required>
											<c:set var="part" value="${pessoas }"></c:set>
											<option value=""></option>
											<c:forEach items="${pessoas }" var="pessoa">
												<c:set var="selected" value=""></c:set>
												<c:set var="participanteSelecionado" value="id=${pessoa.id }"></c:set>
												<c:if test="${fn:contains(part, participanteSelecionado)}">
													<c:set var="selected" value="selected=\"selected\""></c:set>
												</c:if>
												<option value="${pessoa.id }">${pessoa.nome }</option>
											</c:forEach>
										</select>
	
									</div>
								</div>
								<!-- div select -->
							</div>
							<div class="form-group">
								<div class="form-item">
									<label class="col-sm-2 control-label">Ano de submissão:</label>
									<div class="col-sm-2">
										<input type="text" name="ano" id="anoRelatorio" class="form-control data">
									</div>
									<div class="col-sm-1" id="submit-p-pessoa">
										<button name="gerar" type="submit" class="btn btn-primary">Gerar</button>
									</div>
								</div>
							</div>
						</form:form>
						<br /> <br />
					</div>
				</c:if>
				<!-- TAB APROVADOS -->
				<c:if test="${tipoRelatorio == 'aprovados'}">
					<div class="row">
						<div class="col-md-10">
							<h4>Projetos Aprovados</h4>
						</div>
						<div class="col-md-2" style="text-align: right;">
							<a href="<c:url value="/direcao/relatorios" />" class="btn btn-primary btn-sm">Nova consulta</a>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<strong>
								Gerado em: <fmt:formatDate value="${data_pesquisa}"	pattern="dd/MM/yyyy' às 'HH:mm:ss" />
							</strong>
						</div>
						<div class="col-md-6">
							<strong>Quantidade de Projetos: ${fn:length(relatorio.projetosAprovados)}</strong>
						</div>
					</div>	
					<br>
					<div class="row">	
						<div class="col-md-6">
							<c:if test="${not empty inicio_intervalo_inicio}">
								<strong>Início do Intervalo de Início: <fmt:parseDate
									value="${inicio_intervalo_inicio}" pattern="yyyy-MM"
									var="data_inicio_Intervalo_I_format" /> <fmt:formatDate
									value="${data_inicio_Intervalo_I_format}" pattern="MM-yyyy" /> </strong>
							</c:if>
							<c:if test="${empty inicio_intervalo_inicio}">
								<strong>Início do Intervalo de Início: - - - </strong>
							</c:if>
						</div>
						<div class="col-md-6">	
							<c:if test="${not empty termino_intervalo_inicio}">
								<strong>Termino do Intervalo de Início:<fmt:parseDate
									value="${termino_intervalo_inicio}" pattern="yyyy-MM"
									var="data_termino_Intervalo_I_format" /> <fmt:formatDate
									value="${data_termino_Intervalo_I_format}" pattern="MM-yyyy" /> </strong>
							</c:if>
							<c:if test="${empty termino_intervalo_inicio}">
								<strong>Termino do Intervalo de Início: - - - </strong>
							</c:if>
							
						</div>
					</div>
					<br>
					<div class="row">	
						<div class="col-md-6">
							<c:if test="${not empty inicio_intervalo_termino}">
								<strong>Início do Intervalo de Término: <fmt:parseDate
									value="${inicio_intervalo_termino}" pattern="yyyy-MM"
									var="data_inicio_Intervalo_F_format" /> <fmt:formatDate
									value="${data_inicio_Intervalo_F_format}" pattern="MM-yyyy" /> </strong>
							</c:if>
							<c:if test="${empty inicio_intervalo_termino}">
								<strong>Início do Intervalo de Término: - - - </strong>
							</c:if>
						</div>
						<div class="col-md-6">	
							<c:if test="${not empty termino_intervalo_termino}">
								<strong>Término do Intervalo de Término: <fmt:parseDate
									value="${termino_intervalo_termino}" pattern="yyyy-MM"
									var="data_termino_Intervalo_F_format" /> <fmt:formatDate
									value="${data_termino_Intervalo_F_format}" pattern="MM-yyyy" /></strong>
							</c:if>
							<c:if test="${empty termino_intervalo_termino}">
								<strong>Término do Intervalo de Término: - - - </strong>
							</c:if>
							
						</div>
					</div>
					<br>
					<table id="relatorios-projetosAprovados" class="display">
						<thead>
							<tr>
								<th>Coordenador</th>
								<th>Nome do Projeto</th>
								<th>Início</th>
								<th>Término</th>
								<th>Bolsas</th>
								<th>Valor total de Bolsas</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="projeto" items="${relatorio.projetosAprovados}">
								<tr>
									<td>${projeto.nomeCoordenador}</td>
									<td><a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nomeProjeto}</a>
									</td>
									<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.dataInicio}"/> </td>
									<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.dataTermino}"/></td>
									<td>${projeto.qtdBolsas}</td>
									<td><fmt:formatNumber type="CURRENCY"
												currencyCode="BRL">${projeto.valorTotalBolsas}</fmt:formatNumber></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<!-- /TAB APROVADOS -->

				<!-- TAB REPROVADOS -->
				<div id="tab-projetos-aprovados">
					<c:if test="${tipoRelatorio == 'reprovados'}">
						<div class="row">
							<div class="col-md-10">
								<h4>Projetos Reprovados</h4>
							</div>
							<div class="col-md-2" style="text-align: right;">
								<a href="<c:url value="/direcao/relatorios" />" class="btn btn-primary btn-sm">Nova consulta</a>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<strong>
									Gerado em: <fmt:formatDate value="${data_pesquisa}"	pattern="dd/MM/yyyy' às 'HH:mm:ss" />
								</strong>
							</div>
							<div class="col-md-8">
								<strong>Quantidade de Projetos: ${fn:length(relatorio.projetosReprovados)}</strong>
							</div>
							<div class="col-md-4">
								<c:if test="${not empty data_de_submissao}">
									<strong>Data: ${data_de_submissao}</strong>
								</c:if>
								<c:if test="${empty data_de_submissao}">
									<strong>Data: - - - </strong>
								</c:if>
							</div>
						</div>
						<br>
						<table id="relatorios-projetosReprovados" class="display">
							<thead>
								<tr>
									<th>Coordenador</th>
									<th class="col-sm-6">Nome do Projeto</th>
									<th>Submissão</th>
									<th>Avaliação</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="projeto" items="${relatorio.projetosReprovados}">
									<tr>
										<td>${projeto.nomeCoordenador}</td>
										<td><a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nomeProjeto}</a>
										</td>
										<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.dataDeSubimissao}"/></td>
										<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.dataDeAvaliacao}"/></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
				<!-- TAB P/ PESSOA -->
				<div id="tab-p-pessoa">
					<c:if test="${tipoRelatorio == 'por-pessoa'}">
						<div class="row">
							<div class="col-md-10">
								<h4>Projetos por usuário</h4>
							</div>
							<div class="col-md-2" style="text-align: right;">
								<a href="<c:url value="/direcao/relatorios" />" class="btn btn-primary btn-sm">Nova consulta</a>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12">
								<dl class="dl-horizontal">
									<dt>
										<strong>Gerado em:</strong>
									</dt>
									<dd>
										<fmt:formatDate value="${data_pesquisa}"	pattern="dd/MM/yyyy' às 'HH:mm:ss" />
									</dd>
								</dl>
							</div>
							<div class="col-md-6">
								<dl class="dl-horizontal">
									<dt>Nome:</dt>
									<dd>${relatorio.nomeUsuario}</dd>

									<dt>Carga horária total:</dt>
									<dd>${relatorio.cargaHorariaTotalUsuario} hrs.</dd>
								</dl>
							</div>
							<div class="col-md-6">
								<dl class="dl-horizontal">
									<dt>Ano:</dt>
									<dd>${relatorio.anoConsulta}</dd>

									<dt>Valor total de bolsas:</dt>
									<dd><fmt:formatNumber type="CURRENCY"
												currencyCode="BRL">${relatorio.valorTotalBolsasUsuario}</fmt:formatNumber></dd>
								</dl>
							</div>
						</div>

						<table id="relatorios-projetosPorUsuario" class="display">
							<thead>
								<tr>
									<th>Nome do Projeto</th>
									<th>Vínculo</th>
									<th>Carga Horária</th>
									<th>Valor da Bolsa</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="projeto" items="${relatorio.projetosPorPessoa}">
									<tr>
										<td><a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nomeProjeto}</a>
										</td>
										<td>${projeto.vinculo}</td>
										<td>${projeto.cargaHoraria}</td>
										<td><fmt:formatNumber type="CURRENCY"
												currencyCode="BRL">${projeto.valorBolsa}</fmt:formatNumber></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
				<!-- /TAB P/ PESSOA -->
			</div>
			<!-- /panel-body -->
		</div>
		<!--/panel  -->
	</div>
	<!-- /container -->
	<jsp:include page="../modulos/footer.jsp" />

	<script>
		$(function() {
			var text_export = 'Exportar para PDF';
			var tabela_dom = 'Bfrtip';
			var tabela_ext = 'print';
			var img_align = 'center';
			$('#relatorios-projetosAprovados')
			.DataTable({
				"columnDefs" : [ 
	    		    {className: "dt-center", "targets": [ 4, 5]},
	    		],
				dom : tabela_dom,
				buttons : [ {
					extend : tabela_ext,
					text : text_export,
					orientation: 'landscape',
					title : 'Relatório - Projetos Aprovados',
					message : 'Gerado em: <fmt:formatDate value="${data_pesquisa}" pattern="dd/MM/yyyy' às 'HH:mm:ss"/>;\t'+
								'Quantidade de Projetos: ${fn:length(relatorio.projetosAprovados)};\t'+ 
								'Início do Intervalo de Início: <c:if test="${empty inicio_intervalo_inicio}">- \t</c:if>'+
								'${inicio_intervalo_inicio};\t'+
								'Termino do Intervalo de Início: <c:if test="${empty termino_intervalo_inicio}">- \t</c:if>'+
								'${termino_intervalo_inicio};\t'+
								'Início do Intervalo de Término: <c:if test="${empty inicio_intervalo_termino}">- \t</c:if>'+
								'${termino_intervalo_inicio};\t'+
								'Término do Intervalo de Término: <c:if test="${empty termino_intervalo_termino}">- \t</c:if>'+
								'${termino_intervalo_termino}.\t',
					customize : function(doc) {
						$(doc.document.body).find( 'table' )
                        .addClass( 'compact' )
                        .css( 'font-size', 'inherit' );
					}
				} ],
				"language" : {
					"url" : "<c:url value="/resources/js/Portuguese-Brasil.json"/>"
				}
			});
			
			$('#relatorios-projetosReprovados')
				.DataTable({
					"columnDefs" : [ 
		    		    {className: "dt-center", "targets": [ 2, 3]},
		    		],
					dom : tabela_dom,
					buttons : [ {
						extend : tabela_ext,
						text : text_export,
						title : 'Relatório - Projetos Reprovados',
						message : 'Gerado em: <fmt:formatDate value="${data_pesquisa}" pattern="dd/MM/yyyy' às 'HH:mm:ss"/>;\n'+
									'Quantidade de registros: ${fn:length(relatorio.projetosReprovados)}; \t'+
									'Submissão: <c:if test="${empty submissao_format}">-\t</c:if><fmt:formatDate value="${submissao_format}" pattern="MM-yyyy" /> \t',
						customize : function(doc) {
							$(doc.document.body).find( 'table' )
	                        .addClass( 'compact' )
	                        .css( 'font-size', 'inherit' );
						}
					} ],
					"language" : {
						"url" : "<c:url value="/resources/js/Portuguese-Brasil.json"/>"
					}
				});

			$('#relatorios-projetosPorUsuario')
				.DataTable({
					dom : tabela_dom,
					buttons : [ {
						extend : tabela_ext,
						text : text_export,
						title : 'Relatório - Projetos por usuário',
						message : 'Gerado em: <fmt:formatDate value="${data_pesquisa}" pattern="dd/MM/yyyy' às 'HH:mm:ss"/>;\n'+
									'Nome: ${relatorio.nomeUsuario}; \t Ano: <c:if test="${empty relatorio.anoConsulta}">- \t</c:if>${relatorio.anoConsulta}; \n'+
									'Carga horária total: ${relatorio.cargaHorariaTotalUsuario}; \t Valor total de bolsas: ${relatorio.valorTotalBolsasUsuario}; \n',
						customize : function(doc) {
							$(doc.document.body).find( 'table' )
	                        .addClass( 'compact' )
	                        .css( 'font-size', 'inherit' );
						}
					} ],
					"language" : {
						"url" : "<c:url value="/resources/js/Portuguese-Brasil.json"/>"
					}
				});

		});
	</script>
</body>
</html>
