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
<title>Cadastro de Projetos</title>
</head>
<body>
	<fmt:formatNumber value="${projeto.valorDaBolsa}"  type="currency" var="valorBolsa"/>
	<c:if test="${action eq 'cadastrar' }">
		<c:set var="url" value="/projeto/cadastrar"></c:set>
		<c:set var="titulo" value="Novo Projeto"></c:set>
	</c:if>
	<c:if test="${action eq 'editar' }">
		<c:set var="url" value="/projeto/editar"></c:set>
		<c:set var="titulo" value="Editar - ${projeto.nome } "></c:set>
	</c:if>

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
			<h2>${titulo}</h2>
			<form:form id="adicionarProjetoForm" role="form" commandName="projeto" enctype="multipart/form-data" servletRelativeAction="${url }" method="POST" cssClass="form-horizontal">

				<input type="hidden" id="valorDaBolsa" name="valorDaBolsa" value="${projeto.valorDaBolsa }"/>
				<input type="hidden" id="id" name="id" value="${projeto.id }"/>
				<input type="hidden" id="codigo" name="codigo" value="${projeto.codigo }"/>
				
				<div class="form-group form-item">
					<label for="nome" class="col-sm-2 control-label"><span class="required">*</span> Nome:</label>
					<div class="col-sm-10">
						<form:input id="nome" name="nome" path="nome" cssClass="form-control" placeholder="Nome do projeto" required="required"/>
						<div class="error-validation">
							<form:errors path="nome"></form:errors>
						</div>
					</div>
				</div>

				<div class="form-group form-item">
					<label for="descricao" class="col-sm-2 control-label"><span class="required">*</span> Descrição:</label>
					<div class="col-sm-10">
						<form:textarea id="descricao" path="descricao" class="form-control" rows="5" placeholder="Descrição" name="descricao" required="required"/>
						<div class="error-validation">
							<form:errors path="descricao"></form:errors>
						</div>
					</div>

				</div>

				<div class="form-group">
					<div class="form-item">
						<label for="inicio" class="col-sm-2 control-label">Início:</label>
						<div class="col-sm-2">
							<form:input id="inicio" type="text" path="inicio" cssClass="form-control data" placeholder="Data de início"/>
							<div class="error-validation">
								<form:errors path="inicio"></form:errors>
							</div>
							<c:if test="${not empty error_inicio}">
								<div class="error-validation">
									<span>${error_inicio}</span>
								</div>
							</c:if>
						</div>
					</div>

					<div class="form-item">
						<label for="termino" class="col-sm-2 control-label">Término:</label>
						<div class="col-sm-2">
							<form:input id="termino" type="text" path="termino" cssClass="form-control data" placeholder="Data de término"/>
							<div class="error-validation">
								<form:errors path="termino"></form:errors>
							</div>
							<c:if test="${not empty error_termino}">
								<div class="error-validation">
									<span>${error_termino}</span>
								</div>
							</c:if>
						</div>
					</div>

					<div class="form-item">
						<label for="quantidadeBolsa" class="col-sm-2 control-label">Número de bolsas:</label>
						<div class="col-sm-2" >
							<form:input id="quantidadeBolsa" name="quantidadeBolsa" type="number" placeholder="0" path="quantidadeBolsa" cssClass="form-control" min="0"/>
							<div class="error-validation">
								<form:errors path="quantidadeBolsa"></form:errors>
							</div>
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="form-item">
						<label for="cargaHoraria" class="col-sm-2 control-label">Carga horária:</label>
						<div class="col-sm-2">
							<form:input id="cargaHoraria" name="cargaHoraria" type="number" placeholder="0" path="cargaHoraria" cssClass="form-control" min="0"/>
							<div class="error-validation">
								<form:errors path="cargaHoraria"></form:errors>
							</div>
						</div>
					</div>
					
					<div class="form-item">
						<label for="bolsa" class="col-sm-2 control-label">Valor da bolsa:</label>
						<div class="col-sm-2">
							<input id="bolsa" name="bolsa" placeholder="R$ 0,00" class="form-control" value="${valorBolsa }"/>
							<div class="error-validation">
								<form:errors path="valorDaBolsa"></form:errors>
							</div>
						</div>
					</div>
				</div>
				
				<div class="form-group form-item">
					<label for="idParticipantes" class="col-sm-2 control-label">Participantes:</label>
					<div class="col-sm-10">
						<select id="participantes" name="idParticipantes" class="form-control" multiple="multiple">
							<c:set var="part" value="${projeto.participantes }"></c:set>
							<c:forEach items="${participantes }" var="participante">
								<c:set var="selected" value=""></c:set>
								<c:set var="idParticipante" value="id=${participante.id }"></c:set>
								<c:if test="${fn:contains(part, idParticipante)}">
									<c:set var="selected" value="selected=\"selected\""></c:set>
								</c:if>
								<option value="${participante.id }" ${selected }>${participante.nome }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				
				<div class="form-group form-item">
					<label for="local" class="col-sm-2 control-label">Local:</label>
					<div class="col-sm-10">
						<form:input id="local" path="local" cssClass="form-control" placeholder="Local do projeto" />
					</div>
				</div>

				<div class="form-group form-item">
					<label for="atividades" class="col-sm-2 control-label">Atividades:</label>
					<div class="col-sm-10">
						<form:textarea id="atividades" path="atividades" name="atividades" class="form-control" rows="5" placeholder="Atividades"></form:textarea>
					</div>
				</div>
				
				<div class="form-group form-item">
					<label for="atividades" class="col-sm-2 control-label">Anexos:</label>
					<div class="col-sm-10">
						<input type="file" id="anexos" name="anexos" class="file" multiple="multiple" ></input>
						<c:if test="${not empty projeto.documentos }">
							<table id="table-anexos" class="table table-striped">
								<thead>
									<tr>
										<th data-column-id="nome" data-order="desc">Arquivo</th>
										<th data-column-id="excluir" width="5%">Excluir</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${projeto.documentos }" var="documento">
		                    			<tr id="documento-${documento.id}">
									        <td>
									            <a href="<c:url value="/documento/${documento.id }" />">${documento.nome }</a>
									        </td>
									        <td>
									        	<a id="exluir-arquivo" data-toggle="modal" data-target="#delete-file" href="#" data-id="${documento.id}" data-name="${documento.nome }">
													<button class="btn btn-danger"><i class="fa fa-trash-o"></i></button>
												</a>
									        </td>
									    </tr>	
		                    		</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-2"></div>
					<div class="col-sm-2">
						<span class="campo-obrigatorio"><span class="required">*</span> Campos obrigatórios</span>
					</div>
				</div>

				<div class="controls">
					<input name="salvar" type="submit" class="btn btn-primary" value="Salvar" />
					<a href="<c:url value="/projeto/index"></c:url>" class="btn btn-default">Cancelar</a>
				</div>
			</form:form>
		</div>
	</div>
	
	<!-- Modal Excluir Arquivo -->
	<div class="modal fade" id="delete-file" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        			<h4 class="modal-title" id="excluirArquivoModalLabel">Excluir</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger confirm-delete-file" data-dismiss="modal">Excluir</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />

</body>
</html>
