package br.com.jope.psicologia.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="mensagem_sessao")
@NamedQueries({
	@NamedQuery(name=MensagemSessao.FIND_MENSAGEM_SESSAO_POR_SESSAO, query="select ms from MensagemSessao ms join ms.sessao s where s.nuSessao = :nuSessao order by ms.dhInclusao asc ")
})
public class MensagemSessao extends BaseEntity {

	private static final long serialVersionUID = -2779616812366536768L;
	public static final String FIND_MENSAGEM_SESSAO_POR_SESSAO = "MensagemSessao.findMensagemSessaoPorSessao";

	@Id
	@SequenceGenerator(name = "seq_nu_mensagem_sessao", sequenceName = "seq_nu_mensagem_sessao", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nu_mensagem_sessao")
	@Column(name="nu_mensagem_sessao", nullable=false)
	private Long nuMensagemSessao;
	
	@ManyToOne
	@JoinColumn(name="nu_sessao", nullable=false)
	private Sessao sessao;
	
	@ManyToOne
	@JoinColumn(name="nu_usuario", nullable=false)
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dh_inclusao", nullable=false)
	private Date dhInclusao;
	
	@Column(name="de_mensagem", nullable=false)
	private String deMensagem;
	
	public Long getNuMensagemSessao() {
		return nuMensagemSessao;
	}
	public void setNuMensagemSessao(Long nuMensagemSessao) {
		this.nuMensagemSessao = nuMensagemSessao;
	}
	public Sessao getSessao() {
		return sessao;
	}
	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getDhInclusao() {
		return dhInclusao;
	}
	public void setDhInclusao(Date dhInclusao) {
		this.dhInclusao = dhInclusao;
	}
	public String getDeMensagem() {
		return deMensagem;
	}
	public void setDeMensagem(String deMensagem) {
		this.deMensagem = deMensagem;
	}
	
}
