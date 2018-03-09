<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<style type="text/css">
		div.bloco1 {
		    position: relative;
		    width: 250px;
		    height: 250px;
		    border: 3px solid #0000005c;
		    margin-top: 0%;
		    margin-right: 10%;
		    margin-bottom: 0%;
		    margin-left: 24%;
		}
		
		div.bloco2 {
		    position: relative;
		    width: 250px;
		    height: 250px;
		    border: 3px solid #0000005c;
		    margin-top: -18%;
		    margin-right: 10%;
		    margin-bottom: 0%;
		    margin-left: 57%;
		}
		
		div.bloco1image {
		    margin-top: 26%;
		    margin-right: 10%;
		    margin-bottom: 0%;
		    margin-left: 25%;
		}
		
		div.bloco2image {
		    margin-top: 20%;
		    margin-right: 10%;
		    margin-bottom: 0%;
		    margin-left: 20%;
		}
	</style>
</head>
<body>

<!-- 	<form class="form-inline my-2 my-lg-0"> -->
<!-- 		<input class="form-control mr-sm-2" type="search" placeholder="Nome" -->
<!-- 			aria-label="Search"> -->
<!-- 		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Procurar</button> -->
<!-- 	</form> -->

	<c:if test="${not empty sessao and sessionScope.usuario.enumPerfil.ordinal() eq 2}">
		<div class="jumbotron text-center">
			<div class="bloco1">
				<div class="bloco1image">
					<c:if test="${sessaoAberta}">
						<a href="cliente"> 
							<img src="resources/imagens/dooropen.png" align="middle" height="130" width="130" title="Sessão Aberta" >
						</a>
					</c:if>	
					<c:if test="${not sessaoAberta}">
						<img src="resources/imagens/doorclose.jpg" align="middle" height="160" width="160" title="Encerrada sessão" >
					</c:if>	
				</div>
			</div>
		</div>
	</c:if>
	
	<c:if test="${not empty sessaoMedicoList and sessionScope.usuario.enumPerfil.ordinal() eq 1}">
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th>Nome</th>
					<th>Email</th>
					<th>Telefone</th>
					<th>Sesão</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${sessaoMedicoList}" varStatus="vs" var="s">
					<tr class="gradeX">
						<td><c:out value="${s.cliente.deNome}" /></td>
						<td><c:out value="${s.cliente.usuario.deLogin}" /></td>
						<td><c:out value="${s.cliente.usuario.coTelefone}" /></td>
						<td>
							<c:if test="${not empty s.dhFinalSessao}">
								<img src="resources/imagens/close.jpg" align="middle" height="30" width="30" title="Encerrada sessão" >
							</c:if>
							<c:if test="${empty s.dhFinalSessao}">
								<a href="gerenciarSessao?sessao=${s.nuSessao}"> 
									<img src="resources/imagens/open.jpg" align="middle" height="27" width="27" title="Abrir sessão" >
								</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

</body>
</html>