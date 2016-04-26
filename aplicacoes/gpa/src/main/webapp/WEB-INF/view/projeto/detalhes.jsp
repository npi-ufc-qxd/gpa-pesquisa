<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
	<jsp:include page="../modulos/header-estrutura.jsp" />
	<title>Informações do Projeto</title>
</head>

<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">${projeto.nome}</h3>
			</div>
	
			<div class="panel-body">
				<input id="projetoId" type="hidden" value="${projeto.id }"/>
				<div class="form-horizontal">

					<c:if test="${(projeto.status == 'NOVO' or projeto.status == 'RESOLVENDO_PENDENCIAS' or projeto.status == 'RESOLVENDO_RESTRICOES') and permissao == 'coordenador'}">
						<div>
							<label style="width: 100%; text-align: right;">
								<a id="submeter" data-toggle="modal" data-target="#confirm-submit" href="#" title="Submeter" data-href="<c:url value="/projeto/submeter/${projeto.id}" ></c:url>" data-name="${projeto.nome }" class="btn btn-primary btn-sm">
									<i class="fa fa-cloud-upload"></i>
								</a>
								<c:if test="${(not empty projeto.inicio) && (not empty projeto.termino)}">
									<a id="vincular" href="<c:url value="/projeto/participacoes/${projeto.id}" ></c:url>" target="_blank" title="Vincular participantes" class="btn btn-primary btn-sm">
										<i class="fa fa-users"></i>
									</a>
								</c:if>
								<a id="upload-documentos" href="<c:url value="/projeto/uploadDocumento/${projeto.id}" ></c:url>" title="Upload Documentos">
									<button class="btn btn-primary btn-sm"><i class="fa fa-file"></i></button>
								</a>
								<a id="editar" href="<c:url value="/projeto/editar/${projeto.id}" ></c:url>" title="Editar projeto" class="btn btn-primary btn-sm">
									<i class="fa fa-edit"></i>
								</a>
								<a id="excluir" data-toggle="modal" data-target="#confirm-delete" href="#" title="Excluir" data-href="<c:url value="/projeto/excluir/${projeto.id}"></c:url>" data-name="${projeto.nome }" class="btn btn-danger btn-sm">
									<i class="fa fa-trash-o"></i>
								</a>
							</label>
						</div>
					</c:if>
					
					<div id="accordion" class="accordion">
						<div class="accordion-group">
							<div class="accordion-heading">
								<h4 class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#basico"><i class="accordion-icon fa fa-minus"></i>	Informações Básicas</h4>
							</div>
							<span class="line"></span>
							<div id="basico" class="accordion-body collapse in">
								<div class="form-group">
									<label class="col-sm-2 control-label">Nome:</label>
									<div class="col-sm-4 value-label">
										<label>${projeto.nome }</label>
									</div>
								</div><!-- nome -->
								<div class="form-group">
									<label class="col-sm-2 control-label">Descrição:</label>
									<div class="col-sm-10 value-label">
										<label>${projeto.descricao }</label>
									</div>
								</div><!-- descrição -->
								<div class="form-group">
									<label class="col-sm-2 control-label">Coordenador:</label>
									<div class="col-sm-4 value-label">
										<label><a href="<c:url value="/pessoa/detalhes/${projeto.coordenador.id}" ></c:url>">${projeto.coordenador.nome}</a></label>
									</div>
									<label class="col-sm-3 control-label">Status:</label>
									<div class="col-sm-3 value-label">
										<label>${projeto.status.descricao }</label>
									</div>
								</div><!-- coordenador -->
								<div class="form-group">
									<label class="col-sm-2 control-label">Início:</label>
									<div class="col-sm-2 value-label">
										<c:if test="${empty projeto.inicio }">
											<label>-</label>
										</c:if>
										<label><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.inicio }" /></label>
									</div>
									<label class="col-sm-2 control-label">Término:</label>
									<div class="col-sm-2 value-label">
										<c:if test="${empty projeto.termino }">
											<label>-</label>
										</c:if>
										<label><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.termino }" /></label>
									</div>
								</div><!-- data inicio e fim -->
								<div class="form-group">
									<label class="col-sm-2 control-label">Valor:</label>
									<div class="col-sm-2 value-label">
										<fmt:setLocale value="pt_BR"/>
										<label><fmt:formatNumber value="${projeto.valorProjeto }" type="currency" currencySymbol="R$ "/></label>
									</div>
									<label class="col-sm-2 control-label">Fonte:</label>
									<div class="col-sm-2 value-label">
										<label>${projeto.fonteFinanciamento.nome}</label>
									</div>
								</div><!-- valor e fonte de financiamento -->
								<div class="form-group">
									<label class="col-sm-2 control-label">Data de submissão:</label>
									<div class="col-sm-4 value-label">
										<c:if test="${empty projeto.submissao }">
											<label>-</label>
										</c:if>
										<c:if test="${not empty projeto.submissao }">
											<label>
											<fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${projeto.submissao }" />
											</label>
										</c:if>
									</div>
							
									<c:if test="${not empty projeto.parecer.dataRealizacao }">
										<div class="col-sm-3 control-label">
											<label>Data de emissão do parecer:</label>
										</div>
										<div class="col-sm-3 value-label">
											<label>
												<fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${projeto.parecer.dataRealizacao }" />
											</label>
										</div>
									</c:if>
								</div> <!-- data submissão -->
								<div class="form-group">
									<label class="col-sm-2 control-label">Local de execução:</label>
									<div class="col-sm-10 value-label">
										<c:if test="${empty projeto.local }">
											<label>-</label>
										</c:if>
										<c:if test="${not empty projeto.local }">
											<label>${projeto.local }</label>
										</c:if>					
									</div>
								</div><!-- local de execução -->
								<div class="form-group">
									<c:if test="${not empty projeto.homologacao }">
										<div class="col-sm-2 control-label">
											<label class="field">Data de Homologação:</label>
										</div>
										<div class="col-sm-10 value-label">
											<label><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${projeto.homologacao }" /></label>				
										</div>
									</c:if>							
								</div><!-- homologação -->
								<div class="form-group">
									<label class="col-sm-2 control-label">Atividades Gerais:</label>
									<div class="col-sm-10 value-label">
										<c:if test="${empty projeto.atividades }">
											<label>-</label>
										</c:if>
										<c:if test="${not empty projeto.atividades }">
											<label>							
											${projeto.atividades }
											</label>
										</c:if>
									</div>		
								</div><!-- atividades gerais -->
							</div><!-- basico body -->
						</div> <!-- accordion basico -->
						
						
						<div class="accordion-group">
							<div class="accordion-heading">
								<h4 class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#anexos"><i class="accordion-icon fa fa-plus"></i>	Anexos</h4>
							</div>
							<span class="line"></span>
							<div id="anexos" class="accordion-body collapse">
								<div class="form-group">
						<label class="col-sm-2 control-label">Anexos:</label>
						<div class="col-sm-10 value-label">
							<c:if test="${empty projeto.documentos }">
								<label>-</label>
							</c:if>
							<c:if test="${not empty projeto.documentos }">
								<table id="anexos-table">
									<thead>
										<tr>
											<th class="text-left">Nome</th>
											<th class="text-left">Autor</th>
											<th class="text-left">Data</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${projeto.documentos }" var="documento">
											<tr>
												<th class="col-sm-2 value-label"><label><a
														href="<c:url value="/documento/${projeto.id }/${documento.id }" ></c:url>">${documento.nome }</a></label>
												</th>
												<th class="col-sm-2 value-label"><label><c:url
															value=" ${documento.pessoa.nome}"></c:url></label></th>
												<th class="col-sm-2 value-label"><label><fmt:formatDate
															pattern="dd/MM/yyyy HH:mm" value="${documento.data }" /></label>
												</th>

											</tr>
											<br>
										</c:forEach>
									</tbody>
								</table>
							</c:if>


						</div>
					</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Arquivo do Projeto:</label>
										<div class="col-sm-10 value-label">
											<c:if test="${empty projeto.arquivoProjeto }">
												<label>-</label>						
											</c:if>				
											<c:if test="${not empty projeto.arquivoProjeto }">
												<label><a href="<c:url value="/documento/${projeto.id }/${projeto.arquivoProjeto.id }" ></c:url>">${projeto.arquivoProjeto.nome }</a></label><br>	
											</c:if>
										</div>
								</div><!-- arquivo projeto -->
							</div> <!-- anexos body -->
						</div> <!-- accordion anexos-->
	
						
						<div class="accordion-group">
							<div class="accordion-heading">
								<h4 class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#participantes"><i class="accordion-icon fa fa-plus"></i>	Participantes</h4>
							</div>
							<span class="line"></span>
							<div id="participantes" class="accordion-body collapse">
								<div>
									<div>
										<c:if test="${empty projeto.participacoes }">
											<label>-</label>
										</c:if>
										<c:if test="${not empty projeto.participacoes }">
											<table id="participacoes-projeto" class="display">
												<thead>
													<tr>
														<th class="dt-center">Participante</th>
														<th class="dt-center">Início</th>
														<th class="dt-center">Término</th>
														<th class="dt-center">Carga Horária Mensal</th>
														<th class="dt-center">Valor da Bolsa</th>
														<th class="dt-center">Tipo de Participação</th>
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
															<td class="dt-center"><fmt:formatNumber type="CURRENCY"
																	currencyCode="BRL">${participacao.bolsaValorMensal}</fmt:formatNumber></td>
															<td class="dt-center">${participacao.tipo.descricao }</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
									</div>
								</div><!-- participantes -->
							</div><!-- participantes body -->
						</div><!-- accordion participantes -->
						
						<div class="accordion-group">
							<div class="accordion-heading">
								<h4 class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#logs"><i class="accordion-icon fa fa-plus"></i>Histórico</h4>
							</div>
							<div id="logs" class="accordion-body collapse">
								<div class="form-group">
									<c:forEach items="${projeto.logs }" var="log">
										<label class="col-sm-2 control-label">Descrição:</label>
											<div class="col-sm-2 value-label">
												<label >${log.descricao}</label>
											</div>
										<label class="col-sm-2 control-label">Autor:</label>
											<div class="col-sm-2 value-label">
												<label>${log.autor}</label>
											</div>
										<label class="col-sm-2 control-label">Data:</label>
											<div class="col-sm-2 value-label">
												<label><fmt:formatDate pattern="dd/MM/yyyy" value="${log.data}" /></label>
											</div><br><br><br>
										</c:forEach>	
									</div>
								</div><!-- participantes -->
							</div><!-- participantes body -->
						</div><!-- accordion participantes -->
						
						<span class="line"></span>
						
						<c:if test="${not empty projeto.parecer and projeto.status != 'RESOLVENDO_PENDENCIAS'}">
							<div class="accordion-group">
								<div class="accordion-heading">
									<h4 class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#parecer"><i class="accordion-icon fa fa-plus"></i>	Parecer Técnico</h4>
								</div>
								<span class="line"></span>
								<div class="accordion-body collapse" id="parecer">
									<div class="form-group">
										<label class="col-sm-2 control-label">Parecerista:</label>
										<div class="col-sm-4 value-label">
											<label><a href="<c:url value="/pessoa/detalhes/${projeto.parecer.parecerista.id}" ></c:url>">${projeto.parecer.parecerista.nome}</a></label>
										</div>
										<c:if test="${projeto.status == 'AGUARDANDO_PARECER'}">
											<label class="col-sm-2 control-label">Prazo parecer:</label>
											<div class="col-sm-4 value-label">
												<label><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.parecer.prazo }" /></label>
											</div>						
										</c:if>
										<c:if test="${projeto.status != 'AGUARDANDO_PARECER'}">
											<label class="col-sm-2 control-label">Posicionamento:</label>
											<div class="col-sm-4 value-label">
												<label>${projeto.parecer.status.descricao }</label>
											</div>						
										</c:if>
										<label class="col-sm-2 control-label">Parecer:</label>
										<div class="col-sm-10 value-label">
											<label>${projeto.parecer.parecer}</label>
										</div>
									</div><!-- parecer -->
									<div class="form-group">
										<label class="col-sm-2 control-label">Anexo:</label>
										<div class="col-sm-10 value-label">
											<a href="<c:url value="/documento/${projeto.id }/${projeto.parecer.documento.id }" />">${projeto.parecer.documento.nome }</a>
										</div>
									</div><!-- anexo -->
								</div><!-- parecer body -->
							</div><!-- accordion parecer -->
						</c:if>
						
						<c:if test="${not empty projeto.parecerRelator}">	
							<div class="accordion-group">
								<div class="accordion-heading">
									<h4 class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#relator"><i class="accordion-icon fa fa-plus"></i>		Parecer Relator</h4>
								</div>
								<span class="line"></span>
								<div class="accordion-body collapse" id="relator">
									<div class="form-group">
										<label class="col-sm-2 control-label">Relator:</label>
										<div class="col-sm-4 value-label">
											<label><a href="<c:url value="/pessoa/detalhes/${projeto.parecerRelator.relator.id}" ></c:url>">${projeto.parecerRelator.relator.nome}</a></label>
										</div>
										
										<c:if test="${not empty projeto.parecerRelator.status}">
											<label class="col-sm-2 control-label">Posicionamento:</label>
											<div class="col-sm-4 value-label">
												<label>${projeto.parecerRelator.status.descricao }</label>
											</div>						
										
											<label class="col-sm-2 control-label">Observação:</label>
											<div class="col-sm-10 value-label">
												<label>${projeto.parecerRelator.observacao}</label>
											</div>
										</c:if>
										<c:if test="${empty projeto.parecerRelator.status}">
											<label class="col-sm-3 control-label">Aguardando Avaliação</label>
										</c:if>
									</div><!-- relator -->
								</div><!-- relator body -->
							</div><!-- accordion relator -->
						</c:if>
					
						
						<c:if test="${projeto.status == 'APROVADO' or  projeto.status == 'REPROVADO'}">
							<div class="accordion-group">
								<div class="accordion-heading">
									<h4 class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#homologacao"><i class="accordion-icon fa fa-plus"></i>		Homologação</h4>
								</div>
								<span class="line"></span>
								<div class="accordion-body collapse" id="homologacao">
									<div class="form-group">
										<label class="col-sm-2 control-label">Observação:</label>
										<div class="col-sm-10 value-label">
											<c:if test="${empty projeto.observacaoHomologacao }">
												<label>-</label>
											</c:if>
											<c:if test="${not empty projeto.observacaoHomologacao }">
												<label>							
												${projeto.observacaoHomologacao }
												</label>
											</c:if>
										</div>		
									</div><!-- observacao  -->
									<div class="form-group">
										<label class="col-sm-2 control-label">Oficio:</label>
										<div class="col-sm-10 value-label">
											<c:if test="${empty projeto.oficio }">
												<label>-</label>						
											</c:if>				
											<c:if test="${not empty projeto.oficio }">
												<label><a href="<c:url value="/documento/${projeto.id }/${projeto.oficio.id }" ></c:url>">${projeto.oficio.nome }</a></label><br>							
											</c:if>
										</div>
									</div><!-- oficio -->
									<div class="form-group">
										<label class="col-sm-2 control-label">Ata:</label>
										<div class="col-sm-10 value-label">
											<c:if test="${empty projeto.ata }">
												<label>-</label>
											</c:if>
											<c:if test="${not empty projeto.ata }">
												<label><a href="<c:url value="/documento/${projeto.id }/${projeto.ata.id }" ></c:url>">${projeto.ata.nome }</a></label><br>							
											</c:if>
										</div>
									</div><!-- ata -->
								</div><!-- homologacao body -->
							</div><!-- accordion homologacao -->
						</c:if>
					</div><!-- div accordion - geral -->
										
					<c:if test="${permissao != 'participante' }">
					<h4 class="subtitle">Comentários</h4>
					<span class="line"></span>
					<div id="comentarios" class="col-sm-12">
						<c:forEach items="${projeto.comentarios }" var="comentario">
							<div class="panel panel-default">
								<div class="panel-heading">${comentario.autor.nome } 
									<span class="date-comment"><fmt:formatDate pattern="dd/MM/yyyy" value="${comentario.data }"/> - 
									<fmt:formatDate pattern="HH:mm" value="${comentario.data }" /></span>
								</div>
								<div class="panel-body">${comentario.texto }</div>
							</div>
						</c:forEach>
					</div>
					</c:if>
						<form id="comentarForm">
							<div id="div-comentario" class="col-sm-12 form-item">
								<div id="campo-comentario" class="col-sm-12">
									<textarea id="comentario" name="comentario" class="form-control" rows="5" placeholder="Comentário" required="required"></textarea>
								</div>
							</div>
							<br>
							<div class="controls">
								<input id="comentar" name="comentar" type="submit" class="btn btn-primary" value="Enviar" />
							</div>
						</form>
				
				</div> <!-- form-horizontal -->
			</div> <!-- /panel-body -->
		</div> <!-- /panel -->
	</div> <!-- /container -->
	
	<!-- Modal Excluir Projeto -->
	<div class="modal fade" id="confirm-delete">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;<span class="sr-only">Close</span></button>
	       			<h4 class="modal-title">Excluir</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<a href="#" class="btn btn-danger btn-sm">Excluir</a>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal Submeter Projeto -->
	<div class="modal fade" id="confirm-submit">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
	       			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;<span class="sr-only">Close</span></button>
	       			<h4 class="modal-title">Submeter</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<a href="#" class="btn btn-primary btn-sm">Submeter</a>
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
	
	<jsp:include page="../modulos/footer.jsp" />
	
	<script type="text/javascript">
		$(function(){
			$('#menu-projetos').addClass('active');
		});
	</script>
</body>
</html>
