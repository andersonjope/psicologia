/** browser dependent definition are aligned to one and the same standard name * */

'use strict';

var RTCPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
var RTCSessionDescription = window.RTCSessionDescription || window.mozRTCSessionDescription;

navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia;

var constraints = {video: true, audio: true};
var peerConnCfg = {'iceServers': [{'urls': 'stun:stun.services.mozilla.com'}, {'urls': 'stun:stun2.l.google.com:19302'}]};
var psi_pac = "psi_pac";
var pac_psi = "pac_psi";
var wsc = null;
var pc_psi_pac = null;
var pc_pac_psi = null;
var localVideoStream = null;
var remoteVideo = null;
var localVideo = null;
var peerConn = null;
var processado = false;
var processadoCliente = false;
var uuid;

$(document).ready(function() {
	loadInit();
});

function loadInit(){
	if (navigator.getUserMedia) {
		remoteVideo = document.getElementById('remoteVideo');
		localVideo = document.getElementById('localVideo');

		$("#initVideoCliente").click(function() {
			messageInitConnection("iniciarpsipac");
		});

		$("#endVideoCliente").click(function() {
			messageEndCall();
		});
		
		$("#endVideoPaciente").click(function() {
			messageEndCall();
		});
		
	} else {
		$("#initVideoCliente").css('display', 'none');
		$("#endVideoCliente").css('display', 'none');
		alert("Desculpa, o seu navegador não suporta!")
	}
}

function messageInitConnection(processo) {
	uuid = loadUuid();
	processado = false;
	if(ws.readyState === 1){
		ws.send(JSON.stringify({
			"operacao" : "video",
			"processo" : processo,
			"acao" : origem,
			"uuid": uuid
		}));		
	}
	$("#initVideoCliente").css('display', 'none');
	$("#endVideoCliente").css('display', 'block');
}

function messageVideo(message) {
	try {
		var signal = message;

		if (signal.processo) {
			if (!processado) {
				processado = true;
				if (signal.processo == "iniciarpsipac") {
					if (psi_pac == origem) {
						initiateCall(signal.acao);
					} else if (pac_psi == origem) {
						if (!peerConn){
							answerCall(signal.acao);
						}
					}		
					setTimeout(messageInitConnection("iniciarpacpsi"),5000);
				}else if (signal.processo == "iniciarpacpsi") {
					$("#endVideoPaciente").css('display', 'block');
					if (psi_pac == origem) {
						if (!peerConn){
							answerCall(signal.acao);
						}
					} else if (pac_psi == origem) {
						initiateCall(signal.acao);
					}					
				}
			}
		} else {
			if (signal.sdp) {
				peerConn.setRemoteDescription(new RTCSessionDescription(signal.sdp)).then(function() {
					  if(signal.sdp.type == 'offer') {
						  peerConn.createAnswer().then(createdDescription).catch(errorHandler);
					  }
				  }).catch(errorHandler);
			} else if (signal.candidate) {
				peerConn.addIceCandidate(new RTCIceCandidate(signal.candidate)).catch(errorHandler);
			} else if (signal.ice) {
				peerConn.addIceCandidate(new RTCIceCandidate(signal.ice)).catch(errorHandler);
			} else if (signal.closeConnection) {
				endCall();
			}
		}

	} catch (e) {
		// alert(e);
	}
}

function messageEndCall() {
	if (ws) {
		ws.send(JSON.stringify({
			"operacao" : "video",
			"closeConnection" : true,
			"uuid": uuid
		}));
	}
}

function initiateCall(processo) {
	peerConn = prepareCall(processo);
	peerConn.oniceconnectionstatechange = onIceConnectionStateChange;
	if (navigator.getUserMedia) {
		navigator.getUserMedia(constraints, function(stream) {
			localVideoStream = stream;
			
			try {
				localVideo.srcObject = localVideoStream;
			} catch (error) {
				localVideo.src = URL.createObjectURL(localVideoStream);
			}
			
			localVideoStream.getTracks().forEach(function(track) {
				peerConn.addTrack(track, localVideoStream);
			  });
			
			createAndSendOffer();
		}, function(error) { });
	} else {
		alert("Sorry, your browser does not support WebRTC!");
	}
}

function onIceConnectionStateChange(ev) {
    ev.currentTarget.iceConnectionState;
}

function prepareCall(acao) {
	if (acao === psi_pac) {
		pc_psi_pac = new RTCPeerConnection(peerConnCfg);
		pc_psi_pac.onicecandidate = onIceCandidateHandler;
		pc_psi_pac.ontrack = onTrackStreamHandler;
		return pc_psi_pac;
	} else if (acao === pac_psi) {
		pc_pac_psi = new RTCPeerConnection(peerConnCfg);
		pc_pac_psi.onicecandidate = onIceCandidateHandler;
		pc_pac_psi.ontrack = onTrackStreamHandler;
		return pc_pac_psi;
	}
};

function onIceCandidateHandler(evt) {
	if (!evt || !evt.candidate) {
		return;
	}
	ws.send(JSON.stringify({
		"operacao" : "video",
		"candidate" : evt.candidate,
		"uuid": uuid
	}));
}

function onTrackStreamHandler(evt) {
	if (remoteVideo.srcObject !== evt.streams[0]) {
	    remoteVideo.srcObject = evt.streams[0];
	  }
}

function createAndSendOffer() {
	peerConn.createOffer(function(offer) {
		var off = new RTCSessionDescription(offer);
		peerConn.setLocalDescription(new RTCSessionDescription(off),
				function() {
					ws.send(JSON.stringify({
						"operacao" : "video",
						"sdp" : off,
						"uuid": uuid
					}));
				}, function(error) { });
	}, function(error) { });
}



function answerCall(acao) {
	peerConn = prepareCall(acao);
	createAndSendAnswer();
}

function createAndSendAnswer() {
  peerConn.createAnswer(
    function (answer) {
      var ans = new RTCSessionDescription(answer);
      peerConn.setLocalDescription(ans, function() {
          ws.send(JSON.stringify({
        	  "operacao" : "video",
        	  "sdp": ans,
        	  "uuid": uuid
          }));
        }, 
        function (error) { });
    },
    function (error) { });
}

function createdDescription(description) {
	  peerConn.setLocalDescription(description).then(function() {
			ws.send(JSON.stringify({
				"operacao" : "video",
				"sdp": peerConn.localDescription,
				"uuid": uuid
			}));
	  }).catch(errorHandler);
}

function endCall() {
	if(peerConn){
		peerConn.close();
		peerConn = null;
		if (localVideoStream) {
			localVideoStream.getTracks().forEach(function (track) {
				track.stop();
			});
			localVideo.srcObject = null;
			localVideo.src = "";
		}
		if (remoteVideo){
			remoteVideo.srcObject = null;
			remoteVideo.src = "";
		}
	}
	if($("#endVideoCliente").length > 0){
		$("#endVideoCliente").css('display', 'none');		
		$("#initVideoCliente").css('display', 'block');
	}
	if($("#endVideoPaciente").length > 0){
		$("#endVideoPaciente").css('display', 'none');
	}
	criaTagVideo();
}

function errorHandler(error) {
}

function loadUuid() {
    function s4() {
    	return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
    }
    return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
}

