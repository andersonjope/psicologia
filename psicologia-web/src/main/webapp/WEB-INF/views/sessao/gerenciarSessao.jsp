<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function() {
		$("#showBotaoVoltar").removeAttr("style");
		$("#iniciar").click(function() {
		 	$("#acao").val('1');
		 	$("#formSessaoId").submit();
		});
		
		$("#pausar").click(function() {
		 	$("#acao").val('2');
		 	$("#formSessaoId").submit();
		});
		
		$("#encerrar").click(function() {
		 	$("#acao").val('3');
		 	$("#formSessaoId").submit();
		});
		
		$("#aumentar").click(function() {
		 	$("#acao").val('4');
		 	$("#formSessaoId").submit();
		});
		
		$("#diminuir").click(function() {
		 	$("#acao").val('5');
		 	$("#formSessaoId").submit();
		});
		
		$("#somLigado").click(function() {
		 	$("#acao").val('6');
		 	$("#formSessaoId").submit();
		});
		
		$("#somMudo").click(function() {
		 	$("#acao").val('7');
		 	$("#formSessaoId").submit();
		});
	});
</script>

</head>
	<spring:url value="/alterarSessao" var="alterarSessao" />
	<form:form id="formSessaoId" modelAttribute="formularioSessao" action="${alterarSessao}" method="POST">
		<form:hidden path="nuSessao" />
		<form:hidden path="acao" id="acao"/>
		<form:hidden path="sessaoIniciada"/>
		<form:hidden path="somAtivo"/>
	
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:if test="${not formularioSessao.sessaoIniciada}">
					<a id="iniciar" href="#" >
						<img src="resources/imagens/play.png" title="Iniciar" align="middle" height="50" width="50" >
					</a>
				</c:if>
				<c:if test="${formularioSessao.sessaoIniciada}">
					<a id="pausar" href="#" >
						<img src="resources/imagens/pause.png" title="Pausar" align="middle" height="50" width="50" >				
					</a>
				</c:if>
				<c:if test="${formularioSessao.sessaoIniciada}">
					<a id="encerrar" href="#" >
						<img src="resources/imagens/closes.png" title="Encerrar sessão" align="middle" height="50" width="50" >
					</a>
				</c:if>
				<c:if test="${formularioSessao.sessaoIniciada}">
					<a id="aumentar" href="#" >
						<img src="resources/imagens/plus.png" title="Aumentar" align="middle" height="40" width="40" >		
					</a>
					<a id="diminuir" href="#" >
						<img src="resources/imagens/menos.png" title="Diminuir" align="middle" height="40" width="40" >		
					</a>
					<c:if test="${not formularioSessao.somAtivo}">
						<a id="somLigado" href="#" >
							<img src="resources/imagens/autofligado.png" title="Som" align="middle" height="30" width="30" >		
						</a>
					</c:if>
					<c:if test="${formularioSessao.somAtivo}">
						<a id="somMudo" href="#" >
							<img src="resources/imagens/autofdesligado.png" title="Mudo" align="middle" height="30" width="30">
						</a>
					</c:if>
				</c:if>
			</div>
		</div>
	</form:form>
	
	<c:if test="${not empty salaSessaoList}">
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th>Data</th>
					<th>Velocidade</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${salaSessaoList}" varStatus="vs" var="s">
					<tr class="gradeX">
						<td><c:out value="${s.dhRegistro}" /></td>
						<td><c:out value="${s.nuVelocidadeMovimento}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</html>