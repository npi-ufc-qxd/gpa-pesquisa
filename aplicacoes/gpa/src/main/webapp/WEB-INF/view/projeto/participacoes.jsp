<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="../modulos/header-estrutura.jsp" />
	<title>Participantes do Projeto</title>
</head>
<body>
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
				<c:if test="${not empty erro}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<c:out value="${erro}"> </c:out>
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

				<c:if test="${not empty validacao.globalErrors }">
					<div class="alert alert-dismissible alert-info" role="alert">
						<button type="button" class="close" data-dismiss="alert">×</button>
						<c:forEach items="${validacao.globalErrors}" var="vg">
							<p><spring:message code="${vg.defaultMessage}"></spring:message></p>
						</c:forEach>
						<a href='<c:url value="/projeto/editar/${projeto.id}"></c:url>' class="btn btn-default btn-xs">Editar Início/Término</a>
					</div>
				</c:if>

				<!-- Formulario -->
				<div class="formulario">
					<form:form id="adicionarParticipacaoForm" role="form" commandName="participacao" enctype="multipart/form-data" servletRelativeAction="/projeto/participacoes/${projeto.id}" method="POST" cssClass="form-horizontal">
						<div class="form-group">
							<div class="form-item">
										<label class="col-sm-2 control-label"></label>
										<div class="col-sm-5">
											<div class="checkbox">
						                        	<label>
						                          		<input type="checkbox" id="pessoaExternaCheckBox"> Participante Externo
						                        	</label>
						                    </div>						                    
					                    </div>
				                    </div>
				        </div>
						<div class="form-group">
								<div class="form-item" id="divParticipante">	
									<label for="idParticipantes" class="col-sm-2 control-label">Novo participante:</label>
									<div class="col-sm-4">
										<select name="participanteSelecionado" class="select-participantes form-control">
											<c:set var="part" value="${pessoas}"></c:set>
											
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
								<div class="form-item" id="divParticipanteExterno" style="display: none;">
									<label for="idParticipantes" class="col-sm-2 control-label">Participante externo:</label>
									<div class="col-sm-4">
										<select name="participanteExternoSelecionado" class="form-control">
											<c:set var="part" value="${pessoasExternas }"></c:set>
											
											<c:forEach items="${pessoasExternas }" var="participante">
												<c:set var="selected" value=""></c:set>
												<c:set var="participanteSelecionado" value="id=${participante.id }"></c:set>
												<c:if test="${fn:contains(part, participanteSelecionado)}">
													<c:set var="selected" value="selected=\"selected\""></c:set>
												</c:if>
												<option value="${participante.id }" ${selected }>${participante.nome }</option>
											</c:forEach>
										</select>
									
									<a href="#" id="cadastrarPessoaExternaBtn" class="btn btn-link">Cadastrar pessoa externa</a>
									</div>
								</div>
								<div class="form-item">
									<label for="tipoParticipante" class="col-sm-2 control-label">Tipo de participante:</label>
									<div class="col-sm-4">
										<select id="tipoParticipante" name="tipo" class="form-control">
											<c:forEach items="${tiposDeParticipacao}" var="tipo">
												<option value="${tipo }" >${tipo.descricao}</option>
											</c:forEach>
										</select>
									</div>
								</div>							
							</div>
						<form:input type="hidden" id="externoBoolean" path="externo" value="false"/>
							
						<div class="form-group">
							<div class="form-item">
								<label class="col-sm-2 control-label"><span class="required">*</span> Mês/Ano início: </label>
								<div class="col-sm-2">
									<form:input id="mesInicio" name="mesInicio" type="number" 
										min="1" max="12" path="mesInicio" 
										cssClass="form-control" required="required" />
									<div class="error-validation">
										<form:errors path="mesInicio"></form:errors>
									</div>
								</div>
							</div>
							<div class="form-item">
								<div class="col-sm-2">
									<form:input id="anoInicio" name="anoInicio" type="number" 
										path="anoInicio" cssClass="form-control" required="required" />
									<div class="error-validation">
										<form:errors path="anoInicio"></form:errors>
									</div>
								</div>
							</div>
							<div class="form-item">
								<label class="col-sm-2 control-label"><span class="required">*</span> Mês/Ano término: </label>
								<div class="col-sm-2">
									<form:input id="mesTermino" name="mesTermino" type="number" 
										min="1" max="12" path="mesTermino" 
										cssClass="form-control" required="required" />
									<div class="error-validation">
										<form:errors path="mesTermino"></form:errors>
									</div>
								</div>
							</div>
							<div class="form-item">
								<div class="col-sm-2">
									<form:input id="anoTermino" name="anoTermino" type="number"
										 path="anoTermino" cssClass="form-control" required="required" />
									<div class="error-validation">
										<form:errors path="anoTermino"></form:errors>
									</div>
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="form-item">
								<label for="cargaHorariaMensal" class="col-sm-2 control-label"><span class="required">*</span> Carga horária mensal (em horas):</label>
								<div class="col-sm-4">
									<form:input id="cargaHorariaMensal" name="cargaHorariaMensal"
										type="number" path="cargaHorariaMensal"
										cssClass="form-control" min="1" required="required" />
									<div class="error-validation">
										<form:errors path="cargaHorariaMensal"></form:errors>
									</div>
								</div>
							</div>

							<div class="form-item">
								<label for="bolsa" class="col-sm-2 control-label"><span
									class="required">*</span> Valor da bolsa (R$):</label>
								<div class="col-sm-4">
									<form:input id="bolsaValorMensal" name="bolsaValorMensal"
										type="text" path="bolsaValorMensal"
										cssClass="form-control campo-valor" />
									<div class="error-validation">
										<form:errors path="bolsaValorMensal"></form:errors>
									</div>
								</div>
							</div>
						</div>

						<div class="controls">
							<input name="adicionar" type="submit" class="btn btn-primary" value="Adicionar" />
							<a title="Voltar para detalhes do projeto" href="<c:url value="/projeto/detalhes/${projeto.id}"></c:url>" class="btn btn-warning"><span class="fa fa-arrow-circle-o-left"></span> Voltar</a>
						</div>

					</form:form>
				</div>
				<!-- /formulario -->
				<hr>
				
				<div>
					<c:if test="${empty projeto.participacoes}">
						<div class="alert alert-warning" role="alert">Não há participantes vinculados.</div>
					</c:if>
					
					<c:if test="${not empty projeto.participacoes}">
						<table id="participacoes-projeto" class="display">
							<thead>
								<tr>
									<th class="dt-center">Participante</th>
									<th class="dt-center">Início</th>
									<th class="dt-center">Término</th>
									<th class="dt-center">Carga Horária Mensal</th>
									<th class="dt-center">Valor da Bolsa</th>
									<th class="dt-center">Tipo de Participação</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="participacao" items="${projeto.participacoes}">
									<tr>
										<c:choose>
											<c:when test="${not participacao.externo}">
												<td class="dt-center">${participacao.participante.nome }</td>
											</c:when>
											<c:otherwise>
												<td class="dt-center">${participacao.participanteExterno.nome }</td>
											</c:otherwise>
										</c:choose>
										<td class="dt-center"><fmt:formatNumber
												minIntegerDigits="2">${participacao.mesInicio}</fmt:formatNumber>/${participacao.anoInicio}</td>
										<td class="dt-center"><fmt:formatNumber
												minIntegerDigits="2">${participacao.mesTermino}</fmt:formatNumber>/${participacao.anoTermino}</td>
										<td class="dt-center"><fmt:formatNumber
												minIntegerDigits="2">${participacao.cargaHorariaMensal}</fmt:formatNumber></td>
										<td class="dt-center"><fmt:formatNumber type="currency"
												currencySymbol="R$ ">${participacao.bolsaValorMensal}</fmt:formatNumber></td>
										<td class="dt-center">${participacao.tipo.descricao }</td>
										<td class="acoes dt-center"><a id="excluir"
											data-toggle="modal" class="btn btn-danger btn-xs"
											data-target="#confirm-delete-participacao" href="#"
											data-href="<c:url value="/projeto/participacoes/${projeto.id}/excluir/${participacao.id }"></c:url>"
											data-name="${participacao.participante.nome }" data-name-externo="${participacao.participanteExterno.nome }">
											<i class="fa fa-trash-o"></i>
										</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
				<!-- /content -->
			</div>
			<!-- /panel-body -->
		</div>
		<!-- /panel -->
	</div>
	<!-- /container -->

	<!-- Modal Excluir Projeto -->
	<div class="modal fade" id="confirm-delete-participacao">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">Excluir participante</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<a href="#" class="btn btn-danger btn-sm">Excluir</a>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
	<div id="cadastrarPessoaExternaModal" class="modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button id="fecharModalBtn" type="button" class="close"
						data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title">Cadastrar Nova Pessoa Externa</h4>
				</div>
				<form:form id="cadastrarPessoaExternaForm" cssClass="form-horizontal" role="form">
					<div class="modal-body">
						<div class="form-group">
							<div class="form-item">
								<label for="inputNome" class="col-lg-2 control-label">Nome</label>
								<div class="col-lg-8">
									<input type="text" class="form-control" id="inputNome" name="inputNome"/>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="form-item">
								<label for="inputCPF" class="col-lg-2 control-label">CPF</label>
								<div class="col-lg-8">
									<input type="text" class="form-control" id="inputCPF" name="inputCPF"/>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="form-item">
								<label for="inputEmail" class="col-lg-2 control-label">Email</label>
								<div class="col-lg-8">
									<input type="text" class="form-control" id="inputEmail" name="inputEmail"/>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button id="cancelarModalBtn" type="button" class="btn btn-default"
							data-dismiss="modal">Cancelar</button>
						<button id="submeterNovaPessoaExterna" type="submit"
							class="btn btn-primary">Cadastrar</button>
					</div>
				</form:form>
				
			</div>
			
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
	
	<script type="text/javascript">
		$(function() {
			$('#menu-projetos').addClass('active');
		});
	</script>
</body>
</html>
