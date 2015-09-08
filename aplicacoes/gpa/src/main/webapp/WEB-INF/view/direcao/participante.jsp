<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Informações do Usuário</title>
</head>

<body>
	<div class="container">
		<jsp:include page="../modulos/header.jsp" />
		<div class="formulario detalhes">
			<input id="projetoId" type="hidden" value="${projeto.id }" />
			<h2>${pessoa.nome }</h2>
			<div class="form-group">
				<label class="col-sm-2 control-label field">Email:</label>
				<div class="col-sm-4 field-value">
					<label>${pessoa.email }</label>
				</div>
			</div>			
			<hr>
		
			<div id="tabs" class="tabs">
				<nav>
			        <ul>
			            <li><a href="#section-projetos-coordenador"><span>Coordena&nbsp; <i class="fa"></i></span></a></li>
			            <li><a href="#section-projetos-participante"><span>Participa&nbsp; <i class="fa"></i></span></a></li>
			            <li><a href="#section-projetos-coordenou"><span>Coordenou&nbsp; <i class="fa"></i></span></a></li>
			            <li><a href="#section-projetos-participou"><span>Participou&nbsp; <i class="fa"></i></span></a></li>			            
			            <li><a href="#section-projetos-reprovados"><span>Reprovados&nbsp; <i class="fa"></i></span></a></li>			            
			        </ul>
		    	</nav>				
		        			
				<div class="content">
					<section id="section-projetos-coordenador">
					<div class="col-sm-12 field-value">
						<c:if test="${empty pessoa.projetos }">
							<div class="alert alert-warning" role="alert">Não há projetos cadastrados.</div>
						</c:if>
					</div>
					<div class="col-sm-12 field-value">
						<table class="table table-condensed">				
							<c:forEach items="${pessoa.projetos }" var="projeto">
								<tr class="active">
									<td>
									<a href="<c:url value="/projeto/${projeto.id }/detalhes" />"
									class="col-sm-12" style="padding-left: 0px;">${projeto.nome }</a>
									</td>
								</tr>
							</c:forEach>						
						</table>
					</div>
					</section>
					<section id="section-projetos-participante">
						<div class="col-sm-12 field-value">					
							<c:if test="${empty pessoa.projetos }">
								<div class="alert alert-warning" role="alert">Não há projetos cadastrados.</div>
							</c:if>
						</div>
						<div class="col-sm-12 field-value">
							<table class="table table-condensed">
							<tbody>
								<c:forEach items="${projetos }" var="projeto">
									<tr class="active">
										<td>
										<a href="<c:url value="/projeto/${projeto.id }/detalhes" />"
											class="col-sm-12" style="padding-left: 0px;">${projeto.nome }</a>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</section>
					<section id="section-projetos-coordenou">
						<div class="col-sm-12 field-value">					
								<c:if test="${empty coordenou }">
									<div class="alert alert-warning" role="alert">Não há projetos cadastrados.</div>
								</c:if>
							</div>
							<div class="col-sm-12 field-value">
								<table class="table table-condensed">
								<tbody>
									<c:forEach items="${coordenou }" var="projeto">
										<tr class="active">
											<td>
											<a href="<c:url value="/projeto/${projeto.id }/detalhes" />"
												class="col-sm-12" style="padding-left: 0px;">${projeto.nome }</a>
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
					</section>
					
					<section id="section-projetos-participou">
						<div class="col-sm-12 field-value">					
							<c:if test="${empty participou }">
								<div class="alert alert-warning" role="alert">Não há projetos cadastrados.</div>
							</c:if>
						</div>
						<div class="col-sm-12 field-value">
							<table class="table table-condensed">
							<tbody>
								<c:forEach items="${participou }" var="projeto">
									<tr class="active">
										<td>
										<a href="<c:url value="/projeto/${projeto.id }/detalhes" />"
											class="col-sm-12" style="padding-left: 0px;">${projeto.nome }</a>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</section>
					
					<section id="section-projetos-reprovados">
						<div class="col-sm-12 field-value">					
							<c:if test="${empty reprovados }">
								<div class="alert alert-warning" role="alert">Não há projetos cadastrados.</div>
							</c:if>
						</div>
						<div class="col-sm-12 field-value">
							<table class="table table-condensed">
							<tbody>
								<c:forEach items="${reprovados }" var="projeto">
									<tr class="active">
										<td>
										<a href="<c:url value="/projeto/${projeto.id }/detalhes" />"
											class="col-sm-12" style="padding-left: 0px;">${projeto.nome }</a>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</section>
				</div>

			</div>
		</div>
	</div>
	<jsp:include page="../modulos/footer.jsp" />
	<script>
	    new 
		CBPFWTabs(document.getElementById('tabs'));
	</script>
</body>
</html>
