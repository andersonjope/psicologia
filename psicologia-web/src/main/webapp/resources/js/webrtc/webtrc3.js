/** browser dependent definition are aligned to one and the same standard name * */

'use strict';

navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia

var wscPsiPac = null;
var wscPacPsi = null;
var localVideoStream = null;
var remoteVideo = null;
var localVideo = null;
var peerConn = null;
var constraints = {video: true, audio: true};
var peerConnCfg = {'iceServers': [{'urls': 'stun:stun.services.mozilla.com'}, {'urls': 'stun:stun.l.google.com:19302'}]};
var origen = '';

$(document).ready(function() {
//	$("#endVideoCliente").attr('disabled', 'disabled');
	
	if(navigator.getUserMedia) {
		origen = $("#origen").val();
		remoteVideo = document.getElementById('remoteVideo');
		localVideo = document.getElementById('localVideo');
		
		wscPsiPac = new WebSocket(wsWebRtcPsiPac);
		wscPsiPac.onmessage = function(evt) { messageData(evt) };
		wscPsiPac.onerror = function(evt) { onError(evt) };
		
//		wscPacPsi = new WebSocket(wsWebRtcPacPsi);
//		wscPacPsi.onmessage = function(evt) { messageData(evt) };						
//		wscPacPsi.onerror = function(evt) { onError(evt) };
		
		$("#initVideoCliente").click(function(){
//			initiateCall();
			alert("iniciar video");
			messageInitConnection();
		});
		
		$("#endVideoCliente").click(function(){
			alert("encerrar video");
			messageEndCall();
		});
		console.log("origen: " + origen + " wsPSI " + wsWebRtcPsiPac + " wsPAC " + wsWebRtcPacPsi);
	}else{
		$("#initVideoCliente").attr('display', 'none');
		$("#endVideoCliente").attr('display', 'none');
		alert("Sorry, your browser does not support WebRTC!")
	}	
});


function prepareCall() {
	console.log("prepareCall");
	
	peerConn = new RTCPeerConnection(peerConnCfg);
	// send any ice candidates to the other peer
	peerConn.onicecandidate = onIceCandidateHandler;
	// once remote stream arrives, show it in the remote video element
	peerConn.onaddstream = onAddStreamHandler;
};

// run start(true) to initiate a call
function initiateCall() {
	console.log("initiateCall");
	//wscPsiPac.onmessage = function(evt) { messageData(evt) };
	prepareCall();
  // get the local stream, show it in the local video element and send it
// navigator.mediaDevices.getUserMedia(constraints).then(getUserMediaSuccess).catch(errorHandler);
	if(navigator.getUserMedia) {
		navigator.getUserMedia(constraints, function (stream) {
			localVideoStream = stream;
		    localVideo.src = window.URL.createObjectURL(localVideoStream);
		    peerConn.addStream(localVideoStream);
		    createAndSendOffer();
		}, function(error) { console.log(error);});
	} else {
		alert("Sorry, your browser does not support WebRTC!")
	}
};

function answerCall() {
	console.log("answerCall");
	prepareCall();
	createAndSendAnswer();
  // get the local stream, show it in the local video element and send it
// navigator.mediaDevices.getUserMedia(constraints).then(createAndSendAnswer()).catch(errorHandler);
//	if(navigator.getUserMedia) {
//		navigator.getUserMedia(constraints, function (stream) {
//			if(origen == 'PSI'){
//				localVideoStream = stream;
//				localVideo.src = window.URL.createObjectURL(localVideoStream);
//				peerConn.addStream(localVideoStream);
//			}
//			createAndSendAnswer();
//		}, function(error) { console.log(error);});
//	} else {
//		alert("Sorry, your browser does not support WebRTC!")
//	}
};

function messageInitConnection() {
	console.log("messageInitConnection. ");
	wscPsiPac.send(JSON.stringify({"processo": "iniciar"}));
//	wscPacPsi.send(JSON.stringify({"processo": "iniciar"}));
}

function messageData (evt) {
	console.log("onMessage: " + evt.data);
	
	try {
	  var signal = JSON.parse(evt.data);
	  
	  if(signal.processo){
		  if(signal.processo == "iniciar"){
			  if(origen == "PAC"){
				  if (!peerConn){
					  answerCall();
				  }
			  }else{
				  initiateCall();
			  }
		  }		  
	  }else{
		  
		  if (signal.sdp) {
			  console.log("Received SDP from remote peer.");
			  // peerConn.setRemoteDescription(new RTCSessionDescription(signal.sdp));
			  
			  // console.log("signal.sdp " + signal.sdp);
			  peerConn.setRemoteDescription(new RTCSessionDescription(signal.sdp)).then(function() {
				  // Only create answers in response to offers
				  if(signal.sdp.type == 'offer') {
					  peerConn.createAnswer().then(createdDescription).catch(errorHandler);
				  }
			  }).catch(errorHandler);
			  
		  } else if (signal.candidate) {
			  console.log("Received ICECandidate from remote peer. signal.candidate");
			  peerConn.addIceCandidate(new RTCIceCandidate(signal.candidate)).catch(errorHandler);
		  }else if(signal.ice) {
			  console.log("Received ICECandidate from remote peer. signal.ice ");
			  peerConn.addIceCandidate(new RTCIceCandidate(signal.ice)).catch(errorHandler);
		  } else if (signal.closeConnection){
			  console.log("Received 'close call' signal from remote peer.");
			  endCall();
		  }		  
	  }
	  
  } catch (e) { 
//	  alert(e);
  }
}

function createdDescription(description) {
  console.log('got description');

	peerConn.setLocalDescription(description).then(function() {
		wscPsiPac.send(JSON.stringify({'sdp': peerConn.localDescription}));
//		wscPacPsi.send(JSON.stringify({'sdp': peerConn.localDescription}));
	}).catch(errorHandler);
}

function createAndSendOffer() {
	console.log("createAndSendOffer ");
	peerConn.createOffer(
			function (offer) {
				var off = new RTCSessionDescription(offer);
				peerConn.setLocalDescription(new RTCSessionDescription(off), 
				function() {
					wscPsiPac.send(JSON.stringify({"sdp": off}));
//					wscPacPsi.send(JSON.stringify({"sdp": off}));
				}, 
				function(error) { console.log(error);});
			}, 
		function (error) { console.log(error);});
};

function createAndSendAnswer() {
	console.log("createAndSendAnswer ");
  peerConn.createAnswer(
    function (answer) {
      var ans = new RTCSessionDescription(answer);
      peerConn.setLocalDescription(ans, function() {
          wscPsiPac.send(JSON.stringify({"sdp": ans}));
//          wscPacPsi.send(JSON.stringify({"sdp": ans}));
        }, 
        function (error) { console.log(error);}
      );
    },
    function (error) {console.log(error);}
  );
};

function onIceCandidateHandler(evt) {
	console.log("onIceCandidateHandler ")
  if (!evt || !evt.candidate){
	  return;	  
  } 
  wscPsiPac.send(JSON.stringify({"candidate": evt.candidate}));
//  wscPacPsi.send(JSON.stringify({"candidate": evt.candidate}));
};

function onAddStreamHandler(evt) {
	console.log("onAddStreamHandler ");
	if(origen == 'PSI'){
		
//	  $("#initVideoCliente").attr('disabled', 'disabled');;
//	  $("#endVideoCliente").attr('disabled', 'false');
	}
  // set remote video stream as source for remote video HTML5 element
	console.log(evt);
  remoteVideo.src = window.URL.createObjectURL(evt.stream);
};

function endCall() {
	if(peerConn){
		peerConn.close();
		peerConn = null;
//		$("#initVideoCliente").attr('disabled', 'false');
//		$("#endVideoCliente").attr('disabled', 'disabled');;
		if (localVideoStream) {
			localVideoStream.getTracks().forEach(function (track) {
				track.stop();
			});
			localVideo.src = "";
		}
		if(origen == 'PAC'){
			if (remoteVideo) remoteVideo.src = "";	  
		}		
	}
};

//function getUserMediaSuccess(stream) {
//	console.log("window.URL " + window.URL);
//	localVideoStream = stream;
//	localVideo.src = window.URL.createObjectURL(localVideoStream);
//	peerConn.addStream(localVideoStream);
//	createAndSendOffer();
//}

function errorHandler(error) {
  console.log("ERROR HANDLER: " + error);
}

function logError(error) {
    console.log("LOG ERROR: " + error.name + ": " + error.message);
}

function messageEndCall(){
	if(wscPsiPac){
		wscPsiPac.send(JSON.stringify({"closeConnection": true}));		
	}
//	if(wscPacPsi){
//		wscPacPsi.send(JSON.stringify({"closeConnection": true}));		
//	}
}

function onError(evt) {
	endCall();
	alert('Erro ao conetar ao servidor: ' + evt.data);
	window.close();
}