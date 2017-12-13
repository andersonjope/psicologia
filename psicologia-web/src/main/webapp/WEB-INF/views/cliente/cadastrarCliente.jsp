<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
</head>
<div class="row">
	<div class="col-xl-10 col-sm-6 mb-3">
		<div class="card text-white bg-success o-hidden h-100">
			<div class="card-body">
				<div class="card-body-icon">
					<i class="fa fa-fw fa-list"></i>
				</div>
				<div class="mr-5">Cadastro de Clientes</div>
			</div>
			<div id="page-wrapper">
            	<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-6">
										<spring:url value="/salvarCliente" var="salvarCliente" />
										<form:form id="formId" modelAttribute="formularioCliente" action="${salvarCliente}" method="POST" >
											<div class="form-group">
												<label>Nome:</label> 
												<form:input path="deNome" id="deNome" size="50"/>
												<form:errors path="deNome" cssClass="error"/>
											</div>
											<div class="form-group">
												<label>E-mail:</label> 
												<form:input path="deLogin" id="deLogin" size="50"/>
												<form:errors path="deLogin" cssClass="error"/>
											</div>
											<div class="form-group">
												<input type="submit" value="Salvar" class="btn btn-primary"/>
											</div>
										</form:form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row">
	                <div class="col-lg-12">
	                    <div class="panel panel-default">
	                        <div class="panel-heading">
	                            Lista de Clientes
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
	                        	<c:forEach items="">
	                        	
	                        	</c:forEach>
	                        </div>
	                    </div>
	                </div>
	           	</div>
	                                                
            </div>
		</div>
	</div>
</div>
</html>