/** browser dependent definition are aligned to one and the same standard name **/

'use strict';

var wscPsiPac = null;
var wscPacPsi = null;

$(document).ready(function() {
	wscPsiPac = new WebSocket(wsWebRtcPsiPac);
	wscPacPsi = new WebSocket(wsWebRtcPacPsi);
});

//navigator.getUserMedia = navigator.mediaDevices.getUserMedia;
navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia;
window.RTCPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
window.RTCIceCandidate = window.RTCIceCandidate || window.mozRTCIceCandidate || window.webkitRTCIceCandidate;
window.RTCSessionDescription = window.RTCSessionDescription || window.mozRTCSessionDescription || window.webkitRTCSessionDescription;
window.SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition || window.mozSpeechRecognition || window.msSpeechRecognition || window.oSpeechRecognition;

var origen = '';
var localVideoStream = null;
var videoCallButton = null;
var endCallButton = null;
var peerConn = null;

var constraints = {video: { width: 1280, height: 720 }, audio: true};

var peerConnCfg = {'iceServers': [{'urls': 'stun:stun.services.mozilla.com'}, {'urls': 'stun:stun.l.google.com:19302'}]};

function pageReady(origen) {
	origen = origen;
  // check browser WebRTC availability 
  if(navigator.getUserMedia) {
	  if(origen == 'R'){
		  remoteVideo = document.getElementById('remoteVideo');
	  }else{
		  videoCallButton = document.getElementById("videoCallButton");
		  endCallButton = document.getElementById("endCallButton");
		  localVideo = document.getElementById('localVideo');
		  videoCallButton.removeAttribute("disabled");
		  videoCallButton.addEventListener("click", initiateCall);
		  endCallButton.addEventListener("click", function (evt) {
			  wscPsiPac.send(JSON.stringify({"closeConnection": true}));
		  });		  
	  }
  } else {
    alert("Sorry, your browser does not support WebRTC!")
  }
};

function prepareCall() {
  peerConn = new RTCPeerConnection(peerConnCfg);
  // send any ice candidates to the other peer
  peerConn.onicecandidate = onIceCandidateHandler;
  // once remote stream arrives, show it in the remote video element
  peerConn.onaddstream = onAddStreamHandler;
};

// run start(true) to initiate a call
function initiateCall() {
  prepareCall();
  // get the local stream, show it in the local video element and send it
//  navigator.mediaDevices.getUserMedia(constraints).then(getUserMediaSuccess).catch(errorHandler);
  
  navigator.getUserMedia({ "audio": true, "video": true }, function (stream) {
	    localVideoStream = stream;
	    localVideo.src = window.URL.createObjectURL(localVideoStream);
	    peerConn.addStream(localVideoStream);
	    createAndSendOffer();
	  }, function(error) { console.log(error);});
  
};

function answerCall() {
  prepareCall();
  // get the local stream, show it in the local video element and send it
//  navigator.mediaDevices.getUserMedia(constraints).then(createAndSendAnswer()).catch(errorHandler);
  
  navigator.getUserMedia({ "audio": true, "video": true }, function (stream) {
	  if(origen == 'L'){
    localVideoStream = stream;
    localVideo.src = window.URL.createObjectURL(localVideoStream);
    peerConn.addStream(localVideoStream);
	  }
    createAndSendAnswer();
  }, function(error) { console.log(error);});
};

wscPsiPac.onmessage = function (evt) {
  var signal = null;
  if (!peerConn) answerCall();
  
  signal = JSON.parse(evt.data);
  if (signal.sdp) {
    console.log("Received SDP from remote peer.");
    //peerConn.setRemoteDescription(new RTCSessionDescription(signal.sdp));
    
    //console.log("signal.sdp " + signal.sdp);
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
  } else if ( signal.closeConnection){
    console.log("Received 'close call' signal from remote peer.");
    endCall();
  }
	
//	if (!peerConn) answerCall();
//	
//	var message = JSON.parse(evt.data);
//    if (message.desc) {
//        var desc = message.desc;
//
//        // if we get an offer, we need to reply with an answer
//        if (desc.type == "offer") {
//        	peerConn.setRemoteDescription(desc).then(function () {
//                return peerConn.createAnswer();
//            })
//            .then(function (answer) {
//                return peerConn.setLocalDescription(answer);
//            })
//            .then(function () {
//                var str = JSON.stringify({ desc: peerConn.localDescription });
//                wscPsiPac.send(str);
//            })
//            .catch(logError);
//        } else if (desc.type == "answer") {
//        	peerConn.setRemoteDescription(desc).catch(logError);
//        } else {
//            log("Unsupported SDP type. Your code may differ here.");
//        }
//    } else{
//    	peerConn.addIceCandidate(message.candidate).catch(logError);
//    }
	
}

function createdDescription(description) {
  console.log('got description');

  peerConn.setLocalDescription(description).then(function() {
	  wscPsiPac.send(JSON.stringify({'sdp': peerConn.localDescription}));
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
        }, 
        function(error) { console.log(error);}
      );
    }, 
    function (error) { console.log(error);}
  );
};

function createAndSendAnswer() {
	console.log("createAndSendAnswer ");
  peerConn.createAnswer(
    function (answer) {
      var ans = new RTCSessionDescription(answer);
      peerConn.setLocalDescription(ans, function() {
          wscPsiPac.send(JSON.stringify({"sdp": ans}));
        }, 
        function (error) { console.log(error);}
      );
    },
    function (error) {console.log(error);}
  );
};

function onIceCandidateHandler(evt) {
	console.log("onIceCandidateHandler ")
  if (!evt || !evt.candidate) 
	  return;
  wscPsiPac.send(JSON.stringify({"candidate": evt.candidate}));
};

function onAddStreamHandler(evt) {
	console.log("onAddStreamHandler ");
	if(origen == 'L'){
	  videoCallButton.setAttribute("disabled", true);
	  endCallButton.removeAttribute("disabled");
	}
  // set remote video stream as source for remote video HTML5 element
	console.log(evt);
  remoteVideo.src = window.URL.createObjectURL(evt.stream);
};

function endCall() {
	if(!videoCallButton){
		peerConn.close();
		peerConn = null;
		videoCallButton.removeAttribute("disabled");
		endCallButton.setAttribute("disabled", true);
		if (localVideoStream) {
			localVideoStream.getTracks().forEach(function (track) {
				track.stop();
			});
			localVideo.src = "";
		}
		if(origen == 'L'){
			if (remoteVideo) remoteVideo.src = "";	  
		}		
	}
};

function getUserMediaSuccess(stream) {
	console.log("window.URL " + window.URL);
	localVideoStream = stream;
	localVideo.src = window.URL.createObjectURL(localVideoStream);
	peerConn.addStream(localVideoStream);
	createAndSendOffer();
}

function errorHandler(error) {
  console.log(error);
}

function logError(error) {
    console.log(error.name + ": " + error.message);
}
