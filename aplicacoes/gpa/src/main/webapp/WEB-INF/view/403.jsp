<%-- <!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
	<title>403 - Acesso negado</title>
	<link href="<c:url value="/webjars/bootstrap/3.1.1/css/bootstrap.min.css" />" rel="stylesheet" />
	<script src="<c:url value="/webjars/jquery/2.1.0/jquery.js" />"></script>
	<script src="<c:url value="/webjars/bootstrap/3.1.1/js/bootstrap.min.js" />"></script>

</head>
<body>
	<div class="container">
		<div class="col-lg-8 col-lg-offset-2 text-center">
			<div class="logo">
				<h1>403</h1>
				<p class="lead text-muted">${message}</p>
			</div>
			<br>
		</div>
	</div>
</body>
</html>
--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<jsp:include page="modulos/header-estrutura.jsp" />
<title>403 - Acesso Negado</title>
</head>
<body>
	<div class="container">
		<jsp:include page="modulos/header.jsp" />
		
		<div class = "error">
		    <div class="col-lg-8 col-lg-offset-2 text-center">
				<div class="logo">
					<h1>403</h1>
				</div>
				<p class="lead text-muted">${message}</p>
				<br>
				<div class="col-lg-6 col-lg-offset-3">
					<a class="btn btn-warning btn-group-justified" href="<c:url value='sessaousuario'/>">Voltar</a>
				</div>
			</div>
		</div>
	</div>
	
	<jsp:include page="modulos/footer.jsp" />
</body>
</html>