var requestId;
var requestAnimate = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || function(callback) {
			window.setTimeout(callback, 1000 / 60)
		};

var canvas = document.getElementById("canvas");
var context = canvas.getContext("2d");	

//create te container that will hold the boincing balls.
var container = {
	x : 0,
	y : 0,
	width : canvas.width,
	height : canvas.height
};
//create the array of circles that will be animated
var ball_x;
var ball_y;
var ball_r;
var ball_vx;
var ball_vy;

function ball(_ball_x, _ball_y, _ball_r, _ball_vx, _ball_vy){
	ball_x = _ball_x;
	ball_y = _ball_y;
	ball_r = _ball_r;
	ball_vx = _ball_vx;
	ball_vy = _ball_vy;
}

function animate(velocidade) {
//	context.save();
	//draw the container
	context.fillStyle = "#000000";
	context.fillRect(container.x, container.y, container.width, container.height);

	//draw the circles
	context.fillStyle = '#006400';
	context.beginPath();
	context.arc(ball_x, ball_y, ball_r, Math.PI * 2, false);
	context.fill();

	//time to animate our circles ladies and gentlemen.
	if (ball_x - ball_r + ball_vx < container.x || ball_x + ball_r + ball_vx > container.x + container.width) {
		ball_vx = -ball_vx;
	}

	if (ball_y + ball_r + ball_vy > container.y + container.height || ball_y - ball_r + ball_vy < container.y) {
		ball_vy = -ball_vy;
	}
//	console.log("ball_vx: " + ball_vx + " : ball_vy: " + ball_vy + " : ball_x: " + ball_x + " : ball_y: " + ball_y);

	ball_x += ball_vx
	ball_y += ball_vy
	
	movimentacaoBall(ball_x, container.width);
	
	if(requestId){
		cancelAnimationFrame(requestId);
	}
	if (velocidade > 0) {
		requestId = requestAnimate(animate);		
	}
}

function canvasCliente(_velocidade, _playStop){
	var _ball_x = (canvas.width / 2);
	var _ball_y = (canvas.height / 2);
	var _ball_r = 20;
	var _ball_vx = 0;
	var _ball_vy = 0;
	
	playStop(_playStop);
	ball(_ball_x, _ball_y, _ball_r, _ball_vx, _ball_vy);
	
	var velocidade = parseInt(_velocidade);
	ball_vx = parseInt(velocidade);
	animate(velocidade);
}
