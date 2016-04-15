<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Fontes de Financiamento</title>
<jsp:include page="../modulos/header-estrutura.jsp" />
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Fontes de Financiamento</h3>
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

				<div id="fontes-financiamento" align="left">
					
					<div id="mensagem-fonte-financiamento" class="alert alert-warning" role="alert">Nenhuma fonte de financiamento cadastrada.</div>

					<c:if test="${not empty fontesFinanciamento}">

						<table id="table-fontes-financiamento"
							class="table table-striped table-hover">

							<tbody>

								<c:forEach items="${fontesFinanciamento}"
									var="fonteFinanciamento">
									<tr id="fonte-${fonteFinanciamento.id}">

										<td>${fonteFinanciamento.nome}</td>

										<td class="align-right"><a
											id="exluir-fonte-financiamento" data-toggle="modal"
											data-target="#confirm-delete-fonte-financiamento" href="#" title="Excluir"
											data-name="${fonteFinanciamento.nome }"
											data-id="${fonteFinanciamento.id}">
												<button class="btn btn-danger btn-xs">
													<i class="fa fa-trash-o"></i>
												</button>
										</a></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>

					</c:if>

					<form:form id="adicionarFonteFinanciamentoForm" role="form" commandName="fonteFinanciamento" servletRelativeAction="/administracao/fonte-financiamento/cadastrar" method="POST" cssClass="form-horizontal">

						<div class="form-group form-item col-sm-10">
							<label for="nome" class="col-sm-2 control-label"><span
								class="required">*</span> Nome:</label>
							<div class="col-sm-10">
								<form:input id="nome" name="nome" path="nome"
									cssClass="form-control"
									placeholder="Nome da fonte de financiamento"
									required="required" />
								<div class="error-validation">
									<form:errors path="nome" cssClass="errors"></form:errors>
								</div>
							</div>
						</div>

						<div class="controls col-sm-2">
							<input name="cadastrar" type="submit" class="btn btn-primary"
								value="Cadastrar" />
						</div>

						<div class="form-group col-sm-12">
							<div class="col-sm-1"></div>
							<div class="col-sm-2">
								<span class="campo-obrigatorio"><span class="required">*</span>
									Campos obrigatórios</span>
							</div>
						</div>

					</form:form>

				</div>
			</div>
		</div>
	</div>
	
	<!-- Modal Excluir Fonte de Financiamento -->
	<div class="modal fade" id="confirm-delete-fonte-financiamento">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">
						&times;<span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">Excluir</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button id="button-delete-fonte-financiamento" class="btn btn-danger btn-sm">Excluir</button>
					<button type="button" class="btn btn-default btn-sm"
						data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
	
	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>