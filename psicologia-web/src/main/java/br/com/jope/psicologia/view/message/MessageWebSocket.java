package br.com.jope.psicologia.view.message;

import java.io.Serializable;
import java.util.Map;

public class MessageWebSocket implements Serializable {

	private static final long serialVersionUID = -8444637075069910010L;
	private Map<String, String> users;
	private Candidate candidate;
	private Sdp sdp;
	private String user;
	private String operacao;
	private String from;
	private String hash;
	private String message;
	private String processo;
	private String acao;
	private String uuid;
	private String velocidade;
	private boolean closeConnection;
	private String playStop;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, String> getUsers() {
		return users;
	}
	public void setUsers(Map<String, String> users) {
		this.users = users;
	}
	public String getVelocidade() {
		return velocidade;
	}
	public void setVelocidade(String velocidade) {
		this.velocidade = velocidade;
	}
	public String getPlayStop() {
		return playStop;
	}
	public void setPlayStop(String playStop) {
		this.playStop = playStop;
	}
	public String getProcesso() {
		return processo;
	}
	public void setProcesso(String processo) {
		this.processo = processo;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public boolean isCloseConnection() {
		return closeConnection;
	}
	public void setCloseConnection(boolean closeConnection) {
		this.closeConnection = closeConnection;
	}
	
	public Sdp getSdp() {
		return sdp;
	}
	public void setSdp(Sdp sdp) {
		this.sdp = sdp;
	}
	public Candidate getCandidate() {
		return candidate;
	}
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	
}
