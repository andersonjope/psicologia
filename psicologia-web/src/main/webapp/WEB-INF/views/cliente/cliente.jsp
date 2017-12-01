<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="resources/js/ping-pong.js"></script>
    <script type="text/javascript" src="resources/js/websocket/websocket.js"></script>
    <script type="text/javascript">
		$(document).ready(function() {
			$("#canvas").width($(".content").width());
			$("#canvas").height($(".content").height());
		});
	</script>
</head>
<body>
	<h1>Sala do Cliente  - ${idCliente}</h1>

	<canvas id="canvas"></canvas>
	
</body>
	<script type="text/javascript">
		initWebsocket("${idCliente}");
		canvasCliente(0);	
	</script>	
</html>