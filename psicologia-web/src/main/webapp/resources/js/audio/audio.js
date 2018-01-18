var direta = false;
var esquerda = false;
var audioDireita = 'audioDireita';
var audioEsquerda = 'audioEsquerda';

function acaoAudio(acao, audio, playStop){
	var _audio = document.getElementById(audio);
	if(acao == "play" && playStop == "true"){//play
		_audio.play();
	}else{
		_audio.pause();
		_audio.currentTime = 0;
	}
}

function movimentacaoBall(x_pos, xBall, playStop){
	if(xBall > 0){
		if(x_pos > xBall && !direta){
	    	direta = true;
	    	esquerda = false;
	    	acaoAudio('pause', 'audioDireita', playStop);	    	
	    	acaoAudio('play', 'audioEsquerda', playStop);	    	
	    }else if(x_pos < xBall && !esquerda){
	    	esquerda = true;
	    	direta = false;
	    	acaoAudio('pause', 'audioEsquerda', playStop);	    	
	    	acaoAudio('play', 'audioDireita', playStop);	    	
	    }
	}
}