<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Visualizar Relatórios</title>
</head>
<body>
	<div class="container">
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
							<form:form method ="GET" action="/gpa-pesquisa/projeto/relatorio-p-docente"  cssClass = "form-horizontal">
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
		<jsp:include page="../modulos/footer.jsp" />
</body>
</html>