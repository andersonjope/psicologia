var requestAnimate = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || function(callback) {
			window.setTimeout(callback, 1000 / 60)
		};

var canvas = document.getElementById("canvas");
var c = canvas.getContext("2d");

//create te container that will hold the boincing balls.
var container = {
	x : 0,
	y : 0,
	width : canvas.width,
	height : canvas.height
};
//create the array of circles that will be animated
var circles = [ {
	x : (canvas.width / 2),
	y : (canvas.height / 2),
	r : 20,
	vx : 0,
	vy : 0
} ];

function animate() {
//	c.save();
	//draw the container
	c.fillStyle = "#000000";
	c.fillRect(container.x, container.y, container.width, container.height);

	//loop throughj the circles array
	for (var i = 0; i < circles.length; i++) {
		//draw the circles
		c.fillStyle = '#006400';
		c.beginPath();
		c.arc(circles[i].x, circles[i].y, circles[i].r, Math.PI * 2, false);
		c.fill()

		//time to animate our circles ladies and gentlemen.
		if (circles[i].x - circles[i].r + circles[i].vx < container.x || circles[i].x + circles[i].r + circles[i].vx > container.x + container.width) {
			circles[i].vx = -circles[i].vx;
		}

		if (circles[i].y + circles[i].r + circles[i].vy > container.y + container.height || circles[i].y - circles[i].r + circles[i].vy < container.y) {
			circles[i].vy = -circles[i].vy;
		}

		circles[i].x += circles[i].vx
		circles[i].y += circles[i].vy
	}
//	c.restore();
	requestAnimate(animate);
}
requestAnimate(animate);

function canvasCliente(velocidade, playStop){
	console.log(circles);
	circles.vx = parseInt(velocidade);
//	c.clearRect(0, 0, container.width, container.height);
	animate();
	requestAnimate(animate);
}