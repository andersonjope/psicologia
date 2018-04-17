<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head> 
</head>
<body>
	<div id="frame" style="width:100%; height:100%;"></div>
	<input type="hidden" id="origem" value="pac_psi">
	<script type="text/javascript">
		urlWebSocket("${hashSessao}");
	</script>	
	
	<div class="form-group" id="buttonPaciente">
		<div class="col-sm-offset-2 col-sm-10">
			<img id="online" src="resources/imagens/status_away.png" style="display: none;" title="Online" align="middle" height="32" width="32" >
			<img id="offline" src="resources/imagens/status_busy.png" style="display: block;" title="Offline" align="middle" height="32" width="32" >
			
		    <input id="endVideoPaciente" style="display: none;" type="button" class="btn btn-success" value="Encerrar Vídeo"/>
    	</div>
    </div>
	<div id="videos" class="form-group">
		<div class="col-sm-10">
	    	<video id="remoteVideo" autoplay ></video>
	    	<video id="localVideo" autoplay muted></video>
	    </div>
    </div>
</body>
    	
</html>