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
	<title>Atribuir Parecerista</title>
</head>
<body>

	<div class="container">
		<jsp:include page="../modulos/header.jsp" />
		<div class="formulario" align="center">
			<h2>Atribuir Parecerista - <a href="<c:url value="/projeto/${projeto.id}/detalhes" ></c:url>">${projeto.nome}</a></h2>
			<form:form id="atribuirPareceristaForm" servletRelativeAction="/projeto/diretor/atribuirParecerista" method="POST" cssClass="form-horizontal">
				<input type="hidden" name="projetoId" value="${projeto.id}">
				<div class="form-group form-item">
					<label for="parecerista" class="col-sm-2 control-label"><span class="required">*</span> Parecerista:</label>
					<div class="col-sm-4">
						<select id="parecerista" name="pareceristaId" class="form-control">
							<c:forEach items="${usuarios}" var="usuario">
								<option value="${usuario.id}">${usuario.nome}</option>
							</c:forEach>
						</select>
						<c:if test="${not empty erro_parecerista}">
							<div class="error-validation">
								<span>${erro_parecerista}</span>
							</div>
						</c:if>
					</div>
				</div>
				
				<div class="form-group form-item">
					<label for="prazo" class="col-sm-2 control-label"><span class="required">*</span> Prazo:</label>
					<div class="col-sm-2">
						<input id="prazo" type="text" name="prazo" value="${prazo}" class="form-control data" placeholder="Prazo" required="required"/>
						<c:if test="${not empty erro_prazo}">
							<div class="error-validation">
								<span>${erro_prazo}</span>
							</div>
						</c:if>
					</div>
				</div>
				
				<div class="form-group form-item">
					<label for="observacao" class="col-sm-2 control-label">Observações:</label>
					<div class="col-sm-10">
						<textarea id="observacao" name="observacao" class="form-control" rows="5" placeholder="Observações" ></textarea>
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
					<a href="<c:url value="/projeto/index"></c:url>" class="btn btn-default">Cancelar</a>
				</div>
			</form:form>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp"></jsp:include>

</body>

</html>