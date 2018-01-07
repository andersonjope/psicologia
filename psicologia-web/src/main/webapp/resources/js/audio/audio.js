var direta = false;
var esquerda = false;

function acaoAudio(acao, audio){
	var _audio = document.getElementById(audio);
	if(acao == "play"){
		_audio.play();
	}else{
		_audio.pause();
		_audio.currentTime = 0;
	}
}

function movimentacaoBall(x_pos, xBall){
	if(x_pos > xBall && !direta){
    	direta = true;
    	esquerda = false;
    	acaoAudio('play', 'audioDireita');	    	
    	acaoAudio('pause', 'audioEsquerda');	    	
    }else if(x_pos < xBall && !esquerda){
    	esquerda = true;
    	direta = false;
    	acaoAudio('play', 'audioEsquerda');	    	
    	acaoAudio('pause', 'audioDireita');	    	
    }
}