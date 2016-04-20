<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura.jsp" />
		<title>Upload de Arquivos</title>
	</head>
<body>
	<c:set var="url" value="/projeto/uploadDocumentos/${projeto.id }"></c:set>
	<c:set var="titulo" value="Upload Arquivos"></c:set>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">
					${projeto.codigo } - ${projeto.nome } <small>(Início/Término:
						<fmt:formatDate pattern="MM/yyyy" value="${projeto.inicio }" /> -
						<fmt:formatDate pattern="MM/yyyy" value="${projeto.termino }" />)
					</small>
				</h3>
			</div>
			<div class="panel-body">
				
				<div class="formulario">
					<form:form id="adicionarArquivos" role="form" commandName="projeto" enctype="multipart/form-data" servletRelativeAction="${url }" method="POST" cssClass="form-horizontal">
						<input type="hidden" id="id" name="id" value="${projeto.id }"/>
						<div class="form-group form-item">
							<label for="anexos" class="col-sm-2 control-label">Anexos:</label>
							<div class="col-sm-10">
								<input id="anexos" type="file" name="anexos"
									class="anexo file" multiple="multiple" data-show-preview="false"></input>
								<c:if test="${not empty projeto.documentos }">
									<table id="table-anexos"
										class="table table-striped table-hover">
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
											        		data-name="${documento.nome }" data-id="${documento.id }" data-projeto-id="${projeto.id}">
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
						
						<hr></hr>
			
						<div class="controls">
							<input name="salvar" type="submit" class="btn btn-primary" value="Salvar" id="btnSalvarParticipacao"/>
							<a  class="btn btn-default back">Cancelar</a>
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
					<button id="button-delete-file" class="btn btn-danger btn-sm">Excluir</button>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
	
	<jsp:include page="../modulos/footer.jsp" />

</body>
</html>
