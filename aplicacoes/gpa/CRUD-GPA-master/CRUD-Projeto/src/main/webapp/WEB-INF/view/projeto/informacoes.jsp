<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Informações do Projeto</title>
		<link href="<c:url value="/webjars/bootstrap/3.1.1/css/bootstrap.min.css" />" rel="stylesheet" />
		<link href="<c:url value="/resources/css/estilo.css" />" rel="stylesheet"/>
	</head>

	
<body>
	<header class="jumbotron" id="header-page">
		<div class="row">
			<div class="col-md-6">
				<img width="370" src="../resources/images/brasao-qxd.png" alt="Brasão UFC Quixadá">
			</div>
			<div class="col-md-6">
			</div>
		</div>
	</header>

	<div align="center">
		<ul class="pager">
			<li class="previous"><a href="<c:url value="/listar" />">&larr; Voltar para Listagem</a></li>
		</ul>
	</div>

	<div class="container" >
		<div class="novo-projeto" align="left">
			<div class="form" align="center" style="margin-bottom: 30px">
				<h2>Informações do Projeto</h2>
				<form:form commandName="projeto" id="reg" >
	
					<form:label path="id">ID:</form:label>
					<br>
					<form:input path="id" readonly="true" class="form-control"/>
					<br>
	
					<form:label path="nome">Nome:</form:label>
					<br>
					<form:input path="nome"  readonly="true" class="form-control"/>
					<br>
					<form:errors class="invalid" path="nome" />
	
					<form:label path="status">Status:</form:label>
					<br>
					<form:input path="status" readonly="true" class="form-control"/>
					<br>
					
					<form:label path="descricao">Descrição:</form:label>
					<br>
					<form:input path="descricao" readonly="true" class="form-control"/>
					<br>
					
					<form:label path="atividades">Atividades:</form:label>
					<br>
					<form:input path="atividades" readonly="true" class="form-control"/>
					<br>
					
					<form:label path="participantes">Participantes:</form:label>
					<br>
					<form:input path="participantes" readonly="true" class="form-control"/>
					<br>
					
					<form:label path="local">Local:</form:label>
					<br>
					<form:input path="local" readonly="true" class="form-control"/>
					<br>
	
					<form:label path="quantidadeBolsa">Número de Bolsas:</form:label>
					<br>
					<form:input path="quantidadeBolsa" readonly="true" class="form-control"/>
					<br>
					
					
					
					<c:forEach var="documento" items="${projeto.documentos}">
					<a href="<c:url value="/files/${documento.id}"></c:url>">${documento.nomeOriginal}</a>
					<br>
					</c:forEach>
					
					
					<br>
					<form:label path="inicio">Início:</form:label>
					<br>
					<form:input path="inicio" readonly="true" class="form-control"/>
					<br>
					
					<form:label path="termino">Término:</form:label>
					<br>
					<form:input path="termino" readonly="true" class="form-control"/>
					<br>
	
					<br>
	
				</form:form>
			</div>
		</div>
	</div>

	<ul class="pager">
		<li class="previous"><a href="/exemplo-jpa-spring-mvc/listar" style="margin-bottom: 80px">&larr; Voltar para
				Listagem</a></li>
	</ul>

	<jsp:include page="../modulos/footer.jsp" />

	<script src="<c:url value="/webjars/jquery/2.1.0/jquery.min.js" />"></script>
	<script src="<c:url value="/webjars/jquery-maskedinput/1.3.1/jquery.maskedinput.min.js" />"></script>
	<script src="<c:url value="/webjars/jquery-validation/1.12.0/jquery.validate.min.js" />"></script>
</body>
</html>
