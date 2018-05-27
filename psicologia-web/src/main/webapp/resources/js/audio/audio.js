var direta = false;
var esquerda = false;
var audioDireita = 'audioDireita';
var audioEsquerda = 'audioEsquerda';
var _playStop = false;
var audioDerection;

function playStop(playStop){
	_playStop = playStop;
}

function audioInit(valor){
	direta = valor;
	esquerda = valor;
}

function acaoAudio(acao, audio){
	var _audio = document.getElementById(audio);
	if(acao == "play" && _playStop == "true"){
		_audio.play();
	}else{
		_audio.pause();
		_audio.currentTime = 0;
	}
}

function movimentacaoBall(x_pos, width){
	if(_playStop == "true" && width > 0){
		if(x_pos >= width && !direta){
	    	direta = true;
	    	esquerda = false;
	    	audioDerection = "direita";
	    }else if(x_pos <= width && !esquerda){
	    	esquerda = true;
	    	direta = false;
	    	audioDerection = "esquerda";
	    }
	}
}

function validaExecucaoAudio(){
	if(_playStop == "true"){
		if(audioDerection == "direita"){
			console.log("direita");
	    	acaoAudio('play', 'audioDireita');	    	
	    	acaoAudio('pause', 'audioEsquerda');	    	
		}else if(audioDerection == "esquerda"){
			console.log("esquerda");
	    	acaoAudio('play', 'audioEsquerda');	    	
	    	acaoAudio('pause', 'audioDireita');	    	
		}		
	}else{
		audioDerection = "";
		acaoAudio('pause', 'audioEsquerda');	    	
		acaoAudio('pause', 'audioDireita');	 
	}
}

setInterval(validaExecucaoAudio,500);