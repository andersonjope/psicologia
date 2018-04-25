function mensagePingPong(message) {
	var velocidade = message.velocidade;
	var playStop = message.playStop;
	if (velocidade > 0) {
		loadIframe(velocidade, playStop);
	}else{
		if($("#iniciar").length > 0){
			$("#iniciar").css("display", "block");
		}
		loadIframe(0,false);				
	}
}
               
function loadIframe(velocidade, playStop){
	var iFrame = "<iframe width='100%' height='100%' frameborder='0' scrolling='no' id='iframeCanvas' src='http://\\" + serverName + ':' + serverPort + contextPath + "/iframeCanvas?time=" + new Date() + "&velocidade=" + velocidade + "&playStop=" + playStop + "\'> </iframe>";
	$("#frame").html(iFrame);
}
