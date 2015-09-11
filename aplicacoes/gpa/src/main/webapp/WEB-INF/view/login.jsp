<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Login</title>
	<link rel="shortcut icon" href="<c:url value="/resources/images/gpa-icone.jpg" />" />
	
	<link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>" rel="stylesheet">
	<link href="<c:url value="/resources/css/font-awesome.min.css"/>" rel="stylesheet"/>
	<link href="<c:url value="/resources/css/bootstrapValidator.css" />" rel="stylesheet" />
	<link href="<c:url value="/resources/css/style-login.css"/>" rel="stylesheet">
</head>

<body>
	<div id="container">
		<div id="header">
			<img alt="Sistema de Afastamento de Professores" src="<c:url value="/resources/images/gpa-logo.jpg" />">
		</div>

		<div class="formulario">
			<div class="login-text">
				<span>Faça seu login</span>
			</div>
			<form:form id="login-form" role="form" servletRelativeAction="/j_spring_security_check" method="post" class="form-horizontal">
				<c:if test="${not empty erro}">
					<div class="login-error">${erro }</div>
				</c:if>

				<div class="form-group">
					<div id="inputLogin" class="form-inline input-group input-login">
						<span class="input-group-addon"><i class="fa fa-user"></i></span>
						<input type="text" name="j_username" id="cpf" class="form-control" placeholder="cpf" required="required">
					</div>
				</div>

				<div class="form-group">
					<div id="inputSenha" class="form-inline input-group input-login">
						<span class="input-group-addon"><i class="fa fa-lock"></i></span>
						<input type="password" name="j_password" id="senha" class="form-control" placeholder="senha" required="required">
					</div>
				</div>
				
				<!-- <div id="div-captcha-erro">
					<div id="captcha-login" class=""></div>
				</div> -->

				<div class="controls">
					<input id="btn-login" class="btn btn-default" name="submit" type="submit" value="Login" value="Login"/>
				</div>
			</form:form>
		</div>
	</div>
	<footer>
		<img id="logo-npi" alt="Núcleo de Práticas em Informática - Campus da UFC em Quixadá" 
			src="<c:url value="/resources/images/logo-npi.png" />">
		<p>Desenvolvido por <a href="http://www.npi.quixada.ufc.br" target="_blank">Núcleo de Práticas em Informática</a></p>
		<p><a href="http://www.quixada.ufc.br" target="_blank">Universidade Federal do Ceará - Campus Quixadá</a></p>
	</footer>

	<!-- <script src="https://www.google.com/recaptcha/api.js" async defer></script> -->
	<script src="<c:url value="/resources/js/jquery-2.1.4.min.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrapValidator.min.js" />"></script>
	<script src="<c:url value="/resources/js/language/pt_BR.js" />"></script>
	<script src="<c:url value="/resources/js/gpa-login.js" />"></script>

</body>
</html>