package br.com.jope.psicologia.view.message;

import java.io.Serializable;

public class Sdp implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;
	private String sdp;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSdp() {
		return sdp;
	}
	public void setSdp(String sdp) {
		this.sdp = sdp;
	}
	
}
