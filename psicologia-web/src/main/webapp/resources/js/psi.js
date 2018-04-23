'use strict';

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
}

function onMessage(evt){
	var message = JSON.parse(evt.data);
	if(message.operacao === "connection" || message.operacao === "close"){
		console.log("onMessage connection---------");
		validaSituacaoUsuario(message);
		loadIframe(0,false);
	} else if(message.operacao === "pingpong"){
		mensagePingPong(message);
	} else if(message.operacao === "video"){
		console.log("onMessage video++++++++++++");
		messageVideo(message);
	} else if(message.operacao === "error"){
		console.log("onMessage error++++++++++++");
		validaSituacaoUsuario(message);
	} else if(message.operacao === "mensagem"){
		carregaMensagens(message.nuSessao);
	}
}

function validaSituacaoUsuario(message){
	var users = message.users;
	$.each(users, function(key, value) {
		if(key != $("#origem").val()){
			if(value === "true"){
				$("#online").css("display", "block");
				$("#offline").css("display", "none");
				$("#inputMensagem").css("display", "block");
				$("#btEnviarMensagem").css("display", "block");
				if($("#initVideoCliente").length > 0){
					$("#initVideoCliente").css("display", "block");
					$("#iniciar").css("display", "block");
				}
			}else{
				$("#online").css("display", "none");
				$("#offline").css("display", "block");
				$("#inputMensagem").css("display", "none");
				$("#btEnviarMensagem").css("display", "none");
				if($("#initVideoCliente").length > 0){
					$("#initVideoCliente").css("display", "none");
					$("#iniciar").css("display", "none");
				}
				if($("#endVideoPaciente").length > 0){
					$("#endVideoPaciente").css("display", "none");
				}
			}
		}				
	});
}

function carregaMensagens(nuSessao){
	$.ajax({
        url: "http://" + document.location.host + context + "/loadMensagemSessao",
        data: "nuSessao=" + nuSessao,
        type: "GET",
        success: function(data) {
        	var li = "";
        	$.each(data, function(key, value) {
	        	if(value.tipoUsuario === "psi"){
	        		li += "<li class='left clearfix'>" +
                        "<div class='chat-body clearfix'>" +
                            "<p>" + 
                            	value.deMensagem +
                            "</p>" +
                        "</div>" +
                    "</li>"
	        	}else if(value.tipoUsuario === "pac"){
	        		li += "<li class='right clearfix'>" +
                        "<div class='chat-body clearfix'>" +
                            "<p style='text-align: right;'>" + 
                            	value.deMensagem +
                            "</p>" +
                        "</div>" +
                    "</li>"
	        	}
        	});
        	$("#htmlMensagem").html("<ul class='chat'>" + li + "</ul>");
        	$("#scroltop").animate({ scrollTop: $("#scroltop")[0].scrollHeight}, 1000);
        }
    });
}

function enviarMensagem(nuSessao, nuUsuario){
	var inputMensagem = $("#inputMensagem").val();
	if(inputMensagem != ""){
		$.ajax({
			url: "http://" + document.location.host + context + "/incluirMensagemSessao",
			data: "nuSessao=" + nuSessao + "&nuUsuario=" + nuUsuario + "&mensagem=" +$("#inputMensagem").val(),
			type: "POST",
			success: function(data) {
				$("#inputMensagem").val("");
				if(data == "OK"){
					ws.send(JSON.stringify({
						"operacao" : "mensagem",
						"nuSessao" : nuSessao
					}));
				}
			}
		});		
	}
}