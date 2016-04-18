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
		            <li class="active"><a aria-expanded="true" href="#tab-meus-projetos" data-toggle="tab">Meus projetos <span class="badge">${projetosNaoHomologados.size()}</span></a></li>
		            <li class=""><a aria-expanded="false" href="#tab-em-participacao" data-toggle="tab">Participações <span class="badge">${participacoesEmProjetos.size() }</span></a></li>
		            <li class=""><a aria-expanded="false" href="#tab-homologados" data-toggle="tab">Projetos Homologados <span class="badge">${projetosHomologados.size() }</span></a></li>
		            <li class=""><a aria-expanded="false" href="#tab-parecer" data-toggle="tab">Emissão de Parecer <span class="badge">${projetosAguardandoParecer.size() }</span></a></li>
		            <li class=""><a aria-expanded="false" href="#tab-parecer-emitidos" data-toggle="tab">Pareceres Emitidos <span class="badge">${projetosParecerEmitido.size() }</span></a></li>
		            <li class=""><a aria-expanded="false" href="#tab-projetos-aguardando-avaliacao" data-toggle="tab">Projetos a Avaliar <span class="badge">${projetosAguardandoAvaliacao.size() }</span></a></li>
		            <li class=""><a aria-expanded="false" href="#tab-projetos-avaliados" data-toggle="tab">Projetos Avaliados <span class="badge">${projetosAvaliados.size() }</span></a></li>
		        </ul>
		        <div class="tab-content">
		        	<div class="tab-pane fade active in" id="tab-meus-projetos">
			        	<c:if test="${empty projetosNaoHomologados}">
							<div class="alert alert-warning" role="alert">Não há projetos cadastrados.</div>
						</c:if>
							
						<c:if test="${not empty projetosNaoHomologados}">
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
									<c:forEach var="projetoNaoHomologado" items="${projetosNaoHomologados}">
										<tr>
											<td>${projetoNaoHomologado.codigo }</td>
											<td>
												<a href="<c:url value="/projeto/detalhes/${projetoNaoHomologado.id}" ></c:url>">${projetoNaoHomologado.nome}</a>
											</td>
											<td>${projetoNaoHomologado.status.descricao}</td>
											<td>
												<c:if test="${empty projetoNaoHomologado.submissao }">-</c:if>
												<fmt:formatDate pattern="dd/MM/yyyy" value="${projetoNaoHomologado.submissao }" />
											</td>
													
											<td class="acoes">
												<c:if test="${projetoNaoHomologado.status == 'NOVO' or projetoNaoHomologado.status == 'RESOLVENDO_PENDENCIAS' or projetoNaoHomologado.status == 'RESOLVENDO_PENDENCIAS_RELATOR'}">
													<a id="submeter" data-toggle="modal" data-target="#confirm-submit" href="#" title="Submeter"
														data-href="<c:url value="/projeto/submeter/${projetoNaoHomologado.id}" ></c:url>" data-name="${projetoNaoHomologado.nome }">
														<button class="btn btn-primary btn-xs"><i class="fa fa-cloud-upload"></i></button>
													</a>
															
													<c:if test="${(not empty projetoNaoHomologado.inicio) && (not empty projetoNaoHomologado.termino)}">
														<a id="vincular" href="<c:url value="/projeto/participacoes/${projetoNaoHomologado.id}" ></c:url>" title="Vincular participantes">
															<button class="btn btn-primary btn-xs"><i class="fa fa-users"></i></button>
														</a>
													</c:if>
														
													<a id="editar" href="<c:url value="/projeto/editar/${projetoNaoHomologado.id}" ></c:url>" title="Editar">
														<button class="btn btn-primary btn-xs"><i class="fa fa-edit"></i></button>
													</a>
			
													<a id="excluir" data-toggle="modal" data-target="#confirm-delete" href="#" title="Excluir"
														data-href="<c:url value="/projeto/excluir/${projetoNaoHomologado.id}"></c:url>" data-name="${projetoNaoHomologado.nome }">
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
		        		<c:if test="${empty participacoesEmProjetos}">
						<div class="alert alert-warning" role="alert">Não há participações em projetos.</div>
						</c:if>
						<c:if test="${not empty participacoesEmProjetos}">
							<table id="minhas-participacoes" class="display">
								<thead>
									<tr>
										<th class="col-md-1 col-md-offset-1">Código</th>
										<th>Projeto</th>
										<th>Coordenador(a)</th>
										<th>Status</th>
										<th>Participação</th>
										<th>Duração</th>
										<th class="col-md-1 col-md-offset-1">Carga horária mensal</th>
										<th>Valor bolsa mensal</th>										
									</tr>
								</thead>
								<tbody>
									<c:forEach var="participacao" items="${participacoesEmProjetos}">
										<tr>
											<td>${participacao.projeto.codigo}</td>
											<td><a href="<c:url value="/projeto/detalhes/${participacao.projeto.id}" ></c:url>">${participacao.projeto.nome}</a></td>
											<td><a href="<c:url value="/pessoa/detalhes/${participacao.projeto.coordenador.id}" ></c:url>">${participacao.projeto.coordenador.nome}</a></td>					
											<td>${participacao.projeto.status.descricao}</td>
											<td>${participacao.tipo.descricao}</td>
											<td><fmt:formatNumber minIntegerDigits="2">${participacao.mesInicio}</fmt:formatNumber>/${participacao.anoInicio}
											<fmt:formatNumber minIntegerDigits="2">${participacao.mesTermino}</fmt:formatNumber>/${participacao.anoTermino}</td>
											<td><fmt:formatNumber minIntegerDigits="2">${participacao.cargaHorariaMensal}</fmt:formatNumber></td>
											<td><fmt:formatNumber type="CURRENCY" currencyCode="BRL">${participacao.bolsaValorMensal}</fmt:formatNumber></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
		        	</div><!-- tab-em-participacao -->

			        <div class="tab-pane fade" id="tab-homologados">
				    	<c:if test="${empty projetosHomologados}">
							<div class="alert alert-warning" role="alert">Não há projetos homologados.</div>
						</c:if>
						<c:if test="${not empty projetosHomologados}">
							<table id="projetos-homologados" class="display">
								<thead>
									<tr>
										<th>Código</th>
										<th>Nome</th>
										<th>Status</th>
										<th>Data Submissão</th>
										<th>Data Homologação</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="projeto" items="${projetosHomologados}">
										<tr>
											<td>${projeto.codigo }</td>
											<td><a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nome}</a></td>
											<td>${projeto.status.descricao}</td>
											<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.submissao }" /></td>
											<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.homologacao }" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
				    </div><!-- tab-homologados -->
				        	
			        <div class="tab-pane fade" id="tab-parecer">
			            <c:if test="${empty projetosAguardandoParecer}">
							<div class="alert alert-warning" role="alert">Não há projetos aguardando parecer.</div>
						</c:if>
						<c:if test="${not empty projetosAguardandoParecer}">
							<table id="projetos-aguardando-parecer" class="display">
								<thead>
									<tr>
										<th>Código</th>
										<th>Projeto</th>
										<th>Coordenador</th>
										<th>Data Submissão</th>
										<th>Prazo</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="projeto" items="${projetosAguardandoParecer}">
										<tr>
											<td>${projeto.codigo }</td>
											<td><a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nome}</a></td>
											<td><a href="<c:url value="/pessoa/detalhes/${projeto.coordenador.id}" ></c:url>">${projeto.coordenador.nome}</a></td>
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
			        
			        <div class="tab-pane fade" id="tab-parecer-emitidos">
			            <c:if test="${empty projetosParecerEmitido}">
							<div class="alert alert-warning" role="alert">Não há projetos com parecer emitido.</div>
						</c:if>
						<c:if test="${not empty projetosParecerEmitido}">
							<table id="projetos-parecer-emitido" class="display">
								<thead>
									<tr>
										<th>Código</th>
										<th>Projeto</th>
										<th>Coordenador</th>
										<th>Parecer</th>
										<th>Data do Parecer</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="projeto" items="${projetosParecerEmitido}">
										<tr>
											<td>${projeto.codigo }</td>
											<td><a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nome}</a></td>
											<td><a href="<c:url value="/pessoa/detalhes/${projeto.coordenador.id}" ></c:url>">${projeto.coordenador.nome}</a></td>
											<td>${projeto.parecer.status }</td>
											<td>
												<fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.parecer.dataRealizacao }" />
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
			        </div><!-- tab-parecer emitidos -->
		        	
		        	 <div class="tab-pane fade" id="tab-projetos-aguardando-avaliacao">
			            <c:if test="${empty projetosAguardandoAvaliacao}">
							<div class="alert alert-warning" role="alert">Não há projetos aguardando avaliação.</div>
						</c:if>
						<c:if test="${not empty projetosAguardandoAvaliacao}">
							<table id="projetos-aguardando-avaliacao" class="display">
								<thead>
									<tr>
										<th>Código</th>
										<th>Projeto</th>
										<th>Coordenador</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="projeto" items="${projetosAguardandoAvaliacao}">
										<tr>
											<td>${projeto.codigo }</td>
											<td><a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nome}</a></td>
											<td><a href="<c:url value="/pessoa/detalhes/${projeto.coordenador.id}" ></c:url>">${projeto.coordenador.nome}</a></td>
											<td class="acoes">
												<a id="avaliarProjeto" title="Avaliar Projeto" href="<c:url value="/projeto/avaliar/${projeto.id}" ></c:url>">
													<button class="btn btn-primary btn-xs"><i class="fa fa-gavel"></i></button>
												</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
			        </div><!-- tab-aguardando avaliacao -->
			        
			        <div class="tab-pane fade" id="tab-projetos-avaliados">
			            <c:if test="${empty projetosAvaliados}">
							<div class="alert alert-warning" role="alert">Não há projetos avaliados.</div>
						</c:if>
						<c:if test="${not empty projetosAvaliados}">
							<table id="projetos-avaliados" class="display">
								<thead>
									<tr>
										<th>Código</th>
										<th>Projeto</th>
										<th>Coordenador</th>
										<th>Avaliação</th>
										<th>Data Avaliação</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="projeto" items="${projetosAvaliados}">
										<tr>
											<td>${projeto.codigo }</td>
											<td><a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nome}</a></td>
											<td><a href="<c:url value="/pessoa/detalhes/${projeto.coordenador.id}" ></c:url>">${projeto.coordenador.nome}</a></td>
											<td>${projeto.parecerRelator.status}</td>
											<td>
												<fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.parecerRelator.data}" />
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
			        </div><!-- tab-projetos-avaliados -->
		        	
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
					<a href="#" class="btn btn-danger btn-sm">Excluir</a>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Cancelar</button>
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
					<a href="#" class="btn btn-primary btn-sm">Submeter</a>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Cancelar</button>
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

