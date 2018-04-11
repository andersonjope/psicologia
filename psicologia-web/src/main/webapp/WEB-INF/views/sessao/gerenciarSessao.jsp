<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function() {
		$("#showBotaoVoltar").removeAttr("style");
	});
</script>
</head>
<body>
	<input type="hidden" id="origen" value="psi_pac">
	<div class="col-sm-offset-2 col-sm-12" style="float: left;">
		<div id="controles" >
			<c:import url="/WEB-INF/views/sessao/controlesSessao.jsp"></c:import>
		</div>
	
	    <div class="form-group">
			<div class="col-sm-offset-2 col-sm-12">
			    <input id="initVideoCliente" type="button" class="btn btn-success" value="Iniciar Chamada Vídeo"/>
			    <input id="endVideoCliente" style="display: none;" type="button" class="btn btn-success" value="Encerrar Chamda Vídeo"/>
	    	</div>
	    </div>
		<div id="videos" class="form-group">
			<div class="col-sm-12">
		    	<video id="remoteVideo" autoplay></video>
		    	<video id="localVideo" autoplay muted></video>
		    </div>
	    </div>
	
		<div class="form-group">
			<div class="col-sm-12">
				<button id="buttonAccordion" class="buttonAccordion"></button>
				<div class="painelVideoBolinha">
					<div id="frame" style="width:100%; height:100%;"></div>
				</div>
			</div>
		</div>
	</div>
	
<body>	
	<script type="text/javascript">
		urlWebSocket("${hashSessao}");
	</script>
	
</html>