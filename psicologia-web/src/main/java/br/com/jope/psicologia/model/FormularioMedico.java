package br.com.jope.psicologia.model;

import javax.validation.Valid;

import br.com.jope.psicologia.entity.Medico;

public class FormularioMedico extends AbstractFormulario {

	private static final long serialVersionUID = -183895083944787159L;

	@Valid
	private Medico medico;

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	
}
