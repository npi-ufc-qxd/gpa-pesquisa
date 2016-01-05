<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Login</title>
	<link rel="shortcut icon" href="<c:url value="/resources/images/gpa-icone.jpg" />" />
	
	<link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>" rel="stylesheet">
	<link href="<c:url value="/webjars/font-awesome/4.5.0/css/font-awesome.min.css"/>" rel="stylesheet"/>
	<link href="<c:url value="/webjars/bootstrapvalidator/0.5.3/css/bootstrapValidator.css" />" rel="stylesheet" />
	<link href="<c:url value="/resources/css/style-login.css"/>" rel="stylesheet">
</head>

<body onload='document.loginForm.j_username.focus();'>
	<div id="container">
		<div id="header">
			<img alt="Sistema de Afastamento de Professores" src="<c:url value="/resources/images/gpa-logo.jpg" />">
		</div>
		<div class="formulario">
			<div class="login-text">
				<span>Faça seu login</span>
			</div>

			<form id="loginForm" name="loginForm" action="<c:url value='j_spring_security_check' />" method='POST' class="form-horizontal">
				<c:if test="${not empty erro}">
					<div class="login-error">${erro }</div>
				</c:if>
				
				<div class="form-group form-item">
					<div id="inputLogin" class="form-inline input-group input-login">
						<span class="input-group-addon"><i class="fa fa-user"></i></span>
						<input class="form-control" type='text' name='j_username' placeholder="cpf" required="required"/>
					</div>
				</div>
				
				<div class="form-group form-item">
					<div id="inputSenha" class="form-inline input-group input-login">
						<span class="input-group-addon"><i class="fa fa-lock"></i></span>
						<input class="form-control" type='password' name='j_password' placeholder="senha" required="required"/>
					</div>
				</div>
				
				<div class="controls">
					<input id="btn-login" class="btn btn-primary" type="submit" value="Login"/>
				</div>
			</form>
		</div>
	</div>
	<footer>
		<img id="logo-npi" alt="Núcleo de Práticas em Informática - Campus da UFC em Quixadá" 
			src="<c:url value="/resources/images/logo-npi.png" />">
		<p>Desenvolvido por <a href="http://www.npi.quixada.ufc.br" target="_blank">Núcleo de Práticas em Informática</a></p>
		<p><a href="http://www.quixada.ufc.br" target="_blank">Universidade Federal do Ceará - Campus Quixadá</a></p>
	</footer>
	<script src="<c:url value="/webjars/jquery/2.1.4/jquery.min.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js" />"></script>
	<script src="<c:url value="/webjars/bootstrapvalidator/0.5.3/js/bootstrapValidator.min.js" />"></script>
	<script src="<c:url value="/resources/js/language/pt_BR.js" />"></script>
	<script src="<c:url value="/resources/js/gpa-login.js" />"></script>
</body>
</html>