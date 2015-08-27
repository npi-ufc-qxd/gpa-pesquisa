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
	<div class="container">
		<jsp:include page="../modulos/header.jsp" />
		<div class="formulario detalhes">
			<input id="projetoId" type="hidden" value="${projeto.id }"/>
			<h2>${projeto.nome }</h2>
			<h3>Informações</h3><hr>
			<div class="form-group">
				<label class="col-sm-2 control-label field">Status:</label>
				<div class="col-sm-4 field-value">
					<label>${projeto.status.descricao }</label>
				</div>
				<label class="col-sm-2 control-label field">Autor:</label>
				<div class="col-sm-4 field-value">
					<a href="<c:url value="/pessoa/${projeto.autor.id}/detalhes" ></c:url>">${projeto.autor.nome}</a>
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
					<c:if test="${empty projeto.participantes }">
						<label>-</label>
					</c:if>
					<c:if test="${not empty projeto.participantes }">
						<c:forEach items="${projeto.participantes }" var="participante">
							<label><a href="<c:url value="/pessoa/${participante.id}/detalhes" ></c:url>">${participante.nome};</a></label>
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
			<div class="form-group">
			<h3>Observações do Diretor</h3><hr>
				<div class="col-sm-4 field-value">						
					<p>${projeto.parecer.observacao}</p>									   		
				</div>				   		
			</div>
			</sec:authorize>
			
						
			<sec:authorize ifAnyGranted="DIRETOR">
			<c:if test="${projeto.parecer != null}">
				<h3>Parecer</h3><hr>
				<div class="form-group">
					<label class="col-sm-2 control-label field">Parecerista:</label>
					<div class="col-sm-4 field-value">
						<label><a href="<c:url value="/pessoa/${projeto.parecer.parecerista.id}/detalhes" ></c:url>">${projeto.parecer.parecerista.nome}</a></label>
					</div>
					<c:if test="${projeto.status == 'AGUARDANDO_PARECER'}">
						<label class="col-sm-2 control-label field">Prazo parecer:</label>
						<div class="col-sm-4 field-value">
							<label><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.parecer.prazo }" /></label>
						</div>						
					</c:if>
					<c:if test="${projeto.status != 'AGUARDANDO_PARECER'}">
						<label class="col-sm-2 control-label field">Posicionamento:</label>
						<div class="col-sm-4 field-value">
							<label>${projeto.parecer.status }</label>
						</div>						
					</c:if>
										
					<div class="form-group">
					<label class="col-sm-2 control-label field">Parecer:</label>
					<div class="col-sm-4 field-value">
						<label>${projeto.parecer.parecer}</label>
					</div>
					</div>
					 		
				</div>	
				<br>
			<div class="form-group">
				<label class="col-sm-2 control-label field">Anexo:</label>

				<div class="col-sm-10 field-value">
						<label><a href="<c:url value="/documento/${projeto.id }/${projeto.parecer.documento.id }" />" class="col-sm-12" style="padding-left: 0px;">${projeto.parecer.documento.nome }</a></label>
				</div>
			</div>
				
			</c:if>
			</sec:authorize>								
			
			<h3>Comentários</h3><hr>
			<div class="form-group">
				<div class="col-sm-12 field-value">
					<ul class="cbp_tmtimeline">
						<c:forEach items="${projeto.comentarios }" var="comentario">
							<li>
						        <time class="cbp_tmtime">
						        	<span><fmt:formatDate pattern="dd/MM/yyyy" value="${comentario.data }"/></span>
						        	<span><fmt:formatDate pattern="HH:mm" value="${comentario.data }" /></span>
						        </time>
						        <div class="cbp_tmlabel">
						            <h2>${comentario.autor.nome }</h2>
						            <p>${comentario.texto }</p>
						        </div>
						    </li>
						</c:forEach>
					</ul>
				</div>
			</div>
			
			<div id="div-comentario" class="form-group">
				<div id="campo-comentario" class="col-sm-12 field-value">
					<textarea id="comentario" name="comentario" class="form-control" rows="5" placeholder="Comentário"></textarea>
				</div>
				<small id="empty-comentario" class="has-error help-block">Por favor insira um valor</small>
			</div>
			<div>
				<input id="comentar" name="comentar" type="submit" class="btn btn-primary" value="Enviar" />
			</div>
		</div>
	</div>
	<jsp:include page="../modulos/footer.jsp" />	
</body>
</html>
