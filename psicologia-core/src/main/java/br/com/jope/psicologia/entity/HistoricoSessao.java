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
@Table(name="historico_sessao")
@NamedQueries({
	@NamedQuery(name=HistoricoSessao.FIND_HISTORICO_SESSAO, query="select hs from HistoricoSessao hs join hs.sessao s where hs.dhEncerramento is null and s.nuSessao = :nuSessao order by hs.dhRegistro desc ")
})
public class HistoricoSessao extends BaseEntity {

	private static final long serialVersionUID = -1697054865809839261L;
	public static final String FIND_HISTORICO_SESSAO = "HistoricoSessao.findUsuarioLogin";

	@Id
	@SequenceGenerator(name = "seq_nu_historico_sessao", sequenceName = "seq_nu_historico_sessao", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nu_historico_sessao")
	@Column(name="nu_historico_sessao", nullable=false)
	private Long nuHistoricoSessao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dh_registro", nullable=false)
	private Date dhRegistro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dh_inicio_cliente", nullable=true)
	private Date dhInicioCliente;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dh_inicio_medico", nullable=true)
	private Date dhInicioMedico;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dh_encerramento", nullable=true)
	private Date dhEncerramento;
	
	@ManyToOne
	@JoinColumn(name="nu_cliente", nullable=true)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="nu_medico", nullable=true)
	private Medico medico;
	
	@ManyToOne
	@JoinColumn(name="nu_usuario_encerramento", nullable=true)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name="nu_sessao", nullable=false)
	private Sessao sessao;
	
	public Long getNuHistoricoSessao() {
		return nuHistoricoSessao;
	}
	public void setNuHistoricoSessao(Long nuHistoricoSessao) {
		this.nuHistoricoSessao = nuHistoricoSessao;
	}
	public Date getDhRegistro() {
		return dhRegistro;
	}
	public void setDhRegistro(Date dhRegistro) {
		this.dhRegistro = dhRegistro;
	}
	public Date getDhInicioCliente() {
		return dhInicioCliente;
	}
	public void setDhInicioCliente(Date dhInicioCliente) {
		this.dhInicioCliente = dhInicioCliente;
	}
	public Date getDhInicioMedico() {
		return dhInicioMedico;
	}
	public void setDhInicioMedico(Date dhInicioMedico) {
		this.dhInicioMedico = dhInicioMedico;
	}
	public Date getDhEncerramento() {
		return dhEncerramento;
	}
	public void setDhEncerramento(Date dhEncerramento) {
		this.dhEncerramento = dhEncerramento;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Sessao getSessao() {
		return sessao;
	}
	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}
	
}
