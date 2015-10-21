<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Informações do Usuário</title>
</head>

<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Informações do Usuário</h3>
			</div>
			<div class="panel-body">
				<div class="bs-component">
					<h3>${pessoa.nome }
						<small class="text-muted">${pessoa.email}</small>
					</h3>
					<br>
					<ul class="nav nav-tabs">
						<li class="active"><a aria-expanded="false" href="#aba_coordena" data-toggle="tab"><span class="visible-md-inline visible-lg-inline">Projetos que </span>Coordena</a></li>
						<li class=""><a aria-expanded="false" href="#aba_participa" data-toggle="tab"><span class="visible-md-inline visible-lg-inline">Projetos que </span>Participa</a></li>
						<li class=""><a aria-expanded="false" href="#aba_coordenou" data-toggle="tab"><span class="visible-md-inline visible-lg-inline">Projetos que </span>Coordenou</a></li>
						<li class=""><a aria-expanded="false" href="#aba_participou" data-toggle="tab"><span class="visible-md-inline visible-lg-inline">Projetos que </span>Participou</a></li>
						<li class="disabled"><a aria-expanded="false" href="#aba_reprovados" data-toggle="tab">Reprovados</a></li>
					</ul>
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade active in" id="aba_coordena">
							<h5>
								Projetos <b>em andamento</b> que este usuário <b>coordena</b>.
							</h5>
							<br>
							<c:if test="${empty pessoa.projetos }">
								<div class="alert alert-warning">
									<h4>Atenção!</h4>
									<p>Não há projetos cadastrados.</p>
								</div>
							</c:if>

							<div class="list-group">
								<c:forEach items="${pessoa.projetos }" var="projeto">
									<a href="<c:url value="/projeto/detalhes/${projeto.id }" />"
										class="list-group-item">
										<h4 class="list-group-item-heading">${projeto.nome }</h4>
										<p class="list-group-item-text">${fn:substring(projeto.descricao, 0, 200)}...</p>
									</a>
								</c:forEach>
							</div>
						</div>

						<div class="tab-pane fade" id="aba_participa">
							<h5>
								Projetos <b>em andamento</b> que este usuário <b>participa</b>.
							</h5>
							<br>
							<c:if test="${empty projetos }">
								<div class="alert alert-warning">
									<h4>Atenção!</h4>
									<p>Não há projetos cadastrados.</p>
								</div>
							</c:if>

							<div class="list-group">
								<c:forEach items="${projetos }" var="projeto">
									<a href="<c:url value="/projeto/detalhes/${projeto.id }" />"
										class="list-group-item">
										<h4 class="list-group-item-heading">${projeto.nome }</h4>
										<p class="list-group-item-text">${fn:substring(projeto.descricao, 0, 200)}...</p>
									</a>
								</c:forEach>
							</div>
						</div>

						<div class="tab-pane fade" id="aba_coordenou">
							<h5>
								Projetos <b>concluídos</b> que este usuário <b>coordena</b>.
							</h5>
							<br>
							<c:if test="${empty coordenou }">
								<div class="alert alert-warning">
									<h4>Atenção!</h4>
									<p>Não há projetos cadastrados.</p>
								</div>
							</c:if>

							<div class="list-group">
								<c:forEach items="${coordenou }" var="projeto">
									<a href="<c:url value="/projeto/detalhes/${projeto.id }" />"
										class="list-group-item">
										<h4 class="list-group-item-heading">${projeto.nome }</h4>
										<p class="list-group-item-text">${fn:substring(projeto.descricao, 0, 200)}...</p>
									</a>
								</c:forEach>
							</div>
						</div>

						<div class="tab-pane fade" id="aba_participou">
							<h5>
								Projetos <b>concluídos</b> que este usuário <b>participa</b>.
							</h5>
							<br>
							<c:if test="${empty participou }">
								<div class="alert alert-warning">
									<h4>Atenção!</h4>
									<p>Não há projetos cadastrados.</p>
								</div>
							</c:if>

							<div class="list-group">
								<c:forEach items="${participou }" var="projeto">
									<a href="<c:url value="/projeto/detalhes/${projeto.id }" />"
										class="list-group-item">
										<h4 class="list-group-item-heading">${projeto.nome }</h4>
										<p class="list-group-item-text">${fn:substring(projeto.descricao, 0, 200)}...</p>
									</a>
								</c:forEach>
							</div>
						</div>

						<div class="tab-pane fade" id="aba_reprovados">
							<h5>
								Projetos <b>reprovados</b> que este usuário <b>participa</b>.
							</h5>
							<br>
							<c:if test="${empty reprovados }">
								<div class="alert alert-warning">
									<h4>Atenção!</h4>
									<p>Não há projetos cadastrados.</p>
								</div>
							</c:if>

							<div class="list-group">
								<c:forEach items="${reprovados }" var="projeto">
									<a href="<c:url value="/projeto/detalhes/${projeto.id }" />"
										class="list-group-item">
										<h4 class="list-group-item-heading">${projeto.nome }</h4>
										<p class="list-group-item-text">${fn:substring(projeto.descricao, 0, 200)}...</p>
									</a>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
	
	<script>
		$(function(){
			// Permite acessar uma aba específica informando seu ID na URL.
			// Ex: [URI]/gpa-pesquisa/pessoa/detalhes/7#aba_participa
		
			var abaAtual = $('.nav-tabs');
			abaAtual.on('click', 'a', function(e){
				var $this = $(this);
				e.preventDefault();
				window.location.hash = $this.attr('href');
				$this.tab('show');
			});
			
			// Tab baseada no Hash
			function atualizaHash() {
				abaAtual.find('a[href="'+ window.location.hash +'"]').tab('show');
			}
			
			$(window).bind('hashchange', atualizaHash);
			if(window.location.hash){
				atualizaHash();
			}
		});
	</script>
</body>
</html>
