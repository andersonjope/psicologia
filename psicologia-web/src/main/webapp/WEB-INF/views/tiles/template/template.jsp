<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel='stylesheet' href='webjars/bootstrap/4.0.0-alpha.6-1/css/bootstrap.min.css'>
    
    <script type="text/javascript" src="webjars/jquery/3.2.1/jquery.min.js"></script>
	<script type="text/javascript" src="webjars/bootstrap/4.0.0-alpha.6-1/js/bootstrap.min.js"></script>
    
	<style type="text/css">
		#template, .header, .menu-content, .footer{
			width: 100%;
			float: left;
		}
		.header {
			
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
<body>
	<noscript><h2>Enable Java script</h2></noscript>	
	<div id="template">
		<div class="header">
			<tiles:insertAttribute name="header"/>			
		</div>
		<div class="menu-content">
			<div class="menu">
				<tiles:insertAttribute name="menu"/>		
			</div>
			<div class="content">
				<tiles:insertAttribute name="content"/>	
			</div>
		</div>
		<div class="footer">
			<tiles:insertAttribute name="footer"/>
		</div>
	</div>

</body>
</html>