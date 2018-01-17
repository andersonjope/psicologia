var direta = false;
var esquerda = false;
var audioDireita = 'audioDireita';
var audioEsquerda = 'audioEsquerda';

function acaoAudio(acao, audio){
	var _audio = document.getElementById(audio);
	if(acao == "play"){//play
		_audio.play();
	}else{
		_audio.pause();
		_audio.currentTime = 0;
	}
}

function movimentacaoBall(x_pos, xBall){
	if(xBall > 0){
		if(x_pos > xBall && !direta){
	    	direta = true;
	    	esquerda = false;
	    	acaoAudio('pause', 'audioDireita');	    	
	    	acaoAudio('play', 'audioEsquerda');	    	
	    }else if(x_pos < xBall && !esquerda){
	    	esquerda = true;
	    	direta = false;
	    	acaoAudio('pause', 'audioEsquerda');	    	
	    	acaoAudio('play', 'audioDireita');	    	
	    }
	}
}