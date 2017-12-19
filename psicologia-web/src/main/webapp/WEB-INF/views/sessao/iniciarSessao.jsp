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
		<div class="card text-white bg-primary o-hidden h-100">
			<div class="card-body">
				<div class="card-body-icon">
					<i class="fa fa-fw fa-list"></i>
				</div>
				<div class="mr-5">Sessão</div>
			</div>
			<div id="page-wrapper">
            	<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-6">
										<spring:url value="/criarSessao" var="criarSessao" />
										<form:form id="formId" modelAttribute="formularioSessao" action="${criarSessao}" method="POST" >
											<div class="form-group">
												<label>Médico:</label> 
												<form:select path="nuMedico" id="nuMedico">
													<form:option value=""> Select - Médico </form:option>
													<form:options items="${medicoList}" itemValue="nuMedico" itemLabel="deNome"></form:options>
												</form:select>
												<br/>
												<form:errors path="nuMedico" cssClass="error"/>
											</div>
											
											<div class="form-group">
												<label>Cliente:</label> 
												<form:select path="nuCliente" id="nuCliente">
													<form:option value=""> Select - Cliente </form:option>
													<form:options items="${clienteList}" itemValue="nuCliente" itemLabel="deNome"></form:options>
												</form:select>
												<br/>
												<form:errors path="nuCliente" cssClass="error"/>
											</div>
											
											<div class="form-group">
												<input type="submit" value="Criar Sessão" class="btn"/>
											</div>
										</form:form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
	                                                
            </div>
		</div>
	</div>
	<c:if test="${not empty sessaoList}">
		<div id="page-wrapper">
			<div class="row">
		        <div class="col-lg-12">
		            <div class="panel panel-default">
		                <div class="panel-heading">
		                    Sessão Ativas
		                </div>
		                <!-- /.panel-heading -->
		                <div class="panel-body">
		                	<div class="table-responsive">
		                         <table width="100%" class="table table-striped table-bordered table-hover">
			                        <thead>
			                            <tr>
			                                <th>Nome do Médico</th>
			                                <th>Nome do Cliente</th>
			                                <th>Data de Início</th>
			                                <th>Situação</th>
			                                <th>Ações</th>
			                            </tr>
			                        </thead>
			                        <tbody>
					                  	<c:forEach items="${sessaoList}" varStatus="vs" var="s">
					                  		<tr class="gradeX">
			                               		<td><c:out value ="${s.medico.deNome}"/></td>
			                               		<td><c:out value ="${s.cliente.deNome}"/></td>
			                               		<td><c:out value ="${s.dhInicioSessao}"/></td>
			                               		<td>
			                               			<c:if test="${not empty s.dhFinalSessao}">Encerrada</c:if>
			                               			<c:if test="${empty s.dhFinalSessao}">Ativa</c:if>			                               		
			                               		</td>
			                               		<td>
			                               			<c:if test="${empty s.dhFinalSessao}">
				                               			<a href="encerrarSessao?sessao=${s.nuSessao}">
															<i class="fa"></i> Encerrar
														</a>
				                               			<br/>
				                               			<a href="gerenciarSessao?sessao=${s.nuSessao}">
															<i class="fa"></i> Gerenciar
														</a>
													</c:if>
			                               		</td>
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