package br.com.jope.psicologia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Table(name="cliente")
@NamedQueries({
	@NamedQuery(name=Cliente.FIND_CLIENTE_POR_USUARIO, query="select c from Cliente c join c.usuario u where u.nuUsuario = :nuUsuario ")
})
public class Cliente extends BaseEntity {

	private static final long serialVersionUID = -7502111196152307044L;
	public static final String FIND_CLIENTE_POR_USUARIO = "Cliente.findClientePorUsuario";
	
	@Id
	@SequenceGenerator(name = "seq_nu_cliente", sequenceName = "seq_nu_cliente", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nu_cliente")
	@Column(name="nu_cliente", nullable=false)
	private Long nuCliente;
	
	@Valid
	@OneToOne
	@JoinColumn(name="nu_usuario", nullable=false)
	private Usuario usuario;
	
	@Column(name="de_nome_contato", length=100)
	private String deNomeContato;
	
	@Column(name="co_telefone_contato", length=11)
	private String coTelefoneContato;

	public Long getNuCliente() {
		return nuCliente;
	}

	public void setNuCliente(Long nuCliente) {
		this.nuCliente = nuCliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDeNomeContato() {
		return deNomeContato;
	}

	public void setDeNomeContato(String deNomeContato) {
		this.deNomeContato = deNomeContato;
	}

	public String getCoTelefoneContato() {
		return coTelefoneContato;
	}

	public void setCoTelefoneContato(String coTelefoneContato) {
		this.coTelefoneContato = coTelefoneContato;
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
