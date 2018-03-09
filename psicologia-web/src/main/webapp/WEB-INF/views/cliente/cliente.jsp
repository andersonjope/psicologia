<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<style type="text/css">
		.content-wrapper{
			margin-left: 0px;
		}
	</style>
</head>
<body>
	<div id="frame" style="width:100%; height:100%;"></div>
</body>
	<script type="text/javascript">
		initWebsocket("${idCliente}");
	</script>	
	
</html>