'use strict';

var wsPingPong = "";
var wsWebRtc = "";
var urlWS = "";
var ws = null;
var context = "/psicologia-web";
var origem = null;

$(document).ready(function() {
	origem = $("#origem").val();
	$(".hideMessage").delay(3000).hide(100);
	
	$(".maskcpf").inputmask("999.999.999-99", { "placeholder": "000.000.000-00" });
	$(".masktelefone").inputmask("(99)[9]9999-9999", { "placeholder": "(xx)xxxxx-xxxx" });
	$(".maskdata").inputmask("99/99/99", { "placeholder": "dd/mm/aa" });
	$(".maskcep").inputmask("99.999-999", { "placeholder": "00.000-000" });
	
	var acc = document.getElementsByClassName("buttonAccordion");

	for (var i = 0; i < acc.length; i++) {
		acc[i].addEventListener("click", function() {
			this.classList.toggle("active");

			var panel = this.nextElementSibling;
			if (panel.style.maxHeight) {
				panel.style.maxHeight = null;
			} else {
				panel.style.maxHeight = panel.scrollHeight + "px";
			}

		});
	}
});

function urlWebSocket(hash){
	wsPingPong = "ws://" + document.location.host + context + "/pingpong/" + hash;
	wsWebRtc =  "ws://" + document.location.host + context + "/webtrc/" + hash;
	
	urlWS =  "ws://" + document.location.host + context + "/ws/" + hash;
	
	ws = new WebSocket(urlWS);
	
	ws.onopen = function() { 
		if(ws.readyState === 1){
			ws.send(JSON.stringify({
				"operacao" : "connection",
				"user" : $("#origem").val()
			}));
		}
	};
	
	ws.onmessage = function(evt) { onMessage(evt) };
	ws.onerror = function(evt) { onError(evt) };
	ws.onclose = function(evt) { onClose(evt) };
}

function onMessage(evt){
	var message = JSON.parse(evt.data);
	if(message.operacao === "connection"){
		validaSituacaoUsuario(message);
		loadIframe(0,false);
	} else if(message.operacao === "pingpong"){
		mensagePingPong(message);
	} else if(message.operacao === "video"){
		messageVideo(message);
	}else{
		console.log("onMessage else.............");
		$("#online").css("display", "none");
		$("#offline").css("display", "block");
		if($("#initVideoCliente").length > 0){
			$("#initVideoCliente").css("display", "none");
			$("#iniciar").css("display", "none");
			$("#endVideoCliente").css("display", "none");
		}
		if($("#endVideoPaciente").length > 0){
			$("#endVideoPaciente").css("display", "none");
		}
		loadIframe(0, false);
	}
}

function onError(evt){
	console.log(evt);
}

function onClose(evt){
	console.log("onClose: " + evt.data);
}

function validaSituacaoUsuario(message){
	var users = message.users;
	$.each(users, function(key, value) {
		if(key != $("#origem").val()){
			if(value === "true"){
				$("#online").css("display", "block");
				$("#offline").css("display", "none");
				if($("#initVideoCliente").length > 0){
					$("#initVideoCliente").css("display", "block");
					$("#iniciar").css("display", "block");
				}
			}else{
				$("#online").css("display", "none");
				$("#offline").css("display", "block");						
			}
		}				
	});
}
