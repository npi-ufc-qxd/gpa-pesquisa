<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-default">
  <div class="container-fluid container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<c:url value="/projeto/listar" />">GPA - Pesquisa</a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li id="menu-projetos" class=""><a href="<c:url value="/projeto/listar" />" title="Projetos">Projetos</a></li>
        <li id="menu-novo-projeto" class="">
           	<a href="<c:url value="/projeto/cadastrar" />" title="Novo Projeto">Novo Projeto</a>
       	</li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="<c:url value="/j_spring_security_logout" />" title="Sair">Sair</a></li>
      </ul>
    </div>
  </div>
</nav>
