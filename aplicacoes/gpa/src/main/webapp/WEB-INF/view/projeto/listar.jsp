<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Projetos</title>
</head>
<body>
	<div class="container">
		<jsp:include page="../modulos/header.jsp" />
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
		<div id="tabs" class="tabs">
		    <nav>
		        <ul>
		            <li><a href="#section-1"><span>Meus Projetos&nbsp; <i class="fa fa-folder-open-o"></i></span></a></li>
		            <li><a href="#section-2"><span>Aguardando Parecer&nbsp; <i class="fa fa-gavel"></i></span></a></li>
		            <li><a href="#section-3"><span>Projetos Avaliados&nbsp; <i class="fa fa-check-square-o"></i></span></a></li>
		        </ul>
		    </nav>
		    <div class="content">
		        <section id="section-meus-projetos">
		            <c:if test="${empty projetos}">
						<div class="alert alert-warning" role="alert">Não há projetos cadastrados.</div>
					</c:if>
					<c:if test="${not empty projetos}">
						<table id="meus-projetos" class="table table-striped projetos">
							<thead>
								<tr>
									<th>Nome</th>
									<th>Status</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="projeto" items="${projetos}">
									<tr>
										<td>
											<a href="<c:url value="/projeto/${projeto.id}/detalhes" ></c:url>">${projeto.nome}</a>
										</td>
										<td>${projeto.status.descricao}</td>
										<td class="acoes">
											<c:if test="${projeto.status == 'NOVO'}">
												<a id="submeter" data-toggle="modal" data-target="#confirm-submit" href="#"
													data-href="<c:url value="/projeto/${projeto.id}/submeter" ></c:url>" data-name="${projeto.nome }">
													<button class="btn btn-primary">Submeter&nbsp;<i class="fa fa-cloud-upload"></i></button>
												</a>
	
												<a id="editar" href="<c:url value="/projeto/${projeto.id}/editar" ></c:url>">
													<button class="btn btn-primary">Editar&nbsp;<i class="fa fa-edit"></i></button>
												</a>
	
												<a id="excluir" data-toggle="modal" data-target="#confirm-delete" href="#" 
													data-href="<c:url value="/projeto/${projeto.id}/excluir"></c:url>" data-name="${projeto.nome }">
													<button class="btn btn-danger">Excluir&nbsp;<i class="fa fa-trash-o"></i></button>
												</a>
											</c:if>
											<sec:authorize ifAnyGranted="ROLE_DIRETOR">
												<c:if test="${projeto.status == 'SUBMETIDO'}">
													<a id="atribuirParecerista" href="<c:url value="/projeto/diretor/${projeto.id}/atribuirParecerista" ></c:url>">
														<button class="btn btn-primary">Atribuir Parecerista&nbsp;<i class="fa fa-user"></i></button>
													</a>
												</c:if>
											</sec:authorize>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
		        </section>
		        <section id="section-projetos-aguardando-parecer">
		            <c:if test="${empty projetosAguardandoParecer}">
						<div class="alert alert-warning" role="alert">Não há projetos aguardando parecer.</div>
					</c:if>
					<c:if test="${not empty projetosAguardandoParecer}">
						<div class="panel panel-default">
							<table id="projetos-aguardando-parecer" class="table table-striped projetos">
								<thead>
									<tr>
										<th>Nome</th>
										<th>Autor</th>
										<th>Prazo</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="projeto" items="${projetosAguardandoParecer}">
										<tr>
											<td>
												<a href="<c:url value="/projeto/${projeto.id}/detalhes" ></c:url>">${projeto.nome}</a>
											</td>
											<td><a href="<c:url value="/usuario/${projeto.autor.id}/detalhes" ></c:url>">${projeto.autor.nome}</a></td>
											<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.parecer.prazo }" /></td>
											<td class="acoes">
												<c:if test="${projeto.status == 'AGUARDANDO_PARECER'}">
													<a id="emitirParecer" href="<c:url value="/projeto/${projeto.id}/emitirParecer" ></c:url>">
														<button class="btn btn-primary">Emitir Parecer&nbsp; <i class="fa fa-gavel"></i></button>
													</a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
		        </section>
		        <section id="section-projetos-avaliados">
		            <c:if test="${empty projetosAvaliados}">
						<div class="alert alert-warning" role="alert">Não há projetos avaliados.</div>
					</c:if>
					<c:if test="${not empty projetosAvaliados}">
						<div class="panel panel-default">
							<table id="projetos-avaliados" class="table table-striped projetos">
								<thead>
									<tr>
										<th>Nome</th>
										<th>Autor</th>
										<th>Status</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="projeto" items="${projetosAvaliados}">
										<tr>
											<td>
												<a href="<c:url value="/projeto/${projeto.id}/detalhes" ></c:url>">${projeto.nome}</a>
											</td>
											<td><a href="<c:url value="/usuario/${projeto.autor.id}/detalhes" ></c:url>">${projeto.autor.nome}</a></td>
											<td>${projeto.status.descricao}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
		        </section>
		    </div><!-- /content -->
		</div><!-- /tabs -->
	</div>

	<!-- Modal Excluir Projeto -->
	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
        			<h4 class="modal-title" id="excluirModalLabel">Excluir</h4>
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<a href="#" class="btn btn-danger">Excluir</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal Submeter Projeto -->
	<div class="modal fade" id="confirm-submit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
        			<h4 class="modal-title" id="submeterModalLabel">Submeter</h4>
        			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<a href="#" class="btn btn-primary">Submeter</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
	
	<script>
	    new CBPFWTabs( document.getElementById( 'tabs' ) );
	</script>

</body>
</html>

