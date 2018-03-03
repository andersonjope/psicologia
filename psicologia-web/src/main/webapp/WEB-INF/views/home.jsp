<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	
</head>
<body>

<!-- 	<form class="form-inline my-2 my-lg-0"> -->
<!-- 		<input class="form-control mr-sm-2" type="search" placeholder="Nome" -->
<!-- 			aria-label="Search"> -->
<!-- 		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Procurar</button> -->
<!-- 	</form> -->

	<c:if test="${not empty sessaoMedicoList}">
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