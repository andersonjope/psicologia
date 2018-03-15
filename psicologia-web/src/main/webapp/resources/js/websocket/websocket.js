'use strict';

$(document).ready(function() {
	console.log("websocket " + " : " + wsPingPong);
	var websocket = new WebSocket(wsPingPong);
	
	websocket.onmessage = function(evt) { onMessage(evt) };
	websocket.onerror = function(evt) { onError(evt) };
	websocket.onopen = function(evt) { onOpen(evt) };
});

function onMessage(evt) {
	if(evt.data !== "" && evt.data !== null){
		try {
			var obj = jQuery.parseJSON(evt.data);
			var velocidade = obj.velocidade;
			var playStop = obj.playStop;
			if (velocidade > 0) {
				loadIframe(velocidade, playStop);
			}else{
				loadIframe(0,false);				
			}
		} catch (e) {
			loadIframe(0,false);
		}
	}
}

function onError(evt) {
	alert('Erro ao conetar ao servidor: ' + evt.data);
	window.close();
}

function onOpen(evt) {
//    alert("Erro ao conetar ao servidor. open");
}
               
function loadIframe(velocidade, playStop){
	var iFrame = "<iframe width='100%' height='100%' frameborder='0' scrolling='no' id='iframeCanvas' src='http://\\" + serverName + ':' + serverPort + contextPath + "/iframeCanvas?time=" + new Date() + "&velocidade=" + velocidade + "&playStop=" + playStop + "\'> </iframe>";
	$("#frame").html(iFrame);
}
