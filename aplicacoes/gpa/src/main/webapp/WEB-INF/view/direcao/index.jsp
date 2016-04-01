<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>Projetos</title>
		<jsp:include page="../modulos/header-estrutura.jsp" />
	</head>
	<body>
		<jsp:include page="../modulos/header.jsp" />
		<div class="container">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Projetos</h3>
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
					<ul class="nav nav-tabs">
						<li class="active"><a aria-expanded="true" href="#tab-em-tramitacao" data-toggle="tab">Em tramitação <span class="badge">${projetosSubmetidos.size() }</span></a></li>
						<li class=""><a aria-expanded="false" href="#tab-avaliados" data-toggle="tab">Avaliados <span class="badge">${projetosAvaliados.size() }</span></a></li>
					</ul>
				    <div class="tab-content">
				       	<div  class="tab-pane fade active in" id="tab-em-tramitacao">
				       		<c:if test="${empty projetosSubmetidos}">
								<div class="alert alert-warning" role="alert">Não há projetos em tramitação.</div>
							</c:if>
							<c:if test="${not empty projetosSubmetidos}">
								<table id="projetos-em-tramitacao" class="display">
									<thead>
										<tr>
											<th>Código</th>
											<th>Nome</th>
											<th>Status</th>
											<th>Data Submissão</th>
											<th>Coordenador</th>
											<th>Parecerista</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="projeto" items="${projetosSubmetidos}">
											<tr>
												<td>${projeto.codigo }</td>
												<td><a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nome}</a></td>
												<td>${projeto.status.descricao}</td>
												<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.submissao }" /></td>
												<td><a href="<c:url value="/pessoa/detalhes/${projeto.coordenador.id}" ></c:url>">${projeto.coordenador.nome}</a></td>
												<td>
													<c:if test="${projeto.parecer == null }">-</c:if>
													<c:if test="${projeto.parecer != null }">
														<a href="<c:url value="/pessoa/detalhes/${projeto.parecer.parecerista.id}" ></c:url>">${projeto.parecer.parecerista.nome}</a>
													</c:if>
												</td>
												<td class="acoes">
													<c:if test="${projeto.status == 'SUBMETIDO'}">
														<a id="atribuirParecerista" title="Atribuir parecerista" href="<c:url value="/direcao/atribuir-parecerista/${projeto.id}" ></c:url>">
															<button class="btn btn-primary btn-xs"><i class="fa fa-user"></i></button>
														</a>
													</c:if>
													<c:if test="${projeto.status == 'AGUARDANDO_AVALIACAO'}">
														<a id="avaliarProjeto" title="Avaliar" data-toggle="modal"href="<c:url value="/direcao/avaliar/${projeto.id}" ></c:url>">
															<button class="btn btn-primary btn-xs"><i class="fa fa-check-square-o"></i></button>
														</a>
													</c:if>
													<c:if test="${projeto.status == 'AGUARDANDO_PARECER'}">
														<a id="alterarParecerista" title="Alterar parecerista" data-toggle="modal"href="<c:url value="/direcao/alterar-parecerista/${projeto.id}" ></c:url>">
															<button class="btn btn-primary btn-xs"><i class="fa fa-edit"></i></button>
														</a>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:if>
				       	</div>
				       	<div class="tab-pane fade" id="tab-avaliados">
				       		<c:if test="${empty projetosAvaliados}">
								<div class="alert alert-warning" role="alert">Não há projetos avaliados.</div>
							</c:if>
							<c:if test="${not empty projetosAvaliados}">
								<input type="hidden" name="parecerId" value="${parecerId}">

								<table id="projetos-avaliados-diretor" class="display">
									<thead>
										<tr>
											<th>Código</th>
											<th>Nome</th>
											<th>Status</th>
											<th>Data Avaliação</th>
											<th>Coordenador</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="projeto" items="${projetosAvaliados}">
											<tr>
												<td>${projeto.codigo }</td>
												<td><a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nome}</a></td>
												<td>${projeto.status.descricao}</td>
												<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.avaliacao }" /></td>
												<td><a href="<c:url value="/pessoa/detalhes/${projeto.coordenador.id}" ></c:url>">${projeto.coordenador.nome}</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:if>
				       	</div>
			       	</div>
		       	</div>
		   </div><!-- /panel -->
		</div> <!-- /container -->
		
		<!-- Modal Excluir Projeto -->
		<div class="modal fade" id="confirm-delete">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;<span class="sr-only">Close</span></button>
		       			<h4 class="modal-title">Excluir</h4>
					</div>
					<div class="modal-body"></div>
					<div class="modal-footer">
						<a href="#" class="btn btn-danger">Excluir</a>
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					</div>
				</div>
			</div>
		</div>
		
		<jsp:include page="../modulos/footer.jsp" />
		
		<script type="text/javascript">
			$('#menu-direcao').addClass('active');
		</script>

	</body>
</html>