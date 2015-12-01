 	<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
	<head>
		<title>Relatórios</title>
		<jsp:include page="../modulos/header-estrutura.jsp" />
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Relatórios</h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<c:if test="${empty relatorio}">
						<h5><label class="col-sm-2 control-label">Tipo de Relatório:</label></h5>
						<div class="col-sm-6">
							<select id="relatorio" name="relatorioParam" class="form-control">
								<option value=""></option>
								<option value="APROVADOS">PROJETOS APROVADOS</option>
								<option value="REPROVADOS">PROJETOS REPROVADOS</option>
								<option value="POR_USUARIO">PROJETOS POR USUÁRIO</option>
							</select>
						</div>
					</c:if>
					<c:if test="${not empty relatorio}">
					<div class="controls">
						<a href="<c:url value="/direcao/relatorios" />" title="Direção" 
						class="btn btn-primary btn-sm">Nova consulta</a>
					</div>
					</c:if>
				</div>
				<hr>
				<c:if test="${empty relatorio}">
					<%-- Form dos projetos aprovados--%>
					<div id="form_aprovados">
				
						<form:form id="relatoriosAprovadosForm"
							enctype="multipart/form-data"
							servletRelativeAction= "relatorios/aprovados" method="GET"
							cssClass="form-horizontal">
							<div class="form-group">
								<div class="form-item">
									<label class ="col-sm-2 control-label" >Início do Intervalo:</label>
									<div class="col-sm-2">
										<input type="text" name="inicio" id="inicioRelatorio"
										class="form-control data">
									</div>
								</div>
								<div class="form-item">
									<label class ="col-sm-2 control-label">Término do Intervalo:</label>
									<div class ="col-sm-2">
										<input type="text" name="termino" id="terminoRelatorio"
										class="form-control data">
									</div>
									<button name="gerar" type="submit" class="btn btn-primary">Gerar</button>
								</div>
							</div>
							
						</form:form>
					</div>
					<%-- form dos projetos reprovados--%>
					<div id="form_reprovados">
						<form:form id="relatoriosRerovadosForm"
							enctype="multipart/form-data"
							servletRelativeAction="relatorios/reprovados" method="GET"
							cssClass="form-horizontal">
							<label class ="col-sm-2 control-label" >Data da submissão:</label>
							<div class="col-sm-2">
								<input type="text" name="submissao" id="submissaoRelatorio"
								class="form-control data">
							</div>
							<button name="gerar" type="submit" class="btn btn-primary">Gerar</button>
						</form:form>
					</div>
					<%-- form por pessoa--%>
					<div id="form_p-pessoa">
						<form:form id="relatoriosPPessoaForm"
							enctype="multipart/form-data"
							servletRelativeAction="relatorios/p-pessoa" method="GET"
							cssClass="form-horizontal">
	
							<div class="form-group form-item">
								<label class="col-sm-1 control-label">Nome:</label>
								<div class="col-sm-5">
									<select id="select_pessoaRelatorio" name="id"
										class="form-control" required>
										<c:set var="part" value="${pessoas }"></c:set>
										<option value=""></option>
										<c:forEach items="${pessoas }" var="pessoa">
											<c:set var="selected" value=""></c:set>
											<c:set var="participanteSelecionado"
												value="id=${pessoa.id }"></c:set>
											<c:if test="${fn:contains(part, participanteSelecionado)}">
												<c:set var="selected" value="selected=\"selected\""></c:set>
											</c:if>
											<option value="${pessoa.id }">${pessoa.nome }</option>
										</c:forEach>
									</select>
	
								</div>
								<!-- div select -->
								<label class="col-sm-1 control-label">Data da submissao:</label>
								<div class="col-sm-2">
									<input type="text" name="ano" id="anoRelatorio"
										class="form-control data">
	
								</div>
								<div class="col-sm-1" id="submit-p-pessoa">
									<button name="gerar" type="submit" class="btn btn-primary">Gerar</button>
								</div>
							</div>
						</form:form>
						<br /> <br />
					</div>
				</c:if>
				<!-- TAB APROVADOS -->
	        	<c:if test="${not empty relatorio}">
					<c:if test="${empty relatorio.projetosAprovados 
						&& empty relatorio.projetosReprovados 
						&& empty relatorio.projetosPorPessoa}">
								<div class="alert alert-warning" role="alert">Não há projetos neste periodo informado.</div>
					</c:if>
				</c:if>
					<c:if test="${not empty relatorio.projetosAprovados}">
						<h4>Informações Gerais</h4>
						<div class="form-group">
						<label class="col-sm-4 control-label">Quantidade de Projetos: ${fn:length(relatorio.projetosAprovados)}</label>
						</div>
						
						<table id="tab-relatorios" class="display">
							<thead>
								<tr>
									<th>Coordenador do Projeto</th>
									<th>Nome do Projeto</th>
									<th>Início</th>
									<th>Término</th>
									<th>Quantidade de Bolsas</th>
									<th>Valor total de Bolsas</th>
 								</tr>
							</thead>
							<tbody>
								<c:forEach var="projeto" items="${relatorio.projetosAprovados}">
								<tr>
									<td>${projeto.nomeCoordenador}</td>
									<td><a
										href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nomeProjeto}</a>
									</td>
									<td>${projeto.dataInicio}</td>
									<td>${projeto.dataTermino}</td>
									<td>${projeto.qtdBolsas}</td>
									<td>${projeto.valorTotalBolsas}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</c:if>
	        	</div>
	        	</div>
 	        	<!-- TAB REPROVADOS -->
	        	<div id="tab-projetos-aprovados">
					<c:if test="${not empty relatorio.projetosReprovados}">
						<h4>Informações Gerais</h4>
						<div class="form-group">
						<label class="col-sm-4 control-label">Quantidade de Projetos: ${fn:length(relatorio.projetosReprovados)}</label>
						</div>
						
						<table id="tab-relatorios" class="display">
							<thead>
								<tr>
									<th>Coordenador do Projeto</th>
									<th>Nome do Projeto</th>
									<th>Submissão</th>
									<th>Avaliação</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="projeto" items="${relatorio.projetosReprovados}">
									<tr>
										<td>${projeto.nomeCoordenador}</td>
										<td>
											<a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nomeProjeto}</a>
										</td>
										<td>${projeto.dataDeSubimissao}</td>
										<td>${projeto.dataDeAvaliacao}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
	        	</div>
				<!-- TAB P/ PESSOA -->
				<div id="tab-p-pessoa">
					<c:if test="${not empty relatorio.projetosPorPessoa}">
							<h4>Informações Gerais</h4>
							<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">Nome:</label>
								<div class="col-sm-4 value-label">
									<label>${relatorio.nomeUsuario}</label>
								</div>
								<label class="col-sm-2 control-label">Ano: </label>
								<div class="col-sm-4 value-label">
									<label>${relatorio.anoConsulta}</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">Carga Horária Total: </label>
								<div class="col-sm-4 value-label">
									<label>${relatorio.cargaHorariaTotalUsuario}</label>
								</div>
								<label class="col-sm-2 control-label">Valor Total de Bolsas: </label>
								<div class="col-sm-4 value-label">
									<label>${relatorio.valorTotalBolsasUsuario}</label>
								</div>
							</div>
							</div>
						
						<table id="tab-relatorios" class="display">
							<thead>
								<tr>
									<th>Nome do Projeto</th>
									<th>Vínculo</th>
									<th>Carga Horária</th>
									<th>Valor da Bolsa</th> 

								</tr>
							</thead>
							<tbody>
								<c:forEach var="projeto" items="${relatorio.projetosPorPessoa}">
									<tr>
										<td>
											<a href="<c:url value="/projeto/detalhes/${projeto.id}" ></c:url>">${projeto.nomeProjeto}</a>
										</td>
										<td>${projeto.vinculo}</td>
										<td>${projeto.cargaHoraria}</td>
										<td>${projeto.valorBolsa}</td> 

									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
			</div><!-- /panel-body -->
		</div><!--/panel  -->
	</div><!-- /container -->
	<jsp:include page="../modulos/footer.jsp" />
</body>	
</html>
