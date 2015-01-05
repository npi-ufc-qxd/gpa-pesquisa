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
<title>Submeter Projeto</title>
</head>
<body>
	<fmt:formatNumber value="${projeto.valorDaBolsa}"  type="currency" var="valorBolsa"/>
	<div class="container">
		<jsp:include page="../modulos/header.jsp" />
		<div class="novo-projeto" align="left">
			<c:if test="${not empty alert}">
				<div class="alert alert-warning alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<c:out value="${alert }"></c:out>
				</div>
			</c:if>
			<c:if test="${not empty erro}">
				<div class="alert alert-danger alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<c:out value="${erro }"></c:out>
				</div>
			</c:if>
			<div class="formulario">
				<h2>Submeter - ${projeto.nome }</h2>
				<form:form id="submeterProjetoForm" role="form" commandName="projeto" enctype="multipart/form-data" servletRelativeAction="/projeto/submeter" method="POST" cssClass="form-horizontal">
					<input type="hidden" id="valorDaBolsa" name="valorDaBolsa" value="${projeto.valorDaBolsa }"/>
					<input type="hidden" id="id" name="id" value="${projeto.id }"/>
					<input type="hidden" id="codigo" name="codigo" value="${projeto.codigo }"/>
					
					<div class="form-group">
						<div class="form-item">
							<label for="nome" class="col-sm-2 control-label"><span class="required">*</span> Nome:</label>
							<div class="col-sm-10">
								<form:input id="nome" name="nome" path="nome" cssClass="form-control" placeholder="Nome do projeto" required="required"/>
								<c:if test="${not empty erro_nome}">
									<div class="error-validation">
										<span>${erro_nome}</span>
									</div>
								</c:if>
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="form-item">
							<label for="descricao" class="col-sm-2 control-label"><span class="required">*</span> Descrição:</label>
							<div class="col-sm-10">
								<form:textarea id="descricao" path="descricao" class="form-control" rows="5" placeholder="Descrição" name="descricao" required="required"/>
								<c:if test="${not empty erro_descricao}">
									<div class="error-validation">
										<span>${erro_descricao}</span>
									</div>
								</c:if>
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="form-item">
							<label for="inicio" class="col-sm-2 control-label"><span class="required">*</span> Início:</label>
							<div class="col-sm-2">
								<form:input id="inicio" type="text" path="inicio" cssClass="form-control data" placeholder="Data de início" required="required"/>
								<c:if test="${not empty erro_inicio}">
									<div class="error-validation">
										<span>${erro_inicio}</span>
									</div>
								</c:if>
							</div>
						</div>

						<div class="form-item">
							<label for="termino" class="col-sm-2 control-label"><span class="required">*</span> Término:</label>
							<div class="col-sm-2">
								<form:input id="termino" type="text" path="termino" cssClass="form-control data" placeholder="Data de término"  required="required"/>
								<c:if test="${not empty erro_termino}">
									<div class="error-validation">
										<span>${erro_termino}</span>
									</div>
								</c:if>
							</div>
						</div>

						<div class="form-item">
							<label for="quantidadeBolsa" class="col-sm-2 control-label">Número de bolsas:</label>
							<div class="col-sm-2" >
								<form:input id="quantidadeBolsa" name="quantidadeBolsa" type="number" placeholder="0" path="quantidadeBolsa" cssClass="form-control" min="0" />
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="form-item">
							<label for="cargaHoraria" class="col-sm-2 control-label"><span class="required">*</span> Carga horária:</label>
							<div class="col-sm-2">
								<form:input id="cargaHoraria" name="cargaHoraria" type="number" placeholder="0" path="cargaHoraria" cssClass="form-control" min="1" required="required"/>
								<c:if test="${not empty erro_cargaHoraria}">
									<div class="error-validation">
										<span>${erro_cargaHoraria}</span>
									</div>
								</c:if>
							</div>
						</div>
						
						<div class="form-item">
							<label for="bolsa" class="col-sm-2 control-label">Valor da bolsa:</label>
							<div class="col-sm-2">
								<input id="bolsa" name="bolsa" placeholder="R$ 0,00" class="form-control" value="${valorBolsa }" />
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="form-item">
							<label for="participantes" class="col-sm-2 control-label"><span class="required">*</span> Participantes:</label>
							<div class="col-sm-10">
								<select id="participantes" name="idParticipantes" class="form-control" multiple="multiple" required="required">
									<c:set var="part" value="${projeto.participantes }"></c:set>
									<option value=""></option>
									<c:forEach items="${participantes }" var="participante">
										<c:set var="selected" value=""></c:set>
										<c:set var="idParticipante" value="id=${participante.id }"></c:set>
										<c:if test="${fn:contains(part, idParticipante)}">
											<c:set var="selected" value="selected=\"selected\""></c:set>
										</c:if>
										<option value="${participante.id }" ${selected }>${participante.nome }</option>
									</c:forEach>
								</select>
								<c:if test="${not empty erro_participantes}">
									<div class="error-validation">
										<span>${erro_participantes}</span>
									</div>
								</c:if>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="form-item">
							<label for="local" class="col-sm-2 control-label"><span class="required">*</span> Local:</label>
							<div class="col-sm-10">
								<form:input id="local" path="local" cssClass="form-control" placeholder="Local do projeto" required="required"/>
							</div>
							<c:if test="${not empty erro_local}">
									<div class="error-validation">
										<span>${erro_local}</span>
									</div>
								</c:if>
						</div>
					</div>

					<div class="form-group">
						<div class="form-item">
							<label for="atividades" class="col-sm-2 control-label"><span class="required">*</span> Atividades:</label>
							<div class="col-sm-10">
								<form:textarea id="atividades" path="atividades" name="atividades" class="form-control" rows="5" placeholder="Atividades" required="required"></form:textarea>
							</div>
							<c:if test="${not empty erro_atividades}">
									<div class="error-validation">
										<span>${erro_atividades}</span>
									</div>
								</c:if>
						</div>
					</div>
					
					<div class="form-group form-item">
						<div class="form-item">
							<label for="atividades" class="col-sm-2 control-label"><span class="required">*</span> Anexos:</label>
							<div class="col-sm-10">
								<input type="file" id="anexos" name="anexos" class="file" multiple="multiple"></input>
								<c:if test="${not empty erro_documentos}">
									<div class="error-validation">
										<span>${erro_documentos}</span>
									</div>
								</c:if>
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
															<button class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button>
														</a>
											        </td>
											    </tr>	
				                    		</c:forEach>
										</tbody>
									</table>
								</c:if>
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
						<input name="salvar" type="submit" class="btn btn-primary" value="Salvar" />
						<a href="<c:url value="/projeto/index"></c:url>" class="btn btn-default">Cancelar</a>
					</div>
				</form:form>
			</div>
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
