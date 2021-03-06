<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>

<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="resources/css/styles/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/psi.css">
    <link rel="stylesheet" href="resources/css/sb-admin-2.min.css">
    
    <link rel="icon" href="resources/imagens/logo.png">
    
    <script type="text/javascript">
		var serverName = "${pageContext.request.serverName}";
		var serverPort = "${pageContext.request.serverPort}";
		var contextPath = "${pageContext.request.contextPath}";
	</script>
    
    <tilesx:useAttribute id="scriptList" name="scripts" classname="java.util.List" />
    <c:if test="${not empty scriptList}">
		<c:forEach var="item" items="${scriptList}">
		  <script type="text/javascript" src="${item}"></script>
		</c:forEach>
    </c:if>
    
    
	<style type="text/css">
		.error{
			color: red;
			font-weight: bold;
		}
		#template, .header, .menu-content, .footer{
			width: 100%;
			float: left;
		}
		.header {
			display: block;
		}
		.menu-content{
			padding: 10px;
		}
		.menu-content .menu {
			width: 15%;
			float: left;
		}		
		.menu-content .content {
			width: 85%;
			float: left;
		}
		.footer {
		   margin-top: 2.4em;
		   margin-bottom: 2.4em;
		   clear: both;
		}
	</style>
<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body id="page-top">
	<noscript><h2>Enable Java script</h2></noscript>
	
	<div id="template">
		<div class="header">
			<tiles:insertAttribute name="header"/>			
		</div>
		<div class="content-wrapper">
			<div class="content">
				<tiles:insertAttribute name="pageMessages" />
				<tiles:insertAttribute name="content"/>	
			</div>
		</div>
		<div class="footer">
			<tiles:insertAttribute name="footer"/>
		</div>
	</div>
	
	<tilesx:useAttribute id="addScriptList" name="addScripts" classname="java.util.List" />
    <c:if test="${not empty addScriptList}">
		<c:forEach var="item" items="${addScriptList}">
		    <c:if test="${item ne ''}">
				<script type="text/javascript" src="${item}"></script>
			</c:if>
		</c:forEach>
    </c:if>
    
    <tilesx:useAttribute id="addAudiosList" name="addAudios" classname="java.util.List" />
	<c:if test="${not empty addAudiosList}">
		<c:forEach var="item" items="${addAudiosList}" varStatus="vs">
			<c:if test="${item ne ''}">
				<audio id="${item}" preload="auto" loop src="resources/js/audio/${item}.mp3"> </audio>
			</c:if>
		</c:forEach>
	</c:if>
	
</body>
</html>