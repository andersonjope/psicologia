<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>

<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="resources/css/bootstrap/dataTables.bootstrap4.css">
    <link rel="stylesheet" href="resources/css/sb-admin.min.css">
    
    <tilesx:useAttribute id="scriptList" name="scripts" classname="java.util.List" />
    <c:if test="${not empty scriptList}">
		<c:forEach var="item" items="${scriptList}">
		  <script type="text/javascript" src="${item}"></script>
		</c:forEach>
    </c:if>
    
    <tilesx:useAttribute id="addScriptList" name="addScripts" classname="java.util.List" />
    <c:if test="${not empty addScriptList}">
		<c:forEach var="item" items="${addScriptList}">
		    <c:if test="${item ne ''}">
				<script type="text/javascript" src="${item}"></script>
			</c:if>
		</c:forEach>
    </c:if>
    
	<script type="text/javascript">
		var serverName = "${pageContext.request.serverName}";
		var serverPort = "${pageContext.request.serverPort}";
		var contextPath = "${pageContext.request.contextPath}";
	</script>
    
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
			display: none;
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
<body class="fixed-nav sticky-footer bg-dark" id="page-top">
	<noscript><h2>Enable Java script</h2></noscript>
	
	<div id="template">
		<div class="header">
			<tiles:insertAttribute name="header"/>			
		</div>
		<div class="menu-content content-wrapper">
			<div class="menu">
				<tiles:insertAttribute name="menu"/>		
			</div>
			<div class="content container-fluid">
				<tiles:insertAttribute name="pageMessages" />
				<tiles:insertAttribute name="content"/>	
			</div>
		</div>
		<div class="footer">
			<tiles:insertAttribute name="footer"/>
		</div>
	</div>
	
</body>
</html>