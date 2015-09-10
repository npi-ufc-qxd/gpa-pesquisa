<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Participantes do Projeto</title>
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
		<c:if test="${not empty info}">
			<div class="alert alert-success alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<c:out value="${info}"></c:out>
			</div>
		</c:if>
			<div class="formulario">
			<h2>${projeto.nome }</h2>
			<form:form id="adicionarParticipacaoForm" role="form" commandName="participacao" enctype="multipart/form-data" servletRelativeAction="/projeto/${projeto.id}/participacoes" method="POST" cssClass="form-horizontal">
			
				
				<div class="form-group form-item">
					<label for="idParticipantes" class="col-sm-2 control-label">Participantes:</label>
					<div class="col-sm-10">
						<select name="participanteSelecionado" class="form-control">
							<c:set var="part" value="${pessoas }"></c:set>
							<c:forEach items="${pessoas }" var="participante">
								<c:set var="selected" value=""></c:set>
								<c:set var="participanteSelecionado" value="id=${participante.id }"></c:set>
								<c:if test="${fn:contains(part, participanteSelecionado)}">
									<c:set var="selected" value="selected=\"selected\""></c:set>
								</c:if>
								<option value="${participante.id }" ${selected }>${participante.nome }</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">Mês/Ano início:</label>
					<div class="form-item">
						<div class="col-sm-2">
							<form:input id="mesInicio" name="mesInicio" type="number" placeholder="1" min="1" max="12" path="mesInicio" cssClass="form-control"/>
							<div class="error-validation">
								<form:errors path="mesInicio"></form:errors>
							</div>
						</div>
					</div>
					<div class="form-item">
						<div class="col-sm-2">
							<form:input id="anoInicio" name="anoInicio" type="number" placeholder="2015" min="2015" path="anoInicio" cssClass="form-control"/>
							<div class="error-validation">
								<form:errors path="anoInicio"></form:errors>
							</div>
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">Mês/Ano término:</label>
					<div class="form-item">
						<div class="col-sm-2">
							<form:input name="mesTermino" type="number" placeholder="1" min="1" max="12" path="mesTermino" cssClass="form-control"/>
							<div class="error-validation">
								<form:errors path="mesTermino"></form:errors>
							</div>
						</div>
					</div>
					<div class="form-item">
						<div class="col-sm-2">
							<form:input name="anoTermino" type="number" placeholder="2015" min="2015" path="anoTermino" cssClass="form-control"/>
							<div class="error-validation">
								<form:errors path="anoTermino"></form:errors>
							</div>
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="form-item">
						<label for="cargaHorariaMensal" class="col-sm-2 control-label">Carga horária mensal (em horas):</label>
						<div class="col-sm-2">
							<form:input id="cargaHorariaMensal" name="cargaHorariaMensal" type="number" placeholder="0" path="cargaHorariaMensal" cssClass="form-control" min="0"/>
							<div class="error-validation">
								<form:errors path="cargaHorariaMensal"></form:errors>
							</div>
						</div>
					</div>
					
					<div class="form-item">
						<label for="bolsa" class="col-sm-2 control-label">Valor da bolsa (R$):</label>
						<div class="col-sm-2">
							<input id="bolsaValorMensal" name="bolsaValorMensal" type="number" step="0.01" min="0.00" placeholder="0.00" class="form-control"/>
							<div class="error-validation">
								<form:errors path="bolsaValorMensal"></form:errors>
							</div>
						</div>
					</div>
				</div>
							
				<div class="form-group">
					<div class="col-sm-2"></div>
					<div class="col-sm-2">
						<span class="campo-obrigatorio"><span class="required">*</span> Campos obrigatórios</span>
					</div>
				</div>

				<div class="controls">
					<input name="adicionar" type="submit" class="btn btn-primary" value="Adicionar" />
				</div>
			</form:form>
			</div>		   
		    <div class="content">
	            <c:if test="${empty projeto.participacoes}">
					<div class="alert alert-warning" role="alert">Não há participantes vinculados.</div>
				</c:if>
				<c:if test="${not empty projeto.participacoes}">
					
					<table id="table_id" class="display">
						<thead>
							<tr>
								<th>Participante</th>
								<th>Início</th>
								<th>Término</th>
								<th>Carga Horária</th>
								<th>Bolsa</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="participacao" items="${projeto.participacoes}">
								<tr>
									<td>${participacao.participante.nome }</td>
									<td><fmt:formatNumber minIntegerDigits="2">${participacao.mesInicio}</fmt:formatNumber>/${participacao.anoInicio}</td>
									<td><fmt:formatNumber minIntegerDigits="2">${participacao.mesTermino}</fmt:formatNumber>/${participacao.anoTermino}</td>
									<td><fmt:formatNumber minIntegerDigits="2">${participacao.cargaHorariaMensal}</fmt:formatNumber></td>
									<td><fmt:formatNumber type="CURRENCY" currencyCode="BRL">${participacao.bolsaValorMensal}</fmt:formatNumber></td>
									<td class="acoes">
										<a id="excluir" data-toggle="modal" data-target="#confirm-delete" href="#" 
											data-href="<c:url value="/projeto/${projeto.id}/vincular/${participacao.id }/excluir"></c:url>" data-name="${participacao.participante.nome }">
											<button class="btn btn-danger">Excluir&nbsp;<i class="fa fa-trash-o"></i></button>
										</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
		    </div><!-- /content -->
	</div><!-- /container -->

	<!-- Modal Excluir Projeto -->
	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
        			<h4 class="modal-title" id="excluirModalLabel">Excluir</h4>
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<a href="#" class="btn btn-danger">Excluir</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="../modulos/footer.jsp" />
	
</body>
</html>

