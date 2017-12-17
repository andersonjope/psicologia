function initWebsocket(identificador){
	var wsUri = "ws://" + document.location.host + "/psicologia-web/pingpong/" + identificador;
	var websocket = new WebSocket(wsUri);

	websocket.onmessage = function(evt) { onMessage(evt) };
	websocket.onerror = function(evt) { onError(evt) };
	websocket.onopen = function(evt) { onOpen(evt) };
}

function onMessage(evt) {
	if(evt.data != ""){
		var obj = jQuery.parseJSON(evt.data);
		
		var velocidade = obj.velocidade;
		var altura = obj.altura;
		var largura = obj.largura;
		if (velocidade > 1) {
			canvasCliente(velocidade, altura, largura);
		}
	}
}

function onError(evt) {
	alert('Erro ao conetar ao servidor: ' + evt.data);
}

function onOpen(evt) {
    //alert("Erro ao conetar ao servidor. open");
}
                