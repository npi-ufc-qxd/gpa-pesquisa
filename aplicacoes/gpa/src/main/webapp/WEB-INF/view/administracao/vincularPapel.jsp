<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Vincular Papel ao Usuário</title>
</head>

<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Vincular Papel ao Usuário</h3>
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
				<div class="bs-component">
					<h3>${pessoa.nome }
						<small class="text-muted">${pessoa.email}</small>
					</h3>
					<form:form id="responderForm" role="form" commandName="pessoa"
						class="form-horizontal" method="POST"
						servletRelativeAction="/administracao/pessoa/vincular">
						<form:hidden path="cpf"/>
						<form:hidden path="id"/>
						<c:if test="${papeis != null }">
							<div class="admradio col-sm-6">
								<c:forEach var="papel" items="${papeis}"
									varStatus="papelId">
									 <div class="admradio-success">
										<c:if test="${papel.status}">
											<c:if test="${papel.nome == 'COORDENADOR'}">
												<input type="checkbox" name="coordenador" 
												id="coordenador" checked disabled="disabled"/>
								            	<label for="coordenador">
							            			<strong>Coordenação</strong>
							            		</label>
											</c:if>
											<c:if test="${papel.nome != 'COORDENADOR'}">
												<input type="checkbox" name="papeis[${papelId.index }].id" 
												id="papeis[${papelId.index }].id" value="${papel.id}" checked/>
								            	<label for="papeis[${papelId.index }].id">
									            	<c:if test="${papel.nome == 'DIRECAO'}">
									            		<strong>Direção</strong>
									            	</c:if>
									            	<c:if test="${papel.nome == 'ADMINISTRACAO'}">
									            		<strong>Administração</strong>
									            	</c:if>
								            	</label>
							            	</c:if>
										</c:if>
										<c:if test="${!papel.status}">
											<c:if test="${papel.nome == 'COORDENADOR'}">
												<input type="checkbox" name="coordenador" 
												id="coordenador" checked disabled="disabled"/>
								            	<label for="coordenador">
							            			<strong>Coordenação</strong>
							            		</label>
											</c:if>
											<c:if test="${papel.nome != 'COORDENADOR'}">
												<input type="checkbox" name="papeis[${papelId.index }].id" 
												id="papeis[${papelId.index }].id" value="${papel.id}"/>
								            	<label for="papeis[${papelId.index }].id">
									            	<c:if test="${papel.nome == 'DIRECAO'}">
									            		<strong>Direção</strong>
									            	</c:if>
									            	<c:if test="${papel.nome == 'ADMINISTRACAO'}">
									            		<strong>Administração</strong>
									            	</c:if>
								            	</label>
								            </c:if>
										</c:if>
									</div>
								</c:forEach>
							</div>
						</c:if>
						<div class="col-sm-12"><hr></div>
						<div class="form-group">
							<div class="col-sm-2">				
								<button type="submit" class="btn btn-primary">
									Vincular&nbsp;<i class="glyphicon glyphicon-floppy-disk"></i>
								</button>
							</div>
							<div class="col-sm-2">				
								<a id="editar" href="<c:url value="/administracao" ></c:url>"
								class="btn btn-default">Administração&nbsp;
									<i class="glyphicon glyphicon-arrow-left"></i>
								</a>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>
