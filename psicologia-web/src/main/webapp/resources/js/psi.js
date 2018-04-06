var wsPingPong = "";
var wsWebRtc = "";
var context = "/psicologia-web";

function urlWebSocket(hash){
	wsPingPong = "ws://" + document.location.host + context + "/pingpong/" + hash;
	wsWebRtc =  "ws://" + document.location.host + context + "/webtrc/" + hash;
}