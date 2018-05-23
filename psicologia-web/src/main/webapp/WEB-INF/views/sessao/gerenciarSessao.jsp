<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<input type="hidden" id="origem" value="psi_pac">
	<input type="hidden" id="nuSessao" value="${nuSessao}">
	<input type="hidden" id="nuUsuario" value="${nuUsuario}">
	<div class="col-sm-offset-2 col-sm-12" style="float: left;">
		<div id="controles">
			<c:import url="/WEB-INF/views/sessao/controlesSessao.jsp"></c:import>
		</div>

	</div>		
	<div class="col-sm-offset-2 col-sm-12" style="float: left;">
	    <div class="form-group">
			<div class="col-sm-offset-2 col-sm-12">
				<img id="online" src="resources/imagens/status_away.png" style="display: none;" title="Online" align="middle" height="32" width="32" >
				<img id="offline" src="resources/imagens/status_busy.png" style="display: block;" title="Offline" align="middle" height="32" width="32" >
				
			    <input id="initVideoCliente" style="display: none;" type="button" class="btn btn-success" value="Iniciar Chamada Vídeo"/>
			    <input id="endVideoCliente" style="display: none;" type="button" class="btn btn-success" value="Encerrar Chamda Vídeo"/>
	    	</div>
	    </div>
		<div id="videos" class="form-group">
			<div class="col-sm-8">
		    	<video id="remoteVideo" autoplay></video>
		    	<video id="localVideo" autoplay muted></video>
		    </div>
		    <div class="col-sm-4" style="float: left;">
				<div id="accordion">
					<div class="card">
						<div id="collapseOne" class="collapse show" data-parent="#accordion">
							<div class="card-body">
							
								<div class="chat-panel panel panel-default">
			                        <div id="scroltop" class="panel-body">
			                            <div id="htmlMensagem"></div>
			                        </div>
			                        <div class="panel-footer">
			                            <div class="input-group">
			                                <input style="display: none;" id="inputMensagem" type="text" maxlength="250" class="form-control input-sm" placeholder="Digite a mensagem aqui..." />
			                                <span class="input-group-btn">
			                                    <button style="display: none;" id="btEnviarMensagem" class="btn btn-warning" id="btn-chat">ENVIAR</button>
			                                </span>
			                            </div>
			                        </div>
			                    </div>
							</div>
						</div>
					</div>
				</div>
			</div>
	    </div>
	    
		<div class="form-group">
			<div class="col-sm-12" style="float: left">
				<button id="buttonAccordion" class="buttonAccordion">Visual Scan</button>
				<div class="painelVideoBolinha">
					<c:import url="/WEB-INF/views/canvas/canvas.jsp"></c:import>
				</div>
			</div>
		</div>
	</div>
	
<body>	
	<script type="text/javascript">
		urlWebSocket("${hashSessao}");
		
		$(document).ready(function() {
			$("#showBotaoVoltar").removeAttr("style");
			$("#btEnviarMensagem").click(function() {
				enviarMensagem($("#nuSessao").val(), $("#nuUsuario").val());
			});
		});
	</script>
	
</html>