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
	<title>Atribuir Parecerista</title>
</head>
<body>
	<c:if test="${action eq 'atribuir' }">
		<c:set var="titulo" value="Atribuir Parecerista"></c:set>
		<c:set var="parecerista" value="Parecerista:"></c:set>
	</c:if>
	<c:if test="${action eq 'alterar' }">
		<c:set var="titulo" value="Alterar Parecerista "></c:set>
		<c:set var="parecerista" value="Novo Parecerista:"></c:set>
	</c:if>
	
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">${titulo }</h3>
			</div>
			<div class="panel-body">
				<form:form id="atribuirPareceristaForm" commandName="parecer" servletRelativeAction="/direcao/atribuir-parecerista" method="POST" cssClass="form-horizontal">
					<form:hidden path="id"/>
					<input type="hidden" name="projetoId" value="${projeto.id}">
					<input type="hidden" name="action" value="${action}">
					<div class="form-group">
						<label class="col-sm-2 control-label">Projeto:</label>
						<div class="col-sm-8 value-label">
							<label>${projeto.nome }</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">Coordenador:</label>
						<div class="col-sm-8 value-label">
							<label>${projeto.coordenador.nome }</label>
						</div>
					</div>
					<c:if test="${action eq 'alterar' }">
						<div class="form-group">
							<label class="col-sm-2 control-label">Parecerista atual:</label>
							<div class="col-sm-8 value-label">
								<label>${projeto.parecer.parecerista.nome }</label>
							</div>
						</div>
					</c:if>
					<div class="form-group form-item">
						<label for="parecerista" class="col-sm-2 control-label"><span class="required">*</span> ${parecerista}</label>
						<div class="col-sm-4">
							<select id="parecerista" name="pareceristaId" class="form-control">
								<c:forEach items="${usuarios}" var="usuario">
									<option value="${usuario.id}">${usuario.nome}</option>
								</c:forEach>
							</select>
							<form:errors path="parecerista"></form:errors>
						</div>
					</div>
					
					<div class="form-group form-item">
						<label for="prazo" class="col-sm-2 control-label"><span class="required">*</span> Prazo:</label>
						<div class="col-sm-2">
							<form:input id="prazo" path="prazo" cssClass="form-control data" placeholder="Prazo" required="required" />
							<form:errors path="prazo" cssClass="error-validation"></form:errors>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-sm-2"></div>
						<div class="col-sm-2">
							<span class="campo-obrigatorio"><span class="required">*</span> Campos obrigatórios</span>
						</div>
					</div>
	
					<div class="controls">
						<input name="salvar" type="submit" class="btn btn-primary" value="Salvar" />
						<a href="javascript:history.back();" class="btn btn-default">Cancelar</a>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp"></jsp:include>
	
	<script type="text/javascript">
		$('#menu-projetos').addClass('active');
	</script>
</body>

</html>