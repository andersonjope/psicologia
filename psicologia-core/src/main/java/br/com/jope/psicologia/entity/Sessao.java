package br.com.jope.psicologia.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="sessao")
public class Sessao extends BaseEntity {

	private static final long serialVersionUID = 2735099985464966406L;

	@Id
	@SequenceGenerator(name = "seq_nu_sessao", sequenceName = "seq_nu_sessao", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nu_sessao")
	@Column(name="nu_sessao", nullable=false)
	private Long nuSessao;
	
	@ManyToOne
	@JoinColumn(name="nu_medico", nullable=false)
	private Medico medico;
	
	@ManyToOne
	@JoinColumn(name="nu_cliente", nullable=false)
	private Cliente cliente;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dh_inicio_sessao", nullable=false)
	private Date dhInicioSessao;
	
	@Column(name="dh_final_sessao")
	private Date dhFinalSessao;

	@OneToMany(mappedBy="sessao", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy(value="dhRegistro desc")
	private List<SalaSessao> salaSessaoList;
	
	public Long getNuSessao() {
		return nuSessao;
	}

	public void setNuSessao(Long nuSessao) {
		this.nuSessao = nuSessao;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDhInicioSessao() {
		return dhInicioSessao;
	}

	public void setDhInicioSessao(Date dhInicioSessao) {
		this.dhInicioSessao = dhInicioSessao;
	}

	public Date getDhFinalSessao() {
		return dhFinalSessao;
	}

	public void setDhFinalSessao(Date dhFinalSessao) {
		this.dhFinalSessao = dhFinalSessao;
	}

	public List<SalaSessao> getSalaSessaoList() {
		return salaSessaoList;
	}

	public void setSalaSessaoList(List<SalaSessao> salaSessaoList) {
		this.salaSessaoList = salaSessaoList;
	}

	public void addSalaSessao(SalaSessao salaSessao) {
		if(salaSessaoList == null) {
			salaSessaoList = new ArrayList<>();
		}
		salaSessao.setSessao(this);
		salaSessaoList.add(salaSessao);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nuSessao == null) ? 0 : nuSessao.hashCode());
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
		Sessao other = (Sessao) obj;
		if (nuSessao == null) {
			if (other.nuSessao != null)
				return false;
		} else if (!nuSessao.equals(other.nuSessao))
			return false;
		return true;
	}
	
}
