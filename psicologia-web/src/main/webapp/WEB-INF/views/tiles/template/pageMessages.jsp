<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<c:forEach items="${messages}" var="currentMessage">	
	<c:if test="${currentMessage.messageType.descricao eq 'ERROR'}">
		<div class="bg-danger">
			<h4 class="block">Erro! <c:out value="${currentMessage.message}"/></h4>
		</div>
	</c:if>
	<c:if test="${currentMessage.messageType.descricao eq 'SUCCESS'}">
		<div class="bg-success">
			<h4 class="block">Successo! <c:out value="${currentMessage.message}"/></h4>
		</div>
	</c:if>
	<c:if test="${currentMessage.messageType.descricao eq 'WARNING'}">
		<div class="bg-warning">
			<h4 class="block">Alerta! <c:out value="${currentMessage.message}"/></h4>
		</div>
	</c:if>
	<c:if test="${currentMessage.messageType.descricao eq 'INFO'}">
		<div class="bg-primary">
			<h4 class="block">Informação! <c:out value="${currentMessage.message}"/></h4>
		</div>
	</c:if>
</c:forEach>