 	<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura.jsp" />
		<title>Relatorios</title>
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Relatorios</h3>
			</div>
		</div>
		<div class="panel-body">
			<div class="row">
				<c:if test="${empty relatorio}">
				<label class="col-sm-2 control-label">Tipo de Relatorio:</label>
				<div class="col-sm-6">
					<select id="relatorio" name="relatorioParam" class="form-control">
						<option value=""></option>
						<option value="APROVADOS">PROJETOS APROVADOS</option>
						<option value="REPROVADOS">PROJETOS REPROVADOS</option>
						<option value="POR_USUARIO">PROJETOS POR USUÁRIO</option>
					</select>
				</div>
				</c:if>
				<c:if test="${not empty relatorio}">
				<a href="<c:url value="/direcao/relatorios" />" title="Direção" class="btn btn-primary">Nova consulta</a>
				</c:if>
			</div>

			<br />
			<br />
			<c:if test="${empty relatorio}">
			<%-- Form dos projetos aprovados--%>
			<div id="form_aprovados">
		
				<form:form id="relatoriosAprovadosForm"
					enctype="multipart/form-data"
					servletRelativeAction= "relatorios/aprovados" method="GET"
					cssClass="form-horizontal">
					
				
					<label class ="col-sm-2 control-label" >Início do Intervalo:</label>
					<div class="col-sm-2">
					<input type="text" name="inicio" id="inicioRelatorio"
						class="form-control data"></div>
					<label class ="col-sm-2 control-label">Terminno do Intervalo:</label>
					<div class ="col-sm-2">
					<input type="text" name="termino" id="terminoRelatorio"
						class="form-control data"></div>
					<input name="gerar" type="submit" class="btn btn-primary"
						value="gerar" />
				</form:form>
			</div>
			<%-- form dos projetos reprovados--%>
			<div id="form_reprovados">
				<form:form id="relatoriosRerovadosForm"
					enctype="multipart/form-data"
					servletRelativeAction="relatorios/reprovados" method="GET"
					cssClass="form-horizontal">
					<label class ="col-sm-2 control-label" >Data da submissao:</label>
					<div class="col-sm-2">
					<input type="text" name="submissao" id="inicioRelatorio"
						class="form-control data"></div>
					<!-- <label class ="col-sm-2 control-label">Terminno do Intervalo:</label>
					<div class ="col-sm-2">
					<input type="text" name="termino" id="terminoRelatorio"
						class="form-control data"></div> -->
					<input name="gerar" type="submit" class="btn btn-primary"
						value="gerar" />
				</form:form>
			</div>
			<%-- form por pessoa--%>
			<div id="form_p-pessoa">
				<div class="row">
					<form:form id="relatoriosPPessoaForm"
						enctype="multipart/form-data"
						servletRelativeAction="relatorios/p-pessoa" method="GET"
						cssClass="form-horizontal">

						<div class="form-group form-item">
							<label class="col-sm-1 control-label">Nome:</label>
							<div class="col-sm-5">
								<select id="select_pessoaRelatorio" name="id"
									class="form-control" required>
									<c:set var="part" value="${pessoas }"></c:set>
									<option value=""></option>
									<c:forEach items="${pessoas }" var="pessoa">
										<c:set var="selected" value=""></c:set>
										<c:set var="participanteSelecionado"
											value="id=${pessoa.id }"></c:set>
										<c:if test="${fn:contains(part, participanteSelecionado)}">
											<c:set var="selected" value="selected=\"selected\""></c:set>
										</c:if>
										<option value="${pessoa.id }">${pessoa.nome }</option>
									</c:forEach>
								</select>

							</div>
							<!-- div select -->
							<label class="col-sm-1 control-label">Data da submissao:</label>
							<div class="col-sm-2">
								<input type="text" name="ano" id="anoRelatorio"
									class="form-control data">

							</div>
							<div class="col-sm-1" id="submit-p-pessoa">
								<input name="gerar" type="submit" class="btn btn-primary"
									value="gerar" />
							</div>

						</div>
					</form:form>
				</div>
				
				<br /> <br />
			</div>
			</c:if>
			<!-- TAB APROVADOS -->
			<div class="tab-content">
		        <div class="tab-pane fade active in" id="tab-projetos-aprovados">
		        	<c:if test="${not empty relatorio}">
						<c:if test="${empty relatorio.projetosAprovados}"><c:if test="${empty relatorio.projetosReprovados}"><c:if test="${empty relatorio.projetosPorPessoa}">
							<div class="alert alert-warning" role="alert">Não há projetos neste periodo informado.</div>
						</c:if></c:if></c:if>
					</c:if>
					<c:if test="${not empty relatorio.projetosAprovados}">
						<table id="meus-projetos" class="display">
							<thead>
								<tr>
									<th>Coordenador do Projeto</th>
										<th>Nome do Projeto</th>
										<th>Início</th>
										<th>Término</th>
										<th>Quantidade de Bolsas</th>
										<th>Valor total de Bolsas</th>
										<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="projeto" items="${projetosAprovados}">
									<tr>
										<td>${projeto.nomeCoordenador}</td>
										<td>${projeto.nomeProjeto}</td>
										<td>${projeto.dataInicio}</td>
										<td>${projeto.dataTermino}</td>
										<td>${projeto.qtdBolsas}</td>
										<td>${projeto.valorTotalBolsas}</td>
										<td>
											<a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nomeProjeto}</a>
											</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
	        	</div>
	        	</div>
	        	<!-- TAB REPROVADOS -->
	        	<div class="tab-content">
	        	<div class="tab-pane fade active in" id="tab-projetos-aprovados">
					<c:if test="${not empty relatorio.projetosReprovados}">
						<table id="meus-projetos" class="display">
							<thead>
								<tr>
									<th>Coordenador do Projeto</th>
									<th>Nome do Projeto</th>
									<th>Submissão</th>
									<th>Avaliação</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="projeto" items="${projetosReprovados}">
									<tr>
										<td>${projeto.nomeCoordenador}</td>
										<td>${projeto.nomeProjeto}</td>
										<td>${projeto.dataDeSubimissao}</td>
										<td>${projeto.dataDeAvaliacao}</td>
										<td>
											<a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nomeProjeto}</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
	        	</div>
	        	
			</div>
			<!-- TAB P/ PESSOA -->
			<div calss="tab-content">
				<div class="tab-pane fade active in" id="tab-p-pessoa">
					<c:if test="${not empty relatorio.projetosPorPessoa}">
						<table id="meus-projetos" class="display">
							<thead>
								<tr>
									<th>Nome do Projeto</th>
									<th>Vínculo</th>
									<!-- <th>Carga Horária</th>
									<th>Valor da Bolsa</th> -->
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="projeto" items="${projetosPorPessoa}">
									<tr>
										<td>${projeto.nomeProjeto}</td>
										<td>${projeto.vinculo}</td>
										<%-- <td>${projeto.cargaHoraria}</td>
										<td>${projeto.valorBolsa}</td> --%>
										<td><a
											href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nomeProjeto}</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../modulos/footer.jsp" />
	<script type="text/javascript">

	// checa se o documento foi carregado
	$(document).ready(function () {
		//PROJETOS APROVADOS
		$("#form_aprovados").hide();
		$("#form_reprovados").hide();
		$("#form_p-pessoa").hide();
	
		
	$("#relatorio").change(function() {
		$("#form_aprovados").hide();
		$("#form_reprovados").hide();
		$("#form_p-pessoa").hide();

			var opcao_select = $("#relatorio option:selected").text();
			if (opcao_select == "PROJETOS APROVADOS") {
				$("#form_aprovados").slideToggle("slow");
			}
			if (opcao_select == "PROJETOS REPROVADOS") {
				$("#form_reprovados").slideToggle("slow");
			}if (opcao_select == "PROJETOS POR USUÁRIO") {
				$("#form_p-pessoa").slideToggle("slow");
			}
		});
	
	
	});
</script>
</body>	

</html>