<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
	<head>
		<title>Emitir Parecer</title>
		<jsp:include page="../modulos/header-estrutura.jsp" />
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="title">
					<h3 class="panel-title">Emitir Parecer</h3>
				</div>
				<div class="align-right">
					<a id="solicitar-resolucao-pendencias" data-toggle="modal"
					data-target="#confirm-solicitar-resolucao-pendencias" title="Solicitar Resolução de Pendências">
						
						<button class="btn btn-warning btn-sm">
							Solicitar Resolução de Pendências
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
				<form:form id="emitirParecerForm" enctype="multipart/form-data" servletRelativeAction="/projeto/emitir-parecer"
					commandName="parecer" method="POST" cssClass="form-horizontal">
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
						<label for="parecer" class="col-sm-2 control-label"><span class="required">*</span> Parecer:</label>
						<div class="col-sm-10">
							<form:textarea path="parecer" cssClass="form-control" rows="8" placeholder="Parecer" required="required" />
							<form:errors path="parecer" cssClass="error-validation"></form:errors>		
						</div>
					</div>
	
					<div class="form-group">
						<label for="anexo" class="col-sm-2 control-label">Anexo:</label>
						<div class="col-sm-10 files">
							<input type="file" id="anexos" name="anexo" class="anexo file-loading" data-show-preview="false"></input>
						</div>
					</div>
	
					<div class="form-group">
						<div class="col-sm-2"></div>
						<div class="col-sm-2">
							<span class="campo-obrigatorio"><span class="required">*</span> Campos obrigatórios</span>
						</div>
					</div>
					
					<div class="col-sm-2"></div>
					
					<div class="col-sm-10 accordion-group">
						<div class="accordion-heading">
							<h4 class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#pendencias"><i class="accordion-icon fa fa-plus"></i> Histórico de Pendências</h4>
							<div id="pendencias" class="accordion-body collapse">
								<table class="display pendencias-table">
									<thead>
										<tr>
											<th class="col-sm-2">Data</th>
											<th class="col-sm-10">Descrição</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="pendencia" items="${parecer.pendencias}">
											<tr>
												<td class="col-sm-2"><fmt:formatDate pattern="dd/MM/yyyy" value="${pendencia.data}" /></td>
												<td class="col-sm-10">${pendencia.descricao}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
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
	
	<!-- Modal Solicitar Resolução de Pendências -->
	<div class="modal fade" id="confirm-solicitar-resolucao-pendencias">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">
						&times;<span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">Solicitar Resolução de Pendências</h4>
				</div>
				
				<form:form commandName="pendencia" servletRelativeAction="/projeto/solicitar-resolucao-pendencias/${projeto.id}">
					<div class="modal-body form-group">
						
						<form:textarea path="descricao" cssClass="form-control" placeholder="Descrição das pendências" required="required"/>
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