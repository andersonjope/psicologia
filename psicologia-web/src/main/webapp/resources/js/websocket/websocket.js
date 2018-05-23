function mensagePingPong(message) {
	var velocidade = message.velocidade;
	var playStop = message.playStop;
	if (velocidade > 0) {
		canvasCliente(velocidade, playStop);
	}else{
		if($("#iniciar").length > 0){
			$("#iniciar").css("display", "block");
		}
		canvasCliente(0, false);
	}
}
