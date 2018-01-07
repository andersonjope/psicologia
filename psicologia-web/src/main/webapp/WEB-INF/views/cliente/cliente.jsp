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
	<h1>Sala do Cliente  - ${idCliente}</h1>
	<div id="containerCanvas">
		<canvas id="canvas" height="400" width="600"></canvas>
	</div>
	
</body>
	<script type="text/javascript">
		initWebsocket("${idCliente}");
		canvasCliente(0,0,0);	
	</script>	
	
</html>