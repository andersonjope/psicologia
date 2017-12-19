<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="resources/css/bootstrap/dataTables.bootstrap4.css">
    <link rel="stylesheet" href="resources/css/sb-admin.min.css">
    
	<script type="text/javascript">
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
	
    <script type="text/javascript" src="resources/js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/bootstrap/js/bootstrap.bundle.min.js"></script>
	
	<script type="text/javascript" src="resources/js/jquery-easing/jquery.easing.min.js"></script>
	
	<script type="text/javascript" src="resources/js/sb-admin.min.js"></script>
	<script type="text/javascript" src="resources/js/sb-admin-charts.min.js"></script>
	<script type="text/javascript" src="resources/js/sb-admin-datatables.min.js"></script>
</body>
</html>