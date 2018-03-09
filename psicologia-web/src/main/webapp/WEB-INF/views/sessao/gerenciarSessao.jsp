<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<!-- <script type="text/javascript" src="http://cdn.peerjs.com/0.3/peer.js"></script> -->

<script type="text/javascript">
	$(document).ready(function() {
		$("#showBotaoVoltar").removeAttr("style");
	});
	
</script>

</head>
<body>
	<div id="frame" style="width:100%; height:100%;"></div>

	<div id="controles" >
		<c:import url="/WEB-INF/views/sessao/controlesSessao.jsp"></c:import>
	</div>
	
<!-- 	<video id="localVideo" autoplay muted></video> -->
<!--     <video id="remoteVideo" autoplay></video> -->

<!--     <br /> -->

<!--      <div> -->
<!--       <button id="startButton">Start</button> -->
<!--       <button id="callButton">Call</button> -->
<!--       <button id="hangupButton">Hang Up</button> -->
<!--     </div> -->

<!--     <script type="text/javascript"> -->
<!--       pageReady(); -->
<!--     </script> -->
	
<body>	
	<script type="text/javascript">
		initWebsocket("${idCliente}");
	</script>
	
<!-- 	<script type="text/javascript" src="https://webrtc.github.io/adapter/adapter-latest.js"></script> -->
<!-- 	<script type="text/javascript" src="resources/js/webrtc/webtrc.js"></script> -->
	
</html>