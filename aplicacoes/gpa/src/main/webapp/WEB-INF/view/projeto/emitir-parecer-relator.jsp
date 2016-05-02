<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
	<head>
		<title>Avaliar Projeto</title>
		<jsp:include page="../modulos/header-estrutura.jsp" />
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="title">
					<h3 class="panel-title">Avaliar Projeto</h3>
				</div>
				<div class="align-right">
					<a id="solicitar-resolucao-restricoes" data-toggle="modal"
					data-target="#confirm-solicitar-resolucao-restricoes" title="Solicitar Resolução de Restrições">
						
						<button class="btn btn-warning btn-sm">
							Solicitar Resolução de Restrições
						</button>
						
					</a>
				</div>
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
				<form:form id="avaliarProjetoForm" enctype="multipart/form-data" servletRelativeAction="/projeto/avaliar"
					modelAttribute="parecer" method="POST" cssClass="form-horizontal">
					<input type="hidden" name="id-projeto" value="${projeto.id }"/>
					<div class="form-group">
						<label class="col-sm-2 control-label">Projeto:</label>
						<div class="col-sm-8 value-label">
							<label>${projeto.nome }</label>
						</div>
					</div>
					
					<div class="form-group">
						<label for="coordenador" class="col-sm-2 control-label">Coordenador:</label>
						<div class="col-sm-8 value-label">
							<label><a href="<c:url value="/pessoa/${projeto.coordenador.id}/detalhes" ></c:url>">${projeto.coordenador.nome}</a></label>
						</div>
					</div>
					
					<c:if test="${action == 'cadastrar'}">
						<div class="form-group form-item">
							<label for="posicionamento" class="col-sm-2 control-label">Posicionamento:</label>
							<div class="col-sm-4">
								<select id="posicionamento" name="posicionamento" class="form-control">
									<c:forEach items="${posicionamento}" var="pos">
										<option value="${pos}">${pos.descricao}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					
						<div class="form-group form-item">
							<label for="observacao" class="col-sm-2 control-label"> Observação:</label>
							<div class="col-sm-10">
								<form:textarea path="observacao" cssClass="form-control" rows="8" placeholder="Observacao"/>
								<form:errors path="observacao" cssClass="error-validation"></form:errors>		
							</div>
						</div>
					</c:if>
					
					<c:if test="${action == 'editar'}">
						<div class="form-group form-item">
							<label for="posicionamento" class="col-sm-2 control-label">Posicionamento:</label>
							<div class="col-sm-4">
								<select id="posicionamento" name="posicionamento" class="form-control">
									<c:forEach items="${posicionamento}" var="pos">
										<c:if test="${parecer.status == pos}">
											<option selected="selected" value="${pos}">${pos.descricao}</option>
										</c:if>
										
										<c:if test="${parecer.status != pos}">
											<option value="${pos}">${pos.descricao}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
					
						<div class="form-group form-item">
							<label for="observacao" class="col-sm-2 control-label"> Observação:</label>
							<div class="col-sm-10">
								<form:textarea value="${parecer.observacao}" path="observacao" cssClass="form-control" rows="8" placeholder="Observacao" />
								<form:errors path="observacao" cssClass="error-validation"></form:errors>		
							</div>
						</div>
					</c:if>
	
					<div class="form-group">
						<div class="col-sm-2"></div>
						<div class="col-sm-2">
							<span class="campo-obrigatorio"><span class="required">*</span> Campos obrigatórios</span>
						</div>
					</div>
	
					<div class="controls">
						<input name="salvar" type="submit" class="btn btn-primary" value="Salvar" />
						<a href="<c:url value="/projeto"></c:url>" class="btn btn-default">Cancelar</a>
					</div>
					
				</form:form>
			</div><!-- /panel-body -->
		</div><!-- /panel -->
	</div><!-- /container -->
	
	
	<!-- Modal Solicitar Resolução de Restrições -->
	<div class="modal fade" id="confirm-solicitar-resolucao-restricoes">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">
						&times;<span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">Solicitar Resolução de Restrições</h4>
				</div>
				
				<form:form commandName="pendencia" servletRelativeAction="/projeto/solicitar-resolucao-pendencias/${projeto.id}">
					<div class="modal-body form-group">
						
						<form:textarea path="descricao" cssClass="form-control" placeholder="Descrição das restrições" required="required"/>
						<form:errors path="descricao" cssClass="error-validation"></form:errors>	
						
					</div>
					<div class="modal-footer controls">
						<input name="enviar" type="submit" class="btn btn-warning btn-sm" value="Enviar Solicitação">
						<button type="button" class="btn btn-default btn-sm"
							data-dismiss="modal">Cancelar</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	
	
	<jsp:include page="../modulos/footer.jsp"></jsp:include>
	
	<script type="text/javascript">
		$('#menu-projetos').addClass('active');
	</script>
</body>
</html>