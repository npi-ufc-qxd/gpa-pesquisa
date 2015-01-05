<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

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
		            <li><a href="#meus-projetos"><span>Meus Projetos&nbsp; <i class="fa fa-folder-open-o"></i></span></a></li>
		            <li><a href="#section-projetos-submetidos"><span>Projetos Submetidos&nbsp; <i class="fa fa-cloud-upload"></i></span></a></li>
		            <li><a href="#section-projetos-avaliados"><span>Projetos Avaliados&nbsp; <i class="fa fa-check-square-o"></i></span></a></li>
		            <li><a href="#section-participantes"><span>Participantes&nbsp; <i class="fa fa-users"></i></span></a></li>
		        </ul>
		    </nav>
		    <div class="content">
		        <section id="meus-projetos">
		            <c:if test="${empty projetos}">
						<div class="alert alert-warning" role="alert">Não há projetos cadastrados.</div>
					</c:if>
					<c:if test="${not empty projetos}">
						<div class="panel panel-default">
							<table id="table-meus-projetos" class="table table-striped projetos">
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
											<td><a href="<c:url value="/projeto/${projeto.id}/detalhes" ></c:url>">${projeto.nome}</a></td>
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
														<a id="atribuirParecerista"href="<c:url value="/projeto/diretor/${projeto.id}/atribuirParecerista" ></c:url>">
															<button class="btn btn-primary">Atribuir Parecerista&nbsp;<i class="fa fa-user"></i></button>
														</a>
													</c:if>
												</sec:authorize>
												<c:if test="${projeto.status == 'AGUARDANDO_AVALIACAO'}">
													<a id="avaliarProjeto" data-toggle="modal" href="<c:url value="/projeto/diretor/${projeto.id}/avaliar" ></c:url>">
														<button class="btn btn-primary">Avaliar Projeto&nbsp;<i class="fa fa-check-square-o"></i></button>
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
		        <section id="section-projetos-submetidos">
		            <c:if test="${empty projetosSubmetidos}">
						<div class="alert alert-warning" role="alert">Não há projetos submetidos.</div>
					</c:if>
					<c:if test="${not empty projetosSubmetidos}">
						<div class="panel panel-default">
							<table id="table-projetos-submetidos" class="table table-striped projetos">
								<thead>
									<tr>
										<th>Nome</th>
										<th>Autor</th>
										<th>Status</th>
										<th>Parecerista</th>
										<th>Prazo</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="projeto" items="${projetosSubmetidos}">
										<tr>
											<td><a href="<c:url value="/projeto/${projeto.id}/detalhes" ></c:url>">${projeto.nome}</a></td>
											<td><a href="<c:url value="/usuario/${projeto.autor.id}/detalhes" ></c:url>">${projeto.autor.nome}</a></td>
											<td>${projeto.status.descricao}</td>
											<td>
												<c:if test="${projeto.parecer == null }">-</c:if>
												<c:if test="${projeto.parecer != null }">
													<a href="<c:url value="/usuario/${projeto.parecer.parecerista.id}/detalhes" ></c:url>">${projeto.parecer.parecerista.nome}</a>
												</c:if>
											</td>
											<td>
												<c:if test="${projeto.parecer == null }">-</c:if>
												<c:if test="${projeto.parecer != null }"><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.parecer.prazo}" /></c:if>
											</td>
											<td class="acoes">
												<c:if test="${projeto.status == 'SUBMETIDO'}">
													<a id="atribuirParecerista" href="<c:url value="/projeto/diretor/${projeto.id}/atribuirParecerista" ></c:url>">
														<button class="btn btn-primary">Atribuir Parecerista&nbsp;<i class="fa fa-user"></i></button>
													</a>
												</c:if>
												<c:if test="${projeto.status == 'AGUARDANDO_AVALIACAO'}">
													<a id="avaliarProjeto" data-toggle="modal"href="<c:url value="/projeto/diretor/${projeto.id}/avaliar" ></c:url>">
														<button class="btn btn-primary">Avaliar Projeto&nbsp;<i class="fa fa-check-square-o"></i></button>
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
							<input type="hidden" name="parecerId" value="${parecerId}">
							<table id="table-projetos-avaliados" class="table table-striped projetos">
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
											<td><a href="<c:url value="/projeto/${projeto.id}/detalhes" ></c:url>">${projeto.nome}</a></td>
											<td><a href="<c:url value="/usuario/${projeto.autor.id}/detalhes" ></c:url>">${projeto.autor.nome}</a></td>
											<td>${projeto.status.descricao}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
		        </section>
		        <section id="section-participantes">
		        	<c:if test="${empty participantes}">
						<div class="alert alert-warning" role="alert">Não há participantes nos projetos.</div>
					</c:if>
					<c:if test="${not empty participantes}">
						<div class="panel panel-default">
							<table id="table-participantes" class="table table-striped projetos">
								<thead>
									<tr>
										<th>Nome</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="participante" items="${participantes}">
										<tr>
											<td>
												<a href="<c:url value="/usuario/${participante.id}/detalhes" ></c:url>">${participante.nome}</a>
											</td>
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