function initWebsocket(identificador){
	var wsUri = "ws://" + document.location.host + "/psicologia-web/pingpong/" + identificador;
	var websocket = new WebSocket(wsUri);

	websocket.onmessage = function(evt) { onMessage(evt) };
	websocket.onerror = function(evt) { onError(evt) };
	websocket.onopen = function(evt) { onOpen(evt) };
}

function onMessage(evt) {
	if(evt.data !== "" && evt.data !== null){
		try {
			var obj = jQuery.parseJSON(evt.data);
			var velocidade = obj.velocidade;
			var altura = obj.altura;
			var largura = obj.largura;
			if (velocidade > 1) {
				loadIframe(velocidade, altura, largura);
			}
		} catch (e) {
			loadIframe(0,0,0);
		}
	}
}

function onError(evt) {
	alert('Erro ao conetar ao servidor: ' + evt.data);
	window.close();
}

function onOpen(evt) {
    //alert("Erro ao conetar ao servidor. open");
}
               
function loadIframe(velocidade, altura, largura){
	var iFrame = "<iframe width='100%' height='100%' frameborder='0' scrolling='no' id='iframeCanvas' src='http://\\" + serverName + ':' + serverPort + contextPath + "/iframeCanvas?time=" + new Date() + "&velocidade=" + velocidade + "&altura=" + altura + "&largura=" + largura + "\'> </iframe>";
	$("#frame").html(iFrame);
}
