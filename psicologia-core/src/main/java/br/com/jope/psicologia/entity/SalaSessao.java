package br.com.jope.psicologia.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="sala_sessao")
public class SalaSessao extends BaseEntity {

	private static final long serialVersionUID = 6552635688518126552L;

	@Id
	@SequenceGenerator(name = "seq_nu_sala_sessao", sequenceName = "seq_nu_sala_sessao", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nu_sala_sessao")
	@Column(name="nu_sala_sessao", nullable=false)
	private Long nuSalaSessao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dh_registro", nullable=false)
	private Date dhRegistro;
	
	@Column(name="nu_velocidade_movimento", nullable=false)
	private Integer nuVelocidadeMovimento;
	
	@Column(name="nu_altura", nullable=true)
	private Integer nuAltura;
	
	@Column(name="nu_largura", nullable=true)
	private Integer nuLargura;
	
	@ManyToOne
	@JoinColumn(name="nu_sessao")
	private Sessao sessao;

	public Long getNuSalaSessao() {
		return nuSalaSessao;
	}

	public void setNuSalaSessao(Long nuSalaSessao) {
		this.nuSalaSessao = nuSalaSessao;
	}

	public Date getDhRegistro() {
		return dhRegistro;
	}

	public void setDhRegistro(Date dhRegistro) {
		this.dhRegistro = dhRegistro;
	}

	public Integer getNuVelocidadeMovimento() {
		return nuVelocidadeMovimento;
	}

	public void setNuVelocidadeMovimento(Integer nuVelocidadeMovimento) {
		this.nuVelocidadeMovimento = nuVelocidadeMovimento;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public Integer getNuAltura() {
		return nuAltura;
	}

	public void setNuAltura(Integer nuAltura) {
		this.nuAltura = nuAltura;
	}

	public Integer getNuLargura() {
		return nuLargura;
	}

	public void setNuLargura(Integer nuLargura) {
		this.nuLargura = nuLargura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nuSalaSessao == null) ? 0 : nuSalaSessao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalaSessao other = (SalaSessao) obj;
		if (nuSalaSessao == null) {
			if (other.nuSalaSessao != null)
				return false;
		} else if (!nuSalaSessao.equals(other.nuSalaSessao))
			return false;
		return true;
	}
	
}
