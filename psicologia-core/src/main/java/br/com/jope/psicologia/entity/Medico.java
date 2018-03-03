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
@Table(name="medico")
public class Medico extends BaseEntity {

	private static final long serialVersionUID = 2877923100456579325L;

	@Id
	@SequenceGenerator(name = "seq_nu_medico", sequenceName = "seq_nu_medico", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nu_medico")
	@Column(name="nu_medico", nullable=false)
	private Long nuMedico;
	
	@NotEmpty(message="Preenchimento obrigatório.")
	@Column(name="de_nome", nullable=false)
	private String deNome;
	
	@Valid
	@OneToOne
	@JoinColumn(name="nu_usuario", nullable=false)
	private Usuario usuario;

	public Long getNuMedico() {
		return nuMedico;
	}

	public void setNuMedico(Long nuMedico) {
		this.nuMedico = nuMedico;
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
		result = prime * result + ((nuMedico == null) ? 0 : nuMedico.hashCode());
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
		Medico other = (Medico) obj;
		if (nuMedico == null) {
			if (other.nuMedico != null)
				return false;
		} else if (!nuMedico.equals(other.nuMedico))
			return false;
		return true;
	}
	
}
