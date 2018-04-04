/** browser dependent definition are aligned to one and the same standard name * */

'use strict';

navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;

var hostname = window.location.hostname;

var constraints = {video: true, audio: true};
var peerConnCfg = {'iceServers': [{'urls': 'stun:stun.services.mozilla.com'}, {'urls': 'stun:stun2.l.google.com:19302'}]};
var psi_pac = "psi_pac";
var pac_psi = "pac_psi";
var wsc = null;
var origen = null;
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
	origen = $("#origen").val();
	if (navigator.getUserMedia) {
		remoteVideo = document.getElementById('remoteVideo');
		localVideo = document.getElementById('localVideo');
		
		wsc = new WebSocket(wsWebRtc);
		wsc.onmessage = function(evt) {
			messageData(evt)
		};
		wsc.onerror = function(evt) {
			onError(evt)
		};
		wsc.onclose = function(evt) {
			onClose(evt)
		};

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
	wsc.send(JSON.stringify({
		"processo" : processo,
		"acao" : origen,
		"uuid": uuid
	}));
	$("#initVideoCliente").css('display', 'none');
	$("#endVideoCliente").css('display', 'block');
}

function messageData(evt) {
	try {
		var signal = JSON.parse(evt.data);

		if (signal.processo) {
			if (!processado) {
				processado = true;
				if (signal.processo == "iniciarpsipac") {
					if (psi_pac == origen) {
						initiateCall(signal.acao);
					} else if (pac_psi == origen) {
						if (!peerConn){
							answerCall(signal.acao);
						}
					}		
					setTimeout(messageInitConnection("iniciarpacpsi"),2000);
				}else if (signal.processo == "iniciarpacpsi") {
					$("#endVideoPaciente").css('display', 'block');
					if (psi_pac == origen) {
						if (!peerConn){
							answerCall(signal.acao);
						}
					} else if (pac_psi == origen) {
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
	if (wsc) {
		wsc.send(JSON.stringify({
			"closeConnection" : true,
			"uuid": uuid
		}));
	}
}

function initiateCall(processo) {
	peerConn = prepareCall(processo);
	if (navigator.getUserMedia) {
		navigator.getUserMedia(constraints, function(stream) {
			localVideoStream = stream;
			localVideo.src = window.URL.createObjectURL(localVideoStream);
			
			localVideoStream.getTracks().forEach(function(track) {
				peerConn.addTrack(track, localVideoStream);
			  });
			
			createAndSendOffer();
		}, function(error) { });
	} else {
		alert("Sorry, your browser does not support WebRTC!")
	}
};

function prepareCall(acao) {
	if (acao === psi_pac) {
		pc_psi_pac = new RTCPeerConnection(peerConnCfg);
		pc_psi_pac.onicecandidate = onIceCandidateHandler;
		pc_psi_pac.onaddstream = onAddStreamHandler;
		return pc_psi_pac;
	} else if (acao === pac_psi) {
		pc_pac_psi = new RTCPeerConnection(peerConnCfg);
		pc_pac_psi.onicecandidate = onIceCandidateHandler;
		peerConn.onaddstream = onAddStreamHandler;
		return pc_pac_psi;
	}
};

function onIceCandidateHandler(evt) {
	if (!evt || !evt.candidate) {
		return;
	}
	wsc.send(JSON.stringify({
		"candidate" : evt.candidate,
		"uuid": uuid
	}));
};

function onAddStreamHandler(evt) {
  remoteVideo.src = window.URL.createObjectURL(evt.stream);
};

function createAndSendOffer() {
	peerConn.createOffer(function(offer) {
		var off = new RTCSessionDescription(offer);
		peerConn.setLocalDescription(new RTCSessionDescription(off),
				function() {
					wsc.send(JSON.stringify({
						"sdp" : off,
						"uuid": uuid
					}));
				}, function(error) { });
	}, function(error) { });
};



function answerCall(acao) {
	peerConn = prepareCall(acao);
	createAndSendAnswer();
}

function createAndSendAnswer() {
  peerConn.createAnswer(
    function (answer) {
      var ans = new RTCSessionDescription(answer);
      peerConn.setLocalDescription(ans, function() {
          wsc.send(JSON.stringify({
        	  "sdp": ans,
        	  "uuid": uuid
          }));
        }, 
        function (error) { });
    },
    function (error) { });
};

function createdDescription(description) {
	  peerConn.setLocalDescription(description).then(function() {
			wsc.send(JSON.stringify({
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
			localVideo.src = "";
		}
		if (remoteVideo){
			remoteVideo.src = "";	  
		}
	}
	$("#initVideoCliente").css('display', 'block');
	$("#endVideoCliente").css('display', 'none');
	$("#endVideoPaciente").css('display', 'none');
	window.location.reload();
};

function errorHandler(error) {
}

function onError(evt) {
	avisoReload();
}

function loadUuid() {
    function s4() {
    	return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
    }
    return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
}

function onClose(){
	avisoReload();
}

function avisoReload(){
}