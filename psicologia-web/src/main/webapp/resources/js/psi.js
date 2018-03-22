var wsPingPong = "";
var wsWebRtcPsiPac = "";
var wsWebRtcPacPsi = "";

function urlWebSocket(hash){
	console.log("hash " + hash);
	wsPingPong = "ws://" + document.location.host + "/psicologia-web/pingpong/" + hash;
	wsWebRtcPsiPac =  "ws://" + document.location.host + "/psicologia-web/webtrc/" + hash + "psi_pac";
	wsWebRtcPacPsi =  "ws://" + document.location.host + "/psicologia-web/webtrc/" + hash + "pac_psi";
}