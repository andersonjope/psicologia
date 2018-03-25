/** browser dependent definition are aligned to one and the same standard name * */

'use strict';

navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;

var hostname = window.location.hostname;

var constraints = {video: true, audio: true};
//var peerConnCfg = {'iceServers': [{urls: "turn:" + hostname, username: userHash, credential: userHash }]};		
var peerConnCfg = {'iceServers': [{'urls': 'stun:stun.services.mozilla.com'}, {'urls': 'stun:stun.l.google.com:19302'}]};		
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

$(document).ready(function() {
	loadInit(wsWebRtcPsiPac);
});

function loadInit(wsWebRtc){
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

		$("#initVideoCliente").click(function() {
			console.log("iniciar video");
			messageInitConnection();
		});

		$("#endVideoCliente").click(function() {
			console.log("encerrar video");
			messageEndCall();
		});
		console.log("origen: " + origen + " wsWebRtc " + wsWebRtcPsiPac + " : " + wsWebRtcPacPsi);
	} else {
		$("#initVideoCliente").attr('display', 'none');
		$("#endVideoCliente").attr('display', 'none');
		alert("Sorry, your browser does not support WebRTC!")
	}
}

function messageInitConnection() {
	console.log("messageInitConnection. ");
	origen = $("#origen").val();
	processado = false;
	wsc.send(JSON.stringify({
		"processo" : origen
	}));
}

function messageData(evt) {
	console.log("onMessage: ");

	try {
		var signal = JSON.parse(evt.data);

		if (signal.processo) {
			if (!processado) {
				processado = true;
				if (psi_pac == origen) {
					console.log(origen + ": " + signal.processo);
					initiateCall(signal.processo);
				} else if (pac_psi == origen) {
					console.log(origen + ": " + signal.processo);
					if (!peerConn){
						answerCall(signal.processo);
						
						console.log("iniciando processo cliente");
						setTimeout(messageInitConnection(), 2000);
						console.log("iniciando processo cliente WS");
						loadInit(wsWebRtcPacPsi);
						console.log("iniciado processo cliente WS");
					}
					
				}
			}
		} else {

			if (signal.sdp) {
				console.log("Received SDP from remote peer.");
//				peerConn.setRemoteDescription(new RTCSessionDescription(signal.sdp));
				peerConn.setRemoteDescription(new RTCSessionDescription(signal.sdp)).then(function() {
					  // Only create answers in response to offers
					  if(signal.sdp.type == 'offer') {
						  peerConn.createAnswer().then(createdDescription).catch(errorHandler);
					  }
				  }).catch(errorHandler);
				
//				peerConn.setRemoteDescription(new RTCSessionDescription(signal.sdp), function () {
//		            // if we received an offer, we need to answer
//		            if (signal.sdp.type == 'offer'){
//		            	peerConn.createAnswer(createdDescription, errorHandler);		            	
//		            }
//		        }, errorHandler);
				
			} else if (signal.candidate) {
				console .log("Received ICECandidate from remote peer. signal.candidate" );
				peerConn.addIceCandidate(new RTCIceCandidate(signal.candidate)).catch(errorHandler);
			} else if (signal.ice) {
				console.log("Received ICECandidate from remote peer. signal.ice ");
				peerConn.addIceCandidate(new RTCIceCandidate(signal.ice)).catch(errorHandler);
			} else if (signal.closeConnection) {
				console.log("Received 'close call' signal from remote peer." );
			}
		}

	} catch (e) {
		// alert(e);
	}
}

function messageEndCall() {
	if (wsc) {
		wsc.send(JSON.stringify({
			"closeConnection" : true
		}));
	}
}

function initiateCall(processo) {
	console.log("initiateCall");
	// wscPsiPac.onmessage = function(evt) { messageData(evt) };
	peerConn = prepareCall(processo);
	console.log("peerConn " + peerConn);
	// get the local stream, show it in the local video element and send it
	// navigator.mediaDevices.getUserMedia(constraints).then(getUserMediaSuccess).catch(errorHandler);
	if (navigator.getUserMedia) {
		console.log("initiateCall 1 ");
		navigator.getUserMedia(constraints, function(stream) {
			localVideoStream = stream;
			localVideo.src = window.URL.createObjectURL(localVideoStream);
//			peerConn.addStream(localVideoStream);
			
			localVideoStream.getTracks().forEach(function(track) {
				peerConn.addTrack(track, localVideoStream);
			  });
			
			createAndSendOffer();
		}, function(error) {
			console.log("initiateCall erro : " + error);
		});
	} else {
		alert("Sorry, your browser does not support WebRTC!")
	}
};

function prepareCall(processo) {
	if (processo === psi_pac) {
		console.log("prepareCall 1");
		pc_psi_pac = new RTCPeerConnection(peerConnCfg);
		// send any ice candidates to the other peer
		pc_psi_pac.onicecandidate = onIceCandidateHandler;
		// once remote stream arrives, show it in the remote video element
		pc_psi_pac.onaddstream = onAddStreamHandler;
		console.log("prepareCall 1.1");
		return pc_psi_pac;
	} else if (processo === pac_psi) {
		console.log("prepareCall 2");
		pc_pac_psi = new RTCPeerConnection(peerConnCfg);
		// send any ice candidates to the other peer
		pc_pac_psi.onicecandidate = onIceCandidateHandler;
		// once remote stream arrives, show it in the remote video element
		peerConn.onaddstream = onAddStreamHandler;
		console.log("prepareCall 2.2");
		return pc_pac_psi;
	}
};

function onIceCandidateHandler(evt) {
	console.log("onIceCandidateHandler ")
	if (!evt || !evt.candidate) {
		return;
	}
	wsc.send(JSON.stringify({
		"candidate" : evt.candidate
	}));
};

function onTrackHandler(evt) {
	console.log("onAddStreamHandler ");
	remoteVideo.srcObject = evt.streams[0];
};

function onAddStreamHandler(evt) {
	console.log("onAddStreamHandler ");
  remoteVideo.src = window.URL.createObjectURL(evt.stream);
};

function createAndSendOffer() {
	console.log("createAndSendOffer ");
	peerConn.createOffer(function(offer) {
		var off = new RTCSessionDescription(offer);
		peerConn.setLocalDescription(new RTCSessionDescription(off),
				function() {
					wsc.send(JSON.stringify({
						"sdp" : off
					}));
				}, function(error) {
					console.log("createAndSendOffer erro 1 : " + error);
				});
	}, function(error) {
		console.log("createAndSendOffer erro 2 : " + error);
	});
};



function answerCall(processo) {
	console.log("answerCall");
	peerConn = prepareCall(processo);
	createAndSendAnswer();
}

function createAndSendAnswer() {
	console.log("createAndSendAnswer ");
  peerConn.createAnswer(
    function (answer) {
      var ans = new RTCSessionDescription(answer);
      peerConn.setLocalDescription(ans, function() {
          wsc.send(JSON.stringify({"sdp": ans}));
        }, 
        function (error) { 
        	console.log("createAndSendAnswer erro 1 " + error);
        }
      );
    },
    function (error) { 
    	console.log("createAndSendAnswer erro 2 " + error);
    }
  );
};

function createdDescription(description) {
	  console.log('got description');

	  peerConn.setLocalDescription(description).then(function() {
			wsc.send(JSON.stringify({'sdp': peerConn.localDescription}));
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
		if(origen == psi_pac){
			if (remoteVideo) remoteVideo.src = "";	  
		}		
	}
};

function errorHandler(error) {
	console.log("ERROR HANDLER: " + error);
}

function onError(evt) {
//	endCall();
//	window.close();
}