<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
	<jsp:include page="../modulos/header-estrutura.jsp" />
	<title>Informações do Usuário</title>
</head>

<body>
	<div class="container">
		<jsp:include page="../modulos/header.jsp" />
		<div class="formulario detalhes">
			<input id="projetoId" type="hidden" value="${projeto.id }"/>
			<h2>${usuario.nome }</h2>
			<h3>Informações</h3><hr>
			<div class="form-group">
				<label class="col-sm-2 control-label field">Nome:</label>
				<div class="col-sm-4 field-value">
					<label>${usuario.nome }</label>
				</div>
			</div>
			
			<h3>Projetos que coordena</h3><hr>
			<div class="form-group">
				<div class="col-sm-12 field-value">
					<c:if test="${empty usuario.projetos }">
						<label>-</label>
					</c:if>
				</div>
				<div class="col-sm-12 field-value">
					<c:forEach items="${usuario.projetos }" var="projeto">
						<a href="<c:url value="/projeto/${projeto.id }/detalhes" />" class="col-sm-12" style="padding-left: 0px;">${projeto.nome }</a>
					</c:forEach>
				</div>
			</div>
			
			<h3>Projetos de que participa</h3><hr>
			<div class="form-group">
				<div class="col-sm-12 field-value">
					<c:if test="${empty usuario.projetos }">
						<label>-</label>
					</c:if>
				</div>
				<div class="col-sm-12 field-value">
					<c:forEach items="${projetos }" var="projeto">
						<a href="<c:url value="/projeto/${projeto.id }/detalhes" />" class="col-sm-12" style="padding-left: 0px;">${projeto.nome }</a>
					</c:forEach>
				</div>
			</div>
			
		</div>
	</div>
	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>
