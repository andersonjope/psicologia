<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
</head>
	<spring:url value="/salvarCliente" var="salvarCliente" />
	<form:form id="formId" modelAttribute="formularioCliente" action="${salvarCliente}" method="POST">
		<div class="form-group">
			<label class="control-label col-sm-2" for="cliente.deNome">Nome completo</label>
			<div class="col-sm-10">
				<form:input path="cliente.deNome" id="deNome" class="form-control"/>
				<form:errors path="cliente.deNome" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="cliente.usuario.coCPF">CPF</label>
			<div class="col-sm-10">
				<form:input path="cliente.usuario.coCPF" id="coCPF" class="form-control"/>
				<form:errors path="cliente.usuario.coCPF" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="cliente.usuario.deLogin">Email</label>
			<div class="col-sm-10">
				<form:input path="cliente.usuario.deLogin" id="deLogin" class="form-control"/>
				<form:errors path="cliente.usuario.deLogin" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="cliente.usuario.deEndereco">Endereço</label>
			<div class="col-sm-10">
				<form:input path="cliente.usuario.deEndereco" id="deEndereco" class="form-control"/>
				<form:errors path="cliente.usuario.deEndereco" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="cliente.usuario.coCep">CEP</label>
			<div class="col-sm-10">
				<form:input path="cliente.usuario.coCep" id="coCep" class="form-control"/>
				<form:errors path="cliente.usuario.coCep" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="cliente.usuario.deCidade">Cidade</label>
			<div class="col-sm-10">
				<form:input path="cliente.usuario.deCidade" id="deCidade" class="form-control"/>
				<form:errors path="cliente.usuario.deCidade" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="cliente.usuario.dePais">País</label>
			<div class="col-sm-10">
				<form:input path="cliente.usuario.dePais" id="dePais" class="form-control"/>
				<form:errors path="cliente.usuario.dePais" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="cliente.usuario.coTelefone">Telefone</label>
			<div class="col-sm-10">
				<form:input path="cliente.usuario.coTelefone" id="coTelefone" class="form-control" placeholder="(xx)9xxxx-xxxx"/>
				<form:errors path="cliente.usuario.coTelefone" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="deNascimento">Data de nascimento</label>
			<div class="col-sm-10">
				<form:input path="deNascimento" id="deNascimento" class="form-control" placeholder="dd/mm/aa"/>
				<form:errors path="deNascimento" cssClass="error"/>
			</div>
		</div>
	
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="cliente.usuario.deSexo">Sexo</label>
			<div class="col-sm-10">
				<label class="radio-inline">
					<form:radiobutton path="cliente.usuario.deSexo" value="M"/>Mascuino
				</label> 
				<label class="radio-inline">
					<form:radiobutton path="cliente.usuario.deSexo" value="F"/>Feminino
				</label>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-success">Enviar cadastro</button>
			</div>
		</div>
	</form:form>

	<c:if test="${not empty clienteList}">
		<table class="table">
	        <thead class="thead-dark">
	            <tr>
	                <th scope="col">Nome</th>
	                <th scope="col">Login</th>
	            </tr>
	        </thead>
            <tbody>
	         	<c:forEach items="${clienteList}" varStatus="vs" var="c">
	         		<tr class="gradeX">
                  		<td><c:out value ="${c.deNome}"/></td>
                  		<td><c:out value ="${c.usuario.deLogin}"/></td>
                  	</tr>
	         	</c:forEach>
       		</tbody>
     	</table>
	</c:if>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#showBotaoVoltar").removeAttr("style");
		});
	</script>
  
</html>