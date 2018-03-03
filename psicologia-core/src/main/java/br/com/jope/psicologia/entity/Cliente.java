package br.com.jope.psicologia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="cliente")
public class Cliente extends BaseEntity {

	private static final long serialVersionUID = -7502111196152307044L;
	
	@Id
	@SequenceGenerator(name = "seq_nu_cliente", sequenceName = "seq_nu_cliente", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nu_cliente")
	@Column(name="nu_cliente", nullable=false)
	private Long nuCliente;
	
	@NotEmpty(message="{obrigatorio}")
	@Column(name="de_nome", nullable=false)
	private String deNome;
	
	@Valid
	@OneToOne
	@JoinColumn(name="nu_usuario", nullable=false)
	private Usuario usuario;

	public Long getNuCliente() {
		return nuCliente;
	}

	public void setNuCliente(Long nuCliente) {
		this.nuCliente = nuCliente;
	}

	public String getDeNome() {
		return deNome;
	}

	public void setDeNome(String deNome) {
		this.deNome = deNome;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nuCliente == null) ? 0 : nuCliente.hashCode());
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
		Cliente other = (Cliente) obj;
		if (nuCliente == null) {
			if (other.nuCliente != null)
				return false;
		} else if (!nuCliente.equals(other.nuCliente))
			return false;
		return true;
	}
	
}
