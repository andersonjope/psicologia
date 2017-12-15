package br.com.jope.psicologia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
@NamedQueries({
	@NamedQuery(name=Usuario.FIND_USUARIO_LOGIN, query="select u from Usuario u where u.deLogin = :deLogin ")
})
public class Usuario extends BaseEntity {

	private static final long serialVersionUID = 1503719338265091527L;
	public static final String FIND_USUARIO_LOGIN = "Usuario.findUsuarioLogin";

	@Id
	@SequenceGenerator(name = "seq_nu_usuario", sequenceName = "seq_nu_usuario", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nu_usuario")
	@Column(name="nu_usuario", nullable=false)
	private Long nuUsuario;
	
	@Column(name="de_login", nullable=false)
	private String deLogin;
	
	@Column(name="de_senha", nullable=false)
	private String deSenha;

	public Long getNuUsuario() {
		return nuUsuario;
	}

	public void setNuUsuario(Long nuUsuario) {
		this.nuUsuario = nuUsuario;
	}

	public String getDeLogin() {
		return deLogin;
	}

	public void setDeLogin(String deLogin) {
		this.deLogin = deLogin;
	}

	public String getDeSenha() {
		return deSenha;
	}

	public void setDeSenha(String deSenha) {
		this.deSenha = deSenha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nuUsuario == null) ? 0 : nuUsuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (nuUsuario == null) {
			if (other.nuUsuario != null)
				return false;
		} else if (!nuUsuario.equals(other.nuUsuario))
			return false;
		return true;
	}
	
}
