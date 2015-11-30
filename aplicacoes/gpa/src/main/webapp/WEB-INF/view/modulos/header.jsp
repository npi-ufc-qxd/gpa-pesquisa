<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-default">
	<div class="container-fluid container">
		<div class="navbar-header">
			<a href="<c:url value="/projeto/" />"><img id="logo"
				alt="GPA-Pesquisa"
				src="<c:url value="/resources/images/gpa-logo.jpg"/>"></a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li id="menu-projetos" class=""><a
					href="<c:url value="/projeto" />" title="Projetos">Projetos</a></li>
				<li id="menu-novo-projeto" class=""><a
					href="<c:url value="/projeto/cadastrar" />" title="Novo Projeto">Novo
						Projeto</a></li>
				<sec:authorize ifAnyGranted="DIRECAO">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" 
							data-toggle="dropdown" role="button" 
		              		aria-haspopup="true" aria-expanded="false">
		              		Direção <span class="caret"></span>
		              	</a>
						<ul class="dropdown-menu " role="menu">
							<li id="menu-direcao" class=""><a
								href="<c:url value="/direcao" />" title="Projetos">
								<i class="glyphicon glyphicon-briefcase"></i> Projetos</a>
							</li>
							<li id="menu-relatorio" class="">
					           	<a href="<c:url value="/direcao/relatorios" />" title="Direção">
					           	<i class="glyphicon glyphicon-book"></i> Relatórios</a>
					       	</li>
							<li role="separator" class="divider"></li>
							<li id="menu-direcao" class=""><a
								href="<c:url value="/direcao/buscar" />" title="Buscar Participantes">
								<i class="glyphicon glyphicon-search"></i> Buscar Participantes</a>
							</li>
							
						</ul>
					</li>
				</sec:authorize>
				<sec:authorize ifAnyGranted="ADMINISTRACAO">
					<li id="menu-administracao" class=""><a
						href="<c:url value="/administracao" />" title="Administração">Administração</a>
					</li>
				</sec:authorize>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle glyphicon glyphicon-menu-down btn-lg" 
					data-toggle="dropdown" role="button" 
	              	aria-haspopup="true" aria-expanded="false"></a>
					<ul class="dropdown-menu " role="menu">
						<li><a href="<c:url value="/j_spring_security_logout" />"
							title="Sair"><i class="glyphicon glyphicon-log-out"></i> Sair</a></li>
					</ul>
				</li>
			</ul>
			<p class="navbar-right navbar-text">
				${sessionScope.usuario}
			</p>
		</div>
	</div>
</nav>
