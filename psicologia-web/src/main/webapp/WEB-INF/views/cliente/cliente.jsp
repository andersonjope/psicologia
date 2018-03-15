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
		video{
			height: 350px; 
			width: 550px;
			float: left;
		}
	</style>
</head>
<body>
	<div id="frame" style="width:100%; height:100%;"></div>
</body>
	<script type="text/javascript">
		urlWebSocket("${hashSessao}");
		$(document).ready(function() {
			$("#showBotaoVoltar").removeAttr("style");
		});
	</script>	
	
	<div class="form-group">
		<div class="col-sm-10">
			<video id="remoteVideo" autoplay></video>
			<video id="localVideo" muted autoplay></video>
    	</div>
    </div>
    	
<!-- 	<script type="text/javascript" src="https://webrtc.github.io/adapter/adapter-latest.js"></script> -->
	<script type="text/javascript" src="resources/js/webrtc/webtrc3.js"></script>
<!-- 		<script type="text/javascript" src="http://cdn.peerjs.com/0.3/peer.min.js"></script> -->
<!-- 		<script type="text/javascript" src="resources/js/webrtc/peer.js"></script> -->
	
    <script type="text/javascript">
    	pageReady("R");
	</script>
	
</html>