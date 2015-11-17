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
				<label class="col-sm-2 control-label">Tipo de Relatorio:</label>
				<div class="col-sm-6">
					<select id="relatorio" name="relatorioParam" class="form-control">
						<option value=""></option>
						<option value="APROVADOS">PROJETOS APROVADOS</option>
						<option value="REPROVADOS">PROJETOS REPROVADOS</option>
						<option value="POR_USUARIO">PROJETOS POR USUÁRIO</option>
					</select>
				</div>
				
			</div>

			<br />
					<br />
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
							<label for="idParticipantes" class="col-sm-1 control-label">Nome:</label>
							<div class="col-sm-5">
								<select id="participante" name="participanteSelecionado"
									class="form-control">
									<c:set var="part" value="${pessoas }"></c:set>
									<c:forEach items="${pessoas }" var="participante">
										<c:set var="selected" value=""></c:set>
										<c:set var="participanteSelecionado"
											value="id=${participante.id }"></c:set>
										<c:if test="${fn:contains(part, participanteSelecionado)}">
											<c:set var="selected" value="selected=\"selected\""></c:set>
										</c:if>
										<option value="${participante.id }" ${selected }>${participante.nome }</option>
									</c:forEach>
								</select>

							</div>
							<!-- div select -->
							<label class="col-sm-1 control-label">Data da submissao:</label>
							<div class="col-sm-2">
								<input type="text" name="submissao" id="anoRelatorio"
									class="form-control data">

							</div>
							<div class="col-sm-1">
								<input name="gerar" type="submit" class="btn btn-primary"
									value="gerar" />
							</div>

						</div>
					</form:form>
				</div>
				<!-- div row -->
				
				<br /> <br />
			</div>
			<div class="tab-content">
		        <div class="tab-pane fade active in" id="tab-projetos-aprovados">
		        	<c:if test="${empty relatorio}">
						<div class="alert alert-warning" role="alert">Não há projetos neste periodo informado.</div>
					</c:if>
					<c:if test="${not empty relatorio}">
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
	        	
	        	<div class="tab-pane fade active in" id="tab-projetos-reprovados">
		        	<c:if test="${empty relatorio}">
						<div class="alert alert-warning" role="alert">Não há projetos neste periodo informado.</div>
					</c:if>
					<c:if test="${not empty relatorio}">
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
	<%-- <div class="container">
		<jsp:include page="../modulos/header.jsp" />
		<c:if test="${not empty erro}">
			<div class="alert alert-danger alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<c:out value="${erro}"></c:out>
			</div>
		</c:if>

		<div class="formulario">
			<h2>Relatórios</h2>
			
			<!-- Forma de visualização opcional -->
			<div  class="col-sm-6 control-label">

				<div class="formulario">
				<h3>Projetos Aprovados</h3>
							<form:form method="GET" action="/gpa-pesquisa/projeto/relatorio-aprovados" cssClass="form-horizontal">
								<h5>Intervalo de Início</h5>
								<input data-provide="datepicker" name="iniInterInicio" type="text">
								<input data-provide="datepicker" name="fimInterInicio" type="text">
								<h5>Intervalo de Término</h5>
								<input data-provide="datepicker" name="iniInterTermino"type="text">
								<input data-provide="datepicker" name="fimInterTermino"type="text"><br>
								<button type="submit" class="btn btn-default">Gerar</button>
							</form:form>

							<a href="<c:url value="/projeto/relatorio-aprovados" />"
								title="Visualizar Relatorios Reprovados">Visualizar Relatórios
								Aprovados</a>
				</div>
				
				<div class="formulario">
							<h3>Projetos Reprovados</h3>
					<form:form method = "GET" action="/gpa-pesquisa/projeto/relatorio-reprovados" cssClass = "form-horizontal">
							<h5>Intervalo da Submissão</h5>
							<input data-provide="datepicker" name="iniInter" type="text">
							<input data-provide="datepicker" name="fimInter" type="text"><br>
							<button type="submit" class="btn btn-default">Gerar</button>
					</form:form>
				</div>
				
				<div class="formulario">
							<h3>Projetos por docente</h3><br>
							<form:form method ="GET" action="/gpa-pesquisa/projeto/relatorio-projeto-por-docente"  cssClass = "form-horizontal">
								<h4>Participantes:</h4>
									<select id="participantes" name="idParticipantes" class="form-control" multiple="multiple">
										<c:set var="part" value="${projeto.participantes }"></c:set>
										<c:forEach items="${participantes }" var="participante">
											<c:set var="selected" value=""></c:set>
											<c:set var="idParticipante" value="id=${participante.id }"></c:set>
											<option value="${participante.id }" ${selected }>${participante.nome }</option>
										</c:forEach>
									</select>
									<span class="campo-obrigatorio"><span class="required">*</span> Campo obrigatório</span>
									<h5>Ano</h5>
									<input id="pickerYear" type="text" name="ano" />
									<span class="add-on"><i class="icon-th"></i></span> <br>     
									<button type="submit" class="btn btn-default">Gerar</button>
							</form:form>
						
					</div>
				</div>
			</div>
		</div>		
		
</body> --%>
</html>