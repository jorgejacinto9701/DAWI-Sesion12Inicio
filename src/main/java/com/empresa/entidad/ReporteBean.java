package com.empresa.entidad;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class ReporteBean {

	private String dia;
	private String ciclo;
	private long cantidad;
	
	public ReporteBean(String dia, long cantidad) {
		super();
		this.dia = dia;
		this.cantidad = cantidad;
	}

	public ReporteBean(String dia, String ciclo, long cantidad) {
		super();
		this.dia = dia;
		this.ciclo = ciclo;
		this.cantidad = cantidad;
	}

	
	
}
