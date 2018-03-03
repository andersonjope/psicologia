<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
</head>
	<spring:url value="/salvarMedico" var="salvarMedico" />
	<form:form id="formId" modelAttribute="formularioMedico" action="${salvarMedico}" method="POST">
		<div class="form-group">
			<label class="control-label col-sm-2" for="medico.deNome">Nome completo</label>
			<div class="col-sm-10">
				<form:input path="medico.deNome" id="deNome" class="form-control"/>
				<form:errors path="medico.deNome" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="medico.usuario.coCPF">CPF</label>
			<div class="col-sm-10">
				<form:input path="medico.usuario.coCPF" id="coCPF" class="form-control"/>
				<form:errors path="medico.usuario.coCPF" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="medico.usuario.deLogin">Email</label>
			<div class="col-sm-10">
				<form:input path="medico.usuario.deLogin" id="deLogin" class="form-control"/>
				<form:errors path="medico.usuario.deLogin" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="medico.usuario.deEndereco">Endereço</label>
			<div class="col-sm-10">
				<form:input path="medico.usuario.deEndereco" id="deEndereco" class="form-control"/>
				<form:errors path="medico.usuario.deEndereco" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="medico.usuario.coCep">CEP</label>
			<div class="col-sm-10">
				<form:input path="medico.usuario.coCep" id="coCep" class="form-control"/>
				<form:errors path="medico.usuario.coCep" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="medico.usuario.deCidade">Cidade</label>
			<div class="col-sm-10">
				<form:input path="medico.usuario.deCidade" id="deCidade" class="form-control"/>
				<form:errors path="medico.usuario.deCidade" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="medico.usuario.dePais">País</label>
			<div class="col-sm-10">
				<form:input path="medico.usuario.dePais" id="dePais" class="form-control"/>
				<form:errors path="medico.usuario.dePais" cssClass="error"/>
			</div>
		</div>
	
		<div class="form-group">
			<label class="control-label col-sm-2" for="medico.usuario.coTelefone">Telefone</label>
			<div class="col-sm-10">
				<form:input path="medico.usuario.coTelefone" id="coTelefone" class="form-control" placeholder="(xx)9xxxx-xxxx"/>
				<form:errors path="medico.usuario.coTelefone" cssClass="error"/>
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
			<label class="control-label col-sm-2" for="medico.usuario.deSexo">Sexo</label>
			<div class="col-sm-10">
				<label class="radio-inline">
					<form:radiobutton path="medico.usuario.deSexo" value="M"/>Mascuino
				</label> 
				<label class="radio-inline">
					<form:radiobutton path="medico.usuario.deSexo" value="F"/>Feminino
				</label>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-success">Enviar cadastro</button>
			</div>
		</div>
	</form:form>
	
	<c:if test="${not empty medicoList}">
		<table class="table">
	        <thead class="thead-dark">
	            <tr>
	                <th scope="col">Nome</th>
	                <th scope="col">Login</th>
	            </tr>
	        </thead>
            <tbody>
	         	<c:forEach items="${medicoList}" varStatus="vs" var="m">
	         		<tr class="gradeX">
                  		<td><c:out value ="${m.deNome}"/></td>
                  		<td><c:out value ="${m.usuario.deLogin}"/></td>
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