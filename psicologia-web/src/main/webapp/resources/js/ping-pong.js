function canvasCliente(velocidade, altura, largura){
	var animate = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || function (callback) {
	    window.setTimeout(callback, 1000 / 60)
	};	
	var canvas = document.getElementById("canvas");
	if(altura > 0){
		canvas.height = altura;
	}
	if(largura > 0){
		canvas.width = largura;
	}
	var width = canvas.width;
	var height = canvas.height;
	var x_speed = velocidade;
	var y_speed = 0;
	var radius = 20;
	var xBall = width / 2;
	var yBall = height / 2;
	//canvas.width = width;
	//canvas.height = height;
	var context = canvas.getContext('2d');
	var player = new Player();
	var computer = new Computer();
	var ball = new Ball(xBall, yBall);
	
	var keysDown = {};
	
	var render = function () {
	    context.fillStyle = "#000";
	    context.fillRect(0, 0, width, height);
	    player.render();
	    computer.render();
	    ball.render();
	};
	
	var update = function () {
	    player.update();
	    computer.update(ball);
	    ball.update(player.paddle, computer.paddle);
	};
	
	var step = function () {
	    update();
	    render();
	    animate(step);
	};
	
	function Paddle(x, y, width, height) {
	    this.x = x;
	    this.y = y;
	    this.width = width;
	    this.height = height;
	    this.x_speed = x_speed;
	    this.y_speed = y_speed;
	}
	
	Paddle.prototype.render = function () {
	    //context.fillStyle = "#000";
	    context.fillRect(this.x, this.y, this.width, this.height);
	};
	
	Paddle.prototype.move = function (x, y) {
	    this.x += x;
	    this.y += y;
	    this.x_speed = x;
	    this.y_speed = y;
	    if (this.x < 0) {    
	        this.x = 0;
	        this.x_speed = x_speed;
	    } else if (this.x + this.width > width) {
	        this.x = width - this.width;
	        this.x_speed = x_speed;
	    }
	};
	
	function Computer() {
	    this.paddle = new Paddle();
	}
	
	Computer.prototype.render = function () {
	    this.paddle.render();
	};
	
	Computer.prototype.update = function (ball) {
	    var x_pos = ball.x;
	    
	    movimentacaoBall(x_pos, xBall);
	    
	    var diff = -((this.paddle.x + (this.paddle.width / 2)) - x_pos);
	    if (diff < 0 && diff < -4) {
	        diff = -5;
	    } else if (diff > 0 && diff > 4) {
	        diff = 5;
	    }
	    this.paddle.move(diff, 0);    
	};
	
	function Player() {
	    this.paddle = new Paddle();
	}
	
	Player.prototype.render = function () {
	    this.paddle.render();
	};
	
	Player.prototype.update = function () {
	    this.paddle.move(0, 0);
	};
	
	function Ball(x, y) {
	    this.x = x;
	    this.y = y;
	    this.x_speed = x_speed;
	    this.y_speed = y_speed;
	}
	
	Ball.prototype.render = function () {
	    context.beginPath();
	    context.arc(this.x, this.y, radius, Math.PI * 2, false);
	    context.fillStyle = "#ff0000";
	    context.fill();
	};
	
	Ball.prototype.update = function () {		
	    this.x += this.x_speed;
	    this.y += this.y_speed;
	 		
	   if (this.x - 5 < 0) {   
	     this.x = 5;
	     this.x_speed = -this.x_speed;
	   } else if (this.x + 5 > width) {
	     this.x = width - 5;
	     this.x_speed = -this.x_speed;
	   }
	   
	  if (this.y - 5 < 0) {
	    this.y = 5;
	    this.y_speed = -this.y_speed;
	  } else if (this.y + 5 > height) {
	    this.y = height - 5;
	    this.y_speed = -this.y_speed;
	  }
	};

	if(velocidade > 0){
		document.getElementById("containerCanvas").replaceChild(canvas, canvas);
	}else{
		document.getElementById("containerCanvas").appendChild(canvas);
	}
	
	animate(step);
}