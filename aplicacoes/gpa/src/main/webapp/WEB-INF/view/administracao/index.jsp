<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
	<head>
		<title>Administração</title>
		<jsp:include page="../modulos/header-estrutura.jsp" />
	</head>
	<body>
		<jsp:include page="../modulos/header.jsp" />
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Administração</h3>
				</div>
				<div class="panel-body">
					<c:if test="${not empty erro}">
						<div class="alert alert-danger alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<c:out value="${erro}"></c:out>
						</div>
					</c:if>
					<c:if test="${not empty info}">
						<div class="alert alert-success alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<c:out value="${info}"></c:out>
						</div>
					</c:if>
					<div class="buscar-pessoa" align="left">
						<h3>Buscar usuários</h3>
						<form:form id="formBuscarPessoa" role="form" servletRelativeAction="/administracao/buscar" method="POST" class="bs-component">	
							<div class="form-group">
							  <div class="input-group">
								<input id="busca" name="busca" type="text" class="form-control" placeholder="Nome ou CPF" size="40" required="required" value="${busca }" autofocus="autofocus"/>
							    <span class="input-group-btn">
							    	<button class="btn btn-default" name="submit" type="submit"><span class="glyphicon glyphicon-search"></span> Buscar</button>
							    </span>
							  </div>
							</div>
						</form:form>
				
						<c:if test="${not empty pessoas}">
						    <table class="table table-striped">
						        <thead class="thead">
						            <tr>
						                <th>Usuário</th>
						                <th>Email</th>
						                <th></th>
						            </tr>
						        </thead>
						        <tbody>
									<c:forEach var="pessoa" items="${pessoas}">
							            <tr>
							                <td><strong>${pessoa.nome}</strong></td>
							                <td>${pessoa.email}</td>
							                <td align="right">
							                	<a data-toggle="modal" data-target="#confirm-vincular" href="#"
							                	data-href="<c:url value="/administracao/pessoa/vincular/${pessoa.cpf }"></c:url>"
							                	title="Vincular papel ao usuário"
							                	data-name="${pessoa.nome }" data-id="${pessoa.cpf }"  
							                	class="btn btn-primary btn-sm"> Vincular papel</a>
							                </td>
							            </tr>
									</c:forEach>
						        </tbody>
						    </table>
						</c:if>
					</div><!--/buscar  -->
				</div><!-- /panel body -->
			</div><!-- /panel -->
		</div> <!-- /container -->
		
		<!-- Modal Vincular Participante -->
		<div class="modal fade" id="confirm-vincular">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;<span class="sr-only">Close</span></button>
		       			<h4 class="modal-title">Vincular papel ao usuário</h4>
					</div>
					<div class="modal-body"></div>
					<div class="modal-footer">
						<a href="#" class="btn btn-primary">Vincular</a>
						<button type="button" class="btn btn-warning" data-dismiss="modal">Cancelar</button>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../modulos/footer.jsp" />
	</body>
</html>