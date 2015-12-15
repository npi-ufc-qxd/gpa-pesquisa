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
								<div class="form-item">
									<label class="col-sm-2 control-label">Início do Intervalo:</label>
									<div class="col-sm-2">
										<input type="text" name="inicio" id="inicioRelatorio" class="form-control data">
									</div>
								</div>
								<div class="form-item">
									<label class="col-sm-2 control-label">Término do Intervalo:</label>
									<div class="col-sm-2">
										<input type="text" name="termino" id="terminoRelatorio" class="form-control data">
									</div>
									<button name="gerar" type="submit" class="btn btn-primary">Gerar</button>
								</div>
							</div>

						</form:form>
					</div>
					<%-- form dos projetos reprovados--%>
					<div id="form_reprovados">
						<form:form id="relatoriosRerovadosForm"
							enctype="multipart/form-data"
							servletRelativeAction="relatorios/reprovados" method="GET"
							cssClass="form-horizontal">
							<label class="col-sm-2 control-label">Intervalo da submissão:</label>
							<div class="col-sm-2">
								<input type="text" name="submissao-inicio" id="submissaoRelatorio-inicio" class="form-control data">
							</div>
							<div class="col-sm-2">
								<input type="text" name="submissao-termino" id="submissaoRelatorio-termino" class="form-control data">
							</div>
							<button name="gerar" type="submit" class="btn btn-primary">Gerar</button>
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
									<label class="col-sm-2 control-label">Data da submissão:</label>
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
							<strong>Quantidade de Projetos: ${fn:length(relatorio.projetosAprovados)}</strong>
						</div>
						<div class="col-md-3">
							<strong>Data início: <fmt:parseDate
									value="${data_de_inicio}" pattern="yyyy-MM"
									var="data_inicio_format" /> <fmt:formatDate
									value="${data_inicio_format}" pattern="MM-yyyy" /></strong>
						</div>
						<div class="col-md-3">
							<strong>Data término: <fmt:parseDate
									value="${data_de_termino}" pattern="yyyy-MM"
									var="data_termino_format" /> <fmt:formatDate
									value="${data_termino_format}" pattern="MM-yyyy" /></strong>
						</div>
						<div class="col-md-4">
							<strong>
								Gerado em: <fmt:formatDate value="${data_pesquisa}"	pattern="dd/MM/yyyy' às 'HH:mm:ss" />
							</strong>
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
								<th>Qtde de Bolsas</th>
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
							<div class="col-md-8">
								<strong>Quantidade de Projetos: ${fn:length(relatorio.projetosReprovados)}</strong>
							</div>
							<div class="col-md-4">
								<c:if test="${not empty data_de_submissao}">
									<strong>Submissão: <fmt:parseDate value="${data_de_submissao}"
											pattern="yyyy-MM" var="submissao_format" /> <fmt:formatDate
											value="${submissao_format}" pattern="MM-yyyy" /></strong>
								</c:if>
								<c:if test="${empty data_de_submissao}">
									<strong>Data de submissão: - - - </strong>
								</c:if>
							</div>
							<div class="col-md-4">
								<strong>
									Gerado em: <fmt:formatDate value="${data_pesquisa}"	pattern="dd/MM/yyyy' às 'HH:mm:ss" />
								</strong>
							</div>
						</div>
						<br>
						<table id="relatorios-projetosReprovados" class="display">
							<thead>
								<tr>
									<th>Coordenador</th>
									<th>Nome do Projeto</th>
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
							<div class="col-md-4">
								<dl class="dl-horizontal">
									<dt>
										<strong>Gerado em:</strong>
									</dt>
									<dd>
										<fmt:formatDate value="${data_pesquisa}"	pattern="dd/MM/yyyy' às 'HH:mm:ss" />
									</dd>
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
										<td><a
											href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nomeProjeto}</a>
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
			var logo_gpa = 'data:image/jpeg;base64,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';
			var text_export = 'Exportar para PDF';
			var tabela_dom = 'Bfrtip';
			var tabela_ext = 'pdfHtml5';
			var img_align = 'center';

			
		$('#relatorios-projetosAprovados')
					.DataTable(
							{
								dom : tabela_dom,
								buttons : [ {
									extend : tabela_ext,
									text : text_export,
									orientation: 'landscape',
									title : 'Relatórios - Projetos Aprovados',
									message : 'Quantidade de registros: ${fn:length(relatorio.projetosAprovados)}\t'+ 
												'Data início: <c:if test="${empty data_inicio_format}">-\t</c:if>'+
												'<fmt:formatDate value="${data_inicio_format}" pattern="MM-yyyy" />\t'+
												'Data término: <c:if test="${empty data_inicio_format}">-\t</c:if>'+
												'<fmt:formatDate value="${data_termino_format}" pattern="MM-yyyy" />\t'+
												'Gerado em: <fmt:formatDate value="${data_pesquisa}" pattern="dd/MM/yyyy' às 'HH:mm:ss"/>',
									customize : function(doc) {
										doc.content.splice(0, 0, {
											margin : [ 0, 0, 0, 11 ],
											alignment : img_align,
											image : logo_gpa
										});
									}
								} ],
								"language" : {
									"url" : "<c:url value="/resources/js/Portuguese-Brasil.json"/>"
								}
							});

			$('#relatorios-projetosReprovados')
					.DataTable(
							{
								dom : tabela_dom,
								buttons : [ {
									extend : tabela_ext,
									text : text_export,
									title : 'Relatórios - Projetos Reprovados',
									message : 'Quantidade de registros: ${fn:length(relatorio.projetosReprovados)} \t'+
												'Submissão: <c:if test="${empty submissao_format}">-\t</c:if><fmt:formatDate value="${submissao_format}" pattern="MM-yyyy" /> \t'+
												'Gerado em: <fmt:formatDate value="${data_pesquisa}" pattern="dd/MM/yyyy' às 'HH:mm:ss"/>',
									customize : function(doc) {
										doc.content.splice(0, 0, {
											margin : [ 0, 0, 0, 11 ],
											alignment : img_align,
											image : logo_gpa
										});
									}
								} ],
								"language" : {
									"url" : "<c:url value="/resources/js/Portuguese-Brasil.json"/>"
								}
							});

			$('#relatorios-projetosPorUsuario')
					.DataTable(
							{
								dom : tabela_dom,
								buttons : [ {
									extend : tabela_ext,
									text : text_export,
									title : 'Relatórios - Projetos por usuário',
									message : 'Nome: ${relatorio.nomeUsuario} \t\t\t\t\t\t\t\t Ano: <c:if test="${empty relatorio.anoConsulta}">-\t</c:if>${relatorio.anoConsulta} \n'+
												'Carga horária total: ${relatorio.cargaHorariaTotalUsuario} \t\t\t\t\t\t\t\t Valor total de bolsas: ${relatorio.valorTotalBolsasUsuario} \n'+
												'Gerado em: <fmt:formatDate value="${data_pesquisa}" pattern="dd/MM/yyyy' às 'HH:mm:ss"/>',
									customize : function(doc) {
										doc.content.splice(0, 0, {
											margin : [ 0, 0, 0, 11 ],
											alignment : img_align,
											image : logo_gpa
										});
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
