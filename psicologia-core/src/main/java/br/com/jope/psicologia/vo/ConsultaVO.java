package br.com.jope.psicologia.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConsultaVO implements Serializable {

	private static final long serialVersionUID = 5635758594110099813L;
	
	private String query;
	private Map<String, Object> parametros;
	
	public ConsultaVO(String query) {
		this.query = query;
		this.parametros = new HashMap<>();
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Map<String, Object> getParametros() {
		return parametros;
	}
	
	public void addParametros(String parametro, Object valor) {
		parametros.put(parametro, valor);
	}
}
