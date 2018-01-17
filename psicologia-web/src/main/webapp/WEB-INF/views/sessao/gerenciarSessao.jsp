<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	function visualizarSalaSessao(){
		window.location.href = contextPath + "/cliente?idCliente=" + "${sessao.cliente.usuario.deLogin}";
	}
</script>

</head>
<div class="row">
	<div class="col-xl-10 col-sm-6 mb-3">
		<div class="card text-white bg-primary o-hidden h-100">
			<div class="card-body">
				<div class="card-body-icon">
					<i class="fa fa-fw fa-list"></i>
				</div>
				<div class="mr-5">Gerenciar Sessão</div>
			</div>
			<div id="page-wrapper">
            	<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-6">
										<div class="form-group">
											<label>Médico:</label> 
											<label style="color: #ccb;font-weight: bold;"><c:out value ="${sessao.medico.deNome}"/></label>
										</div>
										<div class="form-group">
											<label>Cliente:</label> 
											<label style="color: #ccb;font-weight: bold;"><c:out value ="${sessao.cliente.deNome}"/></label>
										</div>
										
										<spring:url value="/alterarSessao" var="alterarSessao" />
										<form:form id="formId" modelAttribute="formularioSessao" action="${alterarSessao}" method="POST" >
											<form:hidden path="nuSessao" />
											<div class="form-group">
												<label>Velocidade:</label> 
												<form:input path="velocidade"/>
												<br />
												<form:errors path="velocidade" cssClass="error"/>
											</div>
											
											<div class="form-group">
												<input type="submit" value="Start/Alterar Sessão" class="btn"/>
<!-- 												<input type="button" onclick="visualizarSalaSessao(); return false;" value="Visualizar Sessão" class="btn"/> -->
											</div>
										</form:form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
            </div>
			<label class="error">Copie a url e cole em outra aba do navegador, após abrir altere os dados:</label><br />http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/cliente?idCliente=${sessao.cliente.usuario.deLogin}
		</div>
	</div>
	<c:if test="${not empty salaSessaoList}">
		<div id="page-wrapper">
			<div class="row">
		        <div class="col-lg-12">
		            <div class="panel panel-default">
		                <div class="panel-heading">
		                    Histórico de Movimentação
		                </div>
		                <!-- /.panel-heading -->
		                <div class="panel-body">
		                	<div class="table-responsive">
		                         <table width="100%" class="table table-striped table-bordered table-hover">
			                        <thead>
			                            <tr>
			                                <th>Data</th>
			                                <th>Velocidade</th>
			                            </tr>
			                        </thead>
			                        <tbody>
					                  	<c:forEach items="${salaSessaoList}" varStatus="vs" var="s">
					                  		<tr class="gradeX">
			                               		<td><c:out value ="${s.dhRegistro}"/></td>
			                               		<td><c:out value ="${s.nuVelocidadeMovimento}"/></td>
			                               	</tr>
					                  	</c:forEach>
				                 	</tbody>
			                	</table>
			                </div>
		                </div>
		            </div>
		        </div>
		   	</div>
	   	</div>
   	</c:if>
</div>
</html>