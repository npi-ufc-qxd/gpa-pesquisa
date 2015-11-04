<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-default">
  <div class="container-fluid container">
    <div class="navbar-header">
    	<a href="<c:url value="/projeto/" />"><img id="logo" alt="GPA-Pesquisa" src="<c:url value="/resources/images/gpa-logo.jpg"/>"></a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li id="menu-projetos" class=""><a href="<c:url value="/projeto" />" title="Projetos">Projetos</a></li>
        <li id="menu-novo-projeto" class="">
           	<a href="<c:url value="/projeto/cadastrar" />" title="Novo Projeto">Novo Projeto</a>
       	</li>
       	<sec:authorize ifAnyGranted="DIRECAO">
      		<li id="menu-direcao" class="">
	           	<a href="<c:url value="/direcao" />" title="Direção">Direção</a>
	       	</li>
       	</sec:authorize>
       	<sec:authorize ifAnyGranted="ADMINISTRACAO">
      		<li id="menu-direcao" class="">
	           	<a href="<c:url value="/administracao" />" title="Administração">Administração</a>
	       	</li>
       	</sec:authorize>
      </ul>
      
      <ul class="nav navbar-nav navbar-right">

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-expanded="false">${usuario.nome }
						<span class="caret"></span>
				</a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="<c:url value="/j_spring_security_logout" />"
							title="Sair">Sair</a></li>
					</ul></li>
			</ul>
    </div>
  </div>
</nav>
