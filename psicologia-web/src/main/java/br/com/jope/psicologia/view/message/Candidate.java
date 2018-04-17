package br.com.jope.psicologia.view.message;

import java.io.Serializable;

public class Candidate implements Serializable{

	private static final long serialVersionUID = 5457846453523153109L;
	
	private String candidate;
	private String sdpMid;
	private String sdpMLineIndex;
	
	public String getCandidate() {
		return candidate;
	}
	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}
	public String getSdpMid() {
		return sdpMid;
	}
	public void setSdpMid(String sdpMid) {
		this.sdpMid = sdpMid;
	}
	public String getSdpMLineIndex() {
		return sdpMLineIndex;
	}
	public void setSdpMLineIndex(String sdpMLineIndex) {
		this.sdpMLineIndex = sdpMLineIndex;
	}
	
}
