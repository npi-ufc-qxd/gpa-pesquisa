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
					<h4>Dados Gerais</h4>
					<span class="line"></span>
			
					<div class="form-group">
						<label class="col-sm-2 control-label">Nome:</label>
						<div class="col-sm-8 value-label">
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
						<label class="col-sm-2 control-label">Status:</label>
						<div class="col-sm-4 value-label">
							<label>${projeto.status.descricao }</label>
						</div>
						<label class="col-sm-2 control-label">Autor:</label>
						<div class="col-sm-4 value-label">
							<a href="<c:url value="/pessoa/detalhes/${projeto.autor.id}" ></c:url>">${projeto.autor.nome}</a>
						</div>
					</div>
			
					<div class="form-group">
						<label class="col-sm-2 control-label field">Início:</label>
						<div class="col-sm-4 field-value">
							<c:if test="${empty projeto.inicio }">
								<label>-</label>
							</c:if>
							<label><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.inicio }" /></label>
						</div>
						<label class="col-sm-2 control-label field">Término:</label>
						<div class="col-sm-4 field-value">
							<c:if test="${empty projeto.termino }">
								<label>-</label>
							</c:if>
							<label><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.termino }" /></label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label field">Data de submissão:</label>
						<div class="col-sm-4 field-value">
							<c:if test="${empty projeto.submissao }">
								<label class="datas">-</label>
							</c:if>
							<c:if test="${not empty projeto.submissao }">
								<label class="datas">
									<fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${projeto.submissao }" />
								</label>
							</c:if>
						</div>
						<div class="col-sm-2 control-label">
							<c:if test="${not empty projeto.parecer.dataRealizacao }">
								<label class="field">Data de emissão do parecer:</label>
							</c:if>
						</div>
						<div class="col-sm-4 field-value">
							<c:if test="${not empty projeto.parecer.dataRealizacao }">
								<label class="datas">
									<fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${projeto.parecer.dataRealizacao }" />
								</label>
							</c:if>
						</div>
					</div>
				<br>
					<div class="form-group">
						<label class="col-sm-2 control-label field">Local:</label>
						<div class="col-sm-4 field-value">
							<c:if test="${empty projeto.local }">
								<label>-</label>
							</c:if>
							<c:if test="${not empty projeto.local }">
								<label>${projeto.local }</label>
							</c:if>					
						</div>
						<label class="col-sm-2 control-label field">Carga horária:</label>
						<div class="col-sm-4 field-value">
							<c:if test="${empty projeto.cargaHoraria }">
								<label>-</label>
							</c:if>
							<c:if test="${not empty projeto.cargaHoraria }">
								<label>${projeto.cargaHoraria }</label>
							</c:if>
							
						</div>
					</div>
					<br>
					<div class="form-group">
						<label class="col-sm-2 control-label field">Bolsas:</label>
						<div class="col-sm-4 field-value">
							<c:if test="${empty projeto.quantidadeBolsa }">
								<label>-</label>
							</c:if>
							<c:if test="${not empty projeto.quantidadeBolsa }">
								<label>${projeto.quantidadeBolsa }</label>
							</c:if>
							
						</div>
						<label class="col-sm-2 control-label field">Valor da bolsa:</label>
						<div class="col-sm-4 field-value">
							<c:if test="${empty projeto.valorDaBolsa or projeto.valorDaBolsa == 0.0 }">
								<label>-</label>
							</c:if>
							<c:if test="${not empty projeto.valorDaBolsa and projeto.valorDaBolsa != 0.0 }">
								<label><fmt:formatNumber value="${projeto.valorDaBolsa}" type="currency"/></label>
							</c:if>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 control-label">
						<c:if test="${not empty projeto.avaliacao }">
								<label class="field">Data de avaliação:</label>
							</c:if>
						</div>
						<div class="col-sm-6 field-value">
							<c:if test="${not empty projeto.avaliacao }">
								<label><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${projeto.avaliacao }" /></label>
							</c:if>					
						</div>							
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label field">Descrição:</label>
						<div class="col-sm-10 field-value">
							<article><label>${projeto.descricao }</label></article>
						</div>						
					</div>
					<br>
					<div class="form-group">
						<label class="col-sm-2 control-label field">Atividades:</label>
						<div class="col-sm-10 field-value">
							<c:if test="${empty projeto.atividades }">
								<label>-</label>
							</c:if>
							<c:if test="${not empty projeto.atividades }">
								<article>
									<label>							
									${projeto.atividades }
									</label>
								</article>
							</c:if>
							
						</div>		
								
					</div>
					<br>
					<div class="form-group">
						<label class="col-sm-2 control-label field">Participantes:</label>
						<div class="col-sm-10 field-value">
							<c:if test="${empty projeto.participacoes }">
								<label>-</label>
							</c:if>
							<c:if test="${not empty projeto.participacoes }">
								<c:forEach items="${projeto.participacoes }" var="participacao">
									<label><a href="<c:url value="/pessoa/${participacao.participante.id}" ></c:url>">${participacao.participante.nome};</a></label>
								</c:forEach>
							</c:if>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label field">Anexos:</label>
						<div class="col-sm-10 field-value">
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
							
					<sec:authorize ifAnyGranted="DIRETOR">
						<h4 class="subtitle">Observações do Diretor</h4>
						<span class="line"></span>
						<div class="form-group">
							<div class="col-sm-4 value-label">						
								<label>${projeto.parecer.observacao}</label>									   		
							</div>				   		
						</div>
					</sec:authorize>
					
								
					<sec:authorize ifAnyGranted="DIRETOR">
						<c:if test="${projeto.parecer != null}">
							<h4 class="subtitle">Parecer</h4>
							<span class="line"></span>
							<div class="form-group">
								<label class="col-sm-2 control-label">Parecerista:</label>
								<div class="col-sm-4 value-label">
									<a href="<c:url value="/pessoa/detalhes/${projeto.parecer.parecerista.id}" ></c:url>">${projeto.parecer.parecerista.nome}</a>
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
								<div class="form-group">
									<label class="col-sm-2 control-label">Parecer:</label>
									<div class="col-sm-4 value-label">
										<label>${projeto.parecer.parecer}</label>
									</div>
								</div>
								 		
							</div>	
							<div class="form-group">
								<label class="col-sm-2 control-label">Anexo:</label>
								<div class="col-sm-10 value-label">
									<a href="<c:url value="/documento/${projeto.id }/${projeto.parecer.documento.id }" />">${projeto.parecer.documento.nome }</a>
								</div>
							</div>
						</c:if>
					</sec:authorize>								
					
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
				
					<form id="comentarForm">
						<div id="div-comentario" class="col-sm-12 form-item">
							<div id="campo-comentario" class="col-sm-12">
								<textarea id="comentario" name="comentario" class="form-control" rows="5" placeholder="Comentário" required="required"></textarea>
							</div>
						</div>
						<div class="controls">
							<input id="comentar" name="comentar" type="button" class="btn btn-primary" value="Enviar" />
						</div>
					</form>
				</div> <!-- form-horizontal -->
			</div> <!-- /panel-body -->
		</div> <!-- /panel -->
	</div> <!-- /container -->
	
	<jsp:include page="../modulos/footer.jsp" />
	
	<script type="text/javascript">
		$('#menu-projetos').addClass('active');
	</script>
		
</body>
</html>
