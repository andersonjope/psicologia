<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<style type="text/css">
	video{
		height: 350px; 
		width: 550px;
		float: left;
	}
</style>
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

    <div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
		    <input id="videoCallButton" type="button" class="btn btn-success" value="Video Call"/>
		    <input id="endCallButton" type="button" class="btn btn-success" value="End Call"/>
    	</div>
    </div>
	<div class="form-group">
		<div class="col-sm-10">
	    	<video id="remoteVideo" autoplay></video>
	    	<video id="localVideo" autoplay muted></video>
	    </div>
    </div>

	
<body>	
	<script type="text/javascript">
		urlWebSocket("${hashSessao}");
	</script>
	
<!-- 	<script type="text/javascript" src="https://webrtc.github.io/adapter/adapter-latest.js"></script> -->
	<script type="text/javascript" src="resources/js/webrtc/webtrc3.js"></script>
<!-- 		<script type="text/javascript" src="http://cdn.peerjs.com/0.3/peer.min.js"></script> -->
<!-- 		<script type="text/javascript" src="resources/js/webrtc/peer.js"></script> -->
	
    <script type="text/javascript">
    	pageReady("L");
	</script>
</html>