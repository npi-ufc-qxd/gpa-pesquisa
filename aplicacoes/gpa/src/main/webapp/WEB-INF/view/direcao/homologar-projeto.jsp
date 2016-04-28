<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
	<head>
		<title>Homologar Projeto</title>
		<jsp:include page="../modulos/header-estrutura.jsp" />
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Homologar</h3>
			</div>
			<div class="panel-body">
				<form:form id="homologarProjetoForm" commandName="projeto" enctype="multipart/form-data" servletRelativeAction="/direcao/homologar" method="POST" cssClass="form-horizontal">
					<input type="hidden" name="id" value="${projeto.id }"/>
					<div class="form-group">
						<label class="col-sm-2 control-label">Projeto:</label>
						<div class="col-sm-8 value-label">
							<label>${projeto.nome }</label>
						</div>
					</div>
					<div class="form-group">
						<label for="coordenador" class="col-sm-2 control-label">Coordenador:</label>
						<div class="col-sm-4 value-label">
							<label><a href="<c:url value="/pessoa/detalhes/${projeto.coordenador.id}" ></c:url>">${projeto.coordenador.nome}</a></label>
						</div>
					</div>
					<div class="well">
						<div class="form-group">
							<label for="coordenador" class="col-sm-2 control-label">Parecerista Técnico:</label>
							<div class="col-sm-2 value-label">
								<label>${projeto.parecer.parecerista.nome }</label>
							</div>
							<label class="col-sm-2 control-label">Posicionamento:</label>
							<div class="col-sm-2 value-label">
								<label>${projeto.parecer.status.descricao }</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Parecer Técnico:</label>
							<div class="col-sm-10 value-label">
								<label>${projeto.parecer.parecer }</label>
							</div>
						</div>
					</div>
					<div class="well">
						<div class="form-group">
							<label class="col-sm-2 control-label">Parecerista Relator:</label>
							<div class="col-sm-2 value-label">
								<label>${projeto.parecerRelator.relator.nome }</label>
							</div>
							<label class="col-sm-2 control-label">Posicionamento:</label>
							<div class="col-sm-2 value-label">
								<label>${projeto.parecer.status.descricao }</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Observações:</label>
							<div class="col-sm-10 value-label">
								<c:if test="${empty projeto.parecerRelator.observacao }">
									<label>-</label>
								</c:if>
								<c:if test="${not empty projeto.parecerRelator.observacao }">
									<label>${projeto.parecerRelator.observacao }</label>
								</c:if>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">Anexo:</label>
						<div class="col-sm-10 value-label">
							<label><a href="<c:url value="/documento/${projeto.id }/${projeto.parecer.documento.id }" />" class="col-sm-12">${projeto.parecer.documento.nome }</a></label>
						</div>
					</div>
					<div class="form-group form-item">
						<label class="col-sm-2 control-label">Status:</label>
						<div class="col-sm-4">
							<select id="homologacao" name="homologacaoParam" class="form-control">
								<option value="APROVADO">APROVADO</option>
								<option value="REPROVADO">REPROVADO</option>
							</select>
						</div>
					</div>
	
					<div class="form-group form-item">
						<label for="ata" class="col-sm-2 control-label"><span class="required">*</span> Ata de reunião:</label>
						<div class="col-sm-10">
							<input type="file" id="ataParam" name="ataParam" class="anexo file-loading" data-show-preview="false" required="required" />
							<form:errors path="ata" cssClass="error-validation"></form:errors>
						</div>
					</div>
					
					<div class="form-group form-item">
						<label for="oficio" class="col-sm-2 control-label"><span class="required">*</span> Ofício de aceitação:</label>
						<div class="col-sm-10">
							<input type="file" id="oficioParam" name="oficioParam" class="anexo file-loading" data-show-preview="false" required="required" />
							<form:errors path="oficio" cssClass="error-validation"></form:errors>
						</div>
					</div>
	
					<div class="form-group">
						<label class="col-sm-2 control-label">Observação:</label>
						<div class="col-sm-10">
							<textarea id="observacao" name="observacao" class="form-control" rows="8" placeholder="Observação"></textarea>
						</div>
					</div>
					
					<div class="controls">
						<input name="salvar" type="submit" class="btn btn-primary" value="Salvar" />
						<a href="javascript:history.back();" class="btn btn-default">Cancelar</a>
					</div>
				</form:form>
			</div><!-- /panel-body -->
		</div><!-- /panel -->
	</div><!-- /container -->
	
	<jsp:include page="../modulos/footer.jsp"></jsp:include>
	
	<script type="text/javascript">
		$('#menu-projetos').addClass('active');
	</script>
	
</body>
</html>