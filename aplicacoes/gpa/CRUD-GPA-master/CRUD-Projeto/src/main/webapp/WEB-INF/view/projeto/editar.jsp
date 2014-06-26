<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Editar Projeto</title>
</head>
<body>
	
<head>

<link href="<c:url value="/webjars/bootstrap/3.1.1/css/bootstrap.min.css" />" rel="stylesheet" />
<script src="<c:url value="/webjars/jquery/2.1.0/jquery.min.js" />"></script>
<script src="<c:url value="/webjars/jquery-maskedinput/1.3.1/jquery.maskedinput.min.js" />"></script>
<script src="<c:url value="/webjars/jquery-validation/1.12.0/jquery.validate.min.js" />"></script>
</head>

<body>
	<ul class="pager">
		<li class="previous"><a href="<c:url value="/listar" />">&larr; Voltar para Listagem</a></li>
	</ul>
	<div class="container" >
	<div class="novo-projeto" align="left">
		<div class="form" align="center">
			<h2>Editar Projeto</h2>
			<form:form commandName="projeto" id="reg"
				action="editarProjetoForm">

				<form:label path="codigo">C�digo</form:label>
				<br>
				<form:input path="codigo" readonly="true" class="form-control"/>
				<br>

				<form:label path="nome">Nome:</form:label>
				<br>
				<form:input path="nome" class="form-control"/>
				<br>
				<form:errors class="invalid" path="nome" />

				<form:label path="status">Status:</form:label>
				<br>
				<form:input path="status" readonly="true" class="form-control"/>
				<br>
				
				<form:label path="descricao">Descri��o:</form:label>
				<br>
				<form:input path="descricao" class="form-control"/>
				<br>
				
				<form:label path="atividades">Atividades:</form:label>
				<br>
				<form:input path="atividades" class="form-control"/>
				<br>
				
				<form:label path="participantes">Participantes:</form:label>
				<br>
				<form:input path="participantes" class="form-control"/>
				<br>
				
				<form:label path="local">Local:</form:label>
				<br>
				<form:input path="local" class="form-control"/>
				<br>

				<form:label path="numero_bolsas">N�mero de Bolsas:</form:label>
				<br>
				<form:input path="numero_bolsas" class="form-control"/>
				<br>
				
				<form:label path="inicio">In�cio:</form:label>
				<br>
				<form:input path="inicio" class="form-control"/>
				<br>
				
				<form:label path="termino">T�rmino:</form:label>
				<br>
				<form:input path="termino" class="form-control"/>
				<br>
				
				<form:label path="documentos">Arquivo:</form:label>
				<br>
				<form:input type="file" class="form-control" path="documentos" />

				<br>
				<input type="submit" value="Salvar" class="btn btn-default"/>
				<a href="<c:url value="/listar" />"><input type="button" value="Cancelar" class="btn btn-default"/></a>

			</form:form>
		</div>
	</div>
</div>
</body>
</html>