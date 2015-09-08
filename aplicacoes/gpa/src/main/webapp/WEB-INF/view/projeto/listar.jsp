<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
		            <li class="active"><a aria-expanded="true" href="#tab-meus-projetos" data-toggle="tab">Meus projetos <span class="badge">${projetos.size() }</span></a></li>
		            <li class=""><a aria-expanded="false" href="#tab-em-participacao" data-toggle="tab">Em Participação <span class="badge">${projetosParticipante.size() }</span></a></li>		            
		            <li class=""><a aria-expanded="false" href="#tab-avaliados" data-toggle="tab">Projetos Avaliados <span class="badge">${projetosAvaliados.size() }</span></a></li>
		            <li class=""><a aria-expanded="false" href="#tab-parecer" data-toggle="tab">Emissão de Parecer <span class="badge">${projetosAguardandoParecer.size() }</span></a></li>
		        </ul>
		        <div class="tab-content">
		        	<div class="tab-pane fade active in" id="tab-meus-projetos">
		        		<c:if test="${empty projetos}">
							<div class="alert alert-warning" role="alert">Não há projetos cadastrados.</div>
						</c:if>
						<c:if test="${not empty projetos}">
							<table id="meus-projetos" class="display">
								<thead>
									<tr>
										<th>Código</th>
										<th>Nome</th>
										<th>Status</th>
										<th>Data Submissão</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="projeto" items="${projetos}">
										<tr>
											<td>${projeto.codigo }</td>
											<td>
												<a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nome}</a>
											</td>
											<td>${projeto.status.descricao}</td>
											<td>
												<c:if test="${empty projeto.submissao }">-</c:if>
												<fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.submissao }" />
											</td>
											<td class="acoes">
												<c:if test="${projeto.status == 'NOVO'}">
													<a id="submeter" data-toggle="modal" data-target="#confirm-submit" href="#" title="Submeter"
														data-href="<c:url value="/projeto/submeter/${projeto.id}" ></c:url>" data-name="${projeto.nome }">
														<button class="btn btn-primary btn-xs"><i class="fa fa-cloud-upload"></i></button>
													</a>
		
													<a id="editar" href="<c:url value="/projeto/editar/${projeto.id}" ></c:url>" title="Editar">
														<button class="btn btn-primary btn-xs"><i class="fa fa-edit"></i></button>
													</a>
		
													<a id="excluir" data-toggle="modal" data-target="#confirm-delete" href="#" title="Excluir"
														data-href="<c:url value="/projeto/excluir/${projeto.id}"></c:url>" data-name="${projeto.nome }">
														<button class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i></button>
													</a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
		        	</div><!-- tab-meus-projetos -->
		        	
		        	<div class="tab-pane fade" id="tab-em-participacao">
		        		<c:if test="${empty projetosParticipante}">
							<div class="alert alert-warning" role="alert">Não há projetos cadastrados.</div>
						</c:if>
						<c:if test="${not empty projetosParticipante}">
							<table id="projetos-em-participacao" class="display">
								<thead>
									<tr>
										<th>Código</th>
										<th>Nome</th>	
										<th>Status</th>										
										<th>Data de Submissão</th>
										<th>Autor</th>									
									</tr>
								</thead>
								<tbody>
									<c:forEach var="projetosParticipante" items="${projetosParticipante}">
										<tr>
											<td>${projetosParticipante.codigo }</td>
											<td><a href="<c:url value="/projeto/detalhes/${projetosParticipante.id}" ></c:url>">${projetosParticipante.nome}</a></td>
											<td>${projetosParticipante.status.descricao}</td>											
											<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projetosParticipante.submissao }" /></td>
											<td><a href="<c:url value="/projeto/detalhes/${projetosParticipante.id}" ></c:url>">${projetosParticipante.autor.nome}</a></td>					
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
		        	</div><!-- tab-em-participacao -->
		        	
			        <div class="tab-pane fade" id="tab-avaliados">
			            <c:if test="${empty projetosAvaliados}">
							<div class="alert alert-warning" role="alert">Não há projetos avaliados.</div>
						</c:if>
						<c:if test="${not empty projetosAvaliados}">
							<table id="projetos-avaliados" class="display">
								<thead>
									<tr>
										<th>Código</th>
										<th>Nome</th>
										<th>Status</th>
										<th>Data Submissão</th>
										<th>Data Avaliação</th>
										<th>Autor</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="projeto" items="${projetosAvaliados}">
										<tr>
											<td>${projeto.codigo }</td>
											<td><a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nome}</a></td>
											<td>${projeto.status.descricao}</td>
											<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.submissao }" /></td>
											<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.avaliacao }" /></td>
											<td><a href="<c:url value="/pessoa/detalhes/${projeto.autor.id}" ></c:url>">${projeto.autor.nome}</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
			        </div><!-- tab-avaliados -->
			        
			        <div class="tab-pane fade" id="tab-parecer">
			            <c:if test="${empty projetosAguardandoParecer}">
							<div class="alert alert-warning" role="alert">Não há projetos aguardando parecer.</div>
						</c:if>
						<c:if test="${not empty projetosAguardandoParecer}">
							<table id="projetos-aguardando-parecer" class="display">
								<thead>
									<tr>
										<th>Nome</th>
										<th>Autor</th>
										<th>Data Submissão</th>
										<th>Prazo</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="projeto" items="${projetosAguardandoParecer}">
										<tr>
											<td><a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nome}</a></td>
											<td><a href="<c:url value="/pessoa/detalhes/${projeto.autor.id}" ></c:url>">${projeto.autor.nome}</a></td>
											<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.submissao }" /></td>
											<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.parecer.prazo }" /></td>
											<td class="acoes">
												<c:if test="${projeto.status == 'AGUARDANDO_PARECER'}">
													<a id="emitirParecer" title="Emitir parecer" href="<c:url value="/projeto/emitir-parecer/${projeto.id}" ></c:url>">
														<button class="btn btn-primary btn-xs"><i class="fa fa-gavel"></i></button>
													</a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
			        </div><!-- tab-parecer -->
		        </div><!-- tab-content -->
		    </div><!-- /panel-body -->
		</div><!-- /panel -->
	</div><!-- /container -->

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

	<!-- Modal Submeter Projeto -->
	<div class="modal fade" id="confirm-submit">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
	       			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;<span class="sr-only">Close</span></button>
	       			<h4 class="modal-title">Submeter</h4>
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
	
	<script type="text/javascript">
		$('#menu-projetos').addClass('active');
	</script>

</body>
</html>

