<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    
</head>
<body>
	<h1>Sala do Médico</h1>

	<spring:url value="/enviaMensagem" var="enviaMensagem" />
	
	<form:form id="formId" modelAttribute="formularioMedico" action="${enviaMensagem}" method="POST" >
		Selecione o cliente:
		<form:select path="identificaoCliente" id="identificaoCliente">
			<form:option value="NONE"> Select - Cliente </form:option>
			<form:options items="${countClienteList}"></form:options>
		</form:select>
		
		<br />
		<br />
		Informe o valor:
		<form:input path="velocidade" id="velocidade"/>
		
		<input type="submit" value="Enviar" />
		
	</form:form>

</body>
</html>