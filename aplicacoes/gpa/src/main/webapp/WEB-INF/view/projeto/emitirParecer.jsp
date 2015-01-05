<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<jsp:include page="../modulos/header-estrutura.jsp" />
	<title>Emitir Parecer</title>
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
		<div class="formulario">
			<h2>Emitir Parecer - <a href="<c:url value="/projeto/${projeto.id}/detalhes" ></c:url>">${projeto.nome}</a></h2>
			<form:form id="emitirParecerForm" enctype="multipart/form-data" servletRelativeAction="/projeto/${projeto.id}/emitirParecer" commandName="parecer" method="POST" cssClass="form-horizontal">
				<div class="form-group">
					<label for="autor" class="col-sm-2 control-label">Autor:</label>
					<div class="col-sm-10 field-value parecer">
						<label><a href="<c:url value="/usuario/${projeto.autor.id}/detalhes" ></c:url>">${projeto.autor.nome}</a></label>
					</div>
				</div>
				<div class="form-group form-item">
					<label for="observacao" class="col-sm-2 control-label">Observação (Diretor):</label>
					<div class="col-sm-10 field-value parecer">
						<c:if test="${empty projeto.parecer.observacao }">
							<label>-</label>
						</c:if>
						<label>${projeto.parecer.observacao }</label>
					</div>
				</div>
				<div class="form-group form-item">
					<label for="posicionamento" class="col-sm-2 control-label"><span class="required">*</span> Posicionamento:</label>
					<div class="col-sm-4">
						<select id="posicionamento" name="posicionamento" class="form-control">
							<c:forEach items="${posicionamento}" var="pos">
								<option value="${pos}">${pos.descricao}</option>
							</c:forEach>
						</select>
					</div>
				</div>
	
				<div class="form-group">
					<div class="form-item">
						<label for="parecer" class="col-sm-2 control-label"><span class="required">*</span> Parecer:</label>
						<div class="col-sm-10">
							<textarea id="parecer" name="parecer" class="form-control" rows="8" placeholder="Parecer" required="required"></textarea>
						</div>
						<c:if test="${not empty erro_parecer}">
							<div class="error-validation">
								<span>${erro_parecer}</span>
							</div>
						</c:if>
					</div>
				</div>
				
				<div class="form-group">
					<label for="anexo" class="col-sm-2 control-label">Anexo:</label>
					<div class="col-sm-10 files">
						<input type="file" id="anexos" name="anexo" class="file"></input>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-2"></div>
					<div class="col-sm-2">
						<span class="campo-obrigatorio"><span class="required">*</span> Campos obrigatórios</span>
					</div>
				</div>

				<div class="controls">
					<input name="emitir" type="submit" class="btn btn-primary" value="Salvar" />
					<a href="<c:url value="/projeto/index"></c:url>" class="btn btn-default">Cancelar</a>
				</div>
			</form:form>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp"></jsp:include>

</body>



</html>