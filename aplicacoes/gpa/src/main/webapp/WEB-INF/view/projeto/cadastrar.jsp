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
	<c:if test="${action eq 'cadastrar' }">
		<c:set var="url" value="/projeto/cadastrar"></c:set>
		<c:set var="titulo" value="Novo Projeto"></c:set>
	</c:if>
	<c:if test="${action eq 'editar' }">
		<c:set var="url" value="/projeto/editar"></c:set>
		<c:set var="titulo" value="Editar Projeto "></c:set>
	</c:if>
	
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">${titulo }</h3>
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
				<div class="formulario">
					<form:form id="adicionarProjetoForm" role="form" commandName="projeto" enctype="multipart/form-data" servletRelativeAction="${url }" method="POST" cssClass="form-horizontal">
			
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
			
						</div>
			
						<div class="form-group form-item">
							<label for="local" class="col-sm-2 control-label">Local de execução:</label>
							<div class="col-sm-10">
								<form:input id="local" path="local" cssClass="form-control" placeholder="Local do projeto" />
							</div>
						</div>
			
						<div class="form-group form-item">
							<label for="atividades" class="col-sm-2 control-label">Atividades gerais:</label>
							<div class="col-sm-10">
								<form:textarea id="atividades" path="atividades" name="atividades" class="form-control" rows="5" placeholder="Atividades"></form:textarea>
							</div>
						</div>
						
						<div class="form-group form-item">
							<label for="anexos" class="col-sm-2 control-label">Anexos:</label>
							<div class="col-sm-10">
								<input id="anexos" type="file" name="anexos" class="anexo file-loading" multiple="multiple" ></input>
								<c:if test="${not empty projeto.documentos }">
									<table id="table-anexos" class="table table-striped table-hover">
										<thead>
											<tr>
												<th></th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${projeto.documentos }" var="documento">
				                    			<tr id="documento-${documento.id}">
											        <td>
											            <a href="<c:url value="/documento/${documento.id }" />">${documento.nome }</a>
											        </td>
											        <td class="align-right">
											        	<a id="exluir-arquivo" data-toggle="modal" data-target="#confirm-delete-file" href="#" title="Excluir"
											        		data-name="${documento.nome }" data-id="${documento.id }">
															<button class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i></button>
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
		</div>
	</div>
	
	<!-- Modal Excluir Arquivo -->
	<div class="modal fade" id="confirm-delete-file">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;<span class="sr-only">Close</span></button>
	       			<h4 class="modal-title">Excluir</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<a id="button-delete-file" href="#" class="btn btn-danger">Excluir</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
	
	<script type="text/javascript">
		$('#menu-novo-projeto').addClass('active');
	</script>

</body>
</html>
