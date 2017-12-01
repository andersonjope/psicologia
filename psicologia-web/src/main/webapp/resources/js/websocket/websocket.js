function initWebsocket(identificador){
	var wsUri = "ws://" + document.location.host + "/psicologia-web/cinemaSocket/" + identificador;
	var websocket = new WebSocket(wsUri);

	websocket.onmessage = function(evt) { onMessage(evt) };
	websocket.onerror = function(evt) { onError(evt) };
	websocket.onopen = function(evt) { onOpen(evt) };
}

function onMessage(evt) {
	if(evt.data != ""){
		var obj = jQuery.parseJSON(evt.data);
		
		var velocidade = obj.velocidade;
		if (velocidade > 1) {
			canvasCliente(velocidade);
		}
	}
}

function onError(evt) {
	alert('Erro ao conetar ao servidor: ' + evt.data);
}

function onOpen(evt) {
    alert("Erro ao conetar ao servidor.");
}

function sendText(json) {
    console.log("sending text: " + json);
    websocket.send(json);
}
                