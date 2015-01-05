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
<title>Avaliar Projeto</title>
</head>
<body>
	<div class="container">
		<jsp:include page="../modulos/header.jsp" />
		<div class="formulario">
			<h2>Avaliar - <a href="<c:url value="/projeto/${projeto.id}/detalhes" ></c:url>">${projeto.nome}</a></h2>
			<form:form id="avaliarProjetoForm" enctype="multipart/form-data" servletRelativeAction="/projeto/diretor/${projeto.id}/avaliar" method="POST" cssClass="form-horizontal">
				<div class="form-group">
					<label for="autor" class="col-sm-2 control-label">Parecerista:</label>
					<div class="col-sm-4 field-value parecer">
						<label>${projeto.parecer.parecerista.nome }</label>
					</div>
					<label for="autor" class="col-sm-2 control-label">Autor:</label>
					<div class="col-sm-4 field-value parecer">
						<label><a href="<c:url value="/usuario/${projeto.autor.id}/detalhes" ></c:url>">${projeto.autor.nome}</a></label>
					</div>
				</div>
				<div class="form-group">
					<label for="observacao" class="col-sm-2 control-label">Parecer:</label>
					<div class="col-sm-10 field-value parecer">
						<label>${projeto.parecer.parecer }</label>
					</div>
				</div>
				<div class="form-group">
					<label for="observacao" class="col-sm-2 control-label">Anexo:</label>
					<div class="col-sm-10 field-value parecer">
						<a href="<c:url value="/documento/${projeto.id }/${projeto.parecer.documento.id }" />" class="col-sm-12" style="margin-top: 5px;">${projeto.parecer.documento.nome }</a>
					</div>
				</div>
				<div class="form-group form-item">
					<label for="avaliacao" class="col-sm-2 control-label">Status:</label>
					<div class="col-sm-4">
						<select id="avaliacao" name="avaliacao" class="form-control">
							<option value="APROVADO">APROVADO</option>
							<option value="APROVADO_COM_RESTRICAO">APROVADO COM RESTRIÇÃO</option>
							<option value="REPROVADO">REPROVADO</option>
						</select>
					</div>
				</div>

				<div class="form-group form-item">
					<label for="ata" class="col-sm-2 control-label"><span class="required">*</span> Ata de reunião:</label>
					<div class="col-sm-10 files">
						<input type="file" id="ata" name="ata" class="file"></input>
						<c:if test="${not empty erro_ata}">
							<div class="error-validation">
								<span>${erro_ata}</span>
							</div>
						</c:if>
					</div>
				</div>
				
				<div class="form-group form-item">
					<label for="oficio" class="col-sm-2 control-label"><span class="required">*</span> Ofício de aceitação:</label>
					<div class="col-sm-10 files">
						<input type="file" id="oficio" name="oficio" class="file"></input>
						<c:if test="${not empty erro_oficio}">
							<div class="error-validation">
								<span>${erro_oficio}</span>
							</div>
						</c:if>
					</div>
				</div>


				<div class="controls">
					<input name="salvar" type="submit" class="btn btn-primary" value="Salvar" />
					<a href="<c:url value="/projeto/index"></c:url>" class="btn btn-default">Cancelar</a>
				</div>
			</form:form>
		</div>
	</div>
	<jsp:include page="../modulos/footer.jsp"></jsp:include>
</body>
</html>