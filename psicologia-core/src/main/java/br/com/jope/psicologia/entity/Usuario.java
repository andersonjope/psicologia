package br.com.jope.psicologia.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@Column(name="co_cpf", length=11)
	private String coCPF;
	
	@Column(name="de_endereco", length=100)
	private String deEndereco;
	
	@Column(name="co_cep", length=8)
	private String coCep;
	
	@Column(name="de_cidade", length=100)
	private String deCidade;
	
	@Column(name="de_pais", length=100)
	private String dePais;
	
	@Column(name="co_telefone", length=11)
	private String coTelefone;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_nascimento")
	private Date dtNascimento;

	@Column(name="de_sexo", length=1)
	private String deSexo;
	
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

	public String getCoCPF() {
		return coCPF;
	}

	public void setCoCPF(String coCPF) {
		this.coCPF = coCPF;
	}

	public String getDeEndereco() {
		return deEndereco;
	}

	public void setDeEndereco(String deEndereco) {
		this.deEndereco = deEndereco;
	}

	public String getCoCep() {
		return coCep;
	}

	public void setCoCep(String coCep) {
		this.coCep = coCep;
	}

	public String getDeCidade() {
		return deCidade;
	}

	public void setDeCidade(String deCidade) {
		this.deCidade = deCidade;
	}

	public String getDePais() {
		return dePais;
	}

	public void setDePais(String dePais) {
		this.dePais = dePais;
	}

	public String getCoTelefone() {
		return coTelefone;
	}

	public void setCoTelefone(String coTelefone) {
		this.coTelefone = coTelefone;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getDeSexo() {
		return deSexo;
	}

	public void setDeSexo(String deSexo) {
		this.deSexo = deSexo;
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
