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
	<title>Atribuir Relator</title>
</head>
<body>
	<c:if test="${action eq 'atribuir' }">
		<c:set var="titulo" value="Atribuir Relator"></c:set>
		<c:set var="relator" value="Relator:"></c:set>
	</c:if>
	<c:if test="${action eq 'alterar' }">
		<c:set var="titulo" value="Alterar Relator "></c:set>
		<c:set var="relator" value="Novo Relator:"></c:set>
	</c:if>
	
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">${titulo }</h3>
			</div>
			<div class="panel-body">
				<form:form id="atribuirRelatorForm" commandName="parecer" servletRelativeAction="/direcao/atribuir-relator" method="POST" cssClass="form-horizontal">
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
							<label class="col-sm-2 control-label">Relator atual:</label>
							<div class="col-sm-8 value-label">
								<label>${projeto.parecerRelator.relator.nome }</label>
							</div>
						</div>
					</c:if>
					<div class="form-group form-item">
						<label for="relator" class="col-sm-2 control-label"><span class="required">*</span> ${relator}</label>
						<div class="col-sm-4">
							<select id="relator" name="relatorId" class="form-control">
								<c:forEach items="${usuarios}" var="usuario">
									<option value="${usuario.id}">${usuario.nome}</option>
								</c:forEach>
							</select>
							<form:errors path="relator"></form:errors>
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