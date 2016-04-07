<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
	<jsp:include page="../modulos/header-estrutura.jsp" />
	<title>Informações do Projeto</title>
</head>

<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">${projeto.nome }</h3>
			</div>
	
			<div class="panel-body">
				<input id="projetoId" type="hidden" value="${projeto.id }"/>
				<div class="form-horizontal">

					<div class="col-md-8">
						<h4>Dados Gerais</h4>
					</div>

					<c:if test="${projeto.status == 'NOVO'}">
						<div class="col-md-4">
							<label style="width: 100%; text-align: right;">
								<a id="submeter" data-toggle="modal" data-target="#confirm-submit" href="#" title="Submeter" data-href="<c:url value="/projeto/submeter/${projeto.id}" ></c:url>" data-name="${projeto.nome }" class="btn btn-primary btn-sm">
									<i class="fa fa-cloud-upload"></i>
								</a>
								<c:if test="${(not empty projeto.inicio) && (not empty projeto.termino)}">
									<a id="vincular" href="<c:url value="/projeto/participacoes/${projeto.id}" ></c:url>" target="_blank" title="Vincular participantes" class="btn btn-primary btn-sm">
										<i class="fa fa-users"></i>
									</a>
								</c:if>
								<a id="editar" href="<c:url value="/projeto/editar/${projeto.id}" ></c:url>" title="Editar projeto" class="btn btn-primary btn-sm">
									<i class="fa fa-edit"></i>
								</a>
								<a id="excluir" data-toggle="modal" data-target="#confirm-delete" href="#" title="Excluir" data-href="<c:url value="/projeto/excluir/${projeto.id}"></c:url>" data-name="${projeto.nome }" class="btn btn-danger btn-sm">
									<i class="fa fa-trash-o"></i>
								</a>
							</label>
						</div>
					</c:if>

					<span class="line"></span>
			
					<div class="form-group">
						<label class="col-sm-2 control-label">Nome:</label>
						<div class="col-sm-10 value-label">
							<label>${projeto.nome }</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">Descrição:</label>
						<div class="col-sm-10 value-label">
							<label>${projeto.descricao }</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">Coordenador:</label>
						<div class="col-sm-4 value-label">
							<label><a href="<c:url value="/pessoa/detalhes/${projeto.coordenador.id}" ></c:url>">${projeto.coordenador.nome}</a></label>
						</div>

							<label class="col-sm-3 control-label">Status:</label>
							<div class="col-sm-3 value-label">
								<label>${projeto.status.descricao }</label>
							</div>
						
					</div>
			
					<div class="form-group">
						<label class="col-sm-2 control-label">Início:</label>
						<div class="col-sm-4 value-label">
							<c:if test="${empty projeto.inicio }">
								<label>-</label>
							</c:if>
							<label><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.inicio }" /></label>
						</div>
						<label class="col-sm-3 control-label">Término:</label>
						<div class="col-sm-3 value-label">
							<c:if test="${empty projeto.termino }">
								<label>-</label>
							</c:if>
							<label><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.termino }" /></label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">Data de submissão:</label>
						<div class="col-sm-4 value-label">
							<c:if test="${empty projeto.submissao }">
								<label>-</label>
							</c:if>
							<c:if test="${not empty projeto.submissao }">
								<label>
									<fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${projeto.submissao }" />
								</label>
							</c:if>
						</div>
						
						<c:if test="${not empty projeto.parecer.dataRealizacao }">
							<div class="col-sm-3 control-label">
								<label>Data de emissão do parecer:</label>
							</div>
							<div class="col-sm-3 value-label">
								<label>
									<fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${projeto.parecer.dataRealizacao }" />
								</label>
							</div>
						</c:if>
						
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">Local de execução:</label>
						<div class="col-sm-10 value-label">
							<c:if test="${empty projeto.local }">
								<label>-</label>
							</c:if>
							<c:if test="${not empty projeto.local }">
								<label>${projeto.local }</label>
							</c:if>					
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 control-label">
						<c:if test="${not empty projeto.avaliacao }">
							<label class="field">Data de avaliação:</label>
						</c:if>
						</div>
						<div class="col-sm-10 value-label">
							<c:if test="${not empty projeto.avaliacao }">
								<label><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${projeto.avaliacao }" /></label>
							</c:if>					
						</div>							
					</div>
					<hr />
					<div class="form-group">
						<label class="col-sm-2 control-label">Atividades Gerais:</label>
						<div class="col-sm-10 value-label">
							<c:if test="${empty projeto.atividades }">
								<label>-</label>
							</c:if>
							<c:if test="${not empty projeto.atividades }">
								<label>							
								${projeto.atividades }
								</label>
							</c:if>
						</div>		
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label">Participantes:</label>
						<div class="col-sm-10 value-label">
							<c:if test="${empty projeto.participacoes }">
								<label>-</label>
							</c:if>
							<c:if test="${not empty projeto.participacoes }">
								<c:forEach items="${projeto.participacoes }" var="participacao">
									<label><a href="<c:url value="/pessoa/detalhes/${participacao.participante.id}" >
									</c:url>">${participacao.participante.nome} </a>(${participacao.tipo.descricao});</label><br>
								</c:forEach>
							</c:if>
						</div>
					</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Anexos:</label>
							<div class="col-sm-10 value-label">
								<c:if test="${empty projeto.documentos }">
									<label>-</label>						
								</c:if>				
								<c:if test="${not empty projeto.documentos }">
									<c:forEach items="${projeto.documentos }" var="documento">
										<label><a href="<c:url value="/documento/${projeto.id }/${documento.id }" ></c:url>">${documento.nome }</a></label><br>							
									</c:forEach>
								</c:if>
							</div>
						</div>
							
					<h4 class="subtitle">Avaliação</h4>
						<span class="line"></span>
						<div class="form-group">
							<div class="col-sm-12">		
								<p class="value-label">${projeto.parecer.observacao}</p>				
							</div>				   		
						</div>
						
			
					<div class="form-group">
						<label class="col-sm-2 control-label">Observação Avaliação:</label>
						<div class="col-sm-10 value-label">
							<c:if test="${empty projeto.observacaoAvaliacao }">
								<label>-</label>
							</c:if>
							<c:if test="${not empty projeto.observacaoAvaliacao }">
								<label>							
								${projeto.observacaoAvaliacao }
								</label>
							</c:if>
						</div>		
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">Oficio:</label>
						<div class="col-sm-10 value-label">
							<c:if test="${empty projeto.oficio }">
								<label>-</label>						
							</c:if>				
							<c:if test="${not empty projeto.oficio }">
								<label><a href="<c:url value="/documento/${projeto.id }/${projeto.oficio.id }" ></c:url>">${projeto.oficio.nome }</a></label><br>							
							</c:if>
							</div>
						</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">Ata:</label>
						<div class="col-sm-10 value-label">
							<c:if test="${empty projeto.ata }">
								<label>-</label>
							</c:if>
							<c:if test="${not empty projeto.ata }">
									<label><a href="<c:url value="/documento/${projeto.id }/${projeto.ata.id }" ></c:url>">${projeto.ata.nome }</a></label><br>							
							</c:if>
							</div>
						</div>
					
						<c:if test="${projeto.parecer != null}">
							<h4 class="subtitle">Parecer</h4>
							<span class="line"></span>
							<div class="form-group">
								<label class="col-sm-2 control-label">Parecerista:</label>
								<div class="col-sm-4 value-label">
									<label><a href="<c:url value="/pessoa/detalhes/${projeto.parecer.parecerista.id}" ></c:url>">${projeto.parecer.parecerista.nome}</a></label>
								</div>
								
								<c:if test="${projeto.status == 'AGUARDANDO_PARECER'}">
									<label class="col-sm-2 control-label">Prazo parecer:</label>
									<div class="col-sm-4 value-label">
										<label><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.parecer.prazo }" /></label>
									</div>						
								</c:if>
								
								<c:if test="${projeto.status != 'AGUARDANDO_PARECER'}">
									<label class="col-sm-2 control-label">Posicionamento:</label>
									<div class="col-sm-4 value-label">
										<label>${projeto.parecer.status }</label>
									</div>						
								</c:if>
								
								<label class="col-sm-2 control-label">Parecer:</label>
								<div class="col-sm-10 value-label">
									<label>${projeto.parecer.parecer}</label>
								</div>
							</div>	
							<div class="form-group">
								<label class="col-sm-2 control-label">Anexo:</label>
								<div class="col-sm-10 value-label">
									<a href="<c:url value="/documento/${projeto.id }/${projeto.parecer.documento.id }" />">${projeto.parecer.documento.nome }</a>
								</div>
							</div>
						</c:if>		
										
						<c:if test="${permissao != 'participante' }">
						<h4 class="subtitle">Comentários</h4>
						<span class="line"></span>
						<div id="comentarios" class="col-sm-12">
							<c:forEach items="${projeto.comentarios }" var="comentario">
								<div class="panel panel-default">
									<div class="panel-heading">${comentario.autor.nome } 
										<span class="date-comment"><fmt:formatDate pattern="dd/MM/yyyy" value="${comentario.data }"/> - 
										<fmt:formatDate pattern="HH:mm" value="${comentario.data }" /></span>
									</div>
									<div class="panel-body">${comentario.texto }</div>
								</div>
							</c:forEach>
						</div>
					</c:if>
						<form id="comentarForm">
							<div id="div-comentario" class="col-sm-12 form-item">
								<div id="campo-comentario" class="col-sm-12">
									<textarea id="comentario" name="comentario" class="form-control" rows="5" placeholder="Comentário" required="required"></textarea>
								</div>
							</div>
							<br>
							<div class="controls">
								<input id="comentar" name="comentar" type="submit" class="btn btn-primary" value="Enviar" />
							</div>
						</form>
				
				</div> <!-- form-horizontal -->
			</div> <!-- /panel-body -->
		</div> <!-- /panel -->
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
		$(function(){
			$('#menu-projetos').addClass('active');
		});
	</script>
</body>
</html>
