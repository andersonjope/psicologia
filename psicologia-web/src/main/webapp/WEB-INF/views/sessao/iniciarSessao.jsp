<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
</head>
	<c:if test="${not empty clienteList}">
		<spring:url value="/criarSessao" var="criarSessao" />
		<form:form id="formId" modelAttribute="formularioSessao" action="${criarSessao}" method="POST">
			<form:hidden path="nuCliente" id="nuCliente"/>
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
					<c:forEach items="${clienteList}" varStatus="vs" var="c">
						<tr class="gradeX">
							<td><c:out value="${c.usuario.deNome}" /></td>
							<td><c:out value="${c.usuario.deLogin}" /></td>
							<td><c:out value="${c.usuario.coTelefone}" /></td>
							<td>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<button type="submit" onclick="$('#nuCliente').val(${c.nuCliente})" class="btn btn-success">Criar Sessão</button>
									</div>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form:form>
	</c:if>
	
	<script type="text/javascript">
	$(document).ready(function() {
		$("#showBotaoVoltar").removeAttr("style");
		$("#iniciarSessao").css("display", 'none');
	});
	</script>

</html>